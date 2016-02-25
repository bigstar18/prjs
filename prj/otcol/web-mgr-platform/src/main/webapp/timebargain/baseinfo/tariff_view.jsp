<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.model.LabelValue"%>

		<%
             String type=(String)request.getAttribute("type");
        %>
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
   document.getElementById("feeAlgr").value="${commodityTariffMap.feeAlgr}";
   document.getElementById("settleFeeAlgr").value="${commodityTariffMap.SETTLEFEEALGR}";
   document.getElementById("forceSettleFeeAlgr").value="${commodityTariffMap.FORCECLOSEFEEALGR}";
   on_change();
   settleFeeAlgr_change();
   forceSettleFeeAlgr_change();
}

function on_change(){
	
	if (tradeRuleForm.feeAlgr.value == "1") {
		document.getElementById("feeRate_BPercent").className = "xian";
		document.getElementById("feeRate_SPercent").className = "xian";
		document.getElementById("historyCloseFeeRate_BPercent").className = "xian";
		document.getElementById("historyCloseFeeRate_SPercent").className = "xian";
		document.getElementById("todayCloseFeeRate_BPercent").className = "xian";
		document.getElementById("todayCloseFeeRate_SPercent").className = "xian";
		
		if(${commodityTariffMap.feeAlgr==1}){
		tradeRuleForm.feeRate_B.value = ${commodityTariffMap.feeRate_B*100};
      	  tradeRuleForm.feeRate_S.value = ${commodityTariffMap.FeeRate_S*100};
      	  tradeRuleForm.forceCloseFeeRate_B.value = ${commodityTariffMap.forceCloseFeeRate_B*100};
	      tradeRuleForm.forceCloseFeeRate_S.value = ${commodityTariffMap.forceCloseFeeRate_S*100};
	     
	      tradeRuleForm.todayCloseFeeRate_B.value = ${commodityTariffMap.todayCloseFeeRate_B*100};
	      tradeRuleForm.todayCloseFeeRate_S.value = ${commodityTariffMap.todayCloseFeeRate_S*100};
	      tradeRuleForm.historyCloseFeeRate_B.value = ${commodityTariffMap.historyCloseFeeRate_B*100};
	      tradeRuleForm.historyCloseFeeRate_S.value = ${commodityTariffMap.historyCloseFeeRate_S*100};
	      }
	}else {
		document.getElementById("feeRate_BPercent").className = "yin";
		document.getElementById("feeRate_SPercent").className = "yin";
		document.getElementById("historyCloseFeeRate_BPercent").className = "yin";
		document.getElementById("historyCloseFeeRate_SPercent").className = "yin";
		document.getElementById("todayCloseFeeRate_BPercent").className = "yin";
		document.getElementById("todayCloseFeeRate_SPercent").className = "yin";
		
		
	}
}
//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
		
		if (!tmp_baseinfo_up2) {
			baseinfo2_onclick();
		}
		if (!tmp_baseinfo_up) {
			baseinfo_onclick();
		}
  
    if (tradeRuleForm.feeRate_S.value == "") {
    	alert("买订立不能为空！");
    	tradeRuleForm.feeRate_S.focus();
      	return false;
    }
    if (tradeRuleForm.feeRate_B.value == "") {
    	alert("卖订立不能为空！");
    	tradeRuleForm.feeRate_B.focus();
      	return false;
    }
     
	if(tradeRuleForm.todayCloseFeeRate_B.value==""){
    	alert("买转让今订货不能为空！");
      tradeRuleForm.todayCloseFeeRate_B.focus();
      return false;
    }  
    if(tradeRuleForm.todayCloseFeeRate_S.value==""){
    	alert("卖转让今订货不能为空！");
      tradeRuleForm.todayCloseFeeRate_S.focus();
      return false;
    }  
	 if(tradeRuleForm.historyCloseFeeRate_B.value==""){
    	alert("买转让历史订货不能为空！");
      tradeRuleForm.historyCloseFeeRate_B.focus();
      return false;
    } 
    if(tradeRuleForm.historyCloseFeeRate_S.value==""){
    	alert("卖转让历史订货不能为空！");
      tradeRuleForm.historyCloseFeeRate_S.focus();
      return false;
    }     
    
    
    if (tradeRuleForm.settleFeeRate_B.value == "") {
    	alert("交收买不能为空！");
    	tradeRuleForm.settleFeeRate_B.focus();
    	return false;
    }
    if (tradeRuleForm.settleFeeRate_S.value == "") {
    	alert("交收卖不能为空！");
    	tradeRuleForm.settleFeeRate_S.focus();
    	return false;
    }
    if (tradeRuleForm.forceSettleFeeAlgr.value == "") {
    	alert("强制转让算法不能为空！");
    	return false;
    }
    if (tradeRuleForm.forceCloseFeeRate_B.value == "") {
    	alert("买强制转让不能为空！");
    	tradeRuleForm.forceCloseFeeRate_B.focus();
    	return false;
    }
    if (tradeRuleForm.forceCloseFeeRate_S.value == "") {
    	alert("卖强制转让不能为空！");
    	tradeRuleForm.forceCloseFeeRate_S.focus();
    	return false;
    }
    if (tradeRuleForm.feeAlgr.value == "") {
    	alert("手续费算法不能为空！");
    	tradeRuleForm.feeAlgr.focus();
    	return false;
    }
    
    
    tradeRuleForm.submit();
     
    tradeRuleForm.save.disabled = true;

	}
  
}
//cancel
function cancel_onclick()
{
   document.location.href = "<c:url value="/timebargain/baseinfo/tariff.do?funcflg=getCommodityTariffList&tariffID=${commodityTariffMap.tariffID}&isFirm=true"/>";
}



