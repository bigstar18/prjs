<%@ tag body-content="empty" dynamic-attributes="columnAttr" %>
<%@ include file="../init.tagf" %>
<%//��Ա����Ϣ%>
<%@ attribute name="id" required="true" %>
<sql_db:transaction dataSource="${tags_db_dataSource}">
  <sql_db:update>
    delete from v_commodity where id=${id}
  </sql_db:update>
  <sql_db:update>
    delete from v_commext where commid=${id}
  </sql_db:update>
</sql_db:transaction>
