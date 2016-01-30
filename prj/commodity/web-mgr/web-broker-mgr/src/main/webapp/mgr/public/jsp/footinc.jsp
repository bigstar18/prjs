<%@ page pageEncoding="GBK"%>

<script>
	var varOnLoad = document.body.onload;
	document.body.onload = new Function("proxyLoad()");
	
	function proxyLoad()
	{
		if (varOnLoad)
			varOnLoad();
			/* initForm() 方法在public/js/formInit.js下 */
		window.setTimeout("initForm();", 50);
	}
	
</script>
