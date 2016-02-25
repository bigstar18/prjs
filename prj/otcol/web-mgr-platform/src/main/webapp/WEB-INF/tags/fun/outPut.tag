<%@ tag body-content="scriptless" %>
<%@ attribute name="str"   required="true"  %>
<%@ attribute name="len"   required="true"  %>
<%@ include file="../init.tagf" %>

<c_db:choose> 
 <c_db:when test="${fn:length(str)>=len}"> 
   <c_db:out value="${str}"/> 
 </c_db:when> 
 <c_db:otherwise>
   <c_db:out value="${str}"/>
   <c_db:forEach begin="${fn:length(str)}" end="${len}" step="1">
      <c_db:out value="1"/>
   </c_db:forEach>
 </c_db:otherwise> 
</c_db:choose>
