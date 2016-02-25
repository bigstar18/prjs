<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>

<head>
<title>插入交易节</title>
</head>
<%
 java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
 if(request.getParameter("add")!=null){
 DBBean bean=null;
 ResultSet rs=null;
 try{
	String ids=request.getParameter("ids");
	String num=delNull(request.getParameter("num"));
	String tradepartition=delNull(request.getParameter("partitionID"));
	StringBuffer sql=null;
	//初始化本地对象
	TradeStatusValue tsv =null;
	//获得远端接口
	KernelEngineRMI dao = (KernelEngineRMI) Naming.lookup("rmi://" + host+":" +port+ "/"+REMOTECLASS+""+tradepartition);
	tsv=dao.getTradeStatus();
	if(Integer.parseInt(num)<=tsv.getLastPartID()){
   	    alert("交易节"+request.getParameter("num")+"是当前正在交易的交易节或者已经交易完成,不能插入交易节!",out);

	}else{
	  bean=new DBBean(JNDI);
	  String dataBaseDate=null;//取数据库服务器时间
      rs=bean.executeQuery("select sysdate from dual");
	  if(rs.next()){
	      dataBaseDate=rs.getString("sysdate");
	  }
	  rs.close();
	  bean.closeStmt();
	  int operCnt=0;//操作商品数量
      String operCode=null;//操作单个商品标的号
	  sql=new StringBuffer();
	  sql.append("select count(code) as n from v_curcommodity where ");
	  sql.append("section>="+num+" and tradepartition="+tradepartition+"");
	  rs=bean.executeQuery(sql.toString());
	  if(rs.next()){
		  operCnt=rs.getInt("n");
	  }
	  rs.close();
	  bean.closeStmt();
	  if(operCnt==1){
          sql=new StringBuffer();
          sql.append("select code from v_curcommodity where ");
		  sql.append("section>="+num+" and tradepartition="+tradepartition+"");
		  rs=bean.executeQuery(sql.toString());
		  if(rs.next()){
			  operCode=rs.getString("code");
		  }
		  rs.close();
		  bean.closeStmt();
	  }
	  sql=new StringBuffer("update v_curcommodity set section=section+1,modifytime=to_date('"+disDate(Timestamp.valueOf(dataBaseDate))+"'");
	  sql.append(",'yyyy-mm-dd hh24:mi:ss') where section>="+num+" and tradepartition="+tradepartition+"");
	  bean.executeUpdate(sql.toString());
	  //关闭数据源
	  bean.close();
      
	  //刷新内存-
      dao.loadCommodity();
	  alert("插入交易节成功!",out);
    }
  }
  catch(Exception e)
  {
  	  e.printStackTrace();
      errOpt();
	  alert("操作出错，请与管理员联系!",out);
  }finally{
      try{if(rs!=null)rs.close();}catch(Exception ex){}
      if(bean!=null)bean.close();
  }
  }
%>
<body>
	<form name=frm id=frm method="post" action="" targetType="hidden" callback='closeDialog(1);'>
		<fieldset width="100%">
		<legend>输入交易节编号</legend>
		<BR>
		<span>
		 <table border="0" cellspacing="0" cellpadding="0" width="100%">
		 	  	<tr height="35">
		 	  		<td align="center">
		 	  		  <input type="text" class="text" maxlength="5" name="num" onchange="checkNumber(this,false,false,'起点交易节')">
		 	  	  </td>
		 	  	</tr>
		 </table>
		 </span>
     </fieldset>
		 <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
            <td width="40%">
            <div align="center">
            <input type="hidden" name="add">
            <input type="hidden" name="ids" value="${param.ids}">
            <input type="hidden" name="partitionID" value="${param.partitionID}">
			      <input type="button" onclick="return frmChk();" class="btn" value="确认">&nbsp;&nbsp;
			      <input name="back" type="button" onclick="window.close()" class="btn" value="取消">&nbsp;&nbsp;
            </div>
            </td>
          </tr>
     </table>
	</form>
<script language="javascript">
  function frmChk(){
  	if(Trim(frm.num.value) == "")
	{
		alert("交易节编号不能为空！");
		frm.num.focus();
		return false;
	}else if(parseInt(frm.num.value)<=0){
	    alert("交易节编号必须大于0！");
		frm.num.focus();
		return false;
	}
	  else{
	  if(userConfirm()){
  	    frm.add.value="保存";
  	    frm.submit();
  	    //return true;
  	  }else{
  	    return false;
  	  }
    }
  }
</script>
</body>
</html>
<%@ include file="../../public/footInc.jsp" %>