//---------------------------start baseinfo-------------------------------
var tmp_baseinfo;
var tmp_baseinfo_up = true;
function baseinfo_onclick()
{
  if (tmp_baseinfo_up)
  {
    tmp_baseinfo_up = false;
    tradeRuleForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo = baseinfo4.innerHTML;
    baseinfo4.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up = true;
    tradeRuleForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo4.innerHTML = tmp_baseinfo;
  }
}

var tmp_baseinfo2;
var tmp_baseinfo_up2 = true;
function baseinfo2_onclick()
{
  if (tmp_baseinfo_up2)
  {
    tmp_baseinfo_up2 = false;
    tradeRuleForm.baseinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo2 = baseinfo2.innerHTML;
    baseinfo2.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up2 = true;
    tradeRuleForm.baseinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo2.innerHTML = tmp_baseinfo2;
  }
}

var tmp_baseinfo3;
var tmp_baseinfo_up3 = true;
function baseinfo3_onclick()
{
  if (tmp_baseinfo_up3)
  {
    tmp_baseinfo_up3 = false;
    tradeRuleForm.baseinfo_img3.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo3 = baseinfo3.innerHTML;
    baseinfo3.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up3 = true;
    tradeRuleForm.baseinfo_img3.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo3.innerHTML = tmp_baseinfo3;
  }
}


//-----------------------------end baseinfo-----------------------------
//---------------------------start settleinfo-------------------------------
var tmp_settleinfo2;
var tmp_settleinfo_up2 = true;
function settleinfo2_onclick()
{
  if (tmp_settleinfo_up2)
  {
    tmp_settleinfo_up2 = false;
    tradeRuleForm.settleinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_settleinfo2 = settleinfo2.innerHTML;
    settleinfo2.innerHTML = "";
  }
  else
  {
    tmp_settleinfo_up2 = true;
    tradeRuleForm.settleinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    settleinfo2.innerHTML = tmp_settleinfo2;
  }
}



