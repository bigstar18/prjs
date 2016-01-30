<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>	
		<title>商品</title>
		<% 
			String nowDate = Tools.fmtDate(new Date());
			request.setAttribute("nowDate", nowDate);
		%>
<style type="text/css">
<!--
.yin {
	visibility:hidden;
	position:absolute;
	
}
.xian{
	visibility:visible;
}
-->
</style>

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
		  obj.enabled = true;
		  obj.style.backgroundColor = "white";
		}

		$(document).ready(function() {
	    	jQuery("#frm").validationEngine('attach');
			//修改按钮注册点击事件
			$("#add").click(function(){
				//验证信息
				if(jQuery("#frm").validationEngine('validateform')){
					var flag = false;
				    flag = save_onclick();
				    if(flag){
				    	var vaild = affirm("您确定要操作吗？");
						if(vaild){
							//添加信息URL
							var updateDemoUrl = $(this).attr("action");
							//全 URL 路径
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
							$(this).attr("disabled",true);
						}
					}	
				}
			});
			$("#update").click(function(){
				//验证信息
				if(jQuery("#frm").validationEngine('validateform')){
					var flag = false;
				    flag = save_onclick();
				    if(flag){
				    	var vaild = affirm("您确定要操作吗？");
						if(vaild){
							//添加信息URL
							var updateDemoUrl = $(this).attr("action");
							//全 URL 路径
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
							$(this).attr("disabled",true);
						}
					}	
				}
			});
			$("#commodityID").change(function(){
				var commodityID = document.getElementById("commodityID").value;
				if (commodityID != "") {
					$.ajax({
						type: "post",
						url: "../../ajaxcheck/demo/ajaxAddCommodityById.action",
						data: {
						        commodityID: commodityID
							  },
						success : function(data){
									var result = eval("(" + data + ")");
									if(result.opr=="1"){
									  alert("此商品代码已存在，请修改");
									  $("#commodityID").val("");
									  $("#commodityID").focus();
									}
						          }
					});
				}
			});
			$("#back").click(function(){
				
				//添加信息URL
				var updateDemoUrl = $(this).attr("action");
				//全 URL 路径
				var url = "${basePath}"+updateDemoUrl+"?breedID="+$("#breedID").val()+"&sortColumns=order+by+commodityID+asc";

				document.location.href = url;
				
			});
			
			try {
				var crud = $("#crud").val();
				var trades = "";
					if ("${entity.tradeTime}") {
						trades = "${entity.tradeTime}".split(",");
					} 				
				$("input[name='entity.tradeTime']").each(function() {
					if (crud == "create") {
						$(this).attr("checked", true);
					} else {
						for (var k=0 ; k<trades.length; k++) {
							if ($(this).val() == trades[k]) {
								$(this).attr("checked", true);
							}
						}
					}
				});
			} catch (e) {
				alert("无交易节！")
			}
	 	});

	 	function change() {
	 		var settlePriceType = $("#settlePriceType").val();
			
			if (settlePriceType == "3") {//指定交收价
				$("#bbb0").hide();
			 	$("#aaa0").hide();
				$("#ccc0").show();
			 	$("#ddd0").show();
			}else if (settlePriceType == "1"){//计算交收价的提前日
				$("#bbb0").show();
			 	$("#aaa0").show();
			 	$("#ccc0").hide();
			 	$("#ddd0").hide();	
			}else {
				$("#bbb0").hide();
			 	$("#aaa0").hide();
			 	$("#ccc0").hide();
			 	$("#ddd0").hide();
			}
		}
		
		function window_onload()
		{
			// 下面两行代码与页面上方当前历史标题栏有关，即commodity_head.jsp
			//changeColor(oper.value);  
			//breedID.value = "<c:out value="${param['breedID']}"/>";

		    change_date_settle();
		    if ($("#crud").val() == "update") {
		     		setReadOnly(document.all.commodityID);
		     // document.all.name.focus();
		     //market.commoditySetType eq 0
		      		setReadOnly(document.all.name);
		      		//setReadOnly(document.all.settleDate);
		      		//setDisabled(document.all.settleDateBtn);
		      		//setReadOnly(document.all.contractFactor); 
		      		setReadOnly(document.all.lastPrice); 
		      		setReadOnly(document.all.marketDate); 
		      		// 与settleWay_settleDate方法相对应，不进行日期的换算
		      		if ($("#settleWay").val() == "1") {
		      			$("#settleDate").attr("readOnly", true).css("background-color", "#C0C0C0");
					}
		    } else {
		    		//延期交收设置日期
			      	settleWay_settleDate();
			}
		    
		      if ($("#limitCmdtyQty").val() == "-1") {
		      	  changeManner101(2);
		      }else {
		      	  changeManner101(1);
		      }
		      if ($("#firmCleanQty").val() == "-1") {
		      	  changeManner102(2);
		      }else {
		      	  changeManner102(1);
		      }
		      if ($("#firmMaxHoldQty").val() == "-1") {
		      	  changeManner103(2);
		      }else {
		      	  changeManner103(1);
		      }
		      if ($("#oneMaxHoldQty").val() == "-1") {
		      	  changeManner104(2);
		      }else {
		      	  changeManner104(1);
		      }
		      
		      setReadOnly(document.all.settleDate7);
		      setReadOnly(document.all.settleDate1);
		      
		      marginAlgr_change();
		      setReadOnly(document.all.marginRate_B);
		      setReadOnly(document.all.marginRate_S);
		      
		      //默认延期费收取方式
		      //document.all.delayFeeWay.value = "1";
		      //document.all.aheadSettlePriceType.value=${aheadSettlePriceType}
		      
		}
		function on_change(){
			$("#forceCloseFeeAlgr").val($("#feeAlgr").val());
			
			if ($("#feeAlgr").val() == "1") {
				$("#feeRate_BPercent").show();$("#feeRate_SPercent").show();
            	$("#historyCloseFeeRate_BPercent").show();$("#historyCloseFeeRate_SPercent").show();
            	$("#todayCloseFeeRate_BPercent").show();$("#todayCloseFeeRate_SPercent").show();
            	$("#forceCloseFeeRate_BPercent").show();$("#forceCloseFeeRate_SPercent").show();
			}else {
				$("#feeRate_BPercent").hide();$("#feeRate_SPercent").hide();
            	$("#historyCloseFeeRate_BPercent").hide();$("#historyCloseFeeRate_SPercent").hide();
            	$("#todayCloseFeeRate_BPercent").hide();$("#todayCloseFeeRate_SPercent").hide();
            	$("#forceCloseFeeRate_BPercent").hide();$("#forceCloseFeeRate_SPercent").hide();
			}
		}
		//save
		function save_onclick()
		{
			if (!$("input[name='entity.tradeTime']:checked").val()) {
				alert("没有选定交易节");
				return false;
			}
			if ($("input[name='type']:checked").val() == "1") {
				$("#marginItem1_S").val($("#marginItem1").val());
				$("#marginItemAssure1_S").val($("#marginItemAssure1").val());
			}
			for (var i=1; i<5; i++) {
		    	if ($("input[name='type"+i+"']:checked").val() == "1") {
		    		$("#marginItem"+(i+1)+"_S").val($("#marginItem"+(i+1)).val());
		    		$("#marginItemAssure"+(i+1)+"_S").val($("#marginItemAssure"+(i+1)).val());
		    	}
		    }
			for (var i=1; i<5; i++) {
		    	if ($("#marginItem"+(i+1)).val()=="" && $("#marginItem"+(i+1)+"_S").val()=="" && $("#marginItemAssure"+(i+1)).val()=="" && $("#marginItemAssure"+(i+1)+"_S").val()=="") {	
		    	} else if ($("#marginItem"+(i+1)).val()!="" && $("#marginItem"+(i+1)+"_S").val()!="" && $("#marginItemAssure"+(i+1)).val()!="" && $("#marginItemAssure"+(i+1)+"_S").val()!="") {}else {alert("第"+(i+1)+"阶段保证金设置有误");return false;}
		    }
			for(var i=2;i<6;i++){
			    var typeid = 'type'+(i-1);	
				var a = document.getElementsByName(typeid); 
				var marginItem = $('#marginItem'+i).val();
				var marginItem_S = $('#marginItem'+i+'_S').val();
				var marginItemAssure = $('#marginItemAssure'+i).val();
				var marginItemAssure_S  = $('#marginItemAssure'+i+'_S').val();
				if(a[0].checked==true){
					 if((marginItem>0 || marginItemAssure>0)&&document.getElementById('settleDate'+i).value==""){
							alert('第'+i+'阶段保证金于日期或金额有误');
							return false;
					 }
					 if(document.getElementById('settleDate'+i).value!=""&&(marginItem<=0 && marginItemAssure<0)){
						 alert('第'+i+'阶段保证金于日期或金额有误');
							 return false;
					 }
				}
				if(a[1].checked==true){
				 	 if((marginItem_S>0||marginItemAssure_S>0||marginItem>0 || marginItemAssure>0)&&document.getElementById('settleDate'+i).value==""){
						alert('第'+i+'阶段保证金于日期或金额有误');
					    return false;
					 }
					 	if((marginItem_S<=0 &&marginItemAssure_S<=0 &&marginItem<=0  && marginItemAssure<0)&&document.getElementById('settleDate'+i).value!=""){
						alert('第'+i+'阶段保证金于日期或金额有误');
						return false;
					 }
				}
			}

		    if(!tmp_baseinfo_up)
		    {
		      baseinfo_onclick();
		    }
		    if (!tmp_baseinfo_up2) {
		    	baseinfo2_onclick();
		    }
		    if (!tmp_baseinfo_up3) {
		    	baseinfo3_onclick();
		    }
		    if (!tmp_baseinfo_up4) {
		    	baseinfo4_onclick();
		    }
		    if (!tmp_baseinfo_up8) {
		    	baseinfo8_onclick();
		    }
		    if(!tmp_settleinfo_up)
		    {
		      settleinfo_onclick();
		    }
		    if(!tmp_baseinfo_upy)
		    {
		      baseinfoy_onclick();
		    }

		    if (document.all.settleDate.value <= document.all.marketDate.value) {
		    	alert("最后交易日不得早于上市日期！");
		    	document.all.settleDate.focus();
		    	return false;
		    }
		    if($("#contractFactor").val() <= 0)
		    {
		       alert("单位交易数量应大于0！");
		       document.all.contractFactor.focus();
		       return false;
		    }
		    if (document.all.minPriceMove.value <= 0 ) {
		    	alert("最小价位应大于0！");
		    	document.all.minPriceMove.focus();
		    	return false;
		    }
		    if (document.all.minQuantityMove.value <= 0) {
		    	alert("最小变动数量应大于0！");
		    	document.all.minQuantityMove.focus();
		    	return false;
		    }	   
		    if (document.all.minSettleMoveQty.value <= 0) {
		    	alert("最小交割单位应大于0！");
		    	document.all.minSettleMoveQty.focus();
		    	return false;
		    }
		    if (document.all.minSettleQty.value <= 0) {
		    	alert("最小交割数量应大于0！");
		    	document.all.minSettleQty.focus();
		    	return false;
		    }
			if(((document.all.lastPrice.value)*10000000)%((document.all.minPriceMove.value)*10000000)!=0){
				alert("开市指导价必须为最小变动价位的整数倍！");
		    	document.all.lastPrice.focus();
		    	return false;
			}
		    if (document.all.lastPrice.value == "") {
		    	alert("开盘指导价不能为空！");
		    	document.all.lastPrice.focus();
		    	return false;
		    }else {
		    	var lastPrice = parseFloat(document.all.lastPrice.value);
		    	if (lastPrice <= 0) {
		    		alert("开盘指导价应大于零！");
			    	document.all.lastPrice.focus();
			    	return false;
		    	}
		    }
		    if (document.all.lastPrice.value < 0) {
		    	alert("开盘指导价应大于0！");
		    	document.all.lastPrice.focus();
		    	return false;
		    }
		    if (document.all.marginAlgr.value == "1") {
		    	if (document.all.marginPriceType.value == "") {
		    		alert("保证金计算依据不能为空！");
		    		return false;
		    	}
		    }
			if (document.all.type101[1].checked == true) {
				if (document.all.limitCmdtyQty.value < 0) {
					alert("商品单边最大订货量不能为负！");
					return false;
				}
			}
			if (document.all.type102[1].checked == true) {
				if (document.all.firmCleanQty.value < 0) {
					alert("交易商净订货量不能为负！");
					return false;
				}
			}
			if (document.all.type103[1].checked == true) {
				if (document.all.firmMaxHoldQtyAlgr.value == "") {
		 			alert("交易商订货量限制算法不能为空！");
		 			return false;
		 		}
		 		if (document.all.firmMaxHoldQtyAlgr.value == "2") {
		 			if (document.all.firmMaxHoldQty.value == "") {
				 		alert("交易商最大双边订货量不能为空！");
				 		document.all.firmMaxHoldQty.focus();
				    	return false;
				 	}
				 	if (document.all.firmMaxHoldQty.value < 0) {
						alert("交易商最大双边订货量不能为负！");
						return false;
					}
		 		}else {
		 			if (document.all.startPercentQty.value == "") {
		 				alert("商品订货量阀值不能为空！");
		 				return false;
		 			}
		 			if (document.all.maxPercentLimit.value == "") {
		 				alert("订货最大比例不能为空！");
		 				return false;
		 			}
		 			document.all.firmMaxHoldQty.value = parseInt((document.all.startPercentQty.value * (document.all.maxPercentLimit.value/100))+"");
		 			if (document.all.firmMaxHoldQty.value < 1) {
		 				alert("阀值与比例设置不正确，乘积最小为1！");
		 				return false;
		 			}
		 		}
			}else {
				document.all.firmMaxHoldQtyAlgr.value = '2';
				document.all.startPercentQty.value = '0';
				document.all.maxPercentLimit.value = '0';
			}
			if (document.all.type104[1].checked == true) {
				if (document.all.oneMaxHoldQty.value < 0) {
					alert("单笔最大委托量不能为负！");
					return false;
				}
			}
			
			if (document.all.settleDate2.value != "") {
				if ((document.all.marginItem2.value == "0.0") || (document.all.marginItem2.value == "0")) {
					if (confirm("保证金为0，是否继续？")) {
						
					}else {
						return false;
					}
				}
			}
			if (document.all.settleDate3.value != "") {
				if ((document.all.marginItem3.value == "0.0") || (document.all.marginItem3.value == "0")) {
					if (confirm("保证金为0，是否继续？")) {
						
					}else {
						return false;
					}
				}
			}
			if (document.all.settleDate4.value != "") {
				if ((document.all.marginItem4.value == "0.0") || (document.all.marginItem4.value == "0")) {
					if (confirm("保证金为0，是否继续？")) {
						
					}else {
						return false;
					}
				}
			}
			if (document.all.settleDate5.value != "") {
				if ((document.all.marginItem5.value == "0.0") || (document.all.marginItem5.value == "0")) {
					if (confirm("保证金为0，是否继续？")) {
						
					}else {
						return false;
					}
				}
			}
			if(document.all.storeRecoupRate.value >= 100){
				alert("仓储补偿费必须小于100");
				return false;
			}
			var addedTax = Number($("#addedTax").val());
		    $("#addedTaxFactor").val(addedTax/(addedTax+100));
		    setEnabled(document.all.marginPriceType);
			/*
		    if(confirm("是否级联增加该商品所属品种下的交易商特殊设置？")){
		    	document.all.action="<c:url value='/timebargain/baseinfo/commodity.do?funcflg=save&logos=true'/>";
			}else{
				
			}*/
		   return true;    
		}

		//spreadAlgr_onchange()
		function spreadAlgr_onchange(value)
		{
			if (value == "1") {
				$("#spreadUpLmtPercent").show();
				$("#spreadDownLmtPercent").show();
			}else {
				$("#spreadUpLmtPercent").hide();
				$("#spreadDownLmtPercent").hide();
			}
		}


		//---------------------------start baseinfo-------------------------------
		
		var tmp_baseinfo_up = true;
		function baseinfo_onclick()
		{
			if (tmp_baseinfo_up)
		  {
		    tmp_baseinfo_up = false;
		    var src = "${skinPath}/image/app/timebargain/ctl_detail_Down.gif";
		    $("#baseinfo_img").attr("src", src);
		    $("#baseinfo").hide();
		  }
		  else
		  {
		    tmp_baseinfo_up = true;
		    var src = "${skinPath}/image/app/timebargain/ctl_detail_Up.gif";
		    $("#baseinfo_img").attr("src", src);
		    $("#baseinfo").show();
		  }
		  
		}
		var tmp_baseinfo_up2 = true;
		function baseinfo2_onclick()
		{
		  if (tmp_baseinfo_up2)
		  {
		    tmp_baseinfo_up2 = false;
		    var src = "${skinPath}/image/app/timebargain/ctl_detail_Down.gif";
		    $("#baseinfo_img2").attr("src", src);
		    $("#baseinfo2").hide();
		  }
		  else
		  {
		    tmp_baseinfo_up2 = true;
		    var src = "${skinPath}/image/app/timebargain/ctl_detail_Up.gif";
		    $("#baseinfo_img2").attr("src", src);
		    $("#baseinfo2").show();
		  }
		}
		
		var tmp_baseinfo_up3 = true;
		function baseinfo3_onclick()
		{
		  if (tmp_baseinfo_up3)
		  {
		    tmp_baseinfo_up3 = false;
		    var src = "${skinPath}/image/app/timebargain/ctl_detail_Down.gif";
		    $("#baseinfo_img3").attr("src", src);
		    $("#baseinfo3").hide();
		  }
		  else
		  {
		    tmp_baseinfo_up3 = true;
		    var src = "${skinPath}/image/app/timebargain/ctl_detail_Up.gif";
		    $("#baseinfo_img3").attr("src", src);
		    $("#baseinfo3").show();
		  }
		}
		
		var tmp_baseinfo_up4 = true;
		function baseinfo4_onclick()
		{
		  if (tmp_baseinfo_up4)
		  {
		    tmp_baseinfo_up4 = false;
		    var src = "${skinPath}/image/app/timebargain/ctl_detail_Down.gif";
		    $("#baseinfo_img4").attr("src", src);
		    $("#baseinfo4").hide();
		  }
		  else
		  {
		    tmp_baseinfo_up4 = true;
		    var src = "${skinPath}/image/app/timebargain/ctl_detail_Up.gif";
		    $("#baseinfo_img4").attr("src", src);
		    $("#baseinfo4").show();
		  }
		}

		var tmp_baseinfo_up8 = true;
		function baseinfo8_onclick()
		{
		  if (tmp_baseinfo_up8)
		  {
		    tmp_baseinfo_up8 = false;
		    var src = "${skinPath}/image/app/timebargain/ctl_detail_Down.gif";
		    $("#baseinfo_img8").attr("src", src);
		    $("#baseinfo8").hide();
		  }
		  else
		  {
		    tmp_baseinfo_up8 = true;
		    var src = "${skinPath}/image/app/timebargain/ctl_detail_Up.gif";
		    $("#baseinfo_img8").attr("src", src);
		    $("#baseinfo8").show();
		  }
		}
		
		var tmp_baseinfo_upy = true;
		function baseinfoy_onclick()
		{
		  if (tmp_baseinfo_upy)
		  {
		    tmp_baseinfo_upy = false;
		    var src = "${skinPath}/image/app/timebargain/ctl_detail_Down.gif";
		    $("#baseinfo_imgy").attr("src", src);
		    $("#baseinfoy").hide();
		  }
		  else
		  {
		    tmp_baseinfo_upy = true;
		    var src = "${skinPath}/image/app/timebargain/ctl_detail_Up.gif";
		    $("#baseinfo_imgy").attr("src", src);
		    $("#baseinfoy").show();
		  }
		}
		//-----------------------------end baseinfo-----------------------------


		//---------------------------start settleinfo-------------------------------
		var tmp_settleinfo_up = true;
		function settleinfo_onclick()
		{
		  if (tmp_settleinfo_up)
		  {
		    tmp_settleinfo_up = false;
		    var src = "${skinPath}/image/app/timebargain/ctl_detail_Down.gif";
		    $("#settleinfo_img").attr("src", src);
		    $("#settleinfo").hide();
		  }
		  else
		  {
		    tmp_settleinfo_up = true;
		    var src = "${skinPath}/image/app/timebargain/ctl_detail_Up.gif";
		    $("#settleinfo_img").attr("src", src);
		    $("#settleinfo").show();
		  }
		}
		//-----------------------------end settleinfo-----------------------------
		function changeManner(id, td1, td2, td3, step){ 
		  var td1 = $("#"+td1);
		  var td2 = $("#"+td2);
		  if(id==1)
		  {
		   td1.html('保证金'+step+'：');
		   td2.html('担保金'+step+'：');
		   $("#"+td3).hide();
		  }
		  else if(id==2)
		  {
			td1.html('买保证金'+step+'：');
			td2.html('买担保金'+step+'：');
			$("#"+td3).show();
		  }
		} 

		function changeManner12(id){ 
		  var td1 = window.td12;

		  if(id==1)
		  {
		   td1.innerHTML='交收货款：';

		   document.getElementById("abcd1").style.visibility = "hidden";
		   document.getElementById("bbb").className = "yin";
		  }
		  else if(id==2)
		  {
		   td1.innerHTML='买交收货款：';

		   document.getElementById("abcd1").style.visibility = "visible";
		   document.getElementById("bbb").className = "xian";
		  }
		  
		} 

		function settleFeeAlgr_change(){
			if ($("#settleFeeAlgr").val() == "1") {
				document.getElementById("settleFeeRate_BPercent").className = "xian";
				document.getElementById("settleFeeRate_SPercent").className = "xian";
			}else {
				document.getElementById("settleFeeRate_BPercent").className = "yin";
				document.getElementById("settleFeeRate_SPercent").className = "yin";
			}
		}
		function changeb(value){
		    if($("#todayCloseFeeRate_B").val()==""){
		    	$("#todayCloseFeeRate_B").val(value);
		    	$("#historyCloseFeeRate_B").val(value);
		    }
		 }
		 function changes(value){
		    if($("#todayCloseFeeRate_S").val()==""){
		    	$("#todayCloseFeeRate_S").val(value);
		    	$("#historyCloseFeeRate_S").val(value);
		    } 
		 }  
		 
		//仅输入数字和.
		  function onlyNumberInput()
		  {
		    if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47)
		    {
		      event.returnValue=false;
		    }
		  }
		 function suffixNamePress()
		{
			
		  if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47)  //|| (event.keyCode>=65 && event.keyCode<=90) || (event.keyCode>=97 && event.keyCode<=122)
		  {
		    event.returnValue=false;
		  }
		  else
		  {
		    event.returnValue=true;
		  }
		}
		 function suffixNamePress2()
		 {
		 	
		   if (event.keyCode<45 || event.keyCode>57 || event.keyCode == 47) 
		   {
		     event.returnValue=false;
		   }
		   else
		   {
		     event.returnValue=true;
		   }
		 }

		function change_date(){
			$("#settleDate1").val($("#marketDate").val());
		}

		function change_date_settle(){
			document.getElementById("settleDate7").value = $("#settleDate").val();
		}

		 function changeManner101(id){
		 	if (id == "1") {
		 		setReadWrite(document.all.limitCmdtyQty);
		 	}
		 	if (id == "2") {
		 		document.all.limitCmdtyQty.value = "-1";
		 		setReadOnly(document.all.limitCmdtyQty);
		 	}
		 }
		 
		  function changeManner102(id){
		 	if (id == "1") {
		 		setReadWrite(document.all.firmCleanQty);
		 	}
		 	if (id == "2") {
		 		document.all.firmCleanQty.value = "-1";
		 		setReadOnly(document.all.firmCleanQty);
		 	}
		 }
		 
		  function changeManner104(id){
		 	if (id == "1") {
		 		setReadWrite(document.all.oneMaxHoldQty);
		 	}
		 	if (id == "2") {
		 		document.all.oneMaxHoldQty.value = "-1";
		 		setReadOnly(document.all.oneMaxHoldQty);
		 	}
		 }
		 
		 function changeManner103(id){
			 if (id == "1") {
			 		setReadWrite(document.getElementById("firmMaxHoldQty"));
			 		//控制交易商订货量限制算法隐现
			 		$("#tdFirmMaxHoldQtyAlgr").show();
			 		changeFirmMaxHoldQtyAlgr();
			 	}
			 	if (id == "2") {
			 		$("#tdFirmMaxHoldQtyAlgr").hide();
			 		$("#tdFirmMaxHoldQty").show();
			 		document.getElementById("firmMaxHoldQty").value = "-1";
			 		setReadOnly(document.getElementById("firmMaxHoldQty"));
			 	}
		 }
		 
	 	function changeFirmMaxHoldQtyAlgr(){
	 		if (document.getElementById("firmMaxHoldQtyAlgr").value == "1") {
	 			$("#tdStartPercentQty").show();
	 			$("#tdMaxPercentLimit").show();
	 			$("#tdFirmMaxHoldQty").hide();
	 		}else {
	 			$("#tdStartPercentQty").hide();
	 			$("#tdMaxPercentLimit").hide();
	 			$("#tdFirmMaxHoldQty").show();
	 		}
	 	}

	 	function change() {
	 		var settlePriceType = $("#settlePriceType").val();
			
			if (settlePriceType == "3") {//指定交收价
				$("#bbb0").hide();
			 	$("#aaa0").hide();
				$("#ccc0").show();
			 	$("#ddd0").show();
			}else if (settlePriceType == "1"){//计算交收价的提前日
				$("#bbb0").show();
			 	$("#aaa0").show();
			 	$("#ccc0").hide();
			 	$("#ddd0").hide();	
			}else {
				$("#bbb0").hide();
			 	$("#aaa0").hide();
			 	$("#ccc0").hide();
			 	$("#ddd0").hide();
			}
		}
		function change2(){
			var settleMarginType = document.forms(0).settleMarginType.value;
			
			if (settleMarginType == "1"){//计算交收价的提前日
				$("#bbb02").show();
				$("#aaa02").show();
			}else {
				$("#bbb02").hide();
				$("#aaa02").hide();
			}
		}

		//计算启用持仓天数限制的最长持仓天数
		function change3(){
			var holdDaysLimit = document.forms(0).holdDaysLimit.value;
			
			if (holdDaysLimit == "1"){
				$("#bbb01").show();
				$("#aaa01").show();
			}else {
				$("#bbb01").hide();
				$("#aaa01").hide();
			}
		}

		// 判断最大持仓天数是否为空
		function changeMaxHoldPositionDay(){
			if(frm.holdDaysLimit.value == 1){
				$("#maxHoldPositionDay").removeClass("validate[maxSize[10] input_text");
				$("#maxHoldPositionDay").addClass("validate[required,maxSize[10] input_text");
			}
		}

		
		function settleMarginAlgr_B_change(){
			if (document.all.settleMarginAlgr_B.value == "1") {
				document.getElementById("settleMarginRate_BPercent").className = "xian";
			}else {
				document.getElementById("settleMarginRate_BPercent").className = "yin";
			}
		}

		function settleMarginAlgr_S_change(){
			if (document.all.settleMarginAlgr_S.value == "1") {
				document.getElementById("settleMarginRate_SPercent").className = "xian";
			}else {
				document.getElementById("settleMarginRate_SPercent").className = "yin";
			}
		}

		function payoutAlgr_change(){
			if (document.all.payoutAlgr.value == "1") {
				document.getElementById("payoutRatePercent").className = "xian";
			}else {
				document.getElementById("payoutRatePercent").className = "yin";
			}
		}


		function marginPriceType_change(){
		 	var marginAlgr = document.getElementById("marginAlgr").value;
		 	var tdName = document.getElementById("td1s");
		 	if (marginAlgr == 1) {
		 		tdName.innerHTML = '保证金计算依据：';
		 		$("#td1s").show();
		 		$("#td2s").show();
		 		document.getElementById("marginPriceType").value = "";
		 	}else {
		 		tdName.innerHTML = '';
		 		$("#td1s").hide();
		 		$("#td2s").hide();
		 		document.getElementById("marginPriceType").value = "0";
		 		
		 	}
		}
		function marginAlgr_change(){
			if (document.all.marginAlgr.value == "1") {
				document.getElementById("marginItem1Percent").innerHTML = '%';
				document.getElementById("marginItemAssure1Percent").innerHTML = '%';
				document.getElementById("marginItem1_SPercent").innerHTML = '%';
				document.getElementById("marginItemAssure1_SPercent").innerHTML = '%';
				
				document.getElementById("marginItem2Percent").innerHTML = '%';
				document.getElementById("marginItemAssure2Percent").innerHTML = '%';
				document.getElementById("marginItem2_SPercent").innerHTML = '%';
				document.getElementById("marginItemAssure2_SPercent").innerHTML = '%';
				
				document.getElementById("marginItem3Percent").innerHTML = '%';
				document.getElementById("marginItemAssure3Percent").innerHTML = '%';
				document.getElementById("marginItem3_SPercent").innerHTML = '%';
				document.getElementById("marginItemAssure3_SPercent").innerHTML = '%';
				
				document.getElementById("marginItem4Percent").innerHTML = '%';
				document.getElementById("marginItemAssure4Percent").innerHTML = '%';
				document.getElementById("marginItem4_SPercent").innerHTML = '%';
				document.getElementById("marginItemAssure4_SPercent").innerHTML = '%';
				
				document.getElementById("marginItem5Percent").innerHTML = '%';
				document.getElementById("marginItemAssure5Percent").innerHTML = '%';
				document.getElementById("marginItem5_SPercent").innerHTML = '%';
				document.getElementById("marginItemAssure5_SPercent").innerHTML = '%';
				
				//document.getElementById("marginRate_BPercent").innerHTML = '%';
				//document.getElementById("marginRate_SPercent").innerHTML = '%';
				
				//document.all.marginRate_B.value = document.all.marginRate_B.value*100;
		    	//document.all.marginRate_S.value = document.all.marginRate_S.value*100;
				
			}else {
				document.getElementById("marginItem1Percent").innerHTML = '';
				document.getElementById("marginItemAssure1Percent").innerHTML = '';
				document.getElementById("marginItem1_SPercent").innerHTML = '';
				document.getElementById("marginItemAssure1_SPercent").innerHTML = '';
				
				document.getElementById("marginItem2Percent").innerHTML = '';
				document.getElementById("marginItemAssure2Percent").innerHTML = '';
				document.getElementById("marginItem2_SPercent").innerHTML = '';
				document.getElementById("marginItemAssure2_SPercent").innerHTML = '';
				
				document.getElementById("marginItem3Percent").innerHTML = '';
				document.getElementById("marginItemAssure3Percent").innerHTML = '';
				document.getElementById("marginItem3_SPercent").innerHTML = '';
				document.getElementById("marginItemAssure3_SPercent").innerHTML = '';
				
				document.getElementById("marginItem4Percent").innerHTML = '';
				document.getElementById("marginItemAssure4Percent").innerHTML = '';
				document.getElementById("marginItem4_SPercent").innerHTML = '';
				document.getElementById("marginItemAssure4_SPercent").innerHTML = '';
				
				document.getElementById("marginItem5Percent").innerHTML = '';
				document.getElementById("marginItemAssure5Percent").innerHTML = '';
				document.getElementById("marginItem5_SPercent").innerHTML = '';
				document.getElementById("marginItemAssure5_SPercent").innerHTML = '';
				
				//document.getElementById("marginRate_BPercent").innerHTML = '';
				//document.getElementById("marginRate_SPercent").innerHTML = '';
				
				//document.all.marginRate_B.value = document.all.marginRate_B.value/100;
				//document.all.marginRate_S.value = document.all.marginRate_S.value/100;
			}	
		}
		 
		 		function settleWay_settleDate(){
		 			var myDate = new Date();
					var time = myDate.toLocaleString().substring(0,4);
					var relTime = parseInt(time);
					var day = 0;
					for (i = relTime + 100; i > relTime; i--) {
						var y = i % 4;
						if (y == 0) {
							day = day + 366;
						}else {
							day = day + 365;
						}
					}
					myDate.setDate(myDate.getDate()+day);
					var date = myDate.toLocaleString().split(" ")[0];
					var year = date.indexOf("年");
					var month = date.indexOf("月");
					var day = date.indexOf("日");	
					var relDate = date.substring(0,year) + "-" + date.substring(year+1,month) + "-" + date.substring(month+1,day);

					// settleDate7为交收保证金生效日期，settleDate为最后交易日
					if ($("#settleWay").val() == "1") {
						$("#settleDate7").val(relDate);
						$("#settleDate").val(relDate).attr("readOnly", true).css("background-color", "#C0C0C0");	
					}else {
						$("#settleDate").attr("readOnly", false).css("background-color", "#FFF");
					}
		 		}
