<%@ tag body-content="scriptless" %>
<%@ attribute name="table" required="true" %>
<%@ include file="../init.tagf" %>

<sql_db:update dataSource="${tags_db_dataSource}">
    DROP TABLE ${table}
</sql_db:update>
