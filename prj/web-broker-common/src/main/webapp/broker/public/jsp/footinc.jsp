<%@ page pageEncoding="GBK"%>

<script>
	var varOnLoad = document.body.onload;
	document.body.onload = new Function("proxyLoad()");
	
	function proxyLoad()
	{
		if (varOnLoad)
			varOnLoad();
			/* initForm() ������public/js/formInit.js�� */
		window.setTimeout("initForm();", 50);
	}
	
</script>
