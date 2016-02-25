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
      
    //  if(commodityForm.type12[1].checked ==true)
    //  {
	//    if(trim(commodityForm.settleMarginRate_S.value) == "")
    // {
	//      alert("卖交收货款不能为空！");
	//      commodityForm.settleMarginRate_S.focus();
	//      return false;
    //  }  
       
    //  }
    if (commodityForm.marketDate.value == "") {
    	alert("上市日期不能为空！");
      	commodityForm.marketDate.focus();
      	return false;
    }
    if (commodityForm.marketDate.value != "") {
    	if(!isDateFormat(commodityForm.marketDate.value)){
		    alert("上市日期格式不正确！");
		    commodityForm.marketDate.focus();
		    return false;
	  	}
    }
    
    if (commodityForm.settleDate.value != "") {
    	if (!isDateFormat(commodityForm.settleDate.value)) {
    		alert("最后交易日格式不正确！");
		    commodityForm.settleDate.focus();
		    return false;
    	}
    }
 
    if(parseInt(trim(commodityForm.contractFactor.value),10) <= 0)
    {
	      alert("最小变动价位应大于0！");
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
    if (commodityForm.minPriceMove.value == "") {
    	alert("最小价位不能为空！");
    	commodityForm.minPriceMove.focus();
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
    if (commodityForm.status.value == "") {
    	alert("状态不能为空！");
    	commodityForm.status.focus();
    	return false;
    }
    if (commodityForm.lastPrice.value == "") {
    	alert("开盘指导价不能为空！");
    	commodityForm.lastPrice.focus();
    	return false;
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
   
    if (commodityForm.firmMaxHoldQty.value == "") {
 		alert("交易商最大双边订货量不能为空！");
 		commodityForm.firmMaxHoldQty.focus();
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
 		alert("交收价计算方式不能为空！");
 		commodityForm.settlePriceType.focus();
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
		if (commodityForm.firmMaxHoldQty.value < 0) {
			alert("交易商最大双边订货量不能为负！");
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
	
	if (commodityForm.settleDate2.value != "") {
		if (!isDateFormat(commodityForm.settleDate2.value)) {
			alert("第二阶段保证金生效日期格式不正确！");
			commodityForm.settleDate2.focus();
			return false;
		}
	}
	if (commodityForm.settleDate3.value != "") {
		if (!isDateFormat(commodityForm.settleDate3.value)) {
			alert("第三阶段保证金生效日期格式不正确！");
			commodityForm.settleDate3.focus();
			return false;
		}
	}
	if (commodityForm.settleDate4.value != "") {
		if (!isDateFormat(commodityForm.settleDate4.value)) {
			alert("第四阶段保证金生效日期格式不正确！");
			commodityForm.settleDate4.focus();
			return false;
		}
	}
	if (commodityForm.settleDate5.value != "") {
		if (!isDateFormat(commodityForm.settleDate5.value)) {
			alert("第五阶段保证金生效日期格式不正确！");
			commodityForm.settleDate5.focus();
			return false;
		}
	}

    commodityForm.addedTaxFactor.value = (parseFloat(commodityForm.addedTax.value)/(parseFloat(commodityForm.addedTax.value)+100));
    setEnabled(commodityForm.marginPriceType);
   
    commodityForm.submit();
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
 
 function changeManner103(id){
 	if (id == "1") {
 		setReadWrite(commodityForm.firmMaxHoldQty);
 	}
 	if (id == "2") {
 		commodityForm.firmMaxHoldQty.value = "-1";
 		setReadOnly(commodityForm.firmMaxHoldQty);
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
	}
}