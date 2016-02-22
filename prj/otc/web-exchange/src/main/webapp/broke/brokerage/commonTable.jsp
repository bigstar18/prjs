<%@ page contentType="text/html;charset=GBK"%>
<script type='text/javascript' src='${basePath}/dwr/engine.js'></script>
<script type='text/javascript' src='${basePath}/dwr/util.js'></script>
<script type="text/javascript" src='${basePath}/dwr/interface/checkAction.js'/></script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/checksAction.js'/></script>

<style>
<!--
select{
	width:100px;
	overflow:hidden;
}
-->
</style>
<table width="95%" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<!-- 基本信息 -->
	<tr>
		<td colspan="4">
			<div class="div_cxtj">
				<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;基本信息
			</div>
				<div id="baseinfo" align="center">
					<table width="100%" border="0" class="table2_style">
						<tr height="35">
						<td align="right" width="18%">居间代码: 
							</td>
							<td colspan="3">
								<input type="hidden" name="obj.memberNo" value="${REGISTERID }">
								<span id="brokerageNoSpan">${obj.brokerageNo }</span>
								<input type="hidden" id="brokerageNo" name="obj.brokerageNo" value="${obj.brokerageNo }">
								<strong class="check_input">&nbsp;*</strong>
							</td>
								<td><div id="brokerageNo_vTip"></div></td>
						</tr>
						<tr height="35">
							<td align="right">居间名称: 
							</td>
							<td colspan="3">
								<input type="text" name="obj.name" id="brokerageName" value="${obj.name}"
									class="input_textcdmax">
							</td>
						</tr>
						
					</table>
				</div>
		</td>
	</tr>
</table>


<!-- 另一个开始-->
<table width="95%" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<tr>
		<td colspan="4">
			<div class="div_cxtj">
				<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;联系信息
			</div>
				<div id="baseinfo1" align="center">
					<table width="100%" border="0" class="table2_style">
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
		</td>
	</tr>
	<!-- 另一个结束-->
</table>