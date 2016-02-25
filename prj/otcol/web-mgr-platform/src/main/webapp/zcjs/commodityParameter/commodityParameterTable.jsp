<%@ page pageEncoding="GBK" %>
<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
  				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll"onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB" abbr="parameterId">���</td>
				<td class="panel_tHead_MB" abbr="breedName">Ʒ������</td>
				<td class="panel_tHead_MB" abbr="propertyName">��Ʒ��������</td>
				<td class="panel_tHead_MB" abbr="parameterName">��Ʒ��������</td>
				<td class="panel_tHead_MB" abbr="parameterDescription">��Ʒ��������</td>
				<td class="panel_tHead_MB" abbr="parameterStatus">״̬</td>
				<td class="panel_tHead_RB" >&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
		<c:forEach items="${resultList}" var="result">
			  		<tr height="22" onclick="selectTr();">
			  			<td class="panel_tBody_LB">&nbsp;</td>
			  			<td class="underLine"><input name="delCheck" type="checkbox" value="<c:out value="${result.parameterId},${result.Status }"/>"></td>
			  			<td class="underLine"><c:out value="${result.parameterId}"/>&nbsp;</td>
			  			<td class="underLine"><c:out value="${result.breedName}"/>&nbsp;</td>
			  			<td class="underLine"><c:out value="${result.propertyName}"/>&nbsp;</td>
			  			<td class="underLine"><c:out value="${result.parameterName}"/>&nbsp;</td>
			  			<td class="underLine"><c:out value="${result.parameterDescription}"/>&nbsp;</td>
			  			<td class="underLine">
			  				<c:if test="${result.parameterStatus==1}">����</c:if>
			  				<c:if test="${result.parameterStatus==2}">��ֹ</c:if>
			  				&nbsp;
			  			</td>
			  			<td class="panel_tBody_RB">&nbsp;</td>
			  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="7">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="7">
					<%@ include file="../../common/public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="7"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>