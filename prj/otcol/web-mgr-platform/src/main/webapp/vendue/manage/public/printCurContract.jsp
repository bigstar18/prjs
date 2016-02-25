<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<!-- IE5.5以上 -->
<html>
<head>
	<title>打印</title>
	<style media=print> 
      .PageNext{page-break-after: always;} 
    </style>
</head>
<body style="margin:0;font-size:9pt">
<%
	//创建连接数据对象
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
	try{
		Context initContext = new InitialContext();
        Context envContext  = (Context)initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource)envContext.lookup(JNDI);
        conn = ds.getConnection();
	    String contractIds=request.getParameter("contractIds");
		String printing=request.getParameter("printing");
		StringBuffer sql=new StringBuffer();
		int rowCnt=0;
		int line=0;
		//将合同内容写入页面
		if(ManaUtil.checkStr(contractIds)){
			sql=new StringBuffer();
			sql.append("select count(contractid) as n from v_bargain where contractid in ("+contractIds+")");
            ps=conn.prepareStatement(sql.toString());
			rs=ps.executeQuery();
			if(rs.next()){
			    rowCnt=rs.getInt("n");
			}
			rs.close();
			ps.close();
			sql=new StringBuffer();
			sql.append("select contractcontent from v_bargain where contractid in ("+contractIds+")");
			Clob clob;
			String contractContent;
            ps=conn.prepareStatement(sql.toString());
			rs=ps.executeQuery();
			while(rs.next()){
	            clob=rs.getClob("contractcontent");
                contractContent="";
                if(clob!=null){
                    Reader is=clob.getCharacterStream();
                    int c=0;
                    char[] buffer=new char[100];
                    while ((c=is.read(buffer))!=-1) {
                        StringBuffer str=new StringBuffer();
                        str.append(buffer,0,c);
				        contractContent+=str.toString();
                    }
					if(line==rowCnt-1){
					    out.print(contractContent);
					}else{
					    out.print(contractContent+"<div class='PageNext'></div>");
					}
					line++;
                }
			}
			rs.close();
			ps.close();
			conn.commit();
            conn.setAutoCommit(true);
		}
	%>
	<script language="javascript">
        window.print();
 	    window.close();
	</script>
</body>
</html>
<%
}catch(Exception e){
    conn.rollback();
    e.printStackTrace();
}finally{
    if(ps!=null){try{ps.close();}catch(Exception ex){}ps=null;}
	try{
        conn.close();
    }catch (Exception e){}
        conn = null;
}
%>
<%@ include file="/vendue/manage/public/footInc.jsp" %>
