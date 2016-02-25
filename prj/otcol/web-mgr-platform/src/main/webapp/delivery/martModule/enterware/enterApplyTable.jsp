<%@ page pageEncoding="GBK" %>
<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" abbr="to_number(id)">申请单号</td>
				<td class="panel_tHead_MB" abbr="warehouseName">仓库</td>
				<td class="panel_tHead_MB" abbr="firmId">${FIRMNAME}</td>
				<td class="panel_tHead_MB" abbr="createdate">申请时间</td>
				<td class="panel_tHead_MB" abbr="enterDate">拟入库时间</td>
				<td class="panel_tHead_MB" abbr="commodityName">品种名称</td>
				<td class="panel_tHead_MB" abbr="weight" align="center">重量</td>
				<td class="panel_tHead_MB" abbr="status">状态</td>
				<td class="panel_tHead_MB">处理</td>
				<td class="panel_tHead_RB" >&nbsp;</td>
			</tr>
		</tHead>
		<tBody>	
			<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.id}"/></td>
				<td class="underLine"><c:out value="${result.warehouseName}"/></td>
				<td class="underLine"><c:out value="${result.firmId}"/></td>
	  			<td class="underLine"><fmt:formatDate value="${result.createdate}" pattern="yyyy-MM-dd"/></td>
	  			<td class="underLine"><fmt:formatDate value="${result.enterDate}" pattern="yyyy-MM-dd"/></td>
				<td class="underLine"><c:out value="${result.commodityName}"/></td>
				<td class="underLine" align="center">
					<fmt:formatNumber value="${result.weight}" pattern="#,##0.0000"/>
					<c:out value="${result.countType}"/>&nbsp;
				</td>
				<td class="underLine"><FONT COLOR="red">&nbsp;<c:out value="${result.status}"/></FONT></td>
				<td class="underLine"><span onclick="fAuditing('<c:out value="${result.id}"/>')" style="cursor:hand;color:blue">查看详情->></span></td>
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
					<%@ include file="../../public/pagerInc.jsp" %>
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