<%@ page pageEncoding="GBK" %>

<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB" abbr="id">±àºÅ</td>
				<td class="panel_tHead_MB" abbr="name">Ãû³Æ</td>
				<td class="panel_tHead_MB">ÏêÏ¸</td>
				<td class="panel_tHead_RB">&nbsp</td>
			</tr>
		</tHead>
		<tBody>
			<c:forEach items="${resultList}" var="result">
			  		<tr height="22" onclick="selectTr();">
			  			<td class="panel_tBody_LB">&nbsp;</td>
			  			<td class="underLine">
			  			<input name="delCheck" type="checkbox" value="<c:out value="${result.breedId}"/>"></td>
			  			<td class="underLine"><c:out value="${result.breedId}"/></td>
			  			<td class="underLine"><c:out value="${result.breedName}"/></td>
						<td class="underLine"><span onclick="fmod('<c:out value="${result.breedId}"/>')" style="cursor:hand;color:blue">½øÈë->></span></td>
			  			<td class="panel_tBody_RB">&nbsp;</td>
			  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="4">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="4">
					<%@ include file="../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="4"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>