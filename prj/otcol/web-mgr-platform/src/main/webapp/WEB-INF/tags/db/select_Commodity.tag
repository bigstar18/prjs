<%@ tag body-content="scriptless" %>
<%@ attribute name="var" required="true" rtexprvalue="false" %>
<%@ variable name-from-attribute="var"
    alias="v_row" scope="NESTED" %>
<%@ attribute name="where"   required="true" %>
<%@ include file="../init.tagf" %>

<sql_db:query dataSource="${tags_db_dataSource}" var="v_result">
    select u1.id,u1.code,u1.firsttime,u1.createtime,u1.status,u1.splitid,u1.category,u1.beginprice,u1.stepprice,u1.amount,u1.tradeunit,u1.alertprice,u1.security,u1.fee,u1.minamount,u2.str1,
     u2.str2,u2.str3,u2.str4,u2.str5,u2.str6,u2.str7,u2.str8,u2.str9,u2.str10,u2.str11,u2.str12,u2.str13,u2.str14,u2.str15,u2.str16,str17,str18,u2.num1,u2.num2,
     u2.num3,u2.num4,u2.num5 from v_commodity u1,v_commext u2
    <c_db:if test="${!empty where}">   WHERE    ${where}   </c_db:if>
</sql_db:query>

<c_db:forEach var="v_row" items="${v_result.rows}">
    <jsp:doBody/>
</c_db:forEach>
