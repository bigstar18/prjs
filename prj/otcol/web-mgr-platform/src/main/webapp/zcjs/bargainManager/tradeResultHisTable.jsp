<%@ page pageEncoding="GBK" %>
<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr>
  				<td class="panel_tHead_LB">&nbsp;</td>
  				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB" abbr="tradeNo" align="center">合同号</td>
				<td class="panel_tHead_MB" abbr="firmId_B" align="center">买方交易商</td>
				<td class="panel_tHead_MB" abbr="firmId_S" align="center">卖方交易商</td>
				<td class="panel_tHead_MB" abbr="breedName" align="center">品种名称</td>
				<td class="panel_tHead_MB" abbr="price" align="center">单价</td>
				<td class="panel_tHead_MB" abbr="quantity" align="center">数量</td>
				<td class="panel_tHead_MB" abbr="spareDate" align="center">交割剩余天数</td>
				<td class="panel_tHead_MB" abbr="status" align="center">合同状态</td>
				<td class="panel_tHead_MB" align="center">交收配对明细</td>
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
			  			<td class="underLine" align="center"><c:if test="${result.spareDate<0}">已交割</c:if><c:if test="${result.spareDate>0}">${result.spareDate}</c:if>&nbsp;</td>
			  			<td class="underLine" align="center">
			  			<c:if test="${result.status==1}">未处理</c:if>
			  			<c:if test="${result.status==2}">处理中</c:if>
			  			<c:if test="${result.status==3}">已处理</c:if>
			  			</td>
			  			<td class="underLine" align="center"><span onclick="fdetail('<c:out value="${result.tradeNo}"/>')" style="cursor:hand;color:blue"/>
			  			<c:out value="进入"/> </td>
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