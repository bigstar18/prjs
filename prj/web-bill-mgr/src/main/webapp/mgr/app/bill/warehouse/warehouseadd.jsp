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
		//warehouseIsNeedKey��Y��ʾ�ֿ�˺�̨��������key 
		<%if(Global.getMarketInfoMap().get("warehouseIsNeedKey").equals("Y") 
				&& Global.getMarketInfoMap().get("marketNO") != null 
				&& !"0".equals(Global.getMarketInfoMap().get("marketNO"))){%>
			$("#mgrIsNeedKeyCode").css("display","block");
		<%}%>
		
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
		$("#add").click(function(check) {
			$("#frm").validationEngine('validateform');
		});
	});
	
 //����ʡ
 function loadProvince()
 { 
  //��ȡʡ����Ӧ���б�����
  var pro=document.getElementById("province");
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
  }
 }
 //������
 function loadCity()
 {
  //��ȡ�û���ʡ��
  var selectProvince=document.getElementById("province").value;
  //����ʡ��ȡ��(citys��һ������)
  var citys=provinces[selectProvince];
  //��ȡ������Ӧ���б�����
  var city=document.getElementById("city");
  //��Ԫ���б���е�Ԫ��ȫ�����
  city.innerText="";
  
  var opt=document.createElement("option");
  opt.innerText="--��ѡ�����--";
  opt.value="0";
  city.appendChild(opt);
  if(selectProvince !=0){
	for(var index=0;index<citys.length;index++)
	  {
	   opt=document.createElement("option");
	   opt.innerText=citys[index];
	   opt.value=citys[index];
	   city.appendChild(opt);
	  }
  }
  
 }
 //�ڴ��ڼ���ʱ���ʡ����Ϣ
 window.onload=loadProvince;
 
 
