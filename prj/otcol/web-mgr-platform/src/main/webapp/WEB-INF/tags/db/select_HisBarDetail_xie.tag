<%@ tag body-content="scriptless" %>
<%@ attribute name="var" required="true" rtexprvalue="false" %>
<%@ variable name-from-attribute="var"
    alias="v_row" scope="NESTED" %>
<%@ attribute name="where"   required="false" %>
<%@ include file="../init.tagf" %>

<sql_db:query dataSource="${tags_db_dataSource}" var="v_result">
     select tradepartition,submitid,code,contractid,price,amount,userid,tradedate,section,b_bail,b_poundage,s_bail,s_poundage,status,contractcontent,b_lastbail,s_lastbail,lastamount,result,actualamount,fellbackamount,note,id,security,tradeunit,str1,str2,str3,str6,str7,str19,trademode,bluserid,name,rown from (select tradepartition,submitid,code,contractid,price,amount,userid,tradedate,section,b_bail,b_poundage,s_bail,s_poundage,status,contractcontent,b_lastbail,s_lastbail,lastamount,result,actualamount,fellbackamount,note,id,security,tradeunit,str1,str2,str3,str6,str7,str19,trademode,bluserid,name,rownum rown from (select u1.tradepartition,u1.submitid,u1.code,u1.contractid,u1.price,u1.amount,u1.userid,u1.tradedate,u1.section,u1.b_bail,u1.b_poundage,u1.s_bail,u1.s_poundage,u1.status,u1.contractcontent,u1.b_lastbail,u1.s_lastbail,u1.lastamount,u1.result,u1.actualamount,u1.fellbackamount,u1.note,u3.id,u3.security,u3.tradeunit,u4.str1,u4.str2,u4.str3,u4.str6,u4.str7,u4.str19,u3.trademode,u3.userid bluserid,u2.name from hisbargain u1,tradeUserExt u2,commodity u3,commext u4
    <c_db:if test="${!empty where}">   WHERE    ${where}   </c_db:if>))
</sql_db:query>

<c_db:forEach var="v_row" items="${v_result.rows}">
    <jsp:doBody/>
</c_db:forEach>