</script>
</head>
<body leftmargin="0" topmargin="0" onload="return window_onload()">
	<table border="0" height="400" width="790" align="center">
		<tr>
			<td>
				<form id="frm" action="" method="POST" class="form">
					<fieldset>
						<legend class="common">
							<b>设置商品信息
							</b>
						</legend>
<table width="700" border="0" align="center"  class="common" cellpadding="0" cellspacing="0">
<!-- 基本信息 -->
        <tr class="common">
          <td colspan="4">
            <fieldset>
              <legend class="common">
                <table cellspacing="0" cellpadding="0" border="0" width="700" class="common" >
                  <col width="65"></col><col></col><col width="6"></col>
                  <tr>
                    <td><b>基本信息</b></td>
                    <td><hr width="629" class="pickList"/></td>
                    <td ><img id="baseinfo_img" src="<c:url value="${skinPath }/image/app/timebargain/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="baseinfo">
<table cellSpacing="0" cellPadding="0"  width="100%" border="0" align="center" class="common">
        
        <tr >
        	<td align="right" width="138">商品品种：</td>
            <td >
         		<input type="text" id="breedID" name="entity.breedID" value="${entity.breedID }" readOnly="readonly" style="width: 80;background-color: #C0C0C0;"/>
            <span class="req">&nbsp;</span>
            </td> 
            <td align="right" width="140"></td>
            <td>
                <input type="hidden" name="entity.sortID" value="${entity.sortID }"/>
			</td> 
        </tr>
        
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        
        <tr> 
            <td align="right">商品名称：</td>
            <td><input type="text" id="name" name="entity.name" value="${entity.name }" onkeypress="chackValue2()" onkeyup="value=value.replace(/[\n\s\t]/g,'') " maxlength="16" style="width: 80" class="validate[required] input_text datepicker" size="10"/>
            <span class="required">*</span></td> 
            
            <td align="right">商品代码：</td>
            <td ><input type="text" id="commodityID" name="entity.commodityID" value="${entity.commodityID }" onkeypress="chackValue()" onkeydown="chackValue3()" onkeyup="value=value.replace(/[\n\s\t]/g,'') " style="ime-mode:disabled; width: 80"  maxlength="11" class="validate[required] input_text datepicker" size="10"/>
            <!-- 对商品代码做限制、限制商品代码不能输入   >,<, 和 /,下划线(_) -->
            <script type="text/javascript">
            	function chackValue(){
            		if (event.keyCode==47  || event.keyCode==60 || event.keyCode==62 || event.keyCode==32 )
  					{
   						 event.returnValue=false;
  					}
  	         	}
  	         	function chackValue2(){
            		if (event.keyCode==32)
  					{
   						 event.returnValue=false;
  					}
  	         	}
  	         	function chackValue3(){
  	         	   
  	         	   if ((event.shiftKey)&&(event.keyCode==189)){ //屏蔽 shift+_(下划线) 
                         event.returnValue=false; 
                   } 
  	         	}
            </script>
            <span class="required">*</span></td>
            
            <td align="right" width="80">当前状态：</td>
            	<td width="110">
				<select id="status" name="entity.status" style="width:80">
					<option value=""></option>
				    <option value="0" <c:if test="${entity.status==0 }">selected</c:if>>有效</option>
					<option value="2" <c:if test="${entity.status==2 }">selected</c:if>>暂停交易</option>					
			   </select> 
			   <span class="required">*</span>            
            </td>  
           
        </tr>       
        
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        <tr>
        	<td align="right">开盘指导价：</td>
            <td>
			  <input type="text" id="lastPrice" name="entity.lastPrice" maxlength="11" value="${entity.lastPrice }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return suffixNamePress()" class="validate[required] input_text datepicker" size="10"/>  
              <span class="required">*</span>       
            </td>  
             <td align="right">上市日期：</td>
			<td>
				<input type="text" style="width: 80px" id="marketDate"
					class="wdate" maxlength="10"
					name="entity.marketDate" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${entity.marketDate }" />"
					onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"
					onchange="change_date()">
				<span class="required">*</span>
			</td>
            <td align="right" >最后交易日：</td>
			<td>
				<input type="text" style="width: 80px" id="settleDate"
					class="wdate" maxlength="10"
					name="entity.settleDate" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${entity.settleDate }" />"
					onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"
					onchange="change_date_settle()">
				<span class="required">*</span>
			</td>     
        </tr>
                 
