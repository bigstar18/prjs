
<script>
//ajax
	var request=false;

        function createXMLHttpRequest() {
            try {
				request = new XMLHttpRequest();
			} catch (trymicrosoft) {
				try {
					request = new ActiveXObject("Msxml2.XMLHTTP");
				} catch (othermicrosoft) {
					try {
						request = new ActiveXObject("Microsoft.XMLHTTP");
					} catch (failed) {
						request = false;
					}
				}
			}

        }

        function validate(v,s) {
			h=''+Date();
            createXMLHttpRequest();
			var url="ChangeBank.jsp";
            var newurl=url+"?bankID="+v+"&firmID="+s+"&time="+h;
            request.open("GET", newurl, true);
            request.onreadystatechange = callback;
            request.send(null);
        }

        function callback() {
            if (request.readyState == 4) {
                if (request.status == 200) {
	                var response = request.responseText.split("_");
					//frm.bankTime.value=response[1];
					document.getElementById("bankTime").innerHTML="可操作时间段："+response[1];
                }
            }
        }
		//清除前后空格
	function trim ( str ) {
		regExp = /\S/
		
		if ( regExp.test(str) == false )
			return "";
		
		regExp = /(^\s*)(.*\S)(\s*$)/
		regExp.exec(str);
		
		return RegExp.$2;
	}
</script>