<%@ page pageEncoding="GBK" %>
<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="150%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
  				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB" abbr="commodityRuleId" align="center">��Ʒ�������</td> 	
				<td class="panel_tHead_MB" abbr="breedId" align="center">Ʒ�ֱ��</td>
				<td class="panel_tHead_MB" abbr="commodityRuleFirmId" align="center">������</td>
				<td class="panel_tHead_MB" abbr="commodityRuleBusinessDirection" align="center">��������</td>
				<td class="panel_tHead_MB" abbr="bail" align="center">��֤��</td>
				<td class="panel_tHead_MB" abbr="bailMode" align="center">��֤��ʽ</td>
				<td class="panel_tHead_MB" abbr="tradePoundage" align="center">����������</td>
				<td class="panel_tHead_MB" abbr="tradePoundageMode" align="center">���������ѷ�ʽ</td>
				<td class="panel_tHead_MB" abbr="deliveryPoundage" align="center">����������</td>
				<td class="panel_tHead_MB" abbr="deliveryPoundageMode" align="center">���������ѷ�ʽ</td>
				<td class="panel_tHead_MB" abbr="maxPrice" align="center">��Ʒ��߼�����</td>
				<td class="panel_tHead_MB" abbr="minPrice" align="center">��Ʒ��ͼ�����</td>
				<td class="panel_tHead_MB" abbr="commodityRuleStatus" align="center">״̬</td>
				<td class="panel_tHead_RB"></td>
			</tr>
		</tHead>
		<tBody>
		<c:forEach items="${resultList}" var="result">
			  		<tr height="22" onclick="selectTr();">
			  			<td class="panel_tBody_LB">&nbsp;</td>
			  			<td class="underLine">
			  			<input name="delCheck" type="checkbox" value="<c:out value="${result.commodityRuleId}"/>"></td>
			  			<td class="underLine" align="center"><span onclick="fmod('<c:out value="${result.commodityRuleId}"/>')" style="cursor:hand;color:blue">
			  			<c:out value="${result.commodityRuleId}"/></span>&nbsp;</td>
						<td class="underLine" align="center"><c:choose><c:when test="${result.breedId==-1 }"><c:out value="ȫ��"/></c:when><c:otherwise><c:out value="${result.breedId}"/></c:otherwise></c:choose>&nbsp;</td>
						<td class="underLine" align="center"><c:choose><c:when test="${result.commodityRuleFirmId=='-1'}"><c:out value="ȫ��"/></c:when><c:otherwise><c:out value="${result.commodityRuleFirmId}"/></c:otherwise></c:choose>&nbsp;</td>
						<td class="underLine" align="center"><c:if test="${result.commodityRuleBusinessDirection=='B'}"><c:out value="��"/></c:if><c:if test="${result.commodityRuleBusinessDirection=='S'}"><c:out value="��"/></c:if>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.bail}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:if test="${result.bailMode==1}">�ٷֱ�</c:if><c:if test="${result.bailMode==2}">����ֵ</c:if>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.tradePoundage}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:if test="${result.tradePoundageMode==1}">�ٷֱ�</c:if><c:if test="${result.tradePoundageMode==2}">����ֵ</c:if>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.deliveryPoundage}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:if test="${result.deliveryPoundageMode==1}">�ٷֱ�</c:if><c:if test="${result.deliveryPoundageMode==2}">����ֵ</c:if>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.maxPrice}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.minPrice}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:if test="${result.commodityRuleStatus==1}">����</c:if><c:if test="${result.commodityRuleStatus==2}">��ֹ</c:if><c:if test="${result.commodityRuleStatus==3}">ɾ��</c:if>&nbsp;</td>

			  			<td class="panel_tBody_RB">&nbsp;</td>
			  		</tr>
	 </c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="14">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="14">
				<%@ include file="../public/pagerInc.jsp" %>
					
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="14"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>