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
				  <div class="div_cxtj">
						<img src="<%=skinPath%>/cssimg/13.gif" />
						&nbsp;&nbsp;������Ϣ
					</div>
					<div class="div_tj">
					<table width="421" border="0" class="table2_style">
						<tr height="35">
							<td width="76" align="right">
								��Ա���:							</td>
							<td colspan="3">
							<input type="text" class="input_textmin" id="id" name="obj.id" value="${obj.id}" onblur="checkMemberId()">
							</td>
						</tr>
						<tr height="35">
							<td align="right">
								��Ա����:							</td>
							<td colspan="3">
								<input type="text" class="input_textmax" id="memberName" name="obj.name" value="${obj.name}">							</td>
						</tr>
						<tr height="35">
							<td align="right">
								��Ա����:							</td>
							<td width="107">
								<label>
									<select name="obj.memberType">
										<c:forEach items="${accountMemberTypeMap}" var="maps">
											<option value="${maps.key}"<c:if test="${obj.memberType==maps.key}">selected="selected"</c:if>>${maps.value}</option>
										</c:forEach>
									</select>
								</label>							</td>
							<td width="85" align="right">
								֤������:							</td>
							<td width="135">
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
								    <input name="obj.papersName" class="input_textmax" type="text" value="${obj.papersName}">
								</td>
						</tr>
					</table>
			</div>
		</td>
	</tr>
</table>
<!-- ��һ����ʼ-->
<table width="500" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<tr>
		<td colspan="4">
					<div class="div_cxtj">
						<img src="<%=skinPath%>/cssimg/13.gif" />
						&nbsp;&nbsp;��ϵ��Ϣ
					</div>
					<div class="div_tj">
					<table width="421" border="0" class="table2_style">
						<tr height="35">
							<td width="74" align="right">
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
								<input type="text" class="input_textmin" name="obj.postCode" value="${obj.postCode }">							</td>
						</tr>
						<tr height="35">
							<td align="right">
								��ϵ�绰:							</td>
							<td width="112" colspan="3">
								<input type="text" class="input_textmax" name="obj.phone" value="${obj.phone}">
						
						</tr>
						<tr height="35">
							<td align="right">
								ͨѶ��ַ:							</td>
							<td colspan="3">
								<input type="text" class="input_textmax" name="obj.address" value="${obj.address }">							</td>
						</tr>
					</table>
				</div>
		</td>
	</tr>
	<!-- ��һ������-->
</table>


