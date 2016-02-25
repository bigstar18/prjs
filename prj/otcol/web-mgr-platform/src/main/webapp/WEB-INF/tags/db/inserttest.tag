<%@ tag body-content="empty" dynamic-attributes="columnAttr" %>
<%@ attribute name="table" required="true" %>
<%@ include file="../init.tagf" %>
<%@ attribute name="dbSource" required="true" %>

<c_db:set var="v_columnNames" value=""/>
<c_db:set var="v_paramMarkers" value=""/>
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
<c_db:set var="v_columnNames"
value="${v_columnNames}${v_separator}${fn:contains(v_entry.key,'_dt')?fn:replace(v_entry.key,'_dt',''):v_entry.key}"/>
<c_db:set var="v_paramMarkers"
value="${v_paramMarkers}${v_separator}${'?'}"/>
<c_db:set var="v_separator" value=", "/>
</c_db:forEach>
INSERT INTO ${table} (${v_columnNames})
VALUES (${v_paramMarkers})
</sql_db:update>