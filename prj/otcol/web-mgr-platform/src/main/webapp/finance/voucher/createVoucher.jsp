<%@ page contentType="text/html;charset=GBK" %>
<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
	<title>创建凭证</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/voucher/voucher.js"></script>
	<script>
		function initData(){
			//初始化行的克隆，并增加一行，默认一借一贷。
			trClone = document.all.tableList.children[1].children[0].cloneNode(true);
			addEntry();
			formNew.summaryNo.focus();
		}
		function newOne(){
			window.location = '<%=basePath%>/voucher/createVoucher.jsp';
		}
		function kd(nxtobjid)
		{
			var nxtobjid=nxtobjid;
			var nxtobj=document.getElementById(nxtobjid);
			var keycode=event.keyCode;
			//alert(event.keyCode);
			if (keycode==13)
			{nxtobj.focus();
			}
		}
		
	</script> 
</head>
<body onload="initData();">
        <form id="formNew" action="<%=basePath%>/voucherController.spr?funcflg=voucherAdd" method="POST" targetType="hidden" callback="newOne();">
		<fieldset width="100%">
		<legend>凭证基本信息</legend>
		<span id="voucherSpan">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
            	<td align="right"> 凭证日期 ：</td>
                <td align="left">
                	<input name="voucherDate1" type="text" class="text" style="width: 100px;" value="<%=nowDate%>" reqfv="required;凭证日期" readonly onkeydown="if(event.keyCode==13)event.keyCode=9">
                </td>
                <td align="right"> <%=SUMMARYNO%> ：</td>
                <td align="left">
                	<input id="summaryNo" name="summaryNo" mapId="summaryNo" type="text" class="text" style="width: 100px;" onchange="changeSummaryNo(this.value)" onkeydown="kd('contractNo')" onkeypress="onlyNumberAndCharInput()" maxlength="32">
                </td>
                <td align="right"> 凭证摘要 ：</td>
                <td align="left">
                	<input id="summary" name="summary" mapId="summary" type="text" class="text" style="width: 150px;" reqfv="required;凭证摘要" onkeydown="if(event.keyCode==13)event.keyCode=9" onkeypress="onlyNumberAndCharInput()" maxlength="32">
                	<button class="smlbtn" onclick="selectSummary(voucherSpan)" onkeydown="if(event.keyCode==13)event.keyCode=9">选择</button>
                </td>
              </tr>
              <tr height="35">
                <!-- <td align="right"> 凭证类型 ：</td>
                <td align="left">
                	<input id="voucherType" name="voucherType" mapId="voucherType" type="text" class="text" style="width: 100px;" readonly onkeydown="if(event.keyCode==13)event.keyCode=9">
                </td> -->
                <input type="hidden" id="voucherType" name="voucherType" mapId="voucherType">
                <td align="right"> <%=CONTRACTNO%> ：</td>
                <td align="left">
                	<input id="contractNo" name="contractNo" type="text" class="text" style="width: 100px;" onkeydown="if(event.keyCode==13)event.keyCode=9" onkeypress="onlyNumberAndCharInput()" maxlength="16">
                </td>
                <td align="right"> 附加说明 ：</td>
                <td align="left">
                	<input name="note" type="text" class="text" style="width: 200px;" onkeydown="kd('entrySummary')" onkeypress="onlyNumberAndCharInput()" maxlength="38">
                </td>
              </tr>
        	</table>
        </span>  
		</fieldset>
		<br>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="350">
	  		<tHead>
	  			<tr height="25">
	  				<td class="panel_tHead_LB">&nbsp;</td>
					<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'entryId_check')"></td>
					<td class="panel_tHead_MB">分录摘要</td>
					<td class="panel_tHead_MB"><%=BITCODE%></td>
					<td class="panel_tHead_MB"><%=BITCODENAME%></td>
					<td class="panel_tHead_MB">借方金额</td>
					<td class="panel_tHead_MB">贷方金额</td>
					<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
		  		<tr onclick="selectTr();">
		  			<td class="panel_tBody_LB">&nbsp;</td>
		  			<td class="underLine"><input name="entryId_check" type="checkbox" onkeydown="if(event.keyCode==13)event.keyCode=9"><input name="entryId" type="hidden"></td>
		  			<td class="underLine"><input name="entrySummary" type="text" class="text" style="width: 200px;" onkeydown="if(event.keyCode==13)event.keyCode=9"></td>
		  			<td class="underLine"><input name="accountCode" type="text" class="text" style="width: 100px;" reqfv="required;科目代码" onchange="changeAccountCode(this.value);" onkeydown="if(event.keyCode==13)event.keyCode=9"></td>
		  			<td class="underLine"><input name="accountName" type="text" class="readonly" style="width: 200px;" readonly reqfv="required;科目名称" onkeydown="if(event.keyCode==13)event.keyCode=9"></td>
		  			<td class="underLine"><input name="debitAmount" type="text" class="text" style="width: 100px;" reqfv = "req_num;1;0;借方金额" value="0" onchange="computeSum();" onkeydown="if(event.keyCode==13)event.keyCode=9"></td>
		  			<td class="underLine"><input name="creditAmount" type="text" class="text" style="width: 100px;" reqfv = "req_num;1;0;贷方金额" value="0" onchange="computeSum();" onkeydown="if(event.keyCode==13)event.keyCode=9"></td>
		  			<td class="panel_tBody_RB">&nbsp;</td>
		  		</tr>
		  	</tBody>
		  	<tFoot>
				<tr height="100%">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="6">&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="4" align="right" class="pager">合计：</td>
					<td align="right" class="pager"><span id="debitSum">0.00</span></td>
					<td align="right" class="pager"><span id="creditSum">0.00</span></td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tFoot_LB">&nbsp;</td>
					<td class="panel_tFoot_MB" colspan="6"></td>
					<td class="panel_tFoot_RB">&nbsp;</td>
				</tr>
			</tFoot>
		</table>
     </form>
     <table border="0" cellspacing="0" cellpadding="0" width="90%">
          <tr height="35">
          	<td width="40%"><div align="right">
              <button class="smlbtn" type="button" onclick="addEntry();" onkeydown="if(event.keyCode==13)event.keyCode=9">增加分录</button>&nbsp;&nbsp;
  			  <button class="smlbtn" type="button" onclick="delEntry();" onkeydown="if(event.keyCode==13)event.keyCode=9">删除分录</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <button id="submitBtn" class="smlbtn" type="button" onclick="this.disabled=true;if(!voucherSubmit()) this.disabled=false;" onkeydown="kd('entrySummary')">提交</button>
            </div></td>
          </tr>
     </table>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>