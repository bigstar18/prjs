<%@ page pageEncoding="GBK"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="javax.naming.*"%>
<%
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	int number = 0;
	String cname = request.getParameter("cname");
	String sql = "select count(*) from v_commodity a,v_commext b,v_commoditytype c where a.id=b.commid and b.str4=c.name and c.name='"+cname+"'";
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