function changeb(){
    if(tradeRuleForm.todayCloseFeeRate_B.value==""){
        tradeRuleForm.todayCloseFeeRate_B.value = tradeRuleForm.feeRate_B.value;
        tradeRuleForm.historyCloseFeeRate_B.value = tradeRuleForm.feeRate_B.value;
    }
   
 }
 function changes(){
    if(tradeRuleForm.todayCloseFeeRate_S.value==""){
        tradeRuleForm.todayCloseFeeRate_S.value = tradeRuleForm.feeRate_S.value;
        tradeRuleForm.historyCloseFeeRate_S.value = tradeRuleForm.feeRate_S.value;
    }   
 }
 
 function settleFeeAlgr_change(){
	if (tradeRuleForm.settleFeeAlgr.value == "1") {
		document.getElementById("settleFeeRate_BPercent").className = "xian";
		document.getElementById("settleFeeRate_SPercent").className = "xian";
		if(${commodityTariffMap.SETTLEFEEALGR==1}){
		tradeRuleForm.settleFeeRate_B.value = ${commodityTariffMap.settleFeeRate_B*100};
	      tradeRuleForm.settleFeeRate_S.value =${commodityTariffMap.settleFeeRate_S*100};
	      }
	}else {
		document.getElementById("settleFeeRate_BPercent").className = "yin";
		document.getElementById("settleFeeRate_SPercent").className = "yin";
		
	}
}
 function forceSettleFeeAlgr_change(){
	if (tradeRuleForm.forceSettleFeeAlgr.value == "1") {
		document.getElementById("forceCloseFeeRate_BPercent").className = "xian";
		document.getElementById("forceCloseFeeRate_SPercent").className = "xian";
		if(${commodityTariffMap.FORCECLOSEFEEALGR==1}){
		 tradeRuleForm.forceCloseFeeRate_B.value = ${commodityTariffMap.forceCloseFeeRate_B*100};
	      tradeRuleForm.forceCloseFeeRate_S.value = ${commodityTariffMap.forceCloseFeeRate_S*100 };
	      }
	}else {
		document.getElementById("forceCloseFeeRate_BPercent").className = "yin";
		document.getElementById("forceCloseFeeRate_SPercent").className = "yin";
		
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
					<form name="tradeRuleForm" action="${pageContext.request.contextPath}/timebargain/baseinfo/tariff.do?funcflg=saveCommodityTariff"
						method="POST" styleClass="form" >
						<tr class="common">
					          <td colspan="4">
					            <fieldset class="pickList" >
					              <legend>
					                
					                  
					                    <b>设置手续费套餐</b>
					                    
					              </legend>
					<span id="baseinfo">
							<table width="100" border="0" align="center"  class="common" cellpadding="0" cellspacing="2">
					<!-- 基本信息 -->
					        <tr class="common">
					          <td colspan="4">
					            <fieldset>
					              <legend>
					                <table cellspacing="0" cellpadding="0" border="0" width="700" class="common">
					                  <col width="65"></col><col></col><col width="6"></col>
					                  <tr>
					                    <td><b>基本信息</b></td>
					                    <td><hr width="629" class="pickList"/></td>
					                    <td ><img id="baseinfo_img" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo_onclick()"/></td>
					                  </tr>
					                </table>
					              </legend>
					<span id="baseinfo4">
					<table cellSpacing="0" cellPadding="0" width="700" border="0" align="center" class="common">
								<tr>
								
								
									<td align="right">
											套餐代码：
									</td>
									
									<td width="125">
										${commodityTariffMap.tariffID}
										<input type="hidden" name="tariffID" value="${commodityTariffMap.tariffID}"/>
									</td>
									
									<td align="right" width="80">
											商品代码：
									</td>
									<td>
										${commodityTariffMap.commodityID}
										<input type="hidden" name="commodityID" value="${commodityTariffMap.commodityId}"/>
									</td>
	
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
					                  <col width="55"></col><col></col><col width="6"></col>
					                  <tr>
					                    <td><b>手续费</b></td>
					                    <td><hr width="639" class="pickList"/></td>
					                    <td ><img id="baseinfo_img2" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo2_onclick()"/></td>
					                  </tr>
					                </table>
					              </legend>
					<span id="baseinfo2">
					<table cellSpacing="0" cellPadding="0" width="700" border="0" align="center" class="common" >
					<!-- 
					<tr>
					<td>加收率： </td>
					<td>
					<html:text size="10" property="TARIFFRATE" maxlength="11" style="ime-mode:disabled" value="${commodityTariffMap.TARIFFRATE}" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changeRate()"/>
					</td></tr>
					 -->
			
         	<tr> 		            
		    <td   rowspan="4" width="250" height="10" valign="top"><div align="right"><span >
					
			交易手续费算法：<select id="feeAlgr" name="feeAlgr" style="width:80" onchange="on_change()">
				    <option value="1">按百分比</option>
					<option value="2">按绝对值</option>
			   </select> 
			   <span class="req">*</span>  
			</span></div>
			</td>	            		            
		            <td align="right" rowspan="1" width="210" height="5">
		            买订立：<html:text size="10" property="feeRate_B" maxlength="15" style="ime-mode:disabled" value="${commodityTariffMap.feeRate_B}" onkeypress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || /\.\d\d\d\d\d\d$/.test(value))event.returnValue=false" styleClass="text" onchange="changeb()"/>
			  <span id="feeRate_BPercent" >%</span><span class="req">*</span>
			  
			  </td>
			  <td align="right" >
			  	卖订立：<html:text size="10" property="feeRate_S" maxlength="15" style="ime-mode:disabled" value="${commodityTariffMap.feeRate_S}" onkeypress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || /\.\d\d\d\d\d\d$/.test(value))event.returnValue=false" styleClass="text" onchange="changes()"/>
			  <span id="feeRate_SPercent" >%</span><span class="req">*</span>  
			  </td>
		    </tr>


				  <tr> 		            
		           <td width="210" align="right" height="5">
		           	买转让历史订货：<html:text size="10" property="historyCloseFeeRate_B" maxlength="15" style="ime-mode:disabled" value="${commodityTariffMap.historyCloseFeeRate_B}" onkeypress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || /\.\d\d\d\d\d\d$/.test(value))event.returnValue=false" styleClass="text" />
		           	<span id="historyCloseFeeRate_BPercent" >%</span><span class="req">*</span>
					</td>
			  		<td align="right" >
					卖转让历史订货：<html:text size="10" property="historyCloseFeeRate_S" maxlength="15" style="ime-mode:disabled"  value="${commodityTariffMap.historyCloseFeeRate_S}" onkeypress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || /\.\d\d\d\d\d\d$/.test(value))event.returnValue=false" styleClass="text" />
					<span id="historyCloseFeeRate_SPercent" >%</span><span class="req">*</span>
					</td>
		          </tr>

				<tr> 		            
		           <td width="210" align="right" height="5">
		           	买转让今订货：<html:text size="10" property="todayCloseFeeRate_B" maxlength="15" style="ime-mode:disabled"  value="${commodityTariffMap.todayCloseFeeRate_B}" onkeypress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || /\.\d\d\d\d\d\d$/.test(value))event.returnValue=false" styleClass="text" />
		           	<span id="todayCloseFeeRate_BPercent" >%</span><span class="req">*</span>
		           </td>
			  	   <td align="right" >
		           	卖转让今订货：<html:text size="10" property="todayCloseFeeRate_S" maxlength="15" style="ime-mode:disabled" value="${commodityTariffMap.todayCloseFeeRate_S}" onkeypress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || /\.\d\d\d\d\d\d$/.test(value))event.returnValue=false" styleClass="text" />
		           	<span id="todayCloseFeeRate_SPercent" >%</span><span class="req">*</span>
		           </td>
		            </tr>
