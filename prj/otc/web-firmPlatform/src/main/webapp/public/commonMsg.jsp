<%@ page pageEncoding="GBK"%>
<%@ include file="/public/session.jsp"%>

<!-- ��ȡ������Ϣ -->
<c:if test="${not empty resultMsg }">
	<script>
       closeDialog('${resultValue}');
    </script>
</c:if>