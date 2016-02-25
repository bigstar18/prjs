<%@ tag body-content="scriptless" %>
<%@ attribute name="var" required="true" rtexprvalue="false" %>
<%@ variable name-from-attribute="var"
    alias="v_row" scope="NESTED" %>
<%@ attribute name="pageIndex"   required="true"  %>
<%@ attribute name="pageSize"   required="true"  %>
<%@ attribute name="where"   required="false" %>
<%@ attribute name="groupBy" required="false" %>
<%@ attribute name="having"  required="false" %>
<%@ attribute name="orderBy" required="false" %>
<%@ include file="../init.tagf" %>

<sql_db:query dataSource="${tags_db_dataSource}" var="v_result">
    select id,name,reservedchar1,reservedchar2,reservedchar3,reservedchar4,rown from (select id,name,reservedchar1,reservedchar2,reservedchar3,reservedchar4,rownum rown from (select * from v_commoditytype 
    <c_db:if test="${!empty where}">   WHERE    ${where}   </c_db:if>
    <c_db:if test="${!empty groupBy}"> GROUP BY ${groupBy} </c_db:if>
    <c_db:if test="${!empty having}">  HAVING   ${having}  </c_db:if>
    <c_db:if test="${!empty orderBy}"> ORDER BY ${orderBy} </c_db:if>
    )) where rown between ${(pageIndex-1)*pageSize+1} and ${pageIndex*pageSize}
    
    
</sql_db:query>

<c_db:forEach var="v_row" items="${v_result.rows}">
    <jsp:doBody/>
</c_db:forEach>
