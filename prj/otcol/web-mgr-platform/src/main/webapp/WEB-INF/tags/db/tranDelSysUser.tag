<%@ tag body-content="empty" dynamic-attributes="columnAttr" %>
<%@ include file="../init.tagf" %>
<%//交易商代码%>
<%@ attribute name="userids" required="true" %>
<sql_db:transaction dataSource="${tags_db_dataSource}">
    <sql_db:update>
        delete from v_sysuser where sysuser in (${userids})
    </sql_db:update>
    <sql_db:update>
        delete from v_sysuserandpartition where sysuser in (${userids})
    </sql_db:update>
</sql_db:transaction>
