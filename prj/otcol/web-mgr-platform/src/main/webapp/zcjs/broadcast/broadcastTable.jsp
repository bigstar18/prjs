<%@ page pageEncoding="GBK" %>
<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
  				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB" abbr="broadcastid">编号</td>
				<td class="panel_tHead_MB" abbr="broadcasttitle">标题</td>
				<td class="panel_tHead_MB" abbr="broadcastsender">发送者</td>
				<td class="panel_tHead_MB" abbr="broadcastsendtime">发送日期</td>
				<td class="panel_tHead_MB" abbr="broadcastcreatetime">创建日期</td>
				<td class="panel_tHead_RB" >&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
		<c:forEach items="${resultList}" var="result">
			  		<tr height="22" onclick="selectTr();">
			  			<td class="panel_tBody_LB">&nbsp;</td>
			  			<td class="underLine">
			  			<input name="delCheck" type="checkbox" value="<c:out value="${result.broadcastId}"/>"></td>
			  			<td class="underLine"><c:out value="${result.broadcastId}"/>&nbsp;</td>
			  			<td class="underLine"><span onclick="fmod('<c:out value="${result.broadcastId}"/>')" style="cursor:hand;color:blue">
			  			<c:out value="${result.broadcastTitle}"/></span>&nbsp;</td>
			  			<td class="underLine"><c:out value="${result.broadcastSender}"/>&nbsp;</td>
						<td class="underLine"><fmt:formatDate value="${result.broadcastSendTime }" pattern="yyyy-MM-dd HH:mm" type="both"/>&nbsp;</td>
						<td class="underLine"><fmt:formatDate value="${result.broadcastCreateTime }" pattern="yyyy-MM-dd HH:mm" type="both"/>&nbsp;</td>
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
					<%@ include file="../../common/public/pagerInc.jsp" %>
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