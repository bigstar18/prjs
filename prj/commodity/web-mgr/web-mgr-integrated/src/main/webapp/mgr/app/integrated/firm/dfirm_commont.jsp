<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table border="0" bordercolor="#FF0000;" width="800px;">
  <tr height="0">
    <td></td>
  </tr>
</table>

<table border="0" width="100%" align="center">
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
				<input type="hidden" id="oldFirmId" name="oldFirmId" />
				<input type="hidden" id="oldEmail" name="oldEmail" />
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="table2_style">
					<tr>
						<td align="right">
							<span class="required">*</span>
							<fmt:message key="MFirm.firmId" bundle="${PropsFieldDic}" />
							：
						</td>
						<td>
							<input type="text" id="id"
								${readonly}
														class="validate[required,maxSize[${firmLen}],custom[onlyLetterNumber],ajax[checkFirmByFirmId]] input_text"
								name="entity.firmId" />
						</td>
						<script>
if(${readonly!=null}){
														    frm.id.style.backgroundColor="#d0d0d0";
														}
												</script>
						<td colspan="2">
							<div class="onfocus">
								不能为空！
							</div>
						</td>
					</tr>
					<tr>
						<td align="right" width="20%">
							<span class="required">*</span> 用户名 ：
						</td>
						<td width="30%">
							<input type="text" id="username"
								class="validate[required,funcCall[checkUserId],maxSize[16],ajax[checkFirmByUserId]] input_text"
								name="username" />
						</td>
						<td colspan="2">
							<div class="onfocus">
								不能为空！此用户名可以登陆交易系统，创建后不能修改。
							</div>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="required">*</span>
							<fmt:message key="MFirm.name" bundle="${PropsFieldDic}" />
							：
						</td>
						<td>
							<input type="text" id="name"
								class="validate[required,maxSize[<fmt:message key='firmName' bundle='${PropsFieldLength}' />]] input_text"
								name="entity.name" />
						</td>
						<td colspan="2">
							<div class="onfocus">
								不能为空！
							</div>
						</td>
					</tr>
					<tr height="35" id="frontIsNeedKeyCode"  style="display: none;">
						<td align="right" class="td_size" width="20%">
							是否启用 KEY ：
						</td>
						<input type="hidden" name="enableKey" value="N" id="enableKey">
						<td align="left" colspan="2"><input type="checkbox" onclick="checkKey(this)" id="kcodech"/>
							<span id="showkey" style="display: none;"><span class="required">*</span>
								<input id="kcode" name="kcode" style="width: 122px;" type="text" value="0123456789ABCDE" readonly="readonly" class="validate[required,custom[onlyLetterNumber]] input_text" />
							</span>
						</td>
					</tr>
					<tr>

						<td align="right" width="20%">
							<fmt:message key="MFirm.fullName" bundle="${PropsFieldDic}" />
							：
						</td>
						<td width="30%">
							<input type="text" id="fullName"
								class="validate[maxSize[<fmt:message key='fullName' bundle='${PropsFieldLength}' />]] input_text"
								name="entity.fullName" />
						</td>
						<td align="right" width="20%">
							<span class="required">*</span>
							<fmt:message key="MFirm.type" bundle="${PropsFieldDic}" />
							：
						</td>
						<td width="*">
							<select id="type" class="validate[required] input_text"
								name="entity.type" onchange="getHidden(this.value)"
								onclick="reduceValue(this.value)">
								<option value="" selected="selected">
									请选择
								</option>
								<c:forEach items="${mfirmTypeMap}" var="map">
									<option value="${map.key}" title='${map.value}'
										<c:if test="${entity.type==map.key }"></c:if>>
										${map.value}
									</option>
								</c:forEach>
							</select>
						</td>
						<script>
