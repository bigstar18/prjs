<%@ page pageEncoding="GBK" %>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<c:if test="${excel!=1}">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				</c:if>
				<td class="panel_tHead_MB" abbr="traderId"><%=TRADERID%></td>
				<td class="panel_tHead_MB" abbr="name">交易员名称</td>
				<td class="panel_tHead_MB" abbr="firmId">归属交易商</td>
				<td class="panel_tHead_MB" abbr="type">类型</td>
				<td class="panel_tHead_MB" abbr="createTime">创建日期</td>
				<td class="panel_tHead_MB" abbr="status">状态</td>
				<td class="panel_tHead_MB" abbr="enableKey">key</td>
				<c:if test="${excel!=1}">
			    <td class="panel_tHead_MB">修改密码</td>
				<td class="panel_tHead_RB">&nbsp;</td>
				</c:if>
			</tr>
		</tHead>
		<tBody>
	  		<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<c:if test="${excel!=1}">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">
	  				<input name="delCheck" type="checkbox" value="<c:out value="${result.traderId}"/>">
	  			</td>
	  			</c:if>
	  			<td class="underLine">
	  				<span onclick="editInfo('<c:out value="${result.traderId}"/>')" style="cursor:hand;color:blue">
	  				<c:out value="${result.traderId}"/>&nbsp;</span></td>
	  			<td class="underLine"><c:out value="${result.name}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.firmId}"/>&nbsp;</td>
	  			<td class="underLine">
	  			<c:if test="${result.type=='A'}">公司管理员</c:if>
			    <c:if test="${result.type=='N'}">普通交易员</c:if>
	  			</td>
	  			<td class="underLine"><c:if test="${result.createTime ==null}">&nbsp;</c:if><c:if test="${result.createTime !=null}"><fmt:formatDate value="${result.createTime}" pattern="yyyy-MM-dd"/></c:if></td>
	  			<td class="underLine">
	  			<c:if test="${result.status=='N'}">正常</c:if>
			    <c:if test="${result.status=='D'}">禁止</c:if>
			    <c:if test="${result.status=='U'}">冻结</c:if>
	  			</td>
	  			<td class="underLine">
	  			<c:if test="${result.enableKey=='N'}">不启用</c:if>
			    <c:if test="${result.enableKey=='Y'}">启用</c:if>
	  			</td>
	  			<c:if test="${excel!=1}">
	  			<td class="underLine">
	  				<span onclick="repairInfo('<c:out value="${result.traderId}"/>')" style="cursor:hand;color:blue">
	  				<img src="<%=basePath%>/public/ico/edit.gif" width="15" height="15" /></span></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  			</c:if>
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
					<c:if test="${excel!=1}">
					 <%@ include file="../public/pagerInc.jsp" %>
					</c:if>
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

