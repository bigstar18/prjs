<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>

<%
	BankDAO dao = BankDAOFactory.getDAO();
	Vector bankList = dao.getBankList(" and validFlag=0 ");
	String bankID = request.getParameter("bankID");
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
	<script language="javascript" src="<%=request.getContextPath()%>/bankInterface/lib/tools.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/bankInterface/lib/validate.js"></script>
    <title>����������ҳ��</title>
  </head>
  
  <body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<!-- <div class="div_cxtjd">
							<img src="<%//=skinPath%>/cssimg/13.gif" />
							&nbsp;�������㹦��
						</div> -->
						<div class="div_tj">
							<form id="frm" action="" method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td height="60">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td" align="right">
															��������:&nbsp;
														</td>
														<td class="table3_td2">
															<label>
																<select name="bankID" class="normal" style="width:120px"  onchange="gotoBankQS();">
																	<OPTION value="-1">��ѡ��</OPTION>
															   <%
																for(int i=0;i<bankList.size();i++) {
																	BankValue bv = (BankValue)bankList.get(i);
																	if(sendQSBank(bv.bankID) != 0){
																	%>
																	<option value="<%=bv.bankID%>" <%if(bv.bankID.equals(bankID)) out.print("selected");%>><%=bv.bankName%></option>
																	<%
																	}
																}
																%>
																</select>
															</label>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
						<div class="div_tj">
							<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
								<tr>
									<td class="table3_td" align="centor">
										<font color="ret">��������ģ�����������㹦�ܣ�����ʾģ�����е�������Ϣ�����в���</font>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
//-->
</SCRIPT>