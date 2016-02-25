<%@ page contentType="text/html;charset=GBK" %>
<base target="post">
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>

<!--取得参数-->
  <head>
	<title>修改系统控制属性</title>
</head>

<c:if test="${not empty param.add}">
<%
try{
    java.util.Date curdate = new java.util.Date();
    java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
%>
<db:update table="v_sysproperty"
    durativetime="${param.durativeTime}"
    spacetime="${param.spaceTime}"
    countdownstart="${param.countdownStart}"
    countdowntime="${param.countdownTime}"
    optmode="${param.optMode}"
    where="tradepartition=${param.tradePartition}"
/>

<%
	  //刷新内存
      String partitionID=request.getParameter("tradePartition");
      long durativeTime=Long.parseLong(request.getParameter("durativeTime"));//默认持续时间
      long spacetime=Long.parseLong(request.getParameter("spaceTime"));//节间信息时间
      int countdownStart=Integer.parseInt(request.getParameter("countdownStart"));//倒计时开始时间
      int countdownTime=Integer.parseInt(request.getParameter("countdownTime"));//倒计时顺延时间
            
	  	KernelEngineRMI dao = (KernelEngineRMI) Naming.lookup("rmi://" + host+":"+port+ "/"+REMOTECLASS+""+partitionID);
      dao.updateSysProperty(durativeTime,spacetime,countdownStart,countdownTime);

%>
     <SCRIPT LANGUAGE='JavaScript'>
        alert('系统控制属性修改成功!');
        window.returnValue="1";
        window.close();
     </script>
<%
}
catch(Exception e)
{
  e.printStackTrace();
  errOpt();
}		
%>
</c:if>
<body>
<form name=frm id=frm method="post" action="" targetType="hidden" callback='closeDialog(1);'>
		<fieldset width="100%">
		<legend>修改系统控制属性信息</legend>
		<BR>
		<span>
		<db:select var="row" table="v_sysproperty" columns="<%=COLS_SYSPROPERTY%>" where="tradepartition='${param.partitionID}'">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr height="35">
            	<td align="right"> 交易板块：</td>
                <td align="left">
                	<select disabled>
                		 <db:select var="rowColumn" columns="<%=COLS_SYSPARTITION%>" table="v_syspartition" where="partitionid=${param.partitionID}">
                		 <option value="${rowColumn.partitionid}">${rowColumn.description}</option>
                		 </db:select>
                        </select>	
                   <input type="hidden" name="tradePartition" value="${param.partitionID}">
                </td>
        </tr>
			   <tr height="35">
            	<td align="right"> 交易节默认持续时间：</td>
                <td align="left">
                	<input name="durativeTime" type="text" maxlength="8" class="text" style="width: 180px;" value="${row.durativetime}" onchange="checkNumber(this,false,false,'交易节默认持续时间')">秒
                </td>
         </tr>
        <tr height="35">
                <td align="right"> 节间休息时间：</td>
                <td align="left">
                	<input name="spaceTime" type="text" maxlength="6" class="text" style="width: 180px;" value="${row.spacetime}" onchange="checkNumber(this,false,false,'节间休息时间')">秒
                </td>
        </tr>
        <tr height="35">       
                <td align="right"> 倒计时开始时间：</td>
                <td align="left">                	
					<input name="countdownStart" type="text" class="text"  style="width: 180px;" value="${row.countdownstart}" onchange="changeSar(this.value)">秒
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 倒计时顺延时间：</td>
                <td align="left">
                	<input name="countdownTime"  readonly="true" type="text" class="text" style="width: 180px;" value="${row.countdowntime}" onchange="checkNumber(this,false,false,'倒计时顺延时间')">秒
                </td>
              </tr>
               <input type="hidden" name="optMode" value="1">
        	</table>
			<BR>
        </span>
		</db:select>
		</fieldset>
		<br>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
              <input type="hidden" name="add">
			  <input type="button" onclick="return frmChk(${param.partitionID})" class="btn" value="保存">&nbsp;&nbsp;
			  <input name="back" type="button" onclick="window.close()" class="btn" value="取消">&nbsp;&nbsp;
            </div></td>
          </tr>
     </table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk(partitionID)
{ 
	if(Trim(frm.tradePartition.value) == "")
	{
		alert("板块号不能为空！");
		frm.tradePartition.focus();
		return false;
	}else if(Trim(frm.durativeTime.value) == "")
	{
		alert("交易节默认持续时间不能为空！");
		frm.durativeTime.focus();
		return false;
	}else if(Trim(frm.spaceTime.value) == "")
	{
		alert("节间休息时间不能为空！");
		frm.spaceTime.focus();
		return false;
	}
	else if(Trim(frm.countdownStart.value) == "")
	{
		alert("倒计时开始时间不能为空！");
		frm.countdownStart.focus();
		return false;
	}
	else if(Trim(frm.countdownTime.value) == "")
	{
		alert("倒计时顺延时间不能为空！");
		frm.countdownTime.focus();
		return false;
	}else if(Trim(frm.optMode.value) == "")
	{
		alert("倒计时处理模式不能为空！");
		frm.optMode.focus();
		return false;
	}
	else if(isNaN(new Number(frm.countdownStart.value)))
	{
		alert("倒计时开始时间不是合法数值");
		frm.countdownStart.focus();
		return false;
	}
	else if(partitionID==3&&Trim(frm.durativeTime.value)!=Trim(frm.countdownStart.value))
	{
		alert("招标模式交易节持续时间和倒计时开始时间必须相等");
		frm.countdownStart.value="";
		frm.countdownStart.focus();
		return false;
	}
	else 
	{ 
	  if(userConfirm()){
	    frm.add.value="true";
		frm.submit();
	  }else{
	    return false;
	  }
	}
}
function changeSar(time)
{
	frm.countdownTime.value=time;
}
//-->
</SCRIPT>
<%@ include file="../../public/footInc.jsp" %>