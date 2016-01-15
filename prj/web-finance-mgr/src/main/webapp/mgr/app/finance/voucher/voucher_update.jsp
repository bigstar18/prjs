<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>凭证修改</title>

      <link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
      <link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
      <script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
      <script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
      <script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>

      <script type="text/javascript" src="${mgrPath}/app/finance/voucher/voucher.js"></script>
      <script type="text/javascript">

        jQuery(document).ready(function() {
	       jQuery("#frm").validationEngine('attach');
	       
	       //修改按钮注册点击事件
	       $("#update").click(function(){
		   //验证信息
		   if(jQuery("#frm").validationEngine('validate')){

			  if(voucherSubmit()){
			   
			    var vaild = affirm("您确定要操作吗？");
			    if(vaild){
				  //添加信息URL
				  var updateUrl = $(this).attr("action");
				  //全 URL 路径
				  var url = "${basePath}"+updateUrl;
				  $("#frm").attr("action",url);
				  $("#frm").submit();
			   }
		     }
		   }
	     });
       });

	//根据摘要代码查询摘要名称
	function changeSummaryNo(val){
		var url = "../../ajaxcheck/finance/getSummaryByNo.action?summaryNo="+val+"&t="+Math.random();
		$.getJSON(url,null,function call(result){
			if(result=="-1"){
				alert("不存在的摘要代码！");
				frm.summaryNo.focus();
				document.getElementById("summary").value = "";
			}else{
				document.getElementById("summary").value=result;
				
			}
		});
	}

	//根据科目代码查询科目名称
	function changeAccountCode(val){
		var url = "../../ajaxcheck/finance/getAccountName.action?accountCode="+val+"&t="+Math.random();
		$.getJSON(url,null,function call(result){
			if(result=="-1"){
				alert("交易商不存在或者有下级的科目代码！");

				// 不存在，清空科目名称		
				var tabBody = document.all.tableList.children[1];
				for(i=tabBody.children.length-1; i>=0; i--){
					if(tabBody.children[i].children[2].children[0].value==val){
						tabBody.children[i].children[3].children[0].value = "";
					}			
				}
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
		voucherSubmit();
	}

	function goback() {
		var url = "${basePath}/finance/voucher/voucherList.action";
		frm.action=url;
		frm.submit();
	}

	function initData(){
		var codes = document.all.namedItem("accountCode");
		for(var i=0; i < codes.length; i++){
			changeAccountCode(codes[i].value);
		}
		//显示借贷金额合计
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

	function disableBtn(){
		frm.add.disabled = true;
	}
	function replyBtn(){
		frm.add.disabled = false;
	}

	// 选择摘要
	function selectSummary(){
		var url = "${basePath}/finance/voucher/viewSummary.action?sortColumns=order+by+summaryNo";

		// 弹出添加页面
		//var resutlt = showDialog(url, "", 650, 550);
		var result=window.showModalDialog(url, "", "dialogWidth=" + 650 + "px; dialogHeight=" + 550 + "px; status=yes;scroll=yes;help=no;");

		if(result){
		   document.getElementById("summaryNo").value = result;
		   document.getElementById("summaryNo").onchange();
		}
		
	}
	
</script>
  </head>
  <body onload="initData();">
	<form id="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
		<input type="hidden" name="entity.voucherNo" value="${entity.voucherNo }" />
		<div class="div_cx">
			<table border="0" width="100%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								温馨提示 :凭证${entity.voucherNo}修改
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
												修改凭证
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">凭证日期：</td>
													<td><fmt:formatDate value="${entity.inputTime}" pattern="yyyy-MM-dd"/></td>
													<td align="right">凭证摘要号：</td>
													<td>
														<input type="text" id="summaryNo" name="entity.summaryNo" value="${entity.summaryNo }" onchange="changeSummaryNo(this.value)" class="validate[required,maxSize[<fmt:message key='Voucher.summaryNo' bundle='${PropsFieldLength}' />]] input_text" onkeydown="if(event.keyCode==13)event.keyCode=9"/>
													</td>
													<td align="right">凭证摘要：</td>
													<td>
														<input type="text" id="summary" name="entity.summary" value="${entity.summary }" class="validate[required,maxSize[<fmt:message key='Voucher.summary' bundle='${PropsFieldLength}' />]] input_text" onkeydown="if(event.keyCode==13)event.keyCode=9"/>
														&nbsp;&nbsp;
														<button class="btn_sec" onClick="selectSummary()">选择</button>
													</td>
												</tr>
												<tr>
													<td align="right">合同号：</td>
													<td>
														<input type="text" id="contractNo" name="entity.contractNo" value="${entity.contractNo }" class="validate[maxSize[<fmt:message key='Customer.fullName' bundle='${PropsFieldLength}' />]] input_text" onkeydown="if(event.keyCode==13)event.keyCode=9" onkeypress="onlyNumberAndCharInput()"/>
													</td>
													<td align="right">附加说明：</td>
													<td colspan="3">
														<input type="text" id="note" name="entity.note" value="${entity.note }" class="input_text" onkeydown="kd('entrySummary')" onkeypress="onlyNumberAndCharInput()"/>
													</td>
												</tr>
											</table>
										</div>
										<br/>
										<div>
											<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" align="center" height="240" class="table2_style">
												<tHead>
										  			<tr height="25">
														<td class="panel_tHead_MB" align="center" style="color: black">分录摘要</td>
														<td class="panel_tHead_MB" align="center" style="color: black">科目代码</td>
														<td class="panel_tHead_MB" align="center" style="color: black">科目名称</td>
														<td class="panel_tHead_MB" align="center" style="color: black">借方金额</td>
														<td class="panel_tHead_MB" align="center" style="color: black">贷方金额</td>
													</tr>
												</tHead>
												<tBody>
												<c:set var="i" value="0" />
												<c:forEach items="${entity.voucherEntries}" var="entry">
											  		<tr>
											  			<input type="hidden" id="entryId" name="voucherEntries[${i }].entryId" value="${entry.entryId }"/>
											  			<td align="right" class="underLine">
											  				<input id="entrySummary" name="voucherEntries[${i }].entrySummary" value="${entry.entrySummary }" type="text" class="text" >
											  			</td>
											  			<td align="right" class="underLine">
											  				<input id="accountCode" name="voucherEntries[${i }].accountCode" value="${entry.accountCode }" type="text" style="width: 200px;" reqfv="required;科目代码" onchange="changeAccountCode(this.value);" onkeydown="if(event.keyCode==13)event.keyCode=9" onkeypress="onlyNumberAndCharInput()" class="validate[required,maxSize[<fmt:message key='Account.code' bundle='${PropsFieldLength}' />]] input_text">
											  			</td>
											  			<td align="right" class="underLine">
											  				<input id="accountName" name="accountName" class="validate[required] input_text" style="width: 200px;" readonly="readonly" reqfv="required;科目名称" onkeydown="if(event.keyCode==13)event.keyCode=9">
											  			</td>
											  			<td align="right" class="underLine">
											  				<input id="debitAmount" name="voucherEntries[${i }].debitAmount" value="${entry.debitAmount }" type="text" style="width: 120px;" reqfv="req_num;1;0;借方金额" value="0" onchange="computeSum();" onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[13],custom[onlyDoubleSp]] input_text" onkeydown="if(event.keyCode==13)event.keyCode=9">
											  			</td>
											  			<td align="right" class="underLine">
											  				<input id="creditAmount" name="voucherEntries[${i }].creditAmount" value="${entry.creditAmount }" type="text" style="width: 120px;" reqfv="req_num;1;0;贷方金额" value="0" onchange="computeSum();" onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[13],custom[onlyDoubleSp]] input_text" onkeydown="if(event.keyCode==13)event.keyCode=9">
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
														<td colspan="3" align="right" class="pager">合计：</td>
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
						<rightButton:rightButton name="修改凭证" onclick="" className="anniu_btn" action="/finance/voucher/updateVoucher.action" id="update"></rightButton:rightButton>
						&nbsp;&nbsp;
						<button class="anniu_btn" onClick="goback();">返回</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
	</body>
</html>