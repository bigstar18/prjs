<%@ page pageEncoding="GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<script type="text/javascript" src="${publicPath}/js/submitform.js"></script>
<!-- ��ȡ������Ϣ �����ȷ����ťʱ ��Ϣ��ҳ�涼�ر�  -->
<script type="text/javascript">
<!--
/*�˷�����formInit.js��*/


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
