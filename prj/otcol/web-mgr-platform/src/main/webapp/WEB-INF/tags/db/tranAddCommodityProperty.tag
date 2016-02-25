<%@ tag body-content="empty" dynamic-attributes="columnAttr" %>
<%@ include file="../init.tagf" %>
<%//商品表信息%>
<%@ attribute name="id" required="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="type" required="true" %>
<%@ attribute name="isnull" required="true" %>
<%@ attribute name="cpid" required="true" %>
<%@ attribute name="charvartext" required="true" %>


<c_db:set var="columns1" value="id,cpid,name,type,isnull,charvartext,reservedchar1,reservedchar2,reservedchar3,reservedchar4,reservedchar5"/>
<c_db:set var="values1" value="${id},${cpid},'${name}',${type},${isnull},'${charvartext}','${reservedchar1}','${reservedchar2}','${reservedchar3}','${reservedchar4}','${reservedchar5}'"/>

<sql_db:transaction dataSource="${tags_db_dataSource}">

  <sql_db:update>
    <c_db:if test="${not empty reservedchar1}"><c_db:set var="columns1" value="${columns21},reservedchar1"/>
    <c_db:set var="values1" value="${values1},'${reservedchar1}'"/>
    </c_db:if>
    <c_db:if test="${not empty reservedchar}"><c_db:set var="columns1" value="${columns1},reservedchar2"/>
    <c_db:set var="values1" value="${values1},'${reservedchar2}'"/>
    </c_db:if>
    <c_db:if test="${not empty reservedchar3}"><c_db:set var="columns1" value="${columns1},reservedchar3"/>
    <c_db:set var="values1" value="${values1},'${reservedchar3}'"/>
    </c_db:if>
    <c_db:if test="${not empty reservedchar4}"><c_db:set var="columns1" value="${columns1},reservedchar4"/>
    <c_db:set var="values1" value="${values1},'${reservedchar4}'"/>
    </c_db:if>
    <c_db:if test="${not empty reservedchar5}"><c_db:set var="columns1" value="${columns1},reservedchar5"/>
    <c_db:set var="values1" value="${values1},'${reservedchar5}'"/>
    </c_db:if>
   
    insert into v_commodityproperty(${columns1}) values(${values1})
  </sql_db:update>
</sql_db:transaction>
