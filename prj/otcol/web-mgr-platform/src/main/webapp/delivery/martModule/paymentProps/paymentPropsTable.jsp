<%@ page pageEncoding="GBK" %>

<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">
				</td>
				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB" abbr="ModuleID">交易模块</td>
				<td class="panel_tHead_MB" abbr="breedName">品种名称</td>
				<td class="panel_tHead_MB" abbr="SettleDayNo">第几交收日</td>
				
				<td class="panel_tHead_MB" abbr="BuyPayoutPct">收买家货款比例</td>
				<td class="panel_tHead_MB" abbr="SellIncomePct">付卖家货款比例</td>
				<td class="panel_tHead_MB">修改</td>
				<td class="panel_tHead_RB">&nbsp</td>
			</tr>
		</tHead>
		<tBody>
			<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">
	  			</td>
	  			
	  			<td class="underLine">
	  				<input name="delCheck" type="checkbox" value="${result.ModuleID},${result.breedID},${result.SettleDayNo}">
	  			</td>
	  			<td class="underLine">
	  				<c:forEach items="${moduleNameMap}" var="moduleNameMap">
						<c:choose>
		  					<c:when test="${result.ModuleID == moduleNameMap.key}">
		  						${moduleNameMap.value}
		  					</c:when>
		  				</c:choose>
			        </c:forEach>
	  			</td>
				<td class="underLine"><c:out value="${result.breedName}"/></td>
				<td class="underLine">${result.SettleDayNo}</td>
				
				<td class="underLine"><c:out value="${result.BuyPayoutPct}"/></td>
				<td class="underLine"><c:out value="${result.SellIncomePct}"/></td>
				<td class="underLine"><span onclick="fupdate('<c:out value="${result.ModuleID}"/>','<c:out value="${result.breedID}"/>','<c:out value="${result.SettleDayNo}"/>')" style="cursor:hand;color:blue">进入->></span></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="8">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="8">
					<%@ include file="../../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="8"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
		</table>