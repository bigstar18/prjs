<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>

<style media=print>
    .Noprint{display:none;}<!--�ñ���ʽ�ڴ�ӡʱ���طǴ�ӡ��Ŀ-->
</style>
<html>
<head><title>��ͬ��Ϣ</title></head>
<%
  String contractID=request.getParameter("contractID");
  StringBuffer sql=new StringBuffer(); 
%>
<%
  //�����������ݶ���
  Connection conn = null;
  PreparedStatement ps = null;
  ResultSet rs = null;
  int cnt = 0;
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
	  
    sql=new StringBuffer("select contractcontent from v_bargain where contractid="+contractID+"");
    ps = conn.prepareStatement(sql.toString());
	rs=ps.executeQuery();
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
<div align="center" id="butDivMod" name="butDivMod" class="Noprint">
     <input type="submit" onclick="javascript:window.print();" class="btn" value="��ӡ">
</div>
<input type="hidden" name="opt">
<input type="hidden" name="contractID" value="<%=contractID%>">
</form>
</body>
</html>
<%
	}
      rs.close();
      ps.close();
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