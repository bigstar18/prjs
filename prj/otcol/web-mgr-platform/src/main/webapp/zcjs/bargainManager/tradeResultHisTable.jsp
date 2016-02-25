<%@ page pageEncoding="GBK" %>
<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr>
  				<td class="panel_tHead_LB">&nbsp;</td>
  				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB" abbr="tradeNo" align="center">��ͬ��</td>
				<td class="panel_tHead_MB" abbr="firmId_B" align="center">�򷽽�����</td>
				<td class="panel_tHead_MB" abbr="firmId_S" align="center">����������</td>
				<td class="panel_tHead_MB" abbr="breedName" align="center">Ʒ������</td>
				<td class="panel_tHead_MB" abbr="price" align="center">����</td>
				<td class="panel_tHead_MB" abbr="quantity" align="center">����</td>
				<td class="panel_tHead_MB" abbr="spareDate" align="center">����ʣ������</td>
				<td class="panel_tHead_MB" abbr="status" align="center">��ͬ״̬</td>
				<td class="panel_tHead_MB" align="center">���������ϸ</td>
				<td class="panel_tHead_RB" >&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
		<c:forEach items="${resultList}" var="result">
			  		<tr onclick="selectTr();">
			  			<td class="panel_tBody_LB">&nbsp;</td>
			  			<td class="underLine">
			  			<input name="delCheck" type="checkbox" value="<c:out value="${result.tradeNo}"/>"></td>
			  			<td class="underLine" align="center"><c:out value="${result.tradeNo}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.firmId_B}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.firmId_S}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.breedName}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.price}"/>&nbsp;</td>
			  			<td class="underLine" align="center"><fmt:formatNumber value="${result.quantity}" type="currency" pattern="${sessionScope.quantityDecimalsString }"/>&nbsp;</td>
			  			<td class="underLine" align="center"><c:if test="${result.spareDate<0}">�ѽ���</c:if><c:if test="${result.spareDate>0}">${result.spareDate}</c:if>&nbsp;</td>
			  			<td class="underLine" align="center">
			  			<c:if test="${result.status==1}">δ����</c:if>
			  			<c:if test="${result.status==2}">������</c:if>
			  			<c:if test="${result.status==3}">�Ѵ���</c:if>
			  			</td>
			  			<td class="underLine" align="center"><span onclick="fdetail('<c:out value="${result.tradeNo}"/>')" style="cursor:hand;color:blue"/>
			  			<c:out value="����"/> </td>
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
					<%@ include file="../../common/public/pagerInc.jsp" %>
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