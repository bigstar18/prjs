var trClone = null;
function addEntry(){
	var tabBody = document.all.tableList.children[1];
	var tr = trClone.cloneNode(true);
	tr.children[0].children[0].name="voucherEntries[1].entrySummary";
	tr.children[1].children[0].name="voucherEntries[1].accountCode";
	tr.children[3].children[0].name="voucherEntries[1].debitAmount";
	tr.children[4].children[0].name="voucherEntries[1].creditAmount";
	tabBody.appendChild(tr);
}

function computeSum(){
	var da = document.all("debitAmount");
	var spanDebitSum = document.getElementById("debitSum");
	var ca = document.all("creditAmount");
	var spanCreditSum = document.getElementById("creditSum");
	// 借贷金额合计
	var dsum = 0;
	var csum = 0;
	// 借贷各方向条目计数，不允许多借多贷、无借、无贷。
	var dCount = 0;
	var cCount = 0;
	if(da){
		if(!da.length){
			dsum = parseFloat(da.value);
		} else {
			for(var i=0; i < da.length; i++){
				var dVal = parseFloat( da[i].value );
				if(dVal != 0){
					dsum = dsum + dVal;
					dCount = dCount + 1;
				}
			}
		}
		spanDebitSum.innerHTML = dsum.toFixed(2);
	}
	if(ca){
		if(!ca.length){
			csum = parseFloat(ca.value);
		} else {
			for(var i=0; i < ca.length; i++){
				var cVal = parseFloat( ca[i].value );
				if(cVal != 0){
					csum = csum + cVal;
					cCount = cCount + 1;
				}
			}
		}
		spanCreditSum.innerHTML = csum.toFixed(2);
	}
	
	if(dsum.toFixed(2) != csum.toFixed(2))
		return 1;
	if(cCount==0||dCount==0)
		return 2;
	if(dCount>1&&cCount>1)
		return 3;
	if(da&&ca&&da.length&&ca.length&&(da.length==ca.length)){
		for(var i=0; i < ca.length; i++){
				var cVal = parseFloat( ca[i].value );
				var dVal = parseFloat( da[i].value );
				if(cVal != 0 && dVal !=0){
					return 5;
				}
				if(cVal == 0 && dVal ==0){
					return 6;
				}
			}
	} else
		return 4;
		
	return 0;
}

function isContractNoRequired(){
	var codes = document.all.namedItem("accountCode");
	if(codes){
		if(!codes.length){
			if(codes.value=="2002"||codes.value=="2002150")
				return true;
		} else {
			for(var i=0; i < codes.length; i++){
				if(codes[i].value=="2002"||codes[i].value=="2002150")
					return true;
			}
		}
	} 
}

function voucherSubmit() {
	if(isContractNoRequired()){
		if(frm.contractNo.value==null||frm.contractNo.value.length==0){
			alert("凭证涉及货款或保证金，请输入成交合同号！");
			frm.contractNo.focus();
			return false;
		}
	}
	var stat = computeSum();
	if(stat == 1){
		alert('借贷发生金额不相等，请确认数据正确！');
		return false;
	}
	if(stat == 2){
		alert('无借方或无贷方发生金额，请确认数据正确！');
		return false;
	}
	if(stat == 3){
		alert('不允许多借多贷，请确认数据正确！');
		return false;
	}
	if(stat == 4){
		alert('借方与贷方金额个数不一致，请确认数据正确！');
		return false;
	}
	if(stat == 5){
		alert('不允许同一分录借贷金额都大于0，请确认数据正确！');
		return false;
	}
	if(stat == 6){
		alert('不允许同一分录借贷金额都等于0，请确认数据正确！');
		return false;
	}
	
	if(stat == 0){
		
	   return true;
		
	}else {
		alert("未知状态，请与管理员联系！");
		return false;
	}
}

// 仅输入数字和字母
function onlyNumberAndCharInput() {
	if ((event.keyCode>=48 && event.keyCode<=57) || (event.keyCode>=65 && event.keyCode<=90) || (event.keyCode>=97 && event.keyCode<=122)) {
		event.returnValue=true;
	} else {
		event.returnValue=false;
	}
}

//仅输入数字
function onlyNumberInput() {
	if (event.keyCode>=48 && event.keyCode<=57) {
		event.returnValue=true;
	} else {
		event.returnValue=false;
	}
}

//仅输入数字和.
function onlyDoubleInput()
{
  if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47)
  {
    event.returnValue=false;
  }
} 