<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>	
<tr>	            
		             <td align="right" >强制转让手续费算法：
            
				<select  id="forceSettleFeeAlgr" name="forceSettleFeeAlgr" style="width:80" onchange="forceSettleFeeAlgr_change()">
				    <option value="1">按百分比</option>
					<option value="2">按绝对值</option>
			   </select> 
			   <span class="req">*</span>            
            </td>
		            <td align="right" width="210">
		            	买强制转让：<html:text size="10" property="forceCloseFeeRate_B" maxlength="15" style="ime-mode:disabled" value="${commodityTariffMap.forceCloseFeeRate_B}" onkeypress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || /\.\d\d\d\d\d\d$/.test(value))event.returnValue=false" styleClass="text" onchange="changeb()"/>
			  			<span id="forceCloseFeeRate_BPercent" >%</span><span class="req">*</span>
					</td>
			  	    <td align="right" >	
						卖强制转让：<html:text size="10" property="forceCloseFeeRate_S" maxlength="15" style="ime-mode:disabled" value="${commodityTariffMap.forceCloseFeeRate_S}" onkeypress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || /\.\d\d\d\d\d\d$/.test(value))event.returnValue=false" styleClass="text" onchange="changes()"/>
			  			<span id="forceCloseFeeRate_SPercent" >%</span><span class="req">*</span>
            		</td>
		            </tr>
	
<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>

								 
        <tr>
	 <td align="right" >交收手续费算法：
            
				<select id="settleFeeAlgr" name="settleFeeAlgr" style="width:80" onchange="settleFeeAlgr_change()">
				    <option value="1">按百分比</option>
					<option value="2">按绝对值</option>
			   </select> 
			   <span class="req">*</span>            
            </td>
     		<td align="right" width="210">
     			交收买：<html:text size="10" property="settleFeeRate_B" maxlength="15" style="ime-mode:disabled" value="${commodityTariffMap.settleFeeRate_B}" onkeypress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || /\.\d\d\d\d\d\d$/.test(value))event.returnValue=false" styleClass="text" onchange="changeb()"/>
			  	<span id="settleFeeRate_BPercent" >%</span><span class="req">*</span>
			</td>
			<td align="right" >  	
				交收卖：<html:text size="10" property="settleFeeRate_S" maxlength="15" style="ime-mode:disabled" value="${commodityTariffMap.settleFeeRate_S}" onkeypress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || /\.\d\d\d\d\d\d$/.test(value))event.returnValue=false" styleClass="text" onchange="changes()"/>
				<span id="settleFeeRate_SPercent" >%</span><span class="req">*</span>
				
            </td>
            
		</tr>
               
  
           
            </table>
      </span>
		 </fieldset>
			 </td>
				 </tr>
									
						        	
								
								 
								<tr>
									<td colspan="2" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											返回
										</html:button>
									</td>
								</tr>
								
							</table>
						</span>
						            </fieldset>
						          </td>
						        </tr>	
						
					</form>
				</td>
			</tr>
			
		</table>
		 
		<html:javascript formName="tradeRuleForm" cdata="false"
			dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
