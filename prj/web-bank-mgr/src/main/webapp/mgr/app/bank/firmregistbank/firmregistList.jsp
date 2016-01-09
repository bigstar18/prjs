<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.MEBS.common.mgr.statictools.ApplicationContextInit"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
try{
request.setAttribute("needlessAccountBank",ApplicationContextInit.getConfig("needlessAccountBank"));
request.setAttribute("defaultAccount",ApplicationContextInit.getConfig("defaultAccount"));
}catch(Exception e){}
%>
<html>
	<head>
		<title>������ǩԼ����</title>
		<script type="text/javascript">
			function registBank(btn){<%//ע���ʺ���ת%>
				var url = "${basePath}"+btn.action+"?firmID=${firmID}";
				if(showDialog(url, "", 650, 530)){
					frm.submit();
				};
			}
			function update(id){<%//�޸Ľ����������е�ע����Ϣ%>
				var url = "${basePath}/bank/firmregistbank/updateRegistForward.action?"+"entity.firm.firmID=${firmID}&entity.bank.bankID="+id;
				if(showDialog(url, "", 650, 530)){
					frm.submit();
				};
			}
			function doRegistWork(btn){<%//ִ����ع���%>
				var url="${basePath}"+btn.action+"?firmID=${firmID}&autoInc=false";
				updateRMIEcside(ec.ids,url);
			}
			function resetsmmy(btn){<%//���ý���������%>
				document.getElementById("subFirm").action='${basePath}'+btn.action;
				document.getElementById("subFirm").submit();
			}
		</script>
	</head>
	<body>
		<form action="${basePath}/bank/firmregistbank/firmIDRegistList.action?sortColumns=order+by+bank.bankID" method="post" name="frm" id="frm">
			<input name="firmID" type="hidden" value="${firmID}"/>
		</form>
		<form action="" id="subFirm" name="subFirm" method="post">
			<input name="firmID" type="hidden" value="${firmID}"/>
		</form>
		<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div class="div_gn">
						<rightButton:rightButton name="ע���ʺ�" onclick="registBank(this);" className="anniu_btn" action="/bank/firmregistbank/addRegistForward.action" id="addRegistForward"></rightButton:rightButton>&nbsp;&nbsp;
						<rightButton:rightButton name="ͬ���˺�" onclick="doRegistWork(this);" className="anniu_btn" action="/bank/firmregistbank/synchroRegist.action" id="synchroRegist"></rightButton:rightButton>&nbsp;&nbsp;
						<rightButton:rightButton name="ǩԼ�˺�" onclick="doRegistWork(this);" className="anniu_btn" action="/bank/firmregistbank/openRegist.action" id="openRegist"></rightButton:rightButton>&nbsp;&nbsp;
						<rightButton:rightButton name="ע���ʺ�" onclick="doRegistWork(this);" className="anniu_btn" action="/bank/firmregistbank/delRegist.action" id="delRegist"></rightButton:rightButton>&nbsp;&nbsp;
						<rightButton:rightButton name="�����ʺ�" onclick="doRegistWork(this);" className="anniu_btn" action="/bank/firmregistbank/forbidRegist.action" id="forbidRegist"></rightButton:rightButton>&nbsp;&nbsp;
						<rightButton:rightButton name="�ָ��ʺ�" onclick="doRegistWork(this);" className="anniu_btn" action="/bank/firmregistbank/recoverRegist.action" id="recoverRegist"></rightButton:rightButton>&nbsp;&nbsp;
						<rightButton:rightButton name="��������" onclick="resetsmmy(this);" className="anniu_btn" action="/bank/firmregistbank/resetsmmy.action" id="resetsmmy"></rightButton:rightButton>&nbsp;&nbsp;
					</div>
					<div class="div_list">
						<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td>
									<ec:table items="pageInfo.result" var="regist"
										action="${basePath}/bank/firmregistbank/firmIDRegistList.action?firmID=${firmID}"											
										autoIncludeParameters="${empty param.autoInc}"
										xlsFileName="regist.xls" csvFileName="regist.csv"
										showPrint="true" listWidth="100%"
										minHeight="345"  style="table-layout:fixed;">
										<ec:row>
											<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${regist.bank.bankID}" width="5%" viewsAllowed="html" />
											<ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:right;"/>
											<ec:column property="bank.bankID" width="10%" title="���д���" style="text-align:center;"><rightHyperlink:rightHyperlink href="#" className="blank_a" onclick="return update('${regist.bank.bankID}');" text="<font color='#880000'>${regist.bank.bankID}</font>" action="/bank/firmregistbank/updateRegistForward.action">
											</rightHyperlink:rightHyperlink></ec:column>
											<ec:column property="property" title="�����˺�" width="10%" style="text-align:center;"  sortable="false"><c:if 
												test="${!((defaultAccount eq regist.account) and (fn:indexOf(needlessAccountBank,regist.bank.bankID)>=0))}">${regist.account}</c:if></ec:column>
											<ec:column property="account1" title="�����ڲ��˺�" width="10%" style="text-align:center;" sortable="false"/>
											<ec:column property="accountName" title="�˻���" width="10%" style="text-align:center;" sortable="false"/>
											<ec:column property="bankName" title="����������" width="10%" style="text-align:center;" sortable="false"/>
											<ec:column property="bankProvince" title="������ʡ��" width="10%" style="text-align:center;" sortable="false"/>
											<ec:column property="bankCity" title="������������" width="10%" style="text-align:center;" sortable="false"/>
											<ec:column property="isOpen" title="ǩԼ״̬" width="10%" style="text-align:center;" value="${firmIDAndAccountIsOpen[regist.isOpen]}"/>
											<ec:column property="status" title="�ʺ�״̬" width="10%" style="text-align:center;" value="${firmIDAndAccountStatus[regist.status]}"/>
											<ec:column property="frozenFuns" title="�����ʽ�" width="10%" style="text-align:right;"><fmt:formatNumber 
													value="${regist.frozenFuns}" pattern="#,##0.00" /></ec:column>
										</ec:row>
									</ec:table>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>