<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page import="gnnt.MEBS.timebargain.manage.model.*"%>
<%@ page pageEncoding="GBK"%>
<%
	String type=(String)request.getAttribute("type");
%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/Date.js"/>"></script>		
		<title></title>
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
			function window_onload() {
    			highlightFormElements(); 
    			var trades = new Array();
    			<c:forEach var="trade" items="${breedForm.tradeTimes}" varStatus="status">
      				trades[<c:out value="${status.index}"/>] = "<c:out value="${trade}"/>";
    			</c:forEach>
   				try {
   	  				for (k = 0; k < breedForm.tradeTimes.length; k++) {
    					if (contains(trades, breedForm.tradeTimes[k].value)) {
        					breedForm.tradeTimes[k].checked = true;
      					}
      					if (breedForm.crud.value == "create") {
      						breedForm.tradeTimes[k].checked = true;
      					}
      				}
   				} catch(e) {
   					alert("无交易节！");
				}
    			if(breedForm.crud.value == "create") {
      				breedForm.spreadAlgr.value = ""
					breedForm.limitCmdtyQty.focus();
					breedForm.type101[0].checked = true;
					breedForm.type102[0].checked = true;
					breedForm.type103[0].checked = true;
					breedForm.type104[0].checked = true;
					breedForm.type105[0].checked = true;
					breedForm.limitBreedQty.value = "-1";
					breedForm.limitCmdtyQty.value = "-1";
					breedForm.firmCleanQty.value = "-1";
					breedForm.firmMaxHoldQty.value = "-1";
					breedForm.oneMaxHoldQty.value = "-1";
					if (breedForm.firmMaxHoldQty.value == "-1") {
      					changeManner104(2);
      				}
					setReadOnly(breedForm.limitBreedQty);
					setReadOnly(breedForm.limitCmdtyQty);
					setReadOnly(breedForm.firmCleanQty);
					setReadOnly(breedForm.firmMaxHoldQty);
					setReadOnly(breedForm.oneMaxHoldQty);
					breedForm.minPriceMove.value="1.0";
					breedForm.contractFactor.value="1";
					breedForm.minQuantityMove.value="1";
					breedForm.minSettleMoveQty.value="1";
					breedForm.minSettleQty.value="1";
					breedForm.marginItemAssure1.value = "0";
					breedForm.marginItemAssure2.value = "0";
					breedForm.marginItemAssure3.value = "0";
					breedForm.marginItemAssure4.value = "0";
					breedForm.marginItemAssure5.value = "0";
    			} else if(breedForm.crud.value == "update") {
      				breedForm.limitCmdtyQty.focus();
      				if (breedForm.limitBreedQty.value == "-1") {
      					changeManner101(2);
					} else {
						changeManner101(1);
					}
					if (breedForm.limitCmdtyQty.value == "-1") {
						changeManner102(2);
					} else {
						changeManner102(1);
					}
					if (breedForm.firmCleanQty.value == "-1") {
						changeManner103(2);
					} else {
						changeManner103(1);
					}
					if (breedForm.firmMaxHoldQty.value == "-1") {
						changeManner104(2);
					} else {
						changeManner104(1);
					}
      				if (breedForm.oneMaxHoldQty.value == "-1") {
      					changeManner105(2);
      				} else {
      					changeManner105(1);
      				}
    			} 
    			marginAlgr_change();//保证金百分比
    			var maxHoldDay=document.getElementById("holdDays");
    			<c:if test="${holdDaysLimit == '1'}">
    				maxHoldDay.style.visibility='visible';
			    </c:if>
			    <c:if test="${holdDaysLimit == '0'}">
    				maxHoldDay.style.visibility='hidden';
			    </c:if>
    			//默认延期费收取方式
    			breedForm.delayFeeWay.value = "1";
    			<c:if test="${breedForm.isSettle == 'Y'}">
    				breed();
    			</c:if>
			}


function chackValue(){
    if (event.keyCode==32)
    {
		 event.returnValue=false;
    }
}

