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
    select id, usercode, userflag, name, address,postid,managername,manageremail,managerid,mgtele,mgfax,mgmobile,tradername,traderemail,traderid,tdtele,tdfax,tdmobile,enterpriseid,validperiod,poster,bank,account,userlevel,createtime,modifytime,str1,str2,str3,pastdate,rown from 
    (select id, usercode, userflag, name, address,postid,managername,manageremail,managerid,mgtele,mgfax,mgmobile,tradername,traderemail,traderid,tdtele,tdfax,tdmobile,enterpriseid,validperiod,poster,bank,account,userlevel,createtime,modifytime,str1,str2,str3,pastdate,rownum rown from 
    (select t1.id, t1.usercode, t1.userflag, t1.name, t1.address,t1.postid,t1.managername,t1.manageremail,t1.managerid,t1.mgtele,t1.mgfax,t1.mgmobile,t1.enterpriseid,t1.validperiod,t1.poster,t1.bank,t1.account,t1.userlevel,t1.createtime,t1.modifytime,t1.str1,t1.str2,t1.str3,
    case when to_date(substr(validperiod,instr(validperiod,';',1)+1,length(validperiod)),'yyyy-MM-dd')<trunc(sysdate) then '1' else '2' end pastdate,t1.tradername,t1.traderemail,t1.traderid,t1.tdtele,t1.tdfax,t1.tdmobile  
    from v_tradeuserext t1
    <c_db:if test="${!empty where}">   WHERE    ${where}   </c_db:if>
    <c_db:if test="${!empty groupBy}"> GROUP BY ${groupBy} </c_db:if>
    <c_db:if test="${!empty having}">  HAVING   ${having}  </c_db:if>
    <c_db:if test="${!empty orderBy}"> ORDER BY ${orderBy} </c_db:if>
    )) where rown between ${(pageIndex-1)*pageSize+1} and ${pageIndex*pageSize}
    
    
</sql_db:query>

<c_db:forEach var="v_row" items="${v_result.rows}">
    <jsp:doBody/>
</c_db:forEach>
