<%@ page contentType="text/html;charset=GBK"%>
<script type='text/javascript' src='${basePath}/dwr/engine.js'></script>
<script type='text/javascript' src='${basePath}/dwr/util.js'></script>
<script type="text/javascript" src='${basePath}/dwr/interface/checkAction.js'/></script>
<style>
<!--
select{
	width:100px;
	overflow:hidden;
}
-->
</style>
<table width="90%" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<!-- ������Ϣ -->
	<tr>
		<td colspan="4">
			<div class="div_cxtj">
				<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;������Ϣ
			</div>
				<div id="baseinfo" align="center">
					<table width="90%" border="0" class="table2_style">
						<input type="hidden" name="obj.memberNo" value="${REGISTERID }">
						<input type="hidden" id="organizationNo" name="obj.organizationNO" value="${obj.organizationNO }">
						<!-- 
						<tr height="35">
							<td align="right" width="18%">
								��������:
							</td>
							<td colspan="3">
								<span id="organizationNoSpan">${obj.organizationNO }</span>
								<strong class="check_input">&nbsp;*</strong>
							</td>
						</tr>
						 -->
						<tr height="35">
							<td align="right" width="18%">
								��������:
							</td>
							<td colspan="3">
								<input type="text" name="obj.name" id="name" value="${obj.name}"
									class="input_textcdmax">
							</td>
						</tr>
						<tr height="35">
							<td  align="right">
								����������:
							</td>
							<td colspan="3">
								<select name="obj.parentOrganizationNO" id="parentNo" disabled="disabled">
								<option value=""></option>
								<c:forEach items="${orgList}" var="list">
							  	<option value="${list.organizationNO}" <c:if test="${obj.parentOrganizationNO==list.organizationNO}">selected="selected"</c:if>>${list.name}</option>
							  </c:forEach>
							</select>
							</td>
					</tr>
	
					</table>
				</div>
		</td>
	</tr>
</table>


<!-- ��һ����ʼ-->
<table width="90%" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<tr>
		<td colspan="4">
			<div class="div_cxtj">
				<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;��ϵ��Ϣ
			</div>
				<div id="baseinfo1" align="center" class="table2_style">
					<table width="100%" border="0">
						<tr height="35">
							<td align="right" width="18%">
							  �����ʼ�:
							</td>
							<td colspan="3">
								<input type="text" name="obj.email" class="input_textcdmax" value="${obj.email}">
							</td>
						</tr>
						<tr height="35">
							
							<td align="right"> 
								��ϵ�绰: 
							</td>
							<td>
								<input type="text" name="obj.telephone" class="input_text" value="${obj.telephone}">
							</td>
							<td align="right" width="10%"> 
								�ֻ�: 
							</td>
							<td>
								<input type="text" name="obj.mobile"  class="input_text" value="${obj.mobile}">
							</td>
						</tr>
						
						<tr height="35">
							<td align="right">
								ͨѶ��ַ:
							</td>
							<td colspan="3">
								<input type="text" name="obj.address" class="input_textcdmax" value="${obj.address}">
							</td>
						</tr>
						
					</table>
				</div>
		</td>
	</tr>
	<!-- ��һ������-->
</table>

