<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>�쳣��¼�����б�</title>
	</head>
	<body onload="getFocus('id');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/user/errorLogonLog/list.action?sortColumns=order+by+userID"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															�û�����:&nbsp;
															<label>
																<input type="text" class="input_text" id="id"
																	name="${GNNT_}primary.userID[allLike]"
																	value="${oldParams['primary.userID[allLike]']}" maxLength="<fmt:message key='traderId_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick=select1();
>
																��ѯ
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick=
	myReset();
>
																����
															</button>
														</td>
														<td class="table3_td_1" align="left">
														&nbsp;
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
						<div class="div_gn">
						<rightButton:rightButton name="����" onclick="activeTrader();"
								className="anniu_btn" action="/user/errorLogonLog/activeTraders.action" id="active"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="ȫ������" onclick="activeAllTrader();"
								className="anniu_btn" action="/user/errorLogonLog/activeAllTraders.action" id="actives"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="user"
											action="${basePath}/user/errorLogonLog/list.action?sortColumns=order+by+userID"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="user.xls" csvFileName="user.csv"
											showPrint="true" listWidth="100%"
											minHeight="345">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													style="text-align:center; " value="${user.userID}"
													width="5%" viewsAllowed="html" />
												<ec:column width="5%" property="2" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="userID" width="20%" title="�û�����"
													style="text-align:left;">
												</ec:column>
												<ec:column property="name" width="20%" title="����Ա����"
													style="text-align:left; ">
												</ec:column>
												<ec:column property="loginDate" title="��¼����" width="20%"
													style="text-align:center;">
													<fmt:formatDate value="${user.loginDate}" pattern="yyyy-MM-dd"/>
												</ec:column>
												<ec:column property="1" width="30%"
													title="չʾ����" style="text-align:center;"  sortable="false"
													filterable="false">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/user/errorLogonLog/getDetail.action" id="detail" text="<font color='#880000'>����</font>" onclick="return detail('${user.userID}');"/>
												</ec:column>
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
		<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="" />
	</textarea>


<SCRIPT type="text/javascript">
	function activeTrader(){
	var a=document.getElementById('active').action;
		var url = "${basePath}"+a+"?autoInc=false";
		updateRMIEcside(ec.ids,url);
	}
	function activeAllTrader(){
	var a=document.getElementById('actives').action;
		var url = "${basePath}"+a+"?autoInc=false";
		deleteAllEcside(ec.ids,url);
	}
	function detail(id){
		var a=document.getElementById("detail").action;
		var url = "${basePath}"+a+"?userId="+id;
		if(showDialog(url, "", 580, 500)){
			frm.submit();
		};
	}
	function select1() {
		frm.submit();
	}
</SCRIPT>
	</body>
</html>