package gnnt.trade.bank.dao.page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PageQuery {

//	public static final int MAX_PAGE_SIZE = 9999;

	/**
	 * 获取任一页第一条数据在数据库中的位置
	 * @param pageNo 页号
	 * @param pageSize 每页包含的记录数
	 * @return 记录对应的rownum
	 * @author tanglt 2011-7-21
	 */
	private static int getStartOfAnyPage(int pageNo, int pageSize) {
		int startIndex = (pageNo - 1) * pageSize + 1;
		if (startIndex < 1)
			startIndex = 1;
//		 System.out.println("Page No to Start Index: " + pageNo + "-" +startIndex);
		return startIndex;
	}

	/**
	 * 生成查询一页数据的sql语句
	 * @param sql 原查询语句
	 * @param startIndex 开始记录位置
	 * @param pageSize 需要获取的记录数
	 * @return 格式化后sql
	 * @author tanglt 2011-7-21
	 */
	private static String intiQuerySQL(String sql, int startIndex, int pageSize) {
		StringBuffer querySQL = new StringBuffer();
		// if (pageSize < PageQuery.MAX_PAGE_SIZE) {
		querySQL.append(
				"select * from (select my_table.*,rownum as my_rownum from(")
				.append(sql).append(") my_table where rownum<")
				.append(startIndex + pageSize).append(") where my_rownum>=")
				.append(startIndex);

		return querySQL.toString();
	}

	/**
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @author tanglt 2011-7-21
	 */
	public static ResultSet executeQuery(Connection conn, PreparedStatement pst, ResultSet rs, String sql, int[] pageinfo) throws SQLException {
		// pageinfo[0]:int totalCount;
		// pageinfo[1]:int pageNo
		// pageinfo[2]:int pageSize
		// pageinfo[3]:int totalPage

		try {
			if(pageinfo==null || pageinfo.length<4){
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				return rs;
			}
			
			if(pageinfo[2]<=0){
				pageinfo[2] = 10;
			}
			
			String countSQL = "select count(*) from ( " + sql + ") ";
			pst = conn.prepareStatement(countSQL);
			rs = pst.executeQuery();
			if (rs.next()) {
				pageinfo[0] = rs.getInt(1);
				pageinfo[3]=pageinfo[0]%pageinfo[2]>0?pageinfo[0]/pageinfo[2]+1:pageinfo[0]/pageinfo[2];
				if(pageinfo[1]>pageinfo[3]){
					pageinfo[1] = pageinfo[3];
				}
			} else {
				pageinfo[0] = 0;
			}

			if (pageinfo[0] < 1){
				return rs;
			}else if(pageinfo[1]<=0){
				pageinfo[1] = 1;
			}
			
			rs.close();
			pst.close();
			
			int startIndex = getStartOfAnyPage(pageinfo[1], pageinfo[2]);
			sql = intiQuerySQL(sql, startIndex, pageinfo[2]);
//			System.out.println("sql=" + sql);

			pst = conn.prepareStatement(sql);
			pst.setFetchSize(pageinfo[2]);
			rs = pst.executeQuery();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.toString());
		}

		return rs;
	}

}
