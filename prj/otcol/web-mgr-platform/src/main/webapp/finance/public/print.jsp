<%@ page contentType="text/html;charset=GBK" %>
<html>

<head>
	<%@ include file="/finance/public/headInc.jsp" %>
	<title>打印</title>
	<script language="javascript">
	var printData = window.dialogArguments;
	function setPrintData( )
	{
 		printSpan.innerHTML = "<span style='font-size:14pt;font-family:宋体'><b>财务系统</b></span><br>"+printData.HTML;
 		var regex=/panel_tHead_MB\w*\>/g;
 		printSpan.innerHTML = printSpan.innerHTML.replace(regex,"panel_tHead_Title>");
 		
 		var tabPager = document.getElementById("PageCtrl_Table");
 		if(tabPager)
	 		tabPager.style.display="none";
 		window.print();
 		window.close();
	}
	</script>
</head>

<body onload="javascript:setPrintData( )" style="margin:0;font-size:9pt">
<span id=printSpan></span>
</body>
</html>
<%@ include file="/finance/public/footInc.jsp" %>
