<%@ tag body-content="empty" dynamic-attributes="columnAttr" %>
<%@ include file="../init.tagf" %>
<%//商品表信息%>
<%@ attribute name="id" required="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="reservedchar1" required="true" %>
<%@ attribute name="reservedchar2" required="true" %>
<%@ attribute name="reservedchar3" required="true" %>
<%@ attribute name="reservedchar4" required="true" %>

<c_db:set var="columns1" value="id,name,reservedchar1,reservedchar2,reservedchar3,reservedchar4"/>
<c_db:set var="values1" value="${id},'${name}','${reservedchar1}','${reservedchar2}','${reservedchar3}','${reservedchar4}'"/>


<sql_db:transaction dataSource="${tags_db_dataSource}">
  <sql_db:update>
    <c_db:if test="${not empty reservedchar2}"><c_db:set var="columns1" value="${columns1},reservedchar2"/>
    <c_db:set var="values1" value="${values1},'${reservedchar2}'"/>
    </c_db:if>
    <c_db:if test="${not empty reservedchar3}"><c_db:set var="columns1" value="${columns1},reservedchar3"/>
    <c_db:set var="values1" value="${values1},'${reservedchar3}'"/>
    </c_db:if>
    <c_db:if test="${not empty reservedchar4}"><c_db:set var="columns1" value="${columns1},reservedchar4"/>
    <c_db:set var="values1" value="${values1},'${reservedchar4}'"/>
    </c_db:if>
    insert into v_commoditytype(${columns1}) values(${values1})
  </sql_db:update>
</sql_db:transaction>
