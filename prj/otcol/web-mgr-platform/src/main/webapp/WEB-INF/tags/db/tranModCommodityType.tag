<%@ tag body-content="empty" dynamic-attributes="columnAttr" %>
<%@ include file="../init.tagf" %>
<%//商品表信息%>

<%@ attribute name="id" required="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="reservedchar1" required="true" %>
<%@ attribute name="reservedchar2" required="true" %>
<%@ attribute name="reservedchar3" required="true" %>
<%@ attribute name="reservedchar4" required="true" %>

<c_db:set var="modParam" value="name='${name}',reservedchar1='${reservedchar1}',reservedchar2='${reservedchar2}',reservedchar3='${reservedchar3}',reservedchar4='${reservedchar4}'"/>
<sql_db:transaction dataSource="${tags_db_dataSource}">

          
    <c_db:choose>
      <c_db:when test="${not empty modParam}">
         <c_db:if test="${not empty reservedchar2}"><c_db:set var="modParam" value="${modParam},reservedchar2='${reservedchar2}'"/></c_db:if>
      </c_db:when>
      <c_db:otherwise>
         <c_db:if test="${not empty reservedchar2}"><c_db:set var="modParam" value="${modParam}reservedchar2='${reservedchar2}'"/></c_db:if>
      </c_db:otherwise>
    </c_db:choose>
    
    <c_db:choose>
      <c_db:when test="${not empty modParam}">
        <c_db:if test="${not empty reservedchar3}"><c_db:set var="modParam" value="${modParam},reservedchar3='${reservedchar3}'"/></c_db:if>
    </c_db:when>
      <c_db:otherwise>
        <c_db:if test="${not empty reservedchar3}"><c_db:set var="modParam" value="${modParam}reservedchar3='${reservedchar3}'"/></c_db:if>
    </c_db:otherwise>
    </c_db:choose>
    
    <c_db:choose>
      <c_db:when test="${not empty modParam}">
        <c_db:if test="${not empty reservedchar4}"><c_db:set var="modParam" value="${modParam},reservedchar4='${reservedchar4}'"/></c_db:if>
      </c_db:when>
      <c_db:otherwise>
        <c_db:if test="${not empty reservedchar4}"><c_db:set var="modParam" value="${modParam}reservedchar4='${reservedchar4}'"/></c_db:if>
      </c_db:otherwise>
    </c_db:choose>
    
      
     
  <c_db:if test="${not empty modParam}">
  <sql_db:update>
     update v_commoditytype set ${modParam} where id=${id}
  </sql_db:update>
  </c_db:if> 
</sql_db:transaction>
