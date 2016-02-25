<%@ page pageEncoding="GBK" %>

<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" abbr="to_number(applyID)">仓单申请号</td>
				<td class="panel_tHead_MB" abbr="firmId">${FIRMID }</td>
				<td class="panel_tHead_MB" abbr="warehouseName">仓库名称</td>
				<td class="panel_tHead_MB" abbr="commodityName">品种名称</td>
				<td class="panel_tHead_MB" abbr="to_number(stockID)">入库单号</td>
				<td class="panel_tHead_MB" abbr="weight">仓单数量</td>
				<td class="panel_tHead_MB" abbr="applyTime">申请时间</td>
				<td class="panel_tHead_MB" abbr="status">当前状态</td>
				<td class="panel_tHead_MB" abbr="type">仓单类型</td>
				<td class="panel_tHead_MB">查看详情</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
		
		<c:forEach items="${resultList }" var="list">
		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine"><c:out value="${list.applyID}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${list.firmId}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${list.warehouseName}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${list.commodityName}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${list.stockID}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${list.weight}"/>&nbsp;</td>
				<td class="underLine"><fmt:formatDate value="${list.applyTime}" pattern="yyyy-MM-dd"/>&nbsp;</td>
	  			<td class="underLine">
	  				<font color="red">
							<c:choose>
								<c:when test="${list.status==1 }">未审核</c:when>
								<c:when test="${list.status==2 }">已审核</c:when>
								<c:when test="${list.status==-2 }">驳回</c:when>
							</c:choose>
						</font>
	  			</td>
	  				<td class="underLine">
	  				
							<c:choose>
								<c:when test="${list.type==0 }">标准仓单</c:when>
								<c:when test="${list.type==1 }">非标准仓单</c:when>
								<c:when test="${list.type==2 }">临时仓单</c:when>
							</c:choose>
					
	  			</td>
				<td class="underLine"><span onclick="viewOprLog('<c:out value="${list.applyID}"/>');"  style="cursor:hand;color:blue">查看详情->></span></td>
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
					<%@ include file="../../../public/pagerInc.jsp" %>
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