<%@ page pageEncoding="GBK" %>

<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">
				</td>
				<td class="panel_tHead_MB" abbr="to_number(RegStockID)">注册单号</td>
				<td class="panel_tHead_MB" abbr="warehouseName">仓库</td>
				<td class="panel_tHead_MB" abbr="to_number(StockID)">入库单号</td>
				<td class="panel_tHead_MB" abbr="firmId">${FIRMID}</td>
				<td class="panel_tHead_MB" abbr="commodityName">品种名称</td>
				<td class="panel_tHead_MB" abbr="weight">现存重量</td>
				<td class="panel_tHead_MB" abbr="frozenWeight">冻结重量</td>
				<td class="panel_tHead_MB" abbr="weight-frozenWeight">可用重量</td>
				<td class="panel_tHead_MB" abbr="CreateTime">注册时间</td>
				<td class="panel_tHead_MB" abbr="type">仓单类型</td>
				<td class="panel_tHead_MB">处理</td>
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
								<c:when test="${result.type==0 }">标准仓单</c:when>
								<c:when test="${result.type==1 }">非标准仓单</c:when>
								<c:when test="${result.type==2 }">临时仓单</c:when>
							</c:choose>
					
	  			</td>
				<td class="underLine"><span onclick="fAuditing('<c:out value="${result.RegStockID}"/>')" style="cursor:hand;color:blue">详情->></span></td>
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