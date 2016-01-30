<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<link rel="stylesheet"
		href="${skinPath }/css/validationengine/validationEngine.jquery.css"
		type="text/css" />
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js"
		type="text/javascript">
</script>
	<link rel="stylesheet"
		href="${skinPath }/css/autocomplete/jquery.autocomplete.css"
		type="text/css" />
	<script type='text/javascript' src='${publicPath }/js/autocomplete/jquery.autocomplete.js'></script>
	<script
		src="${basePath }/mgr/app/bill/js/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
	
</script>
	<script
		src="${publicPath }/js/validationengine/jquery.validationEngine.js"
		type="text/javascript" charset="GBK"></script>
		
	<script>
	jQuery(document).ready(function() {
		
		$("#frm").validationEngine('attach');
	
		
		$("#success").click(function(check) {
			
			
			if ($("#frm").validationEngine('validate')) {
				var valid=affirm("您确定进行拆单吗？")
				if(valid){
					$("#frm").validationEngine('detach');
					var a=document.getElementById('success').action;
					var url = "${basePath}"+a;
					$('#frm').attr('action', url);
				    $("#frm").submit();
				}
		}})
		
		$("#fail").click(function(check) {
				var valid=affirm("一旦进行拆单失败处理，此次拆单将不能被使用，您确定要进行操作吗？");
				if(valid){
					var a=document.getElementById('fail').action;
					var url="${basePath}"+a;
					$('#frm').attr('action', url);
					frm.submit();
				}
	}	);
	});
</script><head>
		<title>仓单处理列表</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								温馨提示 :拆仓单处理
								<div>&nbsp;&nbsp;&nbsp;&nbsp;
								<!--<span class="required">*</span>调用拆仓单成功的方法时：仓库原始凭证号不能为空！-->
								</div>
							</div>
						</div>
						<div class="div_cx">
							<form id="frm" method="post" targetType="hidden">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_width" style="width: 100%">
											<div class="div2_top" style="margin-left: 0px">
												<table align="center" class="div2_top" style="width: 100%">
													<tr align="center">
														<td width="">
															拆仓单编号
														</td>
														<td width="">
															仓单号
														</td>
														<td width="">
															仓库原始凭证号
														</td>
														<td colspan="2" width="">
															数量
														</td>
														<td colspan="2" width="">
															申请时间
														</td>
														<%--<td colspan="2" width="">
															处理时间
														</td>
														--%><td width="">
															状态
														</td>
														<td width="">
															新仓单仓库原始凭证号
														</td>
													</tr>
													<s:iterator id="dismantle" value="#attr.pageInfo.result" >
														<tr align="center">
															<td width="8%">
																${dismantle.dismantleId }
															</td>
															<td width="6%">
																${dismantle.stock.stockId }
																<input type="hidden" id="stock.stockId" name="stock.stockId"
																	class="input_text" value="${dismantle.stock.stockId}" />
															</td>
															<td width="10%">
																
																<div style="width:175px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;wzy:expression(void(this.title=this.innerText))">${dismantle.stock.realStockCode }</div>
															</td>
															<td colspan="2" width="20%" >
																<div style="width:175px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;wzy:expression(void(this.title=this.innerText))"><fmt:formatNumber value="${dismantle.amount }"
														pattern="#,##0.00"/>
																(${dismantle.stock.unit })</div>
															</td>
															<td colspan="2" width="16%">
																<fmt:formatDate value="${dismantle.applyTime}"
																	pattern="yyyy-MM-dd HH:mm:ss" />
															</td>
															<%--<td colspan="2" width="16%">
																<fmt:formatDate value="${dismantle.processTime }"
																	pattern="yyyy-MM-dd HH:mm:ss" />
															</td>
															--%><td width="6%">
																<c:if test="${dismantle.status=='0' }">申请中</c:if>
															</td>
															<td colspan="2" width="17%">
																<input type="hidden" id="dismantleId" name="dismantleId"
																	class="input_text" value="${dismantle.dismantleId}" />
																<input type="text" id="realStockCode${dismantle.dismantleId}" onkeydown="if(event.keyCode==32) return false;"
																	name="realStockCode${dismantle.dismantleId}" class="validate[required,custom[onlyLetterNumber],maxSize[30]] input_text_pwdmin"
																	 data-prompt-position="centerLeft:0"/>
															</td>
														</tr>
															
													</s:iterator>
												</table>
											</div>
										</td>
									</tr>
									<tr height="26">
										<td>
											&nbsp;
										</td>
									</tr>
								</table>
								<div>
									&nbsp;
								</div>
								<table align="right">
									<tr align="right">
										<td align="right">
											<div class="div_gn">
												<rightButton:rightButton className="btn_cz" id="success" name="拆仓单成功"
													action="/stockoperation/apart/updateDisposeSuccess.action" onclick="">
													
												</rightButton:rightButton>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<rightButton:rightButton name="拆仓单失败" className="btn_cz" id="fail" 
													action="/stockoperation/apart/updateDisposeFail.action"  onclick="">
												
												</rightButton:rightButton>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<button class="btn_cz" onclick=window.close();>
													关闭
												</button>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
<script type="text/javascript">
<!--
	function disposeSuccess(){
			
	}
	function disposeFail(){
		
	}
//-->
</script>
<%@include file="/mgr/public/jsp/footinc.jsp"%>