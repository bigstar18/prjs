<%@ page contentType="text/html;charset=GBK" %>
<base target="_self">
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>

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
    where="partitionid=${param.partitionID}"
    />
	 <SCRIPT LANGUAGE="JavaScript">
	  alert("板块信息修改成功！");
	  window.returnValue="1";
	  window.close();
     </script>
</c:if>
<body>
<form name=frm method="post" id=frm action="" targetType="hidden" callback='closeDialog(1);'>
		<fieldset width="100%">
		<legend>修改板块配置信息</legend>
		<BR>
		<span>
		<db:select var="row" table="v_syspartition" columns="<%=COLS_SYSPARTITION%>" where="partitionid='${param.id}'">
			<input name="engineClass" type="hidden" value="${row.engineclass}">
			<input name="quotationClass" type="hidden" value="${row.quotationclass}">
			<input name="submitActionClass" type="hidden" value="${row.submitactionclass}">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
            <input name="partitionID" type="hidden" value="${row.partitionid}">
        	<!-- 
        	<tr height="35">
              <td align="right"> 板块名称 ：</td>
              <td align="left">
              <input name="description" type="text" class="text" style="width: 180px;" value="${row.description}">
              </td>
              </tr>
			  <tr height="35">
              <td align="right"> 交易模式 ：</td>
              <td align="left">
              <select name="tradeMode">
              <option value="0" <c:if test="${row.trademode==0}"><c:out value="selected"/></c:if>>竞买</option>
              <option value="1" <c:if test="${row.trademode==1}"><c:out value="selected"/></c:if>>竞卖</option>
			  <option value="2" <c:if test="${row.trademode==2}"><c:out value="selected"/></c:if>>招标</option>
              </select>
              </td>
              </tr>
               -->
              <tr height="35">
              <td align="right" width="57%"> 加载配置数据：</td>
              <td align="left">
              <select name="validFlag">
              <option value="1" <c:if test="${row.validflag==1}"><c:out value="selected"/></c:if>>是</option>
              <option value="0" <c:if test="${row.validflag==0}"><c:out value="selected"/></c:if>>否</option>
              </select>
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
	}
	else if(Trim(frm.engineClass.value) == "")
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
<%@ include file="../../public/footInc.jsp" %>