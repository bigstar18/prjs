<%@ tag body-content="scriptless" %>
<%@ attribute name="var" required="true" rtexprvalue="false" %>
<%@ variable name-from-attribute="var"
    alias="v_row" scope="NESTED" %>
<%@ attribute name="where"   required="false" %>
<%@ attribute name="groupBy" required="false" %>
<%@ attribute name="having"  required="false" %>
<%@ attribute name="orderBy" required="false" %>
<%@ include file="../init.tagf" %>

<sql_db:query dataSource="${tags_db_dataSource}" var="v_result">
    select count(*) as n from commodity u1,commext u2 
    <c_db:if test="${!empty where}">   WHERE    ${where}   </c_db:if>
    <c_db:if test="${!empty groupBy}"> GROUP BY ${groupBy} </c_db:if>
    <c_db:if test="${!empty having}">  HAVING   ${having}  </c_db:if>
    <c_db:if test="${!empty orderBy}"> ORDER BY ${orderBy} </c_db:if>
    
    
</sql_db:query>

<c_db:forEach var="v_row" items="${v_result.rows}">
    <jsp:doBody/>
</c_db:forEach>
