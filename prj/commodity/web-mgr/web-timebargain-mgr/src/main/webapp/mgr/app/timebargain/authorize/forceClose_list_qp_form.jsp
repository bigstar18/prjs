<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>
		ǿ��ת����Ϣ
		</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${publicPath}/js/firmjson.js"></script>
		<script type="text/javascript"> 
		function setReadOnly(obj)
		{
		  obj.readOnly = true;
		  obj.style.backgroundColor = "#C0C0C0";
		}
		function setReadWrite(obj)
		{
		  obj.readOnly = false;
		  obj.style.backgroundColor = "white";
		}
		function setDisabled(obj)
		{
		  obj.disabled = true;
		  obj.style.backgroundColor = "#C0C0C0";
		}
		function setEnabled(obj)
		{
		  obj.disabled = false;
		  obj.style.backgroundColor = "white";
		}
		function window_onload()
		{
			frm.forceCloseType.value = "1";
		    frm.customerId.value = '${firmID }' + '00';
		    frm.price.value = '${lastPrice }';
		    <c:if test="${BS_Flag==1 }">
		    	frm.BS_Flag.value = "2"; //����ƽ
		    </c:if>
		    <c:if test="${BS_Flag==2 }">
		    	frm.BS_Flag.value = "1"; //������ƽ
		    </c:if>
		    frm.customerId.focus();
		    
		    if (frm.type.value == "0") {
		    	alert("��¼��Ϊ��Ϊί��Ա�����ܽ���ǿ��ת�ò�����");
		    }
		    startXML();
		}
		
		function close_close(){
			window.close();
		}
		
		function forceCloseType_onchange(){
			if (frm.forceCloseType.value == "1"||frm.forceCloseType.value == "") {
				frm.price.value = "";
				setReadWrite(frm.price);
			}
			if (frm.forceCloseType.value == "2") {
				frm.price.value = "0";
				setReadOnly(frm.price);
			}
		}
		
		function startXML(){
				var oldAjaxAsync = $.ajaxSettings.async;
				var url = "${basePath}/ajaxcheck/order/searchForceCloseMoney.action?firmID=" + '${firmID }' + "&commodityID=" + '${commodityID }' + "&bS_Flag=" + '${BS_Flag }' + "&quantity=1" + "&evenPrice=" + '${EvenPrice }' + "&price=" + frm.price.value + "&holdQty=" + '${HoldQty }' + "&usefulFunds=" + '${usefulFunds }';
				$.ajaxSettings.async = false;
				$.getJSON(url,null,function call(result){
					frm.quantity.value = result;
    				document.getElementById("referenceQty").innerHTML = result;
				});
				$.ajaxSettings.async = oldAjaxAsync;
			}
		
		
		
		//����Ա�Ƿ��½���ύί��(ͳһ�ص�����)
		function order()
		{
			var firmID = frm.firmId.value;
			frm.action = "${basePath}/timebargain/authorize/forceClose.action?firmID="+firmID;
			frm.submit();
			frm.order.disabled = true;
			frm.action = "${basePath}/timebargain/authorize/chkLogin.action";
		}
		
		
		
		jQuery(document).ready(function() {
			jQuery("#frm").validationEngine('attach');
			$("#order").click(function(){
				//��֤��Ϣ
				if ($("#frm").validationEngine('validate')) {
					loginConsigner();
				}
			});
			
			//�ύ�¼�
			function loginConsigner() {
				var vaild = confirm("��ȷ��Ҫ������");
				if(vaild){
					//�����ϢURL
					var orderUrl = "/timebargain/authorize/chkLogin.action";
					//ȫ URL ·��
					var url = "${basePath}"+orderUrl;
					$("#frm").attr("action",url);
					frm.submit();
				}
			}
		});
		</script>
	</head>
	<body onload="window_onload();">
		<form id="frm" method="post" enctype="multipart/form-data" action="${basePath}/mgr/app/timebargain/authorize/loginConsigner.action" target="HiddFrame">
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :ǿ��ת����Ϣ
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
												ǿ��ת����Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" class="table2_style"><tr><td><b>��������Ϣ</b></td></tr></table>
											<table border="1" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="left" style="width:20%">
														�����̴���
													</td>
													<td colspan="3" style="width:80%">
														${firmID }&nbsp;
													</td>	            
										        </tr>
										        <tr>
													<td align="left"  style="width:20%">
														���㸡��
													</td>
													<td  style="width:30%">
														<fmt:formatNumber value="${clearFL }" pattern="#,##0.00"/>&nbsp;
													</td>
													<td align="left"  style="width:20%">
														��ʱ����
													</td>
										            <td  style="width:30%">
														<fmt:formatNumber value="${runtimeFL }" pattern="#,##0.00"/>&nbsp;
													</td>	            
										        </tr>
										        <tr>
													<td align="left"  style="width:20%">
														���㱣֤��
													</td>
													<td  style="width:30%">
														<fmt:formatNumber value="${clearMargin }" pattern="#,##0.00"/>&nbsp;
													</td>
													<td align="left"  style="width:20%">
														��ʱ��֤��
													</td>
										            <td  style="width:30%">
														<fmt:formatNumber value="${runtimeMargin }" pattern="#,##0.00"/>&nbsp;
													</td>	            
										        </tr>
										      	 <tr>
													<td align="left"  style="width:20%">
														��Ѻ�ʽ�
													</td>
													<td colspan="3" style="width:80%">
														<fmt:formatNumber value="${maxOverdraft }" pattern="#,##0.00"/>&nbsp;
													</td>            
										        </tr>
										        <tr>
													<td align="left"  style="width:20%">
														�����ʽ�
													</td>
													<td  style="width:30%">
														<fmt:formatNumber value="${usefulFunds }" pattern="#,##0.00"/>&nbsp;
													</td>
													<td align="left" style="width:20%">
														��ͽ��㱣֤��
													</td>
										            <td  style="width:30%">
														<fmt:formatNumber value="${minClearDeposit }" pattern="#,##0.00"/>&nbsp;
													</td>	            
										        </tr>
										    </table>
										    <table border="0" cellspacing="0" cellpadding="4" width="100%" class="table2_style"><tr><td><b>��Ӧ������Ϣ</b></td></tr></table>
										    <table border="1" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
										    	 <tr>
													<td align="left"  style="width:20%">
														��Ʒ����
													</td>
													<td  style="width:30%">
														${commodityID }&nbsp;
													</td>
													<td align="left"  style="width:20%">
														����
													</td>
										            <td  style="width:30%">
													<c:if test="${BS_Flag==1 }">���</c:if><c:if test="${BS_Flag==2 }">����</c:if>&nbsp;
													</td>	            
										        </tr>
										        <tr>
													<td align="left"  style="width:20%">
														��������
													</td>
													<td  style="width:30%">
														${HoldQty }&nbsp;
													</td>
													<td align="left"  style="width:20%">
														��������
													</td>
										            <td  style="width:30%">
														<fmt:formatNumber value="${EvenPrice }" pattern="#,##0.00"/>&nbsp;
													</td>	            
										        </tr>
										    </table>
										    <table border="0" cellspacing="0" cellpadding="4" width="100%" class="table2_style"><tr><td><b>��������</b></td></tr></table>
										    <table border="1" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
										    	<tr>
													<td align="left">
														<span class="required">*</span>
														��������
													</td>
													<td>
														<input type="text" id="customerId" name="entity.customerId"  maxlength="64" style="width:150;height:20"
															class="validate[required]"/>
													</td>
													<td align="left">
														<span class="required">*</span>
														ί�м۸�
													</td>
													<td>
														<input type="text" id="price" name="entity.price"  maxlength="64" style="width:150;height:20" onchange="startXML();"
															class="validate[required,custom[doubleCus],maxSize[16]]"/>
													</td>
												</tr>
												<tr>
													<td align="left">
														<span class="required">*</span>
														ǿ��ת�ü۸�ʽ
													</td>
													<td>
														<select id="forceCloseType" name="entity.forceCloseType" class="validate[required]"
															style="width:150" onchange="forceCloseType_onchange()">
															<option value=""></option>
															<option value="1">ָ���۸�</option>
															<option value="2">���м�</option>
														</select>
													</td>
													<td align="left">
														<span class="required">*</span>
														ί������
													</td>
													<td>
														<input type="text" id="quantity" name="entity.quantity"  maxlength="64" style="width:150;height:20"
															class="validate[required,custom[integer],min[1],maxSize[16]]"/>
													</td>
												</tr>
										    	<tr>
													<td align="left" colspan="4">
														<span class="required">ǿת�ο�������</span><span id="referenceQty"></span>
														
													</td>
												</tr>
												<input type="hidden" id="orderType" name="entity.orderType" value="2"/>
												<input type="hidden" id="Uni_Cmdty_Code" name="entity.Uni_Cmdty_Code" value=""/>
												<input type="hidden" id="closeFlag" name="entity.closeFlag" value="2"/>
												<input type="hidden" id="closeAlgr" name="entity.closeAlgr" value="0"/>
												<input type="hidden" id="firmId" name="entity.firmId" value="${firmID }"/>
												<input type="hidden" id="BS_Flag" name="entity.BS_Flag" value="${BS_Flag }"/>
												<input type="hidden" id="commodityId" name="entity.commodityId" value="${commodityID }"/>
												<input type="hidden" id="closeMode" name="entity.closeMode" value="1"/>
												<input type="hidden" id="type" name="entity.type" value="${relType }"/>

										    </table>  
										</div>
									</td>
								</tr>
								<tr>
								<td>
								<div>
									<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
										<tr>
											<td align="center">
												<rightButton:rightButton name="ǿ��ת��" onclick="" className="btn_sec" action="/timebargain/authorize/forceClose.action" id="order"></rightButton:rightButton>
												&nbsp;&nbsp;
												<button class="btn_sec" onClick="window.close();">�˳�</button>
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
		</form>
	</body>
	</html>
