<%@ page contentType="text/html;charset=GBK"%>

<table border="0" width="100%" align="center">
	<tr>
		<td>
			<div class="warning">
				<div class="content">
					��ܰ��ʾ :���������Թ���
				</div>
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div class="div_cxtj">
				<div class="div_cxtjL"></div>
				<div class="div_cxtjC">
					������Ϣ
				</div>
				<div class="div_cxtjR"></div>
			</div>
			<div style="clear: both;"></div>
			<div class="div_tj">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="table2_style">
					<input type="hidden" id="oldSortNo" name="oldSortNo" value="${entity.sortNo }"/>
					<input type="hidden" id="propertyId" name="entity.propertyId" value="${entity.propertyId }">
					<tr height="20">
						<td align="right" width="25%">
						<span class="required">*</span>
							����:
						</td>
						<td width="*">
							<input type="text" id="name" class="validate[required,maxSize[<fmt:message key='propertyName' bundle='${PropsFieldLength}' />],ajax[checkCategoryPropName]] input_text"
								name="entity.propertyName" value="${entity.propertyName}">
						</td>
					</tr>
					<tr>
						<td align="right">
							ֵ�ֵ�:
						</td>
						<td>
							<select id="hasValueDict" class="input_text"
								name="entity.hasValueDict">
								<option value="Y" <c:if test="${entity.hasValueDict=='Y' }">selected="selected"</c:if>>��</option>
								<option value="N" <c:if test="${entity.hasValueDict=='N' }">selected="selected"</c:if>>��</option>
								</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							������:
						</td>
						<td>
							<select id="isNecessary" class="input_text"
								name="entity.isNecessary" >
								<option value="Y" <c:if test="${entity.isNecessary=='Y' }">selected="selected"</c:if>>��</option>
								<option value="N" <c:if test="${entity.isNecessary=='N' }">selected="selected"</c:if>>��</option>
								</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							����:
						</td>
						<td>
							<select id="searchable" class="input_text"
								name="entity.searchable" >
								<option value="Y" <c:if test="${entity.searchable=='Y' }">selected="selected"</c:if>>��</option>
								<option value="N" <c:if test="${entity.searchable=='N' }">selected="selected"</c:if>>��</option>
								</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							ƥ��ֵ�:
						</td>
						<td>
							<select id="stockCheck" class="input_text"
								name="entity.stockCheck">
								<option value="Y" <c:if test="${entity.stockCheck=='Y' }">selected="selected"</c:if>>���</option>
								<option value="N" <c:if test="${entity.stockCheck=='N' }">selected="selected"</c:if>>�����</option>
								<option value="M" <c:if test="${entity.stockCheck=='M' }">selected="selected"</c:if>>��Χ���</option>
								</select>
						</td>
					</tr>
					
					<tr>
						<td align="right">
							��������:
						</td>
						<td>
							<select id="fieldType" class="input_text"
								name="entity.fieldType" >
								<c:forEach items="${categoryPropTypeMap }" var="map">
									<option value="${map.key }" <c:if test="${entity.fieldType==map.key }">selected="selected"</c:if>>${map.value }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
						<span class="required">*</span>
							�����:
						</td>
						<td>
							<input type="text" id="sortNo" class="validate[required,maxSize[<fmt:message key='sortNo' bundle='${PropsFieldLength}' />],custom[number],ajax[checkCategoryPropBySortNo]] input_text"
								name="entity.sortNo" value="${entity.sortNo}">
						</td>
					</tr>
					<tr>
						<td align="right">
						<span class="required">*</span>
							��������:
						</td>
						<td>
							<select id="propertyType" name="entity.propertyTypeID" class="validate[required] input_text">
								<c:forEach items="${propertyTypeList}" var="type">
								<option value="${type.propertyTypeID}" <c:if test="${entity.propertyTypeID == type.propertyTypeID}">selected="selected"</c:if>>${type.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
