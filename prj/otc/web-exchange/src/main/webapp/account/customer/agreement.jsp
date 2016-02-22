<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE unspecified PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head></head>
	<body width="550px">
		<a name="firstAnchor"></a>
		<div style="margin-left:15px;margin-right:15px;margin-top:30px;margin-bottom:20px;border:2px dashed #F4A460;">
		<p>${content }</p>
		</div>
	</body>
	<script type="text/javascript">
		window.onload=function(){
			window.location.hash = firstAnchor;
		}
	</script>
</html>