</table >
</span>
            </fieldset>
          </td>
        </tr>					
        	
<!-- 参数信息 -->
        
           
           
   <tr class="common">
          <td colspan="4">
            <fieldset>
              <legend>
                <table cellspacing="0" cellpadding="0" border="0" width="700" class="common">
                  <col width="65"></col><col></col><col width="6"></col>
                  <tr>
                    <td><b>基本参数</b></td>
                    <td><hr width="629" class="pickList"/></td>
                    <td ><img id="baseinfo_img3" src="<c:url value="${skinPath }/image/app/timebargain/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo3_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="baseinfo3">
<table cellSpacing="0" cellPadding="0" width="780" border="0" align="center" class="common">                     
         <tr>
         	<td align="right" width="140">交易单位：
         	</td>
         	<td width="150">
         	  <input type="text" id="contractFactor" name="entity.contractFactor" maxlength="11" value="${entity.contractFactor }"
         	  	style="ime-mode:disabled; width: 80"  class="input_text datepicker" size="10" onkeypress="chackValue2()"/>
			  <span class="required">(${entity.contractFactorName }/批)</span><span class="required">*</span>            
            </td>
            <td align="right" width="115">最小变动价位(元)：</td>
            <td>
            	<input type="text" id="minPriceMove" name="entity.minPriceMove" maxlength="11" value="${entity.minPriceMove }"
            		style="ime-mode:disabled; width: 80" onkeypress="return suffixNamePress()" class="validate[maxSize[10],custom[onlyDoubleSp]] input_text"/>  
			    <span class="required">*</span>          
            </td>
            
             <td align="right" width="100">
				最小变动数量：
			 </td>
			 <td width="115">
				<input type="text" size="10" id="minQuantityMove" name="entity.minQuantityMove" value="${entity.minQuantityMove }"
					style="width:80" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="10" class="input_text datepicker" />
				<span class="required">*</span>
			</td>
		</tr>
		<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
         <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
         
          <tr>
            <td align="right" >涨跌停板算法：
            </td>
            <td>
               <select id="spreadAlgr" name="entity.spreadAlgr" style="width:80" onchange="spreadAlgr_onchange(this.value)">
					<option value=""></option>
				    <option value="1" <c:if test="${entity.spreadAlgr==1 }">selected</c:if>>按百分比</option>
					<option value="2" <c:if test="${entity.spreadAlgr==2 }">selected</c:if>>按绝对值</option>
			   </select> <span class="required">*</span>
            </td>
            <td align="right" >涨幅上限：</td>
			<td><input type="text" size="10" id="spreadUpLmt" name="entity.spreadUpLmt" maxlength="10" value="${entity.spreadUpLmt }"
				style="ime-mode:disabled; width: 80" onkeypress="return suffixNamePress()" class="validate[required] input_text datepicker" />
			  <span id="spreadUpLmtPercent">%</span><span class="required">*</span>           
            </td>
            <td align="right">跌幅下限：</td>
            <td>
			  <input type="text" size="10" id="spreadDownLmt" name="entity.spreadDownLmt" maxlength="10" value="${entity.spreadDownLmt }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return suffixNamePress()" class="validate[required] input_text datepicker" />
			  <span id="spreadDownLmtPercent">%</span><span class="required">*</span>   
			  &nbsp;     
            </td>
            <script type="text/javascript">
            	if ("${entity.spreadAlgr}" == "1"){$("#spreadUpLmtPercent").show();$("#spreadDownLmtPercent").show();} else {$("#spreadUpLmtPercent").hide();$("#spreadDownLmtPercent").hide();}
            </script>
        </tr>   
     <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
      		<tr>
			<td align="right" >&nbsp;买方委托权限：</td>
            <td >
				<select id="orderPrivilege_B" name="entity.orderPrivilege_B" style="width:80" class="validate[required]">
					<option value=""></option>
				    <option value="101" <c:if test="${entity.orderPrivilege_B==101 }">selected</c:if>>全权</option>
					<option value="102" <c:if test="${entity.orderPrivilege_B==102 }">selected</c:if>>只可订立</option>
					<option value="103" <c:if test="${entity.orderPrivilege_B==103 }">selected</c:if>>只可转让</option>
					<option value="104" <c:if test="${entity.orderPrivilege_B==104 }">selected</c:if>>无权</option>
			   </select> <span class="required">*</span>            
            </td>  
            
            <td align="right" >&nbsp;卖方委托权限：</td>
            <td >
				<select id="orderPrivilege_S" name="entity.orderPrivilege_S" style="width:80" class="validate[required]">
					<option value=""></option>
				    <option value="101" <c:if test="${entity.orderPrivilege_S==101 }">selected</c:if>>全权</option>
					<option value="102" <c:if test="${entity.orderPrivilege_S==102 }">selected</c:if>>只可订立</option>
					<option value="103" <c:if test="${entity.orderPrivilege_S==103 }">selected</c:if>>只可转让</option>
					<option value="104" <c:if test="${entity.orderPrivilege_S==104 }">selected</c:if>>无权</option>
			   </select> <span class="required">*</span>            
            </td> 
            <td align="right" width="80">
				增值税率：
			</td>
			<td width="115">
				<input type="text" size="10" id="addedTax" name="entity.addedTax" maxlength="10" value="${entity.addedTax}"
					 style="width:80" class="validate[required] input_text datepicker" onkeypress="return suffixNamePress()"/>
				<span >%</span><span class="required">*</span>
			</td>
			</tr>
		<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
		
		<tr>
	        <td align="right" >
					提前交收价计算方式：
			</td>
			<td>
				<select id="aheadSettlePriceType" name="entity.aheadSettlePriceType" style="width:120" class="validate[required]">
					<option value=""></option>
				    <option value="0" <c:if test="${entity.aheadSettlePriceType==0 }">selected</c:if>>按订立价交收</option>
					<option value="1" <c:if test="${entity.aheadSettlePriceType==1 }">selected</c:if>>按自主价交收</option> 
			    </select> <span class="required">*</span>
			</td>
			<td align="right" >
					交收价计算方式：
			</td>
			<td width="140">
				<select id="settlePriceType" name="entity.settlePriceType" style="width:120" onchange="change()">
					<option value=""></option>
				    <option value="0" <c:if test="${entity.settlePriceType==0 }">selected</c:if>>闭市结算价</option>
					<option value="1" <c:if test="${entity.settlePriceType==1 }">selected</c:if>>最后几日加权平均价</option>
					<option value="2" <c:if test="${entity.settlePriceType==2 }">selected</c:if>>订立价</option>
					<option value="3" <c:if test="${entity.settlePriceType==3 }">selected</c:if>>指定交收价</option>
			    </select> <span class="required">*</span>
			</td>
			<td align="right" id="aaa0">
				计算交收价提前日：
			</td>
			<td width="140" id="bbb0">
			<input type="text" size="10" id="beforeDays" maxlength="10" name="entity.beforeDays" value="${entity.beforeDays }"
				style="width:80" class="input_text datepicker" onkeypress="return suffixNamePress()"/>(交易日)
			<span class="req">&nbsp;</span>
			</td>
									
			<td align="right" id="ccc0">
				指定交收价：
			</td>
			<td id="ddd0">
			<input type="text" size="10" id="specSettlePrice" maxlength="10" name="entity.specSettlePrice" value="${entity.specSettlePrice }" 
				style="width:80" class="input_text datepicker" onkeypress="return suffixNamePress()"/>
			<span width="100" class="req">&nbsp;</span>
			</td>
			<script type="text/javascript">
				var settlePriceType = $("#settlePriceType").val();
				
				if (settlePriceType == "3") {//指定交收价
					$("#bbb0").hide();
				 	$("#aaa0").hide();
					$("#ccc0").show();
				 	$("#ddd0").show();
				}else if (settlePriceType == "1"){//计算交收价的提前日
					$("#bbb0").show();
				 	$("#aaa0").show();
				 	$("#ccc0").hide();
				 	$("#ddd0").hide();	
				}else {
					$("#bbb0").hide();
				 	$("#aaa0").hide();
				 	$("#ccc0").hide();
				 	$("#ddd0").hide();
				}
	        </script>
	</tr>
		<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>

	 	
		<!-- add by yangpei 2011-11-02 -->

	<tr>
			<td align="right" >
					交收保证金计算方式：
			</td>
			<td>
				<select id="settleMarginType" name="entity.settleMarginType" style="width:120" onchange="change2()">
				    <option value="0" <c:if test="${entity.settleMarginType==0 }">selected</c:if>>闭市结算价</option>
					<option value="1" <c:if test="${entity.settleMarginType==1 }">selected</c:if>>最后几日加权平均价</option>
					<option value="2" <c:if test="${entity.settleMarginType==2 }">selected</c:if>>订立价</option>
			    </select> <span class="required">*</span>
			</td>
			
			<td align="right" id="aaa02">
				计算交收保证金提前日：
			</td>
			<td id="bbb02">
			<input type="text" size="10" id="beforeDays_M" maxlength="10" name="entity.beforeDays_M" value="${entity.beforeDays_M }"
				style="width:80" class="input_text datepicker" onkeypress="return suffixNamePress()"/>(交易日)
			<span class="required">&nbsp;</span>
			</td>
			<script type="text/javascript">
            	if ("${entity.settleMarginType}" == "1"){$("#aaa02").show();$("#bbb02").show();} else {$("#aaa02").hide();$("#bbb02").hide();}
            </script>						
			<td></td><td></td>
		</tr>
		
		<tr>
			<td align="right" >
					是否启用持仓天数限制：
			</td>
			<td>
				<select id="holdDaysLimit" name="entity.holdDaysLimit" style="width:120" onchange="change3(),changeMaxHoldPositionDay()">
				    <option value="0" <c:if test="${entity.holdDaysLimit==0 }">selected</c:if>>不启用</option>
					<option value="1" <c:if test="${entity.holdDaysLimit==1 }">selected</c:if>>启用</option>					
			    </select> <span class="required">*</span>
			</td>
			
			<td align="right" id="aaa01">
				最长持仓天数：
			</td>
			<td id="bbb01">
			<input type="text" id="maxHoldPositionDay" name="entity.maxHoldPositionDay" value="${entity.maxHoldPositionDay }"
				style="width:80" class="validate[maxSize[10] input_text" onkeypress="return suffixNamePress()"/>(交易日)
			<span class="required">*</span>
			</td>
			<script type="text/javascript">
            	if ("${entity.holdDaysLimit}" == "1"){$("#aaa01").show();$("#bbb01").show();} else {$("#aaa01").hide();$("#bbb01").hide();}
            </script>						
			<td></td><td></td>
		</tr>
	
     </table >
