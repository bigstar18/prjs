<%@ page pageEncoding="GBK" %>
<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr>
  				<td class="panel_tHead_LB">&nbsp;</td>
  				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB" abbr="regStockID" align="center">�ֵ���</td>
				<td class="panel_tHead_MB" abbr="breedID" align="center">��ƷƷ������</td>
				<td class="panel_tHead_MB" abbr="name" align="center">��ƷƷ����</td>
				<td class="panel_tHead_MB" abbr="origin" align="center">����</td>
				<td class="panel_tHead_MB" abbr="grade" align="center">�ȼ�</td>
				<td class="panel_tHead_MB" abbr="sort" align="center">����</td>
				<td class="panel_tHead_MB" abbr="firmId_S" align="center">���������̴���</td>
				<td class="panel_tHead_MB" abbr="weight" align="center">�òֵ���������</td>
				<td class="panel_tHead_MB" abbr="frozenWeight" align="center">���ն�������</td>
				<td class="panel_tHead_MB" abbr="matchWeight" align="center">�������</td>
				<td class="panel_tHead_RB" >&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
		<c:forEach items="${resultList}" var="result">
			  		<tr onclick="selectTr();">
			  			<td class="panel_tBody_LB">&nbsp;</td>
			  			<td class="underLine">
			  			<input name="delCheck" type="checkbox" value="<c:out value="${result.regStockID}" />"></td>
			  			<td class="underLine" align="center"><c:out value="${result.regStockID}"/>&nbsp;</td>
			  			<td class="underLine" align="center"><c:out value="${result.breedID}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.name}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.origin}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.grade}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.sort}"/>&nbsp;</td>
			  			<td class="underLine" align="center"><c:out value="${result.FirmID}"/>&nbsp;</td>
			  			<td class="underLine" align="center"><fmt:formatNumber value="${result.weight}" type="currency" pattern="${sessionScope.quantityDecimalsString }"/>&nbsp;</td>
			  			<td class="underLine" align="center"><fmt:formatNumber value="${result.frozenWeight}" type="currency" pattern="${sessionScope.quantityDecimalsString }"/>&nbsp;</td>
			  			<td class="underLine" align="center"><input type="text" name="${result.regStockID }" id="${result.regStockID }" value="" onblur="checkNumber('${result.weight }','${result.frozenWeight }','${result.regStockID }')"/></td>
			  			<td class="panel_tBody_RB">&nbsp;</td>
			  		</tr>
			  		
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="11">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="11">
					<%@ include file="../../common/public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="11"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>