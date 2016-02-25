<%@ page contentType="text/html;charset=GBK" %>
<html>
  <head> 
    <%@ include file="../public/headInc.jsp" %>
	<title>编辑摘要</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
		function doSubmit()
		{
			if(!checkValue("formNew"))
				return;
			if(formNew.summaryNo.value.length!=3)
			{
			  alert("摘要号必须是三位!");
			  return false; 
			}
			if(confirm("您确定提交吗？")) {
			   formNew.submit();
			}
		}
	</script> 
</head>
<body>
        <form id="formNew" action="<%=basePath%>/voucherController.spr?funcflg=summaryMod" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>摘要基本信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=SUMMARYNO%> ：</td>
                <td align="left">
                	<input class="normal" name="summaryNo" value="<c:out value='${summary.summaryNo}'/>" style="width: 100px;" readonly>
                </td>
                <td align="right"> <!-- 凭证类型 ： -->&nbsp;</td>
                <td align="left">&nbsp;
                	<!-- <select id="voucherType" name="voucherType" class="normal" style="width: 100px">
						<option value="C">收</option>
          				<option value="P">付</option>
          				<option value="T">转</option>
					</select>
                	 -->
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 摘要名称 ：</td>
                <td align="left" colspan="3">
                	<input name="summary" value="<c:out value='${summary.summary}'/>" type="text" class="text" style="width: 310px;">
                </td>
              </tr>
               </table>
		       </fieldset>
		       <fieldset width="100%">
		       <legend>附加资金信息</legend>
			   <table border="0" cellspacing="0" cellpadding="0" width="100%">
               <tr height="35">
                <td align="right"> 归入总账项目 ：</td>
                <td align="left">
                	<select name="ledgerItem"  class="normal" style="width: 100px">
					<OPTION value=""></OPTION>
					<c:forEach items="${fieldList}" var="result">
			        <option value="${result.code}">${result.name}</option>
			        </c:forEach>
				    </select>
				    <script>
						formNew.ledgerItem.value = "<c:out value='${summary.ledgerItem}'/>"
					</script>
                </td>
                <td align="right"> 交易商资金借贷方向 ：</td>
                <td align="left">
                	<select id="fundDCFlag" name="fundDCFlag" class="normal" style="width: 100px">
						<option value="N">不涉及</option>
          				<option value="C">记贷方</option>
          				<option value="D">记借方</option>
					</select>
					<script>
						formNew.fundDCFlag.value = "<c:out value='${summary.fundDCFlag}'/>"
					</script>
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 附加帐 ：</td>
                <td align="left">
                	<select name="appendAccount"  class="normal" style="width: 100px">
					<option value="N">无附加</option>
          			<option value="T">增值税</option>
          			<option value="W">担保金</option>
				    </select>
				    <script>
						formNew.appendAccount.value = "<c:out value='${summary.appendAccount}'/>"
					</script>
                </td>
                <td align="right"> &nbsp;</td>
                <td align="left">&nbsp;</td>
              </tr>
	          </table>
			</fieldset>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr height="35">
	         <td colspan="4"><div align="center">
	           <button class="smlbtn" type="button" onclick="doSubmit();">提交</button>&nbsp;
			  <button class="smlbtn" type="button" onclick="window.close()">关闭窗口</button>
	         </div></td>
	         </tr>
			</table>
	        </form>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>