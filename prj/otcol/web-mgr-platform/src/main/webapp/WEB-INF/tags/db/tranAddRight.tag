<%@ tag body-content="empty" dynamic-attributes="columnAttr" %>
<%@ include file="../init.tagf" %>
<%//交易商代码%>
<%@ attribute name="sysuser" required="true" %>
<%@ attribute name="accessnum" required="true" %>
<%@ attribute name="tradepartition" required="true" %>
<sql_db:transaction dataSource="${tags_db_dataSource}">
    <sql_db:update>
        update v_sysuser set accessnum=${accessnum} where sysuser='${sysuser}'
    </sql_db:update>
    <sql_db:update>
        update v_sysuserandpartition set tradepartition='${tradepartition}' where sysuser ='${sysuser}'
    </sql_db:update>
</sql_db:transaction>
