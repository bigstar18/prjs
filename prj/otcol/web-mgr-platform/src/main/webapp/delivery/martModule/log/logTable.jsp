<%@ page pageEncoding="GBK" %>
<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
 		<tHead>
 			<tr height="25">
 				<td class="panel_tHead_LB">&nbsp;</td>
			<td class="panel_tHead_MB" abbr="id">���</td>
			<td class="panel_tHead_MB" abbr="popedom">����������</td>
			<td class="panel_tHead_MB" abbr="userid">�����˱��</td>
			<td class="panel_tHead_MB" abbr="operatetime">����ʱ��</td>
			<td class="panel_tHead_MB">�鿴����</td>
			<td class="panel_tHead_RB">&nbsp</td>
		</tr>
	</tHead>
	<tBody>
	
	<c:forEach items="${resultList}" var="list">
	<tr height="22" onclick="selectTr();">
  			<td class="panel_tBody_LB">&nbsp;</td>
  			<td class="underLine"><c:out value="${list.id}"/></td>
  			<td class="underLine">
	  			<c:choose>
  					<c:when test="${list.popedom==0 }">�г�</c:when>
  					<c:when test="${list.popedom==1 }">�ֿ�</c:when>
  					<c:otherwise>������</c:otherwise>
  				</c:choose>
  			</td>
  			<td class="underLine"><c:out value="${list.userid}"/></td>
			<td class="underLine">
				<fmt:formatDate value="${list.operatetime}" pattern="yyyy-MM-dd"/>
			</td>
			<td class="underLine"><span onclick="viewOprLog('<c:out value="${list.id}"/>');"  style="cursor:hand;color:blue">�鿴����->></span></td>
  			<td class="panel_tBody_RB">&nbsp;</td>
  		</tr>
  		</c:forEach>
  		
  	</tBody>
  	<tFoot>
		<tr height="100%">
			<td class="panel_tBody_LB">&nbsp;</td>
			<td colspan="5">&nbsp;</td>
			<td class="panel_tBody_RB">&nbsp;</td>
		</tr>
		<tr height="22">
			<td class="panel_tBody_LB">&nbsp;</td>
			<td class="pager" colspan="5">
				<%@ include file="../../public/pagerInc.jsp" %>
			</td>
			<td class="panel_tBody_RB">&nbsp;</td>
		</tr>
		<tr height="22">
			<td class="panel_tFoot_LB">&nbsp;</td>
			<td class="panel_tFoot_MB" colspan="5"></td>
			<td class="panel_tFoot_RB">&nbsp;</td>
		</tr>
	</tFoot>
</table>
