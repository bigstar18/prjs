<%@ tag body-content="scriptless" %>
<%@ attribute name="var" required="true" rtexprvalue="false" %>
<%@ variable name-from-attribute="var"
    alias="v_row" scope="NESTED" %>
<%@ attribute name="contractid" required="true" %>
<%@ attribute name="operation" required="true" %>
<%@ include file="../init.tagf" %>

<sql_db:query dataSource="${tags_db_dataSource}" var="v_result">
    select * from (select ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,FrozenCaptial,market1,market2,type from dailymoney where contractno=${contractid} and operation=${operation} 
 UNION select ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,FrozenCaptial,market1,market2,type from v_hismoney  where contractno=${contractid} and operation=${operation})
</sql_db:query>

<c_db:forEach var="v_row" items="${v_result.rows}">
    <jsp:doBody/>
</c_db:forEach>
