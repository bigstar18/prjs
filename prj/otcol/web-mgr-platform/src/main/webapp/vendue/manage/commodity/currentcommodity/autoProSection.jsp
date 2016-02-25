<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>

<head>
<title>自动分配交易节</title>
</head>
<%
java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
if(request.getParameter("add")!=null){
Connection conn = null;
PreparedStatement ps = null;
ResultSet rs=null;
int startSection=Integer.parseInt(request.getParameter("startSection"));//起始交易节
String partitionID=request.getParameter("partitionID");//板块号
boolean curSectionFlag=false;//判断输入的起始交易节是否大于当前正进行交易的交易节,是的话不能指定,标志控制
StringBuffer sql=new StringBuffer();
try{
	Context initContext = new InitialContext();
    Context envContext  = (Context)initContext.lookup("java:/comp/env");
    DataSource ds = (DataSource)envContext.lookup(JNDI);
    conn = ds.getConnection();
    conn.setAutoCommit(false);
	sql.append("select section from v_syscurstatus where tradepartition="+partitionID+" and section>=");
	sql.append(""+(startSection)+"");
	ps=conn.prepareStatement(sql.toString());
	rs=ps.executeQuery();
	if(rs.next()){
	    curSectionFlag=true;
	}
	rs.close();
	ps.close();
	if(curSectionFlag==false){//判断输入的起始交易节是否大于当前正进行交易的交易节,是的话不能指定
    String ids=request.getParameter("ids");//标的号集合
    int num=Integer.parseInt(request.getParameter("num"));//每节标的数
    String startCode=request.getParameter("startCode");
    String endCode=request.getParameter("endCode");
    //String[] idsArray=ids.split(",");
	String errorMess="";
    int status=-1;//商品的状态
    ArrayList optResult=new ArrayList();//存放可以修改交易节的商品
    String returnInfo="";//存放不能修改交易节的商品,返回给客户
	int operCnt=0;//操作商品数量
    String operCode=null;//操作单个商品标的号
    String w="(";//sql语句条件
	String dataBaseDate=null;//取数据库服务器时间
	//获得远端接口
	KernelEngineRMI dao = (KernelEngineRMI) Naming.lookup("rmi://" + host +":"+port+ "/"+REMOTECLASS+""+partitionID);
	ps=conn.prepareStatement("select sysdate from dual");
    rs=ps.executeQuery();
	if(rs.next()){
	    dataBaseDate=rs.getString("sysdate");
	}
	rs.close();
	ps.close();
    sql=new StringBuffer("select code,bargainflag,section from v_curcommodity");
    sql.append(" where code>='"+startCode+"' and code<='"+endCode+"' and tradepartition");
    sql.append("="+partitionID+" order by code asc");
	ps=conn.prepareStatement(sql.toString());
    rs=ps.executeQuery(sql.toString());
    while(rs.next()){
       String code=rs.getString("code");
       status=rs.getInt("bargainflag");
	   if(FindData.judgeCommTrading(JNDI,Integer.parseInt(partitionID),rs.getInt("section"))){
           if(status==0){
               optResult.add(code);
           }else if(status==1){
               returnInfo=returnInfo+code+",";
           }
	   }else{
	       returnInfo=returnInfo+code+",";
           errorMess+=code+",";
	   }
    }
    //关闭数据源
    rs.close();
    ps.close();
    if(optResult.size()>=num){
    for(int i=0;i<optResult.size();i++){
      if((i+1)%num==0||(i+1)==optResult.size()){
        w=w+"'"+optResult.get(i).toString()+"'"+")";
		ps=conn.prepareStatement("update v_curcommodity set section="+startSection+",modifytime=to_date('"+disDate(Timestamp.valueOf(dataBaseDate))+"','yyyy-mm-dd hh24:mi:ss') where code in "+w);
		ps.executeUpdate();
		ps.close();
        w="(";
        startSection=startSection+1;
      }else{
        w=w+"'"+optResult.get(i).toString()+"'"+",";
      }
    }
	operCnt=optResult.size();
	if(operCnt==1){
	    operCode=startCode;
	}
	
	//关闭数据源操作
	conn.commit();
    conn.setAutoCommit(true);
    conn.close();
	//刷新内存
    dao.loadCommodity();
    if("".equals(returnInfo)){
		alert("自动分配交易节成功!",out);
    }else{
        alert("操作结果:\r\n"+"商品 "+returnInfo+"是成交状态或者正在交易中,不能分配交易节!",out);
    }
    }else{
	    if(!"".equals(errorMess)){
	        errorMess+="正在交易中,不能分配交易节;";
	    }
		alert(errorMess+"商品数量小于每节标的数,不能正确分配!",out);
    }
 }else{
     alert("所输入的起始交易节编号已经交易完成,请重新输入！",out);
 }
}

catch(Exception e)
{   
	conn.rollback();
  	e.printStackTrace();
    errOpt();
	alert("操作出错，请与管理员联系!",out);
}finally{
	if(rs!=null){try{rs.close();}catch(Exception ex){}rs=null;}
    if(ps!=null){try{ps.close();}catch(Exception ex){}ps=null;}
    try{
        conn.close();
    }catch (Exception e){}
    conn = null;
}
}
%>
<body>
	<form name=frm id=frm action="" targetType="hidden" callback='closeDialog(1);'>
		<fieldset width="100%">
		<legend>输入交易节编号</legend>
		<BR>
		<span>
		 <table border="0" cellspacing="0" cellpadding="0" width="100%">
		       <tr height="35">
		 	  	    <td align="right">起始标的号：</td>
		 	  		<td align="left">
		 	  		  <input type="text" maxlength="64" class="text" name="startCode">
		 	  	    </td>
		 	  	</tr>
		 	  	<tr height="35">
		 	  	    <td align="right">结束标的号：</td>
		 	  		<td align="left">
		 	  		  <input type="text" maxlength="64" class="text" name="endCode">
		 	  	    </td>
		 	  	</tr>
		 	  	<tr height="35">
		 	  	    <td align="right">起始交易节编号：</td>
		 	  		<td align="left">
		 	  		  <input type="text" maxlength="5" class="text" name="startSection" onchange="checkNumber(this,false,false,'起始交易节编号')">
		 	  	    </td>
		 	  	</tr>
		 	  	<tr height="35">
		 	  	  <td align="right">每节标的数量：</td>
		 	  	  <td align="left">
		 	  		  <input type="text" maxlength="10" class="text" name="num" onchange="checkNumber(this,false,false,'每节标的数量')">
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
            <input type="hidden" name="partitionID" value="${param.partitionID}">
            <input type="hidden" name="ids" value="${param.ids}">
			<input type="button" onclick="return frmChk();" class="btn" value="确认">&nbsp;&nbsp;
			<input name="back" type="button" onclick="window.close()" class="btn" value="取消">&nbsp;&nbsp;
            </div>
            </td>
          </tr>
     </table>
	</form>
