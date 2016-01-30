<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>

<!-- 获取返回信息 -->
<c:if test="${not empty ReturnValue.info }">
	<script>
        alert('${ReturnValue.info}');
    </script>
</c:if>