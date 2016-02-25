<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/vendue/manage/globalDef.jsp"%> 
<%
  //创建连接数据对象
  Connection conn = null;
  PreparedStatement ps = null;
  ResultSet rs = null;
  String dispatchUrl = "hiscontract/hisConList.jsp?funcflg=true";
  try{
    Context initContext = new InitialContext();
    Context envContext  = (Context)initContext.lookup("java:/comp/env");
    DataSource ds = (DataSource)envContext.lookup(JNDI);
    conn = ds.getConnection();
    
    ps=conn.prepareStatement(" select t.issettle issettle from M_TradeModule t where t.moduleid=4 ");
	rs=ps.executeQuery();
	if(rs.next()){
		if("Y".equalsIgnoreCase(rs.getString("issettle")))
		{
			dispatchUrl = "hiscontractForSettle/hisConList.jsp?funcflg=true";
		}
	}
		
			
 }catch(Exception e){
     e.printStackTrace();
 }finally{
	 if(rs!=null){try{rs.close();}catch(Exception ex){}rs=null;}
     if(ps!=null){try{ps.close();}catch(Exception ex){}ps=null;}
	 try{
         conn.close();
     }catch (Exception e){}
         conn = null;
 } 
%>
<body onload="forwardPage('<%=dispatchUrl %>')"></body>
<script refer>
	function forwardPage(nextPage)
	{
		window.location.href = nextPage;
	}
</script>
