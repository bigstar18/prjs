<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%> 
<%
String colspan = request.getParameter("colspan");
String pageIndex= request.getParameter("pageIndex");
String pageCnt= request.getParameter("pageCnt");
%>
<tFoot>
				<tr height="100%">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="<%=colspan%>">&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="<%=colspan%>" align="right" class="pagerext">
					<!--��ҳ��Ϣ-->
					��${param.pageIndex}/${param.pageCnt}ҳ ��${param.totalCnt}��&nbsp;&nbsp;&nbsp;
						<input type="hidden" name="pageCnt" value="${param.pageCnt}">
						ÿҳ<input class="text" style="width:25px;" name="pageSize" value="${param.pageSize}" onkeydown="keyChkSetPageSize()" onkeyup="this.value=this.value.replace(/\D/g,'')">��&nbsp;&nbsp;
					<c:choose> 
						 <c:when test="${param.pageIndex > 1}"> 
						   <span style="cursor:hand" onclick="pgTurn(1)">��ҳ</span>&nbsp;&nbsp; <span style="cursor:hand" onclick="pgTurn(2)">��ҳ</span>&nbsp;&nbsp;
						 </c:when> 
						 <c:otherwise>
						   ��ҳ&nbsp;&nbsp; ��ҳ&nbsp;&nbsp;
						 </c:otherwise> 
					</c:choose>
					<%/*
					<c:choose> 
						 <c:when test="${param.pageIndex<param.pageCnt}"> 
						 */%>
						 <%if(Integer.parseInt(pageIndex)<Integer.parseInt(pageCnt)){%>
						   <span style="cursor:hand" onclick="pgTurn(3)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(4)">βҳ</span>&nbsp;&nbsp;
						 <%} else {%>
						 <%/*</c:when> 
						 <c:otherwise>*/%>
						   ��ҳ&nbsp;&nbsp; βҳ&nbsp;&nbsp;
						 <%}%>
						   <%/*
						 </c:otherwise> 
					</c:choose>*/%>
					<input type="hidden" name="pageIndex" value="<c:out value='${param.pageIndex}'/>">
					��<input class="text" type="text" style="width:25px;" name="pageJumpIdx" onkeyup="this.value=this.value.replace(/\D/g,'')">ҳ
					
					<input type="button" name="go" class="smlbtn" value="GO" style="width:25px;" onClick="return keyChk()">&nbsp;&nbsp;
					</td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tFoot_LB">&nbsp;</td>
					<td class="panel_tFoot_MB" colspan="<%=colspan%>"></td>
					<td class="panel_tFoot_RB">&nbsp;</td>
				</tr>
			</tFoot>