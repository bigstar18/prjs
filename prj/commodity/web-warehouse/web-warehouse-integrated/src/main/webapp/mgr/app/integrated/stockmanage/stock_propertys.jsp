<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="modulebase">
<table border="0" width="100%">
	<c:set var="propnum" value="0"/>
	<c:forEach var="pmap" items="${propertyMap}" varStatus="status"><%//Map<属性分类编号,Map<商品分类属性编号,List<品名属性集合>>> %>
	<tr>
		<td align="center">
		<table  border='0' cellspacing='4' cellpadding='0'width='100%'>
			<tr height='20px'>
				<td align="center">
				<strong><font color="#000000">${ptnameMap[pmap.key]}:</font></strong>
				</td>
				<td colspan="2">&nbsp;</td>
			</tr>
			<c:forEach var="cpmap" items="${pmap.value}"><%//Map<商品分类属性编号,List<品名属性集合>> %>
			<tr height='20px'>
				<td width='30%' align='right'>
					<c:if test="${categoryPropertyMap[cpmap.key].isNecessary eq 'Y'}"><span class='required'>*</span></c:if>
					${categoryPropertyMap[cpmap.key].propertyName}
					<input id='goodsPropertys[${propnum}].propertyName'  name='goodsPropertys[${propnum}].propertyName' type='hidden' value='${categoryPropertyMap[cpmap.key].propertyName}'/>
					<input id='goodsPropertys[${propnum}].propertyTypeID'  name='goodsPropertys[${propnum}].propertyTypeID' type='hidden' value='${pmap.key}'/>
				</td>
				<td width='28%'>
					<c:set var="classType" value=""/>
					<c:if test="${categoryPropertyMap[cpmap.key].isNecessary eq 'Y'}">
					<c:set var="classType" value="${classType},required"/>
					</c:if>
					<c:if test="${categoryPropertyMap[cpmap.key].fieldType==1}">
					<c:set var="classType" value="${classType},custom[number]"/>
					</c:if>
					<c:if test="${categoryPropertyMap[cpmap.key].hasValueDict eq 'Y'}">
					<select style='width: 130px' id='goodsPropertys[${propnum}].propertyValue'  name='goodsPropertys[${propnum}].propertyValue'  class='select validate[maxSize[64]${classType}]'>
						<option value="">请选择</option>
						<c:forEach var="pplist" items="${cpmap.value}">
						<option value="${pplist.propertyValue}">${pplist.propertyValue}</option>
						</c:forEach>
					</select>
					</c:if>
					<c:if test="${!(categoryPropertyMap[cpmap.key].hasValueDict eq 'Y')}">
					<input id='goodsPropertys[${propnum}].propertyValue'  name='goodsPropertys[${propnum}].propertyValue' class='validate[maxSize[64]${classType}]'  value=''/>
					</c:if>
				</td>
				<td>
					<c:if test="${categoryPropertyMap[cpmap.key].hasValueDict eq 'Y'}"><div class='onfocus'>请选择${categoryPropertyMap[cpmap.key].propertyName}信息</div></c:if>
					<c:if test="${!(categoryPropertyMap[cpmap.key].hasValueDict eq 'Y')}"><div class='onfocus'>请输入${categoryPropertyMap[cpmap.key].propertyName}信息</div></c:if>
				</td>
			</tr>
			<c:set var="propnum" value="${propnum+1}"/>
			</c:forEach>
		</table>
		</td>
	</tr>
	</c:forEach>
</table>
</div>