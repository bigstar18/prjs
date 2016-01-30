<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/validationEngine.jquery.css"
		type="text/css" />
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js"
		type="text/javascript">
</script>
<script type="text/javascript" src="${basePath }/mgr/app/bill/js/province_city.js"></script>

	<script
		src="${basePath }/mgr/app/bill/js/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
	
</script>
	<script
		src="${publicPath }/js/validationengine/jquery.validationEngine.js"
		type="text/javascript" charset="GBK"></script>
	<script>
	jQuery(document).ready(function() {
		jQuery("#frm").validationEngine( {
			relative:true,
			ajaxFormValidation : true,
			ajaxFormValidationURL : "../../ajaxcheck/checkWarehouseForm.action",
			onAjaxFormComplete : ajaxValidationCallback,
			onBeforeAjaxFormValidation : beforeCall
		});
		function beforeCall(form, options) {
			return true;
		}

		// Called once the server replies to the ajax form validation request
		function ajaxValidationCallback(status, form, json, options) {
			if (status === true) {
				var vaild = window.confirm("��ȷ��Ҫ������");
				if (vaild == true) {
					form.validationEngine('detach');
					//$('#frm').attr('action', 'action');
					$('#frm').submit();
				}
			}
		}
		$("#update").click(function(check) {
			$("#frm").validationEngine('validateform');
		});
	});
	
 //����ʡ
 function loadProvince()
 { 
  //��ȡʡ����Ӧ���б�����
  var pro=document.getElementById("province");
  //��ȡ�Ѿ�����ʡ�ݵ�ֵ
  var proValue=document.getElementById("province").value;
  //����������ȡʡ����Ϣ
  for(var p in provinces)
  {
   //����optionԪ��
   var opt=document.createElement("option");
   //����optionԪ���е���Ϣ
   opt.innerText=p;
   opt.value=p;
   //��optionԪ����ӵ�selectԪ����(option��select���ӽڵ�)
   pro.appendChild(opt);
   if(proValue == p){
	   opt.selected=true;
   }
  }
 }
 //������
 function loadCity(val)
 {
  //��ȡ�û���ʡ��
  var selectProvince=val;
  //����ʡ��ȡ��(citys��һ������)
  var citys=provinces[selectProvince];
  //��ȡ������Ӧ���б�����
  var city=document.getElementById("city");
  var cityValue=document.getElementById("city").value;
  //��Ԫ���б���е�Ԫ��ȫ�����
  city.innerText="";
  
  var opt=document.createElement("option");
  opt.innerText="--��ѡ�����--";
  opt.value="";
  city.appendChild(opt);
  if(selectProvince !=0){
	for(var index=0;index<citys.length;index++)
	  {
	   opt=document.createElement("option");
	   opt.innerText=citys[index];
	   opt.value=citys[index];
	   city.appendChild(opt);
	   if(cityValue ==citys[index]){
		   opt.selected=true;
	   }
	  }
  }
  
 }
 //�ڴ��ڼ���ʱ���ʡ����Ϣ
 //window.onload=loadProvince;
