<%@ page pageEncoding="GBK" %>

<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">&nbsp;</td>
				<td class="panel_tHead_MB" abbr="userId">编号</td>
				<td class="panel_tHead_MB" abbr="name">名称</td>
				<td class="panel_tHead_MB" abbr="warehousename">所属仓库</td>
				<td class="panel_tHead_MB" abbr="roleStatus">管理员级别</td>
				<td class="panel_tHead_MB">详细</td>
				<td class="panel_tHead_MB">修改密码</td>
				<td class="panel_tHead_RB">&nbsp</td>
			</tr>
		</tHead>
		<tBody>
		<c:forEach items="${resultList}" var="list">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">
	  				&nbsp;
	  			</td>
	  			<td class="underLine">${list.userId }</td>
	  			<td class="underLine">${list.name }</td>
	  			<td class="underLine">
	  				<c:if test="${list.warehousename==null }">
	  					暂无所属仓库
	  				</c:if>
	  				${list.warehousename }
	  			</td>
				<td class="underLine">
					<c:choose>
						<c:when test="${list.roleStatus==0 }">超级管理员</c:when>
						<c:otherwise>普通管理员</c:otherwise>
					</c:choose>
				</td>
				<td class="underLine">
					<span onclick="viewUser('<c:out value='${list.userId }'/>','<c:out value='${list.manage_id}'/>')" style="cursor:hand;color:blue">
					查看详情->>
					</span>
				</td>
				<td class="underLine">
				  <span onclick="editPwd('<c:out value='${list.userId }'/>','<c:out value='${list.manage_id}'/>')" style="cursor:hand;color:blue">
					<img src="<%=basePath%>/public/ico/edit.gif" width="15" height="15" />
			      </span>
				</td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
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