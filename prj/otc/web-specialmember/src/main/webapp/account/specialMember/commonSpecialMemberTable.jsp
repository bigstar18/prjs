<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type='text/javascript' src='${basePath}/dwr/engine.js'></script>
<script type='text/javascript' src='${basePath}/dwr/util.js'></script>
<script type="text/javascript" src='${basePath}/dwr/interface/checkAction.js'/></script>

<table width="500" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<!-- ������Ϣ -->
	<tr>
		<td colspan="4">
			<fieldset>
				<legend>
				<span class="st_title2"><b>��&nbsp;��&nbsp;��&nbsp;Ϣ&nbsp;&nbsp;</b></span>
				</legend>
				<div id="baseinfo" align="center">
					<table width="421" border="0">
						<tr height="35">
							<td width="105" align="right">
								�ر��Ա���:</td>
							<td colspan="3">
							<input type="text" class="input_textmin" id="id" name="obj.id" value="${obj.id}" onblur="checkSpecialMemberId()">
							</td>
						</tr>
						<tr height="35">
							<td align="right">
								�ر��Ա����:							</td>
							<td colspan="3">
								<input class="input_textmax" type="text" id="memberName" name="obj.name" value="${obj.name}">							</td>
						</tr>
						<tr height="35">
							<td width="105" align="right">
								֤������:							</td>
							<td width="306">
								<select name="obj.papersType">
									<c:forEach items="${accountPapersTypeMap}" var="maps">
											<option value="${maps.key}"<c:if test="${obj.papersType==maps.key}">selected="selected"</c:if>>${maps.value}</option>
									</c:forEach>
						  </select>							</td>
						</tr>
							<tr height="35">
							<td align="right">
								֤������:							</td>
							<td colspan="3">
								    <input class="input_textmax" name="obj.papersName" type="text" value="${obj.papersName}">
								</td>
						</tr>
					</table>
				</div>
			</fieldset>
		</td>
	</tr>
</table>
<!-- ��һ����ʼ-->
<table width="500" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<tr>
		<td colspan="4">
			<fieldset>
				<legend>
								<span class="st_title2"><b>��&nbsp;ϵ&nbsp;��&nbsp;Ϣ&nbsp;&nbsp;</b></span>
				</legend>
				<div id="baseinfo1" align="center">
					<table width="421" border="0">
						<tr height="35">
							<td width="101" align="right">
								��������:							</td>
							<td colspan="3">
								<label>
									<input class="input_textmax" type="text" name="obj.email" value="${obj.email}" >
								</label>							</td>
						</tr>
						<tr height="35">
							<td align="right">
								�������:							</td>
							<td>
								<label>
									<input class="input_textmin" type="text" name="obj.fax" value="${obj.fax}">
								</label>							</td>
							<td align="right">
								�ʱ�:							</td>
							<td>
								<input class="input_textmin" type="text" name="obj.postCode" value="${obj.postCode }" >							</td>
						</tr>
						<tr height="35">
							<td align="right">
								��ϵ�绰:							</td>
							<td width="128" colspan="3">
								<input class="input_textmax" type="text" name="obj.phone" value="${obj.phone}">
						  
						</tr>
						<tr height="35">
							<td align="right">
								ͨѶ��ַ:							</td>
							<td colspan="3">
								<input class="input_textmax" type="text" name="obj.address" value="${obj.address }">							</td>
						</tr>
					</table>
				</div>
			</fieldset>
		</td>
	</tr>
	<!-- ��һ������-->
</table>


