<%@ page contentType="text/html;charset=GBK"%>
<html><head>
<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
<script src="${publicPath }/js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${publicPath}/js/PasswordMode.js"></script>
</head>
<body>
<a name="firstAnchor"></a> 
<div id="print" style="margin-left:10px;margin-right:10px;margin-top:30px;margin-bottom:20px; border:2px dashed #F4A460;">
<div align="center">${netCustomerInZS.accountagreement }</div>
</div>
<div id="noprint" align="right" style="margin-right:10px;margin-bottom:5px;"><input type="button" value="´òÓ¡" onclick="myPrint()"/></div>
<br/>
</body>
<SCRIPT type="text/javascript">
	window.onload=function(){
		window.location.hash = firstAnchor;
	}
	function myPrint(){
		document.getElementById("noprint").style.display="none";
		window.print();
		document.getElementById("noprint").style.display="";
	}
</SCRIPT>
</html>