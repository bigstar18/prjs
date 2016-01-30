<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<base target="_self">
<%
request.setAttribute("maxDouble",9999999999999.99);
request.setAttribute("maxInteger",999999999);
%>
<html>
	<head>
		<title>��������������</title>
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
						var vaild = affirm("��ȷ��Ҫ������");
						if (vaild == true) {
							firm.validationEngine('detach');
							firm.attr("action",'${basePath}'+$("#update").attr("action"));
							firm.submit();
						}
					}
				});
			});
			function query(){<%//ִ�в�ѯ����%>
				$("#frm").validationEngine('detach');
				$("#frm").attr("action","${basePath}/bank/fee/setUpBankFeeForward.action");
				$("#frm").submit();
			}
			function addtr(){<%//�����%>
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
				td4.append("<select id='tmode"+num+"' name='feeInfoList["+num+"].tmode' onchange='changetmode("+num+")'><option value='0' selected='selected'>�ٷֱ�</option><option value='1'>����ֵ</option></select>")
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
			function deltr(){<%//ɾ����%>
				var tabBody = document.all.tableList.children[1];
				for(i=tabBody.children.length-1; i>=0; i--){
					if(tabBody.children[i].children[0].children[0].checked){
						tabBody.children[i].removeNode(true);
					}
				}
			}
			function chrate(feenum){<%//ͨ��������������ߡ����ֵ%>
				var tmode = $("#tmode"+feenum);
				if(tmode.attr("value")!=0){
					var rate = $("#rate"+feenum);
					var maxRateValue = $("#maxRateValue"+feenum);
					var minRateValue = $("#minRateValue"+feenum);
					maxRateValue.attr("value",rate.val());
					minRateValue.attr("value",rate.val());
				}
			}
			function changetmode(feenum){<%//ͨ������������%>
				changetmodetype(feenum);
				changetmodevalue(feenum);
			}
			function changetmodetype(feenum){<%//�޸���Ϣչʾ%>
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
			function changetmodevalue(feenum){<%//�޸Ĳ���ֵ%>
				var rate = $("#rate"+feenum);
				var maxRateValue = $("#maxRateValue"+feenum);
				var minRateValue = $("#minRateValue"+feenum);
				rate.attr("value",0);
				maxRateValue.attr("value",0);
				minRateValue.attr("value",0);
			}
			function checkDownLimit(field,role,i,option){<%//��֤��ʼ���%>
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
					return "*��ʼ���ܴ��ڽ������";
				}

				for(var i=0;i<num;i++){
					if(i!=feenum){
						if(upli == -1 && $("#upLimit"+i) && $("#upLimit"+i).val()){
							result = compareNum(downLimit.val(),$("#upLimit"+i).val());
							if(result == -1){
								return "*������Ϊ-1ʱ����Сֵ������ڵ��������������õ����ֵ";
							}
						}
						if($("#downLimit"+i) && $("#downLimit"+i).val()){
							result = compareNum(downLimit.val(),$("#downLimit"+i).val());
							if(result == 0){
								return "*�Ѿ������˱���ʼ���";
							}
							if(result == 1){
								if($("#upLimit"+i) && $("#upLimit"+i).val()){
									result = compareNum(downLimit.val(),$("#upLimit"+i).val());
									if(result == -1){
										return "*����ʼ����������ֶ��н���";
									}
								}
							}
						}
					}
				}
			}

			function checkUpLimit(field,role,i,option){<%//��֤�������%>
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
					return "*��������С����ʼ���";
				}

				for(var i=0;i<num;i++){
					if(i!=feenum){
						if($("#upLimit"+i) && $("#upLimit"+i).val()){
							result = compareNum(upLimit.val(),$("#upLimit"+i).val());
							if(result == 0){
								return "*�Ѿ������˱��������";
							}
							if(result == -1){
								if($("#downLimit"+i) && $("#downLimit"+i).val()){
									result = compareNum(upLimit.val(),$("#downLimit"+i).val());
									if(result == 1){
										return "*����������������ֶ��н���";
									}
								}
							}
						}
					}
				}
			}
			function checkMinRateValue(field,role,i,option){<%//��֤��Сֵ%>
				var feenum = field.attr("id").replace("minRateValue","");
				var minRateValue = $("#minRateValue"+feenum);
				var maxRateValue = $("#maxRateValue"+feenum);

				var result = compareNum(minRateValue.val(),maxRateValue.val());
				if(result == 0 || result == -1){
					maxRateValue.validationEngine('hide');
				}else if(result == 1){
					return "*��������Ѳ��ܸ������������";
				}
			}
			function checkMaxRateValue(field,role,i,option){<%//��֤���ֵ%>
				var feenum = field.attr("id").replace("maxRateValue","");
				var minRateValue = $("#minRateValue"+feenum);
				var maxRateValue = $("#maxRateValue"+feenum);

				var result = compareNum(minRateValue.val(),maxRateValue.val());
				if(result == 0 || result == -1){
					minRateValue.validationEngine('hide');
				}else if(result == 1){
					return "*��������Ѳ��ܵ������������";
				}
			}
			function compareNum(num1,num2){<%//��֤�������ֵĴ�С%>
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
			<input type="hidden" name="bankID" value="${bank.bankID}"/><%//���������б�� %>
			<div class="div_cx"  style="overflow:auto;height:480px;" >
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :${bank.bankName}&nbsp;���������á�<font color="#FF0000;"><strong>ע������Ϊ -1 ʱ����ʾ������</strong></font>
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
												������Ϣ
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
														<td align="center">��ѡ��</td>
														<td align="center">��ʼ���</td>
														<td align="center">�������</td>
														<td align="center">����������</td>
														<td align="center">������</td>
														<td align="center">���������</td>
														<td align="center">���������</td>
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
																<option value="0" <c:if test="${feeInfo.tmode==0}">selected="selected"</c:if>>�ٷֱ�</option>
																<option value="1" <c:if test="${feeInfo.tmode==1}">selected="selected"</c:if>>����ֵ</option>
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
							<button class="btn_sec" onClick=addtr();>���ӷֶ�</button>
							&nbsp;&nbsp;<button class="btn_sec" onClick=deltr();>ɾ���ֶ�</button>
							&nbsp;&nbsp;<rightButton:rightButton name="����" onclick="" className="btn_sec" action="/bank/fee/setUpBankFee.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;<button class="btn_sec" onClick=window.close();>�ر�</button>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>