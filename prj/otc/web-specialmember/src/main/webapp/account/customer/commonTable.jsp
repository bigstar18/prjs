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
							<input type="hidden" name="obj.customerNo" id="customerId" value="${obj.customerNo}">
							<td width="85" align="right">
								������Ա:
							</td>
							<td width="135">
								<select name="obj.memberNo" id="memberNo" onmouseover="this.title=this.options[this.selectedIndex].text;" onchange="change()" >
									<option value="">��ѡ��</option>
									<c:forEach items="${memberInfoList}" var="result">
										<option value="${result.id}" <c:if test="${result.id==obj.memberNo}">selected="selected"</c:if>>${result.name}</option>
									</c:forEach>
								</select>
							</td>
							<td width="76" align="right">
								�����˺�:
							</td>
							<td colspan="2">
									<input type="text" id="memberNos" value="${obj.memberNo}" size="6" readonly="readonly" style="background-color: #bebebe"><input type="text" id="shortId" size="12" onblur="checkMemberNo()">
							</td>
						</tr>
						<tr height="35">
							<td align="right">
								�ͻ�����:
							</td>
							<td width="107">
								<input type="text" name="obj.name" id="customerName" value="${obj.name}"
									style="width: 100">
							</td>
						<td align="right">
							֤������:
						</td>
						<td>
							<select name="obj.papersType" id="papersType">
								<option value="">��ѡ��</option>
								<c:forEach items="${papersTypeMap}" var="maps">
							  	<option value="${maps.key}" <c:if test="${obj.papersType==maps.key}">selected="selected"</c:if>>${maps.value}</option>
							  </c:forEach>
							</select>
						</td>
					</tr>
					<tr height="35">
							<td align="right">
								֤������:
							</td>
							<td width="107" colspan="3">
								<input type="text" name="obj.papersName" id="papersName" value="${obj.papersName}"
									style="width: 300">
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
								��������:
							</td>
							<td colspan="3">
								<label>
									<input type="text" name="obj.email" id="email" style="width: 300" value="${ obj.email}">
								</label>
							</td>
						</tr>
						<tr height="35">
							<td align="right">
								�������:
							</td>
							<td>
								<label>
									<input type="text" name="obj.fax" id="fax" style="width: 100" value="${obj.fax}">
								</label>
							</td>
							<td align="right">
								�ʱ�:
							</td>
							<td>
								<input type="text" name="obj.postCode" id="postCode" style="width: 100" value="${obj.postCode}">
							</td>
						</tr>
						<tr height="35">
							<td align="right">
								��ϵ�绰:
							</td>
							<td width="112" colspan="3">
								<input type="text" name="obj.phone" id="phone" style="width: 300" value="${obj.phone}">
							</td>
						</tr>
						
						<tr height="35">
							<td align="right">
								��ַ:
							</td>
							<td colspan="3">
								<input type="text" name="obj.address" id="address" style="width: 300" value="${obj.address}">
							</td>
						</tr>
						
					</table>
				</div>
		</td>
	</tr>
	<!-- ��һ������-->
</table>
<script type="text/javascript">
	var customerId=document.getElementById("customerId").value;
	var memberNo=document.getElementById("memberNos").value;
	var shortId=customerId.substr(memberNo.length);
	document.getElementById("shortId").value=shortId;
	function change(){
		frm.memberNos.value=frm.memberNo.value;
	}
</script>
