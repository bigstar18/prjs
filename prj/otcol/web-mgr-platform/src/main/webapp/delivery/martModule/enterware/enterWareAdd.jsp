<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>

<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
	<title></title>
	<style type="text/css">
	<!--
	.cd_bt {
		font-size: 12px;
		line-height: 16px;
		font-weight: bold;
		text-decoration: none;
		text-align: right;
		padding-right: 5px;
	}
	-->
	
    </style>

</head>
	
<body>
<form name="frm" action="<%=basePath%>servlet/enterWareController.wha?funcflg=enterWareAdd" method="post">
	<input type="hidden" name="operate" id="operate" value="${value}">
	<input type="hidden" name="existAmount" id="existAmount" value="">
	<fieldset width="100%">
	<legend>商品入库单</legend>
			<table width="96%" border="0" align="center" cellpadding="0"
				cellspacing="0" bordercolor="#333333"
				style="border-collapse:collapse;">
				<tr height="40px">
					<td colspan="5" class="cd_hr">
						<br>
						&nbsp;<font color=red></font>
					</td>
				</tr>
			</table>
			<table width="96%" border="1" align="center" cellpadding="0px"
				cellspacing="0" bordercolor="#333333"
				style="border-collapse:collapse;">
				<tr height="36px"  >
					<td  width="15%"class="cd_bt" >
						入库通知单号：
					</td>
				
					<td  width="25%"class="cd_list1">
						&nbsp;<input name="enterInformId" id="enterInformId" value="" onblur="enterApplyInformAjaxChange();"  type="text" class="form_k1" >
						<font color="red"><span id="enterInformflag"></span></font>
					</td>
					<td class="cd_bt">
						入库时间：
					</td>
					<td class="cd_list1" colspan="3">
						<MEBS:calendar eltID="enterDate" eltName="enterDate" eltCSS="date" eltStyle="width:120px" eltImgPath="<%=skinPath%>/images/" eltValue="<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy-MM-dd" />"/>&nbsp;<font color="red">*</font>					</td>
					</td>
				</tr>
				<tr height="36px">
					<td width="15%" class="cd_bt">
						${FIRMID}：
					</td>
					<td width="25%" class="cd_list1">
						&nbsp;<input type="text" name="firmId" id="firmId" value=""  class="form_k1" onchange="firmIdajaxChange();" onkeyup="this.value=this.value.replace(/\W/g,'')"><font color="red"><span id="firmflag"></span></font>
						<font color="red">*<span id="firmIdFlag"></span></font></div>
					</td>
					<td class="cd_bt">
						${FIRMNAME}：
					</td>
					<td class="cd_list1" colspan=3>
					<div align="left">
							&nbsp;<input name="firmName" id="firmName" value="" type="text" class="form_k1" readonly>
						</div>
					</td>
					
				</tr>
				<tr height="36px">
					<td width="20%" class="cd_bt">
						仓库名称：
					</td>
					<td width="30%" class="cd_list1">
							&nbsp;<select id="warehouseId" name="warehouseId" style="width:150" class="form_k" onchange="warehouseajaxChange();">
									<option value="">请选择</option>
									<c:forEach items="${warehouseList}" var="result">
		              			  <option value="${result.id}">${result.name}</option>
		              			  </c:forEach>
							</select>
					</td>
				
					<td width="" class="cd_bt">
						品种名称：
					</td>
				
					<td width="" class="cd_list1" colspan=3>
							<div align="left">
						&nbsp;<select id="commodityId" name="commodityId" style="width:150" class="form_k" onchange="commodityajaxChange();">
									<option value="">请选择</option>
				
							</select>
							</div>
					</td>
				</tr>
				<tr height="36px">
				
					
						<td width="20%" class="cd_bt">
						等级：
					</td>
					<td width="30%"class="cd_list1">
						<div align="left">
							&nbsp;<select id="grade" name="grade" style="width:150" class="form_k" >
									<option value="">请选择</option>
							</select>
						</div>
					</td>
					
					<td width="" class="cd_bt">
							生产厂家或产地：
					</td>
					<td width="" colspan=3 class="cd_list1">
						<div align="left">
							&nbsp;<select id="origin" name="origin" style="width:150" class="form_k">
									<option value="">请选择</option>
							</select>
						</div>
					</td>
				</tr>
				<tr height="36px">
			<td width="" class="cd_bt">
							种类：
					</td>
					<td width=""  class="cd_list1">
							<div align="left">
							&nbsp;<select id="sort" name="sort" style="width:150" class="form_k">
									<option value="">请选择</option>
									
							</select>
						</div>
					</td>
					<td width="" class="cd_bt">
						批号：
					</td>
					<td width="" class="cd_list1" >
						&nbsp;<input name="lot" id="lot" value="" class="form_k1" style="width:150" >
					</td>
					<td width="" class="cd_bt">
						生产年月：
					</td>
					<td width="" class="cd_list1" >
					
						&nbsp;<input name="productionDate" id="productionDate" value="" class="form_k1" style="width:150" onkeypress="onlyDate()" >
					</td>
				</tr>
				
					<tr height="36px">
						<td class="cd_bt">入库重量：</td>
						<td class="cd_list1" >
							&nbsp;<input name="weight" id="weight" type="text" value="" class="form_k1" onblur="weight_blur()"><span id="unit1"></span >
						</td>
						<td class="cd_bt">件&nbsp;&nbsp;&nbsp;&nbsp;数：	</td>
						<td class="cd_list1" >
							&nbsp;<input name="quantity" id="quantity" type="text"style="width:150" value="" reqfv="required;件数" class="form_k1" readonly="readonly">
						</td>
						<td class="cd_bt" >货 位 号：</td>
						<td class="cd_list1">
							&nbsp;<input name="cargoNo"style="width:150" reqfv="required;货位号" type="text" value="" class="form_k1">
						</td>
					</tr>

				<tr height="36px">
						<td class="cd_bt">每件重量：</td>
						<td class="cd_list1" >
							&nbsp;<input name="unitWeight" id="unitWeight" reqfv="required;每件重量" type="text" value="" class="form_k1" onblur="unitWeight_blur()"><span  id="unit2"></span>
						</td>
						<td class="cd_bt">包装方式：	</td>
						<td class="cd_list1" colspan=3>
							&nbsp;<input name="packaging" id="packaging" reqfv="required;包装方式" type="text" value="" style="width:150" class="form_k1">
						</td>
				 </tr>
		</table>
		
		<div align="right" >
			<font color="red" size="2">说明：件数是由公式 件数=入库重量/每件重量 得出的，结果舍掉小数，不四舍五入</font> 
        </div>
		<br>
		
			<table width="96%" border="0" align="center" cellpadding="0"
				cellspacing="0" bordercolor="#333333"
				style="border-collapse:collapse;">
				<tr height="40px">
					<td id="qn" colspan="5" align="center" class="cd_hr">

					</td>
				</tr>
			</table>
			<BR>
				<table width="96%" id="quality" border="1" align="center"
				cellpadding="0px" cellspacing="0" bordercolor="#333333"
				style="border-collapse:collapse;">

				</table>
				<br><br><br><br>
		<table width="96%" border="0" align="center" cellpadding="0px"
				cellspacing="0" bordercolor="#333333"
				style="border-collapse:collapse;">
				<tr height="25px">
					<td width="20%" class="cd_bt" align="right">
						仓库经办人：
					</td>
					<td width="20%" class="cd_list1" align="left" >
						<input name="agency" type="text"
							value="" class="form_kr"
							reqfv="required;仓库经办人" size=15>
					</td>
					<td width="20%" class="cd_bt" align="right">
						仓库负责人：
					</td>
					<td width="10%" class="cd_list1"align="left">
						<input name="responsibleman" type="text"
							value="" class="form_kr"
							reqfv="required;仓库负责人" size=15 >
					</td>
					<td width="20%" class="cd_bt" align="right">
						交易商经办人：
					</td>
					<td width="10%" class="cd_list1"align="left">
						<input name="dealerAgency" type="text"
							value="" class="form_kr"
							reqfv="required;交易商经办人" size=15>
					</td>
				</tr>
			</table>
		<br>
		<div align="center">
		  <input class="mdlbtn" type="button" onclick="doSubmit();" value="添加入库单" />
		  <input class="smlbtn" type="button" value="返回" onclick="freturn();"/>
           </div>
	</fieldset>
	
