<%@ page pageEncoding="GBK" %>
<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="250%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
  				<c:forEach items="${prosceniumShowList}" var="prosceniumShow">
  				<td class="panel_tHead_MB" abbr="${prosceniumShow.showProperty }" align="center">${prosceniumShow.showName }</td> 
  				</c:forEach>
  				<td class="panel_tHead_MB" align="center">操作</td>
  				<td class="panel_tHead_RB"></td>
			</tr>
		</tHead>
		<tBody>
		<c:forEach items="${resultList}" var="result">
			  		<tr height="22" onclick="selectTr();">
			  			<td class="panel_tBody_LB">&nbsp;</td>
			  			<c:forEach items="${prosceniumShowList}" var="prosceniumShow">
			  			<td class="underLine" align="center">${result[prosceniumShow.showProperty]}&nbsp;</td> 
  						</c:forEach>
  						<td class="underLine" align="center">
						<span onclick="fmod('<c:out value="${result.goodsOrderId}"/>')" style="cursor:hand;color:blue">
			  			商品参数</span>&nbsp;</td>
  						<td class="panel_tBody_RB">&nbsp;</td>
			  		</tr>
	 </c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="${count+1 }">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="${count+1 }">
				<%@ include file="../public/pagerInc.jsp" %>
					
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="${count+1 }"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>