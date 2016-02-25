<%@ include file="../../common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>


<html xmlns:MEBS>
  <head>
	<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		
		
		<IMPORT namespace="MEBS" implementation="<%=basePath%>/timebargain/scripts/calendar.htc">
		
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
	    highlightFormElements();
	    frm.commodityID.focus();
	    
	   //marginAlgr_change();
	    
	    changeManner(1);
	    changeManner1(1);
	    changeManner2(1);
	    changeManner3(1);
	    changeManner4(1);
	    
	    marginPriceType_change();
	}

	function marginPriceType_change(){
	 	var marginAlgr = frm.marginAlgr.value;
	 	var tdName = document.getElementById("td1s");
	 	if (marginAlgr == 1) {
	 		tdName.innerHTML = '��֤��������ݣ�';
	 		document.getElementById("td1s").className = "xian";
	 		document.getElementById("td2s").className = "xian";
	 		frm.marginPriceType.value = "";
	 	}else {
	 		tdName.innerHTML = '';
	 		document.getElementById("td1s").className = "yin";
	 		document.getElementById("td2s").className = "yin";
	 		frm.marginPriceType.value = "0";
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
	
	 function suffixNamePress(){
		  if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47)
		  {
		    event.returnValue=false;
		  }
		  else
		  {
		    event.returnValue=true;
		  }
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
 					frm.save.disalbed = true;
			}
		}
		
		function cancel_onclick(){
			document.location.href = "<%=basePath%>/timebargain/commodityMargin/app/commodityMarginApp.jsp";
		}
</script>
  </head>
  
  <body onload="window_onload()">
  <table width="730" border="0" align="center"  class="common" cellpadding="0" cellspacing="0">
    <tr class="common">
          <td >
            <form id="frm" name="frm" action="<%=basePath%>/timebargain/commodityMarginAppSave.spr" method="POST" class="form">
            <fieldset class="pickList">
              <legend class="common">
                    <b>��֤��</b>
              </legend>
