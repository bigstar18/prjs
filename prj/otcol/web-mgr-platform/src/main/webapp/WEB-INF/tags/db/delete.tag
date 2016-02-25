<%@ tag body-content="empty" %>
<%@ attribute name="table" required="true" %>
<%@ attribute name="where" required="false" %>
<%@ include file="../init.tagf" %>

<sql_db:update dataSource="${tags_db_dataSource}">
DELETE FROM ${table}
<c_db:if test="${!empty where}"> WHERE ${where} </c_db:if>
</sql_db:update>

