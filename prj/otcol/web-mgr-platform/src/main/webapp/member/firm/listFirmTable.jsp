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
				<td class="panel_tHead_MB" abbr="firmId">�����̴���</td>
				<td class="panel_tHead_MB" abbr="name">����������</td>
				<td class="panel_tHead_MB" abbr="fullname">������ȫ��</td>
				<td class="panel_tHead_MB" abbr="fullname">�����̴���</td>
				<td class="panel_tHead_MB" abbr="tariffname">�����������ײ�</td>
				<td class="panel_tHead_MB" abbr="firmCategoryId">���</td>
				<td class="panel_tHead_MB" abbr="contactMan">��ϵ��</td>
				<td class="panel_tHead_MB" abbr="createTime">��������</td>
				<td class="panel_tHead_MB" abbr="type">����</td>
				<td class="panel_tHead_MB" abbr="status">״̬</td>
				<c:if test="${excel!=1}">
					<td class="panel_tHead_MB">���뽻��Ա</td>
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
	  			<c:if test="${result.type==1}">����</c:if>
			    <c:if test="${result.type==2}">����</c:if>
			    <c:if test="${result.type==3}">����</c:if>
	  			</td>
	  			<td class="underLine">
	  			<c:if test="${result.status=='N'}">����</c:if>
			    <c:if test="${result.status=='D'}">��ֹ</c:if>
			    <c:if test="${result.status=='U'}">����</c:if>
			    <c:if test="${result.status=='E'}">����</c:if>
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
 