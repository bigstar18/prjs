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
		    <script language="javascript" src="<%=request.getContextPath()%>/delivery/public/jstools/jquery.js"></script>	
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
    if(tradeBreedRuleForm.crud.value == "create")
    {
      tradeBreedRuleForm.firmID.focus();
    }
    else if(tradeBreedRuleForm.crud.value == "update")
    {

      setReadOnly(tradeBreedRuleForm.firmID);
      setReadOnly(tradeBreedRuleForm.breedID);
    }
}
function on_change(){
	tradeBreedRuleForm.forceCloseFeeAlgr.value =  tradeBreedRuleForm.feeAlgr.value;
	
	if (tradeBreedRuleForm.feeAlgr.value == "1") {
		document.getElementById("feeRate_BPercent").className = "xian";
		document.getElementById("feeRate_SPercent").className = "xian";
		document.getElementById("historyCloseFeeRate_BPercent").className = "xian";
		document.getElementById("historyCloseFeeRate_SPercent").className = "xian";
		document.getElementById("todayCloseFeeRate_BPercent").className = "xian";
		document.getElementById("todayCloseFeeRate_SPercent").className = "xian";
		document.getElementById("forceCloseFeeRate_BPercent").className = "xian";
		document.getElementById("forceCloseFeeRate_SPercent").className = "xian";
	}else {
		document.getElementById("feeRate_BPercent").className = "yin";
		document.getElementById("feeRate_SPercent").className = "yin";
		document.getElementById("historyCloseFeeRate_BPercent").className = "yin";
		document.getElementById("historyCloseFeeRate_SPercent").className = "yin";
		document.getElementById("todayCloseFeeRate_BPercent").className = "yin";
		document.getElementById("todayCloseFeeRate_SPercent").className = "yin";
		document.getElementById("forceCloseFeeRate_BPercent").className = "yin";
		document.getElementById("forceCloseFeeRate_SPercent").className = "yin";
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
   	if (tradeBreedRuleForm.crud.value=="create") {
   	  
   	  if (tradeBreedRuleForm.firmID.value == "") {
    	alert("交易商代码不能为空！");
    	tradeBreedRuleForm.firmID.focus();
      	return false;
      }
      if(trim(tradeBreedRuleForm.breedID.value) == "")
      {
        alert("品种代码不能为空！");
        tradeBreedRuleForm.breedID.focus();
        return false;
      } 
    }
    if (tradeBreedRuleForm.feeRate_S.value == "") {
    	alert("买订立不能为空！");
    	tradeBreedRuleForm.feeRate_S.focus();
      	return false;
    }
    if (tradeBreedRuleForm.feeRate_B.value == "") {
    	alert("卖订立不能为空！");
    	tradeBreedRuleForm.feeRate_B.focus();
      	return false;
    }
     
	if(tradeBreedRuleForm.todayCloseFeeRate_B.value==""){
    	alert("买转让今订货不能为空！");
      tradeBreedRuleForm.todayCloseFeeRate_B.focus();
      return false;
    }  
    if(tradeBreedRuleForm.todayCloseFeeRate_S.value==""){
    	alert("卖转让今订货不能为空！");
      tradeBreedRuleForm.todayCloseFeeRate_S.focus();
      return false;
    }  
	 if(tradeBreedRuleForm.historyCloseFeeRate_B.value==""){
    	alert("买转让历史订货不能为空！");
      tradeBreedRuleForm.historyCloseFeeRate_B.focus();
      return false;
    } 
    if(tradeBreedRuleForm.historyCloseFeeRate_S.value==""){
    	alert("卖转让历史订货不能为空！");
      tradeBreedRuleForm.historyCloseFeeRate_S.focus();
      return false;
    }     
    
    
    if (tradeBreedRuleForm.settleFeeRate_B.value == "") {
    	alert("交收买不能为空！");
    	tradeBreedRuleForm.settleFeeRate_B.focus();
    	return false;
    }
    if (tradeBreedRuleForm.settleFeeRate_S.value == "") {
    	alert("交收卖不能为空！");
    	tradeBreedRuleForm.settleFeeRate_S.focus();
    	return false;
    }
    if (tradeBreedRuleForm.forceCloseFeeAlgr.value == "") {
    	alert("强制转让算法不能为空！");
    	return false;
    }
    if (tradeBreedRuleForm.forceCloseFeeRate_B.value == "") {
    	alert("买强制转让不能为空！");
    	tradeBreedRuleForm.forceCloseFeeRate_B.focus();
    	return false;
    }
    if (tradeBreedRuleForm.forceCloseFeeRate_S.value == "") {
    	alert("卖强制转让不能为空！");
    	tradeBreedRuleForm.forceCloseFeeRate_S.focus();
    	return false;
    }
    if (tradeBreedRuleForm.feeAlgr.value == "") {
    	alert("手续费算法不能为空！");
    	tradeBreedRuleForm.feeAlgr.focus();
    	return false;
    }
  
    
    if (tradeBreedRuleForm.crud.value=="update") {
    	setReadWrite(tradeBreedRuleForm.firmID);
    	setReadWrite(tradeBreedRuleForm.breedID);
    	
    }else if (tradeBreedRuleForm.crud.value == "create") {
    	setReadWrite(tradeBreedRuleForm.firmID);
    	setEnabled(tradeBreedRuleForm.breedID);
    }
    if(confirm("是否级联变动该品种下商品的交易商特殊手续费？")){
    	tradeBreedRuleForm.action="<c:url value='/timebargain/baseinfo/tradeBreedRule.do?funcflg=saveFirmBreedFee&logos=true'/>";
		tradeBreedRuleForm.submit();
	}else{
		tradeBreedRuleForm.submit();
	}
   	if (tradeBreedRuleForm.crud.value=="update") {
    	setReadOnly(tradeBreedRuleForm.firmID);
    	setReadOnly(tradeBreedRuleForm.breedID);
    }else if (tradeBreedRuleForm.crud.value == "create") {
    	setReadOnly(tradeBreedRuleForm.firmID);
    	setDisabled(tradeBreedRuleForm.breedID);
    }
    
    tradeBreedRuleForm.save.disabled = true;

	}
  
}
//cancel
function cancel_onclick()
{
   document.location.href = "<c:url value="/timebargain/baseinfo/tradeBreedRule.do?funcflg=searchFirmBreedFee"/>";
}