function on_change(){
	breedForm.forceCloseFeeAlgr.value = breedForm.feeAlgr.value;
	
	if (breedForm.feeAlgr.value == "1") {
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
	if(!tmp_baseinfo_up)
    {
      baseinfo_onclick();
    }
    if(!tmp_paraminfo_up)
    {
      paraminfo_onclick();
    }
    
    if(!tmp_paraminfo_up6)
    {
      paraminfo6_onclick();
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
	if(!tmp_baseinfo_upy)
    {
      baseinfoy_onclick();
    }
    
    if(breedForm.commoditySetType.value == "0")
    {
     
    }  
    else if(breedForm.commoditySetType.value == "1")
    {
      if(trim(breedForm.contractFactor.value) == "")
      {
	      alert("最小变动价位不能为空！");
	      breedForm.contractFactor.focus();
	      return false;
      }
    }
    if(breedForm.type[1].checked ==true )
      {
	    if(trim(breedForm.marginItem1_S.value) == "")
      {
	      alert("卖保证金1不能为空！");
	      breedForm.marginItem1_S.focus();
	      return false;
      }  
       if(trim(breedForm.marginItemAssure1_S.value) == "")
      {
	      alert("卖担保金1不能为空！");
	      breedForm.marginItemAssure1_S.focus();
	      return false;
      }  
      }
       if (breedForm.minQuantityMove.value <= 0) {
    	alert("最小变动数量应大于0！");
    	breedForm.minQuantityMove.focus();
    	return false;
    }
    if (breedForm.minQuantityMove.value == "") {
    	alert("最小变动数量不能为空！");
    	breedForm.minQuantityMove.focus();
    	return false;
    }
    var holdDaysLimit=document.getElementById("holdDaysLimit").value;
    if(holdDaysLimit==1){
   		 if (breedForm.maxHoldPositionDay.value == "") {
    		alert("最长持仓天数不能为空！");
    		breedForm.maxHoldPositionDay.focus();
    		return false;
    	}
    }	
    if (breedForm.minSettleMoveQty.value <= 0) {
    	alert("最小交割单位应大于0！");
    	breedForm.minSettleMoveQty.focus();
    	return false;
    }
    if (breedForm.minSettleMoveQty.value == "") {
    	alert("最小交割单位不能为空！");
    	breedForm.minSettleMoveQty.focus();
    	return false;
    }
    if (breedForm.minSettleQty.value <= 0) {
    	alert("最小交割数量应大于0！");
    	breedForm.minSettleQty.focus();
    	return false;
    }
    if (breedForm.minSettleQty.value == "") {
    	alert("最小交割数量不能为空！");
    	breedForm.minSettleQty.focus();
    	return false;
    }
    if(breedForm.todayCloseFeeRate_B.value==""){
    	alert("买转让今订货不能为空！");
      breedForm.todayCloseFeeRate_B.focus();
      return false;
    }  
    if(breedForm.todayCloseFeeRate_S.value==""){
    	alert("卖转让今订货不能为空！");
      breedForm.todayCloseFeeRate_S.focus();
      return false;
    }
    if(breedForm.historyCloseFeeRate_B.value==""){
    	alert("买转让历史订货不能为空！");
      breedForm.historyCloseFeeRate_B.focus();
      return false;
    } 
    if(breedForm.historyCloseFeeRate_S.value==""){
    	alert("卖转让历史订货不能为空！");
      breedForm.historyCloseFeeRate_S.focus();
      return false;
    }   
    
    if (breedForm.sortID.value == "") {
    	alert("品种分类不能为空！");
    	breedForm.sortID.focus();
    	return false;
    }
    if (breedForm.breedName.value == "") {
    	alert("品种名称不能为空！");
    	breedForm.breedName.focus();
    	return false;
    }
    if (breedForm.feeAlgr.value == "") {
    	alert("手续费算法不能为空！");
    	breedForm.feeAlgr.focus();
    	return false;
    }
    if (breedForm.feeRate_B.value == "") {
    	alert("买订立不能为空！");
    	breedForm.feeRate_B.focus();
    	return false;
    }
    if (breedForm.feeRate_S.value == "") {
    	alert("卖订立不能为空！");
    	breedForm.feeRate_S.focus();
    	return false;
    }
    if (breedForm.settleFeeAlgr.value == "") {
    	alert("交收手续费不能为空！");
    	breedForm.settleFeeAlgr.focus();
    	return false;
    }
    if (breedForm.settleFeeRate_B.value == "") {
    	alert("交收买不能为空！");
    	breedForm.settleFeeRate_B.focus();
    	return false;
    }
    if (breedForm.settleFeeRate_S.value == "") {
    	alert("交收卖不能为空！");
    	breedForm.settleFeeRate_S.focus();
    	return false;
    }
    if (breedForm.forceCloseFeeAlgr.value == "") {
    	alert("强制转让算法不能为空！");
    	return false;
    }
    if (breedForm.forceCloseFeeRate_B.value == "") {
    	alert("买强制转让不能为空！");
    	breedForm.forceCloseFeeRate_B.focus();
    	return false;
    }
    if (breedForm.forceCloseFeeRate_S.value == "") {
    	alert("卖强制转让不能为空！");
    	breedForm.forceCloseFeeRate_S.focus();
    	return false;
    }
    if (breedForm.spreadAlgr.value == "") {
    	alert("停板涨跌幅算法不能为空！");
    	breedForm.spreadAlgr.focus();
    	return false;
    }
    if (breedForm.spreadUpLmt.value == "") {
    	alert("涨幅上限不能为空！");
    	breedForm.spreadUpLmt.focus();
    	return false;
    }
    if (breedForm.spreadDownLmt.value == "") {
    	alert("涨幅下限不能为空！");
    	breedForm.spreadDownLmt.focus();
    	return false;
    }
    
    //if (breedForm.maxFeeRate.value == "") {
    	//alert("最大交易手续费系数不能为空！");
    	//breedForm.maxFeeRate.focus();
    	//return false;
   // }
    
    if (breedForm.marginAlgr.value == "") {
    	alert("保证金算法不能为空！");
    	breedForm.marginAlgr.focus();
    	return false;
    }
    if (breedForm.marginItem1.value == "") {
    	alert("买保证金1不能为空！");
    	breedForm.marginItem1.focus();
    	return false;
    }
    if (breedForm.marginItemAssure1.value == "") {
    	alert("买担保金1不能为空！");
    	breedForm.marginItemAssure1.focus();
    	return false;
    }
    if (breedForm.contractFactor.value <=0) {
    	alert("单位交易数量须大于0！");
    	breedForm.contractFactor.focus();
    	return false;
    }
     if (breedForm.minPriceMove.value <=0) {
    	alert("最小价位须大于0！");
    	breedForm.minPriceMove.focus();
    	return false;
    }
    if (breedForm.minPriceMove.value == "") {
    	alert("最小价位不能为空！");
    	breedForm.minPriceMove.focus();
    	return false;
    }
    if (breedForm.limitCmdtyQty.value == "") {
    	alert("商品单边最大订货量不能为空！");
    	breedForm.limitCmdtyQty.focus();
    	return false;
    }
    
    if (breedForm.marginAlgr.value == "1") {
    	if (breedForm.marginPriceType.value == "") {
    		alert("保证金计算依据不能为空！");
    		return false;
    	}
    }
    
    	
    if (breedForm.addedTax.value == "") {
    	alert("增值税率不能为空！");
    	return false;
    	}
    	
    if (breedForm.lowestSettleFee.value == "") {
    	alert("交收手续费最低金额不能为空！");
    	breedForm.lowestSettleFee.focus();
    	return false;
    }
    if (breedForm.limitBreedQty.value == "") {
    	alert("品种单边最大订货量不能为空！");
    	breedForm.limitBreedQty.focus();
    	return false;
    }
    if (breedForm.firmCleanQty.value == "") {
    	alert("交易商净订货量不能为空！");
    	breedForm.firmCleanQty.focus();
    	return false;
    }
    if (breedForm.oneMaxHoldQty.value == "") {
    	alert("单笔最大委托量不能为空！");
    	breedForm.oneMaxHoldQty.focus();
    	return false;
    }
 	
 	
 	if (breedForm.settleMarginRate_B.value == "") {
 		alert("买方标准不能为空！");
 		breedForm.settleMarginRate_B.focus();
    	return false;
 	}
 	if (breedForm.settleMarginRate_S.value == "") {
 		alert("卖方标准不能为空！");
 		breedForm.settleMarginRate_S.focus();
    	return false;
 	}
 	
 	if (breedForm.orderPrivilege.value == "") {
 		alert("委托权限不能为空！");
 		breedForm.orderPrivilege.focus();
    	return false;
 	}
 	
 	if (breedForm.settlePriceType.value == "") {
 		alert("交收价计算方式不能为空！");
 		breedForm.settlePriceType.focus();
    	return false;
 	}	
 	if (breedForm.aheadSettlePriceType.value == "") {
 		alert("提前交收价方式不能为空！");
 		breedForm.aheadSettlePriceType.focus();
    	return false;
 	}
 	
 	//add by yangpei 2011-12-2交收保证金计算方式
 	if (breedForm.settleMarginType.value == "") {
 		alert("交收保证金计算方式不能为空！");
 		breedForm.settleMarginType.focus();
    	return false;
 	}

	if (breedForm.type101[1].checked == true) {
		if (breedForm.limitBreedQty.value < 0) {
			alert("品种单边最大订货量不能为负！");
			return false;
		}
	}
	if (breedForm.type102[1].checked == true) {
		if (breedForm.limitCmdtyQty.value < 0) {
			alert("商品单边最大订货量不能为负！");
			return false;
		}
	}
	if (breedForm.type103[1].checked == true) {
		if (breedForm.firmCleanQty.value < 0) {
			alert("交易商最大净订货量不能为负！");
			return false;
		}
	}
 	if (breedForm.type104[1].checked == true) {
 		if (breedForm.firmMaxHoldQtyAlgr.value == "") {
 			alert("交易商订货量限制算法不能为空！");
 			return false;
 		}
 		if (breedForm.firmMaxHoldQtyAlgr.value == "2") {
 			if (breedForm.firmMaxHoldQty.value == "") {
		 		alert("交易商最大双边订货量不能为空！");
		 		breedForm.firmMaxHoldQty.focus();
		    	return false;
		 	}
		 	if (breedForm.firmMaxHoldQty.value < 0) {
				alert("交易商最大双边订货量不能为负！");
				return false;
			}
 		}else {
 			if (breedForm.startPercentQty.value == "") {
 				alert("商品订货量阀值不能为空！");
 				return false;
 			}
 			if (breedForm.maxPercentLimit.value == "") {
 				alert("订货最大比例不能为空！");
 				return false;
 			}
 			breedForm.firmMaxHoldQty.value = parseInt((breedForm.startPercentQty.value * (breedForm.maxPercentLimit.value/100))+"");
 			if (breedForm.firmMaxHoldQty.value < 1) {
 				alert("阀值与比例设置不正确，乘积最小为1！");
 				return false;
 			}
 		}
	}else {
		breedForm.firmMaxHoldQtyAlgr.value = '2';
		breedForm.startPercentQty.value = '0';
		breedForm.maxPercentLimit.value = '0';
	}
	
	if (breedForm.type105[1].checked == true) {
		if (breedForm.oneMaxHoldQty.value < 0) {
			alert("单笔最大委托量不能为负！");
			return false;
		}
	}
	
	if (breedForm.payoutRate.value == "") {
		alert("交收货款标准不能为空！");
		return false;
	}
	
	if (breedForm.payoutAlgr.value == "") {
		alert("交收货款算法不能为空！");
		return false;
	}
	
	if (breedForm.settleMarginAlgr_S.value == "") {
		alert("卖方方式不能为空！");
		return false;
	}
	
	if (breedForm.settleMarginAlgr_B.value == "") {
		alert("买方方式不能为空！");
		return false;
	}
	
	if (breedForm.delayRecoupRate.value == "") {
		alert("买方延期补偿比率不能为空！");
		return false;
	}
	if (breedForm.delayRecoupRate_S.value == "") {
		alert("卖方延期补偿比率不能为空！");
		return false;
	}
	if (breedForm.storeRecoupRate.value == "") {
		alert("仓储补偿费不能为空！");
		return false;
	}
	if(breedForm.storeRecoupRate.value >= 100){
		alert("仓储补偿费必须小于100");
		return false;
	}
	if (breedForm.settleWay.value == "") {
		alert("交收方式不能为空！");
		return false;
	}
	if (breedForm.delayFeeWay.value == "") {
		alert("延期费收取方式不能为空！");
		return false;
	}

    breedForm.addedTaxFactor.value = (parseFloat(breedForm.addedTax.value)/(parseFloat(breedForm.addedTax.value)+100));
    breedForm.submit();
    breedForm.save.disabled = true;
	}
}

function suffixNamePress()
{
	
  if ((event.keyCode>=46 && event.keyCode<=57) )  //|| (event.keyCode>=65 && event.keyCode<=90) || (event.keyCode>=97 && event.keyCode<=122)
  {
  	
    event.returnValue=true;
  }
  else
  {
    event.returnValue=false;
  }
}
//cancel
function cancel_onclick()
{
    document.location.href = "<c:url value="/timebargain/baseinfo/breed.jsp"/>";
}
//spreadAlgr_onchange()
function spreadAlgr_onchange()
{
	if (breedForm.spreadAlgr.value == "1") {
		document.getElementById("spreadUpLmtPercent").className = "xian";
		document.getElementById("spreadDownLmtPercent").className = "xian";
	}else {
		document.getElementById("spreadUpLmtPercent").className = "yin";
		document.getElementById("spreadDownLmtPercent").className = "yin";
	}
	
    if(breedForm.spreadAlgr.value == "3")
    {
        breedForm.spreadUpLmt.value = 0;
        breedForm.spreadDownLmt.value = 0;
        setReadOnly(breedForm.spreadUpLmt);
        setReadOnly(breedForm.spreadDownLmt);
    } 
    else
    {
        setReadWrite(breedForm.spreadUpLmt);
        setReadWrite(breedForm.spreadDownLmt);
    }   
}

//---------------------------start baseinfo-------------------------------
var tmp_baseinfo;
var tmp_baseinfo_up = true;
function baseinfo_onclick()
{
  if (tmp_baseinfo_up)
  {
    tmp_baseinfo_up = false;
    breedForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo = baseinfo.innerHTML;
    baseinfo.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up = true;
    breedForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo.innerHTML = tmp_baseinfo;
  }
}

var tmp_baseinfo2;
var tmp_baseinfo_up2 = true;
function baseinfo2_onclick()
{
  if (tmp_baseinfo_up2)
  {
    tmp_baseinfo_up2 = false;
    breedForm.baseinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo2 = baseinfo2.innerHTML;
    baseinfo2.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up2 = true;
    breedForm.baseinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
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
    breedForm.baseinfo_img3.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo3 = baseinfo3.innerHTML;
    baseinfo3.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up3 = true;
    breedForm.baseinfo_img3.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo3.innerHTML = tmp_baseinfo3;
  }
}

var tmp_baseinfo4;
var tmp_baseinfo_up4 = true;
function baseinfo4_onclick()
{
  if (tmp_baseinfo_up4)
  {
    tmp_baseinfo_up4 = false;
    breedForm.baseinfo_img4.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo4 = baseinfo4.innerHTML;
    baseinfo4.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up4 = true;
    breedForm.baseinfo_img4.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo4.innerHTML = tmp_baseinfo4;
  }
}

var tmp_baseinfoy;
var tmp_baseinfo_upy = true;
function baseinfoy_onclick()
{
  if (tmp_baseinfo_upy)
  {
    tmp_baseinfo_upy = false;
    breedForm.baseinfo_imgy.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfoy = baseinfoy.innerHTML;
    baseinfoy.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_upy = true;
    breedForm.baseinfo_imgy.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfoy.innerHTML = tmp_baseinfoy;
  }
}
//-----------------------------end baseinfo-----------------------------
//---------------------------start paraminfo-------------------------------
var tmp_paraminfo;
var tmp_paraminfo_up = true;
function paraminfo_onclick()
{
  if (tmp_paraminfo_up)
  {
    tmp_paraminfo_up = false;
    breedForm.paraminfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_paraminfo = paraminfo.innerHTML;
    paraminfo.innerHTML = "";
  }
  else
  {
    tmp_paraminfo_up = true;
    breedForm.paraminfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    paraminfo.innerHTML = tmp_paraminfo;
  }
}

var tmp_paraminfo6;
var tmp_paraminfo_up6 = true;
function paraminfo6_onclick()
{
  if (tmp_paraminfo_up6)
  {
    tmp_paraminfo_up6 = false;
    breedForm.paraminfo_img6.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_paraminfo6 = paraminfo6.innerHTML;
    paraminfo6.innerHTML = "";
  }
  else
  {
    tmp_paraminfo_up6 = true;
    breedForm.paraminfo_img6.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    paraminfo6.innerHTML = tmp_paraminfo6;
  }
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

 function changeb(){
    if(breedForm.todayCloseFeeRate_B.value==""){
        breedForm.todayCloseFeeRate_B.value = breedForm.feeRate_B.value;
        breedForm.historyCloseFeeRate_B.value = breedForm.feeRate_B.value;
    }
 }
 function changes(){
    if(breedForm.todayCloseFeeRate_S.value==""){
        breedForm.todayCloseFeeRate_S.value = breedForm.feeRate_S.value;
        breedForm.historyCloseFeeRate_S.value = breedForm.feeRate_S.value;
    }   
 }
 
 function marginPriceType_change(){
 	var marginAlgr = breedForm.marginAlgr.value;
 	var tdName = document.getElementById("td1s");
 	if (marginAlgr == 1) {
 		tdName.innerHTML = '保证金计算依据：';
 		document.getElementById("td1s").className = "xian";
 		document.getElementById("td2s").className = "xian";
 		breedForm.marginPriceType.value = "";
 	}else {
 		tdName.innerHTML = '';
 		document.getElementById("td1s").className = "yin";
 		document.getElementById("td2s").className = "yin";
 		breedForm.marginPriceType.value = "0";
 		
 	}
 }
 
 function changeManner101(id){
 	if (id == "1") {
 		setReadWrite(breedForm.limitBreedQty);
 	}
 	if (id == "2") {
 		breedForm.limitBreedQty.value = "-1";
 		setReadOnly(breedForm.limitBreedQty);
 	}
 }
 
  function changeManner102(id){
 	if (id == "1") {
 		setReadWrite(breedForm.limitCmdtyQty);
 	}
 	if (id == "2") {
 		breedForm.limitCmdtyQty.value = "-1";
 		setReadOnly(breedForm.limitCmdtyQty);
 	}
 }
 
 function changeManner103(id){
 	if (id == "1") {
 		setReadWrite(breedForm.firmCleanQty);
 	}
 	if (id == "2") {
 		breedForm.firmCleanQty.value = "-1";
 		setReadOnly(breedForm.firmCleanQty);
 	}
 }
 
  function changeManner105(id){
 	if (id == "1") {
 		setReadWrite(breedForm.oneMaxHoldQty);
 	}
 	if (id == "2") {
 		breedForm.oneMaxHoldQty.value = "-1";
 		setReadOnly(breedForm.oneMaxHoldQty);
 	}
 }

 function changeManner104(id){
 	if (id == "1") {
 		setReadWrite(breedForm.firmMaxHoldQty);
 		//控制交易商订货量限制算法隐现
 		document.getElementById("tdFirmMaxHoldQtyAlgr").style.visibility = 'visible';
 		changeFirmMaxHoldQtyAlgr();
 	}
 	if (id == "2") {
 		document.getElementById("tdFirmMaxHoldQtyAlgr").style.visibility = 'hidden';
 		document.getElementById("tdStartPercentQty").style.visibility = 'hidden';
 		document.getElementById("tdMaxPercentLimit").style.visibility = 'hidden';
 		document.getElementById("tdFirmMaxHoldQty").style.visibility = 'visible';
 		breedForm.firmMaxHoldQty.value = "-1";
 		setReadOnly(breedForm.firmMaxHoldQty);
 	}
 }
 
 	function changeFirmMaxHoldQtyAlgr(){
 		if (breedForm.firmMaxHoldQtyAlgr.value == "1") {
 			document.getElementById("tdStartPercentQty").style.visibility = 'visible';
 			document.getElementById("tdMaxPercentLimit").style.visibility = 'visible';
 			document.getElementById("tdFirmMaxHoldQty").style.visibility = 'hidden';
 		}else {
 			document.getElementById("tdStartPercentQty").style.visibility = 'hidden';
 			document.getElementById("tdMaxPercentLimit").style.visibility = 'hidden';
 			document.getElementById("tdFirmMaxHoldQty").style.visibility = 'visible';
 		}
 	}


function change(){
	var settlePriceType = document.forms(0).settlePriceType.value;
	//var ntd = document.getElementById("ntd");
	
	
	if (settlePriceType == "3") {//指定交收价
		document.getElementById("trSettlePriceType").className = "xian";
		document.getElementById("bbb0").className = "yin";
	 	document.getElementById("aaa0").className = "yin";
		document.getElementById("ccc0").className = "xian";
	 	document.getElementById("ddd0").className = "xian";
	}else if (settlePriceType == "1"){//计算交收价的提前日
		document.getElementById("trSettlePriceType").className = "xian";
		document.getElementById("bbb0").className = "xian";
	 	document.getElementById("aaa0").className = "xian";
	 	document.getElementById("ccc0").className = "yin";
	 	document.getElementById("ddd0").className = "yin";
		
	}else {
		document.getElementById("trSettlePriceType").className = "yin";
		document.getElementById("bbb0").className = "yin";
	 	document.getElementById("aaa0").className = "yin";
	 	document.getElementById("ccc0").className = "yin";
	 	document.getElementById("ddd0").className = "yin";
	}
}
//add by yangpei 2011-12-2交收保证金计算方式
function change2(){
	var settleMarginType = document.forms(0).settleMarginType.value;
	if (settleMarginType == "1"){//计算交收价的提前日
		document.getElementById("bbb02").className = "xian";
	 	document.getElementById("aaa02").className = "xian";
		
	}else {
		document.getElementById("bbb02").className = "yin";
	 	document.getElementById("aaa02").className = "yin";
	}
}

function settleFeeAlgr_change(){
	if (breedForm.settleFeeAlgr.value == "1") {
		document.getElementById("settleFeeRate_BPercent").className = "xian";
		document.getElementById("settleFeeRate_SPercent").className = "xian";
	}else {
		document.getElementById("settleFeeRate_BPercent").className = "yin";
		document.getElementById("settleFeeRate_SPercent").className = "yin";
	}
}

function settleMarginAlgr_B_change(){
	if (breedForm.settleMarginAlgr_B.value == "1") {
		document.getElementById("settleMarginRate_BPercent").className = "xian";
	}else {
		document.getElementById("settleMarginRate_BPercent").className = "yin";
	}
}

function settleMarginAlgr_S_change(){
	if (breedForm.settleMarginAlgr_S.value == "1") {
		document.getElementById("settleMarginRate_SPercent").className = "xian";
	}else {
		document.getElementById("settleMarginRate_SPercent").className = "yin";
	}
}

function payoutAlgr_change(){
	if (breedForm.payoutAlgr.value == "1") {
		document.getElementById("payoutRatePercent").className = "xian";
	}else {
		document.getElementById("payoutRatePercent").className = "yin";
	}
}

function marginAlgr_change(){
	if (breedForm.marginAlgr.value == "1") {
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


	function breed(){
		breedForm.breedName.value = document.all.breedID.options[document.all.breedID.selectedIndex].text;
	}

function changeUnits(){ 
    var value=document.getElementById("contractFactorName").value;
    if(value=="")
    alert("请先输入交易单位名称");
    else 
   document.getElementById("span_contractFactor").innerHTML ="("+value+ "/批)";
    }
function changeUnits1(){ 
    var value=document.getElementById("contractFactorName").value;
    if(value=="")
   document.getElementById("span_contractFactor").innerHTML ="如(吨/批)"
   else 
   document.getElementById("span_contractFactor").innerHTML ="("+value+ "/批)";
     }
function changeHoldDays(){
	var holdDaysLimit=document.getElementById("holdDaysLimit").value;
	var maxHoldDay=document.getElementById("holdDays");
	if(holdDaysLimit==1){
		maxHoldDay.style.visibility='visible';
	}else{
		maxHoldDay.style.visibility='hidden';
	}
}
 
//-----------------------------end paraminfo-----------------------------
</script>
</head>
<body leftmargin="14" topmargin="0" onLoad="return window_onload()" onkeypress="keyEnter(event.keyCode);">
	<table border="0"  height="100%" width="100%"  align="center" >
		<tr>
			<td>
				<html:form action="/timebargain/baseinfo/breed.do?funcflg=save" method="POST" styleClass="form">
				<fieldset>
				<legend class="common"><b>设置品种信息</b></legend>
				<span id="baseinfo9">
				<table width="700" border="0" align="center"  class="common" cellpadding="0" cellspacing="2">
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
								<span id="baseinfo">
								<table cellSpacing="0" cellPadding="0" width="700" border="0" align="left" class="common">
        							<tr>
        			 					<td align="right" width="98"><span class="req">品种</span>：</td>
       									<c:choose>
       									<c:when test="${breedForm.crud == 'update'}">
       									<td >
       										<input type="hidden" name="breedID" id="breedID" value="${breedForm.breedID }">
       										<input type="text" name="breedName" id="breedName" style="width:80" value="${breedForm.breedName }" readonly="readonly">
       									</td>
       									</c:when>
       									<c:otherwise>
	      								<td style="white-space:nowrap;">
											<html:select property="breedID"  style="width:80" onchange="breed()">
		                  						<html:options collection="breedSelect" property="value" labelProperty="label"/>
		               						</html:select>
		          							<span class="req">*</span>
		          						</td>
		          						</c:otherwise>
		          						</c:choose>
		            					<td ><input type="hidden" name="breedName" /><span class="req">&nbsp;</span></td>
            							<td>&nbsp;</td>
            							<td align="right" width="90"><span class="req">品种分类</span>：</td>   
            							  
            							<td style="white-space:nowrap;">
											<html:select property="sortID"  style="width:80">
                   								<html:options collection="cmdtySortSelect" property="value" labelProperty="label"/>
                							</html:select>
											<span class="req">*</span>
										</td>
										<%
											String useBreedTradeMode = (String) request.getAttribute("useBreedTradeMode");
											if (useBreedTradeMode.equals("Y")) {
										%>
										<td align="right" width="90"><span class="req">品种交易模式</span>：</td>     
            							<td style="white-space:nowrap;" >  
											<select name="breedTradeMode">
							            		<option value="0" <c:if test="${breed.breedTradeMode==0 }">selected</c:if>>标准模式</option>
							            		<option value="1" <c:if test="${breed.breedTradeMode==1 }">selected</c:if>>买专场</option>
							            		<option value="2" <c:if test="${breed.breedTradeMode==2 }">selected</c:if>>卖专场</option>
							            		<option value="3" <c:if test="${breed.breedTradeMode==3 }">selected</c:if>>买专场不可转让</option>
							            		<option value="4" <c:if test="${breed.breedTradeMode==4 }">selected</c:if>>卖专场不可转让</option>
							            	</select>
											<span class="req">*</span>
										</td>
										<%
											} else if (useBreedTradeMode.equals("N")) {
										%>
											<html:hidden property="breedTradeMode" value="0"/>
										<%
											}
										%>	
							            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>&nbsp;</td>
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
					        	<table cellspacing="0" cellpadding="0" border="0" width="750" class="common">
					           		<col width="65"></col><col></col><col width="6"></col>
					         		<tr>
					              		<td><b>基本参数</b></td>
					                    <td><hr width="765" class="pickList"/></td>
					                    <td ><img id="baseinfo_img2" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo2_onclick()"/></td>
					             	</tr>
					         	</table>
					       		</legend>
								<span id="baseinfo2">
								<table cellSpacing="0" cellPadding="0" width="765" border="0" align="left" class="common">   
									<tr>
        								<html:hidden property="cmdtyPrefix"/>   
        	  							<td align="right">&nbsp;&nbsp;报价单位：</td>
            							<td width="140">元/<html:text property="contractFactorName" maxlength="10" styleClass="text" size="7" onchange="changeUnits1()" onkeypress="chackValue()"/>          
			  								<span class="req">&nbsp;</span>  
            							</td>    
        								<td align="right" >交易单位：</td> 
            							<td width="140"> 
			  								<html:text property="contractFactor" maxlength="10"  style="ime-mode:disabled"   styleClass="text" size="5" onchange="changeUnits()" onkeypress="onlyNumberInput()"/>
			  								<span id="span_contractFactor"  class="req">
			  									<c:if test="${breedForm.contractFactorName!=null}">(${breedForm.contractFactorName}/批)</c:if>
			  									<c:if test="${breedForm.contractFactorName==null}">如(吨/批)</c:if>
			  								</span>          
            							</td>
            							<td align="right">增值税率：</td>
										<td>
											<html:text size="10" property="addedTax" maxlength="10" styleClass="text" style="ime-mode:disabled"  onkeypress="suffixNamePress()" />
											<span >%</span><span class="req">*</span>
										</td>
        							</tr>
        							<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
							        <%
							        	String typeSpreadAlgr = (String)request.getAttribute("typeSpreadAlgr");
							        %>
							        <tr>
							            <td align="right" >&nbsp;涨跌停板算法：</td>
							            <td >
											<html:select property="spreadAlgr" style="width:80" onchange="spreadAlgr_onchange()">
												<html:option value=""></html:option>
											    <html:option value="1">按百分比</html:option>
												<html:option value="2">按绝对值</html:option>
										   </html:select> <span class="req">*</span>            
							            </td>        
							            <td align="right">涨幅上限：</td>
							            <td width="110">
			  								<html:text property="spreadUpLmt" maxlength="10" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>
			  								<span id="spreadUpLmtPercent" class="<%if("1".equals(typeSpreadAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>           
            							</td>
            							<td align="right">跌幅下限：</td>
            							<td>
											<html:text property="spreadDownLmt" maxlength="10" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>
											<span id="spreadDownLmtPercent" class="<%if("1".equals(typeSpreadAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>           
								      	</td>
								      	<td>&nbsp;</td>
        							</tr> 
									<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>			
									<%
										String typeSettlePriceType = (String)request.getAttribute("typeSettlePriceType");
									%>
									<tr>
										<td align="right">&nbsp;&nbsp;最小变动价位(元)：</td>
            							<td width="110">
			  								<html:text property="minPriceMove" maxlength="10" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>          
			  								<span class="req">*</span>  
            							</td>
            							<td align="right">&nbsp;&nbsp;最小变动数量：</td>
            							<td width="110">
			  								<html:text property="minQuantityMove" maxlength="3"   style="ime-mode:disabled"  onkeyup="value=value.replace(/[^\d]/g,'')" styleClass="text" size="10"/>          
			  								<span class="req">*</span>  
            							</td>
            							<td align="right" width="100">交收价计算方式：</td>
										<td width="140">
											<html:select property="settlePriceType" style="width:90" onchange="change()">
												<html:option value=""></html:option>
											    <html:option value="0">闭市结算价</html:option>
												<html:option value="1">最后几日加权平均价</html:option>
												<html:option value="2">订立价</html:option>
												<html:option value="3">指定交收价</html:option>
										    </html:select> <span class="req">*</span>
										</td>
         							</tr>
									<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
	 								<tr id="trSettlePriceType" class="<%if("3".equals(typeSettlePriceType) || "1".equals(typeSettlePriceType)){%>xian<%}else{%>yin<%}%>">
	 									<td align="right" width="110" class="<%if("1".equals(typeSettlePriceType)){%>xian<%}else{%>yin<%}%>" id="aaa0">计算交收价提前日：</td>
										<td width="130" class="<%if("1".equals(typeSettlePriceType)){%>xian<%}else{%>yin<%}%>" id="bbb0">
											<html:text size="10" property="beforeDays" maxlength="10" styleClass="text" onkeypress="return onlyNumberInput()"/>(交易日)
											<span class="req">&nbsp;</span>
										</td>
										<td align="right" class="<%if("3".equals(typeSettlePriceType)){%>xian<%}else{%>yin<%}%>" id="ccc0">指定交收价：</td>
										<td  class="<%if("3".equals(typeSettlePriceType)){%>xian<%}else{%>yin<%}%>" id="ddd0">
											<html:text size="10" property="specSettlePrice" maxlength="10" styleClass="text" onkeypress="return onlyNumberInput()"/>
											<span width="100" class="req">&nbsp;</span>
										</td>
	 								</tr>
	 								<!-- 提前交收交收价计算方式  add by zhangjian 2011年12月15日15:24:02 -->
	 								<tr>
	 	 								<td align="right">提前交收价计算方式：</td>
										<td>
											<html:select property="aheadSettlePriceType" style="width:100" >
												<html:option value=""></html:option>
											    <html:option value="0">按订立价交收</html:option>
												<html:option value="1">按自主价交收</html:option> 
										    </html:select> <span class="req">*</span>
										</td>
										<!-- add by yangpei 2011-12-2交收保证金计算方式   -->
									 	<%
											String typeSettleMarginType = (String)request.getAttribute("typeSettleMarginType");
										%>
	 									<td align="right" style="white-space:nowrap;">交收保证金计算方式：</td>
										<td>
											<html:select property="settleMarginType" style="width:120" onchange="change2()">
												<html:option value=""></html:option>
											    <html:option value="0">闭市结算价</html:option>
												<html:option value="1">最后几日加权平均价</html:option>
												<html:option value="2">订立价</html:option>
										    </html:select> <span class="req">*</span>
										</td>
										<td align="right" class="<%if("1".equals(typeSettleMarginType)){%>xian<%}else{%>yin<%}%>" id="aaa02">计算保证金提前日：</td>
										<td class="<%if("1".equals(typeSettleMarginType)){%>xian<%}else{%>yin<%}%>" id="bbb02">
											<html:text size="10" property="beforeDays_M" maxlength="10" styleClass="text" onkeypress="return onlyNumberInput()"/>(交易日)
											<span class="req">&nbsp;</span>
										</td>
	 								</tr>

									<tr>
										<td align="right" style="white-space:nowrap;">
							          	 是否启用持仓天数限制：
							          	 </td>
							          	 <td>
							          	 	<html:select property="holdDaysLimit" style="width:120" onchange="changeHoldDays()" >
											    <html:option value="0">不启用</html:option>
												<html:option value="1">启用</html:option>
										    </html:select> <span class="req">*</span>
							          	 </td>
										   		<td colspan="3"  align="left"
										   		<c:choose><c:when test="${holdDaysLimit=='1'}">style="visible"</c:when><c:otherwise>style="hidden"</c:otherwise></c:choose>
										   		 id="holdDays" style="white-space:nowrap;">
										   		最长持仓天数:
										   		<html:text property="maxHoldPositionDay" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="11"/><span>交易日</span> 
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
                  					<col width="80"></col><col></col><col width="6"></col>
                  					<tr>
                    					<td><b>订货量限制</b></td>
                    					<td><hr width="618" class="pickList"/></td>
                    					<td ><img id="paraminfo_img6" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:paraminfo6_onclick()"/></td>
                  					</tr>
                				</table>
              					</legend>
								<span id="paraminfo6">
								<table cellSpacing="0" cellPadding="0" width="720" border="0" align="left" class="common">                        
         							<tr>
									<%
										String type101 = (String)request.getAttribute("type101");
										String type102 = (String)request.getAttribute("type102");
										String type103 = (String)request.getAttribute("type103");
										String type104 = (String)request.getAttribute("type104");
										String type105 = (String)request.getAttribute("type105");
										String firmMaxHoldQtyAlgr = (String)request.getAttribute("firmMaxHoldQtyAlgr");
									%>
										<td align="right">
											<span class="req">品种单边最大订货量(批)</span>：
										</td>
										<td>
											<input type="radio" name="type101" value="2" onclick="changeManner101(2);" <%if("2".equals(type101)){out.println("checked");} %> style="border:0px;"><span class="req">不限制</span>
											<input type="radio" name="type101" value="1" onclick="changeManner101(1);" <%if("1".equals(type101)){out.println("checked");} %> style="border:0px;"><span class="req">限制</span>
											<html:text property="limitBreedQty" style="ime-mode:disabled"  styleClass="text" size="10" onkeypress="return onlyNumberInput()"/>
            								<span class="req">*</span>
										</td>
										<td align="right" >商品单边最大订货量(批)：</td>
            							<td >
											<input type="radio" name="type102" value="2" onclick="changeManner102(2);" <%if("2".equals(type102)){out.println("checked");} %> style="border:0px;">不限制
            								<input type="radio" name="type102" value="1" onclick="changeManner102(1);" <%if("1".equals(type102)){out.println("checked");} %> style="border:0px;">限制
            								<html:text property="limitCmdtyQty" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>
            								<span class="req">*</span>
            							</td>
									</tr>
									<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
									<tr>			
										<td align="right" >交易商最大净订货量：</td>
           								<td>
											<input type="radio" name="type103" value="2" onclick="changeManner103(2);" <%if("2".equals(type103)){out.println("checked");} %> style="border:0px;">不限制
           									<input type="radio" name="type103" value="1" onclick="changeManner103(1);" <%if("1".equals(type103)){out.println("checked");} %> style="border:0px;">限制
           									<html:text property="firmCleanQty" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>
            								<span class="req">*</span>
           								</td>
           								<td align="right">单笔最大委托量：</td>
										<td>
											<input type="radio" name="type105" value="2" onclick="changeManner105(2);" <%if("2".equals(type105)){out.println("checked");} %> style="border:0px;">不限制
											<input type="radio" name="type105" value="1" onclick="changeManner105(1);" <%if("1".equals(type105)){out.println("checked");} %> style="border:0px;">限制
											<html:text property="oneMaxHoldQty"  style="ime-mode:disabled"  styleClass="text" size="10" onkeypress="return onlyNumberInput()"/>
            								<span class="req">*</span>
										</td>
									</tr>
             						<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
									<tr>
										<td align="right" >交易商最大双边订货量：</td>
           								<td>
											<input type="radio" name="type104" value="2" onclick="changeManner104(2);" <%if("2".equals(type104)){out.println("checked");} %> style="border:0px;">不限制
							           		<input type="radio" name="type104" value="1" onclick="changeManner104(1);" <%if("1".equals(type104)){out.println("checked");} %> style="border:0px;">限制
							           		<span id="tdFirmMaxHoldQty" style="visibility:<%if("1".equals(firmMaxHoldQtyAlgr)){%>visible<%}else{%>hidden<%}%>;">
							           			<html:text property="firmMaxHoldQty"  onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="text" size="10"/>
							           			<span class="req">*</span>
							           		</span>
           								</td>
									</tr>
									<tr >	
           								<td id="tdFirmMaxHoldQtyAlgr"  colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;交易商订货量限制算法：
							           		<html:select property="firmMaxHoldQtyAlgr" style="width:80" onchange="changeFirmMaxHoldQtyAlgr()">
												<html:option value=""></html:option>
											    <html:option value="1">按百分比</html:option>
												<html:option value="2">按绝对值</html:option>
										   	</html:select> 
							               	<span class="req">*</span>&nbsp;&nbsp;&nbsp;&nbsp;
           									<span id="tdStartPercentQty" style="visibility:<%if("1".equals(firmMaxHoldQtyAlgr)){%>visible<%}else{%>hidden<%}%>;" >商品订货量阀值：<html:text property="startPercentQty" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="text" size="10"/>
        										<span class="req">*</span>
           									</span>&nbsp;&nbsp;&nbsp;&nbsp;
           									<span id="tdMaxPercentLimit" style="visibility:<%if("1".equals(firmMaxHoldQtyAlgr)){%>visible<%}else{%>hidden<%}%>;">订货最大比例：<html:text property="maxPercentLimit" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="text" size="10"/>
        										<span class="req">%*</span>
           									</span>
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
                  					<col width="60"></col><col></col><col width="6"></col>
                  					<tr>
                    					<td><b>手续费</b></td>
                    					<td><hr width="638" class="pickList"/></td>
                    					<td ><img id="paraminfo_img" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:paraminfo_onclick()"/></td>
                  					</tr>
                				</table>
              					</legend>
								<span id="paraminfo">
								<table cellSpacing="0" cellPadding="0" width="720" border="0" align="left" class="common">
					         	<%
					         		String typeFeeAlgr = (String)request.getAttribute("typeFeeAlgr");
					         	%>
         							<tr > 		            
		            					<td   rowspan="4" width="220" height="10" valign="top">
		            						<div align="right">
		            							<span >交易手续费算法：
		            								<html:select property="feeAlgr" style="width:80" onchange="on_change()">
														<html:option value=""></html:option>
					    								<html:option value="1">按百分比</html:option>
														<html:option value="2">按绝对值</html:option>
			   										</html:select> 
			   										<span class="req">*</span>  
												</span>
											</div>
										</td>	            		            
		            					<td align="right" rowspan="1" width="225" height="5">买订立：<html:text property="feeRate_B" maxlength="6"  style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changeb()" size="10"/>
			  								<span id="feeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
			  							</td>
			  							<td align="right" >卖订立：<html:text property="feeRate_S"  maxlength="6" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changes()" size="10"/>
			  								<span id="feeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>  </td>
			  							<td width="25"></td>
		    						</tr>
									<tr> 		            
		            					<td align="right" width="225" height="5">买转让历史订货：<html:text property="historyCloseFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>
											<span id="historyCloseFeeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
										</td>
			  							<td align="right">卖转让历史订货：<html:text property="historyCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>
											<span id="historyCloseFeeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
										</td>
		           					</tr>
									<tr> 		            
		            					<td align="right" width="225"  height="5">买转让今订货：<html:text property="todayCloseFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>
		            						<span id="todayCloseFeeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
		            					</td>
								  		<td align="right">卖转让今订货：<html:text property="todayCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>
							            	<span id="todayCloseFeeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
							          	</td>
		        					</tr>
		            				<tr>
		              					<td align="right" width="225">买强制转让：<html:text property="forceCloseFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changeb()" size="10"/>
			  								<span id="forceCloseFeeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
			  	 						</td>
							  			<td align="right">卖强制转让：<html:text property="forceCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changes()" size="10"/>
							  				<span id="forceCloseFeeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>         
				            			</td>
						           	</tr>
    								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>     
							        <%
							        	String typeSettleFeeAlgr = (String)request.getAttribute("typeSettleFeeAlgr");
							        %>
        							<tr>
							        	<td align="right" >交收手续费算法：<html:select property="settleFeeAlgr" style="width:80" onchange="settleFeeAlgr_change()">
												<html:option value=""></html:option>
											    <html:option value="1">按百分比</html:option>
												<html:option value="2">按绝对值</html:option>
										   </html:select> 
										   <span class="req">*</span>&nbsp;           
							            </td>
            							<td align="right" >交收买：<html:text property="settleFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changeb()" size="10"/>
			  								<span id="settleFeeRate_BPercent" class="<%if("1".equals(typeSettleFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
			 							</td>
			 							<td align="right">交收卖：<html:text property="settleFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" onchange="changes()" size="10"/>
			  								<span id="settleFeeRate_SPercent" class="<%if("1".equals(typeSettleFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
            							</td>
        							</tr>
        							<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
       								<tr>
            							<td align="right">最低交收手续费金额：<html:text property="lowestSettleFee" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>
			   								<span class="req">*</span>&nbsp;          
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
                    					<td><b>保证金</b></td>
                    					<td><hr width="639" class="pickList"/></td>
                    					<td ><img id="baseinfo_img4" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo4_onclick()"/></td>
                  					</tr>
               	 				</table>
              					</legend>
								<span id="baseinfo4">
								<table cellSpacing="0" cellPadding="0" width="720" border="0" align="center" class="common"> 
           							<tr>
            							<td align="right" width="130">&nbsp;&nbsp;&nbsp;&nbsp;保证金算法：</td>
            							<td width="100">
            								<html:select property="marginAlgr" style="width:80" onchange="marginPriceType_change();marginAlgr_change()">
												<html:option value=""></html:option>
				    							<html:option value="1">按百分比</html:option>
												<html:option value="2">按绝对值</html:option>
			   								</html:select> 
			   								<span class="req">*</span>            
            							</td>
            			<%
            				String typeBZJ = (String)request.getAttribute("typeBZJ");
            			%>
            			<td align="right" width="110" id="td1s" class="<%if("1".equals(typeBZJ)){%>xian<%}else{%>yin<%}%>">
											保证金计算依据：
									</td>
									<td width="368" id="td2s" colspan="3" class="<%if("1".equals(typeBZJ)){%>xian<%}else{%>yin<%}%>">
										<html:select property="marginPriceType" style="width:180" >
											<html:option value=""></html:option>
											<c:if test="${floatingType<3}">
				                           <html:option value="0">订立价</html:option>
				                           </c:if>
				                           <c:if test="${floatingType>=3}">
					                       <html:option value="1">昨结算价</html:option> 
					                       <html:option value="2">盘中按订立价盘后按结算价</html:option>
					                       </c:if>
			                  	 </html:select> <span class="req">*</span>
						</td>
            
            
              
              
            </tr>
            
           <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
           
            <%
             String type1=(String)request.getAttribute("type1");
             %>            
        <tr>
            
			
			<td align="right" >
           			 第一阶段买卖保证金：
            </td>
            <td colspan="3">
            
             <input type="radio" name="type" value="1" onclick="changeManner(1);" <%if("1".equals(type)){out.println("checked");} %> style="border:0px;">相同
             
             <input type="radio" name="type" value="2" onclick="changeManner(2);" <%if("2".equals(type)){out.println("checked");} %> style="border:0px;">不同
            </td >
			
			
        </tr>
        <tr>
        	
            
             <td align="right" id="td1"><%if("1".equals(type)){%>保证金1<%}else{%>买保证金1<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItem1" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />
			  <span id="marginItem1Percent"></span><span class="req">*</span>           
            </td>
            <td align="right" width="112" id="td2"><%if("1".equals(type)){%>担保金1<%}else{%>买担保金1<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItemAssure1" maxlength="11" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />
			  <span id="marginItemAssure1Percent"></span><span class="req">*</span>           
            </td>     
            </tr>
           <tr class="<%if("1".equals(type)){%>yin<%}else{%>xian<%}%>" id="aaa">
             
            
            <td align="right"  id="abc2">卖保证金1：</td>
            <td  id="abc1" style="visibility:<%if("1".equals(type)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItem1_S" maxlength="11"  onkeypress="return onlyNumberInput()" styleClass="text" />
			  <span id="marginItem1_SPercent"></span><span class="req">*</span>           
            </td >
            <td align="right"  id="abc3">
            卖担保金1：</td>
            <td  id="abc4" style="visibility:<%if("1".equals(type)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItemAssure1_S" maxlength="11"  onkeypress="return onlyNumberInput()" styleClass="text" />
			  <span id="marginItemAssure1_SPercent"></span><span class="req">*</span>           
            </td>
        </tr>
        
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        
          <%
             String type2=(String)request.getAttribute("type2");
             %>
        <tr>
            
			
			<td align="right">
            	第二阶段买卖保证金：
            </td>
			<td colspan="3">
            
             <input type="radio" name="type1" value="1" onclick="changeManner1(1);" <%if("1".equals(type1)){out.println("checked");} %> style="border:0px;">相同
             
             <input type="radio" name="type1" value="2" onclick="changeManner1(2);" <%if("2".equals(type1)){out.println("checked");} %> style="border:0px;">不同
            </td >
			
			
            </tr>
            <tr>
        
        
        <td align="right" id="td3">&nbsp;&nbsp;<%if("1".equals(type1)){%>保证金2<%}else{%>买保证金2<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItem2" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            <span id="marginItem2Percent"></span>
            </td> 
            <td align="right" id="td4">&nbsp;&nbsp;<%if("1".equals(type1)){%>担保金2<%}else{%>买担保金2<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItemAssure2" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            <span id="marginItemAssure2Percent"></span>
            </td>  
        </tr>
        <tr class="<%if("1".equals(type1)){%>yin<%}else{%>xian<%}%>" id="aaa1">
            
			<td align="right"  id="abc21">&nbsp;&nbsp;卖保证金2：</td>
            <td style="visibility:<%if("1".equals(type1)){%>hidden<%}else{%>visible<%}%>;" id="abc11">
			  <html:text size="10" property="marginItem2_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            <span id="marginItem2_SPercent"></span>
            </td> 
            <td align="right" id="abc31">&nbsp;&nbsp;卖担保金2：</td>
            <td style="visibility:<%if("1".equals(type1)){%>hidden<%}else{%>visible<%}%>;" id="abc41">
			  <html:text size="10" property="marginItemAssure2_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            <span id="marginItemAssure2_SPercent"></span>
            </td>           
        </tr>
            
      <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr> 
               
         <%
             String type3=(String)request.getAttribute("type3");
             %>
        <tr>
          
			
			<td align="right">
           		 第三阶段买卖保证金：
            </td>
			<td colspan="3">
          
             <input type="radio" name="type2" value="1" onclick="changeManner2(1);" <%if("1".equals(type2)){out.println("checked");} %> style="border:0px;">相同
             <input type="radio" name="type2" value="2" onclick="changeManner2(2);" <%if("2".equals(type2)){out.println("checked");} %> style="border:0px;">不同
            </td >
			
			
			</tr>
			<tr >
            
			<td align="right" id="td5"><%if("1".equals(type2)){%>保证金3<%}else{%>买保证金3<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItem3" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />        
            <span id="marginItem3Percent"></span>
            </td>
            <td align="right" id="td6"><%if("1".equals(type2)){%>担保金3<%}else{%>买担保金3<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItemAssure3" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />        
            <span id="marginItemAssure3Percent"></span>
            </td>
            </tr>   
             <tr class="<%if("1".equals(type2)){%>yin<%}else{%>xian<%}%>" id="aaa2">
            
			<td align="right" id="abc22">卖保证金3：</td>
            <td style="visibility:<%if("1".equals(type2)){%>hidden<%}else{%>visible<%}%>;" id="abc12">
			  <html:text size="10" property="marginItem3_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />        
            <span id="marginItem3_SPercent"></span>
            </td>
            <td align="right"  id="abc32">卖担保金3：</td>
            <td style="visibility:<%if("1".equals(type2)){%>hidden<%}else{%>visible<%}%>;" id="abc42">
			  <html:text size="10" property="marginItemAssure3_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />        
            <span id="marginItemAssure3_SPercent"></span>
            </td>
            </tr>
			
			<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
			
          <%
             String type4=(String)request.getAttribute("type4");
             %>
        <tr>
            
			
			<td align="right">
             	 第四阶段买卖保证金：
            </td>
			<td colspan="3">
            
             <input type="radio" name="type3" value="1" onclick="changeManner3(1);" <%if("1".equals(type3)){out.println("checked");} %> style="border:0px;">相同
             <input type="radio" name="type3" value="2" onclick="changeManner3(2);" <%if("2".equals(type3)){out.println("checked");} %> style="border:0px;">不同
            </td >
			
			
            </tr>
            <tr>
			
            <td align="right" id="td7"><%if("1".equals(type3)){%>保证金4<%}else{%>买保证金4<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItem4" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            <span id="marginItem4Percent"></span>
            </td>
            <td align="right" id="td8"><%if("1".equals(type3)){%>担保金4<%}else{%>买担保金4<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItemAssure4" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            <span id="marginItemAssure4Percent"></span>
            </td>
        </tr>
         <tr class="<%if("1".equals(type3)){%>yin<%}else{%>xian<%}%>" id="aaa3">
             
            <td align="right" id="abc13">卖保证金4：</td>
            <td  id="abc23" style="visibility:<%if("1".equals(type3)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItem4_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            <span id="marginItem4_SPercent"></span>
            </td>
            <td align="right" id="abc33">卖担保金4：</td>
            <td  id="abc43" style="visibility:<%if("1".equals(type3)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItemAssure4_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            <span id="marginItemAssure4_SPercent"></span>
            </td>
        </tr>
            
       <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
           
        
        
        <tr>
            
			
			<td align="right">
             	 第五阶段买卖保证金：
            </td>
			<td colspan="3">
            
             <input type="radio" name="type4" value="1" onclick="changeManner4(1);" <%if("1".equals(type4)){out.println("checked");} %> style="border:0px;">相同
             <input type="radio" name="type4" value="2" onclick="changeManner4(2);" <%if("2".equals(type4)){out.println("checked");} %> style="border:0px;">不同
            </td >
			
			
            </tr>
            <tr>
			
            <td align="right" id="td50"><%if("1".equals(type4)){%>保证金5<%}else{%>买保证金5<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItem5" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            <span id="marginItem5Percent"></span>
            </td>
            <td align="right" id="td60"><%if("1".equals(type4)){%>担保金5<%}else{%>买担保金5<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItemAssure5" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            <span id="marginItemAssure5Percent"></span>
            </td>
        </tr>
         <tr class="<%if("1".equals(type4)){%>yin<%}else{%>xian<%}%>" id="aaa4">
             
            <td align="right" id="abc13">卖保证金5：</td>
            <td  id="abc24" style="visibility:<%if("1".equals(type4)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItem5_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            <span id="marginItem5_SPercent"></span>
            </td>
            <td align="right" id="abc33">卖担保金5：</td>
            <td  id="abc44" style="visibility:<%if("1".equals(type4)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItemAssure5_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />           
            <span id="marginItemAssure5_SPercent"></span>
            </td>
        </tr>
            
            
            
       <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        <tr>
        	<td align="left" >
        		&nbsp;&nbsp;交收保证金：
        	</td>
        	
        </tr>
        
        <%
        	String typeSettleMarginAlgr_B = (String)request.getAttribute("typeSettleMarginAlgr_B");
        %>
        <tr>
            <td align="right" id="td9">买方方式：</td>
            <td>
            <html:select property="settleMarginAlgr_B" style="width:80" onchange="settleMarginAlgr_B_change()">
					<html:option value=""></html:option>
				    <html:option value="1">按百分比</html:option>
					<html:option value="2">按绝对值</html:option>
			   </html:select> 
            <span class="req">*</span>
            </td>
            <td align="right" id="td0">买方标准：</td>
            <td>
			  <html:text size="10" property="settleMarginRate_B" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            <span id="settleMarginRate_BPercent" class="<%if("1".equals(typeSettleMarginAlgr_B)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
            </td>
        </tr>
             
        <%
        	String typeSettleMarginAlgr_S = (String)request.getAttribute("typeSettleMarginAlgr_S");
        %>
        <tr id="aaa4">
            <td align="right"  id="abc14">卖方方式：</td>
            <td  >
            <html:select property="settleMarginAlgr_S" style="width:80" onchange="settleMarginAlgr_S_change()">
					<html:option value=""></html:option>
				    <html:option value="1">按百分比</html:option>
					<html:option value="2">按绝对值</html:option>
			   </html:select>
            <span class="req">*</span>
            </td>
            <td align="right"  id="abc34">卖方标准：</td>
            <td>
			  <html:text size="10" property="settleMarginRate_S" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            <span id="settleMarginRate_SPercent" class="<%if("1".equals(typeSettleMarginAlgr_S)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
            </td>
        </tr>
        
        <%
        	String typePayoutAlgr = (String)request.getAttribute("typePayoutAlgr");
        %>
        <tr>
        	<td align="right"  id="abc14">交收货款算法：</td>
            <td  >
            <html:select property="payoutAlgr" style="width:80" onchange="payoutAlgr_change()">
					<html:option value=""></html:option>
				    <html:option value="1">按百分比</html:option>
					<html:option value="2">按绝对值</html:option>
			   </html:select>
            <span class="req">*</span>
            </td>
            <td align="right"  id="abc34">交收货款标准：</td>
            <td>
			  <html:text size="10" property="payoutRate" maxlength="16" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" />         
            <span id="payoutRatePercent" class="<%if("1".equals(typePayoutAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
            </td>
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
					                    <td ><img id="baseinfo_imgy" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfoy_onclick()"/></td>
					             	</tr>
					         	</table>
					           	</legend>
								<span id="baseinfoy">
								<table cellSpacing="0" cellPadding="0" width="700" border="0" align="left" class="common">
        							<tr>
            							<td align="right" width="120">买方延期补偿比率：</td>
            							<td >
            								<html:text property="delayRecoupRate" maxlength="13" styleClass="text" size="10" onkeypress="suffixNamePress()"/>
            								<span >%</span><span class="req">*</span>
            							</td>
            							<td align="right" >仓储补偿费：</td>
            							<td >
            								<html:text property="storeRecoupRate" maxlength="13" styleClass="text" size="10" onkeypress="suffixNamePress()"/>
            								<span class="req">*</span>
            							</td>
        							</tr>
        							<tr>  
         								<td align="right" width="120">卖方延期补偿比率：</td>
            							<td >
            								<html:text property="delayRecoupRate_S" maxlength="13" styleClass="text" size="10" onkeypress="suffixNamePress()"/>
            								<span >%</span><span class="req">*</span>
            							</td>
            							<td align="right" >交收方式：</td>     
            							<td>  
											<html:select property="settleWay"  >
												<html:option value=""></html:option>
							                   	<html:option value="0">按期交收</html:option>
							                   	<html:option value="1">延期交收</html:option>
                							</html:select>
											<span class="req">*</span>
										</td>
        							</tr>
        							<tr>
            							<td align="right">最小交割单位：</td>
            							<td>
			  								<html:text property="minSettleMoveQty" maxlength="10"   style="ime-mode:disabled"  onkeyup="value=value.replace(/[^\d]/g,'')" styleClass="text" size="10"/>          
			  								<span class="req">*</span>  
            							</td>
            							<td align="right">最小交割数量：</td>
            							<td>
			  								<html:text property="minSettleQty" maxlength="10"   style="ime-mode:disabled"  onkeyup="value=value.replace(/[^\d]/g,'')" styleClass="text" size="10"/>          
			  								<span class="req">*</span>  
            							</td>
        							</tr> 
        							<tr>
        								<td align="right">延期费收取方式：</td>     
            							<td>  
											<html:select property="delayFeeWay"  >
												<html:option value=""></html:option>
                   								<html:option value="1">按自然日逐日收取</html:option>
                							</html:select>
											<span class="req">*</span>
										</td>
										<td align="right">递延申报交收价类型：</td>    
										<td>  
											<html:select property="delaySettlePriceType"  >
												<html:option value=""></html:option>
                   								<html:option value="0">按结算价交收</html:option>
                   								<html:option value="1">按订立价交收</html:option>
                							</html:select>
											<span class="req">*</span>
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
			<html:hidden property="delaySettlePriceType" value="0"/>
			<html:hidden property="delayRecoupRate" value="0"/>
			<html:hidden property="delayRecoupRate_S" value="0"/>
			<html:hidden property="storeRecoupRate" value="0"/>
			<html:hidden property="settleWay" value="0"/>
			<html:hidden property="delayFeeWay" value="1"/>
			<html:hidden property="minSettleMoveQty" value="1"/>
			<html:hidden property="minSettleQty" value="1"/>
			<html:hidden property="delaySettlePriceType" value="0"/>
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
					                    <td ><img id="baseinfo_img3" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo3_onclick()"/></td>
					                  </tr>
					                </table>
					              </legend>
					<span id="baseinfo3">
					<table cellSpacing="0" cellPadding="0" width="700" border="0" align="left" class="common">
         		
         		<tr>
				<td>&nbsp;&nbsp;</td>
                  <td align="left">
                    <c:forEach var="tradeTime" items="${listSection}">
                      <html-el:multibox property="tradeTimes" styleId="${tradeTime.label}" styleClass="NormalInput">
                        <c:out value="${tradeTime.value}"/>
                      </html-el:multibox>
                      <label for="<c:out value="${tradeTime.label}"/>" class="hand">
                      <c:out value="${tradeTime.label}"/>
                      </label>
                    </c:forEach>
                  </td>
                </tr>	
         
								</table >
						</span>
						            </fieldset>
						          </td>
						        </tr>	
        
	
	
  
            							
								<tr>
									<td colspan="4" height="3">	
								</td>
								</tr>
								                   
								<tr>
									<td colspan="4" align="center">
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
						<html:hidden property="crud" />
						<html:hidden property="isSettle" />
						<input type="hidden" name="commoditySetType" />
						<html:hidden property="forceCloseFeeAlgr"/>
						<html:hidden property="addedTaxFactor"/>
						<html:hidden property="orderPrivilege" value="101"/>  
					</html:form>
				</td>
			</tr>
		</table>
		     <table cellSpacing="0" cellPadding="0" width="700" border="0" align="center" class="common">
		    
			<tr>
				<td colspan="4" align="left">
			 	<span class="req">提示：红色字体表示不与商品关联</span>
			 </td>
			</tr>
			</table>

			
		<html:javascript formName="breedForm" cdata="false"
			dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
