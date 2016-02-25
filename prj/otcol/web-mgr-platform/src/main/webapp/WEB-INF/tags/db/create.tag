<%@ tag body-content="scriptless" %>
<%@ attribute name="table" required="true" %>
<%@ include file="../init.tagf" %>

<sql_db:update dataSource="${tags_db_dataSource}">
CREATE TABLE ${table} ( <jsp:doBody/> )
</sql_db:update>
