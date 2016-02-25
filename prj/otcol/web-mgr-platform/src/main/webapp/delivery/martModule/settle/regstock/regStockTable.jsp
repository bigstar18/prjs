<%@ page pageEncoding="GBK" %>

<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">
				</td>
				<td class="panel_tHead_MB" abbr="to_number(RegStockID)">ע�ᵥ��</td>
				<td class="panel_tHead_MB" abbr="warehouseName">�ֿ�</td>
				<td class="panel_tHead_MB" abbr="to_number(StockID)">��ⵥ��</td>
				<td class="panel_tHead_MB" abbr="firmId">${FIRMID}</td>
				<td class="panel_tHead_MB" abbr="commodityName">Ʒ������</td>
				<td class="panel_tHead_MB" abbr="weight">�ִ�����</td>
				<td class="panel_tHead_MB" abbr="frozenWeight">��������</td>
				<td class="panel_tHead_MB" abbr="weight-frozenWeight">��������</td>
				<td class="panel_tHead_MB" abbr="CreateTime">ע��ʱ��</td>
				<td class="panel_tHead_MB" abbr="type">�ֵ�����</td>
				<td class="panel_tHead_MB">����</td>
				<td class="panel_tHead_RB">&nbsp</td>
			</tr>
		</tHead>
		<tBody>
			<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">
	  			</td>
	  			
	  			<td class="underLine"><c:out value="${result.RegStockID}"/>&nbsp;</td>
				<td class="underLine"><c:out value="${result.warehouseName}"/>&nbsp;</td>
				
				<td class="underLine"><c:out value="${result.StockID}"/>&nbsp;</td>
				<td class="underLine"><c:out value="${result.firmId}"/>&nbsp;</td>
				<td class="underLine"><c:out value="${result.commodityName}"/>&nbsp;</td>
				<td class="underLine"><c:out value="${result.weight}"/>&nbsp;</td>
				<td class="underLine"><c:out value="${result.frozenWeight}"/>&nbsp;</td>
				<td class="underLine"><c:out value="${result.weight - result.frozenWeight}"/>&nbsp;</td>
				
				<td class="underLine"><fmt:formatDate value="${result.CreateTime}" pattern="yyyy-MM-dd"/></td>
					<td class="underLine">
	  				
							<c:choose>
								<c:when test="${result.type==0 }">��׼�ֵ�</c:when>
								<c:when test="${result.type==1 }">�Ǳ�׼�ֵ�</c:when>
								<c:when test="${result.type==2 }">��ʱ�ֵ�</c:when>
							</c:choose>
					
	  			</td>
				<td class="underLine"><span onclick="fAuditing('<c:out value="${result.RegStockID}"/>')" style="cursor:hand;color:blue">����->></span></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="12">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="12">
					<%@ include file="../../../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="12"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
		</table>