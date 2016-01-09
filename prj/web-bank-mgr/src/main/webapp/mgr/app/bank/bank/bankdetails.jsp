<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
request.setAttribute("maxDouble",9999999999999.99);
%>
<html>
	<head>
		<title>银行修改</title>
		<meta http-equiv="Pragma" content="no-cache">
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${basePath }/mgr/app/bank/js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${basePath }/mgr/app/bank/js/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script>
			jQuery(document).ready(function() {
				if ("" != '${ReturnValue.info}' + "") {
					parent.document.frames('leftFrame').location.reload();
				}
				var firm = $("#frm");
				firm.validationEngine('attach');
				$("#update").click(function(check) {
					if (firm.validationEngine('validate')) {
						var vaild = affirm("您确定要操作吗？");
						if (vaild == true) {
							firm.validationEngine('detach');
							firm.attr("action",'${basePath}'+$("#update").attr("action"));
							firm.submit();
						}
					}
				});
			});
			function begintimes(){<%//起始时间%>
				var begintime0 = getvalue("begintime0");
				var begintime1 = getvalue("begintime1");
				var begintime2 = getvalue("begintime2");
				$("#begintime").val(begintime0+":"+begintime1+":"+begintime2);
			}
			function endTimes(){<%//结束时间%>
				var endTime0 = getvalue("endTime0");
				var endTime1 = getvalue("endTime1");
				var endTime2 = getvalue("endTime2");
				$("#endTime").val(endTime0+":"+endTime1+":"+endTime2);
			}
			function getvalue(id){
				var value = $("#"+id).val();
				var length = 2-value.length;
				for(var i=0;i<length;i++){
					value = "0"+value;
				}
				return value;
			}
		</script>
	</head>
	<body style="overflow-y: hidden">
		<iframe id="hiddenframe" name="hiddenframe" width=0 height=0 style="display:none" src="" application="yes"></iframe>
		<form id="frm" method="post" action="" target="hiddenframe">
			<input type="hidden" name="entity.bankID" value="${entity.bankID}"/><%//隐藏域银行编号 %>
			<div class="div_cx"  style="overflow:auto;height:480px;" >
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :银行修改
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
												基本信息
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														银行代码：
													</td>
													<td>
														${entity.bankID}
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														银行名称：
													</td>
													<td>
														<input type="text" id="bankName" name="entity.bankName" value="${entity.bankName}"
															class="validate[required,maxSize[<fmt:message key='Bank.bankName' bundle='${PropsFieldLength}' />]] input_text"/>
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														<span class="required">*</span>
														单笔最大出金金额：
													</td>
													<td width="30%">
														<input type="text" id="maxPersgltransMoney" name="entity.maxPersgltransMoney"
															class="validate[required,custom[doubleCus],min[0.01],max[${maxDouble}]] input_text"
															value="<c:if test="${entity.maxPersgltransMoney>0}"><fmt:formatNumber value="${entity.maxPersgltransMoney}" pattern="0.00" /></c:if>" />
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														<span class="required">*</span>
														每日最大出金金额：
													</td>
													<td width="30%">
														<input type="text" id="maxPertransMoney" name="entity.maxPertransMoney"
															class="validate[required,custom[doubleCus],min[0.01],max[${maxDouble}]] input_text"
															value="<c:if test="${entity.maxPertransMoney>0}"><fmt:formatNumber value="${entity.maxPertransMoney}" pattern="0.00" /></c:if>" />
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														<span class="required">*</span>
														每日最大出金次数：
													</td>
													<td width="30%">
														<input type="text" id="maxPertransCount" name="entity.maxPertransCount"
															class="validate[required,custom[integer],min[0],max[${maxDouble}]] input_text"
															value="${entity.maxPertransCount}" />
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														<span class="required">*</span>
														出金审核额度：
													</td>
													<td width="30%">
														<input type="text" id="maxAuditMoney" name="entity.maxAuditMoney"
															class="validate[required,custom[doubleCus],min[0.01],max[${maxDouble}]] input_text"
															value="<c:if test="${entity.maxAuditMoney>0}"><fmt:formatNumber value="${entity.maxAuditMoney}" pattern="0.00" /></c:if>" />
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														转账限制：
													</td>
													<td width="30%">
														<select id="control" name="entity.control">
															<c:forEach var="map" items="${bankControl}">
															<option value="${map.key}" <c:if test="${entity.control eq map.key}">selected="selected"</c:if>>${map.value}</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														<span class="required">*</span>
														转账开始时间：
													</td>
													<td width="30%">
														<input id="begintime0" maxlength="2" style="width: 30px;" class="validate[required,custom[integer],min[0],max[23],funcCall[begintimes]] input_text">时
														<input id="begintime1" maxlength="2" style="width: 30px;" class="validate[required,custom[integer],min[0],max[59],funcCall[begintimes]] input_text">分
														<input id="begintime2" maxlength="2" style="width: 30px;" class="validate[required,custom[integer],min[0],max[59],funcCall[begintimes]] input_text">秒
														<input type="hidden" id="begintime" name="entity.begintime" value="${entity.begintime}"/>
														<script>
															var begintime = '${entity.begintime}';
															var bt = begintime.split(":");
															$("#begintime0").val(bt[0]);
															$("#begintime1").val(bt[1]);
															$("#begintime2").val(bt[2]);
														</script>
													</td>
												</tr>
												<tr>
													<td align="right" width="20%">
														<span class="required">*</span>
														转账结束时间：
													</td>
													<td width="30%">
														<input id="endTime0" maxlength="2" style="width: 30px;" class="validate[required,custom[integer],min[0],max[23],funcCall[endTimes]] input_text">时<!-- custom[onlyNumber] -->
														<input id="endTime1" maxlength="2" style="width: 30px;" class="validate[required,custom[integer],min[0],max[59],funcCall[endTimes]] input_text">分
														<input id="endTime2" maxlength="2" style="width: 30px;" class="validate[required,custom[integer],min[0],max[59],funcCall[endTimes]] input_text">秒
														<input type="hidden" id="endTime" name="entity.endTime" value="${entity.endTime}"/>
														<script>
															var endTime = '${entity.endTime}';
															var et = endTime.split(":");
															$("#endTime0").val(et[0]);
															$("#endTime1").val(et[1]);
															$("#endTime2").val(et[2]);
														</script>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div class="div_cx">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr>
						<td align="right">
							<rightButton:rightButton name="修改" onclick="" className="btn_sec" action="/bank/bank/updateBank.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;<button class="btn_sec" onClick=window.close();>关闭</button>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>