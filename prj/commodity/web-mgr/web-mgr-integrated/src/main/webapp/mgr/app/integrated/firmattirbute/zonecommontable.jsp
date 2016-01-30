<%@ page contentType="text/html;charset=GBK"%>
<table border="0" width="100%" align="center">
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			<div>
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="table2_style">
					<tr>
						<td align="right" width="25%">
							<span class="required">*</span>
							������
							��
						</td>
						<td width="30%">
							<input type="text" id="code"
								${readonly}
								class="validate[required,custom[onlyLetterNumber],maxSize[<fmt:message key='code_q' bundle='${PropsFieldLength}' />],ajax[checkZoneByCode]] input_text"
								name="entity.code" value="${entity.code}" />
						</td>
						<script>
						if(${readonly!=null}){
								 frm.id.style.backgroundColor="#d0d0d0";
								}
						</script>
						<td colspan="2" width="*">
							<div class="onfocus">
								����Ϊ�գ�
							</div>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="required">*</span>
							�������� ��
						</td>
						<td>
							<input type="text" id="name" class="validate[required,,maxSize[<fmt:message key='name_q' bundle='${PropsFieldLength}' />]] input_text" name="entity.name" value="${entity.name}"/>
						</td>
						<td colspan="2">
							<div class="onfocus">
								����Ϊ�գ�
							</div>
						</td>
					</tr>
					<tr>
						<td align="right" width="20%">
							<span class="required">*</span>
							�Ƿ��ѡ�� ��
						</td>
						<td  width="30%">
							<select id="isvisibal" name="entity.isvisibal" class="input_text" style="width: 120px;" value="${entity.isvisibal}">
								<option value="Y" <c:if test="${entity.isvisibal eq 'Y'}">selected="selected"</c:if>>��ѡ��</option>
								<option value="N" <c:if test="${entity.isvisibal eq 'N'}">selected="selected"</c:if>>����ѡ��</option>
							</select>
						</td>
						<td colspan="2">
							<div class="onfocus">
								����Ϊ�գ�
							</div>
						</td>
					</tr>
					<tr>
						<td align="right" width="20%">
							<span class="required">*</span>
							����ţ�
						</td>
						<td  width="30%">
							<input type="text" id="sortNo"  ${readonly} class="validate[required,custom[onlyNumberSp],maxSize[2],ajax[checkZoneBySortNo]] input_text" name="entity.sortNo" value="${entity.sortNo}"/>
						</td>
						<script>
						if(${readonly!=null}){
								 frm.sortNo.style.backgroundColor="#d0d0d0";
								}
						</script>
						<td colspan="2">
							<div class="onfocus">
								����Ϊ�գ�
							</div>
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