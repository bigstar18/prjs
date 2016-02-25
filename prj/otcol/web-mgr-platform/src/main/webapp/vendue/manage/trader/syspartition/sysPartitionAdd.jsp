<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>

<!--用户权限判断-->
<!--判断用户所输入的板块编号是否有重复-->
<c:set var="addFlag" value="true"/>
<%/*
<fun:acl userid="loginID" subject="10"/>*/%>
<!--取得参数-->
<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>
<c:if test="${not empty param.add}">
<%
java.util.Date curdate = new java.util.Date();
%>
<db:select var="row" table="v_syspartition" columns="<%=COLS_SYSPARTITION%>" where="partitionid=${param.partitionID}">
	<c:set var="addFlag" value="false"/>
</db:select>
<c:choose>
<c:when test="${addFlag=='true'}">
<db:insert table="v_syspartition"
  partitionid="${param.partitionID}"
  engineclass="${param.engineClass}"
  quotationclass="${param.quotationClass}"
  submitactionclass="${param.submitActionClass}"
  validflag="${param.validFlag}"
  description="${param.description}"
/>
<SCRIPT LANGUAGE="JavaScript">
<!--
alert("系统板块配置添加成功！");
gotoUrl("sysPartitionList.jsp");
//-->
</SCRIPT>
</c:when>
<c:otherwise>
<SCRIPT LANGUAGE="JavaScript">
<!--
alert("所输入的板块编号已经存在,请重新输入.");
gotoUrl("sysPartitionAdd.jsp");
//-->
</SCRIPT>
</c:otherwise>
</c:choose>
</c:if>
  <head>
	<title>添加板块</title>
</head>
<body>
<form name=frm id=frm>
		<fieldset width="100%">
		<legend>系统板块配置</legend>
		<BR>
		<span>
<table border="0" cellspacing="0" cellpadding="0" width="50%" align="center">
			  <tr height="35">
            	<td align="right"> 板块编号 ：</td>
                <td align="left">
                	<input name="partitionID" type="text" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'板块编号')">
                </td>
        </tr>
			  <tr height="35">
            	<td align="right"> 交易引擎对象java实现类的名称 ：</td>
                <td align="left">
                	<input name="engineClass" type="text" class="text" style="width: 180px;">
                </td>
        </tr>
        <tr height="35">
                <td align="right"> 行情对象java实现类的名称 ：</td>
                <td align="left">
                	<input name="quotationClass" type="text" class="text" style="width: 180px;">
                </td>
        </tr>
        <tr height="35">       
                <td align="right"> 委托响应java实现类的名称 ：</td>
                <td align="left">
                	<input name="submitActionClass" type="text" class="text" style="width: 180px;">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 是否加载此项配置数据 ：</td>
                <td align="left">
                	<select name="validFlag">
                		 <option value="1">是</option>
                		 <option value="0">否</option>
                  </select>	
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 板块描述 ：</td>
                <td align="left">
                	<input name="description" type="text" class="text" style="width: 180px;">
                </td>
               </tr>
        	</table>
			<BR>
        </span>  
		</fieldset>
		<br>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
			  <input name="add" type="submit" onclick="return frmChk()" class="btn" value="确定">&nbsp;&nbsp;
			  <input name="back" type="button" onclick="javascript:history.back();" class="btn" value="返回">&nbsp;&nbsp;
            </div></td>
          </tr>
     </table>
</form>
</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--

function frmChk()
{
	if(Trim(frm.partitionID.value) == "")
	{
		alert("板块编号不能为空！");
		frm.partitionID.focus();
		return false;
	}else if(Trim(frm.engineClass.value) == "")
	{
		alert("交易引擎对象的java实现类的名称不能为空！");
		frm.engineClass.focus();
		return false;
	}else if(Trim(frm.quotationClass.value) == "")
	{
		alert("行情对象的java实现类的名称不能为空！");
		frm.quotationClass.focus();
		return false;
	}else if(Trim(frm.submitActionClass.value) == "")
	{
		alert("委托响应的java实现类的名称不能为空！");
		frm.submitActionClass.focus();
		return false;
	}else if(Trim(frm.validFlag.value) == "")
	{
		alert("是否加载配置数据不能为空！");
		frm.validFlag.focus();
		return false;
	}
	else 
	{
		return true;
	}
}

//-->
</SCRIPT>