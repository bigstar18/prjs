<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>

<!-- ��ȡ������Ϣ -->
<c:if test="${not empty ReturnValue.info }">
	<script>
        alert('${ReturnValue.info}');
    </script>
</c:if>