</span>
            </fieldset>
          </td>
        </tr>	
        
        <tr class="common">
          <td colspan="4">
            <fieldset>
              <legend>
                <table cellspacing="0" cellpadding="0" border="0" width="700" class="common">
                  <col width="95"></col><col></col><col width="6"></col>
                  <tr>
                    <td><b>订货量限制</b></td>
                    <td><hr width="609" class="pickList"/></td>
                    <td ><img id="baseinfo_img8" src="<c:url value="${skinPath }/image/app/timebargain/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo8_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="baseinfo8">
<table cellSpacing="0" cellPadding="0" width="720" border="0" align="center" class="common">                           
       		<tr>
       			<%
					String type101 = (String)request.getAttribute("type101");
					String type102 = (String)request.getAttribute("type102");
					String type103 = (String)request.getAttribute("type103");
					String type104 = (String)request.getAttribute("type104");
					String type105 = (String)request.getAttribute("type105");
				%>
			         	<td align="right">&nbsp;&nbsp;商品单边最大订货量(批)：</td>
			            <td>
			            	<input type="radio" name="type101" value="2" onclick="changeManner101(2);" <%if("2".equals(type101)){out.println("checked");} %> style="border:0px;">不限制
			     			<input type="radio" name="type101" value="1" onclick="changeManner101(1);" <%if("1".equals(type101)){out.println("checked");} %> style="border:0px;">限制
			     			<input type="text" id="limitCmdtyQty" name="entity.limitCmdtyQty" value="${entity.limitCmdtyQty }"
			     				style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" size="10"/>
			     			<span class="required">*</span>
			            </td>
						
						<td align="right" width="120">
							交易商净订货量：
						</td>
						<td>
							<input type="radio" name="type102" value="2" onclick="changeManner102(2);" <%if("2".equals(type102)){out.println("checked");} %> style="border:0px;">不限制
							<input type="radio" name="type102" value="1" onclick="changeManner102(1);" <%if("1".equals(type102)){out.println("checked");} %> style="border:0px;">限制
							<input type="text" id="firmCleanQty" name="entity.firmCleanQty" value="${entity.firmCleanQty }"
								style="ime-mode:disabled; width: 80" class="input_text datepicker" onkeypress="return onlyNumberInput()" size="10"/>
			         				<span class="required">*</span>
						</td>	
					</tr>
					<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
					<tr>
						<td align="right" >
           					交易商最大双边订货量：
           				</td>
			           	<td>
							<input type="radio" name="type103" value="2" onclick="changeManner103(2);" <%if("2".equals(type103)){out.println("checked");} %> style="border:0px;">不限制
			           		<input type="radio" name="type103" value="1" onclick="changeManner103(1);" <%if("1".equals(type103)){out.println("checked");} %> style="border:0px;">限制	
			            	<span id="tdFirmMaxHoldQty">
	           					<input type="text" id="firmMaxHoldQty" name="entity.firmMaxHoldQty" value="${entity.firmMaxHoldQty }"
	           						style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="input_text datepicker" size="10" value="0"/>
	           				<span class="required">*</span></span>
			           	</td>
			           	
						<td align="right">&nbsp;&nbsp;单笔最大委托量：</td>
			            <td>
							<input type="radio" name="type104" value="2" onclick="changeManner104(2);" <%if("2".equals(type104)){out.println("checked");} %> style="border:0px;">不限制
			            	<input type="radio" name="type104" value="1" onclick="changeManner104(1);" <%if("1".equals(type104)){out.println("checked");} %> style="border:0px;">限制
			            	<input type="text" id="oneMaxHoldQty" name="entity.oneMaxHoldQty" value="${entity.oneMaxHoldQty }"
			            		style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" size="10"/>  
						  	<span class="required">*</span>          
			            </td>
					</tr>
		
			    	<tr>		
			           	<td id="tdFirmMaxHoldQtyAlgr" colspan="4">
			           		&nbsp;&nbsp;交易商订货量限制算法：
			           		<select id="firmMaxHoldQtyAlgr" name="entity.firmMaxHoldQtyAlgr" style="width:80" onchange="changeFirmMaxHoldQtyAlgr()">
								<option value=""></option>
							    <option value="1" <c:if test="${entity.firmMaxHoldQtyAlgr==1 }">selected</c:if>>按百分比</option>
								<option value="2" <c:if test="${entity.firmMaxHoldQtyAlgr==2 }">selected</c:if>>按绝对值</option>
						   </select> 
			               <span class="required">*</span>
			               &nbsp;&nbsp;&nbsp;&nbsp;
			               <span id="tdStartPercentQty">
			               		商品订货量阀值：<input type="text" id="startPercentQty" name="entity.startPercentQty" value="${entity.startPercentQty }"
			               							style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" size="10"/>
			            		<span class="required">*</span>
			               </span>
			               
			               &nbsp;&nbsp;&nbsp;&nbsp;
			               <span id="tdMaxPercentLimit">
			               		订货最大比例：<input type="text" id="maxPercentLimit" name="entity.maxPercentLimit" value="${entity.maxPercentLimit }"
			               						style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" size="10"/>
			            		<span>%</span><span class="required">*</span>
			               </span>
			           	</td>
			           	<script type="text/javascript">
						      if ("${entity.firmMaxHoldQtyAlgr}" == "1"){$("#tdFirmMaxHoldQty").show();$("#tdStartPercentQty").show();$("#tdMaxPercentLimit").show();} else {$("#tdFirmMaxHoldQty").hide();$("#tdStartPercentQty").hide();$("#tdMaxPercentLimit").hide();}
						</script>
					</tr>
            <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
       				
