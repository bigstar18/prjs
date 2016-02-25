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
<%@ attribute name="province" required="false" %>
<%@ attribute name="userlevel" required="false" %>
<%@ attribute name="createtime" required="true" %>
<%@ attribute name="modifytime" required="true" %>
<%//交易商表信息%>
<%@ attribute name="code" required="true" %>
<%@ attribute name="password" required="true" %>
<%@ attribute name="status" required="true" %>
<%@ attribute name="overdraft" required="true" %>
<%@ attribute name="feecut" required="true" %>
<%@ attribute name="feecutfee" required="true" %>
<%@ attribute name="balance" required="true" %>
<%@ attribute name="frozencaptial" required="true" %>
<%@ attribute name="category1" required="true" %>
<%@ attribute name="category2" required="true" %>
<%@ attribute name="tradecount" required="true" %>
<%@ attribute name="totalsecurity" required="true" %>

<c_db:set var="columns1" value="id,userflag,createtime,modifytime"/>
<c_db:set var="values1" value="${id},${userflag},to_date('${createtime}','yyyy-mm-dd hh24:mi:ss'),to_date('${modifytime}','yyyy-mm-dd hh24:mi:ss')"/>

<c_db:set var="columns2" value="usercode,password,status,overdraft,feecut,feecutfee,balance,frozencapital,category1,category2,tradecount,totalsecurity"/>
<c_db:set var="values2" value="'${code}','${password}',${status},${overdraft},${feecut},${feecutfee},${balance},${frozencaptial},${category1},${category2},${tradecount},${totalsecurity}"/>
<%//交易用户分市场背资金%>
<c_db:set var="columns3" value="usercode,marketid,balance"/>
<sql_db:transaction dataSource="${tags_db_dataSource}">
  <sql_db:update>
    <c_db:if test="${not empty usercode}"><c_db:set var="columns1" value="${columns1},usercode"/>
    <c_db:set var="values1" value="${values1},'${usercode}'"/>
    </c_db:if>
    <c_db:if test="${not empty name}"><c_db:set var="columns1" value="${columns1},name"/>
    <c_db:set var="values1" value="${values1},'${name}'"/>
    </c_db:if>
    <c_db:if test="${not empty address}"><c_db:set var="columns1" value="${columns1},address"/>
    <c_db:set var="values1" value="${values1},'${address}'"/>
    </c_db:if>
    <c_db:if test="${not empty postid}"><c_db:set var="columns1" value="${columns1},postid"/>
    <c_db:set var="values1" value="${values1},'${postid}'"/>
    </c_db:if>
    <c_db:if test="${not empty managername}"><c_db:set var="columns1" value="${columns1},managername"/>
    <c_db:set var="values1" value="${values1},'${managername}'"/>
    </c_db:if>
    <c_db:if test="${not empty manageremail}"><c_db:set var="columns1" value="${columns1},manageremail"/>
    <c_db:set var="values1" value="${values1},'${manageremail}'"/>
    </c_db:if>
    <c_db:if test="${not empty managerid}"><c_db:set var="columns1" value="${columns1},managerid"/>
    <c_db:set var="values1" value="${values1},'${managerid}'"/>
    </c_db:if>
    <c_db:if test="${not empty mgtele}"><c_db:set var="columns1" value="${columns1},mgtele"/>
    <c_db:set var="values1" value="${values1},'${mgtele}'"/>
    </c_db:if>
    <c_db:if test="${not empty mgfax}"><c_db:set var="columns1" value="${columns1},mgfax"/>
    <c_db:set var="values1" value="${values1},'${mgfax}'"/>
    </c_db:if>
    <c_db:if test="${not empty mgmobile}"><c_db:set var="columns1" value="${columns1},mgmobile"/>
    <c_db:set var="values1" value="${values1},'${mgmobile}'"/>
    </c_db:if>
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
    <c_db:if test="${not empty enterpriseid}"><c_db:set var="columns1" value="${columns1},enterpriseid"/>
    <c_db:set var="values1" value="${values1},'${enterpriseid}'"/>
    </c_db:if>
    <c_db:if test="${not empty validperiod}"><c_db:set var="columns1" value="${columns1},validperiod"/>
    <c_db:set var="values1" value="${values1},'${validperiod}'"/>
    </c_db:if>
    <c_db:if test="${not empty poster}"><c_db:set var="columns1" value="${columns1},poster"/>
    <c_db:set var="values1" value="${values1},'${poster}'"/>
    </c_db:if>
    <c_db:if test="${not empty bank}"><c_db:set var="columns1" value="${columns1},bank"/>
    <c_db:set var="values1" value="${values1},'${bank}'"/>
    </c_db:if>
    <c_db:if test="${not empty account}"><c_db:set var="columns1" value="${columns1},account"/>
    <c_db:set var="values1" value="${values1},'${account}'"/>
    </c_db:if>
    <c_db:if test="${not empty userlevel}"><c_db:set var="columns1" value="${columns1},userlevel"/>
    <c_db:set var="values1" value="${values1},'${userlevel}'"/>
    </c_db:if>
    <c_db:if test="${not empty province}"><c_db:set var="columns1" value="${columns1},str1"/>
    <c_db:set var="values1" value="${values1},'${province}'"/>
    </c_db:if>
    insert into v_tradeuserext(${columns1}) values(${values1})
  </sql_db:update>
  <sql_db:update>
    insert into v_tradeuser(${columns2}) values(${values2})
  </sql_db:update>
</sql_db:transaction>
