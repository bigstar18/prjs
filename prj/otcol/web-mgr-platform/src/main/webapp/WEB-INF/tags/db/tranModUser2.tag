<%@ tag body-content="empty" dynamic-attributes="columnAttr" %>
<%@ include file="../init.tagf" %>
<%//会员表信息%>
<%@ attribute name="id" required="true" %>
<%@ attribute name="usercode" required="false" %>
<%@ attribute name="userflag" required="true" %>
<%@ attribute name="name" required="false" %>
<%@ attribute name="address" required="false" %>
<%@ attribute name="postid" required="false" %>
<%@ attribute name="managername" required="false" %>
<%@ attribute name="manageremail" required="false" %>
<%@ attribute name="managerid" required="false" %>
<%@ attribute name="mgtele" required="false" %>
<%@ attribute name="mgfax" required="false" %>
<%@ attribute name="mgmobile" required="false" %>
<%@ attribute name="tradername" required="false" %>
<%@ attribute name="traderemail" required="false" %>
<%@ attribute name="traderid" required="false" %>
<%@ attribute name="tdtele" required="false" %>
<%@ attribute name="tdfax" required="false" %>
<%@ attribute name="tdmobile" required="false" %>
<%@ attribute name="enterpriseid" required="false" %>
<%@ attribute name="validperiod" required="false" %>
<%@ attribute name="poster" required="false" %>
<%@ attribute name="bank" required="false" %>
<%@ attribute name="account" required="false" %>
<%@ attribute name="userlevel" required="false" %>
<%@ attribute name="province" required="false" %>
<%@ attribute name="modifytime" required="true" %>
<%//交易商表信息%>
<%@ attribute name="status" required="true" %>
<%@ attribute name="code" required="true" %>
<c_db:set var="modParam" value="modifytime=to_date('${modifytime}','yyyy-mm-dd hh24:mi:ss'),userflag=${userflag}"/>
<c_db:set var="modParam2" value="status=${status}"/>

<sql_db:transaction dataSource="${tags_db_dataSource}">
  <sql_db:update>
    <c_db:if test="${not empty usercode}"><c_db:set var="modParam" value="${modParam},usercode='${usercode}'"/></c_db:if>
    <c_db:if test="${not empty name}"><c_db:set var="modParam" value="${modParam},name='${name}'"/></c_db:if>
    <c_db:if test="${not empty address}"><c_db:set var="modParam" value="${modParam},address='${address}'"/></c_db:if>
    <c_db:if test="${not empty postid}"><c_db:set var="modParam" value="${modParam},postid='${postid}'"/></c_db:if>
    <c_db:if test="${not empty managername}"><c_db:set var="modParam" value="${modParam},managername='${managername}'"/></c_db:if>
    <c_db:if test="${not empty manageremail}"><c_db:set var="modParam" value="${modParam},manageremail='${manageremail}'"/></c_db:if>
    <c_db:if test="${not empty managerid}"><c_db:set var="modParam" value="${modParam},managerid='${managerid}'"/></c_db:if>
    <c_db:if test="${not empty mgtele}"><c_db:set var="modParam" value="${modParam},mgtele='${mgtele}'"/></c_db:if>
    <c_db:if test="${not empty mgfax}"><c_db:set var="modParam" value="${modParam},mgfax='${mgfax}'"/></c_db:if>
    <c_db:if test="${not empty mgmobile}"><c_db:set var="modParam" value="${modParam},mgmobile='${mgmobile}'"/></c_db:if>
    <c_db:if test="${not empty tradername}"><c_db:set var="modParam" value="${modParam},tradername='${tradername}'"/></c_db:if>
    <c_db:if test="${not empty traderemail}"><c_db:set var="modParam" value="${modParam},traderemail='${traderemail}'"/></c_db:if>
    <c_db:if test="${not empty traderid}"><c_db:set var="modParam" value="${modParam},traderid='${traderid}'"/></c_db:if>
    <c_db:if test="${not empty tdtele}"><c_db:set var="modParam" value="${modParam},tdtele='${tdtele}'"/></c_db:if>
    <c_db:if test="${not empty tdfax}"><c_db:set var="modParam" value="${modParam},tdfax='${tdfax}'"/></c_db:if>
    <c_db:if test="${not empty tdmobile}"><c_db:set var="modParam" value="${modParam},tdmobile='${tdmobile}'"/></c_db:if>
    <c_db:if test="${not empty enterpriseid}"><c_db:set var="modParam" value="${modParam},enterpriseid='${enterpriseid}'"/></c_db:if>
    <c_db:if test="${not empty validperiod}"><c_db:set var="modParam" value="${modParam},validperiod='${validperiod}'"/></c_db:if>
    <c_db:if test="${not empty poster}"><c_db:set var="modParam" value="${modParam},poster='${poster}'"/></c_db:if>
    <c_db:if test="${not empty bank}"><c_db:set var="modParam" value="${modParam},bank='${bank}'"/></c_db:if>
    <c_db:if test="${not empty account}"><c_db:set var="modParam" value="${modParam},account='${account}'"/></c_db:if>
    <c_db:if test="${not empty userlevel}"><c_db:set var="modParam" value="${modParam},userlevel='${userlevel}'"/></c_db:if>
    <c_db:if test="${not empty province}"><c_db:set var="modParam" value="${modParam},str1='${province}'"/></c_db:if>
    update v_tradeuserext set ${modParam} where id=${id}
  </sql_db:update>
   <sql_db:update>
    update v_tradeuser set ${modParam2} where usercode='${code}'
   </sql_db:update>
</sql_db:transaction>
