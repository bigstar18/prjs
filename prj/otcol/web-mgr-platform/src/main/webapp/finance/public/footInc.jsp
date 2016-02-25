	<script>
		var varOnLoad = document.body.onload;
		document.body.onload = new Function("proxyLoad()");
		
		function proxyLoad()
		{
			if (varOnLoad)
				varOnLoad();
			window.setTimeout("initForm();", 50);
		}
	</script>