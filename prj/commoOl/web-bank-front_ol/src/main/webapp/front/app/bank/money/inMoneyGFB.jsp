<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<%@include file="/front/public/jsp/commonmsg.jsp"%>
<base target="_blank">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<title>¹ú¸¶±¦Èë½ð</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${frontPath }/app/bank/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>
</head>
<object classid=clsid:62B938C4-4190-4F37-8CF0-A92B0A91CC77 
		codebase="NetSign.cab" data=data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA== 
		id=InfoSecNetSign1 style="HEIGHT: 0px; WIDTH: 0px" VIEWASTEXT width="0" height="0">
    <embed width="0" height="0" src="data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA==" type="application/x-oleobject">
    </embed> 
</object>
<body oncontextmenu="return false">
<form name="subfrm" id="subfrm" action="https://mertest.gopay.com.cn/PGServer/Trans/BlockTrade.do" method="post" >
	<input type="hidden" id="tranCode" name="tranCode" value="8801" />
	<input type="hidden" id="version" name="version" value="2.0" />
	<input type="hidden" id="merchantID" name="merchantID" value="${MarkCode}" />
	<input type="hidden" id="contractNo" name="contractNo" value="${account}" />
	<input type="hidden" id="merOrderNum" name="merOrderNum" value="${merOrderNum}" />
	<input type="hidden" id="backgroundMerUrl" name="backgroundMerUrl" value="${backgroundMerUrl}" />
	<input type="hidden" id="frontMerUrl" name="frontMerUrl" value="${frontMerUrl}" />
	<input type="hidden" id="virCardNoIn" name="virCardNoIn" value="${MarketGSAcount}" />
	<input type="hidden" id="tranAmt" name="tranAmt" value="${money}" />
	<input type="hidden" id="currencyType" name="currencyType" value="156" />
	<input type="hidden" id="tranDateTime" name="tranDateTime" value="${GFBTime}" />
	<input type="hidden" id="tranIP" name="tranIP" value="${tranIP}" />
	<input type="hidden" id="isRepeatSubmit" name="isRepeatSubmit" value="1" />
	<input type="hidden" id="msgExt" name="msgExt" value="${msgExt}" />
	<input type="hidden" id="merRemark1" name="merRemark1" value="${firmID}" />
	<input type="hidden" id="merRemark2" name="merRemark2" value="${account}" />
	<input type="hidden" id="signType" name="signType" value="1" />
	<input type="hidden" id="signValue"name="signValue"value="${signValue}" />
	<input type="hidden" id="gopayServerTime"name="gopayServerTime"value="${GFBTime}" />
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
$("#subfrm").submit();
window.location="${basePath}/bank/money/gotoMoneyPage.action";
</SCRIPT>