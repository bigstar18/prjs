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
    select id,code,firsttime,createtime,status,splitid,category,beginprice,stepprice,amount,tradeunit,alertprice,security,fee,minamount,str1,str6,str7,rown from (select id,code,firsttime,createtime,status,splitid,category,beginprice,stepprice,amount,tradeunit,alertprice,security,fee,minamount,str1,str6,str7,rownum rown from (select u1.id,u1.code,u1.firsttime,u1.createtime,u1.status,u1.splitid,u1.category,u1.beginprice,u1.stepprice,u1.amount,u1.tradeunit,u1.alertprice,u1.security,u1.fee,u1.minamount,u2.str1,u2.str6,u2.str7 from v_commodity u1,v_commext u2,v_curCommodity u3
    <c_db:if test="${!empty where}">   WHERE    ${where}   </c_db:if>
    <c_db:if test="${!empty groupBy}"> GROUP BY ${groupBy} </c_db:if>
    <c_db:if test="${!empty having}">  HAVING   ${having}  </c_db:if>
    <c_db:if test="${!empty orderBy}"> ORDER BY ${orderBy} </c_db:if>
    )) where rown between ${(pageIndex-1)*pageSize+1} and ${pageIndex*pageSize}
    
    
</sql_db:query>

<c_db:forEach var="v_row" items="${v_result.rows}">
    <jsp:doBody/>
</c_db:forEach>
