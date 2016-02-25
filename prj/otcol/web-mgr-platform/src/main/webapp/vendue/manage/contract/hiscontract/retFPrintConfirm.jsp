<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/vendue/manage/globalDef.jsp"%>

<style media=print>
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
<html>
<head><title>合同信息</title></head>
<%
  String contractID=request.getParameter("contractID");
%>
<%
  //创建连接数据对象
  Connection conn = null;
  PreparedStatement ps = null;
  ResultSet rs = null;
  int cnt = 0;
  StringBuffer sql=new StringBuffer();
  try{
    Context initContext = new InitialContext();
    Context envContext  = (Context)initContext.lookup("java:/comp/env");
    DataSource ds = (DataSource)envContext.lookup(JNDI);
    conn = ds.getConnection();
	String opt=delNull(request.getParameter("opt"));
%>
<body>
<form name="frm">
<%
    sql=new StringBuffer("select contractcontent from v_hisbargain where contractid='"+contractID+"'");
    ps = conn.prepareStatement(sql.toString());
	rs=ps.executeQuery();
%>
<%
	while(rs.next()){
	  Clob clob;
	  clob=rs.getClob(1);
      if(clob!=null){
        Reader is=clob.getCharacterStream();
        int c=0;
        char[] buffer=new char[100];
        StringBuffer str=null;
        while ((c=is.read(buffer))!=-1) {
          str=new StringBuffer();
          str.append(buffer,0,c);
          out.print(str.toString());
        }
      }
%>
 <%}
     rs.close();
     ps.close();
 %>
<div align="center" id="butDivMod" name="butDivMod" class="Noprint">
<input type="button" onclick="window.print();" class="btn" value="打印">
</div>
</body>
<input type="hidden" name="opt">
<input type="hidden" name="contractID" value="<%=contractID%>">
</form>
</html>
<%
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