</table >
</span>
            </fieldset>
          </td>
        </tr>	
	
           
             
		 <tr class="common">
          <td colspan="4">
            <fieldset>
              <legend>
                <table cellspacing="0" cellpadding="0" border="0" width="700" class="common">
                  <col width="55"></col><col></col><col width="6"></col>
                  <tr>
                    <td><b>手续费</b></td>
                    <td><hr width="639" class="pickList"/></td>
                    <td ><img id="baseinfo_img2" src="<c:url value="${skinPath }/image/app/timebargain/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo2_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="baseinfo2">
<table cellSpacing="0" cellPadding="0" width="720" border="0" align="center" class="common">                           
       
       		<%
         		String typeFeeAlgr = (String)request.getAttribute("typeFeeAlgr");
         	%>
         	<tr> 		            
		    <td align="right" rowspan="4" width="245" height="10" valign="top">
						交易手续费算法：<select id="feeAlgr" name="entity.feeAlgr" style="width:80" onchange="on_change()">
					<option value=""></option>
				    <option value="1" <c:if test="${entity.feeAlgr == '1' }">selected</c:if>>按百分比</option>
					<option value="2" <c:if test="${entity.feeAlgr == '2' }">selected</c:if>>按绝对值</option>
			   </select> 
			   <span class="required">*</span>  
			</td>	            		            
		            
		           <td align="right" colspan="1" width="210" height="5">
		            	买订立：<input type="text" id="feeRate_B" name="entity.feeRate_B" maxlength="6" value="${entity.feeRate_B }" 
		            		style="ime-mode:disabled; width: 80" onkeypress="return suffixNamePress()" class="validate[required] input_text datepicker" onchange="changeb(this.value)" size="10"/>
			  			<span id="feeRate_BPercent">%</span><span class="required">*</span>
			  	   </td>
			  	   <td align="right" >
			  		卖订立：<input type="text" id="feeRate_S" name="entity.feeRate_S" maxlength="6" value="${entity.feeRate_S }"
			  			style="ime-mode:disabled; width: 80" onkeypress="return suffixNamePress()" class="validate[required] input_text datepicker" onchange="changes(this.value)" size="10"/>
			  		<span id="feeRate_SPercent">%</span><span class="required">*</span>
			  	   </td>
			  	   <td width="20"></td>
		          </tr>

				   <tr> 		            
		           <td width="210" align="right" height="5">
		           	买转让历史订货：<input type="text" id="historyCloseFeeRate_B" name="entity.historyCloseFeeRate_B" value="${entity.historyCloseFeeRate_B }"
		           		maxlength="11" style="ime-mode:disabled; width: 80" onkeypress="return suffixNamePress()" class="validate[required] input_text datepicker" size="10"/>
		           	<span id="historyCloseFeeRate_BPercent">%</span><span class="required">*</span>
		           </td>
			  	   <td align="right" >
		           	卖转让历史订货：<input type="text" id="historyCloseFeeRate_S" name="entity.historyCloseFeeRate_S" value="${entity.historyCloseFeeRate_S }"
		           		maxlength="11" style="ime-mode:disabled; width: 80" onkeypress="return suffixNamePress()" class="validate[required] input_text datepicker" size="10"/>
		           	<span id="historyCloseFeeRate_SPercent">%</span><span class="required">*</span>
		          </td>
		              </tr>

				<tr> 		            
		            <td align="right" width="210" height="5">
		            	买转让今订货：<input type="text" id="todayCloseFeeRate_B" name="entity.todayCloseFeeRate_B" value="${entity.todayCloseFeeRate_B }"
		            		maxlength="11" style="ime-mode:disabled; width: 80" onkeypress="return suffixNamePress()" class="validate[required] input_text datepicker" size="10"/>
		            	<span id="todayCloseFeeRate_BPercent">%</span><span class="required">*</span>
		            </td>
			  	   	<td align="right" >	
		            	卖转让今订货：<input type="text" id="todayCloseFeeRate_S" name="entity.todayCloseFeeRate_S" value="${entity.todayCloseFeeRate_S }"
		            		maxlength="11" style="ime-mode:disabled; width: 80" onkeypress="return suffixNamePress()" class="validate[required] input_text datepicker" size="10"/>
		            	<span id="todayCloseFeeRate_SPercent">%</span><span class="required">*</span>
		            </td>
		        </tr>
		              
              <tr>
              	<td align="right" width="210">
              	买强制转让：<input type="text" size="10" id="forceCloseFeeRate_B" name="entity.forceCloseFeeRate_B" value="${entity.forceCloseFeeRate_B }"
              		maxlength="11" style="ime-mode:disabled; width: 80" onkeypress="return suffixNamePress()" class="validate[required] input_text datepicker" onchange="changeb(this.value)"/>
	  			<span id="forceCloseFeeRate_BPercent">%</span><span class="required">*</span>
	  			</td>
	  	   		<td align="right" >
	  			卖强制转让：<input type="text" size="10" id="forceCloseFeeRate_S" name="entity.forceCloseFeeRate_S" value="${entity.forceCloseFeeRate_S }"
	  				maxlength="11" style="ime-mode:disabled; width: 80" onkeypress="return suffixNamePress()" class="validate[required] input_text datepicker" onchange="changes(this.value)"/>
	  			<span id="forceCloseFeeRate_SPercent">%</span><span class="required">*</span>        
          		</td>
              </tr>
			  <script type="text/javascript">
            	if ("${entity.feeAlgr}" == "1"){
                	$("#feeRate_BPercent").show();$("#feeRate_SPercent").show();
                	$("#historyCloseFeeRate_BPercent").show();$("#historyCloseFeeRate_SPercent").show();
                	$("#todayCloseFeeRate_BPercent").show();$("#todayCloseFeeRate_SPercent").show();
                	$("#forceCloseFeeRate_BPercent").show();$("#forceCloseFeeRate_SPercent").show();
                } else {
                	$("#feeRate_BPercent").hide();$("#feeRate_SPercent").hide();
                	$("#historyCloseFeeRate_BPercent").hide();$("#historyCloseFeeRate_SPercent").hide();
                	$("#todayCloseFeeRate_BPercent").hide();$("#todayCloseFeeRate_SPercent").hide();
                	$("#forceCloseFeeRate_BPercent").hide();$("#forceCloseFeeRate_SPercent").hide();
                }
            </script>
	 
	 
	 <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>    
         
       
        <%
        	String typeSettleFeeAlgr = (String)request.getAttribute("typeSettleFeeAlgr");
        %>
         <tr>
        	<td align="right" >交收手续费算法：<select id="settleFeeAlgr" name="entity.settleFeeAlgr" style="width:80" onchange="settleFeeAlgr_change()">
					<option value=""></option>
				    <option value="1" <c:if test="${entity.settleFeeAlgr==1 }">selected</c:if>>按百分比</option>
					<option value="2" <c:if test="${entity.settleFeeAlgr==2 }">selected</c:if>>按绝对值</option>
			   </select> 
			   <span class="required">*</span>            
            </td>
            <td align="right" width="210">
            	交收买：<input type="text" size="10" id="settleFeeRate_B" name="entity.settleFeeRate_B" maxlength="11" value="${entity.settleFeeRate_B }"
            		style="ime-mode:disabled; width: 80" onkeypress="return suffixNamePress()" class="validate[required] input_text datepicker" onchange="changeb(this.value)"/>
			  <span id="settleFeeRate_BPercent" class="<%if("1".equals(typeSettleFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="required">*</span>
			  </td>
			  <td align="right" >
			  交收卖：<input type="text" size="10" id="settleFeeRate_S" name="entity.settleFeeRate_S" maxlength="11" value="${entity.settleFeeRate_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return suffixNamePress()" class="validate[required] input_text datepicker" onchange="changes(this.value)"/>
			  <span id="settleFeeRate_SPercent" class="<%if("1".equals(typeSettleFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="required">*</span>         
           </td>
        </tr> 
         <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
    <tr>
    	
            <td align="right">
				最低交收手续费金额：<input type="text" id="lowestSettleFee" name="entity.lowestSettleFee" value="${entity.lowestSettleFee }"
					maxlength="11" style="ime-mode:disabled; width: 80" onkeypress="return suffixNamePress()" class="validate[required] input_text datepicker" size="10"/>
			   <span class="required">*</span>            
            </td>	
            <td align="right">&nbsp;
            <!-- 
				最大交易手续费系数：<text id="maxFeeRate" maxlength="15" style="ime-mode:disabled; width: 80" onkeypress="return suffixNamePress()" class="validate[required] input_text datepicker" size="10"/>
			   <span>%</span><span class="required">*</span>            
             -->
            </td>		
    </tr>
        
        </table >
