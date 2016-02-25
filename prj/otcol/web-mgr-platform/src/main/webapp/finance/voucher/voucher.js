//用于增加分录的tr，增加时克隆一个。
		var trClone = null;
		
		function voucherSubmit()
		{
			if(isContractNoRequired()){
				if(formNew.contractNo.value==null||formNew.contractNo.value.length==0){
					alert("凭证涉及货款或保证金，请输入成交合同号！");
					formNew.contractNo.focus();
					return false;
				}
			}
			if(!checkValue("formNew"))
				return false;
			if(isParticularBank()){
				alert('监管账户资金只允许同一交易商的科目间划转！');
				return false;
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
				formNew.submit();
				return true;
			}else {
				alert("未知状态，请与管理员联系！");
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
		            alert("交易商不存在或者有下级的科目代码！");
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
		            	alert("不存在的摘要代码！");
		            } else {
		            	var arr = vSummary.split( "|||" );
		            	vSummary = arr[0];
		            	voucherT = arr[1];
		            }
		        } else {
		            alert("不存在的摘要代码！");
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
			//借贷金额合计
			var dsum = 0;
			var csum = 0;
			//借贷各方向条目计数，不允许多借多贷、无借、无贷。
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
		
		