<%@ tag body-content="empty" dynamic-attributes="columnAttr" %>
<%@ include file="../init.tagf" %>
<%//商品表信息%>
<%@ attribute name="id" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="firsttime" required="true" %>
<%@ attribute name="createtime" required="true" %>
<%@ attribute name="status" required="true" %>
<%@ attribute name="splitid" required="true" %>
<%@ attribute name="category" required="true" %>
<%@ attribute name="beginprice" required="true" %>
<%@ attribute name="stepprice" required="true" %>
<%@ attribute name="amount" required="true" %>
<%@ attribute name="tradeunit" required="true" %>
<%@ attribute name="alertprice" required="true" %>
<%@ attribute name="security" required="true" %>
<%@ attribute name="fee" required="true" %>
<%@ attribute name="minamount" required="true" %>
<%@ attribute name="userid" required="true" %>
<%@ attribute name="trademode" required="true" %>
<%//商品扩展属性表信息%>
<%@ attribute name="commid" required="true" %>
<%@ attribute name="str1" required="false" %>
<%@ attribute name="str2" required="false" %>
<%@ attribute name="str3" required="false" %>
<%@ attribute name="str4" required="false" %>
<%@ attribute name="str5" required="false" %>
<%@ attribute name="str8" required="false" %>
<%@ attribute name="str9" required="false" %>
<%@ attribute name="str10" required="false" %>
<%@ attribute name="str11" required="false" %>
<%@ attribute name="str12" required="false" %>
<%@ attribute name="str13" required="false" %>
<%@ attribute name="str14" required="false" %>
<%@ attribute name="str15" required="false" %>
<%@ attribute name="str16" required="false" %>
<%@ attribute name="str17" required="false" %>
<%@ attribute name="str18" required="false" %>
<%@ attribute name="str19" required="false" %>
<%@ attribute name="num1" required="false" %>

<c_db:set var="columns1" value="id,code,firsttime,createtime,status,splitid,category,beginprice,stepprice,amount,tradeunit,alertprice,security,fee,minamount,userid,trademode"/>
<c_db:set var="values1" value="${id},'${code}',to_date('${firsttime}','yyyy-mm-dd'),to_date('${createtime}','yyyy-mm-dd'),${status},'0',${category},${beginprice},${stepprice},${amount},${tradeunit},${alertprice},${security},${fee},${minamount},'${userid}',${trademode}"/>

<c_db:set var="columns2" value="commid"/>
<c_db:set var="values2" value="${commid}"/>
<%//判断父标是否拆过标%>
<c_db:set var="dismantleMark" value=""/>
<c_db:set var="dismantleValue" value=""/>
<sql_db:transaction dataSource="${tags_db_dataSource}">
  <sql_db:update>
    insert into v_commodity(${columns1}) values(${values1})
  </sql_db:update>
  <sql_db:update>
    <c_db:if test="${not empty str1}"><c_db:set var="columns2" value="${columns2},str1"/>
    <c_db:set var="values2" value="${values2},'${str1}'"/>
    </c_db:if>
    <c_db:if test="${not empty str2}"><c_db:set var="columns2" value="${columns2},str2"/>
    <c_db:set var="values2" value="${values2},'${str2}'"/>
    </c_db:if>
    <c_db:if test="${not empty str3}"><c_db:set var="columns2" value="${columns2},str3"/>
    <c_db:set var="values2" value="${values2},'${str3}'"/>
    </c_db:if>
    <c_db:if test="${not empty str4}"><c_db:set var="columns2" value="${columns2},str4"/>
    <c_db:set var="values2" value="${values2},'${str4}'"/>
    </c_db:if>
    <c_db:if test="${not empty str5}"><c_db:set var="columns2" value="${columns2},str5"/>
    <c_db:set var="values2" value="${values2},'${str5}'"/>
    </c_db:if>
    <c_db:if test="${not empty str8}"><c_db:set var="columns2" value="${columns2},str8"/>
    <c_db:set var="values2" value="${values2},'${str8}'"/>
    </c_db:if>
    <c_db:if test="${not empty str9}"><c_db:set var="columns2" value="${columns2},str9"/>
    <c_db:set var="values2" value="${values2},'${str9}'"/>
    </c_db:if>
    <c_db:if test="${not empty str10}"><c_db:set var="columns2" value="${columns2},str10"/>
    <c_db:set var="values2" value="${values2},'${str10}'"/>
    </c_db:if>
    <c_db:if test="${not empty str11}"><c_db:set var="columns2" value="${columns2},str11"/>
    <c_db:set var="values2" value="${values2},'${str11}'"/>
    </c_db:if>
    <c_db:if test="${not empty str12}"><c_db:set var="columns2" value="${columns2},str12"/>
    <c_db:set var="values2" value="${values2},'${str12}'"/>
    </c_db:if>
    <c_db:if test="${not empty str13}"><c_db:set var="columns2" value="${columns2},str13"/>
    <c_db:set var="values2" value="${values2},'${str13}'"/>
    </c_db:if>
    <c_db:if test="${not empty str14}"><c_db:set var="columns2" value="${columns2},str14"/>
    <c_db:set var="values2" value="${values2},'${str14}'"/>
    </c_db:if>
    <c_db:if test="${not empty str15}"><c_db:set var="columns2" value="${columns2},str15"/>
    <c_db:set var="values2" value="${values2},'${str15}'"/>
    </c_db:if>
    <c_db:if test="${not empty str16}"><c_db:set var="columns2" value="${columns2},str16"/>
    <c_db:set var="values2" value="${values2},'${str16}'"/>
    </c_db:if>
    <c_db:if test="${not empty str17}"><c_db:set var="columns2" value="${columns2},str17"/>
    <c_db:set var="values2" value="${values2},'${str17}'"/>
    </c_db:if>
    <c_db:if test="${not empty str18}"><c_db:set var="columns2" value="${columns2},str18"/>
    <c_db:set var="values2" value="${values2},'${str18}'"/>
    </c_db:if>
    <c_db:if test="${not empty str19}"><c_db:set var="columns2" value="${columns2},str19"/>
    <c_db:set var="values2" value="${values2},'${str19}'"/>
    </c_db:if>
    
    <c_db:if test="${not empty num1}"><c_db:set var="columns2" value="${columns2},num1"/>
    <c_db:set var="values2" value="${values2},${num1}"/>
    </c_db:if>
    insert into v_commext(${columns2}) values(${values2})
  </sql_db:update>
  <sql_db:query var="v_result">
     select splitid from v_commodity where id=${splitid}
  </sql_db:query>
  <c_db:forEach var="v_row" items="${v_result.rows}">
     <c_db:set var="dismantleMark" value="${v_row.splitid}"/>
  </c_db:forEach>
  <c_db:choose>
     <c_db:when test="${dismantleMark=='0'}">
       <c_db:set var="dismantleValue" value="${id},"/>
     </c_db:when>
     <c_db:otherwise>
       <c_db:set var="dismantleValue" value="${dismantleMark}${id},"/>
     </c_db:otherwise>
  </c_db:choose>
  <sql_db:update>
    update v_commodity set status=3,splitid='${dismantleValue}' where id=${splitid}
  </sql_db:update>
</sql_db:transaction>
