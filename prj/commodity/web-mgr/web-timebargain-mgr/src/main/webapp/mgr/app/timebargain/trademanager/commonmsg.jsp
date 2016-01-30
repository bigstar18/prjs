<%@ page pageEncoding="GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<script type="text/javascript" src="${publicPath}/js/submitform.js"></script>
<!-- 获取返回信息 当点击确定按钮时 信息和页面都关闭  -->
<script type="text/javascript">
<!--
/*此方法在formInit.js下*/


closeDialog1('${ReturnValue.result}');

function closeDialog1(parentReload){
	if(!parentReload)
		parentReload=0;
	if(isNaN(parentReload))
	{
	   	parentReload=parseInt(parentReload);
	}
	if(parentReload>0)
	{
	   window.returnValue = parentReload;
	   window.location='${basePath}/timebargain/tradeManager/tradeStatus.action';
	}else{
		if(parent)
		   parent.clearSubmitCount();
	}
	
}
//-->
</script>
