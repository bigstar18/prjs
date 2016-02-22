<%@ page pageEncoding="GBK"%>

<script>
	var varOnLoad = document.body.onload;
	document.body.onload = new Function("proxyLoad()");
	
	function proxyLoad()
	{
		if (varOnLoad)
			varOnLoad();
		window.setTimeout("initForm('${oldObjectGetKey}');", 50);
	}
	
</script>
<c:if test="${not empty resultMsg }">
	<script>
       closeDialog('${resultValue}');
    </script>
</c:if>