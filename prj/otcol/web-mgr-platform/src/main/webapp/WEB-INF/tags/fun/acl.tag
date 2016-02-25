<%@ tag body-content="scriptless" %>
<%@ attribute name="userid"   required="true"  %>
<%@ attribute name="subject" required="true" %>
<%@ include file="../init.tagf" %>

<sql_db:query dataSource="${tags_db_dataSource}" var="v_result">
    SELECT UserLevel FROM MarketUser WHERE MarketUserID='${userid}'
</sql_db:query>

<c_db:forEach var="v_row" items="${v_result.rows}">
     <c_db:if test="${!fn:contains(v_row.UserLevel,subject) && userid!='admin'}">
     </c_db:if>
</c_db:forEach>
