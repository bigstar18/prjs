<%@ page pageEncoding="GBK"%>
<%@ include file="/public/session.jsp"%>

<!-- 获取返回信息 -->
<c:if test="${not empty resultMsg }">
	<script>
       closeDialog('${resultValue}');
    </script>
</c:if>