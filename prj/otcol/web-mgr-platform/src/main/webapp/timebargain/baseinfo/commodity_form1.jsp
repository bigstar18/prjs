<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page import="gnnt.MEBS.timebargain.manage.model.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.webapp.form.*"%>
<%@ page pageEncoding="GBK"%>

	<%
     	String type=(String)request.getAttribute("type");
     	
     	CommodityForm commodityForm = new CommodityForm();
		try {
			commodityForm = (CommodityForm)request.getAttribute("commodityForm");
		} catch (Exception e) {
			e.printStackTrace();
		}
	%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script type="text/javascript" src="../public/calendar/WdatePicker.js"></script>
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
    //highlightFormElements();
    change_date_settle();
    var tradeTimeCs = new Array();
    <c:forEach var="tradeTime" items="${commodityForm.tradeTimes}" varStatus="status">
      tradeTimeCs[<c:out value="${status.index}"/>] = "<c:out value="${tradeTime}"/>";
    </c:forEach>
    try{
    for(var k=0; k < commodityForm.tradeTimes.length; k++)
    {
      //此处是判断回显时的box情况
      if (contains(tradeTimeCs, commodityForm.tradeTimes[k].value))
      {
        commodityForm.tradeTimes[k].checked = true;
      }
      //此处是需求要求添加默认全选
      //if (commodityForm.crud.value == "create") {
      //	commodityForm.tradeTimes[i].checked = true;
     // }
    }
    }catch(e){
    	alert("无交易节！");
    }
    
    
    
    if(commodityForm.crud.value == "create")
    {
    
      commodityForm.commodityID.focus();
     
    }
    else if(commodityForm.crud.value == "update")
    {
      
      setReadOnly(commodityForm.commodityID);
      commodityForm.name.focus();
      <c:if test="${market.commoditySetType eq 0}">
      		setReadOnly(commodityForm.name);
      		//setReadOnly(commodityForm.settleDate);
      		setDisabled(commodityForm.settleDateBtn);
      		setReadOnly(commodityForm.contractFactor); 
      		setReadOnly(commodityForm.lastPrice); 
      		setReadOnly(commodityForm.marketDate); 
      		commodityForm.marketDate.focus();     		
      </c:if>
      //setReadOnly(commodityForm.marketDate2);
      
    }  
    if (commodityForm.limitCmdtyQty.value == "-1") {
      	changeManner101(2);
      }else {
      	changeManner101(1);
      }
      if (commodityForm.firmCleanQty.value == "-1") {
      	changeManner102(2);
      }else {
      	changeManner102(1);
      }
      if (commodityForm.firmMaxHoldQty.value == "-1") {
      	changeManner103(2);
      }else {
      	changeManner103(1);
      }
      if (commodityForm.oneMaxHoldQty.value == "-1") {
      	changeManner104(2);
      }else {
      	changeManner104(1);
      }
      
      setReadOnly(commodityForm.settleDate7);
      setReadOnly(commodityForm.settleDate1);
      
      marginAlgr_change();
      setReadOnly(commodityForm.marginRate_B);
      setReadOnly(commodityForm.marginRate_S);
      //延期交收设置日期
      settleWay_settleDate();
      //默认延期费收取方式
      commodityForm.delayFeeWay.value = "1";
      commodityForm.aheadSettlePriceType.value=${aheadSettlePriceType}

      var maxHoldDay=document.getElementById("holdDays");
		<c:if test="${holdDaysLimit == '1'}">
			maxHoldDay.style.visibility='visible';
	    </c:if>
	    <c:if test="${holdDaysLimit == '0'}">
			maxHoldDay.style.visibility='hidden';
	    </c:if>
      
}
function on_change(){
	commodityForm.forceCloseFeeAlgr.value =  commodityForm.feeAlgr.value;
	
	if (commodityForm.feeAlgr.value == "1") {
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

	for(var i =2;i<6;i++){
	var typeid = 'type'+(i-1);	
		var a = document.getElementsByName(typeid); 
		var marginItem = document.getElementById('marginItem'+i).value;
		var marginItem_S = document.getElementById('marginItem'+i+'_S').value;
		var marginItemAssure = document.getElementById('marginItemAssure'+i).value;
		var marginItemAssure_S  = document.getElementById('marginItemAssure'+i+'_S').value;
		if( a[0].checked==true){
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
	if (confirm("您确定要提交吗？")) {
	
		
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
    
    if(commodityForm.type[1].checked ==true)
      {
	    if(trim(commodityForm.marginItem1_S.value) == "")
      {
	      alert("卖保证金1不能为空！");
	      commodityForm.marginItem1_S.focus();
	      return false;
      }  
       if(trim(commodityForm.marginItemAssure1_S.value) == "")
      {
	      alert("卖担保金1不能为空！");
	      commodityForm.marginItemAssure1_S.focus();
	      return false;
      }  
      }
      
   
    if (commodityForm.marketDate.value == "") {
    	alert("上市日期不能为空！");
      	commodityForm.marketDate.focus();
      	return false;
    }

    if (commodityForm.settleDate.value <= commodityForm.marketDate.value) {
    	alert("最后交易日不得早于上市日期！");
    	commodityForm.settleDate.focus();
    	return false;
    }
    
    if(parseFloat(trim(commodityForm.contractFactor.value),10) <= 0)
    {
	      alert("单位交易数量应大于0！");
	      commodityForm.contractFactor.focus();
	      return false;
    }
   
    if(commodityForm.todayCloseFeeRate_B.value==""){
    	alert("买转让今订货不能为空！");
      commodityForm.todayCloseFeeRate_B.focus();
      return false;
    }  
    if(commodityForm.todayCloseFeeRate_S.value==""){
    	alert("卖转让今订货不能为空！");
      commodityForm.todayCloseFeeRate_S.focus();
      return false;
    } 
    if(commodityForm.historyCloseFeeRate_B.value==""){
    	alert("买转让历史订货不能为空！");
      commodityForm.historyCloseFeeRate_B.focus();
      return false;
    } 
    if(commodityForm.historyCloseFeeRate_S.value==""){
    	alert("卖转让历史订货不能为空！");
      commodityForm.historyCloseFeeRate_S.focus();
      return false;
    }   
    
    if (commodityForm.sortID.value == "") {
    	alert("商品分类不能为空！");
    	commodityForm.sortID.focus();
    	return false;
    }
    
    if (commodityForm.feeAlgr.value == "") {
    	alert("手续费算法不能为空！");
    	commodityForm.feeAlgr.focus();
    	return false;
    }
    if (commodityForm.feeRate_B.value == "") {
    	alert("买订立不能为空！");
    	commodityForm.feeRate_B.focus();
    	return false;
    }
    if (commodityForm.feeRate_S.value == "") {
    	alert("卖订立不能为空！");
    	commodityForm.feeRate_S.focus();
    	return false;
    }
    if (commodityForm.settleFeeAlgr.value == "") {
    	alert("交收手续费不能为空！");
    	commodityForm.settleFeeAlgr.focus();
    	return false;
    }
    if (commodityForm.settleFeeRate_B.value == "") {
    	alert("交收买不能为空！");
    	commodityForm.settleFeeRate_B.focus();
    	return false;
    }
    if (commodityForm.settleFeeRate_S.value == "") {
    	alert("交收卖不能为空！");
    	commodityForm.settleFeeRate_S.focus();
    	return false;
    }
    
    //if (commodityForm.maxFeeRate.value == "") {
    //	alert("最大交易手续费系数不能为空！");
    //	commodityForm.maxFeeRate.focus();
    //	return false;
    //}
    if (commodityForm.forceCloseFeeRate_B.value == "") {
    	alert("买强制转让不能为空！");
    	commodityForm.forceCloseFeeRate_B.focus();
    	return false;
    }
    if (commodityForm.forceCloseFeeRate_S.value == "") {
    	alert("卖强制转让不能为空！");
    	commodityForm.forceCloseFeeRate_S.focus();
    	return false;
    }
    if (commodityForm.spreadAlgr.value == "") {
    	alert("停板涨跌幅算法不能为空！");
    	commodityForm.spreadAlgr.focus();
    	return false;
    }
    if (commodityForm.spreadUpLmt.value == "") {
    	alert("涨幅上限不能为空！");
    	commodityForm.spreadUpLmt.focus();
    	return false;
    }
    if (commodityForm.spreadDownLmt.value == "") {
    	alert("涨幅下限不能为空！");
    	commodityForm.spreadDownLmt.focus();
    	return false;
    }  
    if (commodityForm.marginAlgr.value == "") {
    	alert("保证金算法不能为空！");
    	commodityForm.marginAlgr.focus();
    	return false;
    }
    if (commodityForm.marginItem1.value == "") {
    	alert("买保证金不能为空！");
    	commodityForm.marginItem1.focus();
    	return false;
    }
    if (commodityForm.marginItemAssure1.value == "") {
    	alert("买担保金不能为空！");
    	commodityForm.marginItemAssure1.focus();
    	return false;
    }
    if (commodityForm.minPriceMove.value <= 0 ) {
    	alert("最小价位应大于0！");
    	commodityForm.minPriceMove.focus();
    	return false;
    }
    if (commodityForm.minPriceMove.value == "") {
    	alert("最小价位不能为空！");
    	commodityForm.minPriceMove.focus();
    	return false;
    }
      if (commodityForm.minQuantityMove.value <= 0) {
    	alert("最小变动数量应大于0！");
    	commodityForm.minQuantityMove.focus();
    	return false;
    }
    if (commodityForm.minQuantityMove.value == "") {
    	alert("最小变动数量不能为空！");
    	commodityForm.minQuantityMove.focus();
    	return false;
    }
    if (commodityForm.minSettleMoveQty.value <= 0) {
    	alert("最小交割单位应大于0！");
    	commodityForm.minSettleMoveQty.focus();
    	return false;
    }
    if (commodityForm.minSettleMoveQty.value == "") {
    	alert("最小交割单位不能为空！");
    	commodityForm.minSettleMoveQty.focus();
    	return false;
    }
    if (commodityForm.minSettleQty.value <= 0) {
    	alert("最小交割数量应大于0！");
    	commodityForm.minSettleQty.focus();
    	return false;
    }
    if (commodityForm.minSettleQty.value == "") {
    	alert("最小交割数量不能为空！");
    	commodityForm.minSettleQty.focus();
    	return false;
    }
    if (commodityForm.forceCloseFeeAlgr.value == "") {
    	alert("强制转让算法不能为空！");
    	return false;
    }
    
    if (commodityForm.name.value == "") {
    	alert("商品名称不能为空！");
    	commodityForm.name.focus();
    	return false;
    }
    if(commodityForm.commodityID.value == ""){
        alert("商品代码不能为空！");
     	commodityForm.commodityID.focus();
    	return false;
    }
    if (commodityForm.status.value == "") {
    	alert("状态不能为空！");
    	commodityForm.status.focus();
    	return false;
    }
	if(((commodityForm.lastPrice.value)*10000000)%((commodityForm.minPriceMove.value)*10000000)!=0){
		alert("开市指导价必须为最小变动价位的整数倍！");
    	commodityForm.lastPrice.focus();
    	return false;
	}
    if (commodityForm.lastPrice.value == "") {
    	alert("开盘指导价不能为空！");
    	commodityForm.lastPrice.focus();
    	return false;
    }else {
    	var lastPrice = parseFloat(commodityForm.lastPrice.value);
    	if (lastPrice <= 0) {
    		alert("开盘指导价应大于零！");
	    	commodityForm.lastPrice.focus();
	    	return false;
    	}
    }
    if (commodityForm.lastPrice.value < 0) {
    	alert("开盘指导价应大于0！");
    	commodityForm.lastPrice.focus();
    	return false;
    }
    
    if (commodityForm.settleDate.value == "") {
    	alert("最后交易日不能为空！");
    	commodityForm.settleDate.focus();
    	return false;
    }
    if (commodityForm.limitCmdtyQty.value == "") {
    	alert("商品单边最大订货量不能为空！");
    	commodityForm.limitCmdtyQty.focus();
    	return false;
    }
    if (commodityForm.marginAlgr.value == "1") {
    	if (commodityForm.marginPriceType.value == "") {
    		alert("保证金计算依据不能为空！");
    		return false;
    	}
    }
    	
    if (commodityForm.addedTax.value == "") {
    	alert("增值税率不能为空！");
    	return false;
    	}
    var holdDaysLimit=document.getElementById("holdDaysLimit").value;
    if(holdDaysLimit==1){
   		 if (commodityForm.maxHoldPositionDay.value == "") {
    		alert("最长持仓天数不能为空！");
    		commodityForm.maxHoldPositionDay.focus();
    		return false;
    	}
    }	
    if (commodityForm.lowestSettleFee.value == "") {
    	alert("交收手续费最低金额不能为空！");
    	commodityForm.lowestSettleFee.focus();
    	return false;
    }
    if (commodityForm.firmCleanQty.value == "") {
    	alert("交易商净订货量不能为空！");
    	commodityForm.firmCleanQty.focus();
    	return false;
    }
    
    if (commodityForm.oneMaxHoldQty.value == "") {
    	alert("单笔最大委托量不能为空！");
    	commodityForm.oneMaxHoldQty.focus();
    	return false;
    }
    
 	if (commodityForm.settleMarginRate_B.value == "") {
 		alert("买方标准不能为空！");
 		commodityForm.settleMarginRate_B.focus();
    	return false;
 	}
 	if (commodityForm.settleMarginRate_S.value == "") {
 		alert("卖方标准不能为空！");
 		commodityForm.settleMarginRate_S.focus();
    	return false;
 	}
 
 	if (document.getElementById("settleDate7").value == "") {
 		alert("交收保证金生效日期不能为空！");
    	return false;
 	}
 	
 	if (commodityForm.orderPrivilege_B.value == "") {
 		alert("买方委托权限不能为空！");
 		commodityForm.orderPrivilege_B.focus();
    	return false;
 	}
 	
 	if (commodityForm.orderPrivilege_S.value == "") {
 		alert("卖方委托权限不能为空！");
 		commodityForm.orderPrivilege_S.focus();
    	return false;
 	}
 	if (commodityForm.settlePriceType.value == "") {
 		alert("提前交收价方式不能为空！");
 		commodityForm.settlePriceType.focus();
    	return false;
 	}
 	
 	if (commodityForm.aheadSettlePriceType.value == "") {
 		alert("交收价计算方式不能为空！");
 		commodityForm.aheadSettlePriceType.focus();
    	return false;
 	}
 	//add by yangpei 2011-12-2交收保证金计算方式
 	if (commodityForm.settleMarginType.value == "") {
 		alert("交收保证金计算方式不能为空！");
 		commodityForm.settleMarginType.focus();
    	return false;
 	}
    
	if (commodityForm.type101[1].checked == true) {
		if (commodityForm.limitCmdtyQty.value < 0) {
			alert("商品单边最大订货量不能为负！");
			return false;
		}
	}

	if (commodityForm.type102[1].checked == true) {
		if (commodityForm.firmCleanQty.value < 0) {
			alert("交易商净订货量不能为负！");
			return false;
		}
	}

	if (commodityForm.type103[1].checked == true) {
		if (commodityForm.firmMaxHoldQtyAlgr.value == "") {
 			alert("交易商订货量限制算法不能为空！");
 			return false;
 		}
 		if (commodityForm.firmMaxHoldQtyAlgr.value == "2") {
 			if (commodityForm.firmMaxHoldQty.value == "") {
		 		alert("交易商最大双边订货量不能为空！");
		 		commodityForm.firmMaxHoldQty.focus();
		    	return false;
		 	}
		 	if (commodityForm.firmMaxHoldQty.value < 0) {
				alert("交易商最大双边订货量不能为负！");
				return false;
			}
 		}else {
 			if (commodityForm.startPercentQty.value == "") {
 				alert("商品订货量阀值不能为空！");
 				return false;
 			}
 			if (commodityForm.maxPercentLimit.value == "") {
 				alert("订货最大比例不能为空！");
 				return false;
 			}
 			commodityForm.firmMaxHoldQty.value = parseInt((commodityForm.startPercentQty.value * (commodityForm.maxPercentLimit.value/100))+"");
 			if (commodityForm.firmMaxHoldQty.value < 1) {
 				alert("阀值与比例设置不正确，乘积最小为1！");
 				return false;
 			}
 		}
	}else {
		commodityForm.firmMaxHoldQtyAlgr.value = '2';
		commodityForm.startPercentQty.value = '0';
		commodityForm.maxPercentLimit.value = '0';
	}
	
	
	if (commodityForm.type104[1].checked == true) {
		if (commodityForm.oneMaxHoldQty.value < 0) {
			alert("单笔最大委托量不能为负！");
			return false;
		}
	}
	
	if (commodityForm.settleDate2.value != "") {
		if ((commodityForm.marginItem2.value == "0.0") || (commodityForm.marginItem2.value == "0")) {
			if (confirm("保证金为0，是否继续？")) {
				
			}else {
				return false;
			}
		}
	}
	
	if (commodityForm.settleDate2.value != "") {
		if ((commodityForm.marginItem2.value == "0.0") || (commodityForm.marginItem2.value == "0")) {
			if (confirm("保证金为0，是否继续？")) {
				
			}else {
				return false;
			}
		}
	}
	
	
	if (commodityForm.settleDate2.value != "") {
		if ((commodityForm.marginItem2.value == "0.0") || (commodityForm.marginItem2.value == "0")) {
			if (confirm("保证金为0，是否继续？")) {
				
			}else {
				return false;
			}
		}
	}
	
	if (commodityForm.settleDate2.value != "") {
		if ((commodityForm.marginItem2.value == "0.0") || (commodityForm.marginItem2.value == "0")) {
			if (confirm("保证金为0，是否继续？")) {
				
			}else {
				return false;
			}
		}
	}
	
	if (commodityForm.payoutRate.value == "") {
		alert("交收货款标准不能为空！");
		return false;
	}
	
	if (commodityForm.payoutAlgr.value == "") {
		alert("交收货款算法不能为空！");
		return false;
	}
	
	if (commodityForm.settleMarginAlgr_S.value == "") {
		alert("卖方方式不能为空！");
		return false;
	}
	
	if (commodityForm.settleMarginAlgr_B.value == "") {
		alert("买方方式不能为空！");
		return false;
	}
	
	
	if (commodityForm.delayRecoupRate.value == "") {
		alert("买方延期补偿比率不能为空！");
		return false;
	}
	if (commodityForm.delayRecoupRate_S.value == "") {
		alert("卖方延期补偿比率不能为空！");
		return false;
	}
	if (commodityForm.storeRecoupRate.value == "") {
		alert("仓储补偿费不能为空！");
		return false;
	}
	if(commodityForm.storeRecoupRate.value >= 100){
		alert("仓储补偿费必须小于100");
		return false;
	}
	if (commodityForm.settleWay.value == "") {
		alert("交收方式不能为空！");
		return false;
	}
	if (commodityForm.delayFeeWay.value == "") {
		alert("延期费收取方式不能为空！");
		return false;
	}

    commodityForm.addedTaxFactor.value = (parseFloat(commodityForm.addedTax.value)/(parseFloat(commodityForm.addedTax.value)+100));
    setEnabled(commodityForm.marginPriceType);
    if(confirm("是否级联增加该商品所属品种下的交易商特殊设置？")){
    	commodityForm.action="<c:url value='/timebargain/baseinfo/commodity.do?funcflg=save&logos=true'/>";
		commodityForm.submit();
	}else{
		commodityForm.submit();
	}
    commodityForm.save.disabled = true;
	}
}
//cancel
function cancel_onclick()
{
  parent.HeadFrame.query();
}

//breedID_onchange

//market_onchange

//spreadAlgr_onchange()
function spreadAlgr_onchange()
{
	if (commodityForm.spreadAlgr.value == "1") {
		document.getElementById("spreadUpLmtPercent").className = "xian";
		document.getElementById("spreadDownLmtPercent").className = "xian";
	}else {
		document.getElementById("spreadUpLmtPercent").className = "yin";
		document.getElementById("spreadDownLmtPercent").className = "yin";
	}

    if(commodityForm.spreadAlgr.value == "3")
    {
        commodityForm.spreadUpLmt.value = 0;
        commodityForm.spreadDownLmt.value = 0;
        setReadOnly(commodityForm.spreadUpLmt);
        setReadOnly(commodityForm.spreadDownLmt);
    } 
    else
    {
        setReadWrite(commodityForm.spreadUpLmt);
        setReadWrite(commodityForm.spreadDownLmt);
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
    commodityForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo = baseinfo.innerHTML;
    baseinfo.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up = true;
    commodityForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
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
    commodityForm.baseinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo2 = baseinfo2.innerHTML;
    baseinfo2.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up2 = true;
    commodityForm.baseinfo_img2.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
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
    commodityForm.baseinfo_img3.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo3 = baseinfo3.innerHTML;
    baseinfo3.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up3 = true;
    commodityForm.baseinfo_img3.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
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
    commodityForm.baseinfo_img4.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo4 = baseinfo4.innerHTML;
    baseinfo4.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up4 = true;
    commodityForm.baseinfo_img4.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo4.innerHTML = tmp_baseinfo4;
  }
}

var tmp_baseinfo8;
var tmp_baseinfo_up8 = true;
function baseinfo8_onclick()
{
  if (tmp_baseinfo_up8)
  {
    tmp_baseinfo_up8 = false;
    commodityForm.baseinfo_img8.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo8 = baseinfo8.innerHTML;
    baseinfo8.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up8 = true;
    commodityForm.baseinfo_img8.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo8.innerHTML = tmp_baseinfo8;
  }
}

var tmp_baseinfoy;
var tmp_baseinfo_upy = true;
function baseinfoy_onclick()
{
  if (tmp_baseinfo_upy)
  {
    tmp_baseinfo_upy = false;
    commodityForm.baseinfo_imgy.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfoy = baseinfoy.innerHTML;
    baseinfoy.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_upy = true;
    commodityForm.baseinfo_imgy.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfoy.innerHTML = tmp_baseinfoy;
  }
}
//-----------------------------end baseinfo-----------------------------


//---------------------------start settleinfo-------------------------------
var tmp_settleinfo;
var tmp_settleinfo_up = true;
function settleinfo_onclick()
{
  if (tmp_settleinfo_up)
  {
    tmp_settleinfo_up = false;
    commodityForm.settleinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_settleinfo = settleinfo.innerHTML;
    settleinfo.innerHTML = "";
  }
  else
  {
    tmp_settleinfo_up = true;
    commodityForm.settleinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    settleinfo.innerHTML = tmp_settleinfo;
  }
}
//-----------------------------end settleinfo-----------------------------
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
    if(commodityForm.todayCloseFeeRate_B.value==""){
        commodityForm.todayCloseFeeRate_B.value = commodityForm.feeRate_B.value;
        commodityForm.historyCloseFeeRate_B.value = commodityForm.feeRate_B.value;
    }
 }
 function changes(){
    if(commodityForm.todayCloseFeeRate_S.value==""){
        commodityForm.todayCloseFeeRate_S.value = commodityForm.feeRate_S.value;
        commodityForm.historyCloseFeeRate_S.value = commodityForm.feeRate_S.value;
    } 
 }  
 
  function marginPriceType_change(){
 	var marginAlgr = commodityForm.marginAlgr.value;
 	var tdName = document.getElementById("td1s");
 	if (marginAlgr == 1) {
 		tdName.innerHTML = '保证金计算依据：';
 		document.getElementById("td1s").className = "xian";
 		document.getElementById("td2s").className = "xian";
 		commodityForm.marginPriceType.value = "";
 	}else {
 		tdName.innerHTML = '';
 		document.getElementById("td1s").className = "yin";
 		document.getElementById("td2s").className = "yin";
 		commodityForm.marginPriceType.value = "0";
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
	commodityForm.settleDate1.value = commodityForm.marketDate.value;
}

function change_date_settle(){
	document.getElementById("settleDate7").value = commodityForm.settleDate.value;
}

 function changeManner101(id){
 	if (id == "1") {
 		setReadWrite(commodityForm.limitCmdtyQty);
 	}
 	if (id == "2") {
 		commodityForm.limitCmdtyQty.value = "-1";
 		setReadOnly(commodityForm.limitCmdtyQty);
 	}
 }
 
  function changeManner102(id){
 	if (id == "1") {
 		setReadWrite(commodityForm.firmCleanQty);
 	}
 	if (id == "2") {
 		commodityForm.firmCleanQty.value = "-1";
 		setReadOnly(commodityForm.firmCleanQty);
 	}
 }
 
  function changeManner104(id){
 	if (id == "1") {
 		setReadWrite(commodityForm.oneMaxHoldQty);
 	}
 	if (id == "2") {
 		commodityForm.oneMaxHoldQty.value = "-1";
 		setReadOnly(commodityForm.oneMaxHoldQty);
 	}
 }
 
 function changeManner103(id){
 	if (id == "1") {
 		setReadWrite(commodityForm.firmMaxHoldQty);
 		//控制交易商订货量限制算法隐现
 		document.getElementById("tdFirmMaxHoldQtyAlgr").style.visibility = 'visible';
 		changeFirmMaxHoldQtyAlgr();
 	}
 	if (id == "2") {
 		document.getElementById("tdFirmMaxHoldQtyAlgr").style.visibility = 'hidden';
 		document.getElementById("tdStartPercentQty").style.visibility = 'hidden';
 		document.getElementById("tdMaxPercentLimit").style.visibility = 'hidden';
 		document.getElementById("tdFirmMaxHoldQty").style.visibility = 'visible';
 		commodityForm.firmMaxHoldQty.value = "-1";
 		setReadOnly(commodityForm.firmMaxHoldQty);
 	}
 }
 
 	function changeFirmMaxHoldQtyAlgr(){
 		if (commodityForm.firmMaxHoldQtyAlgr.value == "1") {
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
	
	if (settlePriceType == "3") {//指定交收价
		document.getElementById("bbb0").className = "yin";
	 	document.getElementById("aaa0").className = "yin";
		document.getElementById("ccc0").className = "xian";
	 	document.getElementById("ddd0").className = "xian";
	}else if (settlePriceType == "1"){//计算交收价的提前日
		document.getElementById("bbb0").className = "xian";
	 	document.getElementById("aaa0").className = "xian";
	 	document.getElementById("ccc0").className = "yin";
	 	document.getElementById("ddd0").className = "yin";
		
	}else {
		document.getElementById("bbb0").className = "yin";
	 	document.getElementById("aaa0").className = "yin";
	 	document.getElementById("ccc0").className = "yin";
	 	document.getElementById("ddd0").className = "yin";
	}
}
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
	if (commodityForm.settleFeeAlgr.value == "1") {
		document.getElementById("settleFeeRate_BPercent").className = "xian";
		document.getElementById("settleFeeRate_SPercent").className = "xian";
	}else {
		document.getElementById("settleFeeRate_BPercent").className = "yin";
		document.getElementById("settleFeeRate_SPercent").className = "yin";
	}
}

function settleMarginAlgr_B_change(){
	if (commodityForm.settleMarginAlgr_B.value == "1") {
		document.getElementById("settleMarginRate_BPercent").className = "xian";
	}else {
		document.getElementById("settleMarginRate_BPercent").className = "yin";
	}
}

function settleMarginAlgr_S_change(){
	if (commodityForm.settleMarginAlgr_S.value == "1") {
		document.getElementById("settleMarginRate_SPercent").className = "xian";
	}else {
		document.getElementById("settleMarginRate_SPercent").className = "yin";
	}
}

function payoutAlgr_change(){
	if (commodityForm.payoutAlgr.value == "1") {
		document.getElementById("payoutRatePercent").className = "xian";
	}else {
		document.getElementById("payoutRatePercent").className = "yin";
	}
}

function marginAlgr_change(){
	if (commodityForm.marginAlgr.value == "1") {
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
		
		document.getElementById("marginRate_BPercent").innerHTML = '%';
		document.getElementById("marginRate_SPercent").innerHTML = '%';
		
		//commodityForm.marginRate_B.value = commodityForm.marginRate_B.value*100;
    	//commodityForm.marginRate_S.value = commodityForm.marginRate_S.value*100;
		
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
		
		document.getElementById("marginRate_BPercent").innerHTML = '';
		document.getElementById("marginRate_SPercent").innerHTML = '';
		
		//commodityForm.marginRate_B.value = commodityForm.marginRate_B.value/100;
		//commodityForm.marginRate_S.value = commodityForm.marginRate_S.value/100;
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
			
			if (commodityForm.settleWay.value == "1") {
				document.getElementById("settleDate7").value = relDate;
				commodityForm.settleDate.value = relDate;
				setReadOnly(commodityForm.settleDate);
			}else {
				setReadWrite(commodityForm.settleDate);
			}
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
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="400" width="790" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/commodity.do?funcflg=save"
						method="POST" styleClass="form" target="HiddFrame">
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
                    <td ><img id="baseinfo_img" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="baseinfo">
<table cellSpacing="0" cellPadding="0"  width="100%" border="0" align="center" class="common">
        
        <tr >
        	<td align="right" width="138">商品品种：</td>
            <td >
            
            <%
               LabelValue lv1=(LabelValue)request.getAttribute("LabelValue1");
               if(lv1!=null)
               {
             %>
             <%=lv1.getLabel()%>
            <%
            }
             %>
            <html:hidden property="breedID"/>
            <span class="req">&nbsp;</span>
            </td> 
            <td align="right" width="140">品种分类：</td>
            <td>
				
                <%
               LabelValue lv2=(LabelValue)request.getAttribute("LabelValue2");
               if(lv2!=null)
               {
             %>
             <%=lv2.getLabel()%>
            <%
            }
             %>
                <html:hidden property="sortID"/>

			</td>    
			
        </tr>
        
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        
        <tr>
             
            <td align="right">商品名称：</td>
            <td ><html:text property="name" onkeypress="chackValue2()" onkeyup="value=value.replace(/[\n\s\t]/g,'') " maxlength="16" styleClass="text" size="10"/>
            <span class="req">*</span></td> 
            
            <td align="right">商品代码：</td>
            <td ><html:text property="commodityID" onkeypress="chackValue()" onkeydown="chackValue3()" onkeyup="value=value.replace(/[\n\s\t]/g,'') " style="ime-mode:disabled"  maxlength="11" styleClass="text" size="10"/>
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
            <span class="req">*</span></td>
            
            <c:if test="${param['crud'] == 'create'}">
            <td align="right" width="80">当前状态：</td>
            	<td width="110">
				<html:select property="status" style="width:80">
					<html:option value=""></html:option>
				    <html:option value="0">有效</html:option>
					<html:option value="2">暂停交易</html:option>					
			   </html:select> 
			   <span class="req">*</span>            
            </td>  
            </c:if>
            <c:if test="${param['crud'] == 'update'}">
            
            	<%
            		String status = (String)request.getAttribute("status");
            		String name = (String)request.getAttribute("name");
            		if ("1".equals(status)) {
            			%>
            			<td align="right" width="80">当前状态：</td>
            			<td width="110">
            				<%=name%>
            				<html:hidden property="status" value="<%=status%>"/>
            			</td>
            			
            			<%
            		}else {
            		%>
            		<td align="right" width="80">当前状态：</td>
            		<td width="110">
            		<html:select property="status" style="width:80">
					<html:option value=""></html:option>
				    <html:option value="0">有效</html:option>
					<html:option value="2">暂停交易</html:option>					
			   </html:select> 
			   <span class="req">*</span>
			   </td>
            	<%
            		}
            	%>
            	
            	
            	
            </c:if>
              
                               
        </tr>       
        
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        <tr>
        	  <td align="right">开盘指导价：</td>
            <td>
			  <html:text property="lastPrice" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="10"/>  
              <span class="req">*</span>       
            </td>  
             <td align="right">上市日期：</td>
			<td>
				<input type="text" style="width: 100px" id="marketDate"
															class="wdate" maxlength="10"
															name="marketDate"
															value='<%=commodityForm.getMarketDate() == null ? "" : commodityForm.getMarketDate()%>'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"
															onchange="change_date()">
				<%--<MEBS:calendar eltID="marketDate" eltName="marketDate" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<%=commodityForm.getMarketDate() == null ? "" : commodityForm.getMarketDate()%>" eltF="settleDate1" />--%>
				<span class="req">*</span>
			</td>
            <td align="right" >最后交易日：</td>
			<td>
				<input type="text" style="width: 100px" id="settleDate"
															class="wdate" maxlength="10"
															name="settleDate"
															value='<%=commodityForm.getSettleDate() == null ? "" : commodityForm.getSettleDate()%>'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"
															onchange="change_date_settle()">
				<%--<MEBS:calendar eltID="settleDate" eltName="settleDate" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<%=commodityForm.getSettleDate() == null ? "" : commodityForm.getSettleDate()%>" eltF="settleDate7" />--%>
				<span class="req">*</span>
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
                    <td ><img id="baseinfo_img3" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo3_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="baseinfo3">
<table cellSpacing="0" cellPadding="0" width="720" border="0" align="center" class="common">                     
         <tr>
         	<td align="right" width="140">交易单位：
         	</td>
         	<td width="150">
         	<html:text property="contractFactor"  maxlength="11" style="ime-mode:disabled"  styleClass="text" size="10" onkeypress="chackValue2()"/>
			  <span class="req">
			  <% 
			   if(commodityForm.getContractFactorName()!=null){
			   %>
			   (<%=commodityForm.getContractFactorName()%>/批)
			   <% 
			   }
			   %></span><span class="req">*</span>            
            </td>
            
            <td align="right" width="115">最小变动价位(元)：</td><td><html:text property="minPriceMove"  maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="10"/>  
			  <span class="req">*</span>          
            </td>
            
             <td align="right" width="100">
				最小变动数量：
			 </td>
			 <td width="115">
				<html:text size="10" property="minQuantityMove" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="10" styleClass="text" />
				<span class="req">*</span>
			</td>
		</tr>
		<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
         <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
          
          <%
        	String typeSpreadAlgr = (String)request.getAttribute("typeSpreadAlgr");
          %>
          <tr>
            <td align="right" >涨跌停板算法：
            </td>
            <td>
            <html:select property="spreadAlgr" style="width:80" onchange="spreadAlgr_onchange()">
					<html:option value=""></html:option>
				    <html:option value="1">按百分比</html:option>
					<html:option value="2">按绝对值</html:option>
			   </html:select> <span class="req">*</span>
            </td>
            <td align="right" >涨幅上限：</td>
			<td><html:text size="10" property="spreadUpLmt" maxlength="10" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />
			  <span id="spreadUpLmtPercent" class="<%if("1".equals(typeSpreadAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>           
            </td>
            <td align="right">跌幅下限：</td>
            <td>
			  <html:text size="10" property="spreadDownLmt" maxlength="10" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />
			  <span id="spreadDownLmtPercent" class="<%if("1".equals(typeSpreadAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>   
			  &nbsp;     
            </td>
        </tr>   
     <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
      		<tr>
			<td align="right" >&nbsp;买方委托权限：</td>
            <td >
				<html:select property="orderPrivilege_B" style="width:80">
					<html:option value=""></html:option>
				    <html:option value="101">全权</html:option>
					<html:option value="102">只可订立</html:option>
					<html:option value="103">只可转让</html:option>
					<html:option value="104">无权</html:option>
			   </html:select> <span class="req">*</span>            
            </td>  
            
            <td align="right" >&nbsp;卖方委托权限：</td>
            <td >
				<html:select property="orderPrivilege_S" style="width:80">
					<html:option value=""></html:option>
				    <html:option value="101">全权</html:option>
					<html:option value="102">只可订立</html:option>
					<html:option value="103">只可转让</html:option>
					<html:option value="104">无权</html:option>
			   </html:select> <span class="req">*</span>            
            </td> 
            <td align="right" width="80">
				增值税率：
			</td>
			<td width="115">
				<html:text size="10" property="addedTax" maxlength="10" styleClass="text" onkeypress="return suffixNamePress()"/>
				<span >%</span><span class="req">*</span>
			</td>
			</tr>
		<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
	<td align="right" >
					提前交收价计算方式：
			</td>
			<td>
				<html:select property="aheadSettlePriceType" style="width:120"  >
					<html:option value=""></html:option>
				    <html:option value="0">按订立价交收</html:option>
					<html:option value="1">按自主价交收</html:option> 
			    </html:select> <span class="req">*</span>
			</td><%
			String typeSettlePriceType = (String)request.getAttribute("typeSettlePriceType");
		%>
	<tr>
		<td align="right" >
					交收价计算方式：
			</td>
			<td width="140">
				<html:select property="settlePriceType" style="width:120" onchange="change()">
					<html:option value=""></html:option>
				    <html:option value="0">闭市结算价</html:option>
					<html:option value="1">最后几日加权平均价</html:option>
					<html:option value="2">订立价</html:option>
					<html:option value="3">指定交收价</html:option>
			    </html:select> <span class="req">*</span>
			</td>
			
			<td align="right"  class="<%if("1".equals(typeSettlePriceType)){%>xian<%}else{%>yin<%}%>" id="aaa0">
				计算交收价提前日：
			</td>
			<td width="140" class="<%if("1".equals(typeSettlePriceType)){%>xian<%}else{%>yin<%}%>" id="bbb0">
			<html:text size="10" property="beforeDays" maxlength="10" styleClass="text" onkeypress="return suffixNamePress()"/>(交易日)
			<span class="req">&nbsp;</span>
			</td>
									
			<td align="right" class="<%if("3".equals(typeSettlePriceType)){%>xian<%}else{%>yin<%}%>" id="ccc0">
				指定交收价：
			</td>
			<td  class="<%if("3".equals(typeSettlePriceType)){%>xian<%}else{%>yin<%}%>" id="ddd0">
			<html:text size="10" property="specSettlePrice" maxlength="10" styleClass="text" onkeypress="return suffixNamePress()"/>
			<span width="100" class="req">&nbsp;</span>
			</td>
	</tr>
		<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>

	 	
		<!-- add by yangpei 2011-11-02 -->
	<%
		String typeSettleMarginType = (String)request.getAttribute("typeSettleMarginType");
	 %>
	<tr>
			<td align="right" >
					交收保证金计算方式：
			</td>
			<td>
				<html:select property="settleMarginType" style="width:120" onchange="change2()">
				    <html:option value="0">闭市结算价</html:option>
					<html:option value="1">最后几日加权平均价</html:option>
					<html:option value="2">订立价</html:option>
			    </html:select> <span class="req">*</span>
			</td>
			
			<td align="right"  class="<%if("1".equals(typeSettleMarginType)){%>xian<%}else{%>yin<%}%>" id="aaa02">
				计算交收保证金提前日：
			</td>
			<td class="<%if("1".equals(typeSettleMarginType)){%>xian<%}else{%>yin<%}%>" id="bbb02">
			<html:text size="10" property="beforeDays_M" maxlength="10" styleClass="text" onkeypress="return suffixNamePress()"/>(交易日)
			<span class="req">&nbsp;</span>
			</td>
									
			<td></td><td></td>
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
										   		<html:text property="maxHoldPositionDay" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="11"/><span>交易日</span><span class="req">*</span> 
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
                  <col width="95"></col><col></col><col width="6"></col>
                  <tr>
                    <td><b>订货量限制</b></td>
                    <td><hr width="609" class="pickList"/></td>
                    <td ><img id="baseinfo_img8" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo8_onclick()"/></td>
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
							String firmMaxHoldQtyAlgr = (String)request.getAttribute("firmMaxHoldQtyAlgr");
				%>
       		
         	<td align="right">&nbsp;&nbsp;商品单边最大订货量(批)：</td>
            <td>
            
			<input type="radio" name="type101" value="2" onclick="changeManner101(2);" <%if("2".equals(type101)){out.println("checked");} %> style="border:0px;">不限制
            <input type="radio" name="type101" value="1" onclick="changeManner101(1);" <%if("1".equals(type101)){out.println("checked");} %> style="border:0px;">限制
            <html:text property="limitCmdtyQty"  style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>  
			  <span class="req">*</span>          
            </td>
			
						<td align="right" width="120">
							交易商净订货量：
						</td>
						<td>
							
							<input type="radio" name="type102" value="2" onclick="changeManner102(2);" <%if("2".equals(type102)){out.println("checked");} %> style="border:0px;">不限制
							<input type="radio" name="type102" value="1" onclick="changeManner102(1);" <%if("1".equals(type102)){out.println("checked");} %> style="border:0px;">限制
							<html:text property="firmCleanQty"  styleClass="text" onkeypress="return onlyNumberInput()" size="10"/>
            				<span class="req">*</span>
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
			            <span id="tdFirmMaxHoldQty" style="visibility:<%if("1".equals(firmMaxHoldQtyAlgr)){%>visible<%}else{%>hidden<%}%>;">
	           				<html:text property="firmMaxHoldQty"  onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>
	           			<span class="req">*</span>
			           	</td>
			           	
			<td align="right">&nbsp;&nbsp;单笔最大委托量：</td>
            <td>
            
			<input type="radio" name="type104" value="2" onclick="changeManner104(2);" <%if("2".equals(type104)){out.println("checked");} %> style="border:0px;">不限制
            <input type="radio" name="type104" value="1" onclick="changeManner104(1);" <%if("1".equals(type104)){out.println("checked");} %> style="border:0px;">限制
            <html:text property="oneMaxHoldQty"  style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>  
			  <span class="req">*</span>          
            </td>
					</tr>
					
       				<tr>	
       			
           	<td id="tdFirmMaxHoldQtyAlgr" class="" colspan="4">
           		&nbsp;&nbsp;&nbsp;&nbsp;交易商订货量限制算法：
           		<html:select property="firmMaxHoldQtyAlgr" style="width:80" onchange="changeFirmMaxHoldQtyAlgr()">
					<html:option value=""></html:option>
				    <html:option value="1">按百分比</html:option>
					<html:option value="2">按绝对值</html:option>
			   </html:select> 
               <span class="req">*</span>
               
               &nbsp;&nbsp;&nbsp;&nbsp;
               <span id="tdStartPercentQty" style="visibility:<%if("1".equals(firmMaxHoldQtyAlgr)){%>visible<%}else{%>hidden<%}%>;">
               		商品订货量阀值：<html:text property="startPercentQty" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>
            		<span class="req">*</span>
               </span>
               
               &nbsp;&nbsp;&nbsp;&nbsp;
               <span id="tdMaxPercentLimit" style="visibility:<%if("1".equals(firmMaxHoldQtyAlgr)){%>visible<%}else{%>hidden<%}%>;">
               		订货最大比例：<html:text property="maxPercentLimit" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>
            		<span>%</span><span class="req">*</span>
               </span>
           	</td>
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
                    <td ><img id="baseinfo_img2" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo2_onclick()"/></td>
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
						交易手续费算法：<html:select property="feeAlgr" style="width:80" onchange="on_change()">
					<html:option value=""></html:option>
				    <html:option value="1">按百分比</html:option>
					<html:option value="2">按绝对值</html:option>
			   </html:select> 
			   <span class="req">*</span>  
			</td>	            		            
		            
		           <td align="right" colspan="1" width="210" height="5">
		            	买订立：<html:text property="feeRate_B" maxlength="6"  style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" onchange="changeb()" size="10"/>
			  			<span id="feeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
			  	   </td>
			  	   <td align="right" >
			  		卖订立：<html:text property="feeRate_S"  maxlength="6" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" onchange="changes()" size="10"/>
			  		<span id="feeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
			  	   </td>
			  	   <td width="20"></td>
		          </tr>

				   <tr> 		            
		           <td width="210" align="right" height="5">
		           	买转让历史订货：<html:text property="historyCloseFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="10"/>
		           	<span id="historyCloseFeeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
		           </td>
			  	   <td align="right" >
		           	卖转让历史订货：<html:text property="historyCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="10"/>
		           	<span id="historyCloseFeeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
		          </td>
		              </tr>

				<tr> 		            
		            <td align="right" width="210" height="5">
		            	买转让今订货：<html:text property="todayCloseFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="10"/>
		            	<span id="todayCloseFeeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
		            </td>
			  	   	<td align="right" >	
		            	卖转让今订货：<html:text property="todayCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="10"/>
		            	<span id="todayCloseFeeRate_SPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
		            </td>
		        </tr>
		              
		              <tr>
		              	<td align="right" width="210">
		              	买强制转让：<html:text size="10" property="forceCloseFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" onchange="changeb()"/>
			  			<span id="forceCloseFeeRate_BPercent" class="<%if("1".equals(typeFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
			  			</td>
			  	   		<td align="right" >
			  			卖强制转让：<html:text size="10" property="forceCloseFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" onchange="changes()"/>
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
			   <span class="req">*</span>            
            </td>
            <td align="right" width="210">
            	交收买：<html:text size="10" property="settleFeeRate_B" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" onchange="changeb()"/>
			  <span id="settleFeeRate_BPercent" class="<%if("1".equals(typeSettleFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
			  </td>
			  <td align="right" >
			  交收卖：<html:text size="10" property="settleFeeRate_S" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" onchange="changes()"/>
			  <span id="settleFeeRate_SPercent" class="<%if("1".equals(typeSettleFeeAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>         
           </td>
        </tr> 
         <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
    <tr>
    	
            <td align="right">
				最低交收手续费金额：<html:text property="lowestSettleFee" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="10"/>
			   <span class="req">*</span>            
            </td>	
            <td align="right">&nbsp;
            <!-- 
				最大交易手续费系数：<html:text property="maxFeeRate" maxlength="15" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" size="10"/>
			   <span>%</span><span class="req">*</span>            
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
                    <td ><img id="settleinfo_img" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:settleinfo_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="settleinfo">
<table cellSpacing="0" cellPadding="0" width="720" border="0" align="center" class="common">  
           <tr>
            <td align="right" width="150">&nbsp;&nbsp;&nbsp;&nbsp;保证金算法：
            </td>
            
            <td width="98">
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
            			<td align="right" width="100" id="td1s" class="<%if("1".equals(typeBZJ)){%>xian<%}else{%>yin<%}%>">
											保证金计算依据：
									</td>
									<td width="260" id="td2s" colspan="3" class="<%if("1".equals(typeBZJ)){%>xian<%}else{%>yin<%}%>">
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
            <td align="right" width="150">第一阶段保证金 生效日期：</td>
			<td >
			  	<html:text size="10" property="settleDate1" styleId="settleDate1" maxlength="19" style="ime-mode:disabled;width:80;" onkeypress="return numberPass()" styleClass="text"/>
				<span class="req">*</span>
			</td>
			
			<td align="right">
           			 买卖保证金：
            </td>
            <td colspan="3">
            
             <input type="radio" name="type" value="1" onclick="changeManner(1);" <%if("1".equals(type)){out.println("checked");} %> style="border:0px;">相同
             
             <input type="radio" name="type" value="2" onclick="changeManner(2);" <%if("2".equals(type)){out.println("checked");} %> style="border:0px;">不同
            </td >
			
			
        </tr>
        <tr>
        	<td>&nbsp;</td>
            <td width="100">&nbsp;</td>
            
             <td align="right" id="td1"><%if("1".equals(type)){%>保证金1<%}else{%>买保证金1<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItem1" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress2()" styleClass="text" />
			  <span id="marginItem1Percent"></span><span class="req">*</span>           
            </td>
            <td align="right" width="130" id="td2"><%if("1".equals(type)){%>担保金1<%}else{%>买担保金1<%}%>：</td>
            <td width="115">
			  <html:text size="10" property="marginItemAssure1" maxlength="11" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />
			  <span id="marginItemAssure1Percent"></span><span class="req">*</span>           
            </td>     
            </tr>
           <tr class="<%if("1".equals(type)){%>yin<%}else{%>xian<%}%>" id="aaa">
             <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right"  id="abc2">卖保证金1：</td>
            <td  id="abc1" style="visibility:<%if("1".equals(type)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItem1_S" maxlength="11"  onkeypress="return suffixNamePress2()" styleClass="text" />
			  <span id="marginItem1_SPercent"></span><span class="req">*</span>           
            </td >
            <td align="right"  id="abc3">
            卖担保金1：</td>
            <td  id="abc4" style="visibility:<%if("1".equals(type)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItemAssure1_S" maxlength="11"  onkeypress="return suffixNamePress()" styleClass="text" />
			  <span id="marginItemAssure1_SPercent"></span><span class="req">*</span>           
            </td>
        </tr>
        
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        
          <%
             String type2=(String)request.getAttribute("type2");
             %>
        <tr>
            <td align="right" >第二阶段保证金 生效日期：</td>
			<td>
				<input type="text" style="width: 100px" id="settleDate2"
															class="wdate" maxlength="10"
															name="settleDate2"
															value='<%=commodityForm.getSettleDate2() == null ? "" : commodityForm.getSettleDate2()%>'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
				<%--<MEBS:calendar eltID="settleDate2" eltName="settleDate2" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<%=commodityForm.getSettleDate2() == null ? "" : commodityForm.getSettleDate2()%>" />--%>
			</td>
			<td align="right">
            买卖保证金：
            </td>
			<td colspan="3">
            
             <input type="radio" name="type1" value="1" onclick="changeManner1(1);" <%if("1".equals(type1)){out.println("checked");} %> style="border:0px;">相同
             
             <input type="radio" name="type1" value="2" onclick="changeManner1(2);" <%if("2".equals(type1)){out.println("checked");} %> style="border:0px;">不同
            </td >
			
			
            </tr>
            <tr>
        <td >&nbsp;</td>
        <td>&nbsp;</td>
        <td align="right" id="td3">&nbsp;&nbsp;<%if("1".equals(type1)){%>保证金2<%}else{%>买保证金2<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItem2" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress2()" styleClass="text" />         
            <span id="marginItem2Percent"></span>
            </td> 
            <td align="right" id="td4">&nbsp;&nbsp;<%if("1".equals(type1)){%>担保金2<%}else{%>买担保金2<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItemAssure2" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />         
            <span id="marginItemAssure2Percent"></span>
            </td>  
        </tr>
        <tr class="<%if("1".equals(type1)){%>yin<%}else{%>xian<%}%>" id="aaa1">
            <td>&nbsp;</td>
            <td >
            &nbsp;
            </td >
			<td align="right"  id="abc21">&nbsp;&nbsp;卖保证金2：</td>
            <td style="visibility:<%if("1".equals(type1)){%>hidden<%}else{%>visible<%}%>;" id="abc11">
			  <html:text size="10" property="marginItem2_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress2()" styleClass="text" />         
            <span id="marginItem2_SPercent"></span>
            </td> 
            <td align="right" id="abc31">&nbsp;&nbsp;卖担保金2：</td>
            <td style="visibility:<%if("1".equals(type1)){%>hidden<%}else{%>visible<%}%>;" id="abc41">
			  <html:text size="10" property="marginItemAssure2_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />         
            <span id="marginItemAssure2_SPercent"></span>
            </td>           
        </tr>
            
          <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>  
               
         <%
             String type3=(String)request.getAttribute("type3");
             %>
        <tr>
            <td align="right">第三阶段保证金 生效日期：</td>
			<td>
				<%--<MEBS:calendar eltID="settleDate3" eltName="settleDate3" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<%=commodityForm.getSettleDate3() == null ? "" : commodityForm.getSettleDate3()%>" />--%>
				<input type="text" style="width: 100px" id="settleDate3"
															class="wdate" maxlength="10"
															name="settleDate3"
															value='<%=commodityForm.getSettleDate3() == null ? "" : commodityForm.getSettleDate3()%>'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			</td>
			<td align="right">
            买卖保证金：
            </td>
			<td colspan="3">
          
             <input type="radio" name="type2" value="1" onclick="changeManner2(1);" <%if("1".equals(type2)){out.println("checked");} %> style="border:0px;">相同
             <input type="radio" name="type2" value="2" onclick="changeManner2(2);" <%if("2".equals(type2)){out.println("checked");} %> style="border:0px;">不同
            </td >
			
			
			</tr>
			<tr >
            <td>&nbsp;</td>
            <td>&nbsp;</td>
			<td align="right" id="td5"><%if("1".equals(type2)){%>保证金3<%}else{%>买保证金3<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItem3" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress2()" styleClass="text" />        
            <span id="marginItem3Percent"></span>
            </td>
            <td align="right" id="td6"><%if("1".equals(type2)){%>担保金3<%}else{%>买担保金3<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItemAssure3" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />        
            <span id="marginItemAssure3Percent"></span>
            </td>
            </tr>   
             <tr class="<%if("1".equals(type2)){%>yin<%}else{%>xian<%}%>" id="aaa2">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
			<td align="right" id="abc22">卖保证金3：</td>
            <td style="visibility:<%if("1".equals(type2)){%>hidden<%}else{%>visible<%}%>;" id="abc12">
			  <html:text size="10" property="marginItem3_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress2()" styleClass="text" />        
            <span id="marginItem3_SPercent"></span>
            </td>
            <td align="right"  id="abc32">卖担保金3：</td>
            <td style="visibility:<%if("1".equals(type2)){%>hidden<%}else{%>visible<%}%>;" id="abc42">
			  <html:text size="10" property="marginItemAssure3_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />        
            <span id="marginItemAssure3_SPercent"></span>
            </td>
            </tr>
			
			<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
			
          <%
             String type4=(String)request.getAttribute("type4");
             %>
        <tr>
            <td align="right" >第四阶段保证金 生效日期：</td>
			<td>
				<input type="text" style="width: 100px" id="settleDate4"
															class="wdate" maxlength="10"
															name="settleDate4"
															value='<%=commodityForm.getSettleDate4() == null ? "" : commodityForm.getSettleDate4()%>'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
				<%--<MEBS:calendar eltID="settleDate4" eltName="settleDate4" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<%=commodityForm.getSettleDate4() == null ? "" : commodityForm.getSettleDate4()%>" />--%>
			</td>
			<td align="right">
            买卖保证金：
            </td>
			<td colspan="3">
            
             <input type="radio" name="type3" value="1" onclick="changeManner3(1);" <%if("1".equals(type3)){out.println("checked");} %> style="border:0px;">相同
             <input type="radio" name="type3" value="2" onclick="changeManner3(2);" <%if("2".equals(type3)){out.println("checked");} %> style="border:0px;">不同
            </td >
			
			
            </tr>
            <tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
            <td align="right" id="td7"><%if("1".equals(type3)){%>保证金4<%}else{%>买保证金4<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItem4" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress2()" styleClass="text" />           
            <span id="marginItem4Percent"></span>
            </td>
            <td align="right" id="td8"><%if("1".equals(type3)){%>担保金4<%}else{%>买担保金4<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItemAssure4" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />           
            <span id="marginItemAssure4Percent"></span>
            </td>
        </tr>
         <tr class="<%if("1".equals(type3)){%>yin<%}else{%>xian<%}%>" id="aaa3">
             <td>&nbsp;</td>
             <td>&nbsp;</td>
            <td align="right" id="abc13">卖保证金4：</td>
            <td  id="abc23" style="visibility:<%if("1".equals(type3)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItem4_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress2()" styleClass="text" />           
            <span id="marginItem4_SPercent"></span>
            </td>
            <td align="right" id="abc33">卖担保金4：</td>
            <td  id="abc43" style="visibility:<%if("1".equals(type3)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItemAssure4_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />           
            <span id="marginItemAssure4_SPercent"></span>
            </td>
        </tr>
            
            <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
           
       <tr>
            <td align="right" >第五阶段保证金 生效日期：</td>
			<td>
				<input type="text" style="width: 100px" id="settleDate5"
															class="wdate" maxlength="10"
															name="settleDate5"
															value='<%=commodityForm.getSettleDate5() == null ? "" : commodityForm.getSettleDate5()%>'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
				
				<%--<MEBS:calendar eltID="settleDate5" eltName="settleDate5" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<%=commodityForm.getSettleDate5() == null ? "" : commodityForm.getSettleDate5()%>" />--%>
			</td>
			<td align="right">
            买卖保证金：
            </td>
			<td colspan="3">
            
             <input type="radio" name="type4" value="1" onclick="changeManner4(1);" <%if("1".equals(type4)){out.println("checked");} %> style="border:0px;">相同
             <input type="radio" name="type4" value="2" onclick="changeManner4(2);" <%if("2".equals(type4)){out.println("checked");} %> style="border:0px;">不同
            </td >
			
			
            </tr>
            <tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
            <td align="right" id="td50"><%if("1".equals(type4)){%>保证金5<%}else{%>买保证金5<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItem5" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress2()" styleClass="text" />           
           <span id="marginItem5Percent"></span>
            </td>
            <td align="right" id="td60"><%if("1".equals(type4)){%>担保金5<%}else{%>买担保金5<%}%>：</td>
            <td>
			  <html:text size="10" property="marginItemAssure5" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />           
            <span id="marginItemAssure5Percent"></span>
            </td>
        </tr>
         <tr class="<%if("1".equals(type4)){%>yin<%}else{%>xian<%}%>" id="aaa4">
             <td>&nbsp;</td>
             <td>&nbsp;</td>
            <td align="right" id="abc14">卖保证金5：</td>
            <td  id="abc24" style="visibility:<%if("1".equals(type4)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItem5_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress2()" styleClass="text" />           
            <span id="marginItem5_SPercent"></span>
            </td>
            <td align="right" id="abc34">卖担保金5：</td>
            <td  id="abc44" style="visibility:<%if("1".equals(type4)){%>hidden<%}else{%>visible<%}%>;">
			  <html:text size="10" property="marginItemAssure5_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />           
            <span id="marginItemAssure5_SPercent"></span>
            </td>
        </tr>
            
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
			  <html:text size="10" property="marginRate_B" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />         
              <span id="marginRate_BPercent"></span>
            </td>
            <td align="right">
        		<font color="blue">卖方保证金：</font>
        	</td>
        	<td>
			  <html:text size="10" property="marginRate_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />         
              <span id="marginRate_SPercent"></span>
            </td>
        </tr>
           
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>    
        
        <tr>
        	<td align="right">
        		交收保证金 生效日期：
        	</td>
        	<td >
				<input type="text" id="settleDate7"  style="ime-mode:disabled;width:80;" onkeypress="return numberPass()"/>
				<span class="req">*</span>
			</td> 
        
        <%
        	String typeSettleMarginAlgr_B = (String)request.getAttribute("typeSettleMarginAlgr_B");
        %>
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
			  <html:text size="10" property="settleMarginRate_B" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />         
            <span id="settleMarginRate_BPercent" class="<%if("1".equals(typeSettleMarginAlgr_B)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
            </td>
        </tr>
        
       
        <%
        	String typeSettleMarginAlgr_S = (String)request.getAttribute("typeSettleMarginAlgr_S");
        %>     
        <tr id="aaa4">
             <td>&nbsp;</td>
             <td>&nbsp;</td>
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
			  <html:text size="10" property="settleMarginRate_S" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />         
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
			  <html:text size="10" property="payoutRate" maxlength="16" style="ime-mode:disabled" onkeypress="return suffixNamePress()" styleClass="text" />         
            <span id="payoutRatePercent" class="<%if("1".equals(typePayoutAlgr)){%>xian<%}else{%>yin<%}%>">%</span><span class="req">*</span>
            </td>
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
					                    <td ><img id="baseinfo_imgy" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfoy_onclick()"/></td>
					                  </tr>
					                </table>
					              </legend>
					<span id="baseinfoy">
					<table cellSpacing="0" cellPadding="0" width="700" border="0" align="left" class="common">
        
        <tr>
        	
            <td align="right" width="100">买方延期补偿率：</td>
            <td ><html:text property="delayRecoupRate" maxlength="13" styleClass="text" size="10" onkeypress="suffixNamePress()"/>
            <span >%</span><span class="req">*</span></td>
            
            <td align="right">仓储补偿费：</td>
            <td ><html:text property="storeRecoupRate" maxlength="13" styleClass="text" size="10" onkeypress="suffixNamePress()"/>
            <span class="req">*</span></td>
        </tr>
        <tr> 
            <td align="right" width="100">卖方延期补偿率：</td>
            <td >
				 <html:text property="delayRecoupRate_S" maxlength="13" styleClass="text" size="10" onkeypress="suffixNamePress()"/>
            <span >%</span><span class="req">*</span></td>
            
            <td align="right">交收方式：</td>     
            <td>  
				<html:select property="settleWay"  onchange="settleWay_settleDate()">
					<html:option value=""></html:option>
                   <html:option value="0">按期交收</html:option>
                   <html:option value="1">延期交收</html:option>
                </html:select>
				<span class="req">*</span>
			</td>
			
			
        </tr>
        
        <tr>
			<td align="right" width="100">最小交割单位：
			 </td>
			 <td width="115">
				<html:text size="10" property="minSettleMoveQty" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="10" styleClass="text" />
				<span class="req">*</span>
			</td>
			<td align="right">最小交割数量：
			 </td>
			 <td width="115">
				<html:text size="10" property="minSettleQty" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="10" styleClass="text" />
				<span class="req">*</span>
			</td>
         </tr>  
         <tr>
        <td align="right" width="100">延期费收取方式：</td>     
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
			<html:hidden property="delayRecoupRate" />
			<html:hidden property="delayRecoupRate_S" />
			<html:hidden property="storeRecoupRate" />
			<html:hidden property="settleWay" />
			<html:hidden property="delayFeeWay" />
			<html:hidden property="minSettleMoveQty" />
			<html:hidden property="minSettleQty" />
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
					                    <td ><img id="baseinfo_img4" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo4_onclick()"/></td>
					                  </tr>
					                </table>
					              </legend>
					<span id="baseinfo4">
					<table cellSpacing="0" cellPadding="0" width="700" border="0" align="center" class="common">
								
         
        <tr>
        		  <td></td><td></td><td></td>
                  <td>
                    <c:forEach var="commodity" items="${listSection}">
                      <html-el:multibox property="tradeTimes" styleId="${commodity.label}" styleClass="NormalInput">
                        <c:out value="${commodity.value}"/>
                      </html-el:multibox>
                      <label for="<c:out value="${commodity.label}"/>" class="hand">
                      <c:out value="${commodity.label}"/>
                      </label>
                    </c:forEach>
                  </td>
                </tr>	
                
                 
</table >
</span>
            </fieldset>
          </td>
        </tr>
        
        	
        		<c:if test="${param['oper'] == 'cur'}"> 
								<tr>
									<td colspan="4" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
										<html:reset property="reset" styleClass="button">
											重置
										</html:reset>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											返回
										</html:button>
									</td>
								</tr>
								</c:if>
        	
						<html:hidden property="crud" />
						<html:hidden property="oper" />
						<html:hidden property="forceCloseFeeAlgr"/>
						<html:hidden property="addedTaxFactor"/>
					</html:form>
			
			
			
		</table>
		     
			

		<html:javascript formName="commodityForm" cdata="false"
			dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
