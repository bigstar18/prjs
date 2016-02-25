<%@ page pageEncoding="GBK" %>

<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" abbr="to_number(id)">通知单号</td>
				<td class="panel_tHead_MB" abbr="firmName">${FIRMID}</td>
				<td class="panel_tHead_MB" abbr="firmName">${FIRMNAME}</td>
				<td class="panel_tHead_MB" abbr="enterDate">拟入库时间</td>
				<td class="panel_tHead_MB" abbr="commodityName">品种名称</td>
				<td class="panel_tHead_MB" abbr="warehouseName">仓库名称</td>
				<td class="panel_tHead_MB" abbr="grade">等级</td>
				<td class="panel_tHead_MB" abbr="weight" align="center">重量</td>
				<td class="panel_tHead_MB" abbr="informStatus">状态</td>
				<td class="panel_tHead_MB">处理</td>
				<td class="panel_tHead_RB">&nbsp</td>
			</tr>
		</tHead>
		<tBody>
			<c:forEach items="${resultList }" var="list">
					<tr height="22" onClick="selectTr();">
						<td class="panel_tBody_LB">&nbsp;</td>
						<td class="underLine">${list.id }</td>
						<td class="underLine">${list.firmId }</td>
						<td class="underLine">${list.firmName }</td>
						<td class="underLine"><fmt:formatDate value='${list.enterDate }' pattern="yyyy-MM-dd"/></td>
						<td class="underLine">${list.commodityName }</td>
						<td class="underLine">${list.warehouseName }</td>
						<td class="underLine">${list.grade }</td>
						<td class="underLine" align="center">${list.weight }
						<!-- 添加对单位为空的判断，如果单位为空。则不显示  add by yangpei -->
						<c:if test="${list.countType !=null}">(${list.countType })</c:if>
						&nbsp;&nbsp;</td>
						<td class="underLine"><font style="color: red;">${list.informStatus }</font></td>
						<td class="underLine"><span onClick="fView('<c:out value='${list.id }'/>')" style="cursor:hand;color:blue">查看详情->></span></td>
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
					<%@ include file="../../public/pagerInc.jsp" %>
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