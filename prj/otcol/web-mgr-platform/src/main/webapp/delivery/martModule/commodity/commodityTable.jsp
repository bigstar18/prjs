<%@ page pageEncoding="GBK" %>

<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB" abbr="id">编号</td>
				<td class="panel_tHead_MB" abbr="name">名称</td>
				<td class="panel_tHead_MB" abbr="countType">计量单位</td>
				<!-- <td class="panel_tHead_MB">状态</td> -->
				<td class="panel_tHead_RB">&nbsp</td>
			</tr>
		</tHead>
		<tBody>
			<c:forEach items="${resultList}" var="result">
				<c:if test="${result.ability==0}">
			  		<tr height="22" onclick="selectTr();">
			  			<td class="panel_tBody_LB">&nbsp;</td>
			  			<td class="underLine">
			  			<input name="delCheck" type="checkbox" value="<c:out value="${result.id}"/>"></td>
			  			<td class="underLine">&nbsp;<span onclick="fmod('<c:out value="${result.id}"/>')" style="cursor:hand;color:blue"><c:out value="${result.id}"/></span></td>
			  			<td class="underLine">&nbsp;<c:out value="${result.name}"/></td>
				  			<td class="underLine">&nbsp;<c:out value="${result.countType}"/></td>
						<!-- <td class="underLine"><c:out value="${result.status}"/></td> -->
			  			<td class="panel_tBody_RB">&nbsp;</td>
			  		</tr>
		  		</c:if>
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
					<%@ include file="../../public/pagerInc.jsp" %>
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