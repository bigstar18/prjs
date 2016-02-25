<%@ tag body-content="empty" dynamic-attributes="columnAttr" %>
<%@ include file="../init.tagf" %>
<%//会员表信息%>
<%@ attribute name="partuserid" required="true" %>
<%@ attribute name="userid" required="true" %>
<%@ attribute name="operuser" required="true" %>
<%@ attribute name="operdate" required="true" %>
<%@ attribute name="market" required="true" %>
<%@ attribute name="newstatus" required="true" %>
<%@ attribute name="tradername" required="false" %>
<%@ attribute name="traderid" required="false" %>
<%@ attribute name="tdtele" required="false" %>
<%@ attribute name="tdmobile" required="false" %>
<%@ attribute name="tdfax" required="false" %>
<%@ attribute name="traderemail" required="false" %>

<c_db:set var="columns1" value="id,userid,operuser,market,operdate,newstatus"/>
<c_db:set var="values1" value="${partuserid},${userid},'${operuser}',${market},to_date('${operdate}','yyyy-mm-dd hh24:mi:ss')
,${newstatus}"/>
<sql_db:transaction dataSource="${tags_db_dataSource}">
  <sql_db:update>
    update v_operpartuser set newstatus=0 where userid=${userid}
  </sql_db:update>
  <sql_db:update>
  <c_db:if test="${not empty tradername}"><c_db:set var="columns1" value="${columns1},tradername"/>
    <c_db:set var="values1" value="${values1},'${tradername}'"/>
    </c_db:if>
    <c_db:if test="${not empty traderemail}"><c_db:set var="columns1" value="${columns1},traderemail"/>
    <c_db:set var="values1" value="${values1},'${traderemail}'"/>
    </c_db:if>
    <c_db:if test="${not empty traderid}"><c_db:set var="columns1" value="${columns1},traderid"/>
    <c_db:set var="values1" value="${values1},'${traderid}'"/>
    </c_db:if>
    <c_db:if test="${not empty tdtele}"><c_db:set var="columns1" value="${columns1},tdtele"/>
    <c_db:set var="values1" value="${values1},'${tdtele}'"/>
    </c_db:if>
    <c_db:if test="${not empty tdfax}"><c_db:set var="columns1" value="${columns1},tdfax"/>
    <c_db:set var="values1" value="${values1},'${tdfax}'"/>
    </c_db:if>
    <c_db:if test="${not empty tdmobile}"><c_db:set var="columns1" value="${columns1},tdmobile"/>
    <c_db:set var="values1" value="${values1},'${tdmobile}'"/>
    </c_db:if>
    insert into v_operpartuser(${columns1}) values(${values1})
  </sql_db:update>
</sql_db:transaction>
