<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="gnnt.MEBS.integrated.mgr.model.usermanage.MFirmApply,
					javax.servlet.ServletOutputStream,java.io.* " %>

<html>
	<head>
		<title>bigPicture</title>
	</head>
	<body>
	<br/>
	<br/>
	<div align="center">
		<img width="550" height="450" src="${pageContext.request.contextPath}/trade/mfirm/forwordGetPictures.action?applyID=${applyID}&picture=${picture}">
	</div>
	</body>
	<script  language="javascript">
	</script>
</html>
