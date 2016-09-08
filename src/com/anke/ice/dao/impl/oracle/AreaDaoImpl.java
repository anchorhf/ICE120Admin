package com.anke.ice.dao.impl.oracle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.anke.ice.dao.AreaDao;
import com.anke.ice.dao.DBHelper;
import com.anke.ice.model.AreaModel;
import com.anke.ice.model.MYTreeNode;
import com.anke.ice.util.LoggerUtil;

public class AreaDaoImpl extends BaseDaoImpl implements AreaDao {
	private static final Logger logger = LoggerUtil.getInstance(AreaDaoImpl.class);
	//protected static Connection conn;//建立数据库连接
	private static int id = 1;//

	// 根据表名获取表数据
	@Override
	public List<MYTreeNode> getAreaInfoList(String table) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			StringBuilder sbSQL = new StringBuilder();
			sbSQL.append(" select ID,NAME,FATHERID,ORDERNO,ISVALID");
			sbSQL.append(" from " + table);
			sbSQL.append(" order by ORDERNO");

			String sql = sbSQL.toString();
			List<AreaModel> rows = null;
			
			rows = runner.query(conn, sql, new BeanListHandler<AreaModel>(AreaModel.class));

			return ToTreeNodes(rows);
		} catch (SQLException e) {
			logger.error("查询" + table + "列表失败", e.getCause());
			return null;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
	}

	public static List<MYTreeNode> ToTreeNodes(List<AreaModel> rows) {
		List<MYTreeNode> listNodes = new ArrayList<MYTreeNode>();
		// 生成 树节点时，根据 pid=0的根节点 来生成
		LoadTreeNode(rows, listNodes, -1);
		return listNodes;
	}

	/// <param name="pID">节点父id</param>
	public static void LoadTreeNode(List<AreaModel> rows, List<MYTreeNode> listChildrenNode, int pID) {
		for (int i = 0; i < rows.size(); i++) {
			// 判断权限ParentID 是否和 传入参数相等
			if (rows.get(i).getFATHERID() == pID) {
				// 将 权限转成 树节点
				MYTreeNode node = ToTreeNode(rows.get(i));
				// 将节点加入到 树子节点集合
				listChildrenNode.add(node);
				// 递归 为这个新创建的 树节点找 子节点
				LoadTreeNode(rows, node.children, node.id);
			}
		}
	}

	private static MYTreeNode ToTreeNode(AreaModel info) {
		// 对象的初始化器直接构造
		MYTreeNode treeNode = new MYTreeNode();
		treeNode.id = info.getID();
		treeNode.text = info.getNAME();
		treeNode.state = "open";
		treeNode.checked = false;
		treeNode.url = "";
		treeNode.attributes = String.valueOf(info.getISVALID()); // 储存是否有效
		treeNode.orderno = info.getORDERNO();// 顺序号
		treeNode.children = new ArrayList<MYTreeNode>(); // 子节点集合
		return treeNode;
	}

	// 新增或者保存
	@Override
	public AreaModel saveOrUpdate(AreaModel bean) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			if (bean.getID() <= 0) {
				bean.setID(getdictionaryID());
				sql = DBHelper.createInsertSql(bean);
				runner.insert(conn, sql, new BeanHandler<AreaModel>(AreaModel.class));
			} else {
				sql = DBHelper.createUpdateSql(bean);
				runner.update(conn, sql);
			}
		} catch (SQLException e) {
			logger.error("保存区域信息失败", e);
			return null;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
		return bean;
	}

	// 获取字典表最大id
	public int getdictionaryID() {
		Connection conn = DBHelper.getInstance().getConnection();
		//int id = 1;
		try {
			//conn = DBHelper.getInstance().getConnection();
			AreaModel model = runner.query(conn, "select * from T_AREA order by id desc",
					new BeanHandler<AreaModel>(AreaModel.class));
			if (model != null) {
				if (id <= (model.getID() + 1))
					id = model.getID() + 1;
				else
					id++;
			}
		} catch (SQLException e) {
			logger.error("生成区域编码失败", e.getCause());
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
		return id;
	}

	/*// 新增或者保存（泛型-可以根据传进的表自动）
	@Override
	public <T> int saveOrUpdate(T bean) {
		int d = 0;

		try {
			int getID = (int) bean.getClass().getMethod("getID").invoke(bean);
			if (getID <= 0) {
				bean.getClass().getMethod("setID").invoke(getdictionaryID(bean));
				sql = DBHelper.createInsertSql(bean);
				runner.insert(conn, sql, new BeanHandler<AreaModel>(AreaModel.class));
				d = 1;
			} else {
				sql = DBHelper.createUpdateSql(bean);
				runner.update(conn, sql);
				d = 1;
			}
		} catch (SQLException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			logger.error("保存字典信息失败", e);
			d = 0;
		}
		return d;
	}

	public <T> int getdictionaryID(T model) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		int id = 1;
		Table table = model.getClass().getAnnotation(Table.class); // Field[]
																	// fields =
		model.getClass().getDeclaredFields();
		AreaModel result = null;
		try {
			result = runner.query(conn, "select * from " + table.tableName() + " order by id desc",
					new BeanHandler<AreaModel>(AreaModel.class));
			if (result != null) {
				int getID = (int) result.getClass().getMethod("getID").invoke(result); //
				System.out.println(getID);
				if (id <= (getID + 1))
					id = getID + 1;
				else
					id++;
			}
		} catch (SQLException e) {
			id = 0;
			logger.error("生成区域编码失败", e.getCause());
		}
		return id;
	}*/

	// 修改区域为有效或无效
	@Override
	public int updateAreaIsValid(String table, int id, int isvalid) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {

			return runner.update(conn, "update " + table + " set ISVALID=" + isvalid + " where ID=" + id);
		} catch (SQLException e) {
			logger.error("修改表:" + table + " 有效或无效失败", e.getCause());
			return 0;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
	}
}
