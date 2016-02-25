<%@ page pageEncoding="GBK" %>

	<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" abbr="matchId">配对编号</td>
				<td class="panel_tHead_MB" abbr="contractId">合同号</td>
				<td class="panel_tHead_MB" abbr="breedId">品种代码</td>
				<td class="panel_tHead_MB" abbr="weight">数量</td>
				<td class="panel_tHead_MB" abbr="firmID_B">买方交易商代码</td>
				<td class="panel_tHead_MB" abbr="firmID_S">卖方交易商代码</td>
				<td class="panel_tHead_MB" abbr="createTime">创建时间</td>
				<td class="panel_tHead_MB" abbr="result">执行结果</td>
				<td class="panel_tHead_MB" abbr="status">状态</td>
				<td class="panel_tHead_MB">查看详情</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
			<c:forEach items="${list }" var="list">
				<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">${list.matchId }</td>
	  			<td class="underLine">${list.contractId}</td>
	  			<td class="underLine">${list.breedId }</td>
	  			<td class="underLine">${list.weight }</td>
	  			<td class="underLine">${list.firmID_B }</td>
	  			<td class="underLine">${list.firmID_S }</td>
	  			<td class="underLine"><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd"/> </td>
	  			<td class="underLine">
					<c:choose>
							<c:when test="${list.result==1}">履约</c:when>
							<c:when test="${list.result==2}">买方违约</c:when>
							<c:when test="${list.result==3}">卖方违约</c:when>
							<c:when test="${list.result==4}">双方违约</c:when>
							<c:when test="${list.result==5}">自主交收</c:when>
					</c:choose>
				</td>
	  			<td class="underLine"><font color="red">
					<c:choose>
							<c:when test="${list.status==0}">未处理</c:when>
							<c:when test="${list.status==1}">处理中</c:when>
							<c:when test="${list.status==2}">处理完成</c:when>
							<c:when test="${list.status==3}">作废</c:when>
					</c:choose></font>
				</td>
	  			<td class="underLine"><span onclick="viewSettle('<c:out value="${list.matchId}"/>');"  style="cursor:hand;color:blue">查看详情->></span></td>
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
				<td class="panel_tFoot_MB" colspan="10">
			
				</td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
		
	
