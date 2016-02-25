<%@ page pageEncoding="GBK" %>
<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" abbr="firmId" align="center">交易商代码</td> 	
				<td class="panel_tHead_MB" abbr="buyListing" align="center">买挂牌权限</td>
				<td class="panel_tHead_MB" abbr="sellListing" align="center">卖挂牌权限</td>
				<td class="panel_tHead_MB" abbr="buyDelist" align="center">买摘牌权限</td>
				<td class="panel_tHead_MB" abbr="sellDelist" align="center">卖摘牌权限</td>
				<td class="panel_tHead_MB" abbr="sellRegstock" align="center">卖仓单权限</td>
				<td class="panel_tHead_RB"></td>
			</tr>
		</tHead>
		<tBody>
		<c:forEach items="${resultList}" var="result">
			  		<tr height="22" onclick="selectTr();">
			  			<td class="panel_tBody_LB">&nbsp;</td>
			  			<td class="underLine" align="center"><span onclick="fmod('<c:out value="${result.firmId}"/>')" style="cursor:hand;color:blue">
			  			<c:out value="${result.firmId}"/></span>&nbsp;</td>
			  			<td class="underLine" align="center"><c:if test="${result.buyListing=='Y'}"><c:out value="有"/></c:if><c:if test="${result.buyListing=='N'}"><c:out value="无"/></c:if>&nbsp;</td>
						<td class="underLine" align="center"><c:if test="${result.sellListing=='Y'}"><c:out value="有"/></c:if><c:if test="${result.sellListing=='N'}"><c:out value="无"/></c:if>&nbsp;</td>
						<td class="underLine" align="center"><c:if test="${result.buyDelist=='Y'}"><c:out value="有"/></c:if><c:if test="${result.buyDelist=='N'}"><c:out value="无"/></c:if>&nbsp;</td>
						<td class="underLine" align="center"><c:if test="${result.sellDelist=='Y'}"><c:out value="有"/></c:if><c:if test="${result.sellDelist=='N'}"><c:out value="无"/></c:if>&nbsp;</td>
						<td class="underLine" align="center"><c:if test="${result.sellRegstock=='Y'}"><c:out value="有"/></c:if><c:if test="${result.sellRegstock=='N'}"><c:out value="无"/></c:if>&nbsp;</td>
			  			<td class="panel_tBody_RB">&nbsp;</td>
			  		</tr>
	 </c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="6">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="6">
				<%@ include file="../public/pagerInc.jsp" %>
					
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="6"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>