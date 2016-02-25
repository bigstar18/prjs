<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>
<base target="_self"> 
<c:set var="flog" value="true"/>
<!-- 当前系统状态所有模块的系统状态都不为 闭市(status!=5) 时,设置 flog=true，可以进行修改，否则不能进行修改； -->
<db:select var="row" table="v_syscurstatus" columns="status">
	<c:if test="${row.status!=5}">
		<c:set var="flog" value="false"/>
	</c:if>
</db:select>
<c:if test="${not empty param.newoverdraft}">
<c:catch var="exceError">
<%
java.util.Date curdate = new java.util.Date();
%>
<c:set var="addoverdraft" value="${param.newoverdraft}"/>
<db:update table="${TRADERUSER}"
overdraft="${param.newoverdraft}"
feecut="${param.feecut}"
feecutfee="${param.feecutfee}"
where="usercode='${param.userCode}'"
/>
<!--写日志操作-->
<%
try{

  String oldDraft=delNull(request.getParameter("oldoverdraft"));
  String newDraft=delNull(request.getParameter("newoverdraft"));
  String feecut=delNull(request.getParameter("feecut"));
  String oldfeecut=delNull(request.getParameter("oldfeecut"));
  String feecutfee=delNull(request.getParameter("feecutfee"));
  String oldfeecutfee=delNull(request.getParameter("oldfeecutfee"));
  String userCode=delNull(request.getParameter("userCode"));
  String remark="交易商代码:"+userCode+",虚拟资金原:"+oldDraft+",修改后:"+newDraft+";费用折扣原:"+oldfeecut+
  ",修改后"+feecut;

%>
    <SCRIPT LANGUAGE="JavaScript">
        alert("交易用户信息修改成功！");			
				window.returnValue="1";
        window.close();
    </SCRIPT>
<%
}
catch(Exception e)
{
  	e.printStackTrace();
    errOpt();
}
finally{
}		
%>
</c:catch>
<c:if test="${not empty exceError}">
     <%
	       //异常处理
	       String exceError=pageContext.getAttribute("exceError").toString();
		   log(request,exceError);
		   hintError(out);
	 %>
</c:if>
</c:if>
<html>
  <head>
	<title>修改交易商费用折扣</title>
</head>
<body>
<form name=frm id=frm action="" method="post" callback="closeRefreshDialog();" targetType="hidden">
		<fieldset width="100%">
		<legend>修改交易商费用折扣</legend>
		<BR>
		<span>
		<db:select var="row" table="${TRADERUSER}" where="usercode='${param.userCode}'">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
            	<td align="right"> 交易商代码：</td>
                <td align="left">
                	${row.usercode}<input type="hidden" name="userCode" value="${param.userCode}">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 本次交易累计扣除保证金和手续费：</td>
                <td align="left">
                ${row.totalSecurity}
                </td>
              </tr>
             
             <tr height="35">
                <td align="right"> 保证金费用折扣：</td>
                <td align="left"><input type="text" maxlength="10" name="feecut" class="text" style="width: 133px;" value="${row.feecut}" onchange="checkNumber_l()">
                <input type="hidden" name="oldfeecut" value="${row.feecut}">
                </td>
              </tr>
			  <tr height="35">
                <td align="right"> 手续费费用折扣：</td>
                <td align="left"><input type="text" maxlength="15" name="feecutfee" class="text" style="width: 133px;" value="${row.feecutfee}" onchange="checkNumber_l()">
                <input type="hidden" name="oldfeecutfee" value="${row.feecutfee}">
                </td>
              </tr>
              
               <tr height="35">
				<td align="right">&nbsp;</td>
                <td align="left"><input name="newoverdraft" type="hidden" class="text" style="width: 133px;" value="${row.overdraft}" onchange="checkNumber(this,false,false,'虚拟资金(透支额度)')">
                    <!--原虚拟资金-->
                    <input type="hidden" name="oldoverdraft" value="${row.overdraft}">
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
			  <c:if test="${flog=='true'}"><input type="button" onclick="return frmChk();" class="btn" value="保存">&nbsp;&nbsp;</c:if>
			  <input name="back" type="button" onclick="window.close();" class="btn" value="返回">&nbsp;&nbsp;
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
	if(Trim(frm.newoverdraft.value) == "")
	{
		alert("新增虚拟资金不能为空！");
		frm.newoverdraft.focus();
		return false;
	}
	else if(Trim(frm.feecut.value) == "")
	{
		alert("费用折扣不能为空！");
		frm.feecut.focus();
		return false;
	}else if(parseFloat(Trim(frm.feecut.value))<=0)
	{
		alert("费用折扣必须大于0！");
		frm.feecut.focus();
		return false;
	}
	else
	{   
	  if(userConfirm())
	  {
	    frm.add.value="确定";
		frm.submit();
		//return true;
	  }else{
	    return false;
	  }
	}
}

function checkNumber_l()
{
	if(frm.feecut.value>1)
	{
		alert("费用折扣不能大于 1 ");
		frm.feecut.value="";
	}
	else if(frm.feecut.value<=0)
	{
		alert("费用折扣必需大于 0 ");
		frm.feecut.value="";
	}
	if(frm.feecutfee.value>1)
	{
		alert("费用折扣不能大于 1 ");
		frm.feecutfee.value="";
	}
	else if(frm.feecutfee.value<=0)
	{
		alert("费用折扣必需大于 0 ");
		frm.feecutfee.value="";
	}
}
//-->
</SCRIPT>
<%@ include file="../../public/footInc.jsp" %>