//commodity_onchange
function commodity_onchange()
{
  var commodity = tradeBreedRuleForm.breedID.value;
  if(commodity == "")
  {
    return false;
  }
  commodityManager.getCommodityById(commodity,function(commodity)
  {
    if(commodity != null)
    {
      tradeBreedRuleForm.feeAlgr.value = (commodity.feeAlgr);
      if (commodity.feeAlgr == "1") {
      	tradeBreedRuleForm.feeRate_B.value = (commodity.feeRate_B * 100);
      	formatControl2Precision(tradeBreedRuleForm.feeRate_B);
      	tradeBreedRuleForm.feeRate_S.value = (commodity.feeRate_S * 100);
      	formatControl2Precision(tradeBreedRuleForm.feeRate_S);
      }else {
      	tradeBreedRuleForm.feeRate_B.value = commodity.feeRate_B;
      	tradeBreedRuleForm.feeRate_S.value = commodity.feeRate_S;
      }
      
      
      tradeBreedRuleForm.settleFeeAlgr.value = (commodity.settleFeeAlgr);
      if (commodity.forceCloseFeeAlgr == "1") {
      	  tradeBreedRuleForm.settleFeeRate_B.value = (commodity.settleFeeRate_B * 100);
      	  formatControl2Precision(tradeBreedRuleForm.settleFeeRate_B);
      	  tradeBreedRuleForm.settleFeeRate_S.value = (commodity.settleFeeRate_S * 100);
      	  formatControl2Precision(tradeBreedRuleForm.settleFeeRate_S);
      	  tradeBreedRuleForm.forceCloseFeeRate_B.value = (commodity.forceCloseFeeRate_B * 100);
      	  formatControl2Precision(tradeBreedRuleForm.forceCloseFeeRate_B);
	      tradeBreedRuleForm.forceCloseFeeRate_S.value = (commodity.forceCloseFeeRate_S * 100);
	      formatControl2Precision(tradeBreedRuleForm.forceCloseFeeRate_S);
	     
	      tradeBreedRuleForm.todayCloseFeeRate_B.value = (commodity.todayCloseFeeRate_B * 100);
	      formatControl2Precision(tradeBreedRuleForm.todayCloseFeeRate_B);
	      tradeBreedRuleForm.todayCloseFeeRate_S.value = (commodity.todayCloseFeeRate_S * 100);
	      formatControl2Precision(tradeBreedRuleForm.todayCloseFeeRate_S);
	      tradeBreedRuleForm.historyCloseFeeRate_B.value = (commodity.historyCloseFeeRate_B * 100);
	      formatControl2Precision(tradeBreedRuleForm.historyCloseFeeRate_B);
	      tradeBreedRuleForm.historyCloseFeeRate_S.value = (commodity.historyCloseFeeRate_S * 100);
	      formatControl2Precision(tradeBreedRuleForm.historyCloseFeeRate_S);
      }else {
      	  tradeBreedRuleForm.settleFeeRate_B.value = commodity.settleFeeRate_B;
	      tradeBreedRuleForm.settleFeeRate_S.value = commodity.settleFeeRate_S;
	      tradeBreedRuleForm.forceCloseFeeRate_B.value = commodity.forceCloseFeeRate_B;
	      tradeBreedRuleForm.forceCloseFeeRate_S.value = commodity.forceCloseFeeRate_S;
	     
	      tradeBreedRuleForm.todayCloseFeeRate_B.value = commodity.todayCloseFeeRate_B;
	      tradeBreedRuleForm.todayCloseFeeRate_S.value = commodity.todayCloseFeeRate_S;
	      tradeBreedRuleForm.historyCloseFeeRate_B.value = commodity.historyCloseFeeRate_B;
	      tradeBreedRuleForm.historyCloseFeeRate_S.value = commodity.historyCloseFeeRate_S;
      }
      
      tradeBreedRuleForm.forceCloseFeeAlgr.value = (commodity.forceCloseFeeAlgr);
      on_change();
      settleFeeAlgr_change();
      }
  });   
}





