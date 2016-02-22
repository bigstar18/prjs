<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty resultMsg }">
	<script>
        alert('${resultMsg}');
        if('${resultValue}'>0){
	        window.close();
        }
    </script>
</c:if>
