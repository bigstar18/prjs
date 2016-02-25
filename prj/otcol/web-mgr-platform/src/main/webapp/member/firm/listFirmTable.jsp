<%@ page pageEncoding="GBK" %>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>



	<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<c:if test="${excel!=1}">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">
					<input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')">
				</td>
				</c:if>
				<td class="panel_tHead_MB" abbr="firmId">交易商代码</td>
				<td class="panel_tHead_MB" abbr="name">交易商名称</td>
				<td class="panel_tHead_MB" abbr="fullname">交易商全称</td>
				<td class="panel_tHead_MB" abbr="fullname">加盟商代码</td>
				<td class="panel_tHead_MB" abbr="tariffname">订单手续费套餐</td>
				<td class="panel_tHead_MB" abbr="firmCategoryId">类别</td>
				<td class="panel_tHead_MB" abbr="contactMan">联系人</td>
				<td class="panel_tHead_MB" abbr="createTime">创建日期</td>
				<td class="panel_tHead_MB" abbr="type">类型</td>
				<td class="panel_tHead_MB" abbr="status">状态</td>
				<c:if test="${excel!=1}">
					<td class="panel_tHead_MB">进入交易员</td>
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
	  				<input name="delCheck" type="checkbox" value="<c:out value="${result.firmId}"/>">
	  			</td>
	  			</c:if>
	  			<td class="underLine"><c:out value="${result.firmId}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.name}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.fullname}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.brokerId}"/>&nbsp;</td>
	  		    <td class="underLine"><c:out value="${result.tariffname}"/>&nbsp;</td>
	  		    
	  		    <c:forEach items="${users}" var = "firmCategory">
	  		    	<c:if test="${result.firmCategoryId == firmCategory.id}">
	  		    		<c:set value="${firmCategory.name}" var="firmCategoryName"/>
	  		    	</c:if>
	  		    </c:forEach>
	  		     <td class="underLine"><c:out value="${firmCategoryName}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.contactMan}"/>&nbsp;</td>
	  			<td class="underLine"><c:if test="${result.createTime ==null}">&nbsp;</c:if><c:if test="${result.createTime !=null}"><fmt:formatDate value="${result.createTime}" pattern="yyyy-MM-dd"/></c:if></td>
	  			<td class="underLine">
	  			<c:if test="${result.type==1}">法人</c:if>
			    <c:if test="${result.type==2}">代理</c:if>
			    <c:if test="${result.type==3}">个人</c:if>
	  			</td>
	  			<td class="underLine">
	  			<c:if test="${result.status=='N'}">正常</c:if>
			    <c:if test="${result.status=='D'}">禁止</c:if>
			    <c:if test="${result.status=='U'}">冻结</c:if>
			    <c:if test="${result.status=='E'}">退市</c:if>
	  			</td>
	  			<c:if test="${excel!=1}">
	  			<td class="underLine">
	  				<span onclick="repairInfo('<c:out value="${result.firmId}"/>')" style="cursor:hand;color:blue">
	  				<img src="<%=basePath%>/public/ico/edit.gif" width="15" height="15" /></span></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  			</c:if>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	 <tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="12">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="12">
					<c:if test="${excel!=1}">
					 <%@ include file="../public/pagerInc.jsp" %>
					</c:if>
						
				
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="12"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
 