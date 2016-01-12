<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>		
		<title>
		</title>
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
function window_onload()
{
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
function on_change(){
	
	if (frm.feeAlgr.value == "1") {
		document.getElementById("feeRate_BPercent").innerHTML = "%";
		document.getElementById("feeRate_SPercent").innerHTML = "%";
		document.getElementById("historyCloseFeeRate_BPercent").innerHTML = "%";
		document.getElementById("historyCloseFeeRate_SPercent").innerHTML = "%";
		document.getElementById("todayCloseFeeRate_BPercent").innerHTML = "%";
		document.getElementById("todayCloseFeeRate_SPercent").innerHTML = "%";
		document.getElementById("forceCloseFeeRate_BPercent").innerHTML = "%";
		document.getElementById("forceCloseFeeRate_SPercent").innerHTML = "%";
	}else {
		document.getElementById("feeRate_BPercent").innerHTML = "";
		document.getElementById("feeRate_SPercent").innerHTML = "";
		document.getElementById("historyCloseFeeRate_BPercent").innerHTML = "";
		document.getElementById("historyCloseFeeRate_SPercent").innerHTML = "";
		document.getElementById("todayCloseFeeRate_BPercent").innerHTML = "";
		document.getElementById("todayCloseFeeRate_SPercent").innerHTML = "";
		document.getElementById("forceCloseFeeRate_BPercent").innerHTML = "";
		document.getElementById("forceCloseFeeRate_SPercent").innerHTML = "";
	}
}
//save
function save_onclick()
{
	if (confirm("��ȷ��Ҫ�ύ��")) {
		if (frm.feeRate_B.value == "") {
	    	alert("��������Ϊ�գ�");
	    	frm.feeRate_B.focus();
	      	return false;
	    }	
		
	    if (frm.feeRate_S.value == "") {
	    	alert("����������Ϊ�գ�");
	    	frm.feeRate_S.focus();
	      	return false;
	    }
     
	if(frm.todayCloseFeeRate_B.value==""){
    	alert("��ת�ý񶩻�����Ϊ�գ�");
      frm.todayCloseFeeRate_B.focus();
      return false;
    }  
    if(frm.todayCloseFeeRate_S.value==""){
    	alert("��ת�ý񶩻�����Ϊ�գ�");
      frm.todayCloseFeeRate_S.focus();
      return false;
    }  
	 if(frm.historyCloseFeeRate_B.value==""){
    	alert("��ת����ʷ��������Ϊ�գ�");
      frm.historyCloseFeeRate_B.focus();
      return false;
    } 
    if(frm.historyCloseFeeRate_S.value==""){
    	alert("��ת����ʷ��������Ϊ�գ�");
      frm.historyCloseFeeRate_S.focus();
      return false;
    }     
    if (frm.settleFeeRate_B.value == "") {
    	alert("��������Ϊ�գ�");
    	frm.settleFeeRate_B.focus();
    	return false;
    }
    if (frm.settleFeeRate_S.value == "") {
    	alert("����������Ϊ�գ�");
    	frm.settleFeeRate_S.focus();
    	return false;
    }
    if (frm.forceCloseFeeRate_B.value == "") {
    	alert("��ǿ��ת�ò���Ϊ�գ�");
    	frm.forceCloseFeeRate_B.focus();
    	return false;
    }
    if (frm.forceCloseFeeRate_S.value == "") {
    	alert("��ǿ��ת�ò���Ϊ�գ�");
    	frm.forceCloseFeeRate_S.focus();
    	return false;
    }
    if (frm.feeAlgr.value == "") {
    	alert("�������㷨����Ϊ�գ�");
    	frm.feeAlgr.focus();
    	return false;
    }
  	if (frm.lowestSettleFee.value == "") {
    	alert("��ͽ��������Ѳ���Ϊ�գ�");
    	frm.lowestSettleFee.focus();
    	return false;
    }
    if (frm.settleFeeAlgr.value == "") {
    	alert("�����������㷨����Ϊ�գ�");
    	frm.settleFeeAlgr.focus();
    	return false;
    }
    
    frm.submit();
    frm.add.disabled = true;

	}
  
}
//cancel
function cancle_onclick()
{
   document.location.href = "${basePath}/timebargain/apply/commodityFeeAppList.action";
}

//commodity_onchange
//---------------------------start settleinfo-------------------------------

 
 function settleFeeAlgr_change(){
	if (frm.settleFeeAlgr.value == "1") {
		document.getElementById("settleFeeRate_BPercent").innerHTML = "%";
		document.getElementById("settleFeeRate_SPercent").innerHTML = "%";
	}else {
		document.getElementById("settleFeeRate_BPercent").innerHTML = "";
		document.getElementById("settleFeeRate_SPercent").innerHTML = "";
	}
}
function chectChar(){
  if (event.keyCode == 32)
  {
    event.returnValue=false;
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
//-----------------------------end settleinfo-----------------------------
</script>
	</head>

	<body onload="return window_onload()">
		<table border="0" height="400" width="810" align="center">
			<tr>
				<td>
					<form id="frm" name="frm" action="${basePath}/timebargain/apply/addCommodityFeeApp.action" method="POST" class="form"  >
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :��Ʒ����������
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
												������
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
										
					<table cellSpacing="0" cellpadding="4" width="100%" border="0" align="center" class="table2_style">
					<tr>
						<td align="right" >
				            ��Ʒ���룺<input type="text" size="10" id="commodityID" name="commodityID" maxlength="11" style="ime-mode:disabled"  class="text" />
					  		<span class="required">*</span>
					  	</td>
						<td align="right" colspan="1">
				           ��ͽ��������ѣ�<input type="text" size="10"  name="lowestSettleFee" maxlength="11" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />
					  		<span class="required">*</span>
					  	</td>
					  	
					  	
					</tr>
		         	<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
		         	<tr> 		            
				    <td   rowspan="4" width="250" height="10" valign="top">
				    <div align="right">
				    <span >
							
					�����������㷨��<select name="feeAlgr" style="width:80" onchange="on_change()">
							<option value=""></option>
						    <option value="1">���ٷֱ�</option>
							<option value="2">������ֵ</option>
					   </select> 
					   <span class="required">*</span>  
					</span></div>
					</td>	            		            
				            <td align="right" rowspan="1" width="210" height="5">
				            ������<input type="text" size="10" name="feeRate_B" maxlength="11" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />
					  	<span id="feeRate_BPercent" class=""></span><span class="required">*</span>
					  </td>
					  <td align="right" >
					  	��������<input type="text" size="10" name="feeRate_S" maxlength="11" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />
					  <span id="feeRate_SPercent" class=""></span><span class="required">*</span>  
					  </td>
				    </tr>


				    <tr> 		            
		            <td width="210" align="right" height="5">
		           	��ת����ʷ������<input type="text" size="10" name="historyCloseFeeRate_B" maxlength="600" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />
		           	<span id="historyCloseFeeRate_BPercent" class=""></span><span class="required">*</span>
					</td>
			  		<td align="right" >
					��ת����ʷ������<input type="text" size="10" name="historyCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />
					<span id="historyCloseFeeRate_SPercent" class=""></span><span class="required">*</span>
					</td>
		            </tr>

				   <tr> 		            
		           <td width="210" align="right" height="5">
		           	��ת�ý񶩻���<input type="text" size="10" name="todayCloseFeeRate_B" maxlength="600" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />
		           	<span id="todayCloseFeeRate_BPercent" class=""></span><span class="required">*</span>
		           </td>
			  	   <td align="right" >
		           	��ת�ý񶩻���<input type="text" size="10" name="todayCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />
		           	<span id="todayCloseFeeRate_SPercent" class=""></span><span class="required">*</span>
		           </td>
		            </tr>
		            
		            <tr>
		            <td align="right" width="210">
		            	��ǿ��ת�ã�<input type="text" size="10" name="forceCloseFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />
			  			<span id="forceCloseFeeRate_BPercent" class=""></span><span class="required">*</span>
					</td>
			  	    <td align="right" >	
						��ǿ��ת�ã�<input type="text" size="10" name="forceCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />
			  			<span id="forceCloseFeeRate_SPercent" class=""></span><span class="required">*</span>
            		</td>
		            </tr>
	
<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>

        <tr>
	 <td align="right" >�����������㷨��
            
				<select name="settleFeeAlgr" style="width:80" onchange="settleFeeAlgr_change()">
					<option value=""></option>
				    <option value="1">���ٷֱ�</option>
					<option value="2">������ֵ</option>
			   </select> 
			   <span class="required">*</span>            
            </td>
     		<td align="right" width="210">
     			������<input type="text" size="10" name="settleFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />
			  	<span id="settleFeeRate_BPercent" class=""></span><span class="required">*</span>
			</td>
			<td align="right" >  	
				��������<input type="text" size="10" name="settleFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeyup="return suffixNamePress(this)" class="text" />
				<span id="settleFeeRate_SPercent" class=""></span><span class="required">*</span>
            </td>
            
		</tr>
               
  <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
  		<tr>
									<td colspan="4" align="center">
										<rightButton:rightButton name="�ύ" onclick="save_onclick();" className="anniu_btn" action="/timebargain/apply/addCommodityFeeApp.action" id="add"></rightButton:rightButton>
									    &nbsp;&nbsp;
									    <rightButton:rightButton name="����" onclick="cancle_onclick();" className="anniu_btn" action="/timebargain/apply/commodityFeeAppList.action" id="back"></rightButton:rightButton>
											
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
