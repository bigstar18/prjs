<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
	String nowDate = Tools.fmtDate(new Date());
%>
<html>
	<head>
		<title>凭证添加</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		
		<script type="text/javascript" src="${mgrPath}/app/finance/voucher/voucher.js"></script>
		<script type="text/javascript">

		  jQuery(document).ready(function() {
			jQuery("#frm").validationEngine('attach');
			// 添加按钮注册点击事件
			$("#add").click(function(){
				// 验证信息
				if(jQuery("#frm").validationEngine('validate')){

				  if(voucherSubmit()){
					 
					var vaild = affirm("您确定要操作吗？");
					if(vaild){
						// 添加信息URL
						var addUrl = $(this).attr("action");
						// 全 URL 路径
						var url = "${basePath}"+addUrl;
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
						document.getElementById("summary").value="";
					}else{
						document.getElementById("summary").value=result;
						
					}
				});
			}

			//根据摘要代码查询摘要名称
			function changeAccountCode(val){
				var url = "../../ajaxcheck/finance/getAccountName.action?accountCode="+val+"&t="+Math.random();
				$.getJSON(url,null,function call(result){
					if(result=="-1"){
						alert("交易商不存在或者有下级的科目代码！");	

						// 不存在，清空科目名称				
						var tabBody = document.all.tableList.children[1];
						for(i=tabBody.children.length-1; i>=0; i--){
							if(tabBody.children[i].children[1].children[0].value==val){
								tabBody.children[i].children[2].children[0].value="";
							}			
						}
					}else{
						
						var tabBody = document.all.tableList.children[1];
						for(i=tabBody.children.length-1; i>=0; i--){
							if(tabBody.children[i].children[1].children[0].value==val){
								tabBody.children[i].children[1].children[0].value=val;
								tabBody.children[i].children[2].children[0].value=result;
							}			
						}

						
					}
				});
			}

			function initData(){
				//初始化行的克隆，并增加一行，默认一借一贷。
				trClone = document.all.tableList.children[1].children[0].cloneNode(true);
				addEntry();
				frm.summaryNo.focus();
			}

			function kd(nxtobjid) {
				var nxtobjid=nxtobjid;
				var nxtobj=document.getElementById(nxtobjid);
				var keycode=event.keyCode;
				if (keycode==13) {
					nxtobj.focus();
				}
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

			// 返回凭证列表
			function goback() {
				var url = "${basePath}/finance/voucher/voucherList.action";
				frm.action=url;
				frm.submit();
			}

			
		</script>
	</head>
	<body onload="initData();">
		<form id="frm" method="post" action="${basePath}/finance/voucher/addVoucher.action">
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :凭证添加
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
												添加凭证
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<span id="voucherSpan">				
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">凭证日期：</td>
													<td>
													    <c:out value="<%=nowDate %>"/>
														
													</td>
													<td align="right"><span class="required">*</span>凭证摘要号：</td>
													<td>
														<input type="text" id="summaryNo" name="entity.summaryNo" onchange="changeSummaryNo(this.value)" class="validate[required,maxSize[<fmt:message key='Voucher.summaryNo' bundle='${PropsFieldLength}' />]] input_text" onkeydown="if(event.keyCode==13)event.keyCode=9"/>
													</td>
													<td align="right"><span class="required">*</span>凭证摘要：</td>
													<td>
														<input type="text" id="summary" name="entity.summary" class="validate[required,maxSize[<fmt:message key='Voucher.summary' bundle='${PropsFieldLength}' />]] input_text" onkeydown="if(event.keyCode==13)event.keyCode=9"/>
														&nbsp;&nbsp;
														<button class="btn_sec" onClick="selectSummary()">选择</button>
													</td>
												</tr>
												<tr>
													<td align="right">合同号：</td>
													<td>
														<input type="text" id="contractNo" name="entity.contractNo" class="validate[maxSize[16] input_text " onkeydown="if(event.keyCode==13)event.keyCode=9" onkeypress="onlyNumberAndCharInput()" />
													</td>
													<td align="right">附加说明：</td>
													<td colspan="3">
														<input type="text" id="note" name="entity.note" class="input_text" onkeydown="kd('entrySummary')" onkeypress="onlyNumberAndCharInput()"/>
													</td>
												</tr>
											</table>
											</span>
										</div>
										<br/>
										<div>
											<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="90%" align="center" height="240" class="table2_style">
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
											  		<tr>
											  			<td align="right" class="underLine">
											  				<input id="entrySummary" name="voucherEntries[0].entrySummary" type="text" class="input_text" >
											  			</td>
											  			<td align="right" class="underLine">
											  				<input id="accountCode" name="voucherEntries[0].accountCode" type="text" style="width: 200px;" reqfv="required;科目代码" onchange="changeAccountCode(this.value);" onkeydown="if(event.keyCode==13)event.keyCode=9" onkeypress="onlyNumberAndCharInput()" class="validate[required,maxSize[<fmt:message key='Account.code' bundle='${PropsFieldLength}' />]] input_text" >
											  			</td>
											  			<td align="right" class="underLine">
											  				<input id="accountName" name="accountName" class="validate[required] input_text" readonly="readonly" style="width: 200px;">
											  			</td>
											  			<td align="right" class="underLine">
											  				<input id="debitAmount" name="voucherEntries[0].debitAmount" type="text"  style="width: 120px;" reqfv="req_num;1;0;借方金额" value="0" onchange="computeSum();" onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[13],custom[onlyDoubleSp]] input_text"  onkeydown="if(event.keyCode==13)event.keyCode=9">
											  			</td>
											  			<td align="right" class="underLine">
											  				<input id="creditAmount" name="voucherEntries[0].creditAmount" type="text"  style="width: 120px;" reqfv="req_num;1;0;贷方金额" value="0" onchange="computeSum();" onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[13],custom[onlyDoubleSp]] input_text"  onkeydown="if(event.keyCode==13)event.keyCode=9">
											  			</td>
											  		</tr>
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
							<rightButton:rightButton name="添加" onclick=""  className="btn_sec" action="/finance/voucher/addVoucher.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="goback();">返回</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>