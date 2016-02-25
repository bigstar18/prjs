<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/vendue/manage/globalDef.jsp"%>
<%
String colspan = request.getParameter("colspan");
%>
<tFoot>
				<tr height="100%">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="<%=colspan%>">&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tFoot_LB">&nbsp;</td>
					<td class="panel_tFoot_MB" colspan="<%=colspan%>"></td>
					<td class="panel_tFoot_RB">&nbsp;</td>
				</tr>
			</tFoot>