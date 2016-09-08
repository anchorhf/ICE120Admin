package com.anke.ice.dao.impl.oracle;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;


import com.anke.ice.IDConstant;
import com.anke.ice.dao.DBHelper;
import com.anke.ice.dao.FirstAHBDao;

import com.anke.ice.model.FirstAHBModel;


import com.anke.ice.util.LoggerUtil;
import com.anke.ice.util.WhereClauseUtility;
import com.cloopen.rest.sdk.utils.encoder.BASE64Decoder;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FirstAHBDaoImp extends BaseDaoImpl implements FirstAHBDao{
	private static final Logger logger = LoggerUtil.getInstance(FirstAHBDaoImp.class);
	//protected static Connection conn;//建立数据库连接
	private static int id = 1;
	
	public int generifID() {
		Connection conn = DBHelper.getInstance().getConnection();
		//int id=1;
		try {
			FirstAHBModel model = runner.query(conn, "select * from t_manual order by id desc", new BeanHandler<FirstAHBModel>(FirstAHBModel.class));
			if (model != null) {
				if (id <= (model.getId() + 1))
					id = model.getId() + 1;
				else
					id++;
			}
		} catch (SQLException e) {
			logger.error("生成急救手册编码失败", e.getCause());
		}
		return id;
	}
	
	
	@Override
	public Map<String, Object> FirstAHBList(int pageNum, int pageSize,String mname,int morderno,String mprofile,int misvolunteer,int misvalid)
	{
		Map<String, Object> page = new HashMap<String, Object>();
	    if(morderno==0){
	     morderno=-1;	
	    }
		Connection conn = DBHelper.getInstance().getConnection();
		try{
			StringBuilder sbSQL = new StringBuilder();
			sbSQL.append(" select id as tabid,name as tabname,isvolunteer as tabisvolunteer,profile as tabprofile,isvalid as tabisvalid,orderno as taborderno,url as taburl,rn");
			sbSQL.append(" from (");
			sbSQL.append(" select id,name,isvolunteer,profile,isvalid,orderno,url,rownum rn");
			sbSQL.append(" from (");
			sbSQL.append(" select tm.id,tm.name,tm.profile,(case when tm.isvolunteer=1 then '可见' else '不可见' end) isvolunteer,(case when tm.isvalid=1 then '有效' else '无效' end) isvalid,tm.orderno,tm.url");
			sbSQL.append(" from t_manual tm");
			sbSQL.append(" where 1=1");
			WhereClauseUtility.AddStringLike("tm.name", mname, sbSQL);// 志愿者
			WhereClauseUtility.AddStringLike("tm.profile", mprofile, sbSQL);// 申请地区
			WhereClauseUtility.AddIntEqual("tm.isvalid", misvalid, sbSQL);// 申请状态
			WhereClauseUtility.AddIntEqual("tm.isvolunteer", misvolunteer, sbSQL);// 申请状态
			WhereClauseUtility.AddIntEqual("tm.orderno", morderno, sbSQL);// 申请状态
			sbSQL.append(" order by tm.orderno");
			sbSQL.append(" )t");
			sbSQL.append(" )tt");
			StringBuilder sbSQLR = new StringBuilder();
			sbSQLR.append(sbSQL +" where tt.rn>" + ((pageNum - 1) * pageSize) + " and tt.rn<=" + pageNum * pageSize);
		
			String sql = sbSQLR.toString();
//			System.out.println(sql);
			StringBuilder sbSQLT = new StringBuilder();//取总共条数
			sbSQLT.append("select count(*) from ("+sbSQL+")ttt");
			String countSql = sbSQLT.toString();

			int total = runner.query(conn, countSql, new ScalarHandler<BigDecimal>()).intValue();

			List<FirstAHBModel> rows = runner.query(conn, sql,new BeanListHandler<FirstAHBModel>(FirstAHBModel.class));
			
			page.put("total", total);
			page.put("rows", rows);
			page.put("pages", (total % pageSize != 0 ? total / pageSize + 1 : total / pageSize));
			page.put("to", pageNum * pageSize);
		} catch (SQLException e) {
			logger.error("查询急救手册列表失败", e.getCause());
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
		return page;
	}
	
	@Override
	public FirstAHBModel saveOrUpdate(FirstAHBModel bean){	
		Connection conn = DBHelper.getInstance().getConnection();
		try {
		     String getcount=bean.getStrcontent();
		     bean.setContent(null);
		     bean.setStrcontent(null);
		     
		     int imgcount=bean.getPicname().lastIndexOf("/");
		     String imgname;
		     if(imgcount==-1){
		    	 imgname=bean.getPicname();
		     }else{
		    	 imgname=bean.getPicname().substring(imgcount+1); 
		     }
		 
		  
		     try {
				  SaveUrl(bean.getStrurl(),imgname);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		     
		     InputStream fis = this.getClass().getResourceAsStream("/db.properties");
			    Properties p = new Properties();
				try {
					p.load(fis);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String img_url = p.getProperty("img_url");

		     String urlpath;
		     if(imgname.equals("")){
		    	 urlpath="";
		     }else{
		    	 urlpath=img_url+imgname; 
		     }
				
				 bean.setUrl(urlpath);
				 bean.setStrurl(null);
				 bean.setPicname(null);
			 bean.setTabid(null);
			 bean.setTaborderno(null);
			
			if (bean.getId()<= 0) {
				bean.setId(generifID());
				
//				System.out.println(bean.getId());
				sql = DBHelper.createInsertSql(bean);
				runner.insert(conn, sql, new BeanHandler<FirstAHBModel>(FirstAHBModel.class));
				
				try {
					UpdateContent(getcount, bean.getId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
					
					
			} else {
				sql = DBHelper.createUpdateSql(bean);
				runner.update(conn, sql);
				
				try {
					UpdateContent(getcount, bean.getId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			   }
			} catch (SQLException e) {
			logger.error("保存急救手册失败", e);
			return null;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
		return bean;
		
	}
	
	public int updatecontent(int id,String strArray) {
		Connection conn = DBHelper.getInstance().getConnection();
		try{
			
			String updatesql ="update t_manual set content=content||'"+strArray+"' where id="+id+"";
			runner.update(conn, updatesql);
			return 1;
		   } 
		catch(SQLException e)
		{logger.error("更新急救手册详细失败", e);
		return 0;}
		finally{
			DbUtils.closeQuietly(conn);
		}
		
	}
	
	
	public FirstAHBModel get(int id){
		Connection conn = DBHelper.getInstance().getConnection();
		
		try { 
			StringBuilder sbSQL= new StringBuilder();

			sbSQL.append(" select tm.id,tm.name,tm.orderno,tm.profile,tm.isvolunteer,tm.isvalid,tm.url as picname");
		    sbSQL.append(" from t_manual tm");
			sbSQL.append(" where 1=1");
			WhereClauseUtility.AddIntEqual("tm.id", id, sbSQL);
			String sql = sbSQL.toString();
			FirstAHBModel getfirstahbinfo=runner.query(conn, sql, new BeanHandler<FirstAHBModel>(FirstAHBModel.class));
			FirstAHBModel clobconetent=runner.query(conn,"select content as firstahbcontent from t_manual where id="+id, new BeanHandler<FirstAHBModel>(FirstAHBModel.class));
			Clob clob = clobconetent.getFirstahbcontent();
			   if(clob==null)
			   { 
				   getfirstahbinfo.setStrcontent(null);
			   }
			   else{  
			   String firstahbdetail;
			   try {
				firstahbdetail = ClobToString(clob);
				getfirstahbinfo.setStrcontent(firstahbdetail);
			   } catch (IOException e) {
				e.printStackTrace();
			   }   
			}
			   return getfirstahbinfo;
		} catch (SQLException e) {
			logger.error("得到急救手册失败", e.getCause());
			return null;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
	}
	
	 public String ClobToString(Clob clob) throws SQLException, IOException {

	        String reString = "";
	        Reader is = clob.getCharacterStream();// 得到流
	        BufferedReader br = new BufferedReader(is);
	        String str = br.readLine();
	        StringBuffer sb = new StringBuffer();
	        while (str != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
	            sb.append(str);
	            str = br.readLine();
	        }
	        reString = sb.toString();
	        return reString;
	    }
	 
	
			
	 public void SaveUrl(String strurl,String url) throws IOException {
		 
          String[] piccode=strurl.split(",", 2);
       
		  BASE64Decoder decoder=new BASE64Decoder();
		  
		  try {  
			// Base64解码  
			byte[] bytes = decoder.decodeBuffer(piccode[1]);  
			for (int i = 0; i < bytes.length; ++i) {  
			if (bytes[i] < 0) {// 调整异常数据  
			bytes[i] += 256;  
			}  
			}
			String savepath="../webapps/picfiles/icons/"+url;
			OutputStream out = new FileOutputStream(savepath);  
			out.write(bytes);  
			out.flush();  
			out.close(); 
			} catch (Exception e) {  
						} 
		 }

	 public void UpdateContent(String obj,int id) throws Exception  
	    {  
			Connection conn = DBHelper.getInstance().getConnection();
	        PreparedStatement pstmt = null;  
	        try  
	        {  
	        	String updateSql = "update t_manual set content=? where id="+id+"";

	            pstmt = conn.prepareStatement(updateSql);  
	            pstmt.setCharacterStream(1, new StringReader(obj));  
	            pstmt.executeUpdate();  
	        }  
	        catch (Exception e)  
	        {  
	            logger.error("修改急救手册content失败！");  
	            throw e;  
	        }  
	        finally  
	        {  
	            if (null != pstmt)  
	                pstmt.close();  
	            if (null != conn)  
	                conn.close();  
	        }  
	        
	    }  	

}
