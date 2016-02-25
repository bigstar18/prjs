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
    select id,name,rown,tradepartition, submitid, code, contractid,price,amount,tradedate,section,tradeunit from (select id,name,rownum rown,tradepartition, submitid, code, contractid,price,amount,tradedate,section,tradeunit from (select u1.firmid id,u1.name,u2.userid,u2.tradepartition, u2.submitid, u2.code,u2.contractid,u2.price,u2.amount,u2.tradedate,u2.section,u3.tradeunit from M_firm u1,v_bargain u2,v_commodity u3,v_commext u4
    <c_db:if test="${!empty where}">   WHERE    ${where}   </c_db:if>
    <c_db:if test="${!empty groupBy}"> GROUP BY ${groupBy} </c_db:if>
    <c_db:if test="${!empty having}">  HAVING   ${having}  </c_db:if>
    <c_db:if test="${!empty orderBy}"> ORDER BY ${orderBy} </c_db:if>
    )) where rown between ${(pageIndex-1)*pageSize+1} and ${pageIndex*pageSize}
    
    
</sql_db:query>

<c_db:forEach var="v_row" items="${v_result.rows}">
    <jsp:doBody/>
</c_db:forEach>
