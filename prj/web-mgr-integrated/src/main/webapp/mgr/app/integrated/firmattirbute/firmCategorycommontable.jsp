<%@ page contentType="text/html;charset=GBK"%>
<table border="0" width="100%" align="center">
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
					<tr style="display: none;">
						<td align="right" width="25%">
							<span class="required">*</span>
							类别编号
							：
						</td>
						<td width="30%">
							<input type="text" id="id"
								${readonly}
								class="validate[required,custom[onlyLetterNumber],maxSize[10]] input_text"
								name="entity.id" value="1" />
						</td>
						<script>
						if(${readonly!=null}){
								 frm.id.style.backgroundColor="#d0d0d0";
								}
						</script>
					<td colspan="2" width="*">
							<div class="onfocus">
								不能为空！
							</div>
						</td>
					</tr >
					<tr>
						<td align="right">
							<span class="required">*</span>
							类别名称 ：
						</td>
						<td>
							<input type="text" id="name" class="validate[required,maxSize[<fmt:message key='name_Q' bundle='${PropsFieldLength}' />],ajax[checkFirmCategoryByName]] input_text" name="entity.name" value="${entity.name}"/>
						</td>
						<td colspan="2">
							<div class="onfocus">
								不能为空！
							</div>
						</td>
					</tr>
					<tr>
						<td align="right" width="20%">
							<span class="required">*</span>
							是否可选择 ：
						</td>
						<td  width="30%">
							<select id="isvisibal" name="entity.isvisibal" class="input_text" style="width: 120px;" value="${entity.isvisibal}">
								<option value="Y" <c:if test="${entity.isvisibal eq 'Y'}">selected="selected"</c:if>>可选择</option>
								<option value="N" <c:if test="${entity.isvisibal eq 'N'}">selected="selected"</c:if>>不可选择</option>
							</select>
						</td>
						<td colspan="2">
							<div class="onfocus">
								不能为空！
							</div>
						</td>
					</tr>
					<tr>
						<td align="right" width="20%">
							<span class="required">*</span>
							排序号：
						</td>
						<td  width="30%">
							<input type="text" id="sortNo"  ${readonly} class="validate[required,custom[onlyNumberSp],maxSize[2],ajax[checkFirmCategoryBySortNo]] input_text" name="entity.sortNo" value="${entity.sortNo}"/>
						</td>
						<td colspan="2">
							<div class="onfocus">
								不能为空！
							</div>
						</td>
					</tr>
					<tr>
						<td align="right" >
							类别描述：
						</td>
						<td colspan="3">
							<textarea rows="4" cols="50" class="validate[maxSize[<fmt:message key='note_q' bundle='${PropsFieldLength}' />]]" id="note" name="entity.note">${entity.note}</textarea>
						</td>
					</tr>
					
				</tr> 
				</table>
			</div>
		</td>
	</tr>

</table>
<script>

</script>