	
	    function setSign(value)
		{
		  alert(value);
		  if(value=='true')
		  {
		    
		  }
		  else if(value=='flase')
		  {
		    alert("不存在交易商Id2！");
		    formNew.firmId.value='';
		  }
	    }
	    
	    var req = new ActiveXObject("Microsoft.XMLHTTP");
	    var sign = '';
	    function checkFirm( vCode ){
	    	if(vCode != null && vCode.length > 0){
		    	sign = '';
		    	vNewCode = codeFillZero( vCode );
		    	url = basePath.replace("finance","common") + '/trader/getFirm.jsp?firmId='+vNewCode;
		    	alert(url);
		    	if (req) {
		            req.onreadystatechange = processReqChange;
		            req.open("GET", url, false);
		            req.send();
		            req.abort();
	        	}
	        	setSign(sign);
        	}
	    }
	    
	    
	     function codeFillZero( vCode) {
	    	return trim(vCode);
	    }
	    
	    
	    
	    function processReqChange() {
		    // only if req shows "loaded"
		    if (req.readyState == 4) {
		        // only if "OK"
		        if (req.status == 200) {
		            sign = trim(req.responseText);
		        } else {
		            alert("不存在交易商Id1！");
		            formNew.firmId.value='';
		        }
		    }
		}
		
		