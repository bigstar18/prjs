<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='java.util.List' %>
<%@ include file="headInc.jsp" %>
<link rel="stylesheet" href="<%=skinPath%>/css/button.css" type="text/css"/>
<table width="100%"  border="0" cellpadding="4" cellspacing="4">
<!--算出查出数据总记录数-->
<c:set var="len" value=""/> 
<!--跟踪第几条记录-->
<c:set var="c" value="0"/>
<!--一行显示多少条记录-->
<c:set var="rowLen" value="7"/> 
<%
    List listTrademodule=(List)request.getAttribute("listTrademodule");
    int len=listTrademodule.size();
    pageContext.setAttribute("module", listTrademodule);
 %>
<c:set var="len" value="<%=len%>"/>

        <tr>
       <c:forEach items="${module}" var="row">
		  <td width="14%" class=" 
		  	<c:choose>
		  		<c:when test="${param.idx==row.moduleid}">
		  	  top_b2
		  	  </c:when>
		  	  <c:otherwise>
		  	  	top_b1
		  	  </c:otherwise>
		  	</c:choose>
		  	" align="center"><div align="center"><a href="javascript:clickMenu('${row.moduleid}');" class="but_t">${row.name}板块</a></div></td>
            <!--如果登录用户是所有市场,则显示总计-->
			
			<c:if test="${c==len-1}"><!--判断是不是最后一次循环-->
			<c:set var="c" value="${c+1}"/>
             
            <td width="14%" class="
		  	<c:choose>
		  		<c:when test="${param.idx=='0'}">
		  	  top_b2
		  	  </c:when>
		  	  <c:otherwise>
		  	  	top_b1
		  	  </c:otherwise>
		  	</c:choose>
		  	" align="center"><div align="center"><a href="javascript:clickMenu('0');" class="but_t">总计</a></div></td>
			</c:if>
           
		    <c:if test="${c==len-1||c==len}">
				  <c:if test="${(rowLen-1-c%rowLen)>0}">
		   	    <td width="${(rowLen-1-c%rowLen)*14}%" align="center" colspan="${(rowLen-1-c%rowLen)}">&nbsp;</td>
		      </c:if>
		   </c:if>	           
      <c:set var="c" value="${c+1}"/>
        </c:forEach>
         </tr>
<input type="hidden" name="partitionID" value="${param.partitionID}">
<input type="hidden" name="idx" value="${param.idx}">
</table>