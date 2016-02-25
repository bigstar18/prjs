<%@ tag body-content="empty" dynamic-attributes="columnAttr" %>
<%@ include file="../init.tagf" %>
<%//交易商代码%>
<%@ attribute name="usercodes" required="true" %>
<sql_db:transaction dataSource="${tags_db_dataSource}">
    <sql_db:update>
        update v_tradeuserext set userflag=0 where usercode in (${usercodes})
    </sql_db:update>
    <sql_db:update>
        update v_tradeuser set status=3 where usercode in (${usercodes})
    </sql_db:update>
</sql_db:transaction>
