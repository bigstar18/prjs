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
	// ������ϼ�
	var dsum = 0;
	var csum = 0;
	// �����������Ŀ�������������������޽衢�޴���
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
			alert("ƾ֤�漰�����֤��������ɽ���ͬ�ţ�");
			frm.contractNo.focus();
			return false;
		}
	}
	var stat = computeSum();
	if(stat == 1){
		alert('�����������ȣ���ȷ��������ȷ��');
		return false;
	}
	if(stat == 2){
		alert('�޽跽���޴�����������ȷ��������ȷ��');
		return false;
	}
	if(stat == 3){
		alert('��������������ȷ��������ȷ��');
		return false;
	}
	if(stat == 4){
		alert('�跽�������������һ�£���ȷ��������ȷ��');
		return false;
	}
	if(stat == 5){
		alert('������ͬһ��¼���������0����ȷ��������ȷ��');
		return false;
	}
	if(stat == 6){
		alert('������ͬһ��¼���������0����ȷ��������ȷ��');
		return false;
	}
	
	if(stat == 0){
		
	   return true;
		
	}else {
		alert("δ֪״̬���������Ա��ϵ��");
		return false;
	}
}

// ���������ֺ���ĸ
function onlyNumberAndCharInput() {
	if ((event.keyCode>=48 && event.keyCode<=57) || (event.keyCode>=65 && event.keyCode<=90) || (event.keyCode>=97 && event.keyCode<=122)) {
		event.returnValue=true;
	} else {
		event.returnValue=false;
	}
}

//����������
function onlyNumberInput() {
	if (event.keyCode>=48 && event.keyCode<=57) {
		event.returnValue=true;
	} else {
		event.returnValue=false;
	}
}

//���������ֺ�.
function onlyDoubleInput()
{
  if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47)
  {
    event.returnValue=false;
  }
} 
