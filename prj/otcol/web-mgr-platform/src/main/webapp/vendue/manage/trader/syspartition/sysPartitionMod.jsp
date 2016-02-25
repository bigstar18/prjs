<%@ page contentType="text/html;charset=GBK" %>

<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>

<!--用户权限判断-->
<%/*
<fun:acl userid="loginID" subject="10"/>*/%>
<!--取得参数-->
  <head>
	<title></title>
</head>
<c:if test="${not empty param.add}">
<%
  java.util.Date curdate = new java.util.Date();
  java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
%>
   <db:update table="v_syspartition"
    engineclass="${param.engineClass}"
    quotationclass="${param.quotationClass}"
    submitactionclass="${param.submitActionClass}"
    validflag="${param.validFlag}"
    description="${param.description}"
    where="partitionid=${param.partitionID}"
    />
	 <SCRIPT LANGUAGE="JavaScript">
	 	<!--
	  alert("板块信息修改成功！");
	  //-->
   </script>
</c:if>
<body>
<form name=frm id=frm action="" targetType="hidden" callback='closeRefreshDialog();'>
		<fieldset width="100%">
		<legend>修改板块配置信息</legend>
		<BR>
		<span>
		<db:select var="row" table="v_syspartition" columns="<%=COLS_SYSPARTITION%>" where="partitionid='${param.id}'">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<td align="right"> 板块编号 ：</td>
                <td align="left">
                	<input name="partitionID" type="text" class="text" style="width: 180px;" value="${row.partitionid}" readonly>
                </td>
        </tr>
				<tr height="35">
                <td align="right"> 交易引擎对象java实现类的名称 ：</td>
                <td align="left">
                  <input name="engineClass" type="text" class="text" style="width: 180px;" value="${row.engineclass}">
                </td>
        </tr>
        <tr height="35">       
                <td align="right"> 行情对象java实现类的名称 ：</td>
                <td align="left">
                	<input name="quotationClass" type="text" class="text" style="width: 180px;" value="${row.quotationclass}">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 委托响应java实现类的名称 ：</td>
                <td align="left">
                	<input name="submitActionClass" type="text" class="text" style="width: 180px;" value="${row.submitactionclass}">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 配置数据 ：</td>
                <td align="left">
                	<select name="validFlag">
                		<option value="1" <c:if test="${row.validflag==1}"><c:out value="selected"/></c:if>>是</option>
                		<option value="0" <c:if test="${row.validflag==0}"><c:out value="selected"/></c:if>>否</option>
                	</select>
                </td>
               </tr>
               <tr height="35">
                <td align="right"> 板块的描述 ：</td>
                <td align="left">
                	<input name="description" type="text" class="text" style="width: 180px;" value="${row.description}">
                </td>
              </tr>
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
			  <input type="submit" onclick="return frmChk()" class="btn" value="确定">&nbsp;&nbsp;
			  <input name="back" type="button" onclick="window.close()" class="btn" value="取消">&nbsp;&nbsp;
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
		alert("板块号不能为空！");
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
	{ frm.add.value="true";
		return true;
	}
}

//-->
</SCRIPT>
<%@ include file="/vendue/manage/public/footInc.jsp" %>