<table cellSpacing="0" cellPadding="0" width="730" border="0" align="center" class="common">  
           <tr>
            <td align="right" width="150">
            	��Ʒ���룺
            </td>
            <td >
            	<input type="text" size="10" name="commodityID"  style="ime-mode:disabled;width:80;" onkeypress="checkChar()" class="text"/>
            	<span class="req">*</span>
            	<script type="text/javascript">
            		function checkChar(){
            			if (event.keyCode == 32)
  						{
    						event.returnValue=false;
  						}
            		}
            	</script>
            </td>
            
            <td align="right" >&nbsp;&nbsp;&nbsp;&nbsp;��֤���㷨��
            </td>
            
            <td width="98">
            <select name="marginAlgr" style="width:80" onchange="marginPriceType_change();marginAlgr_change()">
					<option value=""></option>
				    <option value="1">���ٷֱ�</option>
					<option value="2">������ֵ</option>
			   </select> 
			   <span class="req">*</span>            
            </td>
            			<td align="right" width="120" id="td1s" class="">
											��֤��������ݣ�
									</td>
									<td width="100" id="td2s"  class="">
										<select name="marginPriceType" style="width:80" >
										   <option value=""></option>
				                           <option value="0">������</option>
					                       <option value="1">������</option>
					                       <option value="2">���а�������</option>
			                  	 </select> <span class="req">*</span>
						</td>
            
            
              
              
            </tr>
            
            
           <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
            

        <tr>
            <td align="right" width="150">��һ�׶α�֤�� ��Ч���ڣ�</td>
			<td >
				<MEBS:calendar eltID="settleDate1" eltName="settleDate1" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="" />
				<span class="req">*</span>
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
			  <input type="text" size="10" name="marginItem1" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />
			  <span id="marginItem1Percent"></span><span class="req">*</span>           
            </td>
            <td align="right" width="130" id="td2">�򵣱���1��</td>
            <td width="115">
			  <input type="text" size="10" name="marginItemAssure1" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />
			  <span id="marginItemAssure1Percent"></span><span class="req">*</span>           
            </td>     
            </tr>
           <tr class="" id="aaa">
             <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right"  id="abc2">����֤��1��</td>
            <td  id="abc1" style="">
			  <input type="text" size="10" name="marginItem1_S" maxlength="11"  onkeypress="return suffixNamePress()" class="text" />
			  <span id="marginItem1_SPercent"></span><span class="req">*</span>           
            </td >
            <td align="right"  id="abc3">
            ��������1��</td>
            <td  id="abc4" style="">
			  <input type="text" size="10" name="marginItemAssure1_S" maxlength="11"  onkeypress="return suffixNamePress()" class="text" />
			  <span id="marginItemAssure1_SPercent"></span><span class="req">*</span>           
            </td>
        </tr>
        
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>

        <tr>
            <td align="right" >�ڶ��׶α�֤�� ��Ч���ڣ�</td>
			<td>
				<MEBS:calendar eltID="settleDate2" eltName="settleDate2" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="" />
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
			  <input type="text" size="10" name="marginItem2" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />         
            <span id="marginItem2Percent"></span>
            </td> 
            <td align="right" id="td4">&nbsp;&nbsp;�򵣱���2��</td>
            <td>
			  <input type="text" size="10" name="marginItemAssure2" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />         
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
			  <input type="text" size="10" name="marginItem2_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />         
            <span id="marginItem2_SPercent"></span>
            </td> 
            <td align="right" id="abc31">&nbsp;&nbsp;��������2��</td>
            <td style="" id="abc41">
			  <input type="text" size="10" name="marginItemAssure2_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />         
            <span id="marginItemAssure2_SPercent"></span>
            </td>           
        </tr>
            
          <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>  
               

        <tr>
            <td align="right">�����׶α�֤�� ��Ч���ڣ�</td>
			<td>
				<MEBS:calendar eltID="settleDate3" eltName="settleDate3" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="" />
			
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
			  <input type="text" size="10" name="marginItem3" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />        
            <span id="marginItem3Percent"></span>
            </td>
            <td align="right" id="td6">�򵣱���3��</td>
            <td>
			  <input type="text" size="10" name="marginItemAssure3" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />        
            <span id="marginItemAssure3Percent"></span>
            </td>
            </tr>   
             <tr class="" id="aaa2">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
			<td align="right" id="abc22">����֤��3��</td>
            <td style="" id="abc12">
			  <input type="text" size="10" name="marginItem3_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />        
            <span id="marginItem3_SPercent"></span>
            </td>
            <td align="right"  id="abc32">��������3��</td>
            <td style="" id="abc42">
			  <input type="text" size="10" name="marginItemAssure3_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />        
            <span id="marginItemAssure3_SPercent"></span>
            </td>
            </tr>
			
			<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>

        <tr>
            <td align="right" >���Ľ׶α�֤�� ��Ч���ڣ�</td>
			<td>
				<MEBS:calendar eltID="settleDate4" eltName="settleDate4" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="" />
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
			  <input type="text" size="10" name="marginItem4" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />           
            <span id="marginItem4Percent"></span>
            </td>
            <td align="right" id="td8">�򵣱���4��</td>
            <td>
			  <input type="text" size="10" name="marginItemAssure4" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />           
            <span id="marginItemAssure4Percent"></span>
            </td>
        </tr>
         <tr class="" id="aaa3">
             <td>&nbsp;</td>
             <td>&nbsp;</td>
            <td align="right" id="abc13">����֤��4��</td>
            <td  id="abc23" style="">
			  <input type="text" size="10" name="marginItem4_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />           
            <span id="marginItem4_SPercent"></span>
            </td>
            <td align="right" id="abc33">��������4��</td>
            <td  id="abc43" style="">
			  <input type="text" size="10" name="marginItemAssure4_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />           
            <span id="marginItemAssure4_SPercent"></span>
            </td>
        </tr>
            
            <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
           
       <tr>
            <td align="right" >����׶α�֤�� ��Ч���ڣ�</td>
			<td>
				<MEBS:calendar eltID="settleDate5" eltName="settleDate5" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="" />
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
			  <input type="text" size="10" name="marginItem5" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />           
           <span id="marginItem5Percent"></span>
            </td>
            <td align="right" id="td60">�򵣱���5��</td>
            <td>
			  <input type="text" size="10" name="marginItemAssure5" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />           
            <span id="marginItemAssure5Percent"></span>
            </td>
        </tr>
         <tr class="" id="aaa4">
             <td>&nbsp;</td>
             <td>&nbsp;</td>
            <td align="right" id="abc14">����֤��5��</td>
            <td  id="abc24" style="visibility:;">
			  <input type="text" size="10" name="marginItem5_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />           
            <span id="marginItem5_SPercent"></span>
            </td>
            <td align="right" id="abc34">��������5��</td>
            <td  id="abc44" style="visibility:;">
			  <input type="text" size="10" name="marginItemAssure5_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />           
            <span id="marginItemAssure5_SPercent"></span>
            </td>
        </tr>
            
           
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>    
        
        <tr>
        	<td align="right">
        		���ձ�֤�� ��Ч���ڣ�
        	</td>
        	<td >
				<MEBS:calendar eltID="settleDate7" eltName="settleDate7" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="" />
				<span class="req">*</span>
			</td> 

            <td align="right" id="td9">�򷽷�ʽ��</td>
            <td>
            <select name="settleMarginAlgr_B" style="width:80" onchange="settleMarginAlgr_B_change()">
					<option value=""></option>
				    <option value="1">���ٷֱ�</option>
					<option value="2">������ֵ</option>
			   </select>
            <span class="req">*</span>
            </td>
            <td align="right" id="td0">�򷽱�׼��</td>
            <td>
			  <input type="text" size="10" name="settleMarginRate_B" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />         
            <span id="settleMarginRate_BPercent" class=""></span><span class="req">*</span>
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
            <span class="req">*</span>
            </td>
            <td align="right"  id="abc34">������׼��</td>
            <td>
			  <input type="text" size="10" name="settleMarginRate_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />         
            <span id="settleMarginRate_SPercent" class=""></span><span class="req">*</span>
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
            <span class="req">*</span>
            </td>
            <td align="right"  id="abc34">���ջ����׼��</td>
            <td>
			  <input type="text" size="10" name="payoutRate" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" class="text" />         
            <span id="payoutRatePercent" class=""></span><span class="req">*</span>
            </td>
        </tr>
        
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>    
								<tr>
									<td colspan="6" align="center">
										<input type="button" name="save" value="�ύ" class="button"
											onclick="javascript:return save_onclick();"/>
											
										<input type="button" name="cancel" value="����" class="button"
											onclick="javascript:return cancel_onclick();"/>
											
									</td>
								</tr>
							</table>
						</fieldset>
						</form>
						</td>
						</tr>
						
						</table>
  </body>
</html>