</form>
</body>
</html>

<SCRIPT LANGUAGE="JavaScript">

	function weight_blur(){
		if(frm.weight.value != "" && !IsIntOrFloat(frm.weight.value)){
			alert("入库重量应为大于0数字");
			frm.weight.value = "";
			return false;
		}
		if (frm.weight.value != "" && frm.unitWeight.value != "") {
			frm.quantity.value = Math.floor(frm.weight.value/frm.unitWeight.value);
		}
	}
	function unitWeight_blur(){
		if(frm.unitWeight.value != "" && !IsIntOrFloat(frm.unitWeight.value)){
			alert("每件重量应为大于0数字");
			frm.unitWeight.value = "";
			return false;
		}
		if (frm.weight.value != "" && frm.unitWeight.value != "") {
			frm.quantity.value = Math.floor(frm.weight.value/frm.unitWeight.value);
		}
	}	
		
	function doSubmit()
	{
	
	    if(frm.enterInformId.value==""){
			frm.enterInformId.focus();
			alert("入库通知单号不能为空");
			return false;
		}
		if(frm.firmId.value==""){
			frm.firmId.focus();
			alert("交易商不能为空");
			return false;
		}
		if(frm.commodityId.value==""){
			frm.commodityId.focus();
			alert("请选择品种名称");
				return false;
		}
		if(frm.grade.value==""){
			frm.grade.focus();
			alert("请选择产品的等级");
			return false;
		}
		if(frm.origin.value==""){
			frm.origin.focus();
			alert("生产厂家不能为空");
			return false;
		}
		if(frm.lot.value==""){
			frm.lot.focus();
			alert("批号不能为空");
			return false;
		}
		if(frm.productionDate.value==""){
			frm.productionDate.focus();
			alert("请选择生产年月");
			return false;
		}
		if(frm.weight.value==""){
			frm.weight.focus();
			alert("入库重量不能为空");
			return false;
		}
		if(frm.unitWeight.value==""){
			frm.unitWeight.focus();
			alert("每件重量不能为空");
				return false;
		}
		if(frm.cargoNo.value==""){
			frm.cargoNo.focus();
			alert("货位号不能为空");
			return false;
		}
		if(frm.packaging.value==""){
			frm.packaging.focus();
			alert("包装方式不能为空");
			return false;
		}
		if(document.frm.enterDate.value==""){
			alert("入库时间不能为空");
			return;
		}
		if(frm.agency.value==""){
			frm.agency.focus();
			alert("仓库经办人不能为空");
			return false;
		}
		if(frm.responsibleman.value==""){
			frm.responsibleman.focus();
			alert("仓库负责人不能为空");
			return false;
		}
		if(frm.dealerAgency.value==""){
			frm.dealerAgency.focus();
			alert("交易商经办人不能为空");
			return false;
		}
	
		if(frm.qualityParameter!=null){
			var qualityParameter = document.getElementsByName("qualityParameter");
			var qualityName = document.getElementsByName("qualityName");
			for(var i=0;i<qualityParameter.length;i++){
				if(qualityParameter[i].value==null || qualityParameter[i].value ==""){
					alert(qualityName[i].value+"不能为空");
					return false;
				}
			}
		}
		if(confirm("确定执行此操作？")){
			document.getElementById("existAmount").value =document.getElementById("weight").value ;
			frm.submit();
		}
		
	}
	
	
	function enterApplyInformAjaxChange()
	{
		//清空单位处的痕迹  add by yangpei
		$("#unit1").html("");
		$("#unit2").html("");
		
		var enterInformId = $("#enterInformId").val();
		var conditionName = "1";
		var conditionValue = "1";
		$.ajax({   
  				  type : "POST",     //HTTP 请求方法,默认: "GET"   
  				  url : "<%=basePath%>servlet/ajaxController.${POSTFIX}?funcflg=getEnterApplyInform",   //发送请求的地址   
  				  data : "enterInformId=" + enterInformId + "&conditionName=" + conditionName + "&conditionValue" + conditionValue, //发送到服务器的数据   
  				  dataType : "xml",         //预期服务器返回的数据类型   
  				  error: function(xml) { alert('Error loading XML document:' +xml); }, 
  				  success : function (data) {   
			var result = $(data).find("result").text();
			
			if(result==1){
			    $("#enterInformflag").html("");
				$("#firmId").val($(data).find("firmId").text());
				$("#firmId").attr("readonly","readonly");
				$("#firmName").val($(data).find("firmName").text());
				$("#firmName").attr("readonly","readonly");
				$("#warehouseId").attr("value",$(data).find("warehouseId").text());
				$("#commodityId").attr("value",$(data).find("commodityId").text());
				
				$("#origin").empty();
				$("#grade").empty();
				$("#sort").empty();
				$("#commodityId").empty();
				$("#warehouseId").empty();
				$("<option value='"+$(data).find("warehouseId").text()+"'>"+$(data).find("warehouseName").text()+"</option>").appendTo($("#warehouseId"));
				$("<option value='"+$(data).find("commodityId").text()+"'>"+$(data).find("commodityName").text()+"</option>").appendTo($("#commodityId"));

				 $(data).find("sort").each(function(i){
					var sc= "<option value='"+$(data).find("sort").eq(i).text();
					if($(data).find("sort").eq(i).text()==$(data).find("chooseSort").text()){
						sc += "' selected>"+$(data).find("sort").eq(i).text()+"</option>"
						$(sc).appendTo($("#sort"));
					}
  					 });
  					
				 $(data).find("origin").each(function(i){
					var oc = "<option value='"+$(data).find("origin").eq(i).text();
					if($(data).find("origin").eq(i).text()==$(data).find("chooseOrigin").text()){
						oc += "' selected>"+$(data).find("origin").eq(i).text()+"</option>";
   				 		$(oc).appendTo($("#origin"));
   				 	}
  					 });
  					 	
  					  $(data).find("grade").each(function(i){
					var gc = "<option value='"+$(data).find("grade").eq(i).text();	
					if($(data).find("grade").eq(i).text()==$(data).find("chooseGrade").text()){
						gc+="' selected>"+$(data).find("grade").eq(i).text()+"</option>"
						$(gc).appendTo($("#grade"));
  				 		}
  					 });

		   		$("#lot").val($(data).find("lot").text());
		   		$("#lot").attr("readonly","readonly");
		   		$("#productionDate").val($(data).find("productionDate").text());
		  		$("#productionDate").attr("readonly","readonly");
		   		$("#weight").val($(data).find("weight").text());
		   		$("#weight").attr("readonly","readonly");
		   		$("#unitWeight").val($(data).find("unitWeight").text());
		   		$("#unitWeight").attr("readonly","readonly");
		   		$("#quantity").val($(data).find("quantity").text());
		   		$("#quantity").attr("readonly","readonly");
		   		$("#packaging").val($(data).find("packaging").text());
		   		$("#packaging").attr("readonly","readonly");
		   		if($(data).find("countType").text()!='null'){
			   		$("#unit1").html("("+$(data).find("countType").text()+")");
			   		$("#unit2").html("("+$(data).find("countType").text()+")");
		   		}
		   		
		   		var num = Math.floor($(data).find("quality").length/4);
				var count = $(data).find("quality").length%4;
				var qua=new Array($(data).find("quality").length);
				$(data).find("quality").each(function(i){
					qua[i] = $(data).find("quality").eq(i).text();
				});
				var str ="";
				var i=0;
				var j=0;
			
	
				$("#qn").html("质量标准");
				$("#quality").empty();
				for(i=0;i<num;i++){
					str="";
					str=str+"<tr height='25px'>";
					for(j=0;j<4;j++){
	
						str=str+"<td  class='cd_list1'>"+qua[i*4+j]+"</td><td class='cd_list1'><input type='hidden' name='qualityName' value='"+qua[i*4+j]+"'><input name='qualityParameter' type='text' size=10 class='form_kr1' style='width:80' ></td>";
					}
					str=str+"<tr>";
					$(str).appendTo($("#quality"));
				}
				var len;
				if(num!=0){
					i--;
					j--;
					len = i*4+j;
				}else{
					len = j-1;
				}
	
	
				str="<tr height='25px'>";
				for(var m=0;m<count;m++)
				{
					str=str+"<td class='cd_list1'>"+qua[len+1+m]+"</td><td  class='cd_list1'><input type='hidden' name='qualityName' value='"+qua[len+1+m]+"'><input name='qualityParameter' type='text' size=10 class='form_kr1' style='width:80' ></td>";			
				}
				if(count==1){
					len=6;
				}else if(count==2){
					len=4;
				}else if(count==3){
					len=2;
				}
				if(num!=0){
					str=str+"<td class='cd_bt' colspan="+len+" >&nbsp</td>";
				}
					str=str+"<tr>";
			
				$(str).appendTo($("#quality"));
			
			}else if(result ==0 ){
				$("#enterInformId").val("");
				$("#qn").html("");
				$("#quality").empty();
				$("#quality").attr("readonly","");
			    $("#enterInformflag").html("此通知单状态不符");
				$("#lot").val("");
				$("#lot").attr("readonly","");
		   		$("#productionDate").val("");
		   		$("#productionDate").attr("readonly","");
		   		$("#weight").val("");
		   		$("#weight").attr("readonly","");
		   		$("#unitWeight").val("");
		   		$("#unitWeight").attr("readonly","");
		   		$("#quantity").val("");
		 	 	$("#quantity").attr("readonly",""); 		
		   		$("#packaging").val("");
		   	 	$("#packaging").attr("readonly",""); 		
		   		$("#firmId").val("");
		   		$("#firmId").attr("readonly",""); 		
				$("#firmName").val("");
				$("#firmName").attr("readonly",""); 		
			
				$("#unit1").html("");
		   		$("#unit2").html("");
				$("#origin").empty();
				$("#grade").empty();
				$("#sort").empty();
				$("#warehouseId").empty();
				$("#commodityId").empty();
				$("#cargoNo").val("")
				$("#responsibleman").val("")
				$("#agency").val("")
				$("#dealerAgency").val("")
				var plzz = "<option value=''>请选择</option>";
				$(plzz).appendTo($("#warehouseId"));
				$(plzz).appendTo($("#commodityId"));
				$(plzz).appendTo($("#sort"));
				$(plzz).appendTo($("#grade"));
				$(plzz).appendTo($("#origin"));
				
				<c:forEach items="${warehouseList}" var="result">
             		var	wo="<option value='${result.id}'>${result.name}</option>";
            			$(wo).appendTo($("#warehouseId"));
           				</c:forEach>
			}else {
				$("#enterInformId").val("");
				$("#qn").html("");
				$("#quality").empty();
				$("#quality").attr("readonly","");
			    $("#enterInformflag").html("此通知单不存在");
				$("#lot").val("");
				$("#lot").attr("readonly","");
		   		$("#productionDate").val("");
		   		$("#productionDate").attr("readonly","");
		   		$("#weight").val("");
		   		$("#weight").attr("readonly","");
		   		$("#unitWeight").val("");
		   		$("#unitWeight").attr("readonly","");
		   		$("#quantity").val("");
		 	 	$("#quantity").attr("readonly",""); 		
		   		$("#packaging").val("");
		   	 	$("#packaging").attr("readonly",""); 		
		   		$("#firmId").val("");
		   		$("#firmId").attr("readonly",""); 		
				$("#firmName").val("");
				$("#firmName").attr("readonly",""); 		
			
				$("#unit1").html("");
		   		$("#unit2").html("");
				$("#origin").empty();
				$("#grade").empty();
				$("#sort").empty();
				$("#warehouseId").empty();
				$("#commodityId").empty();
				$("#cargoNo").val("")
				$("#responsibleman").val("")
				$("#agency").val("")
				$("#dealerAgency").val("")
				var plzz = "<option value=''>请选择</option>";
				$(plzz).appendTo($("#warehouseId"));
				$(plzz).appendTo($("#commodityId"));
				$(plzz).appendTo($("#sort"));
				$(plzz).appendTo($("#grade"));
				$(plzz).appendTo($("#origin"));
				
				<c:forEach items="${warehouseList}" var="result">
             		var	wo="<option value='${result.id}'>${result.name}</option>";
            			$(wo).appendTo($("#warehouseId"));
           				</c:forEach>
			}
			
			
		}        //请求成功后回调函数   
				 });   
 
	}

	function firmIdajaxChange()
	{
		var firmId = $("#firmId").val();
   		$.ajax({   
    				  type : "POST",     //HTTP 请求方法,默认: "GET"   
    				  url : "<%=basePath%>servlet/ajaxController.${POSTFIX}?funcflg=getFirm",   //发送请求的地址   
    				  data : "firmId=" + firmId, //发送到服务器的数据   
    				  dataType : "xml",         //预期服务器返回的数据类型   
    				  error: function(xml) { alert('Error loading XML document:' +xml); }, 
    				  success : function(data){
    				  			var result = $(data).find("result").text();
				      		    if(result==-1)
								{
								  $("#firmId").val("");
								  $("#firmName").val("");
								  $("#firmIdFlag").html("没有该交易商!")
								}else{
									var firmName = $(data).find("name").text();
									  $("#firmIdFlag").html("")
									  if(name !='null'){
									  $("#firmName").val(firmName);
									  }else{
								      $("#firmName").val("");
									  }
								}
    				  			}        //请求成功后回调函数   
 					 });   
	  
	}
			
	function warehouseajaxChange()
	{
		var warehouseId = $("#warehouseId").val();
		if(warehouseId=="" || warehouseId==null)
			return;
   		$.ajax({   
  				  type : "POST",     //HTTP 请求方法,默认: "GET"   
  				  url : "<%=basePath%>servlet/ajaxController.${POSTFIX}?funcflg=getWarehouse",   //发送请求的地址   
  				  data : "warehouseId=" + warehouseId, //发送到服务器的数据   
  				  dataType : "xml",         //预期服务器返回的数据类型   
  				  error: function(xml) { alert('Error loading XML document:' +xml); }, 
  				  success : function(data){
  				  				$("#commodityId").empty();
  				  				if($(data).find("result").text()=="1"){
  				  					$("<option value=''>请选择</option>").appendTo($("#commodityId"));
									$(data).find("warehousecommodityid").each(function(i){
				 				 	$("<option value='"+$(data).find("warehousecommodityid").eq(i).text()+"'>"+$(data).find("warehousecommodityname").eq(i).text()+"</option>").appendTo($("#commodityId"));
								 });
								}else{
						$("<option value=''>请选择</option>").appendTo($("#commodityId"));	   			
								}
  				  			}        //请求成功后回调函数   
				 });   
	  
	}
			
			
			
	function commodityajaxChange()
	{
		
		var commodityId = $("#commodityId").val();
		$.ajax({   
    				  type : "POST",     //HTTP 请求方法,默认: "GET"   
    				  url : "<%=basePath%>servlet/ajaxController.${POSTFIX}?funcflg=getCommodity",   //发送请求的地址   
    				  data : "commodityId=" + commodityId, //发送到服务器的数据   
    				  dataType : "xml",         //预期服务器返回的数据类型   
    				  error: function(xml) { alert('Error loading XML document:' +xml); }, 
    				  success : function (data) {   
					
					
							$("#origin").empty();
    				  				$("#sort").empty();
    				  				$("#grade").empty();      				  				
    				  				
					   			$(data).find("origin").each(function(i){
				   				 	
				   				 	$("<option value='"+$(data).find("origin").eq(i).text()+"'>"+$(data).find("origin").eq(i).text()+"</option>").appendTo($("#origin"));
				   				 });
					   			
				   						   				 
				   				  $(data).find("grade").each(function(i){
				   				 	
				   				 	$("<option value='"+$(data).find("grade").eq(i).text()+"'>"+$(data).find("grade").eq(i).text()+"</option>").appendTo($("#grade"));
				   				 });
				   				 	 $(data).find("sort").each(function(i){
				   				 	
				   				 	$("<option value='"+$(data).find("sort").eq(i).text()+"'>"+$(data).find("sort").eq(i).text()+"</option>").appendTo($("#sort"));
				   				 });
				   					
				   				 
				   				 
				   				 var c1 = "入库重量";
				   				 var c2 = "每件重量";
				  			
				   				 var unit = $(data).find("counttype").text();
				 				if(unit!=""){
						   			 $("#unit1").html( "("+unit+")");
									 $("#unit2").html( "("+unit+")");
								 }
								 
								  		var num = Math.floor($(data).find("quality").length/4);
					var count = $(data).find("quality").length%4;
					var qua=new Array($(data).find("quality").length);
					//alert(num+":"+count);
					$(data).find("quality").each(function(i){
						qua[i] = $(data).find("quality").eq(i).text();
					});
					var str ="";
					var i=0;
					var j=0;
					
	
			
					$("#qn").html("质量标准");
					$("#quality").empty();
					for(i=0;i<num;i++){
						str="";
						str=str+"<tr height='25px'>";
						for(j=0;j<4;j++){
	
							str=str+"<td  class='cd_list1'>"+qua[i*4+j]+"</td><td class='cd_list1'><input type='hidden' name='qualityName' value='"+qua[i*4+j]+"'><input name='qualityParameter' type='text' size=10 class='form_kr1' style='width:80' ></td>";
						}
						str=str+"<tr>";
						$(str).appendTo($("#quality"));
					}
					var len;
					if(num!=0){
						i--;
						j--;
						len = i*4+j;
					}else{
						len = j-1;
					}
	
	
					str="<tr height='25px'>";
					for(var m=0;m<count;m++)
					{
					
					
						
						str=str+"<td class='cd_list1'>"+qua[len+1+m]+"</td><td  class='cd_list1'><input type='hidden' name='qualityName' value='"+qua[len+1+m]+"'><input name='qualityParameter' type='text' size=10 class='form_kr1' style='width:80' ></td>";			
					}
					if(count==1){
						len=6;
					}else if(count==2){
						len=4;
					}else if(count==3){
						len=2;
					}
					if(num!=0){
				
						
						str=str+"<td class='cd_bt' colspan="+len+" >&nbsp</td>";
					}
						str=str+"<tr>";
				
					$(str).appendTo($("#quality"));
				
				
								
				}
 					 });   
	  
	}
	
	function freturn()
	{
		frm.action = "<%=basePath%>servlet/enterWareController.wha?funcflg=enterWareReturn";
		frm.submit();
	}
</SCRIPT>