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
	<!-- ������Ϣ -->
	<tr>
		<td colspan="4">
			<div class="div_cxtj">
				<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;������Ϣ
			</div>
				<div id="baseinfo" align="center">
					<table width="100%" border="0" class="table2_style">
						<tr height="35">
						<td align="right" width="18%">�Ӽ����: 
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
							<td align="right">�Ӽ�����: 
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


<!-- ��һ����ʼ-->
<table width="95%" border="0" align="center" cellpadding="0"
	cellspacing="2">
	<tr>
		<td colspan="4">
			<div class="div_cxtj">
				<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;��ϵ��Ϣ
			</div>
				<div id="baseinfo1" align="center">
					<table width="100%" border="0" class="table2_style">
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
		</td>
	</tr>
	<!-- ��һ������-->
</table>