</span>
            </fieldset>
          </td>
        </tr>	
	
				
        	
<!-- 相关交收信息 -->
        <tr class="common">
          <td colspan="4">
            <fieldset>
              <legend>
                <table cellspacing="0" cellpadding="0" border="0" width="700" class="common">
                  <col width="55"></col><col></col><col width="6"></col>
                  <tr>
                    <td><b>保证金</b></td>
                    <td><hr width="639" class="pickList"/></td>
                    <td ><img id="settleinfo_img" src="<c:url value="${skinPath }/image/app/timebargain/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:settleinfo_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="settleinfo">
<table cellSpacing="0" cellPadding="0" width="720" border="0" align="center" class="common">  
           <tr>
            <td align="right" width="170">&nbsp;&nbsp;&nbsp;&nbsp;保证金算法：
            </td>
            
            <td width="98">
               <select id="marginAlgr" name="entity.marginAlgr" style="width:80" onchange="marginPriceType_change();marginAlgr_change()">
					<option value=""></option>
				    <option value="1" <c:if test="${entity.marginAlgr==1 }">selected</c:if>>按百分比</option>
					<option value="2" <c:if test="${entity.marginAlgr==2 }">selected</c:if>>按绝对值</option>
			   </select> 
			   <span class="required">*</span>            
            </td>			
         	<td align="right" width="100" id="td1s">
				保证金计算依据：
			</td>
			<td width="260" id="td2s" colspan="3">
				<select id="marginPriceType" name="entity.marginPriceType" class="validate[required]" style="width:180" >
				   <option value=""></option>
			        <c:if test="${floatingType<3}">
                       <option value="0" <c:if test="${entity.marginPriceType==0 }">selected</c:if>>订立价</option>
                    </c:if>
                    <c:if test="${floatingType>=3}">
                       <option value="1" <c:if test="${entity.marginPriceType==1 }">selected</c:if>>昨结算价</option> 
                       <option value="2" <c:if test="${entity.marginPriceType==2 }">selected</c:if>>盘中按订立价盘后按结算价</option>
                    </c:if>
               	 </select> <span class="required">*</span>
			</td>
            <script type="text/javascript">
            	if ("${entity.marginAlgr}" == "1"){$("#td1s").show();$("#td2s").show();} else {$("#td1s").hide();$("#td2s").hide();}
            </script> 
            </tr>
           <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
            
              
        <tr>
            <td align="right" width="170">第一阶段保证金 生效日期：</td>
			<td >
			    <input type="text" style="width: 80px" id="settleDate1"
					class="validate[required] wdate" maxlength="10"
					name="entity.settleDate1" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${entity.settleDate1 }" />"
					onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
				<span class="required">*</span>
			</td>
			
			<td align="right">
           			 买卖保证金：
            </td>
            <td colspan="3">
             <input type="radio" name="type" value="1" onclick="changeManner(1,'td1','td2','aaa',1);" <c:if test="${entity.type == 1 or entity.type == null}">checked</c:if> style="border:0px;">相同
             
             <input type="radio" name="type" value="2" onclick="changeManner(2,'td1','td2','aaa',1);" <c:if test="${entity.type == 2}">checked</c:if> style="border:0px;">不同
            </td >
        </tr>
        <tr> 
             <td align="right" id="td1">
                <c:choose>
                	<c:when test="${entity.type == 2}">买保证金1：</c:when>
                	<c:otherwise>保证金1：</c:otherwise>
                </c:choose>
             </td>
            <td>
			  <input type="text" size="10" id="marginItem1" name="entity.marginItem1" maxlength="11" value="${entity.marginItem1 }"
			  	class="validate[required,maxSize[15],custom[onlyDoubleSp]] input_text datepicker" style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" />
			  <span id="marginItem1Percent"></span><span class="required">*</span>           
            </td>
            <td align="right" width="112" id="td2">
            	<c:choose>
                	<c:when test="${entity.type == 2}">买担保金1：</c:when>
                	<c:otherwise>担保金1：</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input type="text" size="10" id="marginItemAssure1" name="entity.marginItemAssure1" maxlength="11" value="${entity.marginItemAssure1 }"
			  	class="validate[required,maxSize[15],custom[onlyDoubleSp]] input_text datepicker" style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" />
			  <span id="marginItemAssure1Percent"></span><span class="required">*</span>           
            </td>     
            </tr>
            
	         <tr id="aaa">
	             <td align="right" id="abc2">卖保证金1：</td>
	             <td id="abc1">
				  <input size="10" id="marginItem1_S" name="entity.marginItem1_S" maxlength="11" onkeypress="return onlyNumberInput()" value="${entity.marginItem1_S }"
				  	class="validate[required,maxSize[15],custom[onlyDoubleSp]] input_text datepicker" style="ime-mode:disabled; width: 80" />
				  <span id="marginItem1_SPercent"></span><span class="required">*</span>           
	            </td >
	            <td align="right"  id="abc3">
	          		  卖担保金1：</td>
	            <td id="abc4">
				  <input size="10" id="marginItemAssure1_S" name="entity.marginItemAssure1_S" maxlength="11" onkeypress="return onlyNumberInput()" value="${entity.marginItemAssure1_S }"
				  	class="validate[required,maxSize[15],custom[onlyDoubleSp]] input_text datepicker" style="ime-mode:disabled; width: 80" />
				  <span id="marginItemAssure1_SPercent"></span><span class="required">*</span>           
	            </td>   
	        </tr>
        	<script type="text/javascript">
            	if ("${entity.type}" == "2"){$("#aaa").show();} else {$("#aaa").hide();}
            </script>
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
  
        <tr>
            <td align="right" >第二阶段保证金 生效日期：</td>
			<td>
				<input type="text" style="width: 80px" id="settleDate2"
					class="wdate" maxlength="10"
					name="entity.settleDate2" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${entity.settleDate2 }" />"
					onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">			
			</td>
			<td align="right">
          		  买卖保证金：
            </td>
		<td colspan="3">
             <input type="radio" name="type1" value="1" onclick="changeManner(1,'td3','td4','aaa1',2);" <c:if test="${entity.type1 == 1 or entity.type1 == null}">checked</c:if> style="border:0px;">相同
             
             <input type="radio" name="type1" value="2" onclick="changeManner(2,'td3','td4','aaa1',2);" <c:if test="${entity.type1 == 2}">checked</c:if> style="border:0px;">不同
            
            </td >
			
			
            </tr>
            <tr>
        	<td align="right" id="td3">
        	    <c:choose>
                	<c:when test="${entity.type1 == 2}">买保证金2：</c:when>
                	<c:otherwise>保证金2：</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input size="10" id="marginItem2" name="entity.marginItem2" maxlength="16" value="${entity.marginItem2 }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />         
            <span id="marginItem2Percent"></span>
            </td> 
            <td align="right" id="td4">
            	<c:choose>
                	<c:when test="${entity.type1 == 2}">买担保金2：</c:when>
                	<c:otherwise>担保金2：</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input size="10" id="marginItemAssure2" name="entity.marginItemAssure2" maxlength="16" value="${entity.marginItemAssure2 }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />         
            <span id="marginItemAssure2Percent"></span>
            </td>  
        
       
        </tr>
        <tr id="aaa1">
            <td align="right"  id="abc21">&nbsp;&nbsp;卖保证金2：</td>
            <td id="abc11">
			  <input size="10" id="marginItem2_S" name="entity.marginItem2_S" maxlength="16" value="${entity.marginItem2_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />         
            <span id="marginItem2_SPercent"></span>
            </td> 
            <td align="right" id="abc31">&nbsp;&nbsp;卖担保金2：</td>
            <td id="abc41">
			  <input size="10" id="marginItemAssure2_S" name="entity.marginItemAssure2_S" maxlength="16" value="${entity.marginItemAssure2_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />         
            <span id="marginItemAssure2_SPercent"></span>
            </td>     
        </tr>
        <script type="text/javascript">
           	if ("${entity.type1}" == "2"){$("#aaa1").show();} else {$("#aaa1").hide();}
        </script>
          <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>  
        
        <tr>
            <td align="right">第三阶段保证金 生效日期：</td>
			<td>
				<input type="text" style="width: 80px" id="settleDate3"
						class="wdate" maxlength="10"
						name="entity.settleDate3" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${entity.settleDate3 }" />"
						onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			</td>
			<td align="right">
            买卖保证金：
            </td>
			<td colspan="3">
             <input type="radio" name="type2" value="1" onclick="changeManner(1,'td5','td6','aaa2',3);" <c:if test="${entity.type2 == 1 or entity.type2 == null}">checked</c:if> style="border:0px;">相同
             <input type="radio" name="type2" value="2" onclick="changeManner(2,'td5','td6','aaa2',3);" <c:if test="${entity.type2 == 2}">checked</c:if> style="border:0px;">不同
            
            </td >
		</tr>
		<tr >
            <td align="right" id="td5">
            	<c:choose>
                	<c:when test="${entity.type2 == 2}">买保证金3：</c:when>
                	<c:otherwise>保证金3：</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input size="10" id="marginItem3" name="entity.marginItem3" maxlength="16" value="${entity.marginItem3 }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />        
            <span id="marginItem3Percent"></span>
            </td>
            <td align="right" id="td6">
            	<c:choose>
                	<c:when test="${entity.type2 == 2}">买担保金3：</c:when>
                	<c:otherwise>担保金3：</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input size="10" id="marginItemAssure3" name="entity.marginItemAssure3" maxlength="16" value="${entity.marginItemAssure3 }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />        
            <span id="marginItemAssure3Percent"></span>
            </td>
			
            </tr>
            
            <tr id="aaa2"> 
			<td align="right" id="abc22">卖保证金3：</td>
            <td id="abc12">
			  <input size="10" id="marginItem3_S" name="entity.marginItem3_S" maxlength="16" value="${entity.marginItem3_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />        
            <span id="marginItem3_SPercent"></span>
            </td>
            <td align="right"  id="abc32">卖担保金3：</td>
            <td id="abc42">
			  <input size="10" id="marginItemAssure3_S" name="entity.marginItemAssure3_S" maxlength="16" value="${entity.marginItemAssure3_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />        
            <span id="marginItemAssure3_SPercent"></span>
            </td>
            </tr>
			<script type="text/javascript">
            	if ("${entity.type2}" == "2"){$("#aaa2").show();} else {$("#aaa2").hide();}
            </script>
			<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        <tr>
            <td align="right" >第四阶段保证金 生效日期：</td>
			<td>
				<input type="text" style="width: 80px" id="settleDate4"
					class="wdate" maxlength="10"
					name="entity.settleDate4" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${entity.settleDate4 }" />"
					onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			</td>
			<td align="right">
            买卖保证金：
            </td>
			<td colspan="3">
            
             <input type="radio" name="type3" value="1" onclick="changeManner(1,'td7','td8','aaa3',4);" <c:if test="${entity.type3 == 1 or entity.type3 == null}">checked</c:if> style="border:0px;">相同
             <input type="radio" name="type3" value="2" onclick="changeManner(2,'td7','td8','aaa3',4);" <c:if test="${entity.type3 == 2}">checked</c:if> style="border:0px;">不同
            </td >
            </tr>
            <tr>
			<td align="right" id="td7">
				<c:choose>
                	<c:when test="${entity.type3 == 2}">买保证金4：</c:when>
                	<c:otherwise>保证金4：</c:otherwise>
                </c:choose>
			</td>
            <td>
			  <input size="10" id="marginItem4" name="entity.marginItem4" maxlength="16" value="${entity.marginItem4 }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />           
            <span id="marginItem4Percent"></span>
            </td>
            <td align="right" id="td8">
            	<c:choose>
                	<c:when test="${entity.type3 == 2}">买担保金4：</c:when>
                	<c:otherwise>担保金4：</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input size="10" id="marginItemAssure4" name="entity.marginItemAssure4" maxlength="16" value="${entity.marginItemAssure4 }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />           
              <span id="marginItemAssure4Percent"></span>
            </td>
         </tr>
         
         <tr id="aaa3">
             <td align="right" id="abc13">卖保证金4：</td>
            <td id="abc23">
			  <input size="10" id="marginItem4_S" name="entity.marginItem4_S" maxlength="16" value="${entity.marginItem4_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />           
            <span id="marginItem4_SPercent"></span>
            </td>
            <td align="right" id="abc33">卖担保金4：</td>
            <td id="abc43">
			  <input size="10" id="marginItemAssure4_S" name="entity.marginItemAssure4_S" maxlength="16" value="${entity.marginItemAssure4_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />           
              <span id="marginItemAssure4_SPercent"></span>
            </td>
        </tr>
        <script type="text/javascript">
            	if ("${entity.type3}" == "2"){$("#aaa3").show();} else {$("#aaa3").hide();}
        </script> 
            <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
           
       <tr>
            <td align="right" >第五阶段保证金 生效日期：</td>
			<td>
				<input type="text" style="width: 80px" id="settleDate5"
						class="wdate" maxlength="10"
						name="entity.settleDate5" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${entity.settleDate5 }" />"
						onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			</td>
			<td align="right">
            买卖保证金：
            </td>
			<td colspan="3">
            <input type="radio" name="type4" value="1" onclick="changeManner(1,'td50','td30','aaa4',5);" <c:if test="${entity.type4 == 1 or entity.type4 == null}">checked</c:if> style="border:0px;">相同
             <input type="radio" name="type4" value="2" onclick="changeManner(2,'td50','td60','aaa4',5);" <c:if test="${entity.type4 == 2}">checked</c:if> style="border:0px;">不同
             
            </td >
            </tr>
            <tr>
			 <td align="right" id="td50">
			 	<c:choose>
                	<c:when test="${entity.type4 == 2}">买保证金5：</c:when>
                	<c:otherwise>保证金5：</c:otherwise>
                </c:choose>
			 </td>
            <td>
			  <input size="10" id="marginItem5" name="entity.marginItem5" maxlength="16" value="${entity.marginItem5 }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />           
            <span id="marginItem5Percent"></span>
            </td>
            <td align="right" id="td60">
            	<c:choose>
                	<c:when test="${entity.type4 == 2}">买担保金5：</c:when>
                	<c:otherwise>担保金5：</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input size="10" id="marginItemAssure5" name="entity.marginItemAssure5" maxlength="16" value="${entity.marginItemAssure5 }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />           
            <span id="marginItemAssure5Percent"></span>
            </td>   
        </tr>
        
         <tr id="aaa4">
            <td align="right" id="abc13">卖保证金5：</td>
            <td id="abc24">
			  <input size="10" id="marginItem5_S" name="entity.marginItem5_S" maxlength="16" value="${entity.marginItem5_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />           
            <span id="marginItem5_SPercent"></span>
            </td>
            <td align="right" id="abc33">卖担保金5：</td>
            <td id="abc44">
			  <input size="10" id="marginItemAssure5_S" name="entity.marginItemAssure5_S" maxlength="16" value="${entity.marginItemAssure5_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[custom[onlyDoubleSp]] input_text datepicker" />           
            <span id="marginItemAssure5_SPercent"></span>
            </td>  
        </tr>
        <script type="text/javascript">
            	if ("${entity.type4}" == "2"){$("#aaa4").show();} else {$("#aaa4").hide();}
        </script>      
            <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
           <tr>
        	<td align="right">
        		<font color="blue">当前生效交易保证金：</font>
        	</td>
        	
        </tr>
        <tr>
        	<td align="right">
        		<font color="blue">买方保证金：</font>
        	</td>
        	<td>
			  <input type="text" size="10" id="marginRate_B" name="entity.marginRate_B" value="${entity.marginRate_B }"
			  	maxlength="16" style="ime-mode:disabled; width: 80" onkeypress="return suffixNamePress()" class="validate[required] input_text datepicker" />         
              <span id="marginRate_BPercent"></span>
            </td>
            <td align="right">
        		<font color="blue">卖方保证金：</font>
        	</td>
        	<td>
			  <input type="text" size="10" id="marginRate_S" name="entity.marginRate_S" value="${entity.marginRate_S }" 
			  	maxlength="16" style="ime-mode:disabled; width: 80" onkeypress="return suffixNamePress()" class="validate[required] input_text datepicker" />         
              <span id="marginRate_SPercent"></span>
            </td>
        </tr>
           
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>    
        
        <tr>
        	<td align="right">
        		交收保证金 生效日期：
        	</td>
        	<td >
        	    <input type="text" style="width: 80px" id="settleDate7"
						class="validate[required] wdate" maxlength="10"
						onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
				<span class="required">*</span>
			</td> 
            <td align="right" id="td9">买方方式：</td>
            <td>
            <select id="settleMarginAlgr_B" name="entity.settleMarginAlgr_B" style="width:80" onchange="settleMarginAlgr_B_change()" class="validate[required]">
					<option value=""></option>
				    <option value="1" <c:if test="${entity.settleMarginAlgr_B == '1' }">selected</c:if>>按百分比</option>
					<option value="2" <c:if test="${entity.settleMarginAlgr_B == '2' }">selected</c:if>>按绝对值</option>
			</select> 
            <span class="required">*</span>
            </td>
            <td align="right" id="td0">买方标准：</td>
            <td>
			  <input size="10" id="settleMarginRate_B" name="entity.settleMarginRate_B" maxlength="16" value="${entity.settleMarginRate_B }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" />         
              <span id="settleMarginRate_BPercent">%</span><span class="required">*</span>
            </td>
            <script type="text/javascript">
            	if ("${entity.settleMarginAlgr_B}" == "1"){$("#settleMarginRate_BPercent").show();} else {$("#settleMarginRate_BPercent").hide();}
            </script> 
        </tr>     
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right"  id="abc14">卖方方式：</td>
            <td>
            <select id="settleMarginAlgr_S" name="entity.settleMarginAlgr_S" style="width:80" class="validate[required]" onchange="settleMarginAlgr_S_change()">
					<option value=""></option>
				    <option value="1" <c:if test="${entity.settleMarginAlgr_S == '1' }">selected</c:if>>按百分比</option>
					<option value="2" <c:if test="${entity.settleMarginAlgr_S == '2' }">selected</c:if>>按绝对值</option>
			</select>
            <span class="required">*</span>
            </td>
            <td align="right"  id="abc34">卖方标准：</td>
            <td>
			  <input size="10" id="settleMarginRate_S" name="entity.settleMarginRate_S" maxlength="16" value="${entity.settleMarginRate_S }"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" />         
              <span id="settleMarginRate_SPercent">%</span><span class="required">*</span>
            </td>
            <script type="text/javascript">
            	if ("${entity.settleMarginAlgr_S}" == "1"){$("#settleMarginRate_SPercent").show();} else {$("#settleMarginRate_SPercent").hide();}
            </script> 
        </tr>
        
        <tr>
        	<td align="right"  id="abc14">交收货款算法：</td>
            <td  >
            <select id="payoutAlgr" name="entity.payoutAlgr" style="width:80" class="validate[required]" onchange="payoutAlgr_change()">
					<option value=""></option>
				    <option value="1" <c:if test="${entity.payoutAlgr == '1' }">selected</c:if>>按百分比</option>
					<option value="2" <c:if test="${entity.payoutAlgr == '2' }">selected</c:if>>按绝对值</option>
			</select>
            <span class="required">*</span>
            </td>
            <td align="right"  id="abc34">交收货款标准：</td>
            <td>
			  <input size="10" id="payoutRate" name="entity.payoutRate" maxlength="16" value="${entity.payoutRate}"
			  	style="ime-mode:disabled; width: 80" onkeypress="return onlyNumberInput()" class="validate[required] input_text datepicker" />         
              <span id="payoutRatePercent">%</span><span class="required">*</span>
            </td>
            <script type="text/javascript">
            	if ("${entity.payoutAlgr}" == "1"){$("#payoutRatePercent").show();} else {$("#payoutRatePercent").hide();}
            </script> 
        </tr>
				<tr>
					<td colspan="4" height="3"></td>
				</tr>
			</table>
		</fieldset>
		</td>
		</tr>
						
		<!-- 延期交易信息 -->
		<%
			String useDelay = (String) request.getAttribute("useDelay");
			if (useDelay.equals("Y")) {
		%>
        <tr class="common">
			<td colspan="4">
				<fieldset>
	              <legend>
	                <table cellspacing="0" cellpadding="0" border="0" width="700" class="common">
	                  <col width="95"></col><col></col><col width="6"></col>
	                  <tr>
	                    <td><b>延期交易信息</b></td>
	                    <td><hr width="599" class="pickList"/></td>
	                    <td ><img id="baseinfo_imgy" src="<c:url value="${skinPath }/image/app/timebargain/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfoy_onclick()"/></td>
	                  </tr>
	                </table>
	              </legend>
					<span id="baseinfoy">
					<table cellSpacing="0" cellPadding="0" width="700" border="0" align="left" class="common">
        
        <tr>
            <td align="right" width="100">买方延期补偿率：</td>
            <td >
            	<input type="text" id="delayRecoupRate" name="entity.delayRecoupRate" maxlength="13" value="${entity.delayRecoupRate }"
            		class="validate[required] input_text datepicker" size="10" style="width:80" onkeypress="suffixNamePress()"/>
            	<span >%</span><span class="required">*</span>
            </td>  
            <td align="right">仓储补偿费：</td>
            <td ><input type="text" id="storeRecoupRate" name="entity.storeRecoupRate" maxlength="13" value="${entity.storeRecoupRate }"
        			class="validate[required] input_text datepicker" style="width:80" size="10" onkeypress="suffixNamePress()"/>
        		<span class="required">*</span></td>
        </tr>
        <tr> 
            <td align="right" width="100">卖方延期补偿率：</td>
            <td >
				<input type="text" id="delayRecoupRate_S" name="entity.delayRecoupRate_S" maxlength="13" value="${entity.delayRecoupRate_S }"
    				class="validate[required] input_text datepicker" style="width:80" size="10" onkeypress="suffixNamePress()"/>
    			<span >%</span><span class="required">*</span></td>
            
            <td align="right">交收方式：</td>     
            <td>  
            	<select id="settleWay" name="entity.settleWay" class="validate[required]" onchange="settleWay_settleDate()">
					<option value=""></option>
                   	<option value="0" <c:if test="${entity.settleWay == '0' }">selected</c:if>>按期交收</option>
                   	<option value="1" <c:if test="${entity.settleWay == '1' }">selected</c:if>>延期交收</option>
         		</select>
				<span class="required">*</span>
			</td>
			
			
        </tr>
        
        <tr>
			<td align="right" width="100">最小交割单位：</td>
		    <td width="115">
				<input type="text" id="minSettleMoveQty" name="entity.minSettleMoveQty" maxlength="10" value="${entity.minSettleMoveQty }" 
					style="ime-mode:disabled; width: 80"  onkeyup="value=value.replace(/[^\d]/g,'')" class="validate[required] input_text datepicker" size="10"/>          
			  	<span class="required">*</span> 
			</td>
			<td align="right">最小交割数量：</td>
 			<td width="115">
				<input type="text" id="minSettleQty" name="entity.minSettleQty" maxlength="10" value="${entity.minSettleQty }"  
					style="ime-mode:disabled; width: 80"  onkeyup="value=value.replace(/[^\d]/g,'')" class="validate[required] input_text datepicker" size="10"/>          
				<span class="required">*</span>  
 			</td>
         </tr>  
         <tr>
            <td align="right">延期费收取方式：</td>     
    		<td width="100">  
				<select id="delayFeeWay" name="entity.delayFeeWay"  class="validate[required]">
					<option value=""></option>
	           		<option value="1" <c:if test="${entity.delayFeeWay == '1' }">selected</c:if>>按自然日逐日收取</option>
	        	</select>
				<span class="required">*</span>
		   </td>
       		<td align="right">递延申报交收价类型：</td>    
			<td>  
				<select id="delaySettlePriceType" name="entity.delaySettlePriceType"  class="validate[required]">
					<option value=""></option>
            		<option value="0" <c:if test="${entity.delaySettlePriceType == '0' }">selected</c:if>>按结算价交收</option>
            		<option value="1" <c:if test="${entity.delaySettlePriceType == '1' }">selected</c:if>>按订立价交收</option>
         		</select>
				<span class="required">*</span>
			</td>
        </tr>
</table >
						</span>
						            </fieldset>
						          </td>
						        </tr>			
		<%
			} else if (useDelay.equals("N")) {
		%>
			<input type="hidden" id="delayRecoupRate" />
			<input type="hidden" id="delayRecoupRate_S" />
			<input type="hidden" id="storeRecoupRate" />
			<input type="hidden" id="settleWay" />
			<input type="hidden" id="delayFeeWay" />
			<input type="hidden" id="minSettleMoveQty" />
			<input type="hidden" id="minSettleQty" />
		<%
			}
		%>
						
						<tr class="common">
					          <td colspan="4">
					            <fieldset>
					              <legend>
					                <table cellspacing="0" cellpadding="0" border="0" width="700" class="common">
					                  <col width="85"></col><col></col><col width="6"></col>
					                  <tr>
					                    <td><b>所属交易节</b></td>
					                    <td><hr width="609" class="pickList"/></td>
					                    <td ><img id="baseinfo_img4" src="<c:url value="${skinPath }/image/app/timebargain/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo4_onclick()"/></td>
					                  </tr>
					                </table>
					              </legend>
					<span id="baseinfo4">
					<table cellSpacing="0" cellPadding="0" width="700" border="0" align="center" class="common">
								
         
        <tr>
        		  <td></td><td></td><td></td>
                  <td>
                    <c:forEach var="tradeTime" items="${listSection}" varStatus="status">
                      <c:if test="${status.count%5==0}"><br /></c:if>
                      <input type="checkbox" name="entity.tradeTime" class="NormalInput" value="${tradeTime.sectionID }"/>
                        
                      <label class="hand">
                      <c:out value="${tradeTime.name}"/>
                      </label>
                    </c:forEach>
                  </td>
                </tr>	
                
                 
