//�������ӷ�¼��tr������ʱ��¡һ����
		var trClone = null;
		
		function voucherSubmit()
		{
			if(isContractNoRequired()){
				if(formNew.contractNo.value==null||formNew.contractNo.value.length==0){
					alert("ƾ֤�漰�����֤��������ɽ���ͬ�ţ�");
					formNew.contractNo.focus();
					return false;
				}
			}
			if(!checkValue("formNew"))
				return false;
			if(isParticularBank()){
				alert('����˻��ʽ�ֻ����ͬһ�����̵Ŀ�Ŀ�仮ת��');
				return false;
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
				formNew.submit();
				return true;
			}else {
				alert("δ֪״̬���������Ա��ϵ��");
				return false;
			}
		}
		
		function addEntry(){
			var tabBody = document.all.tableList.children[1];
			var tr = trClone.cloneNode(true);
			tabBody.appendChild(tr);
		}
		
		function delEntry()
		{
		  var tabBody = document.all.tableList.children[1];
		  
		  for(i=tabBody.children.length-1; i>=0; i--){
			if(tabBody.children[i].children[1].children[0].checked){
				tabBody.children[i].removeNode(true);
			}			
		  }
		  computeSum();
	    }
	    function setAccountName(vCode,vNewCode,vName)
		{
		  var tabBody = document.all.tableList.children[1];
		  
		  for(i=tabBody.children.length-1; i>=0; i--){
			if(tabBody.children[i].children[3].children[0].value==vCode){
				tabBody.children[i].children[3].children[0].value=vNewCode;
				tabBody.children[i].children[4].children[0].value=vName;
			}			
		  }
	    }
	    
	    var req = new ActiveXObject("Microsoft.XMLHTTP");
	    var vAccountName = '';
	    var vSummary = '';
	    var voucherT = '';
	    function changeAccountCode( vCode ){
	    	if(vCode != null && vCode.length > 0){
		    	vAccountName = '';
		    	vNewCode = codeFillZero( vCode );
		    	url = basePath + '/accountController.spr?funcflg=getAccountName&code='+vNewCode;
		    	if (req) {
		            req.onreadystatechange = processReqChange;
		            req.open("GET", url, false);
		            req.send();
		            req.abort();
	        	}
	        	setAccountName(vCode,vNewCode,vAccountName);
        	}
	    }
	    
	    
	     
	    
	    
	    
	    
	    function codeFillZero( vCode) {
	    	return trim(vCode);
	    }
	    function changeSummaryNo( vCode ){
	    	if(vCode != null && vCode.length > 0){
		    	vSummary = '';
		    	voucherT = '';
		    	url = basePath + '/voucherController.spr?funcflg=getSummary&summaryNo='+vCode;
		    	if (req) {
		            req.onreadystatechange = processSummaryReqChange;
		            req.open("GET", url, false);
		            req.send();
		            req.abort();
	        	}
	        	document.getElementById("summary").value=vSummary;
	        	document.getElementById("voucherType").value=voucherT;
        	}
	    }
	    
	    function processReqChange() {
		    // only if req shows "loaded"
		    if (req.readyState == 4) {
		        // only if "OK"
		        if (req.status == 200) {
		            vAccountName = trim(req.responseText);
		        } else {
		            alert("�����̲����ڻ������¼��Ŀ�Ŀ���룡");
		        }
		    }
		}
		
		function processSummaryReqChange() {
		    // only if req shows "loaded"
		    if (req.readyState == 4) {
		        // only if "OK"
		        if (req.status == 200) {
		            vSummary = trim(req.responseText);
		            if(vSummary.length == 0) {
		            	alert("�����ڵ�ժҪ���룡");
		            } else {
		            	var arr = vSummary.split( "|||" );
		            	vSummary = arr[0];
		            	voucherT = arr[1];
		            }
		        } else {
		            alert("�����ڵ�ժҪ���룡");
		        }
		    }
		}
		
		function isContractNoRequired(){
			var codes = document.all.namedItem("accountCode");
			if(codes){
				if(!codes.length){
					if(codes.value=="202"||codes.value=="205")
						return true;
				} else {
					for(var i=0; i < codes.length; i++){
						if(codes[i].value=="202"||codes[i].value=="205")
							return true;
					}
				}
			} 
		}
		
		function computeSum(){
			var da = document.all.namedItem("debitAmount");
			var spanDebitSum = document.getElementById("debitSum");
			var ca = document.all.namedItem("creditAmount");
			var spanCreditSum = document.getElementById("creditSum");
			//������ϼ�
			var dsum = 0;
			var csum = 0;
			//�����������Ŀ�������������������޽衢�޴���
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
		
		function isParticularBank(){
			var codes = document.all.namedItem("accountCode");
			if(codes){
				if(!codes.length){
					return false;
				} else {
					for(var i=0; i < codes.length; i++){
						var temp = trim(codes[i].value);
						if(temp.substring(0,1)=='9'){
							var sec_code = temp.substring(0,temp.length-2);
							for(var j=0; j < codes.length; j++){
								var temp2 = trim(codes[j].value);
								if(!(temp2.substring(0,temp2.length-2)==sec_code))
									return true;
							}
							break;
						}	
					}
					return false;
				}
			} 
		}
		
		