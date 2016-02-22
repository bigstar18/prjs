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
	<!-- 基本信息 -->
	<tr>
		<td colspan="4">
			<fieldset>
				<legend>
				<span class="st_title2"><b>基&nbsp;本&nbsp;信&nbsp;息&nbsp;&nbsp;</b></span>
				</legend>
				<div id="baseinfo" align="center">
					<table width="100%" border="0">
						<tr height="35">
							<td align="right" width="20%">
								客户代表代码:
							</td>
							<td colspan="3">
								<input type="hidden" name="obj.memberNo" value="${REGISTERID }">
								<input type="text" onblur="checkManagerId()" id="managerNo" name="obj.managerNo" value="${obj.managerNo }" class="input_textcdmax" >
							</td>
							
						</tr>
						<tr height="35">
							<td align="right">
								名称:
							</td>
							<td colspan="3">
								<input type="text" name="obj.name" id="name" value="${obj.name}"
									class="input_textcdmax">
							</td>
						</tr>
						<tr height="35">
							<td  align="right">
								父机构代码:
							</td>
							<td colspan="3">
							<input type="text" name="obj.parentOrganizationNO" id="no" value="${obj.parentOrganizationNO}"
									class="input_textcdmax">
								<!-- <select name="obj.parentOrganizationNO" id="parentNo">
								<option value="">请选择</option>
								<c:forEach items="${orgList}" var="list">
							  	<option value="${list.organizationNO}" <c:if test="${obj.parentOrganizationNO==list.organizationNO}">selected="selected"</c:if>>${list.organizationNO}</option>
							  </c:forEach> -->
							</select>
							</td>
							
					<!-- 	<td align="right">
							证件类型:
						</td>
						<td>
							<select name="obj.papersType" id="papersType">
								<option value="">请选择</option>
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


<!-- 另一个开始-->
<table width="90%" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<tr>
		<td colspan="4">
			<fieldset>
				<legend>
					<span class="st_title2"><b>联&nbsp;系&nbsp;信&nbsp;息&nbsp;&nbsp;</b></span>
				</legend>
				<div id="baseinfo1" align="center">
					<table width="100%" border="0">
						<tr height="35">
							<td align="right" width="20%">
							  电子邮件:
							</td>
							<td colspan="3">
								<input type="text" name="obj.email" class="input_textcdmax" value="${obj.email}">
							</td>
						</tr>
						<tr height="35">
							
							<td align="right"> 
								联系电话: 
							</td>
							<td>
								<input type="text" name="obj.telephone" class="input_textmin" value="${obj.telephone}">
							</td>
							<td align="right" width="12%"> 
								手机: 
							</td>
							<td>
								<input type="text" name="obj.mobile"  class="input_textmin" value="${obj.mobile}">
							</td>
						</tr>
						
						<tr height="35">
							<td align="right">
								通讯地址:
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
	<!-- 另一个结束-->
</table>