</table >
</span>
            </fieldset>
          </td>
        </tr>
               						    	
						<input type="hidden" id="crud" name="crud" value="${request.crud }"/>
						<input type="hidden" id="forceCloseFeeAlgr" name="entity.forceCloseFeeAlgr" value="0" />
						<input type="hidden" id="addedTaxFactor" name="entity.addedTaxFactor"/>  
						<input type="hidden" id="settlePriceType" value="0"/> 
						<input type="hidden" id="maxIssueFeeRate" value="99999"/>
						<input type="hidden" id="maxTradeFeeRate" value="99999"/>
						<input type="hidden" name="entity.marginAssure_B" value="0" />
						<input type="hidden" name="entity.marginAssure_S" value="0" />
						<input type="hidden" id="incomeRate" value="0"/>
						<input type="hidden" id="liquidationRate" value="0"/>
						<input type="hidden" name="entity.reserveCount" value="0"/>
					</form>
			
			
			
		</table>
		 <table cellSpacing="0" cellPadding="0" width="700" border="0" align="center" class="common">
		    <tr>
				<td colspan="4" align="left"> 
				<span style="color: red">提示：手续费算法：百分比按货款计算手续费，绝对值按数量计算手续费</span>
				</td>
			<tr>
			<tr>
				<td colspan="4" align="left"> 
				<span style="color: red">提货单费用价格提前天数：仓单交收费用计算平均价取前n天的平均价</span>
				</td>
			<tr>
			
			</table>

	</body>
</html>
