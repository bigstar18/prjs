<%@ page contentType="text/html;charset=GBK" %>
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

        function getCommodityHold(CommodityID,bs_flag,firmID) {
			h=''+Date();
            createXMLHttpRequest();
			var url="getCommodityHold.jsp";
            var newurl=url+"?CommodityID="+CommodityID+"&bs_flag="+bs_flag+"&firmID="+firmID+"&time="+h;
            request.open("GET", newurl, true);
            request.onreadystatechange = callback;
            request.send(null);
        }

        function callback() {
            if (request.readyState == 4) {
                if (request.status == 200) {
	                var response = request.responseText.split("_");
					//frm.bankTime.value=response[1];
					document.getElementById("holdqty").innerHTML="��ǰ�۸�"+response[0];
					document.getElementById("holdqty1").innerHTML=response[1];
                }
            }
        }
		//���ǰ��ո�
	function trim ( str ) {
		regExp = /\S/
		
		if ( regExp.test(str) == false )
			return "";
		
		regExp = /(^\s*)(.*\S)(\s*$)/
		regExp.exec(str);
		
		return RegExp.$2;
	}
</script>