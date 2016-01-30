<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		
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
	function window_onload(){
	   // highlightFormElements();
	    frm.commodityID.focus();
	    
	   //marginAlgr_change();
	    
	    changeManner(1);
	    changeManner1(1);
	    changeManner2(1);
	    changeManner3(1);
	    changeManner4(1);

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
								if(result.opr!="1"){
								  alert("此商品代码不存在，请修改");
								  $("#commodityID").val("");
								  $("#commodityID").focus();
								}
					          }
				});
			}
		});
	}

	function marginPriceType_change(){
		var marginAlgr = frm.marginAlgr.value;
	 	var $sel = $("#td1s");
	 	$sel.html("");
	 	if (marginAlgr == 1) {
	 		$sel.append("<option value=''>请选择</option>");
	 		$sel.append("<option value='0'>订立价</option>");
	 		$sel.append("<option value='1'>昨结算价</option>");
	 		$sel.append("<option value='2'>盘中按订立价盘后按结算价</option>");
	 		document.getElementById("bzjjsyjkey").style.display ="block";
	 		document.getElementById("bzjjsyjvalue").style.display ="block";
	 	}else {
	 		document.getElementById("bzjjsyjkey").style.display ="none";
	 		document.getElementById("bzjjsyjvalue").style.display ="none";
	 		$sel.append("<option value='0'>订立价</option>");
	 	}
 	}
 	
 	function marginAlgr_change(){
		if (frm.marginAlgr.value == "1") {
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
		}
	}
	
 	function suffixNamePress(obj){
 		/*
 		  if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47)
 		  {
 		    event.returnValue=false;
 		  }
 		  else
 		  {
 		    event.returnValue=true;
 		  }*/
 		  //先把非数字的都替换掉，除了数字和.
 		  obj.value = obj.value.replace(/[^\d.]/g,"");
 		  //必须保证第一个为数字而不是.
 		  obj.value = obj.value.replace(/^\./g,"");
 		  //保证只有出现一个.而没有多个.
 		  obj.value = obj.value.replace(/\.{2,}/g,".");
 		  //保证.只出现一次，而不能出现两次以上
 		  obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
 	}
	 
	 function changeManner(id){ 
		  var td1 = window.td1;
		  var td2 = window.td2;
		  if(id==1)
		  {
		   td1.innerHTML='保证金1：';
		   td2.innerHTML='担保金1：';
		   document.getElementById("abc1").style.visibility = "hidden";
		   document.getElementById("abc4").style.visibility = "hidden";
		   document.getElementById("aaa").className = "yin";
		  }
		  else if(id==2)
		  {
		   td1.innerHTML='买保证金1：';
		   td2.innerHTML='买担保金1：';
		   document.getElementById("abc1").style.visibility = "visible";
		   document.getElementById("abc4").style.visibility = "visible";
		   document.getElementById("aaa").className = "xian";
		  }
		  
	  }
	  
		function changeManner1(id){ 
		  var td1 = window.td3;
		  var td2 = window.td4;
		  if(id==1)
		  {
		   td1.innerHTML='保证金2：';
		   td2.innerHTML='担保金2：';
		   document.getElementById("abc11").style.visibility='hidden';
		   document.getElementById("abc41").style.visibility='hidden';
		   document.getElementById("aaa1").className = "yin";
		  }
		  else if(id==2)
		  {
		   td1.innerHTML='买保证金2：';
		   td2.innerHTML='买担保金2：';
		   document.getElementById("abc11").style.visibility='visible';
		   document.getElementById("abc41").style.visibility='visible';
		   document.getElementById("aaa1").className = "xian";
		  }
		  
		} 
		
		function changeManner2(id){ 
		  var td1 = window.td5;
		  var td2 = window.td6;
		  if(id==1)
		  {
		   td1.innerHTML='保证金3：';
		   td2.innerHTML='担保金3：';
		   document.getElementById("abc12").style.visibility='hidden';
		   document.getElementById("abc42").style.visibility='hidden';
		   document.getElementById("aaa2").className = "yin";
		  }
		  else if(id==2)
		  {
		   td1.innerHTML='买保证金3：';
		   td2.innerHTML='买担保金3：';
		   document.getElementById("abc12").style.visibility='visible';
		   document.getElementById("abc42").style.visibility='visible';
		   document.getElementById("aaa2").className = "xian";
		  }
		  
		} 
		
		function changeManner3(id){ 
		  var td1 = window.td7;
		  var td2 = window.td8;
		  if(id==1)
		  {
		   td1.innerHTML='保证金4：';
		   td2.innerHTML='担保金4：';
		   document.getElementById("abc23").style.visibility='hidden';
		   document.getElementById("abc43").style.visibility='hidden';
		   document.getElementById("aaa3").className = "yin";
		  }
		  else if(id==2)
		  {
		   td1.innerHTML='买保证金4：';
		   td2.innerHTML='买担保金4：';
		   document.getElementById("abc23").style.visibility='visible';
		   document.getElementById("abc43").style.visibility='visible';
		   document.getElementById("aaa3").className = "xian";
		  }
		  
		} 
		
		function changeManner4(id){ 
		  var td1 = window.td50;
		  var td2 = window.td60;
		  if(id==1)
		  {
		   td1.innerHTML='保证金5：';
		   td2.innerHTML='担保金5：';
		   document.getElementById("abc24").style.visibility='hidden';
		   document.getElementById("abc44").style.visibility='hidden';
		   document.getElementById("aaa4").className = "yin";
		  }
		  else if(id==2)
		  {
		   td1.innerHTML='买保证金5：';
		   td2.innerHTML='买担保金5：';
		   document.getElementById("abc24").style.visibility='visible';
		   document.getElementById("abc44").style.visibility='visible';
		   document.getElementById("aaa4").className = "xian";
		  }
		  
		}
		
		function settleMarginAlgr_B_change(){
			if (frm.settleMarginAlgr_B.value == "1") {
				document.getElementById("settleMarginRate_BPercent").innerHTML = "%";
			}else {
				document.getElementById("settleMarginRate_BPercent").innerHTML = "";
			}
		}
		
		function settleMarginAlgr_S_change(){
			if (frm.settleMarginAlgr_S.value == "1") {
				document.getElementById("settleMarginRate_SPercent").innerHTML = "%";
			}else {
				document.getElementById("settleMarginRate_SPercent").innerHTML = "";
			}
		}
		
		function payoutAlgr_change(){
			if (frm.payoutAlgr.value == "1") {
				document.getElementById("payoutRatePercent").innerHTML = "%";
			}else {
				document.getElementById("payoutRatePercent").innerHTML = "";
			}
		}
		
		function save_onclick(){
			if (confirm("您确定要提交吗？")) {
					
					if (frm.commodityID.value == "") {
				    	alert("商品代码不能为空！");
				    	frm.commodityID.focus();
				    	return false;
				    }
					if (frm.marginAlgr.value == "") {
				    	alert("保证金算法不能为空！");
				    	frm.marginAlgr.focus();
				    	return false;
				    }
				    if (frm.marginAlgr.value == "1") {
				    	if (frm.marginPriceType.value == "") {
				    		alert("保证金计算依据不能为空！");
				    		return false;
				    	}
				    }
				    if (frm.marginItem1.value == "") {
				    	alert("买保证金不能为空！");
				    	frm.marginItem1.focus();
				    	return false;
				    }
				    if (frm.marginItemAssure1.value == "") {
				    	alert("买担保金不能为空！");
				    	frm.marginItemAssure1.focus();
				    	return false;
				    }
				    
				    if (frm.type[1].checked) {
				    	if (frm.marginItem1_S.value == "") {
				    		alert("卖保证金不能为空！");
					    	frm.marginItem1_S.focus();
					    	return false;
				    	}
				    	if (frm.marginItemAssure1_S.value == "") {
				    		alert("卖担保金不能为空！");
					    	frm.marginItemAssure1_S.focus();
					    	return false;
				    	}
				    	
				    }
				    if (frm.settleDate1.value == "") {
				    	alert("第一阶段保证金生效日期不能为空！");
				    	return false;
				    }
				    
				    if (document.getElementById("settleDate7").value == "") {
				 		alert("交收保证金生效日期不能为空！");
				    	return false;
 					}
					if (frm.payoutRate.value == "") {
						alert("交收货款标准不能为空！");
						return false;
					}
					
					if (frm.payoutAlgr.value == "") {
						alert("交收货款算法不能为空！");
						return false;
					}
					
					if (frm.settleMarginAlgr_S.value == "") {
						alert("卖方方式不能为空！");
						return false;
					}
					if (frm.settleMarginAlgr_B.value == "") {
						alert("买方方式不能为空！");
						return false;
					}
					if (frm.settleMarginRate_B.value == "") {
				 		alert("买方标准不能为空！");
				 		frm.settleMarginRate_B.focus();
				    	return false;
				 	}
				 	if (frm.settleMarginRate_S.value == "") {
				 		alert("卖方标准不能为空！");
				 		frm.settleMarginRate_S.focus();
				    	return false;
 					}
 					frm.submit();
 					frm.add.disalbed = true;
			}
		}
		
		function cancel_onclick(){
			document.location.href = "${basePath }/timebargain/apply/commodityMarginAppList.action";
		}
