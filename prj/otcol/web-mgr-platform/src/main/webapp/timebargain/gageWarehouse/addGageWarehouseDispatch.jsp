<%@ page pageEncoding="GBK"%>
<%@ page import="java.sql.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="javax.naming.*" %>
  
  <body>
  <%
  //创建连接数据对象
  Connection conn = null;
  PreparedStatement ps = null;
  ResultSet rs = null;
  String dispatchUrl = "addGageWarehouse.jsp";
  try
  {
		Context initContext = new InitialContext();
		Context envContext  = (Context)initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource)envContext.lookup("mgr");
		conn = ds.getConnection();
    
		ps=conn.prepareStatement(" select t.issettle issettle from M_TradeModule t where t.moduleid=2 ");
		rs=ps.executeQuery();
		if(rs.next())
		{
			if("Y".equalsIgnoreCase(rs.getString("issettle")))
			{
				dispatchUrl = "addGageRelatedWarehouse.jsp";
			}
		}
	}
	catch(Exception e)
	{
		dispatchUrl = "addGageWarehouse.jsp";
	}
	finally
	{
		if(rs!=null){try{rs.close();}catch(Exception ex){}rs=null;}
		if(ps!=null){try{ps.close();}catch(Exception ex){}ps=null;}
		if(conn!=null){try{conn.close();}catch(Exception e){}conn = null;}
	}
%>
<body onload="forwardPage('<%=dispatchUrl %>')"></body>
<script refer>
	function forwardPage(nextPage)
	{
		window.location.href = nextPage;
	}
</script>
  </body>
