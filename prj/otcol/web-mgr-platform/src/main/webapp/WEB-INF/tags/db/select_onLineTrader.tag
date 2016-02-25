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
    select id,name,modifytime,usercode,rownum rown,password, status, overdraft, feecut,balance,frozencaptial,category1,category2,tradecount,totalsecurity from (select u1.id,u1.name,u1.modifytime,u2.usercode,u2.password, u2.status, u2.overdraft, u2.feecut,u2.balance,u2.frozencaptial,u2.category1,u2.category2,u2.tradecount,u2.totalsecurity from v_tradeuserext u1,v_tradeuser u2
    <c_db:if test="${!empty where}">   WHERE    ${where}   </c_db:if>
    <c_db:if test="${!empty groupBy}"> GROUP BY ${groupBy} </c_db:if>
    <c_db:if test="${!empty having}">  HAVING   ${having}  </c_db:if>
    <c_db:if test="${!empty orderBy}"> ORDER BY ${orderBy} </c_db:if>
    )
</sql_db:query>

<c_db:forEach var="v_row" items="${v_result.rows}">
    <jsp:doBody/>
</c_db:forEach>