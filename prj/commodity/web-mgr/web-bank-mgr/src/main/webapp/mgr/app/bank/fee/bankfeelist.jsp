<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<base target="_self">
<%
request.setAttribute("maxDouble",9999999999999.99);
request.setAttribute("maxInteger",999999999);
%>
<html>
	<head>
		<title>银行手续费设置</title>
		<meta http-equiv="Pragma" content="no-cache">
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${basePath }/mgr/app/bank/js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${basePath }/mgr/app/bank/js/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script>
			var num = 0;
			jQuery(document).ready(function() {
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
			function query(){<%//执行查询功能%>
				$("#frm").validationEngine('detach');
				$("#frm").attr("action","${basePath}/bank/fee/setUpBankFeeForward.action");
				$("#frm").submit();
			}
			function addtr(){<%//添加行%>
				var feebody = $("#feebody");
				var newtr = jQuery("<tr id='tr"+num+"'></tr>");
				var td1 = jQuery("<td align='center'></td>");
				td1.append("<input id='"+num+"' type='checkbox'/>");
				td1.append("<input type='hidden' name='feeInfoList["+num+"].type' value='${type}'>");
				var td2 = jQuery("<td align='center'></td>");
				td2.append("<input id='downLimit"+num+"' name='feeInfoList["+num+"].downLimit' class='validate[required,custom[integer],min[0],max[${maxInteger}],funcCall[checkDownLimit]] input_text' value='0'/>");
				var td3 = jQuery("<td align='center'></td>");
				td3.append("<input id='upLimit"+num+"' name='feeInfoList["+num+"].upLimit' class='validate[required,custom[integer],min[-1],max[${maxInteger}],funcCall[checkUpLimit]] input_text' value='0'/>");
				var td4 = jQuery("<td align='center'></td>");
				td4.append("<select id='tmode"+num+"' name='feeInfoList["+num+"].tmode' onchange='changetmode("+num+")'><option value='0' selected='selected'>百分比</option><option value='1'>绝对值</option></select>")
				var td5 = jQuery("<td align='center'></td>");
				td5.append("<input id='rate"+num+"' name='feeInfoList["+num+"].rate' onchange='chrate("+num+")' class='validate[required,custom[integer],min[0],max[99]] input_text' value='0'/>");
				td5.append("<font id='label"+num+"' color='#FF0000;'>%</font>");
				var td6 = jQuery("<td align='center'></td>");
				td6.append("<input id='minRateValue"+num+"' name='feeInfoList["+num+"].minRateValue' class='validate[required,custom[doubleCus],min[0],max[${maxDouble}],funcCall[checkMinRateValue]] input_text' value='0'/>");
				var td7 = jQuery("<td align='center'></td>");
				td7.append("<input id='maxRateValue"+num+"' name='feeInfoList["+num+"].maxRateValue' class='validate[required,custom[doubleCus],min[0],max[${maxDouble}],funcCall[checkMaxRateValue]] input_text' value='0'/>");
				newtr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
				feebody.append(newtr);
				num++;
				var firm = $("#frm");
				firm.validationEngine('attach');
			}
			function deltr(){<%//删除行%>
				var tabBody = document.all.tableList.children[1];
				for(i=tabBody.children.length-1; i>=0; i--){
					if(tabBody.children[i].children[0].children[0].checked){
						tabBody.children[i].removeNode(true);
					}
				}
			}
			function chrate(feenum){<%//通过手续费设置最高、最低值%>
				var tmode = $("#tmode"+feenum);
				if(tmode.attr("value")!=0){
					var rate = $("#rate"+feenum);
					var maxRateValue = $("#maxRateValue"+feenum);
					var minRateValue = $("#minRateValue"+feenum);
					maxRateValue.attr("value",rate.val());
					minRateValue.attr("value",rate.val());
				}
			}
			function changetmode(feenum){<%//通过手续费类型%>
				changetmodetype(feenum);
				changetmodevalue(feenum);
			}
			function changetmodetype(feenum){<%//修改信息展示%>
				var tmode = $("#tmode"+feenum);
				var rate = $("#rate"+feenum);
				var label = $("#label"+feenum);
				var maxRateValue = $("#maxRateValue"+feenum);
				var minRateValue = $("#minRateValue"+feenum);
				
				if(tmode.attr("value")==0){
					label.html("%");
					maxRateValue.attr("readonly",false);
					maxRateValue.css("background-color","");
					minRateValue.attr("readonly",false);
					minRateValue.css("background-color","");
					rate.attr("class","validate[required,custom[integer],min[0],max[99]] input_text");
				}else{
					label.html("&nbsp;&nbsp;&nbsp;");
					maxRateValue.attr("readonly","readonly");
					maxRateValue.css("background-color","#CCCCCC");
					minRateValue.attr("readonly","readonly");
					minRateValue.css("background-color","#CCCCCC");
					rate.attr("class","validate[required,custom[doubleCus],min[0],max[${maxDouble}]] input_text");
				}
				var firm = $("#frm");
				firm.validationEngine('attach');
			}
			function changetmodevalue(feenum){<%//修改参数值%>
				var rate = $("#rate"+feenum);
				var maxRateValue = $("#maxRateValue"+feenum);
				var minRateValue = $("#minRateValue"+feenum);
				rate.attr("value",0);
				maxRateValue.attr("value",0);
				minRateValue.attr("value",0);
			}
			function checkDownLimit(field,role,i,option){<%//验证起始金额%>
				var feenum = field.attr("id").replace("downLimit","");
				var downLimit = $("#downLimit"+feenum);
				var upLimit = $("#upLimit"+feenum);

				var result = 0;
				var upli = parseFloat(upLimit.val());
				if(upli != -1){
					result = compareNum(downLimit.val(),upLimit.val());
				}
				if(result == 0 || result == -1){
					upLimit.validationEngine('hide');
				}else if(result == 1){
					return "*起始金额不能大于结束金额";
				}

				for(var i=0;i<num;i++){
					if(i!=feenum){
						if(upli == -1 && $("#upLimit"+i) && $("#upLimit"+i).val()){
							result = compareNum(downLimit.val(),$("#upLimit"+i).val());
							if(result == -1){
								return "*当上限为-1时，最小值必须大于等于所有其他设置的最大值";
							}
						}
						if($("#downLimit"+i) && $("#downLimit"+i).val()){
							result = compareNum(downLimit.val(),$("#downLimit"+i).val());
							if(result == 0){
								return "*已经存在了本起始金额";
							}
							if(result == 1){
								if($("#upLimit"+i) && $("#upLimit"+i).val()){
									result = compareNum(downLimit.val(),$("#upLimit"+i).val());
									if(result == -1){
										return "*本起始金额与其他分段有交叉";
									}
								}
							}
						}
					}
				}
			}

			function checkUpLimit(field,role,i,option){<%//验证结束金额%>
				var feenum = field.attr("id").replace("upLimit","");
				var downLimit = $("#downLimit"+feenum);
				var upLimit = $("#upLimit"+feenum);
	
				var result = 0;
				var upli = parseFloat(upLimit.val());
				if(upli != -1){
					result = compareNum(downLimit.val(),upLimit.val());
				}
				if(result == 0 || result == -1){
					downLimit.validationEngine('hide');
				}else if(result == 1){
					return "*结束金额不能小于起始金额";
				}

				for(var i=0;i<num;i++){
					if(i!=feenum){
						if($("#upLimit"+i) && $("#upLimit"+i).val()){
							result = compareNum(upLimit.val(),$("#upLimit"+i).val());
							if(result == 0){
								return "*已经存在了本结束金额";
							}
							if(result == -1){
								if($("#downLimit"+i) && $("#downLimit"+i).val()){
									result = compareNum(upLimit.val(),$("#downLimit"+i).val());
									if(result == 1){
										return "*本结束金额与其他分段有交叉";
									}
								}
							}
						}
					}
				}
			}
			function checkMinRateValue(field,role,i,option){<%//验证最小值%>
				var feenum = field.attr("id").replace("minRateValue","");
				var minRateValue = $("#minRateValue"+feenum);
				var maxRateValue = $("#maxRateValue"+feenum);

				var result = compareNum(minRateValue.val(),maxRateValue.val());
				if(result == 0 || result == -1){
					maxRateValue.validationEngine('hide');
				}else if(result == 1){
					return "*最低手续费不能高于最高手续费";
				}
			}
			function checkMaxRateValue(field,role,i,option){<%//验证最大值%>
				var feenum = field.attr("id").replace("maxRateValue","");
				var minRateValue = $("#minRateValue"+feenum);
				var maxRateValue = $("#maxRateValue"+feenum);

				var result = compareNum(minRateValue.val(),maxRateValue.val());
				if(result == 0 || result == -1){
					minRateValue.validationEngine('hide');
				}else if(result == 1){
					return "*最高手续费不能低于最低手续费";
				}
			}
			function compareNum(num1,num2){<%//验证两个数字的大小%>
				if(!(num1) || !(num2)) return 2;
				try{
					num1 = parseFloat(num1);
					num2 = parseFloat(num2);
				}catch(e){return 2;}
				if(num1==num2) return 0;
				if(num1>num2) return 1;
				if(num1<num2) return -1;
			}
		</script>
	</head>
	<body style="overflow-y: hidden">
		<form id="frm" method="post" action="">
			<input type="hidden" name="bankID" value="${bank.bankID}"/><%//隐藏域银行编号 %>
			<div class="div_cx"  style="overflow:auto;height:480px;" >
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :${bank.bankName}&nbsp;手续费设置。<font color="#FF0000;"><strong>注：上限为 -1 时，表示无上限</strong></font>
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
												设置信息
											</div>
											<div class="div_cxtjC">
												<select id="type" name="type" onchange="query()">
													<c:forEach var="dic" items="${diclist}">
													<option value="${dic.value}" <c:if test="${type eq dic.value}">selected="selected"</c:if>>${dic.note}</option>
													</c:forEach>
												</select>
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table id="tableList" border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<thead>
													<tr style="background-color: #CCCCCC;">
														<td align="center">请选择</td>
														<td align="center">起始金额</td>
														<td align="center">结束金额</td>
														<td align="center">手续费类型</td>
														<td align="center">手续费</td>
														<td align="center">最低手续费</td>
														<td align="center">最高手续费</td>
													</tr>
												</thead>
												<tbody id="feebody">
													<c:forEach var="feeInfo" items="${pageInfo.result}" varStatus="status">
													<tr id="tr${status.index}">
														<td align="center">
															<input id="${status.index}" type="checkbox"/>
														</td>
														<td align="center">
															<input id="downLimit${status.index}" name="feeInfoList[${status.index}].downLimit" class="validate[required,custom[integer],min[0],max[${maxInteger}],funcCall[checkDownLimit]] input_text" value="<fmt:formatNumber value="${feeInfo.downLimit}" pattern="#0" />"/>
														</td>
														<td align="center">
															<input id="upLimit${status.index}" name="feeInfoList[${status.index}].upLimit" class="validate[required,custom[integer],min[-1],max[${maxInteger}],funcCall[checkUpLimit]] input_text" value="<fmt:formatNumber value="${feeInfo.upLimit}" pattern="#0" />"/>
														</td>
														<td align="center">
															<select id="tmode${status.index}" name="feeInfoList[${status.index}].tmode" onchange="changetmode(${status.index})">
																<option value="0" <c:if test="${feeInfo.tmode==0}">selected="selected"</c:if>>百分比</option>
																<option value="1" <c:if test="${feeInfo.tmode==1}">selected="selected"</c:if>>绝对值</option>
															</select>
														</td>
														<td align="center">
															<input id="rate${status.index}" name="feeInfoList[${status.index}].rate" class="input_text" onchange="chrate(${status.index})" value="<c:if 
																test="${feeInfo.tmode==0}"><fmt:formatNumber value="${feeInfo.rate*100}" pattern="#0.##" /></c:if><c:if 
																test="${!(feeInfo.tmode==0)}"><fmt:formatNumber value="${feeInfo.rate}" pattern="#0.00" /></c:if>"/>
															<font id="label${status.index}" color="#FF0000;"><c:if test="${feeInfo.tmode==0}">%</c:if><c:if test="${!(feeInfo.tmode==0)}">&nbsp;&nbsp;&nbsp;</c:if></font>
														</td>
														<td align="center">
															<input id="minRateValue${status.index}" name="feeInfoList[${status.index}].minRateValue" class="validate[required,custom[doubleCus],min[0],max[${maxDouble}],funcCall[checkMinRateValue]] input_text" value="<fmt:formatNumber value="${feeInfo.minRateValue}" pattern="#0.00" />" <c:if test="${!(feeInfo.tmode==0)}">readonly="readonly" style="background-color: #CCCCCC;"</c:if>/>
														</td>
														<td align="center">
															<input id="maxRateValue${status.index}" name="feeInfoList[${status.index}].maxRateValue" class="validate[required,custom[doubleCus],min[0],max[${maxDouble}],funcCall[checkMaxRateValue]] input_text" value="<fmt:formatNumber value="${feeInfo.maxRateValue}" pattern="#0.00" />" <c:if test="${!(feeInfo.tmode==0)}">readonly="readonly" style="background-color: #CCCCCC;"</c:if>/>
															<script>num=num+1; changetmodetype(${status.index});</script>
														</td>
													</tr>
													</c:forEach>
												</tbody>
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
							<button class="btn_sec" onClick=addtr();>增加分段</button>
							&nbsp;&nbsp;<button class="btn_sec" onClick=deltr();>删除分段</button>
							&nbsp;&nbsp;<rightButton:rightButton name="设置" onclick="" className="btn_sec" action="/bank/fee/setUpBankFee.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;<button class="btn_sec" onClick=window.close();>关闭</button>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>