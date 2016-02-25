<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>


<!--取得参数-->
<html>
  <head>
	<title></title>
</head>
<c:if test="${not empty param.add}">
<%
java.util.Date curdate = new java.util.Date();
java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
%>
  	<db:update table="v_curcommodity"
    tradepartition="${param.tradePartition}"
    lpflag="${param.lpFlag}"
    modifytime="<%=sqlDate%>"
    where="code='${param.code}'"
    />
<SCRIPT LANGUAGE="JavaScript">
<!--
alert("当前商品信息修改成功！");
//window.close();
//-->
</SCRIPT>
</c:if>
<body>
<form name=frm id=frm method="post" action="" targetType="hidden" callback='closeRefreshDialog();'>
		<fieldset width="100%">
		<legend>修改当前交易商品信息</legend>
		<BR>
		<span>
		<db:select var="row" table="v_curcommodity" columns="<%=COLS_CURCOMMODITY%>" where="code='${param.code}'">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr height="35">
                <td align="right"> 标的号 ：</td>
                <td align="left">
                	<input name="code" type="text" class="text" style="width: 180px;" value="${row.code}" readonly>
                </td>
        </tr>
			  <tr height="35">
            	<td align="right"> 交易板块 ：</td>
                <td align="left">
                	<select name="tradePartition">
                		 <option value="1">广州</option>
                		 <option value="2">上海</option>
                  </select>	
                </td>
        </tr>
        <tr height="35">       
                <td align="right"> 是否流拍 ：</td>
                <td align="left">
                	<select name="lpFlag">
                		 <option value="0">否</option>
                		 <option value="1">是</option>
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
			  <input type="submit" onclick="return frmChk()" class="btn" value="保存">&nbsp;&nbsp;
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
{ if(Trim(frm.tradePartition.value) == "")
	{
		alert("交易模板不能为空！");
		frm.tradePartition.focus();
		return false;
	}
	else 
	{ frm.add.value="true";
		return true;
	}
}

//-->
<!--
function refreshParent() {
	window.opener.location.href = window.opener.location.href;
	if (window.opener.progressWindow) window.opener.progressWindow.close();
	window.close();
	}
//-->
</SCRIPT>
<%@ include file="../../public/footInc.jsp" %>