//---------------------------start baseinfo-------------------------------
var tmp_baseinfo;
var tmp_baseinfo_up = true;
function baseinfo_onclick()
{
  if (tmp_baseinfo_up)
  {
    tmp_baseinfo_up = false;
    tradeBreedRuleForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo = baseinfo4.innerHTML;
    baseinfo4.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up = true;
    tradeBreedRuleForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
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
    tradeBreedRuleForm.baseinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo2 = baseinfo2.innerHTML;
    baseinfo2.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up2 = true;
    tradeBreedRuleForm.baseinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
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
    tradeBreedRuleForm.baseinfo_img3.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo3 = baseinfo3.innerHTML;
    baseinfo3.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up3 = true;
    tradeBreedRuleForm.baseinfo_img3.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
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
    tradeBreedRuleForm.settleinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_settleinfo2 = settleinfo2.innerHTML;
    settleinfo2.innerHTML = "";
  }
  else
  {
    tmp_settleinfo_up2 = true;
    tradeBreedRuleForm.settleinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    settleinfo2.innerHTML = tmp_settleinfo2;
  }
}



function changeb(){
    if(tradeBreedRuleForm.todayCloseFeeRate_B.value==""){
        tradeBreedRuleForm.todayCloseFeeRate_B.value = tradeBreedRuleForm.feeRate_B.value;
        tradeBreedRuleForm.historyCloseFeeRate_B.value = tradeBreedRuleForm.feeRate_B.value;
    }
 }
 function changes(){
    if(tradeBreedRuleForm.todayCloseFeeRate_S.value==""){
        tradeBreedRuleForm.todayCloseFeeRate_S.value = tradeBreedRuleForm.feeRate_S.value;
        tradeBreedRuleForm.historyCloseFeeRate_S.value = tradeBreedRuleForm.feeRate_S.value;
    }   
 }
 
 function settleFeeAlgr_change(){
	if (tradeBreedRuleForm.settleFeeAlgr.value == "1") {
		document.getElementById("settleFeeRate_BPercent").className = "xian";
		document.getElementById("settleFeeRate_SPercent").className = "xian";
	}else {
		document.getElementById("settleFeeRate_BPercent").className = "yin";
		document.getElementById("settleFeeRate_SPercent").className = "yin";
	}
}
 function dealFirm(){
		var dealerId = tradeBreedRuleForm.firmID.value;
		$.ajax({
			type:"post",
			url:"<c:url value="/timebargain/baseinfo/tradeRule.do?funcflg=getFirmInfo"/>",
			data:"firmId=" + dealerId,
			success : function(data){
						if(data==1){
						  tradeBreedRuleForm.firmID.value = "";
						  $("#hint").html("没有该交易商");
						}else{
						  $("#hint").html("");
						}
			}
		});
}
//-----------------------------end settleinfo-----------------------------
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="400" width="790" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/tradeBreedRule.do?funcflg=saveFirmBreedFee"
						method="POST" styleClass="form" target="ListFrame1">
						<tr class="common">
					          <td colspan="4">
					            <fieldset class="pickList" >
					              <legend>
					                
					                  
					                    <b>设置特殊手续费</b>
					                    
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
											交易商代码：
									</td>
									
									<td width="125">
										<html:text size="10" property="firmID" maxlength="16" style="ime-mode:disabled" styleClass="text" onchange="dealFirm()"/>         
										<span class="req">*</span>
									</td>
									<td width="80">
										<span id="hint" style="color: red"></span>
									</td>
									<td align="right" width="80">
											品种代码：
									</td>
									<c:if test="${param['crud'] == 'create'}">
										<td width="300">
										<!--  onchange="javascript:commodity_onchange()"-->
										<html:select property="breedID">
                                          <html:options collection="breedSelect" property="value" labelProperty="label"/>
                                        </html:select>
										<span class="req">*</span>
									</td>
									</c:if>
									<c:if test="${param['crud'] == 'update'}">
									<td width="337">
									<%
										String breedID = (String)request.getAttribute("breedID");
										String name = (String)request.getAttribute("name");
										System.out.println("================================="+breedID);
									%>
										<%=breedID%>
										<html:hidden property="breedID" value="<%=breedID%>"/>
										</td>
									</c:if>
									
								
									
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

			<%
         		String typeFeeAlgr = (String)request.getAttribute("typeFeeAlgr");
         	%>
         	<tr> 		            
		    <td   rowspan="4" width="250" height="10" valign="top"><div align="right"><span >
					
			交易手续费算法：<html:select property="feeAlgr" style="width:80" onchange="on_change()">
					<html:option value=""></html:option>
				    <html:option value="1">按百分比</html:option>
					<html:option value="2">按绝对值</html:option>
			   </html:select> 
			   <span class="req">*</span>  
			</span></div>
			</td>	            		            
		            <td align="right" rowspan="1" width="210" height="5">
		            买订立：<html:text size="10" property="feeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changeb()"/>
			  <span id="feeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
			  
			  </td>
			  <td align="right" >
			  	卖订立：<html:text size="10" property="feeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changes()"/>
			  <span id="feeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>  
			  </td>
		    </tr>


				  <tr> 		            
		           <td width="210" align="right" height="5">
		           	买转让历史订货：<html:text size="10" property="historyCloseFeeRate_B" maxlength="600" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />
		           	<span id="historyCloseFeeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
					</td>
			  		<td align="right" >
					卖转让历史订货：<html:text size="10" property="historyCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />
					<span id="historyCloseFeeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
					</td>
		          </tr>

				<tr> 		            
		           <td width="210" align="right" height="5">
		           	买转让今订货：<html:text size="10" property="todayCloseFeeRate_B" maxlength="600" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />
		           	<span id="todayCloseFeeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
		           </td>
			  	   <td align="right" >
		           	卖转让今订货：<html:text size="10" property="todayCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />
		           	<span id="todayCloseFeeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
		           </td>
		            </tr>
		            
		            <tr>
		            <td align="right" width="210">
		            	买强制转让：<html:text size="10" property="forceCloseFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changeb()"/>
			  			<span id="forceCloseFeeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
					</td>
			  	    <td align="right" >	
						卖强制转让：<html:text size="10" property="forceCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changes()"/>
			  			<span id="forceCloseFeeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
            		</td>
		            </tr>
	
<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>

								 
		<%
        	String typeSettleFeeAlgr = (String)request.getAttribute("typeSettleFeeAlgr");
        %>
        <tr>
	 <td align="right" >交收手续费算法：
            
				<html:select property="settleFeeAlgr" style="width:80" onchange="settleFeeAlgr_change()">
					<html:option value=""></html:option>
				    <html:option value="1">按百分比</html:option>
					<html:option value="2">按绝对值</html:option>
			   </html:select> 
			   <span class="req">*</span>            
            </td>
     		<td align="right" width="210">
     			交收买：<html:text size="10" property="settleFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changeb()"/>
			  	<span id="settleFeeRate_BPercent" class="<%if("1".equals(typeSettleFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
			</td>
			<td align="right" >  	
				交收卖：<html:text size="10" property="settleFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changes()"/>
				<span id="settleFeeRate_SPercent" class="<%if("1".equals(typeSettleFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
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
						<html:hidden property="crud" />
						<html:hidden property="forceCloseFeeAlgr"/>
					</html:form>
				</td>
			</tr>
			
		</table>
		 
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