<script language="javascript">
  function frmChk(){
      if(Trim(frm.startCode.value) == "")
	  {
		  alert("起始标的号不能为空！");
		  frm.startCode.focus();
		  return false;
	  }else if(Trim(frm.endCode.value) == "")
	  {
		  alert("结束标的号不能为空！");
		  frm.endCode.focus();
		  return false;
	  }
  	  else if(Trim(frm.startSection.value) == "")
	  {
		  alert("起始交易节编号不能为空！");
		  frm.startSection.focus();
		  return false;
	  }
	  else if(parseInt(frm.startSection.value)<=0)
	  {
		  alert("起始交易节编号必须大于0！");
		  frm.startSection.focus();
		  return false;
	  }
	  else if(Trim(frm.num.value) == "")
	  {
		  alert("每节标的数不能为空！");
		  frm.num.focus();
		  return false;
	  }else if(parseInt(frm.num.value)<=0)
	  {
		  alert("每节标的数必须大于0！");
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
  
  //控制当点击不属于任何交易节时
  function clickNoSection(){
    if(frm.noSection.checked==true){
      frm.section.value="-1";
      frm.section.readOnly=true;
    }else{
      frm.section.value="";
      frm.section.readOnly=false;
    }
  }
</script>
</body>
</html>
<%@ include file="/vendue/manage/public/footInc.jsp" %>