</script>
  </head>
  
  <body onload="window_onload()">
  <table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
          <td >
            <form id="frm" name="frm" action="${basePath }/timebargain/apply/addCommodityMarginApp.action" method="POST">
           <div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :商品保证金申请
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
												保证金
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
<table cellSpacing="0" cellpadding="0" width="100%" align="center" class="table2_style">  
           <tr>
            <td align="right">
            	商品代码：
            </td>
            <td >
            	<input type="text" size="10" id="commodityID" name="commodityID"  style="ime-mode:disabled;width:80;" onkeypress="checkChar()" class="text"/>
            	<span class="required">*</span>
            	<script type="text/javascript">
            		function checkChar(){
            			if (event.keyCode == 32)
  						{
    						event.returnValue=false;
  						}
            		}
            	</script>
            </td>
            
            <td align="right" >保证金算法：
            </td>
            
            <td>
            <select name="marginAlgr" style="width:80px;" onchange="marginPriceType_change();marginAlgr_change()">
					<option value=""></option>
				    <option value="1">按百分比</option>
					<option value="2">按绝对值</option>
			   </select> 
			   <span class="required">*</span>            
            </td>
         	<td align="right"  id="bzjjsyjkey">保证金计算依据：</td>
			<td id="bzjjsyjvalue">
				<select name="marginPriceType" style="width:179px;" id="td1s" >
				   <option value=""></option>
            	</select> 
			</td>
            </tr>
            
            
           <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
            

        <tr>
            <td align="right" width="150">第一阶段保证金 生效日期：</td>
			<td >
				<input type="text" style="width: 80px" id="settleDate1"
						class="wdate" maxlength="10" name="settleDate1"
						onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
				<span class="required">*</span>
			</td>
			
			<td align="right">
           			 买卖保证金：
            </td>
            <td colspan="3">
            
             <input type="radio" name="type" value="1" onclick="changeManner(1);" checked="checked" style="border:0px;">相同
             
             <input type="radio" name="type" value="2" onclick="changeManner(2);"  style="border:0px;">不同
            </td >
			
			
        </tr>
        <tr>
        	<td>&nbsp;</td>
            <td width="100">&nbsp;</td>
            
             <td align="right" id="td1">买保证金1：</td>
            <td>
			  <input type="text" size="10" name="marginItem1" maxlength="11" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />
			  <span id="marginItem1Percent"></span><span class="required">*</span>           
            </td>
            <td align="right" width="130" id="td2">买担保金1：</td>
            <td width="115">
			  <input type="text" size="10" name="marginItemAssure1" maxlength="11" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />
			  <span id="marginItemAssure1Percent"></span><span class="required">*</span>           
            </td>     
            </tr>
           <tr class="" id="aaa">
             <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right"  id="abc2">卖保证金1：</td>
            <td  id="abc1" style="">
			  <input type="text" size="10" name="marginItem1_S" maxlength="11"  onkeyup="return suffixNamePress(this)" class="text" />
			  <span id="marginItem1_SPercent"></span><span class="required">*</span>           
            </td >
            <td align="right"  id="abc3">
            卖担保金1：</td>
            <td  id="abc4" style="">
			  <input type="text" size="10" name="marginItemAssure1_S" maxlength="11"  onkeyup="return suffixNamePress(this)" class="text" />
			  <span id="marginItemAssure1_SPercent"></span><span class="required">*</span>           
            </td>
        </tr>
        
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>

        <tr>
            <td align="right" >第二阶段保证金 生效日期：</td>
			<td>
				<input type="text" style="width: 80px" id="settleDate2"
						class="wdate" maxlength="10" name="settleDate2"
						onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			</td>
			<td align="right">
            买卖保证金：
            </td>
			<td colspan="3">
            
             <input type="radio" name="type1" value="1" onclick="changeManner1(1);" checked="checked" style="border:0px;">相同
             
             <input type="radio" name="type1" value="2" onclick="changeManner1(2);"  style="border:0px;">不同
            </td >
			
			
            </tr>
            <tr>
        <td >&nbsp;</td>
        <td>&nbsp;</td>
        <td align="right" id="td3">&nbsp;&nbsp;买保证金2：</td>
            <td>
			  <input type="text" size="10" name="marginItem2" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />         
            <span id="marginItem2Percent"></span>
            </td> 
            <td align="right" id="td4">&nbsp;&nbsp;买担保金2：</td>
            <td>
			  <input type="text" size="10" name="marginItemAssure2" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />         
            <span id="marginItemAssure2Percent"></span>
            </td>  
        </tr>
        <tr class="" id="aaa1">
            <td>&nbsp;</td>
            <td >
            &nbsp;
            </td >
			<td align="right"  id="abc21">&nbsp;&nbsp;卖保证金2：</td>
            <td style="" id="abc11">
			  <input type="text" size="10" name="marginItem2_S" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />         
            <span id="marginItem2_SPercent"></span>
            </td> 
            <td align="right" id="abc31">&nbsp;&nbsp;卖担保金2：</td>
            <td style="" id="abc41">
			  <input type="text" size="10" name="marginItemAssure2_S" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />         
            <span id="marginItemAssure2_SPercent"></span>
            </td>           
        </tr>
            
          <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>  
               

        <tr>
            <td align="right">第三阶段保证金 生效日期：</td>
			<td>
				<input type="text" style="width: 80px" id="settleDate3"
						class="wdate" maxlength="10" name="settleDate3"
						onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			</td>
			<td align="right">
            买卖保证金：
            </td>
			<td colspan="3">
          
             <input type="radio" name="type2" value="1" onclick="changeManner2(1);" checked="checked" style="border:0px;">相同
             <input type="radio" name="type2" value="2" onclick="changeManner2(2);"  style="border:0px;">不同
            </td >
			
			
			</tr>
			<tr >
            <td>&nbsp;</td>
            <td>&nbsp;</td>
			<td align="right" id="td5">买保证金3：</td>
            <td>
			  <input type="text" size="10" name="marginItem3" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />        
            <span id="marginItem3Percent"></span>
            </td>
            <td align="right" id="td6">买担保金3：</td>
            <td>
			  <input type="text" size="10" name="marginItemAssure3" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />        
            <span id="marginItemAssure3Percent"></span>
            </td>
            </tr>   
             <tr class="" id="aaa2">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
			<td align="right" id="abc22">卖保证金3：</td>
            <td style="" id="abc12">
			  <input type="text" size="10" name="marginItem3_S" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />        
            <span id="marginItem3_SPercent"></span>
            </td>
            <td align="right"  id="abc32">卖担保金3：</td>
            <td style="" id="abc42">
			  <input type="text" size="10" name="marginItemAssure3_S" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />        
            <span id="marginItemAssure3_SPercent"></span>
            </td>
            </tr>
			
			<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>

        <tr>
            <td align="right" >第四阶段保证金 生效日期：</td>
			<td>
				<input type="text" style="width: 80px" id="settleDate4"
						class="wdate" maxlength="10" name="settleDate4"
						onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			</td>
			<td align="right">
            买卖保证金：
            </td>
			<td colspan="3">
            
             <input type="radio" name="type3" value="1" onclick="changeManner3(1);" checked="checked" style="border:0px;">相同
             <input type="radio" name="type3" value="2" onclick="changeManner3(2);"  style="border:0px;">不同
            </td >
			
			
            </tr>
            <tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
            <td align="right" id="td7">买保证金4：</td>
            <td>
			  <input type="text" size="10" name="marginItem4" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />           
            <span id="marginItem4Percent"></span>
            </td>
            <td align="right" id="td8">买担保金4：</td>
            <td>
			  <input type="text" size="10" name="marginItemAssure4" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />           
            <span id="marginItemAssure4Percent"></span>
            </td>
        </tr>
         <tr class="" id="aaa3">
             <td>&nbsp;</td>
             <td>&nbsp;</td>
            <td align="right" id="abc13">卖保证金4：</td>
            <td  id="abc23" style="">
			  <input type="text" size="10" name="marginItem4_S" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />           
            <span id="marginItem4_SPercent"></span>
            </td>
            <td align="right" id="abc33">卖担保金4：</td>
            <td  id="abc43" style="">
			  <input type="text" size="10" name="marginItemAssure4_S" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />           
            <span id="marginItemAssure4_SPercent"></span>
            </td>
        </tr>
            
            <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
           
       <tr>
            <td align="right" >第五阶段保证金 生效日期：</td>
			<td>
				<input type="text" style="width: 80px" id="settleDate5"
						class="wdate" maxlength="10" name="settleDate5"
						onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			</td>
			<td align="right">
            买卖保证金：
            </td>
			<td colspan="3">
            
             <input type="radio" name="type4" value="1" onclick="changeManner4(1);" checked="checked" style="border:0px;">相同
             <input type="radio" name="type4" value="2" onclick="changeManner4(2);"  style="border:0px;">不同
            </td >
			
			
            </tr>
            <tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
            <td align="right" id="td50">买保证金5：</td>
            <td>
			  <input type="text" size="10" name="marginItem5" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />           
           <span id="marginItem5Percent"></span>
            </td>
            <td align="right" id="td60">买担保金5：</td>
            <td>
			  <input type="text" size="10" name="marginItemAssure5" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />           
            <span id="marginItemAssure5Percent"></span>
            </td>
        </tr>
         <tr class="" id="aaa4">
             <td>&nbsp;</td>
             <td>&nbsp;</td>
            <td align="right" id="abc14">卖保证金5：</td>
            <td  id="abc24" style="visibility:;">
			  <input type="text" size="10" name="marginItem5_S" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />           
            <span id="marginItem5_SPercent"></span>
            </td>
            <td align="right" id="abc34">卖担保金5：</td>
            <td  id="abc44" style="visibility:;">
			  <input type="text" size="10" name="marginItemAssure5_S" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />           
            <span id="marginItemAssure5_SPercent"></span>
            </td>
        </tr>
            
           
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>    
        
        <tr>
        	<td align="right">
        		交收保证金 生效日期：
        	</td>
        	<td >
				<input type="text" style="width: 80px" id="settleDate7"
						class="wdate" maxlength="10" name="settleDate7"
						onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
				<span class="required">*</span>
			</td> 

            <td align="right" id="td9">买方方式：</td>
            <td>
            <select name="settleMarginAlgr_B" style="width:80" onchange="settleMarginAlgr_B_change()">
					<option value=""></option>
				    <option value="1">按百分比</option>
					<option value="2">按绝对值</option>
			   </select>
            <span class="required">*</span>
            </td>
            <td align="right" id="td0">买方标准：</td>
            <td>
			  <input type="text" size="10" name="settleMarginRate_B" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />         
            <span id="settleMarginRate_BPercent" class=""></span><span class="required">*</span>
            </td>
        </tr>
 
        <tr id="aaa4">
             <td>&nbsp;</td>
             <td>&nbsp;</td>
            <td align="right"  id="abc14">卖方方式：</td>
            <td  >
            <select name="settleMarginAlgr_S" style="width:80" onchange="settleMarginAlgr_S_change()">
					<option value=""></option>
				    <option value="1">按百分比</option>
					<option value="2">按绝对值</option>
			   </select>
            <span class="required">*</span>
            </td>
            <td align="right"  id="abc34">卖方标准：</td>
            <td>
			  <input type="text" size="10" name="settleMarginRate_S" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />         
            <span id="settleMarginRate_SPercent" class=""></span><span class="required">*</span>
            </td>
        </tr>

        <tr>
        	<td align="right"  id="abc14">交收货款算法：</td>
            <td  >
            <select name="payoutAlgr" style="width:80" onchange="payoutAlgr_change()">
					<option value=""></option>
				    <option value="1">按百分比</option>
					<option value="2">按绝对值</option>
			   </select>
            <span class="required">*</span>
            </td>
            <td align="right"  id="abc34">交收货款标准：</td>
            <td>
			  <input type="text" size="10" name="payoutRate" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />         
            <span id="payoutRatePercent" class=""></span><span class="required">*</span>
            </td>
        </tr>
        
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>    
								<tr>
									<td colspan="6" align="center">
										<rightButton:rightButton name="提交" onclick="save_onclick();" className="anniu_btn" action="/timebargain/apply/addCommodityMarginApp.action" id="add"></rightButton:rightButton>
									    &nbsp;&nbsp;
									    <rightButton:rightButton name="返回" onclick="cancel_onclick();" className="anniu_btn" action="/timebargain/apply/commodityMarginAppList.action" id="back"></rightButton:rightButton>
											
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
						</td>
						</tr>
						
						</table>
  </body>
</html>
