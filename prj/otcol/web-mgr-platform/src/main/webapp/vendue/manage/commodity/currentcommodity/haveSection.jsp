<%@ page pageEncoding="GBK"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="javax.naming.*"%>
<%
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	int number = 0;
	String tradepartition = request.getParameter("tradepartition");
	String unitid = request.getParameter("unitid");
	String sql = "select count(*) from v_flowcontrol t where t.tradepartition='"+tradepartition+"' and t.unitid='"+unitid+"'";
	try {
		Context ctx=new InitialContext();
		DataSource ds=(DataSource)ctx.lookup("java:comp/env/mgr");
		conn = ds.getConnection(); 
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			number = rs.getInt(1);
		}
		rs.close();
		ps.close();
		ps=null;
		conn.close();
		conn=null;
    } catch(Exception e) {
		e.printStackTrace();
	} finally {           
		try {               
			if(conn != null) {
				conn.close();
				conn = null;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
%>
<%=number+"[]"+"0"%>