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
				var vaild = window.confirm("您确定要操作吗？");
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
	
 //加载省
 function loadProvince()
 { 
  //获取省所对应的列表框对象
  var pro=document.getElementById("province");
  //获取已经存在省份的值
  var proValue=document.getElementById("province").value;
  //从数组中提取省份信息
  for(var p in provinces)
  {
   //创建option元素
   var opt=document.createElement("option");
   //设置option元素中的信息
   opt.innerText=p;
   opt.value=p;
   //将option元素添加到select元素中(option是select的子节点)
   pro.appendChild(opt);
   if(proValue == p){
	   opt.selected=true;
   }
  }
 }
 //加载市
 function loadCity(val)
 {
  //获取用户的省份
  var selectProvince=val;
  //根据省获取市(citys是一个数组)
  var citys=provinces[selectProvince];
  //获取市所对应的列表框对象
  var city=document.getElementById("city");
  var cityValue=document.getElementById("city").value;
  //将元素列表框中的元素全部清空
  city.innerText="";
  
  var opt=document.createElement("option");
  opt.innerText="--请选择城市--";
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
 //在窗口加载时添加省份信息
 //window.onload=loadProvince;
</script>
	<head>
		<title>仓库修改</title>
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
												温馨提示 :修改仓库信息,仓库名称必须是仓库全称
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												仓库信息
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div class="div_tj">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<tr height="20">
													<td align="right" width="25%">
														编号:
													</td>
													<td width="25%">
															${entity.id }
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														<span class="required">*</span>仓库编号:
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
														<span class="required">*</span>仓库名称:
													</td>
													<td width="25%" >
														<input type="text" id="warehouseName"
															class="validate[required],maxSize[64],maxSize[<fmt:message key='warehouseName' bundle='${PropsFieldLength}'/>]" name="entity.warehouseName"
															value="${entity.warehouseName }" />
													</td>
													<td align="right" width="25%">
														<span class="required">*</span>仓库状态:
													</td>
													<td width="*">
														<select id="status" name="entity.status" class="validate[required]" style="width: 130px;">
															<option value="0" <c:if test="${entity.status ==0}">selected="selected"</c:if>>可用</option>
															<option value="1"	<c:if test="${entity.status ==1}">selected="selected"</c:if>>不可用</option>
														</select>
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														仓库归属单位：
													</td>
													<td width="25%">
														<input type="text" id="ownershipUnits"
															class="validate[maxSize[<fmt:message key='ownershipUnits_q' bundle='${PropsFieldLength}'/>]]" name="entity.ownershipUnits"
															value="${entity.ownershipUnits}" />
													</td>
													<td align="right" width="25%">
														<span class="required">*</span>仓库等级：
													</td>
													<td width="*">
														<select id="rank"
															class="validate[required]" name="entity.rank" style="width: 133px;">
															<option value="1" <c:if test="${entity.rank==1}">selected='selected'</c:if>>一星级 </option>
															<option value="2" <c:if test="${entity.rank==2}">selected='selected'</c:if>>二星级</option>
															<option value="3" <c:if test="${entity.rank==3}">selected='selected'</c:if>>三星级 </option>
															<option value="4" <c:if test="${entity.rank==4}">selected='selected'</c:if>>四星级</option>
															<option value="5" <c:if test="${entity.rank==5}">selected='selected'</c:if>>五星级</option>
														</select>
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														<span class="required">*</span>注册资本：
													</td>
													<td width="25%">
														<input type="text" id="registeredCapital"
															class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]" name="entity.registeredCapital"
															value="<fmt:formatNumber value='${entity.registeredCapital}' pattern='###0.00'/>"  />
													</td>
													<td align="right" width="25%">
														<span class="required">*</span>投资总额：
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
												联系信息
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div class="div_tj">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<tr height="20">
													<td align="right" width="25%">
														仓库地址：
													</td>
													<td width="*" colspan="3">
														<input type="text" id="address" style="width: 350px;"
															class="validate[maxSize[<fmt:message key='address_q' bundle='${PropsFieldLength}' />]]" name="entity.address"
															value="${entity.address}" />
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														仓库坐标：
													</td>
													<td width="*" colspan="3">
														<input type="text" id="coordinate"  style="width: 350px;"
															class="validate[maxSize[<fmt:message key='coordinate_q' bundle='${PropsFieldLength}' />]]" name="entity.coordinate"
															value="${entity.coordinate}" />
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														省份：
													</td>
													<td width="25%">
														<select id="province" name="entity.province" onchange="loadCity(this.value);" style="width: 133px;" value="${entityt.province}">
														   <option value="0">--请选择省份--</option>
														   <option value="${entity.province}" <c:if test="${entity.province !=''}">selected='selected'</c:if> ></option>
														 </select>
													</td>
													<td align="right" width="25%">
														市：
													</td>
													<td width="25%">
														 <select id="city" name="entity.city" style="width: 133px;" value="${entity.city}">
														  <option value="">--请选择城市--</option>
														  <option value="${entity.city}" <c:if test="${entity.city !=''}">selected='selected'</c:if> ></option>
														 </select>
													</td>
												</tr>
												<tr>
													<td align="right" width="25%">
														与交易中心签订协议日期：
													</td>
													<td width="25%">
														<input type="text" class="wdate" id="agreementDate" style="width: 133px;"
																	name="entity.agreementDate" value="<fmt:formatDate value='${entity.agreementDate}' pattern='yyyy-MM-dd' />"
																	onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />
													</td>
													<td align="right">法人：</td>
													<td>
														<input type="text" id="corporateRepresentative" name="entity.corporateRepresentative" class="validate[maxSize[<fmt:message key='corporateRepresentative_q' bundle='${PropsFieldLength}' />]]" value="${entity.corporateRepresentative}"/>
													</td>
												</tr>
												<tr>
													<td align="right">法人联系电话：</td>
													<td>
														<input type="text" id="representativePhone" name="entity.representativePhone" class="validate[maxSize[<fmt:message key='representativePhone_q' bundle='${PropsFieldLength}' />]]" value="${entity.representativePhone}"/>
													</td>
													<td align="right">业务联系人：</td>
													<td>
														<input type="text" id="contactMan" name="entity.contactMan" class="validate[maxSize[<fmt:message key='contactMan_q' bundle='${PropsFieldLength}' />]]" value="${entity.contactMan}"/>
													</td>
												</tr>
												<tr>
													<td align="right">业务联系电话：</td>
													<td>
														<input type="text" id="phone" name="entity.phone" class="validate[maxSize[<fmt:message key='phone_q' bundle='${PropsFieldLength}' />]]" value="${entity.phone}"/>
													</td>
													<td align="right">业务联系人手机：</td>
													<td>
														<input type="text" id="mobile" name="entity.mobile" class="validate[maxSize[<fmt:message key='mobile_q' bundle='${PropsFieldLength}' />]]" value="${entity.mobile}"/>
													</td>
												</tr>
												<tr>
													<td align="right">邮编：</td>
													<td>
														<input type="text" id="postcode" name="entity.postcode" class="validate[maxSize[<fmt:message key='postcode_q' bundle='${PropsFieldLength}' />]]" value="${entity.postcode}"/>
													</td>
													<td align="right">传真：</td>
													<td>
														<input type="text" id="fax" name="entity.fax" class="validate[maxSize[<fmt:message key='fax_q' bundle='${PropsFieldLength}' />]]" value="${entity.fax}"/>
													</td>
												</tr>
												<tr>
													<td align="right" width="25%">
														仓储环境：
													</td>
													<td width="*" colspan="3">
														<input type="text" id="environmental"  style="width: 350px;"
															class="validate[maxSize[<fmt:message key='environmental_q' bundle='${PropsFieldLength}' />]]" name="entity.environmental"
															value="${entity.environmental}" />
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														检测条件：
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
												港口信息
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
															$("#hasDockLab").html("码头吨位：");
															$("#dockTonnage").attr("class","validate[custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]")
															$("#dockDailyThroughputSp").html("泊位数量：");
															$("#dockDailyThroughput").attr("class","validate[custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]");
															$("#shipTypeSp").html("停靠船舶类型：");
															
														}else{
															$("#hasDockLab").html("<span class='required'>*</span>码头吨位：");
															$("#dockTonnage").attr("class","validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]")
															$("#dockDailyThroughputSp").html("<span class='required'>*</span>泊位数量：");
															$("#dockDailyThroughput").attr("class","validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]");
															$("#shipTypeSp").html("<span class='required'>*</span>停靠船舶类型：");
															
														}
													}
												</script>
												<tr>
													<td align="right"><span class="required">*</span>是否有码头：</td>
													<td>
														<select id="hasDock" name="entity.hasDock" style="width: 133px;" onchange="getValue(this.value)">
															<option value="0" <c:if test="${entity.hasDock ==0}">selected='selected'</c:if> >有码头 </option>
															<option value="1" <c:if test="${entity.hasDock ==1}">selected='selected'</c:if> >没有码头</option>
														</select>
													</td>
													<td align="right"><div id="hasDockLab"><span class="required">*</span>码头吨位：</div></td>
													<td id="dockTonnageTD">
														<input type="text" id="dockTonnage" name="entity.dockTonnage" class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]"  value="<fmt:formatNumber value='${entity.dockTonnage}' pattern='###0.00'/>"/>
													</td>
												</tr>
												<tr>
													<td align="right" width="25%"><div id="dockDailyThroughputSp"><span class="required">*</span>泊位数量：</div></td>
													<td width="25%">
														<input type="text" id="dockDailyThroughput" name="entity.dockDailyThroughput" class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]"  value="${entity.dockDailyThroughput}" />
													</td>
													<td align="right" width="25%"><div id="shipTypeSp"><span class="required">*</span>停靠船舶类型：</div></td>
													<td width="*">
														<select  id="shipType" name="entity.shipType" style="width: 133px;">
															<option value="0" <c:if test="${entity.shipType==0}">selected='selected'</c:if> >海伦</option>
															<option value="1" <c:if test="${entity.shipType==1}">selected='selected'</c:if> >江轮 </option>
															<option value="2" <c:if test="${entity.shipType==2}">selected='selected'</c:if> >全部</option>
															<option value="3" <c:if test="${entity.shipType==3}">selected='selected'</c:if> >不支持</option>
														</select>
													</td>
												</tr>
												<tr>
													<td align="right"><span class="required">*</span>是否有铁路专用线：</td>
													<td>
														<select  id="hasRailway" name="entity.hasRailway" style="width: 133px;" onchange="changeRail(this.value)">
															<option value="0" <c:if test="${entity.hasRailway==0}">selected='selected'</c:if> >有铁路专线 </option>
															<option value="1" <c:if test="${entity.hasRailway==1}">selected='selected'</c:if> >没有铁路专线 </option>
														</select>
													</td>
													<script>
														function changeRail(val){
															if(val==1){
																$("#div2").html("铁路日装卸能力：");
																$("#railwayDailyThroughput").attr("class","validate[custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]");
																
															}else{
																$("#div2").html("<span class='required'>*</span>铁路日装卸能力：");
																$("#railwayDailyThroughput").attr("class","validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]");
															}
														}
													</script>
													<td align="right"><div id="div2"><span class="required">*</span>铁路日装卸能力：</div></td>
													<td>
														<input type="text" id="railwayDailyThroughput" name="entity.railwayDailyThroughput" class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]" value="<fmt:formatNumber value='${entity.railwayDailyThroughput}' pattern='###0.00'/>" data-prompt-position="topRight: -55 -20;"/>
													</td>
												</tr>
												<tr>
													<td align="right"><span class="required">*</span>是否支持槽车装卸：</td>
													<td>
														<select  id="hasTanker" name="entity.hasTanker" style="width: 133px;" onchange="changeTanker(this.value)">
															<option value="0" <c:if test="${entity.hasTanker==0}">selected='selected'</c:if> >支持</option>
															<option value="1" <c:if test="${entity.hasTanker==1}">selected='selected'</c:if> >不支持 </option>
														</select>
													</td>
													<script>
														function changeTanker(val){
															if(val==1){
																$("#div1").html("发车鹤位数量：");
																$("#tankerDailyThroughput").attr("class","validate[custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]")

															}else{
																$("#div1").html("<span class='required'>*</span>发车鹤位数量：");
																$("#tankerDailyThroughput").attr("class","validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]");
															}
														}
													</script>
													<td align="right"><div id="div1"><span class="required">*</span>发车鹤位数量：</div></td>
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
							<rightButton:rightButton name="修改" onclick="" className="btn_sec"
								action="/stock/warehouse/update.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<button class="btn_sec" onClick=
	window.close();;
>
								关闭
							</button>
						</td>
					</tr>
				</table>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>