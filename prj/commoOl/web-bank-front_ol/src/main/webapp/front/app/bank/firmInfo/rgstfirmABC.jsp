<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>农行签解约</title>
<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${frontPath }/app/bank/js/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>
</head>	
<body>
<object classid=clsid:62B938C4-4190-4F37-8CF0-A92B0A91CC77 
		codebase="NetSign.cab" data=data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA== 
		id=InfoSecNetSign1 style="HEIGHT: 0px; WIDTH: 0px" VIEWASTEXT width="0" height="0">
    <embed width="0" height="0" src="data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA==">
    </embed> 
</object>
	<form name="subfrm" id="subfrm" action="${basePath}/bank/firmInfo/rgstDelFirmInfo.action" method="post" target="frame">
	<input type="hidden" name="signstring" id="signstring" value="${signstring}"/>
	<input type="hidden" name="TempString" id="TempString" value="${TempString}"/>
	<input type="hidden" name ="CustSignInfo" id="CustSignInfo" value=""/>
	
	<input type="hidden" name="rgstdelType" id="rgstdelType"value="${rgstdelType}"/>
	<input type="hidden" name="password" id="password"value="${password}"/>
	<input type="hidden" name="RequestID" id="RequestID"value="${RequestID}"/>
	<input type="hidden" name="bankID" id="bankID"value="${bankID}"/>
	
	</form>

</body>
</html>
<SCRIPT LANGUAGE="JavaScript">

		var sure = confirm("请确认您要提交的签名信息:\n\n" + subfrm.TempString.value);
		if(sure == false) {					
			
		}else{
			if(typeof(InfoSecNetSign1)  ==  "undefined"){  
				alert("请插入U盾并安装K宝驱动");  
			}else {
				try{
					var signstring=subfrm.signstring.value;
					var TempString=subfrm.TempString.value;
					InfoSecNetSign1.addFormItem(signstring);
					InfoSecNetSign1.addFormItem(TempString);
					
					InfoSecNetSign1.makeAttachedSign();
					subfrm.CustSignInfo.value = InfoSecNetSign1.attachedSign;
				}catch(err){
					alert("请插入U盾并安装K宝驱动");
				}
			}
		}
		var flag = '${flag}';
		if(subfrm.CustSignInfo.value.length == 0){
			subfrm.action="${basePath}/bank/firmInfo/gotoRgstDelFirmInfoPage.action";
			$("#subfrm").submit();
		}else{
			$("#subfrm").submit();
		}

</SCRIPT>