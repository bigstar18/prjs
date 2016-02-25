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
    select firmid,name,rown, overdraft, feecut,feecutfee,category1,category2,tradecount,totalsecurity from (select firmid,name,rownum rown, overdraft, feecut,feecutfee,category1,category2,tradecount,totalsecurity from (select u3.firmid,u3.name, u2.overdraft, u2.feecut,u2.feecutfee,u2.category1,u2.category2,u2.tradecount,u2.totalsecurity from v_tradeuser u2,M_firm u3,f_firmfunds u1
    <c_db:if test="${!empty where}">   WHERE    ${where}   </c_db:if>
    <c_db:if test="${!empty groupBy}"> GROUP BY ${groupBy} </c_db:if>
    <c_db:if test="${!empty having}">  HAVING   ${having}  </c_db:if>
    <c_db:if test="${!empty orderBy}"> ORDER BY ${orderBy} </c_db:if>
    )) where rown between ${(pageIndex-1)*pageSize+1} and ${pageIndex*pageSize}
    
    
</sql_db:query>

<c_db:forEach var="v_row" items="${v_result.rows}">
    <jsp:doBody/>
</c_db:forEach>
