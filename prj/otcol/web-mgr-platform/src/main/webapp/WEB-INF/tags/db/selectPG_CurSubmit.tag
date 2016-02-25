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
    select tradepartition,id,code,price,amount,userid,submittime,tradeflag,validamount,modifytime,str1,str2,str7,tradeunit,rown from (select tradepartition,id,code,price,amount,userid,submittime,tradeflag,validamount,modifytime,str1,str2,str7,tradeunit,rownum rown from (select t1.tradepartition,t1.id,t1.code,t1.price,t1.amount,t1.userid,t1.submittime,t1.tradeflag,t1.validamount,t1.modifytime,t4.str1,t4.str2,t4.str7,t3.tradeunit from v_cursubmit t1,v_curcommodity t2,v_commodity t3,v_commext t4
    <c_db:if test="${!empty where}">   WHERE    ${where}   </c_db:if>
    <c_db:if test="${!empty groupBy}"> GROUP BY ${groupBy} </c_db:if>
    <c_db:if test="${!empty having}">  HAVING   ${having}  </c_db:if>
    <c_db:if test="${!empty orderBy}"> ORDER BY ${orderBy} </c_db:if>
    )) where rown between ${(pageIndex-1)*pageSize+1} and ${pageIndex*pageSize}
    
    
</sql_db:query>

<c_db:forEach var="v_row" items="${v_result.rows}">
    <jsp:doBody/>
</c_db:forEach>
