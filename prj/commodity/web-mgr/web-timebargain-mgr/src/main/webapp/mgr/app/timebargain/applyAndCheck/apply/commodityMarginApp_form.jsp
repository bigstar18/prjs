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
								  alert("����Ʒ���벻���ڣ����޸�");
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
	 		$sel.append("<option value=''>��ѡ��</option>");
	 		$sel.append("<option value='0'>������</option>");
	 		$sel.append("<option value='1'>������</option>");
	 		$sel.append("<option value='2'>���а��������̺󰴽����</option>");
	 		document.getElementById("bzjjsyjkey").style.display ="block";
	 		document.getElementById("bzjjsyjvalue").style.display ="block";
	 	}else {
	 		document.getElementById("bzjjsyjkey").style.display ="none";
	 		document.getElementById("bzjjsyjvalue").style.display ="none";
	 		$sel.append("<option value='0'>������</option>");
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
 		  //�Ȱѷ����ֵĶ��滻�����������ֺ�.
 		  obj.value = obj.value.replace(/[^\d.]/g,"");
 		  //���뱣֤��һ��Ϊ���ֶ�����.
 		  obj.value = obj.value.replace(/^\./g,"");
 		  //��ֻ֤�г���һ��.��û�ж��.
 		  obj.value = obj.value.replace(/\.{2,}/g,".");
 		  //��֤.ֻ����һ�Σ������ܳ�����������
 		  obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
 	}
	 
	 function changeManner(id){ 
		  var td1 = window.td1;
		  var td2 = window.td2;
		  if(id==1)
		  {
		   td1.innerHTML='��֤��1��';
		   td2.innerHTML='������1��';
		   document.getElementById("abc1").style.visibility = "hidden";
		   document.getElementById("abc4").style.visibility = "hidden";
		   document.getElementById("aaa").className = "yin";
		  }
		  else if(id==2)
		  {
		   td1.innerHTML='��֤��1��';
		   td2.innerHTML='�򵣱���1��';
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
		   td1.innerHTML='��֤��2��';
		   td2.innerHTML='������2��';
		   document.getElementById("abc11").style.visibility='hidden';
		   document.getElementById("abc41").style.visibility='hidden';
		   document.getElementById("aaa1").className = "yin";
		  }
		  else if(id==2)
		  {
		   td1.innerHTML='��֤��2��';
		   td2.innerHTML='�򵣱���2��';
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
		   td1.innerHTML='��֤��3��';
		   td2.innerHTML='������3��';
		   document.getElementById("abc12").style.visibility='hidden';
		   document.getElementById("abc42").style.visibility='hidden';
		   document.getElementById("aaa2").className = "yin";
		  }
		  else if(id==2)
		  {
		   td1.innerHTML='��֤��3��';
		   td2.innerHTML='�򵣱���3��';
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
		   td1.innerHTML='��֤��4��';
		   td2.innerHTML='������4��';
		   document.getElementById("abc23").style.visibility='hidden';
		   document.getElementById("abc43").style.visibility='hidden';
		   document.getElementById("aaa3").className = "yin";
		  }
		  else if(id==2)
		  {
		   td1.innerHTML='��֤��4��';
		   td2.innerHTML='�򵣱���4��';
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
		   td1.innerHTML='��֤��5��';
		   td2.innerHTML='������5��';
		   document.getElementById("abc24").style.visibility='hidden';
		   document.getElementById("abc44").style.visibility='hidden';
		   document.getElementById("aaa4").className = "yin";
		  }
		  else if(id==2)
		  {
		   td1.innerHTML='��֤��5��';
		   td2.innerHTML='�򵣱���5��';
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
			if (confirm("��ȷ��Ҫ�ύ��")) {
					
					if (frm.commodityID.value == "") {
				    	alert("��Ʒ���벻��Ϊ�գ�");
				    	frm.commodityID.focus();
				    	return false;
				    }
					if (frm.marginAlgr.value == "") {
				    	alert("��֤���㷨����Ϊ�գ�");
				    	frm.marginAlgr.focus();
				    	return false;
				    }
				    if (frm.marginAlgr.value == "1") {
				    	if (frm.marginPriceType.value == "") {
				    		alert("��֤��������ݲ���Ϊ�գ�");
				    		return false;
				    	}
				    }
				    if (frm.marginItem1.value == "") {
				    	alert("��֤����Ϊ�գ�");
				    	frm.marginItem1.focus();
				    	return false;
				    }
				    if (frm.marginItemAssure1.value == "") {
				    	alert("�򵣱�����Ϊ�գ�");
				    	frm.marginItemAssure1.focus();
				    	return false;
				    }
				    
				    if (frm.type[1].checked) {
				    	if (frm.marginItem1_S.value == "") {
				    		alert("����֤����Ϊ�գ�");
					    	frm.marginItem1_S.focus();
					    	return false;
				    	}
				    	if (frm.marginItemAssure1_S.value == "") {
				    		alert("����������Ϊ�գ�");
					    	frm.marginItemAssure1_S.focus();
					    	return false;
				    	}
				    	
				    }
				    if (frm.settleDate1.value == "") {
				    	alert("��һ�׶α�֤����Ч���ڲ���Ϊ�գ�");
				    	return false;
				    }
				    
				    if (document.getElementById("settleDate7").value == "") {
				 		alert("���ձ�֤����Ч���ڲ���Ϊ�գ�");
				    	return false;
 					}
					if (frm.payoutRate.value == "") {
						alert("���ջ����׼����Ϊ�գ�");
						return false;
					}
					
					if (frm.payoutAlgr.value == "") {
						alert("���ջ����㷨����Ϊ�գ�");
						return false;
					}
					
					if (frm.settleMarginAlgr_S.value == "") {
						alert("������ʽ����Ϊ�գ�");
						return false;
					}
					if (frm.settleMarginAlgr_B.value == "") {
						alert("�򷽷�ʽ����Ϊ�գ�");
						return false;
					}
					if (frm.settleMarginRate_B.value == "") {
				 		alert("�򷽱�׼����Ϊ�գ�");
				 		frm.settleMarginRate_B.focus();
				    	return false;
				 	}
				 	if (frm.settleMarginRate_S.value == "") {
				 		alert("������׼����Ϊ�գ�");
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
									��ܰ��ʾ :��Ʒ��֤������
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
												��֤��
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
<table cellSpacing="0" cellpadding="0" width="100%" align="center" class="table2_style">  
           <tr>
            <td align="right">
            	��Ʒ���룺
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
            
            <td align="right" >��֤���㷨��
            </td>
            
            <td>
            <select name="marginAlgr" style="width:80px;" onchange="marginPriceType_change();marginAlgr_change()">
					<option value=""></option>
				    <option value="1">���ٷֱ�</option>
					<option value="2">������ֵ</option>
			   </select> 
			   <span class="required">*</span>            
            </td>
         	<td align="right"  id="bzjjsyjkey">��֤��������ݣ�</td>
			<td id="bzjjsyjvalue">
				<select name="marginPriceType" style="width:179px;" id="td1s" >
				   <option value=""></option>
            	</select> 
			</td>
            </tr>
            
            
           <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
            

        <tr>
            <td align="right" width="150">��һ�׶α�֤�� ��Ч���ڣ�</td>
			<td >
				<input type="text" style="width: 80px" id="settleDate1"
						class="wdate" maxlength="10" name="settleDate1"
						onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
				<span class="required">*</span>
			</td>
			
			<td align="right">
           			 ������֤��
            </td>
            <td colspan="3">
            
             <input type="radio" name="type" value="1" onclick="changeManner(1);" checked="checked" style="border:0px;">��ͬ
             
             <input type="radio" name="type" value="2" onclick="changeManner(2);"  style="border:0px;">��ͬ
            </td >
			
			
        </tr>
        <tr>
        	<td>&nbsp;</td>
            <td width="100">&nbsp;</td>
            
             <td align="right" id="td1">��֤��1��</td>
            <td>
			  <input type="text" size="10" name="marginItem1" maxlength="11" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />
			  <span id="marginItem1Percent"></span><span class="required">*</span>           
            </td>
            <td align="right" width="130" id="td2">�򵣱���1��</td>
            <td width="115">
			  <input type="text" size="10" name="marginItemAssure1" maxlength="11" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />
			  <span id="marginItemAssure1Percent"></span><span class="required">*</span>           
            </td>     
            </tr>
           <tr class="" id="aaa">
             <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right"  id="abc2">����֤��1��</td>
            <td  id="abc1" style="">
			  <input type="text" size="10" name="marginItem1_S" maxlength="11"  onkeyup="return suffixNamePress(this)" class="text" />
			  <span id="marginItem1_SPercent"></span><span class="required">*</span>           
            </td >
            <td align="right"  id="abc3">
            ��������1��</td>
            <td  id="abc4" style="">
			  <input type="text" size="10" name="marginItemAssure1_S" maxlength="11"  onkeyup="return suffixNamePress(this)" class="text" />
			  <span id="marginItemAssure1_SPercent"></span><span class="required">*</span>           
            </td>
        </tr>
        
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>

        <tr>
            <td align="right" >�ڶ��׶α�֤�� ��Ч���ڣ�</td>
			<td>
				<input type="text" style="width: 80px" id="settleDate2"
						class="wdate" maxlength="10" name="settleDate2"
						onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			</td>
			<td align="right">
            ������֤��
            </td>
			<td colspan="3">
            
             <input type="radio" name="type1" value="1" onclick="changeManner1(1);" checked="checked" style="border:0px;">��ͬ
             
             <input type="radio" name="type1" value="2" onclick="changeManner1(2);"  style="border:0px;">��ͬ
            </td >
			
			
            </tr>
            <tr>
        <td >&nbsp;</td>
        <td>&nbsp;</td>
        <td align="right" id="td3">&nbsp;&nbsp;��֤��2��</td>
            <td>
			  <input type="text" size="10" name="marginItem2" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />         
            <span id="marginItem2Percent"></span>
            </td> 
            <td align="right" id="td4">&nbsp;&nbsp;�򵣱���2��</td>
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
			<td align="right"  id="abc21">&nbsp;&nbsp;����֤��2��</td>
            <td style="" id="abc11">
			  <input type="text" size="10" name="marginItem2_S" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />         
            <span id="marginItem2_SPercent"></span>
            </td> 
            <td align="right" id="abc31">&nbsp;&nbsp;��������2��</td>
            <td style="" id="abc41">
			  <input type="text" size="10" name="marginItemAssure2_S" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />         
            <span id="marginItemAssure2_SPercent"></span>
            </td>           
        </tr>
            
          <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>  
               

        <tr>
            <td align="right">�����׶α�֤�� ��Ч���ڣ�</td>
			<td>
				<input type="text" style="width: 80px" id="settleDate3"
						class="wdate" maxlength="10" name="settleDate3"
						onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			</td>
			<td align="right">
            ������֤��
            </td>
			<td colspan="3">
          
             <input type="radio" name="type2" value="1" onclick="changeManner2(1);" checked="checked" style="border:0px;">��ͬ
             <input type="radio" name="type2" value="2" onclick="changeManner2(2);"  style="border:0px;">��ͬ
            </td >
			
			
			</tr>
			<tr >
            <td>&nbsp;</td>
            <td>&nbsp;</td>
			<td align="right" id="td5">��֤��3��</td>
            <td>
			  <input type="text" size="10" name="marginItem3" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />        
            <span id="marginItem3Percent"></span>
            </td>
            <td align="right" id="td6">�򵣱���3��</td>
            <td>
			  <input type="text" size="10" name="marginItemAssure3" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />        
            <span id="marginItemAssure3Percent"></span>
            </td>
            </tr>   
             <tr class="" id="aaa2">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
			<td align="right" id="abc22">����֤��3��</td>
            <td style="" id="abc12">
			  <input type="text" size="10" name="marginItem3_S" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />        
            <span id="marginItem3_SPercent"></span>
            </td>
            <td align="right"  id="abc32">��������3��</td>
            <td style="" id="abc42">
			  <input type="text" size="10" name="marginItemAssure3_S" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />        
            <span id="marginItemAssure3_SPercent"></span>
            </td>
            </tr>
			
			<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>

        <tr>
            <td align="right" >���Ľ׶α�֤�� ��Ч���ڣ�</td>
			<td>
				<input type="text" style="width: 80px" id="settleDate4"
						class="wdate" maxlength="10" name="settleDate4"
						onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			</td>
			<td align="right">
            ������֤��
            </td>
			<td colspan="3">
            
             <input type="radio" name="type3" value="1" onclick="changeManner3(1);" checked="checked" style="border:0px;">��ͬ
             <input type="radio" name="type3" value="2" onclick="changeManner3(2);"  style="border:0px;">��ͬ
            </td >
			
			
            </tr>
            <tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
            <td align="right" id="td7">��֤��4��</td>
            <td>
			  <input type="text" size="10" name="marginItem4" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />           
            <span id="marginItem4Percent"></span>
            </td>
            <td align="right" id="td8">�򵣱���4��</td>
            <td>
			  <input type="text" size="10" name="marginItemAssure4" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />           
            <span id="marginItemAssure4Percent"></span>
            </td>
        </tr>
         <tr class="" id="aaa3">
             <td>&nbsp;</td>
             <td>&nbsp;</td>
            <td align="right" id="abc13">����֤��4��</td>
            <td  id="abc23" style="">
			  <input type="text" size="10" name="marginItem4_S" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />           
            <span id="marginItem4_SPercent"></span>
            </td>
            <td align="right" id="abc33">��������4��</td>
            <td  id="abc43" style="">
			  <input type="text" size="10" name="marginItemAssure4_S" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />           
            <span id="marginItemAssure4_SPercent"></span>
            </td>
        </tr>
            
            <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
           
       <tr>
            <td align="right" >����׶α�֤�� ��Ч���ڣ�</td>
			<td>
				<input type="text" style="width: 80px" id="settleDate5"
						class="wdate" maxlength="10" name="settleDate5"
						onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			</td>
			<td align="right">
            ������֤��
            </td>
			<td colspan="3">
            
             <input type="radio" name="type4" value="1" onclick="changeManner4(1);" checked="checked" style="border:0px;">��ͬ
             <input type="radio" name="type4" value="2" onclick="changeManner4(2);"  style="border:0px;">��ͬ
            </td >
			
			
            </tr>
            <tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
            <td align="right" id="td50">��֤��5��</td>
            <td>
			  <input type="text" size="10" name="marginItem5" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />           
           <span id="marginItem5Percent"></span>
            </td>
            <td align="right" id="td60">�򵣱���5��</td>
            <td>
			  <input type="text" size="10" name="marginItemAssure5" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />           
            <span id="marginItemAssure5Percent"></span>
            </td>
        </tr>
         <tr class="" id="aaa4">
             <td>&nbsp;</td>
             <td>&nbsp;</td>
            <td align="right" id="abc14">����֤��5��</td>
            <td  id="abc24" style="visibility:;">
			  <input type="text" size="10" name="marginItem5_S" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />           
            <span id="marginItem5_SPercent"></span>
            </td>
            <td align="right" id="abc34">��������5��</td>
            <td  id="abc44" style="visibility:;">
			  <input type="text" size="10" name="marginItemAssure5_S" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />           
            <span id="marginItemAssure5_SPercent"></span>
            </td>
        </tr>
            
           
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>    
        
        <tr>
        	<td align="right">
        		���ձ�֤�� ��Ч���ڣ�
        	</td>
        	<td >
				<input type="text" style="width: 80px" id="settleDate7"
						class="wdate" maxlength="10" name="settleDate7"
						onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
				<span class="required">*</span>
			</td> 

            <td align="right" id="td9">�򷽷�ʽ��</td>
            <td>
            <select name="settleMarginAlgr_B" style="width:80" onchange="settleMarginAlgr_B_change()">
					<option value=""></option>
				    <option value="1">���ٷֱ�</option>
					<option value="2">������ֵ</option>
			   </select>
            <span class="required">*</span>
            </td>
            <td align="right" id="td0">�򷽱�׼��</td>
            <td>
			  <input type="text" size="10" name="settleMarginRate_B" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />         
            <span id="settleMarginRate_BPercent" class=""></span><span class="required">*</span>
            </td>
        </tr>
 
        <tr id="aaa4">
             <td>&nbsp;</td>
             <td>&nbsp;</td>
            <td align="right"  id="abc14">������ʽ��</td>
            <td  >
            <select name="settleMarginAlgr_S" style="width:80" onchange="settleMarginAlgr_S_change()">
					<option value=""></option>
				    <option value="1">���ٷֱ�</option>
					<option value="2">������ֵ</option>
			   </select>
            <span class="required">*</span>
            </td>
            <td align="right"  id="abc34">������׼��</td>
            <td>
			  <input type="text" size="10" name="settleMarginRate_S" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />         
            <span id="settleMarginRate_SPercent" class=""></span><span class="required">*</span>
            </td>
        </tr>

        <tr>
        	<td align="right"  id="abc14">���ջ����㷨��</td>
            <td  >
            <select name="payoutAlgr" style="width:80" onchange="payoutAlgr_change()">
					<option value=""></option>
				    <option value="1">���ٷֱ�</option>
					<option value="2">������ֵ</option>
			   </select>
            <span class="required">*</span>
            </td>
            <td align="right"  id="abc34">���ջ����׼��</td>
            <td>
			  <input type="text" size="10" name="payoutRate" maxlength="16" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />         
            <span id="payoutRatePercent" class=""></span><span class="required">*</span>
            </td>
        </tr>
        
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>    
								<tr>
									<td colspan="6" align="center">
										<rightButton:rightButton name="�ύ" onclick="save_onclick();" className="anniu_btn" action="/timebargain/apply/addCommodityMarginApp.action" id="add"></rightButton:rightButton>
									    &nbsp;&nbsp;
									    <rightButton:rightButton name="����" onclick="cancel_onclick();" className="anniu_btn" action="/timebargain/apply/commodityMarginAppList.action" id="back"></rightButton:rightButton>
											
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
