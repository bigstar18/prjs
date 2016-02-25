<%@ tag body-content="empty" dynamic-attributes="columnAttr" %>
<%@ include file="../init.tagf" %>
<%//³ö¿â¼ÇÂ¼id%>
<%@ attribute name="outlogid" required="true" %>
<sql_db:transaction dataSource="${tags_db_dataSource}">
    <sql_db:update>
        delete from v_outrecord where outlogid=${outlogid}
    </sql_db:update>
    <sql_db:update>
        delete from v_outlog where id=${outlogid}
    </sql_db:update>
</sql_db:transaction>
