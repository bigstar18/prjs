<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>

<head>
<title>删除交易节</title>
</head>
<%
  java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
  if(request.getParameter("add")!=null){
  String partitionID=request.getParameter("partitionID");
  String numJ=delNull(request.getParameter("num"));
  StringBuffer sql=new StringBuffer();
  DBBean bean=null;
  ResultSet rs=null;
  try{
		  boolean modFlag=true;
		  bean=new DBBean(JNDI);
	      sql.append("select code from v_curcommodity where section="+numJ+" and tradepartition=");
		  sql.append(""+partitionID+"");
          rs=bean.executeQuery(sql.toString());
		  if(rs.next()){
		      modFlag=false;
		  }
		  rs.close();
		  bean.closeStmt();
		  if(modFlag==true){
		  //初始化本地对象
		  TradeStatusValue tsv =null;
		  //获得远端接口
		  KernelEngineRMI dao = (KernelEngineRMI) Naming.lookup("rmi://" + host+":" +port+ "/"+REMOTECLASS+""+partitionID);
		  tsv=dao.getTradeStatus();
		  if(Integer.parseInt(numJ)<=tsv.getLastPartID()){
		      pageContext.setAttribute("modFlag","false");
			  alert("交易节"+numJ+"是当前正在交易的交易节或者已经交易完成,不能删除交易节!",out);
		  }else{
			String dataBaseDate=null;//取数据库服务器时间
            rs=bean.executeQuery("select sysdate from dual");
	        if(rs.next()){
	            dataBaseDate=rs.getString("sysdate");
	        }
	        rs.close();
	        bean.closeStmt();
		    sql=new StringBuffer();
		    String tradepartition=delNull(request.getParameter("partitionID"));
			int operCnt=0;//操作商品数量
            String operCode=null;//操作单个商品标的号
			sql.append("select count(code) as n from v_curcommodity where");
			sql.append(" section>"+numJ+" and tradepartition="+tradepartition+"");
			rs=bean.executeQuery(sql.toString());
			if(rs.next()){
			    operCnt=rs.getInt("n");
			}
			rs.close();
			bean.closeStmt();
			if(operCnt==1){
			    sql=new StringBuffer();
                sql.append("select code from v_curcommodity where");
			    sql.append(" section>"+numJ+" and tradepartition="+tradepartition+"");
				rs=bean.executeQuery(sql.toString());
				if(rs.next()){
				    operCode=rs.getString("code");
				}
			    rs.close();
			    bean.closeStmt();
			}
			sql=new StringBuffer();
		    sql.append("update v_curcommodity set section=section-1,modifytime=to_date('"+disDate(Timestamp.valueOf(dataBaseDate))+"'");
		    sql.append(",'yyyy-mm-dd hh24:mi:ss') where section>"+numJ+" and tradepartition="+tradepartition+"");
			bean.executeUpdate(sql.toString());
		    //关闭数据源
		    bean.close();
			
		    //刷新内存
            dao.loadCommodity();
			alert("交易节删除成功!",out);
		  }
	  }else{
          alert("交易节"+numJ+"已有商品,不能删除!",out);
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
	<form name=frm id=frm action="" method="post" targetType="hidden" callback='closeDialog(1);'>
		<fieldset width="100%">
		<legend>输入交易节编号</legend>
		<BR>
		<span>
		<input type="hidden" name="partitionID" value="${param.partitionID}">
		 <table border="0" cellspacing="0" cellpadding="0" width="100%">
		 	  	<tr height="35">
		 	  		<td align="center">
		 	  		  <input type="text" class="text" maxlength="5" name="num" onchange="checkNumber(this,false,false,'交易节编号')">
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
			      <input type="button" onclick="return frmChk();" class="btn" value="确认">&nbsp;&nbsp;
			      <input name="back" type="button" onclick="window.close()" class="btn" value="取消">&nbsp;&nbsp;
            </div>
            </td>
          </tr>
     </table>
	</form>
<script language="javascript">
var request = new ActiveXObject("Microsoft.XMLHTTP");
var add = true;
function oneAjax(){
	var tradepartition = frm.partitionID.value;
	var unitid = frm.num.value;
	request.onreadystatechange = stateChanged;
	request.open("post","haveSection.jsp?tradepartition="+tradepartition+"&unitid="+unitid,false);
	request.send();
	request.abort();
}
function stateChanged(){
	if (request.readyState == 4){
		var result = request.responseText;
		var bs = result.split("[]");
		var number = bs[0];
		var no = Number(number);
		if(no != 0){
			add = false;
		} else if (no == 0) {
			add = true;
		}
	}
}

  function frmChk(){
	oneAjax();
	if (add == true) {
		alert("无此交易节！");
		return false;
	}
  	if(Trim(frm.num.value) == "")
	{
		alert("交易节编号不能为空！");
		frm.num.focus();
		return false;
	}else if(parseInt(frm.num.value)<=0){
	    alert("交易节编号必须大于0！");
		frm.num.focus();
		return false;
	}else{
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