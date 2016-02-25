<%@ page pageEncoding="GBK" %>

<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  					<td class="panel_tHead_LB">&nbsp;</td>
					<td class="panel_tHead_MB">&nbsp;</td>
					<td class="panel_tHead_MB" abbr="to_number(id)">���ⵥ��</td>
					<td class="panel_tHead_MB" abbr="to_number(enterWareID)">��ⵥ��</td>
					<td class="panel_tHead_MB" abbr="firmID">${FIRMID}</td>
					
					<td class="panel_tHead_MB" abbr="planOutDate">�����ʱ��</td>
					<td class="panel_tHead_MB" abbr="commodityName">Ʒ������</td>
					<td class="panel_tHead_MB" abbr="weight">����</td>
					<td class="panel_tHead_MB" abbr="statusName">״̬</td>
					<td class="panel_tHead_MB">����</td>
					<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
			<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onClick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.id}"/></td>
				<td class="underLine"><c:out value="${result.enterWareID}"/></td>
	  			<td class="underLine"><c:out value="${result.firmID}"/></td>
				
				<td class="underLine"><fmt:formatDate value="${result.planOutDate}" pattern="yyyy-MM-dd"/></td>
				<td class="underLine"><c:out value="${result.commodityName}"/></td>
				<td class="underLine"><c:out value="${result.weight}"/></td>
				
				<td class="underLine"><FONT COLOR="red"><c:out value="${result.statusName}"/></FONT></td>
				<td class="underLine"><span onClick="fAuditing('<c:out value="${result.id}"/>')" style="cursor:hand;color:blue">�鿴����->></span></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="9">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="10">
					<%@ include file="../../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="10"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>