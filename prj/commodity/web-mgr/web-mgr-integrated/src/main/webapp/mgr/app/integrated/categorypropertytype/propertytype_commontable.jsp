<%@ page contentType="text/html;charset=GBK"%>

<table border="0" width="100%" align="center">
	<tr>
		<td>
			<div class="div_cxtj">
				<div class="div_cxtjL"></div>
				<div class="div_cxtjC">
					商品分类属性类型信息
				</div>
				<div class="div_cxtjR"></div>
			</div>
			<div style="clear: both;"></div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%"
				align="center" class="st_bor">
				<input type="hidden" id="oldSortNo" name="oldSortNo" value="${entity.sortNo }"/>
				<input type="hidden" id="oldName" name="oldName" value="${entity.name }"/>
				<tr height="35">
					<td align="right" class="td_size" width="20%">
						<span class="required">*</span> 属性类型编号 ：
					</td>
					<td align="left" width="45%">
						${entity.propertyTypeID }
						<input id="propertyTypeID" style="width: 160px;" name="entity.propertyTypeID"
							type="hidden" class="validate[required,maxSize[32]] input_text" value="${entity.propertyTypeID }"/>
					</td>
					<td align="left">
						<div class="onfocus">
							不能为空！
						</div>
					</td>
				</tr>
				<tr height="35">
					<td align="right" class="td_size" width="20%">
						<span class="required">*</span> 属性类型名称 ：
					</td>
					<td align="left" width="45%">
						<input id="name" style="width: 160px;" name="entity.name"
							type="text" class="validate[required,maxSize[32],ajax[checkCategoryPropTypeByName]] input_text" value="${entity.name }"/>
					</td>
					<td align="left">
						<div class="onfocus">
							不能为空！
						</div>
					</td>
				</tr>
				<%/* 
				<tr height="35">
					<td align="right" class="td_size" width="20%">
						<span class="required">*</span> 属性类型状态 ：
					</td>
					<td align="left" width="45%">
							<select id="status" name="entity.status" class="validate[required]" style="width: 160px;">
								<option value="0"  <c:if test="${entity.status eq 0 }">selected='selected'</c:if> >可见</option>
								<option value="1"  <c:if test="${entity.status eq 1 }">selected='selected'</c:if> >不可见</option>
							</select>
					</td>
					<td align="left">
						<div class="onfocus">
							不能为空！
						</div>
				</td>*/%>
				<tr height="35">
					<td align="right" class="td_size" width="20%">
						<span class="required">*</span> 排序号 ：
					</td>
					<td align="left" width="45%">
						<input id="sortNo" style="width: 160px;" name="entity.sortNo"
							type="text" class="validate[required,custom[number],maxSize[2],ajax[checkCategoryPropTypeBySortNo]] input_text" value="${entity.sortNo }"/>
					</td>
					<td align="left">
						<div class="onfocus">
							不能为空！
						</div>
					</td>
				</tr>
	
			</table>
		</td>
	</tr>
</table>
