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
		//warehouseIsNeedKey：Y表示仓库端后台管理启用key 
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
				var vaild = window.confirm("您确定要操作吗？");
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
	
 //加载省
 function loadProvince()
 { 
  //获取省所对应的列表框对象
  var pro=document.getElementById("province");
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
  }
 }
 //加载市
 function loadCity()
 {
  //获取用户的省份
  var selectProvince=document.getElementById("province").value;
  //根据省获取市(citys是一个数组)
  var citys=provinces[selectProvince];
  //获取市所对应的列表框对象
  var city=document.getElementById("city");
  //将元素列表框中的元素全部清空
  city.innerText="";
  
  var opt=document.createElement("option");
  opt.innerText="--请选择城市--";
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
 //在窗口加载时添加省份信息
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
		<title>仓库添加</title>
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
												温馨提示 :添加仓库信息,仓库名称必须是仓库全称
											</div>
										</div>
									</td>
								</tr>
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
										<div class="div_tj">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<tr height="20">
													<td align="right" width="25%">
														<span class="required">*</span>仓库编号：
													</td>
													<td width="25%">
														<input type="text" id="warehouseId" 
															class="validate[required,custom[onlyLetterNumber],maxSize[30],ajax[checkWarehouseBywarehouseId]]" name="entity.warehouseId"
															value="" />
														<input id="status" type="hidden" name="entity.status" value="0"/>
													</td>
													<td align="left" colspan="2" width="*">
														<div class="onfocus">
															不能为空！
														</div>
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														<span class="required">*</span>仓库名称：
													</td>
													<td width="25%">
														<input type="text" id="warehouseName"
															class="validate[required,maxSize[<fmt:message key='warehouseName' bundle='${PropsFieldLength}' />]]" name="entity.warehouseName"
															value="" />
													</td>
													<td align="left" colspan="2" width="*">
														<div class="onfocus">
															不能为空！
														</div>
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														仓库归属单位：
													</td>
													<td width="25%">
														<input type="text" id="ownershipUnits"
															class="validate[maxSize[<fmt:message key='ownershipUnits_q' bundle='${PropsFieldLength}'/>]]" name="entity.ownershipUnits"
															value="" />
													</td>
													<td align="right" width="25%">
														<span class="required">*</span>仓库等级：
													</td>
													<td width="*">
														<select id="rank"
															class="validate[required]" name="entity.rank" style="width: 133px;">
															<option value="1">一星级 </option>
															<option value="2">二星级</option>
															<option value="3">三星级 </option>
															<option value="4">四星级</option>
															<option value="5">五星级</option>
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
															value="" />
													</td>
													<td align="right" width="25%">
														<span class="required">*</span>投资总额：
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
												联系信息
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div class="div_tj">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<tr height="20">
													<td align="right">
														仓库地址：
													</td>
													<td  colspan="3">
														<input type="text" id="address" style="width: 300px;"
															class="validate[maxSize[<fmt:message key='address_q' bundle='${PropsFieldLength}' />]]" name="entity.address"
															value="" />
													</td>
												</tr>
												<tr height="20">
													<td align="right">
														仓库坐标：
													</td>
													<td width="*" colspan="3">
														<input type="text" id="coordinate" style="width: 300px;"
															class="validate[maxSize[<fmt:message key='coordinate_q' bundle='${PropsFieldLength}' />]]" name="entity.coordinate"
															value="" />
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														省份：
													</td>
													<td width="25%">
														<select id="province" name="entity.province" onchange="loadCity();" style="width: 133px;">
														   <option value="0">--请选择省份--</option>
														 </select>
													</td>
													<td align="right" width="25%">
														城市：
													</td>
													<td width="*">
														 <select id="city" name="entity.city" style="width: 133px;">
														  <option value="">--请选择城市--</option>
														 </select>
													</td>
												</tr>
												<tr>
													<td align="right">
														与交易中心签订协议日期：
													</td>
													<td>
														<input type="text" class="wdate" id="agreementDate" style="width: 133px;"
																	name="entity.agreementDate"
																	onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />
													</td>
													<td align="right">法人：</td>
													<td>
														<input type="text" id="corporateRepresentative" name="entity.corporateRepresentative" class="validate[maxSize[<fmt:message key='corporateRepresentative_q' bundle='${PropsFieldLength}' />]]"/>
													</td>
												</tr>
												<tr>
													<td align="right">法人联系电话：</td>
													<td>
														<input type="text" id="representativePhone" name="entity.representativePhone" class="validate[maxSize[<fmt:message key='representativePhone_q' bundle='${PropsFieldLength}' />]]"/>
													</td>
													<td align="right">业务联系人：</td>
													<td>
														<input type="text" id="contactMan" name="entity.contactMan" class="validate[maxSize[<fmt:message key='contactMan_q' bundle='${PropsFieldLength}' />]]"/>
													</td>
												</tr>
												<tr>
													<td align="right">业务联系电话：</td>
													<td>
														<input type="text" id="phone" name="entity.phone" class="validate[maxSize[<fmt:message key='phone_q' bundle='${PropsFieldLength}' />]]"/>
													</td>
													<td align="right">业务联系人手机：</td>
													<td>
														<input type="text" id="mobile" name="entity.mobile" class="validate[maxSize[<fmt:message key='mobile_q' bundle='${PropsFieldLength}' />]]"/>
													</td>
												</tr>
												<tr>
													<td align="right">邮编：</td>
													<td>
														<input type="text" id="postcode" name="entity.postcode" class="validate[custom[number1],maxSize[<fmt:message key='postcode_q' bundle='${PropsFieldLength}' />]]" />
													</td>
													<td align="right">传真：</td>
													<td>
														<input type="text" id="fax" name="entity.fax" class="validate[custom[fax],maxSize[<fmt:message key='fax_q' bundle='${PropsFieldLength}' />]]"/>
													</td>
												</tr>
												<tr height="20">
													<td align="right">
														仓储环境：
													</td>
													<td colspan="3">
														<input type="text" id="environmental" style="width: 300px;"
															class="validate[maxSize[<fmt:message key='environmental_q' bundle='${PropsFieldLength}' />]]" name="entity.environmental"
															value="" />
													</td>
												</tr>
												<tr height="20">
													<td align="right">
														检测条件：
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
												港口信息
											</div>
											<div class="div_cxtjR"></div>
										</div><%--<input type="checkbox" id="isPost" checked="checked" onclick="getChecked()"/>（是否有港口信息）
										--%><div style="clear: both;"></div>
										<div class="div_tj" id="postMessage">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<script>
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
													<td align="right" width="25%"><span class="required">*</span>是否有码头：</td>
													<td width="25%">
														<select id="hasDock" name="entity.hasDock" style="width: 133px;" onchange="getValue(this.value)">
															<option value="0">有码头 </option>
															<option value="1">没有码头</option>
														</select>
													</td>
													
													<td align="right" width="25%"><div id="hasDockLab"><span class="required">*</span>码头吨位：</div></td>
													<td id="dockTonnageTD"  width="*">
														<input type="text" id="dockTonnage" name="entity.dockTonnage" class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]"/>
													</td>
												</tr>
												<tr>
													<td align="right"  width="25%"><div id="dockDailyThroughputSp"><span class="required">*</span>泊位数量：</div></td>
													<td  width="25%">
														<input type="text" id="dockDailyThroughput" name="entity.dockDailyThroughput" class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]" />
													</td>
												
													<td align="right"  width="25%"><div id="shipTypeSp"><span class="required">*</span>停靠船舶类型：</div></td>
													<td  width="*">
														<select  id="shipType" name="entity.shipType" style="width: 133px;">
															<option value="0">海伦</option>
															<option value="1">江轮 </option>
															<option value="2">全部</option>
															<option value="3">不支持</option>
														</select>
													</td>
												</tr>
												<tr>
													<td align="right"><span class="required">*</span>是否有铁路专用线：</td>
													<td>
														<select  id="hasRailway" name="entity.hasRailway" style="width: 133px;" onchange="changeRail(this.value)">
															<option value="0">有铁路专线 </option>
															<option value="1">没有铁路专线 </option>
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
														<input type="text" id="railwayDailyThroughput" name="entity.railwayDailyThroughput" class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]" data-prompt-position="topRight: -45 -20;"/>
													</td>
												</tr>
												<tr>
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
													<td align="right"><span class="required">*</span>是否支持槽车装卸：</td>
													<td>
														<select  id="hasTanker" name="entity.hasTanker" style="width: 133px;" onchange="changeTanker(this.value)">
															<option value="0">支持</option>
															<option value="1">不支持 </option>
														</select>
													</td>
													<td align="right"><div id="div1"><span class="required">*</span>发车鹤位数量：</div></td>
													<td>
														<input type="text" id="tankerDailyThroughput" name="entity.tankerDailyThroughput" class="validate[required,custom[doubleCus],max[<fmt:message key='maxValue' bundle='${PropsFieldLength}' />]]" data-prompt-position="bottomLeft: 20,5;"/>
														</select>
													</td>
													
												</tr>
												
											</table>
										</div>
									</td>
								</tr>
								<!-- 无仓版环境下添加仓库超级管理员 -->
								<c:if test="${haveWarehouse==0}">
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												仓库管理员信息
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
											<tr height="35">
												<td align="right" class="td_size" width="25%">
													<span class="required">*</span>用户名：
												</td>
												<td align="left" width="25%">
													<input id="userId"  name="userId"
														type="text"
														class="validate[required,custom[onlyLetterNumber],maxSize[<fmt:message key='userID' bundle='${PropsFieldLength}'/>],ajax[checkWuserByUserId]] " />
												</td>
												<td align="left" colspan="2" width="*">
													<div class="onfocus">
														不能为空！
													</div>
												</td>
											</tr>
											<tr height="35">
												<td align="right" class="td_size" width="25%">
													<span class="required">*</span>管理员名称：
												</td>
												<td align="left" width="25%">
													<input id="name"  name="name"
														type="text" class="validate[required,maxSize[<fmt:message key='userName' bundle='${PropsFieldLength}'/>]] " />
												</td>
												<td align="left" colspan="2" width="*">
													<div class="onfocus">
														不能为空！
													</div>
												</td>
											</tr>
											<input type="hidden" id="type" name="type"/>
											<tr style="display:none;">
												<td align="right" class="td_size">
													仓库编号：
												</td>
												<td>
													<input type="text" id="warehouseID" name="warehouseID"  />
												</td>
											</tr>
											<tr height="35">
												<td align="right" class="td_size" width="25%">
													<span class="required">*</span>管理员密码：
												</td>
												<td align="left" width="25%">
													<input id="password" 
														name="password" type="password"
														class="validate[required,custom[password],maxSize[<fmt:message key='userPassword' bundle='${PropsFieldLength}'/>]] " />
												</td>
												<td align="left" colspan="2" width="*">
													<div class="onfocus">
														不能为空！
													</div>
												</td>
											</tr>
				
											<tr height="35">
												<td align="right" class="td_size" width="25%">
													<span class="required">*</span>确认密码：
												</td>
												<td align="left" width="25%">
													<input id="password1"  name="password1"
														type="password"
														class="validate[required,equals[password]] " />
												</td>
												<td align="left" colspan="2" width="*">
													<div class="onfocus">
														不能为空！
													</div>
												</td>
											</tr>
											<tr height="35" id="mgrIsNeedKeyCode"  style="display: none;">
												<td align="right" class="td_size" width="20%">
													是否启用 KEY ：
												</td>
												<td align="left" colspan="2"><input id="kcodech" type="checkbox" onclick="checkKey(this)"/>
													<span id="showkey" style="display: none;"><span class="required">*</span>
														<input id="kcode" name="entity.keyCode" style="width: 122px;" type="text" value="0123456789ABCDE" readonly="readonly" class="validate[required,custom[onlyLetterNumber]] input_text" />
													</span>
												</td>
											</tr>
				
											<tr height="35">
												<td align="right" class="td_size">
													管理员描述 ：
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
							<rightButton:rightButton name="添加" onclick="" className="btn_sec"
								action="${basePath}/stock/warehouse/add.action" id="add"></rightButton:rightButton>
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
<script type="text/javascript" src="${basePath}/mgr/public/js/keycode.js"></script>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>