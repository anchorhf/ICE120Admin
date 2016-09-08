package com.anke.ice.dao.impl.oracle;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.anke.ice.dao.DBHelper;
import com.anke.ice.dao.LeftDao;
import com.anke.ice.model.serchleft;
import com.anke.ice.model.MYTreeNode;
import com.anke.ice.util.LoggerUtil;
import com.anke.ice.util.WhereClauseUtility;

public class LeftDaoImpl extends BaseDaoImpl implements LeftDao {
	private static final Logger logger = LoggerUtil.getInstance(LeftDaoImpl.class);
	//protected static Connection conn;//建立数据库连接
	
	@Override
	public List<MYTreeNode> select(String roleid) {
		Connection conn = DBHelper.getInstance().getConnection();
		try { 
			  String[] sourceStrArray=roleid.split(",");
			  List<serchleft> rows=null;
			 switch(sourceStrArray.length)
			 {
			 case 1:{ StringBuilder sbSQL= new StringBuilder();
			          sbSQL.append(" select me.menuid,me.label,me.parentmenuid,me.url,me.role from t_menu me left join  T_ROLEMENU rm on me.menuid=rm.menuid where me.isshow=1 and rm.roleid="+sourceStrArray[0]+" order by me.menuid");
				      String sql = sbSQL.toString();
//						System.out.println(sql);
			          rows =runner.query(conn,sql, new BeanListHandler<serchleft>(serchleft.class));
			   break; }
			 
			 case 2:{ StringBuilder sbSQL= new StringBuilder();
			  sbSQL.append(" select menuid,label,parentmenuid,url,role from (");
			  sbSQL.append(" (select me.menuid,me.label,me.parentmenuid,me.url,me.role from t_menu me left join  T_ROLEMENU rm on me.menuid=rm.menuid where me.isshow=1 and rm.roleid="+sourceStrArray[0]+")");
			  sbSQL.append(" union ");
			  sbSQL.append(" (select me.menuid,me.label,me.parentmenuid,me.url,me.role from t_menu me left join  T_ROLEMENU rm on me.menuid=rm.menuid where me.isshow=1 and rm.roleid="+sourceStrArray[1]+")) order by menuid");
			  String sql = sbSQL.toString();
//				System.out.println(sql);
	          rows =runner.query(conn,sql, new BeanListHandler<serchleft>(serchleft.class));
			   break; }
			 
			 case 3:{ StringBuilder sbSQL= new StringBuilder();
			  sbSQL.append(" select menuid,label,parentmenuid,url,role from (");
			  sbSQL.append(" (select me.menuid,me.label,me.parentmenuid,me.url,me.role from t_menu me left join  T_ROLEMENU rm on me.menuid=rm.menuid where me.isshow=1 and rm.roleid="+sourceStrArray[0]+" )");
			  sbSQL.append(" union ");
			  sbSQL.append(" (select me.menuid,me.label,me.parentmenuid,me.url,me.role from t_menu me left join  T_ROLEMENU rm on me.menuid=rm.menuid where me.isshow=1 and rm.roleid="+sourceStrArray[1]+" )");
			  sbSQL.append(" union ");
			  sbSQL.append(" (select me.menuid,me.label,me.parentmenuid,me.url,me.role from t_menu me left join  T_ROLEMENU rm on me.menuid=rm.menuid where me.isshow=1 and rm.roleid="+sourceStrArray[2]+" )) order by menuid");
			  String sql = sbSQL.toString();
//				System.out.println(sql);
	          rows =runner.query(conn,sql, new BeanListHandler<serchleft>(serchleft.class));
			   break; }
			 }   
	//		 List<serchleft> rows =runner.query(conn, "select * from T_MENU where ROLE LIKE '%"+sourceStrArray+"%'", new BeanListHandler<serchleft>(serchleft.class));
			return ToTreeNodes(rows);
		} catch (SQLException e) {
			logger.error("查询左侧列表失败", e.getCause());
			return null;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
	}
	
     public static List<MYTreeNode> ToTreeNodes(List<serchleft> rows)
     {
         List<MYTreeNode> listNodes = new ArrayList<MYTreeNode>();
         //生成 树节点时，根据 pid=0的根节点 来生成
         LoadTreeNode(rows, listNodes, 0);
         return listNodes;
     }
  
    
     /// <param name="pID">节点父id</param>
     public static void LoadTreeNode(List<serchleft> rows, List<MYTreeNode> listChildrenNode, int pID)
     {
        for(int i=0;i<rows.size();i++){
             //判断权限ParentID 是否和 传入参数相等
             if (rows.get(i).getPARENTMENUID()==pID)
             {
                 //将 权限转成 树节点
            	 MYTreeNode node =ToTreeNode(rows.get(i));
                 //将节点加入到 树子节点集合
                 listChildrenNode.add(node);
                 //递归 为这个新创建的 树节点找 子节点
                 LoadTreeNode(rows, node.children, node.id);
             }
         }
     }
     private static MYTreeNode ToTreeNode(serchleft leftinfo)
     {
         //对象的初始化器直接构造
            MYTreeNode treeNode = new MYTreeNode();
            treeNode.id=leftinfo.getMENUID();
            treeNode.text = leftinfo.getLABEL();
            treeNode.state = "open";
            treeNode.checked = false;
            treeNode.url = leftinfo.getURL();
            treeNode.children = new ArrayList<MYTreeNode>(); // 子节点集合
            return treeNode;
     }
}		
