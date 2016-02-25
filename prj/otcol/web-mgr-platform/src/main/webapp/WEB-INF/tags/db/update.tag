<%@ tag body-content="empty" dynamic-attributes="columnAttr" %>
<%@ attribute name="table" required="true" %>
<%@ attribute name="where" required="false" %>
<%@ include file="../init.tagf" %>

<c_db:set var="v_setList" value=""/>
<c_db:set var="v_separator" value=""/>
<sql_db:update dataSource="${tags_db_dataSource}">
<c_db:forEach var="v_entry" items="${columnAttr}">
<c_db:choose> 
 <c_db:when test="${fn:contains(v_entry.key,'_dt')}"> 
   <sql_db:dateParam value="${v_entry.value}" type="timestamp"/>   
 </c_db:when> 
 <c_db:otherwise> 
   <sql_db:param value="${v_entry.value}"/>
 </c_db:otherwise> 
</c_db:choose>
<c_db:set var="v_setList"
value="${v_setList}${v_separator}${fn:contains(v_entry.key,'_dt')?fn:replace(v_entry.key,'_dt',''):v_entry.key}=?"/>
<c_db:set var="v_separator" value=", "/>
</c_db:forEach>
UPDATE ${table} SET ${v_setList}
<c_db:if test="${!empty where}"> WHERE ${where} </c_db:if>
</sql_db:update>