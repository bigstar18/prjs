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
			<fieldset>
				<legend>
				<span class="st_title2"><b>��&nbsp;��&nbsp;��&nbsp;Ϣ&nbsp;&nbsp;</b></span>
				</legend>
				<div id="baseinfo" align="center">
					<table width="100%" border="0">
						<tr height="35">
							<td align="right" width="20%">
								�ͻ��������:
							</td>
							<td colspan="3">
								<input type="hidden" name="obj.memberNo" value="${REGISTERID }">
								<input type="text" onblur="checkManagerId()" id="managerNo" name="obj.managerNo" value="${obj.managerNo }" class="input_textcdmax" >
							</td>
							
						</tr>
						<tr height="35">
							<td align="right">
								����:
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
							<input type="text" name="obj.parentOrganizationNO" id="no" value="${obj.parentOrganizationNO}"
									class="input_textcdmax">
								<!-- <select name="obj.parentOrganizationNO" id="parentNo">
								<option value="">��ѡ��</option>
								<c:forEach items="${orgList}" var="list">
							  	<option value="${list.organizationNO}" <c:if test="${obj.parentOrganizationNO==list.organizationNO}">selected="selected"</c:if>>${list.organizationNO}</option>
							  </c:forEach> -->
							</select>
							</td>
							
					<!-- 	<td align="right">
							֤������:
						</td>
						<td>
							<select name="obj.papersType" id="papersType">
								<option value="">��ѡ��</option>
								<c:forEach items="${papersTypeMap}" var="maps">
							  	<option value="${maps.key}" <c:if test="${obj.papersType==maps.key}">selected="selected"</c:if>>${maps.value}</option>
							  </c:forEach>
							</select>
						</td> -->
					</tr>
	
					</table>
				</div>
			</fieldset>
		</td>
	</tr>
</table>


<!-- ��һ����ʼ-->
<table width="90%" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<tr>
		<td colspan="4">
			<fieldset>
				<legend>
					<span class="st_title2"><b>��&nbsp;ϵ&nbsp;��&nbsp;Ϣ&nbsp;&nbsp;</b></span>
				</legend>
				<div id="baseinfo1" align="center">
					<table width="100%" border="0">
						<tr height="35">
							<td align="right" width="20%">
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
								<input type="text" name="obj.telephone" class="input_textmin" value="${obj.telephone}">
							</td>
							<td align="right" width="12%"> 
								�ֻ�: 
							</td>
							<td>
								<input type="text" name="obj.mobile"  class="input_textmin" value="${obj.mobile}">
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
			</fieldset>
		</td>
	</tr>
	<!-- ��һ������-->
</table>
