<%@ include file="../../common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*"%>

<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
			<script type="text/javascript" src="<c:url value="/timebargain/widgets/dwr/interface/commodityManager.js"/>"></script>
			
		    <script type="text/javascript" src="<c:url value="/timebargain/widgets/dwr/engine.js"/>"></script>
		    <script type="text/javascript" src="<c:url value="/timebargain/widgets/dwr/util.js"/>"></script>	
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
    highlightFormElements();
    
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
	if (confirm("您确定要提交吗？")) {
		
		
    if(trim(frm.commodityID.value) == "")
    {
      alert("商品代码不能为空！");
      frm.commodityID.focus();
      return false;
    } 
    if (frm.feeRate_S.value == "") {
    	alert("买订立不能为空！");
    	frm.feeRate_S.focus();
      	return false;
    }
    if (frm.feeRate_B.value == "") {
    	alert("卖订立不能为空！");
    	frm.feeRate_B.focus();
      	return false;
    }
     
	if(frm.todayCloseFeeRate_B.value==""){
    	alert("买转让今订货不能为空！");
      frm.todayCloseFeeRate_B.focus();
      return false;
    }  
    if(frm.todayCloseFeeRate_S.value==""){
    	alert("卖转让今订货不能为空！");
      frm.todayCloseFeeRate_S.focus();
      return false;
    }  
	 if(frm.historyCloseFeeRate_B.value==""){
    	alert("买转让历史订货不能为空！");
      frm.historyCloseFeeRate_B.focus();
      return false;
    } 
    if(frm.historyCloseFeeRate_S.value==""){
    	alert("卖转让历史订货不能为空！");
      frm.historyCloseFeeRate_S.focus();
      return false;
    }     
    if (frm.settleFeeRate_B.value == "") {
    	alert("交收买不能为空！");
    	frm.settleFeeRate_B.focus();
    	return false;
    }
    if (frm.settleFeeRate_S.value == "") {
    	alert("交收卖不能为空！");
    	frm.settleFeeRate_S.focus();
    	return false;
    }
    if (frm.forceCloseFeeRate_B.value == "") {
    	alert("买强制转让不能为空！");
    	frm.forceCloseFeeRate_B.focus();
    	return false;
    }
    if (frm.forceCloseFeeRate_S.value == "") {
    	alert("卖强制转让不能为空！");
    	frm.forceCloseFeeRate_S.focus();
    	return false;
    }
    if (frm.feeAlgr.value == "") {
    	alert("手续费算法不能为空！");
    	frm.feeAlgr.focus();
    	return false;
    }
  	if (frm.lowestSettleFee.value == "") {
    	alert("最低交收手续费不能为空！");
    	frm.lowestSettleFee.focus();
    	return false;
    }
    if (frm.settleFeeAlgr.value == "") {
    	alert("交收手续费算法不能为空！");
    	frm.settleFeeAlgr.focus();
    	return false;
    }
    
    frm.submit();
    frm.save.disabled = true;

	}
  
}
//cancel
function cancel_onclick()
{
   document.location.href = "<%=basePath%>/timebargain/commodityFee/app/commodityFeeApp.jsp";
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
//-----------------------------end settleinfo-----------------------------
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="400" width="790" align="center">
			<tr>
				<td>
					<form id="frm" action="<%=basePath%>/timebargain/commodityFeeAppSave.spr" method="POST" class="form"  >
						<tr class="common">
					          <td colspan="4">
					            
							<table width="100" border="0" align="center"  class="common" cellpadding="0" cellspacing="2">

						        
			
		<tr class="common">
					          <td colspan="4">
					            <fieldset class="pickList">
					              <legend class="common">
					                <b>手续费</b>
					              </legend>
					<table cellSpacing="0" cellPadding="0" width="700" border="0" align="center" class="common" >
			<tr>
				<td align="right" >
		            商品代码：<input type="text" size="10" onkeypress="chectChar()" name="commodityID" maxlength="11" style="ime-mode:disabled"  class="text" />
			  		<span class="req">*</span>
			  	</td>
				<td align="right" colspan="1">
		           最低交收手续费：<input type="text" size="10"  onkeypress="numberPass()"  name="lowestSettleFee" maxlength="11" style="ime-mode:disabled"  class="text" />
			  		<span class="req">*</span>
			  	</td>
			  	
			  	
			</tr>
         	<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
         	<tr> 		            
		    <td   rowspan="4" width="250" height="10" valign="top">
		    <div align="right">
		    <span >
					
			交易手续费算法：<select name="feeAlgr" style="width:80" onchange="on_change()">
					<option value=""></option>
				    <option value="1">按百分比</option>
					<option value="2">按绝对值</option>
			   </select> 
			   <span class="req">*</span>  
			</span></div>
			</td>	            		            
		            <td align="right" rowspan="1" width="210" height="5">
		            买订立：<input type="text" size="10" name="feeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" class="text" />
			  	<span id="feeRate_BPercent" class=""></span><span class="req">*</span>
			  </td>
			  <td align="right" >
			  	卖订立：<input type="text" size="10" name="feeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" class="text" />
			  <span id="feeRate_SPercent" class=""></span><span class="req">*</span>  
			  </td>
		    </tr>


				  <tr> 		            
		           <td width="210" align="right" height="5">
		           	买转让历史订货：<input type="text" size="10" name="historyCloseFeeRate_B" maxlength="600" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" class="text" />
		           	<span id="historyCloseFeeRate_BPercent" class=""></span><span class="req">*</span>
					</td>
			  		<td align="right" >
					卖转让历史订货：<input type="text" size="10" name="historyCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" class="text" />
					<span id="historyCloseFeeRate_SPercent" class=""></span><span class="req">*</span>
					</td>
		          </tr>

				<tr> 		            
		           <td width="210" align="right" height="5">
		           	买转让今订货：<input type="text" size="10" name="todayCloseFeeRate_B" maxlength="600" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" class="text" />
		           	<span id="todayCloseFeeRate_BPercent" class=""></span><span class="req">*</span>
		           </td>
			  	   <td align="right" >
		           	卖转让今订货：<input type="text" size="10" name="todayCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" class="text" />
		           	<span id="todayCloseFeeRate_SPercent" class=""></span><span class="req">*</span>
		           </td>
		            </tr>
		            
		            <tr>
		            <td align="right" width="210">
		            	买强制转让：<input type="text" size="10" name="forceCloseFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" class="text" />
			  			<span id="forceCloseFeeRate_BPercent" class=""></span><span class="req">*</span>
					</td>
			  	    <td align="right" >	
						卖强制转让：<input type="text" size="10" name="forceCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" class="text" />
			  			<span id="forceCloseFeeRate_SPercent" class=""></span><span class="req">*</span>
            		</td>
		            </tr>
	
<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>

        <tr>
	 <td align="right" >交收手续费算法：
            
				<select name="settleFeeAlgr" style="width:80" onchange="settleFeeAlgr_change()">
					<option value=""></option>
				    <option value="1">按百分比</option>
					<option value="2">按绝对值</option>
			   </select> 
			   <span class="req">*</span>            
            </td>
     		<td align="right" width="210">
     			交收买：<input type="text" size="10" name="settleFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" class="text" />
			  	<span id="settleFeeRate_BPercent" class=""></span><span class="req">*</span>
			</td>
			<td align="right" >  	
				交收卖：<input type="text" size="10" name="settleFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" class="text" />
				<span id="settleFeeRate_SPercent" class=""></span><span class="req">*</span>
            </td>
            
		</tr>
               
  <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
  		<tr>
									<td colspan="4" align="center">
										<input type="button" name="save" value="提交" class="button"
											onclick="javascript:return save_onclick();"/>
											
										<input type="button" name="cancel" value="返回" class="button"
											onclick="javascript:return cancel_onclick();"/>
											
									</td>
								</tr>
           
            </table>
		 </fieldset>
			 </td>
				 </tr>
									
						        	
								
								 
								
								
							</table>
						          </td>
						        </tr>	
					</form>
				</td>
			</tr>
			
		</table>
		 
		<%@ include file="../../common/messages.jsp"%>
	</body>
</html>
