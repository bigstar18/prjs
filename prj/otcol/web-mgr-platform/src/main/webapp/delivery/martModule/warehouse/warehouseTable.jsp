<%@ page pageEncoding="GBK" %>

<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB" abbr="id">编号</td>
				<td class="panel_tHead_MB" abbr="name">名称</td>
				<td class="panel_tHead_MB" abbr="linkman">负责人</td>
				<td class="panel_tHead_MB" abbr="tel">电话</td>
				<td class="panel_tHead_MB">详细</td>
				<td class="panel_tHead_MB">关联商品</td>
				<td class="panel_tHead_RB">&nbsp</td>
			</tr>
		</tHead>
		<tBody>
			<c:forEach items="${resultList}" var="result">
			<c:if test="${result.ability==0}">
	  		<tr height="22" onclick="selectTr();" ondblclick="fmod('<c:out value="${result.id}"/>')">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">
	  				<input name="delCheck" type="checkbox" value="<c:out value="${result.id}"/>">
	  			</td>
	  			<td class="underLine"><c:out value="${result.id}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.name}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.linkman}"/>&nbsp;</td>
				<td class="underLine"><c:out value="${result.tel}"/>&nbsp;</td>
				<td class="underLine"><span onclick="fmod('<c:out value="${result.id}"/>')" style="cursor:hand;color:blue">进入->></span></td>
				<td class="underLine"><span onclick="relate('<c:out value="${result.id}"/>')" style="cursor:hand;color:blue">关联->></span></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:if>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="7">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="7">
					<%@ include file="../../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="7"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>