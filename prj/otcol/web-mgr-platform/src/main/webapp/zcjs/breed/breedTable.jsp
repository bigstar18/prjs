<%@ page pageEncoding="GBK" %>
<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
  				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB" abbr="breedId" align="center">Ʒ�ֱ��</td> 	
				<td class="panel_tHead_MB" abbr="breedName" align="center">Ʒ������</td>
				<td class="panel_tHead_MB" abbr="sortName" align="center">��������</td>
				<td class="panel_tHead_MB" abbr="deliveryMinDay" align="center">����������������</td>
				<td class="panel_tHead_MB" abbr="deliveryMaxDay" align="center">���������������</td>
				<td class="panel_tHead_MB" abbr="tradeUnit" align="center">���׵�λ</td>
				<td class="panel_tHead_MB" abbr="unitVolume" align="center">��λ����</td>
				<td class="panel_tHead_MB" abbr="deliveryRatio" align="center">����ϵ��</td>
				<td class="panel_tHead_RB"></td>
			</tr>
		</tHead>
		<tBody>
		<c:forEach items="${resultList}" var="result">
			  		<tr height="22" onclick="selectTr();">
			  			<td class="panel_tBody_LB">&nbsp;</td>
			  			<td class="underLine">
			  			<input name="delCheck" type="checkbox" value="<c:out value="${result.breedId}"/>"></td>
			  			<td class="underLine" align="center"><span onclick="fmod('<c:out value="${result.breedId}"/>')" style="cursor:hand;color:blue">
			  			<c:out value="${result.breedId}"/></span>&nbsp;</td>
			  			<td class="underLine" align="center"><c:out value="${result.breedName}"/>&nbsp;</td>
			  			<td class="underLine" align="center"><c:out value="${result.sortName}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.deliveryMinDay}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.deliveryMaxDay}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.tradeUnit}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.unitVolume}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.deliveryRatio}"/>&nbsp;</td>
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
				<td class="pager" colspan="9">
				<%@ include file="../public/pagerInc.jsp" %>
					
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="9"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>