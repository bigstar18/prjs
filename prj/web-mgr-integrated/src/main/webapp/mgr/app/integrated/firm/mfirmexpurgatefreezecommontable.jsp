<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.MEBS.integrated.mgr.integrated.IntegratedGlobal"%>
<%
	request.setAttribute("certificateTypeMap",IntegratedGlobal.getCertificateTypeMap());
%>
<table border="0" width="100%" align="center">
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<tr>
		<td>
			<div class="warning">
				<div class="content">
					温馨提示 :交易商详情
				</div>
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div class="div_cxtj">
				<div class="div_cxtjL"></div>
				<div class="div_cxtjC">
					基本信息
				</div>
				<div class="div_cxtjR"></div>
			</div>
			<div style="clear: both;"></div>
			<div>
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="table2_style">
					<tr>
						<td align="right" width="23%">
							<fmt:message key="MFirm.firmId" bundle="${PropsFieldDic}" />
							：
						</td>
						<td width="25%">
							${entity.firmId}
							<input type="hidden" id="id"
								class="validate[required,maxSize[10]] input_text_pwdmin"
								name="entity.firmId" value="${entity.firmId}" />
						</td>
						<td align="right" width="23%">
							<fmt:message key="MFirm.name" bundle="${PropsFieldDic}" />
							：
						</td>
						<td width="*">
							${entity.name}
							<input type="hidden" id="name"
								class="validate[required,maxSize[20]] input_text_pwdmin"
								name="entity.name" value="${entity.name}" />
						</td>
					</tr>

					<tr>

						<td align="right">
							<fmt:message key="MFirm.fullName" bundle="${PropsFieldDic}" />
							：
						</td>
						<td>
							${entity.fullName}
						</td>
						<td align="right">
							<fmt:message key="MFirm.type" bundle="${PropsFieldDic}" />
							：
						</td>
						<td>
							${mfirmTypeMap[entity.type] }
							<input type="hidden" id="type" name="entity.type" value="${entity.type}">
						</td>
					</tr>
					<tr>
						<td colspan="4" >
							<div id="content" style="display: block;">
								<table border="0" cellspacing="0" cellpadding="4"
									width="100%">
									<tr>
										<td align="right" width="22%">
											<fmt:message key="MFirm.industryCode" bundle="${PropsFieldDic}" />
											：
										</td>
										<td width="28%">
											${entity.industryCode}
										</td>
										<td align="right" width="21%">
											<fmt:message key="MFirm.zoneCode" bundle="${PropsFieldDic}" />
											：
										</td>
										<td width="*">
												${entity.zoneCode}
										</td>
									</tr> 
									<tr>
										<td align="right" width="22%">
											<fmt:message key="MFirm.organizationCode" bundle="${PropsFieldDic}" />
											：
										</td>
										<td width="28%">
											${entity.organizationCode}
										</td>
										<td align="right" width="21%">
											<fmt:message key="MFirm.corporateRepresentative" bundle="${PropsFieldDic}" />
											：
										</td>
										<td width="*">
											${entity.corporateRepresentative}
										</td>
									</tr> 
								</table>
						</td>
					</tr>
					</div>
					<tr>
						<td align="right">
							<span class="required">*</span><fmt:message key="MFirm.certificateType" bundle="${PropsFieldDic}" />
							：
						</td>
						<td>
							<c:forEach var="map" items="${certificateTypeMap}">
								<c:if test="${map.key eq entity.certificateType }">${map.value}</c:if>
							</c:forEach>
						</td>

						<td align="right">
							<span class="required">*</span><fmt:message key="MFirm.certificateNO" bundle="${PropsFieldDic}" />
							：
						</td>
						<td>
							${entity.certificateNO}
						</td>

					</tr> 
					<tr>
						<td align="right">
							交易商类别
							：
						</td>
						<td>
								<c:if test="${entity.firmCategoryId eq -1}">未分类</c:if>
								<c:forEach var="firmCategory" items="${firmCategoryList}">
									<c:if test="${firmCategory.id eq entity.firmCategoryId}">${firmCategory.name}</c:if>
								</c:forEach>
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div class="div_cxtj">
				<div class="div_cxtjL"></div>
				<div class="div_cxtjC">
					联系信息
				</div>
				<div class="div_cxtjR"></div>
			</div>
			<div style="clear: both;"></div>
			<div>
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="table2_style">
					<tr>
						<td align="right">
							<fmt:message key="MFirm.address" bundle="${PropsFieldDic}" />
							：
						</td>
						<td colspan="3" align="left">
							${entity.address}
						</td>
					</tr>
					<tr>
						<td align="right" width="23%">
							<fmt:message key="MFirm.email" bundle="${PropsFieldDic}" />
							：
						</td>
						<td width="25%">
							${entity.email}
							<input type="hidden" id="email"
								class="validate[required,maxSize[20],custom[email]] input_text_pwdmin"
								name="entity.email" value="${entity.email}" />
						</td>
						<td align="right" width="23%">
							<fmt:message key="MFirm.postCode" bundle="${PropsFieldDic}" />
							：
						</td>
						<td width="*">
							${entity.postCode}
							<input type="hidden" id="postCode" class="input_text_pwdmin"
								name="entity.postCode" value="${entity.postCode}" />
						</td>
					</tr>

					<tr>
						<td align="right">
							<fmt:message key="MFirm.fax" bundle="${PropsFieldDic}" />
							：
						</td>
						<td>
							${entity.fax}
							<input type="hidden" id="fax" class="input_text_pwdmin"
								name="entity.fax" value="${entity.fax}" />
						</td>
						<td align="right">
							<fmt:message key="MFirm.contactMan" bundle="${PropsFieldDic}" />
							：
						</td>
						<td>
							${entity.contactMan}
							<input type="hidden" id="contactMan"
								class="validate[maxSize[8]] input_text_pwdmin"
								name="entity.contactMan" value="${entity.contactMan}" />
						</td>
					</tr>
				</table>
			</div>

		</td>
	</tr>

	<tr>
		<td>
			<div class="div_cxtj">
				<div class="div_cxtjL"></div>
				<div class="div_cxtjC">
					其他信息
				</div>
				<div class="div_cxtjR"></div>
			</div>
			<div style="clear: both;"></div>
			<div>
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="table2_style">
					<tr>
						<td align="right" width="23%"> 
							<fmt:message key="MFirm.note" bundle="${PropsFieldDic}" />
							：
						</td>
						<td colspan="3" align="left" height="30">
						<textarea rows="3" cols="40" readonly="readonly" name="entity.note">${entity.note }</textarea>
							
						</td>
					</tr>
				</table>
			</div>

		</td>
	</tr>



</table>
<script>
	
</script>