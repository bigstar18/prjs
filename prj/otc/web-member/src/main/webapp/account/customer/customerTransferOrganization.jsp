<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<script type='text/javascript' src='${basePath}/dwr/engine.js'>
</script>
<script type='text/javascript' src='${basePath}/dwr/util.js'>
</script>
<script type="text/javascript"
	src='${basePath}/dwr/interface/customerAdd.js' />
</script>
<script type="text/javascript"
	src='${basePath}/dwr/interface/checkAction.js' />


</script>
<html>
	<head>
		<title>客户转移机构</title>
	</head>
	<body leftmargin="0" topmargin="0">
		<form name="frm"
			action="${basePath}/broke/customerTransfer/updateCustomerTransfer.action"
			method="post" targetType="hidden">
			<!-- <input type="hidden" value="${obj.customerNo}" name="obj.customerNo" /> -->
			<div class="div_scromin1">
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;&nbsp;客户转移机构
							</div>
							<div id="baseinfo" align="center">
								<table width="90%" border="0" class="st_bor">
									<tr height="21">
										<td align="right" class="td_size">
											客户代码 ：
										</td>
										<td align="left" width="60%">
											<input name="obj.customerNo" type="text"
												class="input_text_pwd" value="${obj.customerNo }"
												readonly="readonly">
										</td>
									</tr>
									<tr height="21">
										<td align="right" class="td_size">
											客户名称 ：
										</td>
										<td align="left" width="60%">
											<input name="obj.name" type="text" class="input_text_pwd"
												value="${obj.name }" readonly="readonly">
										</td>
									</tr>
									<tr height="21">
										<td align="right">
											所属机构:
										</td>
										<td align="left" width="60%">
											<select id="organization" name="obj.organizationNo"
												onchange="organizationChange(this.value);">
												<option value="">
													请选择
												</option>
											</select>
										</td>
									</tr>
									<tr height="21">
										<td align="right">
											居间代码:
										</td>
										<td align="left" width="60%">
											<select id="brokerage" name="obj.brokerageNo">
												<option value="">
													请选择
												</option>
											</select>
										</td>
									</tr>
									<tr height="21">
										<td align="right">
											客户代表代码:
										</td>
										<td align="left" width="60%">
											<select id="manager" name="obj.managerNo">
												<option value="">
													请选择
												</option>
											</select>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table cellspacing="0" cellpadding="0" border="0" width="100%"
					align="center"">
					<tr>
						<td width="265" align="center">
							<button class="btn_sec" id="update" onClick="customerUpdate()">
								保存
							</button>
						</td>

						<td width="265" align="center">
							<button class="btn_sec" onClick="window.close()">
								关闭
							</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>


<script type="text/javascript">
function customerUpdate() {
	var vaild = affirm("您确定要操作吗？");
	if (vaild == true) {
		frm.submit();
	} else {
		return false;
	}
}

if (typeof window['DWRUtil'] == 'undefined') {
	window.DWRUtil = dwr.util;
}
onloadOrganization('${obj.memberNo}');
function onloadOrganization(id) {
	customerAdd.getOrganizationList(id, function(organizationList) {
		if (organizationList == "") {
			return;
		}
		var organization = document.getElementById("organization");
		DWRUtil.removeAllOptions(organization);
		organization.style.width = '7em';
		var item1 = document.createElement("OPTION");
		organization.options.add(item1);
		item1.value = "";
		item1.innerText = "请选择";
		for ( var i = 0; i < organizationList.length; i++) {
			var item = document.createElement("OPTION");
			organization.options.add(item);
			item.value = organizationList[i].organizationNO;
			item.innerText = organizationList[i].name;
			if (organizationList[i].organizationNO == '${obj.organizationNo}') {
				item.selected = 'selected';
			}
		}
	});
	document.getElementById("organization").value = '${obj.organizationNo}';
	organizationChange('${obj.organizationNo}');
}

function organizationChange(id) {
	customerAdd.getBrokerageAndManagerList(id, function(map) {
		if (!map) {
			return;
		}
		var brokerageList = map['brokerageList'];

		var managerList = map['managerList'];

		var brokerage = document.getElementById("brokerage");
		DWRUtil.removeAllOptions(brokerage);
		brokerage.style.width = '7em';
		var item = document.createElement("OPTION");
		brokerage.options.add(item);
		item.value = "";
		item.innerText = "请选择";
		for ( var i = 0; i < brokerageList.length; i++) {
			//DWRUtil.addOptions(brokerage,brokerageList[i].name);
			var item = document.createElement("OPTION");
			brokerage.options.add(item);
			item.value = brokerageList[i].brokerageNo;
			item.innerText = brokerageList[i].brokerageNo;
			if (brokerageList[i].brokerageNo == '${obj.brokerageNo}') {
				item.selected = 'selected';
			}
		}

		var manager = document.getElementById("manager");
		DWRUtil.removeAllOptions(manager);
		manager.style.width = '7em';
		var item = document.createElement("OPTION");
		manager.options.add(item);
		item.value = "";
		item.innerText = "请选择";
		for ( var i = 0; i < managerList.length; i++) {
			//DWRUtil.addOptions(manager,managerList[i].name);
			var item = document.createElement("OPTION");
			manager.options.add(item);
			item.value = managerList[i].managerNo;
			item.innerText = managerList[i].managerNo;
			if (managerList[i].managerNo == '${obj.managerNo}') {
				item.selected = 'selected';
			}
		}

	});
}
</script>
<%@ include file="/public/footInc.jsp"%>