</script>
	<head>
		<title>�ֿ��޸�</title>
	</head>
	<body style="overflow-y: hidden" onload="loadProvince();onload();">
		<form id="frm" name="frm" method="post"
			action="${basePath}/stock/warehouse/update.action?entity.id=${entity.id}" targetType="hidden">
			<input type="hidden" id="oldWarehouseId" name="oldWarehouseId" value="${entity.warehouseId }"/>
			<div class="div_cx"  style="overflow:auto;height:450px;" >
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<table border="0" width="95%" align="center">
								<tr>
									<td>
										<div class="warning">
											<div class="content">
												��ܰ��ʾ :�޸Ĳֿ���Ϣ,�ֿ����Ʊ����ǲֿ�ȫ��
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												�ֿ���Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div class="div_tj">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<tr height="20">
													<td align="right" width="25%">
														���:
													</td>
													<td width="25%">
															${entity.id }
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														<span class="required">*</span>�ֿ���:
													</td>
													<td width="25%">
													${entity.warehouseId }
														<input type="hidden" id="warehouseId"
															class="validate[required,maxSize[30],maxSize[<fmt:message key='warehouseId_q' bundle='${PropsFieldLength}'/>],ajax[checkWarehouseBywarehouseId]]" name="entity.warehouseId"
															value="${entity.warehouseId }" />
													</td>
													<td width="*" colspan="2">
														&nbsp;
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														<span class="required">*</span>�ֿ�����:
													</td>
													<td width="25%" >
														<input type="text" id="warehouseName"
															class="validate[required],maxSize[64],maxSize[<fmt:message key='warehouseName' bundle='${PropsFieldLength}'/>]" name="entity.warehouseName"
															value="${entity.warehouseName }" />
													</td>
													<td align="right" width="25%">
														<span class="required">*</span>�ֿ�״̬:
													</td>
													<td width="*">
														<select id="status" name="entity.status" class="validate[required]" style="width: 130px;">
															<option value="0" <c:if test="${entity.status ==0}">selected="selected"</c:if>>����</option>
															<option value="1"	<c:if test="${entity.status ==1}">selected="selected"</c:if>>������</option>
														</select>
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														�ֿ������λ��
													</td>
													<td width="25%">
														<input type="text" id="ownershipUnits"
															class="validate[maxSize[<fmt:message key='ownershipUnits_q' bundle='${PropsFieldLength}'/>]]" name="entity.ownershipUnits"
															value="${entity.ownershipUnits}" />
													</td>
													<td align="right" width="25%">
														<span class="required">*</span>�ֿ�ȼ���
													</td>
													<td width="*">
														<select id="rank"
															class="validate[required]" name="entity.rank" style="width: 133px;">
															<option value="1" <c:if test="${entity.rank==1}">selected='selected'</c:if>>һ�Ǽ� </option>
															<option value="2" <c:if test="${entity.rank==2}">selected='selected'</c:if>>���Ǽ�</option>
															<option value="3" <c:if test="${entity.rank==3}">selected='selected'</c:if>>���Ǽ� </option>
															<option value="4" <c:if test="${entity.rank==4}">selected='selected'</c:if>>���Ǽ�</option>
															<option value="5" <c:if test="${entity.rank==5}">selected='selected'</c:if>>���Ǽ�</option>
														</select>
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														<span class="required">*</span>ע���ʱ���
													</td>
													<td width="25%">
														<input type="text" id="registeredCapital"
															class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]" name="entity.registeredCapital"
															value="<fmt:formatNumber value='${entity.registeredCapital}' pattern='###0.00'/>"  />
													</td>
													<td align="right" width="25%">
														<span class="required">*</span>Ͷ���ܶ
													</td>
													<td width="25%">
														<input type="text" id="investmentAmount"
															class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]" name="entity.investmentAmount"
															value="<fmt:formatNumber value='${entity.investmentAmount}' pattern='###0.00'/>" data-prompt-position="topRight: -55 -20;"/>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												��ϵ��Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div class="div_tj">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<tr height="20">
													<td align="right" width="25%">
														�ֿ��ַ��
													</td>
													<td width="*" colspan="3">
														<input type="text" id="address" style="width: 350px;"
															class="validate[maxSize[<fmt:message key='address_q' bundle='${PropsFieldLength}' />]]" name="entity.address"
															value="${entity.address}" />
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														�ֿ����꣺
													</td>
													<td width="*" colspan="3">
														<input type="text" id="coordinate"  style="width: 350px;"
															class="validate[maxSize[<fmt:message key='coordinate_q' bundle='${PropsFieldLength}' />]]" name="entity.coordinate"
															value="${entity.coordinate}" />
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														ʡ�ݣ�
													</td>
													<td width="25%">
														<select id="province" name="entity.province" onchange="loadCity(this.value);" style="width: 133px;" value="${entityt.province}">
														   <option value="0">--��ѡ��ʡ��--</option>
														   <option value="${entity.province}" <c:if test="${entity.province !=''}">selected='selected'</c:if> ></option>
														 </select>
													</td>
													<td align="right" width="25%">
														�У�
													</td>
													<td width="25%">
														 <select id="city" name="entity.city" style="width: 133px;" value="${entity.city}">
														  <option value="">--��ѡ�����--</option>
														  <option value="${entity.city}" <c:if test="${entity.city !=''}">selected='selected'</c:if> ></option>
														 </select>
													</td>
												</tr>
												<tr>
													<td align="right" width="25%">
														�뽻������ǩ��Э�����ڣ�
													</td>
													<td width="25%">
														<input type="text" class="wdate" id="agreementDate" style="width: 133px;"
																	name="entity.agreementDate" value="<fmt:formatDate value='${entity.agreementDate}' pattern='yyyy-MM-dd' />"
																	onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />
													</td>
													<td align="right">���ˣ�</td>
													<td>
														<input type="text" id="corporateRepresentative" name="entity.corporateRepresentative" class="validate[maxSize[<fmt:message key='corporateRepresentative_q' bundle='${PropsFieldLength}' />]]" value="${entity.corporateRepresentative}"/>
													</td>
												</tr>
												<tr>
													<td align="right">������ϵ�绰��</td>
													<td>
														<input type="text" id="representativePhone" name="entity.representativePhone" class="validate[maxSize[<fmt:message key='representativePhone_q' bundle='${PropsFieldLength}' />]]" value="${entity.representativePhone}"/>
													</td>
													<td align="right">ҵ����ϵ�ˣ�</td>
													<td>
														<input type="text" id="contactMan" name="entity.contactMan" class="validate[maxSize[<fmt:message key='contactMan_q' bundle='${PropsFieldLength}' />]]" value="${entity.contactMan}"/>
													</td>
												</tr>
												<tr>
													<td align="right">ҵ����ϵ�绰��</td>
													<td>
														<input type="text" id="phone" name="entity.phone" class="validate[maxSize[<fmt:message key='phone_q' bundle='${PropsFieldLength}' />]]" value="${entity.phone}"/>
													</td>
													<td align="right">ҵ����ϵ���ֻ���</td>
													<td>
														<input type="text" id="mobile" name="entity.mobile" class="validate[maxSize[<fmt:message key='mobile_q' bundle='${PropsFieldLength}' />]]" value="${entity.mobile}"/>
													</td>
												</tr>
												<tr>
													<td align="right">�ʱࣺ</td>
													<td>
														<input type="text" id="postcode" name="entity.postcode" class="validate[maxSize[<fmt:message key='postcode_q' bundle='${PropsFieldLength}' />]]" value="${entity.postcode}"/>
													</td>
													<td align="right">���棺</td>
													<td>
														<input type="text" id="fax" name="entity.fax" class="validate[maxSize[<fmt:message key='fax_q' bundle='${PropsFieldLength}' />]]" value="${entity.fax}"/>
													</td>
												</tr>
												<tr>
													<td align="right" width="25%">
														�ִ�������
													</td>
													<td width="*" colspan="3">
														<input type="text" id="environmental"  style="width: 350px;"
															class="validate[maxSize[<fmt:message key='environmental_q' bundle='${PropsFieldLength}' />]]" name="entity.environmental"
															value="${entity.environmental}" />
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														���������
													</td>
													<td width="*" colspan="3">
														<input type="text" id="testConditions"  style="width: 350px;"
															class="validate[maxSize[<fmt:message key='testConditions_q' bundle='${PropsFieldLength}' />]]" name="entity.testConditions"
															value="${entity.testConditions}" />
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												�ۿ���Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div class="div_tj" id="postMessage">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<script>
													function onload(){
														getValue(${entity.hasDock});
														changeRail(${entity.hasRailway});
														changeTanker(${entity.hasTanker});
														loadCity('${entity.province}');
													}
													function getValue(value){
														if(value ==1){
															$("#hasDockLab").html("��ͷ��λ��");
															$("#dockTonnage").attr("class","validate[custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]")
															$("#dockDailyThroughputSp").html("��λ������");
															$("#dockDailyThroughput").attr("class","validate[custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]");
															$("#shipTypeSp").html("ͣ���������ͣ�");
															
														}else{
															$("#hasDockLab").html("<span class='required'>*</span>��ͷ��λ��");
															$("#dockTonnage").attr("class","validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]")
															$("#dockDailyThroughputSp").html("<span class='required'>*</span>��λ������");
															$("#dockDailyThroughput").attr("class","validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]");
															$("#shipTypeSp").html("<span class='required'>*</span>ͣ���������ͣ�");
															
														}
													}
												</script>
												<tr>
													<td align="right"><span class="required">*</span>�Ƿ�����ͷ��</td>
													<td>
														<select id="hasDock" name="entity.hasDock" style="width: 133px;" onchange="getValue(this.value)">
															<option value="0" <c:if test="${entity.hasDock ==0}">selected='selected'</c:if> >����ͷ </option>
															<option value="1" <c:if test="${entity.hasDock ==1}">selected='selected'</c:if> >û����ͷ</option>
														</select>
													</td>
													<td align="right"><div id="hasDockLab"><span class="required">*</span>��ͷ��λ��</div></td>
													<td id="dockTonnageTD">
														<input type="text" id="dockTonnage" name="entity.dockTonnage" class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]"  value="<fmt:formatNumber value='${entity.dockTonnage}' pattern='###0.00'/>"/>
													</td>
												</tr>
												<tr>
													<td align="right" width="25%"><div id="dockDailyThroughputSp"><span class="required">*</span>��λ������</div></td>
													<td width="25%">
														<input type="text" id="dockDailyThroughput" name="entity.dockDailyThroughput" class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]"  value="${entity.dockDailyThroughput}" />
													</td>
													<td align="right" width="25%"><div id="shipTypeSp"><span class="required">*</span>ͣ���������ͣ�</div></td>
													<td width="*">
														<select  id="shipType" name="entity.shipType" style="width: 133px;">
															<option value="0" <c:if test="${entity.shipType==0}">selected='selected'</c:if> >����</option>
															<option value="1" <c:if test="${entity.shipType==1}">selected='selected'</c:if> >���� </option>
															<option value="2" <c:if test="${entity.shipType==2}">selected='selected'</c:if> >ȫ��</option>
															<option value="3" <c:if test="${entity.shipType==3}">selected='selected'</c:if> >��֧��</option>
														</select>
													</td>
												</tr>
												<tr>
													<td align="right"><span class="required">*</span>�Ƿ�����·ר���ߣ�</td>
													<td>
														<select  id="hasRailway" name="entity.hasRailway" style="width: 133px;" onchange="changeRail(this.value)">
															<option value="0" <c:if test="${entity.hasRailway==0}">selected='selected'</c:if> >����·ר�� </option>
															<option value="1" <c:if test="${entity.hasRailway==1}">selected='selected'</c:if> >û����·ר�� </option>
														</select>
													</td>
													<script>
														function changeRail(val){
															if(val==1){
																$("#div2").html("��·��װж������");
																$("#railwayDailyThroughput").attr("class","validate[custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]");
																
															}else{
																$("#div2").html("<span class='required'>*</span>��·��װж������");
																$("#railwayDailyThroughput").attr("class","validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]");
															}
														}
													</script>
													<td align="right"><div id="div2"><span class="required">*</span>��·��װж������</div></td>
													<td>
														<input type="text" id="railwayDailyThroughput" name="entity.railwayDailyThroughput" class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]" value="<fmt:formatNumber value='${entity.railwayDailyThroughput}' pattern='###0.00'/>" data-prompt-position="topRight: -55 -20;"/>
													</td>
												</tr>
												<tr>
													<td align="right"><span class="required">*</span>�Ƿ�֧�ֲ۳�װж��</td>
													<td>
														<select  id="hasTanker" name="entity.hasTanker" style="width: 133px;" onchange="changeTanker(this.value)">
															<option value="0" <c:if test="${entity.hasTanker==0}">selected='selected'</c:if> >֧��</option>
															<option value="1" <c:if test="${entity.hasTanker==1}">selected='selected'</c:if> >��֧�� </option>
														</select>
													</td>
													<script>
														function changeTanker(val){
															if(val==1){
																$("#div1").html("������λ������");
																$("#tankerDailyThroughput").attr("class","validate[custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]")

															}else{
																$("#div1").html("<span class='required'>*</span>������λ������");
																$("#tankerDailyThroughput").attr("class","validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]");
															}
														}
													</script>
													<td align="right"><div id="div1"><span class="required">*</span>������λ������</div></td>
													<td>
														<input type="text" id="tankerDailyThroughput" name="entity.tankerDailyThroughput" class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]" value="${entity.tankerDailyThroughput}" data-prompt-position="topRight: -55 -20;"/>
														</select>
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
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="60%"
					align="right">
					<tr>
						<td align="center">
							<rightButton:rightButton name="�޸�" onclick="" className="btn_sec"
								action="/stock/warehouse/update.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<button class="btn_sec" onClick=
	window.close();;
>
								�ر�
							</button>
						</td>
					</tr>
				</table>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>