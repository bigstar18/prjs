<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>ƾ֤��Ϣ����</title>
	
	<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
	<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
    <script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
    <script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
    <script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>

    <script type="text/javascript" src="${mgrPath}/app/finance/voucher/voucher.js"></script>
    <script type="text/javascript">
    
	//����ժҪ�����ѯժҪ����
	function changeSummaryNo(val){
		var url = "../../ajaxcheck/finance/getSummaryByNo.action?summaryNo="+val+"&t="+Math.random();
		$.getJSON(url,null,function call(result){
			if(result=="-1"){
				alert("�����ڵ�ժҪ���룡");
			}else{
				document.getElementById("summary").value=result;
			}
		});
	}

	//���ݿ�Ŀ�����ѯ��Ŀ����
	function changeAccountCode(val){
		var url = "../../ajaxcheck/finance/getAccountByCode.action?accountCode="+val+"&t="+Math.random();
		$.getJSON(url,null,function call(result){
			if(result=="-1"){
				alert("�����ڵĿ�Ŀ���룡");
			}else{
				var tabBody = document.all.tableList.children[1];
				for(i=tabBody.children.length-1; i>=0; i--){
					if(tabBody.children[i].children[2].children[0].value==val){
						tabBody.children[i].children[2].children[0].value=val;
						tabBody.children[i].children[3].children[0].value=result;
					}			
				}
			}
		});
	}

	function updateVoucher() {
		var voucherNo = frm.voucherNo.value;
		var url = "${basePath}/finance/voucher/updateVoucher.action?entity.voucherNo="+voucherNo;
		frm.action=url;
		frm.submit();
	}

	function initData(){
		var codes = document.all.namedItem("accountCode");
		for(var i=0; i < codes.length; i++){
			changeAccountCode(codes[i].value);
		}
		//��ʾ������ϼ�
		computeSum();
	}

	function kd(nxtobjid) {
		var nxtobjid=nxtobjid;
		var nxtobj=document.getElementById(nxtobjid);
		var keycode=event.keyCode;
		if (keycode==13) {
			nxtobj.focus();
		}
	}

	// ����
	function goback() {
		var url = "${basePath}/finance/voucher/voucherAuditList.action";
		frm.action=url;
		frm.submit();
	}

	// ƾ֤���
	function auditVoucher(vIsPass){
		if( vIsPass == "true" )
			msg = "ȷ����ˡ�ͨ���������ͨ����ƾ֤�������޸ģ�";
		else
			msg = "ȷ����ˡ�ʧ�ܡ������ʧ�ܺ�ƾ֤����Ϊ�༭״̬��";
		if(confirm( msg )){
			frm.isPass.value = vIsPass;
			var url = "${basePath}/finance/voucher/auditVoucher1.action?isPass="+frm.isPass.value;
			$("#frm").attr("action",url);
			$("#frm").submit();
		}
	}
  </script>
  </head>
  <body onload="initData();">
	<form id="frm" method="post" action="${basePath}/finance/voucher/updateVoucher.action">
		<input type="hidden" name="voucherNo" value="${entity.voucherNo }" />
		<input name="isPass" value="" type="hidden">
		<div class="div_cx">
			<table border="0" width="100%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								��ܰ��ʾ :ƾ֤${entity.voucherNo}��Ϣ����
							</div>
						</div>
					</td>
				</tr>
				<tr>
						<td>
							<table border="0" width="100%" align="center">
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												ƾ֤��Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">ƾ֤���ڣ�</td>
													<td><fmt:formatDate value="${entity.inputTime}" pattern="yyyy-MM-dd"/></td>
													<td align="right">ƾ֤ժҪ�ţ�</td>
													<td>
													    ${entity.summaryNo }
														<%-- <input type="text" id="summaryNo" name="entity.summaryNo" value="${entity.summaryNo }" onchange="changeSummaryNo(this.value)" class="validate[required,maxSize[<fmt:message key='Voucher.summaryNo' bundle='${PropsFieldLength}' />]] input_text" onkeydown="if(event.keyCode==13)event.keyCode=9"/>--%>
													</td>
													<td align="right">ƾ֤ժҪ��</td>
													<td>
													    ${entity.summary }
														<%--<input type="text" id="summary" name="entity.summary" value="${entity.summary }" class="validate[required,maxSize[<fmt:message key='Voucher.summary' bundle='${PropsFieldLength}' />]] input_text" onkeydown="if(event.keyCode==13)event.keyCode=9"/>--%>
													</td>
												</tr>
												<tr>
													<td align="right">��ͬ�ţ�</td>
													<td>
													    ${entity.contractNo }
														<%--<input type="text" id="contractNo" name="entity.contractNo" value="${entity.contractNo }" class="validate[maxSize[<fmt:message key='Customer.fullName' bundle='${PropsFieldLength}' />]] input_text" onkeydown="if(event.keyCode==13)event.keyCode=9" onkeypress="onlyNumberAndCharInput()"/>--%>
													</td>
													<td align="right">����˵����</td>
													<td colspan="3">
													    ${entity.note }
														<%--<input type="text" id="note" name="entity.note" value="${entity.note }" class="validate[maxSize[<fmt:message key='Customer.address' bundle='${PropsFieldLength}' />]]" onkeydown="kd('entrySummary')" onkeypress="onlyNumberAndCharInput()"/>--%>
													</td>
												</tr>
											</table>
										</div>
										<br/>
										<div>
											<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" align="center" height="240" class="table2_style">
												<tHead>
										  			<tr height="25">
														<td class="panel_tHead_MB" align="center" style="color: black">��¼ժҪ</td>
														<td class="panel_tHead_MB" align="center" style="color: black">��Ŀ����</td>
														<td class="panel_tHead_MB" align="center" style="color: black">��Ŀ����</td>
														<td class="panel_tHead_MB" align="center" style="color: black">�跽���</td>
														<td class="panel_tHead_MB" align="center" style="color: black">�������</td>
													</tr>
												</tHead>
												<tBody>
												<c:set var="i" value="0" />
												<c:forEach items="${entity.voucherEntries}" var="entry">
											  		<tr>
											  			<input type="hidden" id="entryId" name="voucherEntries[${i }].entryId" value="${entry.entryId }"/>
											  			<td align="right" class="underLine">${entry.entrySummary }</td>
											  			<td align="right" class="underLine">
											  				<input id="accountCode" name="voucherEntries[${i }].accountCode" value="${entry.accountCode }"  readonly="readonly" type="text" class="text" style="width: 150px;" reqfv="required;��Ŀ����" onchange="changeAccountCode(this.value);" onkeydown="if(event.keyCode==13)event.keyCode=9">
											  			</td>
											  			<td align="right" class="underLine">
											  				<input id="accountName" name="accountName" type="text" class="readonly" style="width: 150px;" readonly="readonly" reqfv="required;��Ŀ����" onkeydown="if(event.keyCode==13)event.keyCode=9">
											  			</td>
											  			<td align="right" class="underLine">
											  				<input id="debitAmount" name="voucherEntries[${i }].debitAmount" value="${entry.debitAmount }" readonly="readonly" type="text" class="text" style="width: 110px;" reqfv="req_num;1;0;�跽���" value="0" onchange="computeSum();" onkeydown="if(event.keyCode==13)event.keyCode=9">
											  			</td>
											  			<td align="right" class="underLine">
											  				<input id="creditAmount" name="voucherEntries[${i }].creditAmount" value="${entry.creditAmount }" readonly="readonly" type="text" class="text" style="width: 110px;" reqfv="req_num;1;0;�������" value="0" onchange="computeSum();" onkeydown="if(event.keyCode==13)event.keyCode=9">
											  			</td>
											  		</tr>
											  	<c:set var="i" value="1" />
											  	</c:forEach>
											  	</tBody>
											  	<tFoot>
													<tr height="100%">
														<td colspan="5">&nbsp;</td>
													</tr>
													<tr height="22">
														<td colspan="3" align="right" class="pager">�ϼƣ�</td>
														<td align="right" class="pager"><span id="debitSum">0.00</span></td>
														<td align="right" class="pager"><span id="creditSum">0.00</span></td>
													</tr>
												</tFoot>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
			</table>
		</div>
		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
				<tr>
					<td align="right">
						<button class="anniu_btn" onClick="window.close();">�ر�</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
	</body>
</html>