function checkKey(inp){
	if(inp.checked){
		document.getElementById("showkey").style.display="";
		var m = initKeyCode('<%=Global.getMarketInfoMap().get("marketNO") %>',document.getElementById("userId").value,frm);
		if(!m.passed){
			alert(m.msg);
			document.getElementById("kcodech").checked = false;
			checkKey(document.getElementById("kcodech"));
		}
	}else{
		document.getElementById("kcode").value="0123456789ABCDE";
		document.getElementById("showkey").style.display="none";
	}
}
</script>
	<head>
		<title>�ֿ����</title>
	</head>
	<body style="overflow-y: hidden">
		<form id="frm" name="frm" method="post"
			action="${basePath}/stock/warehouse/add.action" targetType="hidden">
			<div class="div_cx"  style="overflow:auto;height:450px;" >
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<table border="0" width="95%" align="center">
								<tr>
									<td>
										<div class="warning">
											<div class="content">
												��ܰ��ʾ :��Ӳֿ���Ϣ,�ֿ����Ʊ����ǲֿ�ȫ��
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												������Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div class="div_tj">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<tr height="20">
													<td align="right" width="25%">
														<span class="required">*</span>�ֿ��ţ�
													</td>
													<td width="25%">
														<input type="text" id="warehouseId" 
															class="validate[required,custom[onlyLetterNumber],maxSize[30],ajax[checkWarehouseBywarehouseId]]" name="entity.warehouseId"
															value="" />
														<input id="status" type="hidden" name="entity.status" value="0"/>
													</td>
													<td align="left" colspan="2" width="*">
														<div class="onfocus">
															����Ϊ�գ�
														</div>
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														<span class="required">*</span>�ֿ����ƣ�
													</td>
													<td width="25%">
														<input type="text" id="warehouseName"
															class="validate[required,maxSize[<fmt:message key='warehouseName' bundle='${PropsFieldLength}' />]]" name="entity.warehouseName"
															value="" />
													</td>
													<td align="left" colspan="2" width="*">
														<div class="onfocus">
															����Ϊ�գ�
														</div>
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														�ֿ������λ��
													</td>
													<td width="25%">
														<input type="text" id="ownershipUnits"
															class="validate[maxSize[<fmt:message key='ownershipUnits_q' bundle='${PropsFieldLength}'/>]]" name="entity.ownershipUnits"
															value="" />
													</td>
													<td align="right" width="25%">
														<span class="required">*</span>�ֿ�ȼ���
													</td>
													<td width="*">
														<select id="rank"
															class="validate[required]" name="entity.rank" style="width: 133px;">
															<option value="1">һ�Ǽ� </option>
															<option value="2">���Ǽ�</option>
															<option value="3">���Ǽ� </option>
															<option value="4">���Ǽ�</option>
															<option value="5">���Ǽ�</option>
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
															value="" />
													</td>
													<td align="right" width="25%">
														<span class="required">*</span>Ͷ���ܶ
													</td>
													<td width="*">
														<input type="text" id="investmentAmount"
															class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]" name="entity.investmentAmount" data-prompt-position="topRight: -45 -20;"
															value="" />
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
													<td align="right">
														�ֿ��ַ��
													</td>
													<td  colspan="3">
														<input type="text" id="address" style="width: 300px;"
															class="validate[maxSize[<fmt:message key='address_q' bundle='${PropsFieldLength}' />]]" name="entity.address"
															value="" />
													</td>
												</tr>
												<tr height="20">
													<td align="right">
														�ֿ����꣺
													</td>
													<td width="*" colspan="3">
														<input type="text" id="coordinate" style="width: 300px;"
															class="validate[maxSize[<fmt:message key='coordinate_q' bundle='${PropsFieldLength}' />]]" name="entity.coordinate"
															value="" />
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														ʡ�ݣ�
													</td>
													<td width="25%">
														<select id="province" name="entity.province" onchange="loadCity();" style="width: 133px;">
														   <option value="0">--��ѡ��ʡ��--</option>
														 </select>
													</td>
													<td align="right" width="25%">
														���У�
													</td>
													<td width="*">
														 <select id="city" name="entity.city" style="width: 133px;">
														  <option value="">--��ѡ�����--</option>
														 </select>
													</td>
												</tr>
												<tr>
													<td align="right">
														�뽻������ǩ��Э�����ڣ�
													</td>
													<td>
														<input type="text" class="wdate" id="agreementDate" style="width: 133px;"
																	name="entity.agreementDate"
																	onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />
													</td>
													<td align="right">���ˣ�</td>
													<td>
														<input type="text" id="corporateRepresentative" name="entity.corporateRepresentative" class="validate[maxSize[<fmt:message key='corporateRepresentative_q' bundle='${PropsFieldLength}' />]]"/>
													</td>
												</tr>
												<tr>
													<td align="right">������ϵ�绰��</td>
													<td>
														<input type="text" id="representativePhone" name="entity.representativePhone" class="validate[maxSize[<fmt:message key='representativePhone_q' bundle='${PropsFieldLength}' />]]"/>
													</td>
													<td align="right">ҵ����ϵ�ˣ�</td>
													<td>
														<input type="text" id="contactMan" name="entity.contactMan" class="validate[maxSize[<fmt:message key='contactMan_q' bundle='${PropsFieldLength}' />]]"/>
													</td>
												</tr>
												<tr>
													<td align="right">ҵ����ϵ�绰��</td>
													<td>
														<input type="text" id="phone" name="entity.phone" class="validate[maxSize[<fmt:message key='phone_q' bundle='${PropsFieldLength}' />]]"/>
													</td>
													<td align="right">ҵ����ϵ���ֻ���</td>
													<td>
														<input type="text" id="mobile" name="entity.mobile" class="validate[maxSize[<fmt:message key='mobile_q' bundle='${PropsFieldLength}' />]]"/>
													</td>
												</tr>
												<tr>
													<td align="right">�ʱࣺ</td>
													<td>
														<input type="text" id="postcode" name="entity.postcode" class="validate[custom[number1],maxSize[<fmt:message key='postcode_q' bundle='${PropsFieldLength}' />]]" />
													</td>
													<td align="right">���棺</td>
													<td>
														<input type="text" id="fax" name="entity.fax" class="validate[custom[fax],maxSize[<fmt:message key='fax_q' bundle='${PropsFieldLength}' />]]"/>
													</td>
												</tr>
												<tr height="20">
													<td align="right">
														�ִ�������
													</td>
													<td colspan="3">
														<input type="text" id="environmental" style="width: 300px;"
															class="validate[maxSize[<fmt:message key='environmental_q' bundle='${PropsFieldLength}' />]]" name="entity.environmental"
															value="" />
													</td>
												</tr>
												<tr height="20">
													<td align="right">
														���������
													</td>
													<td colspan="3">
														<input type="text" id="testConditions" style="width: 300px;"
															class="validate[maxSize[<fmt:message key='testConditions_q' bundle='${PropsFieldLength}' />]]" name="entity.testConditions"
															value="" />
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
										</div><%--<input type="checkbox" id="isPost" checked="checked" onclick="getChecked()"/>���Ƿ��иۿ���Ϣ��
										--%><div style="clear: both;"></div>
										<div class="div_tj" id="postMessage">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<script>
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
													<td align="right" width="25%"><span class="required">*</span>�Ƿ�����ͷ��</td>
													<td width="25%">
														<select id="hasDock" name="entity.hasDock" style="width: 133px;" onchange="getValue(this.value)">
															<option value="0">����ͷ </option>
															<option value="1">û����ͷ</option>
														</select>
													</td>
													
													<td align="right" width="25%"><div id="hasDockLab"><span class="required">*</span>��ͷ��λ��</div></td>
													<td id="dockTonnageTD"  width="*">
														<input type="text" id="dockTonnage" name="entity.dockTonnage" class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]"/>
													</td>
												</tr>
												<tr>
													<td align="right"  width="25%"><div id="dockDailyThroughputSp"><span class="required">*</span>��λ������</div></td>
													<td  width="25%">
														<input type="text" id="dockDailyThroughput" name="entity.dockDailyThroughput" class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]" />
													</td>
												
													<td align="right"  width="25%"><div id="shipTypeSp"><span class="required">*</span>ͣ���������ͣ�</div></td>
													<td  width="*">
														<select  id="shipType" name="entity.shipType" style="width: 133px;">
															<option value="0">����</option>
															<option value="1">���� </option>
															<option value="2">ȫ��</option>
															<option value="3">��֧��</option>
														</select>
													</td>
												</tr>
												<tr>
													<td align="right"><span class="required">*</span>�Ƿ�����·ר���ߣ�</td>
													<td>
														<select  id="hasRailway" name="entity.hasRailway" style="width: 133px;" onchange="changeRail(this.value)">
															<option value="0">����·ר�� </option>
															<option value="1">û����·ר�� </option>
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
														<input type="text" id="railwayDailyThroughput" name="entity.railwayDailyThroughput" class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]" data-prompt-position="topRight: -45 -20;"/>
													</td>
												</tr>
												<tr>
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
													<td align="right"><span class="required">*</span>�Ƿ�֧�ֲ۳�װж��</td>
													<td>
														<select  id="hasTanker" name="entity.hasTanker" style="width: 133px;" onchange="changeTanker(this.value)">
															<option value="0">֧��</option>
															<option value="1">��֧�� </option>
														</select>
													</td>
													<td align="right"><div id="div1"><span class="required">*</span>������λ������</div></td>
													<td>
														<input type="text" id="tankerDailyThroughput" name="entity.tankerDailyThroughput" class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]" data-prompt-position="bottomLeft: 20,5;"/>
														</select>
													</td>
													
												</tr>
												
											</table>
										</div>
									</td>
								</tr>
								<!-- �޲ְ滷������Ӳֿⳬ������Ա -->
								<c:if test="${haveWarehouse==0}">
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												�ֿ����Ա��Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
											<tr height="35">
												<td align="right" class="td_size" width="25%">
													<span class="required">*</span>�û�����
												</td>
												<td align="left" width="25%">
													<input id="userId"  name="userId"
														type="text"
														class="validate[required,custom[onlyLetterNumber],maxSize[<fmt:message key='userID' bundle='${PropsFieldLength}'/>],ajax[checkWuserByUserId]] " />
												</td>
												<td align="left" colspan="2" width="*">
													<div class="onfocus">
														����Ϊ�գ�
													</div>
												</td>
											</tr>
											<tr height="35">
												<td align="right" class="td_size" width="25%">
													<span class="required">*</span>����Ա���ƣ�
												</td>
												<td align="left" width="25%">
													<input id="name"  name="name"
														type="text" class="validate[required,maxSize[<fmt:message key='userName' bundle='${PropsFieldLength}'/>]] " />
												</td>
												<td align="left" colspan="2" width="*">
													<div class="onfocus">
														����Ϊ�գ�
													</div>
												</td>
											</tr>
											<input type="hidden" id="type" name="type"/>
											<tr style="display:none;">
												<td align="right" class="td_size">
													�ֿ��ţ�
												</td>
												<td>
													<input type="text" id="warehouseID" name="warehouseID"  />
												</td>
											</tr>
											<tr height="35">
												<td align="right" class="td_size" width="25%">
													<span class="required">*</span>����Ա���룺
												</td>
												<td align="left" width="25%">
													<input id="password" 
														name="password" type="password"
														class="validate[required,custom[password],maxSize[<fmt:message key='userPassword' bundle='${PropsFieldLength}'/>]] " />
												</td>
												<td align="left" colspan="2" width="*">
													<div class="onfocus">
														����Ϊ�գ�
													</div>
												</td>
											</tr>
				
											<tr height="35">
												<td align="right" class="td_size" width="25%">
													<span class="required">*</span>ȷ�����룺
												</td>
												<td align="left" width="25%">
													<input id="password1"  name="password1"
														type="password"
														class="validate[required,equals[password]] " />
												</td>
												<td align="left" colspan="2" width="*">
													<div class="onfocus">
														����Ϊ�գ�
													</div>
												</td>
											</tr>
											<tr height="35" id="mgrIsNeedKeyCode"  style="display: none;">
												<td align="right" class="td_size" width="20%">
													�Ƿ����� KEY ��
												</td>
												<td align="left" colspan="2"><input id="kcodech" type="checkbox" onclick="checkKey(this)"/>
													<span id="showkey" style="display: none;"><span class="required">*</span>
														<input id="kcode" name="entity.keyCode" style="width: 122px;" type="text" value="0123456789ABCDE" readonly="readonly" class="validate[required,custom[onlyLetterNumber]] input_text" />
													</span>
												</td>
											</tr>
				
											<tr height="35">
												<td align="right" class="td_size">
													����Ա���� ��
												</td>
												<td align="left" colspan="2">
													<textarea id="description" name="description" cols="30"
														rows="5" class="validate[maxSize[<fmt:message key='userDescription' bundle='${PropsFieldLength}'/>]]"></textarea>
												</td>
												<td>
													&nbsp;
												</td>
											</tr>
											<tr>
												<td colspan="4" height="5"></td>
											</tr>
										</table>
									</td>
								</tr>
								</c:if>
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
							<rightButton:rightButton name="���" onclick="" className="btn_sec"
								action="${basePath}/stock/warehouse/add.action" id="add"></rightButton:rightButton>
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
<script type="text/javascript" src="${basePath}/mgr/public/js/keycode.js"></script>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>