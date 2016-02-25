<%@ page pageEncoding="GBK" %>

	<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" abbr="matchId">��Ա��</td>
				<td class="panel_tHead_MB" abbr="contractId">��ͬ��</td>
				<td class="panel_tHead_MB" abbr="breedId">Ʒ�ִ���</td>
				<td class="panel_tHead_MB" abbr="weight">����</td>
				<td class="panel_tHead_MB" abbr="firmID_B">�򷽽����̴���</td>
				<td class="panel_tHead_MB" abbr="firmID_S">���������̴���</td>
				<td class="panel_tHead_MB" abbr="createTime">����ʱ��</td>
				<td class="panel_tHead_MB" abbr="result">ִ�н��</td>
				<td class="panel_tHead_MB" abbr="status">״̬</td>
				<td class="panel_tHead_MB">�鿴����</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
			<c:forEach items="${list }" var="list">
				<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">${list.matchId }</td>
	  			<td class="underLine">${list.contractId}</td>
	  			<td class="underLine">${list.breedId }</td>
	  			<td class="underLine">${list.weight }</td>
	  			<td class="underLine">${list.firmID_B }</td>
	  			<td class="underLine">${list.firmID_S }</td>
	  			<td class="underLine"><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd"/> </td>
	  			<td class="underLine">
					<c:choose>
							<c:when test="${list.result==1}">��Լ</c:when>
							<c:when test="${list.result==2}">��ΥԼ</c:when>
							<c:when test="${list.result==3}">����ΥԼ</c:when>
							<c:when test="${list.result==4}">˫��ΥԼ</c:when>
							<c:when test="${list.result==5}">��������</c:when>
					</c:choose>
				</td>
	  			<td class="underLine"><font color="red">
					<c:choose>
							<c:when test="${list.status==0}">δ����</c:when>
							<c:when test="${list.status==1}">������</c:when>
							<c:when test="${list.status==2}">�������</c:when>
							<c:when test="${list.status==3}">����</c:when>
					</c:choose></font>
				</td>
	  			<td class="underLine"><span onclick="viewSettle('<c:out value="${list.matchId}"/>');"  style="cursor:hand;color:blue">�鿴����->></span></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>
			</c:forEach>
		</tBody>
	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="10">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="10">
					<%@ include file="../../../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="10">
			
				</td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
		
	