function getHidden(type) {
	if (type != 3) {
		$("#content").css("display", "block");
	} else {
		$("#content").css("display", "none");
	}
}
function reduceValue(type) {
	if (type == 3) {
		//当交易商类型为个人时，防止之前输入的域编号、行业编号、组织结构代码和法人代表的值录入
		$("#zoneCode").attr("value", "");
		$("#industryCode").attr("value", "");
		$("#organizationCode").attr("value", "");
		$("#corporateRepresentative").attr("value", "");
	}
}
</script>
					</tr>
					<tr>
						<td colspan="4">
							<div id="content" style="display: block;">
								<table border="0" cellspacing="0" cellpadding="2" width="100%"
									height="60px;">
									<tr>
										<td align="right" width="20%">
											所属行业
											：&nbsp;
										</td>
										<td width="31%">
											<select id="industryCode" name="entity.industryCode"  class="input_text">
												<option value="">请选择</option>
												<c:forEach var="industry" items="${industryList}">
													<c:if test="${industry.isvisibal eq 'Y'}">
													<option value="${industry.code}" title="${industry.name}">${industry.name}</option>
													</c:if>
												</c:forEach>
											</select>	
										</td>
										<td align="right" width="19%">
											所属地域
											：
										</td>
										<td width="*">
											&nbsp;
											<select id="zoneCode" name="entity.zoneCode"  class="input_text">
												<option value="">请选择</option>
												<c:forEach var="zone" items="${zoneList}">
													<c:if test="${zone.isvisibal eq 'Y'}">
													<option value="${zone.code}" title="${zone.name}">${zone.name}</option>
													</c:if>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td align="right" width="20%">
											<span class="required">*</span>
											<fmt:message key="MFirm.organizationCode"
												bundle="${PropsFieldDic}" />
											：&nbsp;
										</td>
										<td width="31%">
											<input type="text" id="organizationCode"
												class="validate[required,custom[onlyLetterNumber],maxSize[9]] input_text"
												name="entity.organizationCode">
										</td>
										<td align="right" width="19%">
											<fmt:message key="MFirm.corporateRepresentative"
												bundle="${PropsFieldDic}" />
											：
										</td>
										<td width="*">
											&nbsp;
											<input type="text" id="corporateRepresentative"
												class="validate[maxSize[16]] input_text"
												name="entity.corporateRepresentative">
										</td>
									</tr>
								</table>
						</td>
					</tr>
					</div>
					</td>
					</tr>
					<tr>
						<td align="right">
							<span class="required">*</span>
							<fmt:message key="MFirm.certificateType"
								bundle="${PropsFieldDic}" />
							：
						</td>
						<td>
							<select id="certificateType" name="entity.certificateType"
								class="validate[required] input_text">
								<option value="">
									请选择
								</option>
								<c:forEach items="${certificateTypeList}" var="certificateType">
									<c:if test="${certificateType.isvisibal eq 'Y'}">
									<option value="${certificateType.code}" title='${certificateType.name}'>
										${certificateType.name}
									</option>
									</c:if>
								</c:forEach>
							</select>
						</td>

						<td align="right">
							<span class="required">*</span>
							<fmt:message key="MFirm.certificateNO" bundle="${PropsFieldDic}" />
							：
						</td>
						<td>
							<input type="text" id="certificateNO"
								class="validate[required,maxSize[<fmt:message key='certificateNO' bundle='${PropsFieldLength}'/>],custom[onlyLetterNumber]] input_text"
								name="entity.certificateNO" />
						</td>

					</tr>
					<tr>
					<td align="right">
					<span class="required">*</span>
						交易商类别：
					</td>
					<td>
						<select id="firmCategoryId" name="entity.firmCategoryId"  class="validate[required] input_text">
							<option value="-1">未分类</option>
							<c:forEach var="firmCategory" items="${firmCategoryList}">
								<c:if test="${firmCategory.isvisibal eq 'Y'}">
								<option value="${firmCategory.id}" title="${firmCategory.name}">${firmCategory.name}</option>
								</c:if>
							</c:forEach>
						</select>
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
							<span class="required">*</span>
							<fmt:message key="MFirm.contactMan" bundle="${PropsFieldDic}" />
							：
						</td>
						<td>
							<input type="text" id="contactMan"
								class="validate[required,maxSize[<fmt:message key='contactMan' bundle='${PropsFieldLength}' />]] input_text"
								name="entity.contactMan" />
						</td>


						<td align="right" width="20%">
							<fmt:message key="MFirm.phone" bundle="${PropsFieldDic}" />
							：
						</td>
						<td width="*">
							<input type="text" id="phone"
								class="validate[maxSize[<fmt:message key='phone' bundle='${PropsFieldLength}' />]] input_text"
								name="entity.phone" data-prompt-position="bottomLeft:0" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="required">*</span> 手机号码 ：
						</td>
						<td>
							<input type="text" id="mobile"
								class="validate[required,maxSize[<fmt:message key='mobile' bundle='${PropsFieldLength}' />],custom[mobile]] input_text"
								name="entity.mobile" />
						</td>
						<td align="right" width="20%">
							<fmt:message key="MFirm.fax" bundle="${PropsFieldDic}" />
							：
						</td>
						<td width="30%">
							<input type="text" id="fax"
								class="validate[maxSize[<fmt:message key='fax' bundle='${PropsFieldLength}' />],custom[fax]] input_text"
								name="entity.fax" />
						</td>

					</tr>
					<tr>
						<td align="right" width="20%">
							<span class="required">*</span>
							<fmt:message key="MFirm.email" bundle="${PropsFieldDic}" />
							：
						</td>
						<td width="30%">
							<input type="text" id="email"
								class="validate[required,maxSize[<fmt:message key='email' bundle='${PropsFieldLength}' />],custom[email]] input_text"
								name="entity.email" />
						</td>

						<td align="right" width="20%">
							<fmt:message key="MFirm.postCode" bundle="${PropsFieldDic}" />
							：
						</td>
						<td width="*">
							<input type="text" id="postCode"
								class="validate[custom[onlyNumberSp],maxSize[<fmt:message key='postcode' bundle='${PropsFieldLength}' />]] input_text"
								name="entity.postCode" />
						</td>

					</tr>
					<tr>
						<td align="right">
							<span class="required">*</span>
							<fmt:message key="MFirm.address" bundle="${PropsFieldDic}" />
							：
						</td>
						<td colspan="3" align="left">
							<input type="text" id="address" name="entity.address" style="width: 404px;"
								class="validate[required,maxSize[<fmt:message key='address' bundle='${PropsFieldLength}' />]] input_text"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							<fmt:message key="MFirm.note" bundle="${PropsFieldDic}" />
							：
						</td>
						<td colspan="3">
							<textarea rows="3" cols="64" name="entity.note" id="note"
								class="validate[maxSize[<fmt:message key='note' bundle='${PropsFieldLength}' />]] "></textarea>
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
					密码信息
				</div>
				<div class="div_cxtjR"></div>
			</div>
			<div style="clear: both;"></div>
			<div>
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="table2_style">
					<tr>
						<td align="right" width="20%">
							<span class="required">*</span> 用户密码 ：
						</td>
						<td width="30%">
							<input type="password" id="password" onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
								class="validate[required,minSize[6],maxSize[32]] input_text" name="password" />
						</td>
						<td colspan="2" width="*">
							<div class="onfocus">
								不能为空！
							</div>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="required">*</span> 确认密码 ：
						</td>
						<td>
							<input type="password" id="password1" onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
								class="validate[required,equals[password]] input_text"
								name="password" />
						</td>
						<td colspan="2">
							<div class="onfocus">
								不能为空！
							</div>
						</td>
					</tr>
					<tr>
						<td align="right">
							权限：
						</td>
						<td colspan="3" id="moduleIdtd">
							<c:forEach var="map" items="${tradeModuleMap}">
							<c:if test="${map.value.isFirmSet eq 'Y'}">
							<input type="checkbox" id="ch${map.key}" name="firmModules" value="${map.key}"/>
							<label onclick="ch${map.key}.click();">${map.value.shortName}</label>
							</c:if>
							</c:forEach>
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>

</table>