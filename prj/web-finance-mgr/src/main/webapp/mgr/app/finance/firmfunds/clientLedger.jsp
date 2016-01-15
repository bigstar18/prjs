<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.MEBS.common.mgr.common.Page"%>
<%@page import="gnnt.MEBS.finance.mgr.model.ClientLedger"%>
<%@page import="gnnt.MEBS.finance.mgr.model.LedgerField"%>
<%@page import="gnnt.MEBS.finance.mgr.model.FirmBalance"%>
<%@page import="java.util.Set"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   String nowDate = Tools.fmtDate(new Date());
%>
<html>
	<head>
		<title>���������˵�</title>
		<SCRIPT type="text/javascript">
		<!-- 
			//�鿴��Ϣ����
			function fundsDetail(firmId){
				var url = "${basePath}/finance/firmfunds/fundsdetail.action?firmId="+firmId;
				showDialog(url, "", 400, 500);
			}
			//ִ�в�ѯ�б�
			function dolistquery() {
				 var startDate = document.getElementById("beginDate").value;
				 var endDate =  document.getElementById("endDate").value;

				  if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
				  {
					if(startDate == ""){
						alert("��ʼ���ڲ���Ϊ�գ�");
						frm.beginDate.focus();
						return false;
						
					}
					if(endDate == ""){
						alert("�������ڲ���Ϊ�գ�");
						frm.endDate.focus();
						return false;
						
					}
					if ( startDate > '<%=nowDate%>' ) { 
					        alert("��ʼ���ڲ��ܴ��ڵ�������!"); 
					        frm.beginDate.focus();
					        return false; 
					} 
					if ( startDate > endDate ) { 
				        alert("��ʼ���ڲ��ܴ��ڽ�������!"); 
				        return false; 
				    } 
				  }
				frm.submit();
			}
			function load(){
				  if( ${oldParams['primary.b_Date[>=][date]']==null}&& ${oldParams['primary.b_Date[<=][datetime]']==null}){
						frm.beginDate.value=new Date().format("yyyy-MM-dd");
						frm.endDate.value=new Date().format("yyyy-MM-dd");
				  }
			}
		//-->
		</SCRIPT>
	</head>
	<body onload="load();">
	
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm" action="${basePath}/finance/firmfunds/clientLedger.action?sortColumns=order+by+b_Date" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="width: 1000">
													<tr>
													<td class="table3_td_1" align="left" >
															&nbsp;&nbsp;&nbsp;��ʼ����:&nbsp;
															<label>
																<input type="text" style="width: 120px" id="beginDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}primary.b_Date[>=][date]"
																	value='${oldParams["primary.b_Date[>=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1tjcx" align="left">
														&nbsp;&nbsp;&nbsp;��������:&nbsp;
															<label>
																<input type="text" style="width: 120px" id="endDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}primary.b_Date[<=][datetime]"
																	value='${oldParams["primary.b_Date[<=][datetime]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
														�����̴���:&nbsp;
															<label>
																<input type="text" class="validate[maxSize[<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />]] input_text" id="id"  checked="checked" name="${GNNT_}primary.firmId[=][String]" value="${oldParams['primary.firmId[=][String]']}" />
															</label>
														</td>
														<td class="table3_td_1" align="left" >
														&nbsp;&nbsp;ϵͳ����:&nbsp;
															<label>
																<select type="text" class="input_text" id="moduleId"
																	name="moduleId">
																	<option value="">ȫ��</option>
																	<c:forEach var="list" items="${moduleList}">
																		<option value="${list.moduleId}">${list.name }</option>
																	</c:forEach>
																</select>
															</label>
														</td>
														<script>
															document.getElementById("moduleId").value="${moduleId}";
														</script>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick=dolistquery();>��ѯ</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">����</button>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="firmBalance"
											action="${basePath}/finance/firmfunds/clientLedger.action?sortColumns=order+by+b_Date"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="firmBalance.xls" csvFileName="firmBalance.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="b_Date" title="�������� "  style="text-align:center;">
													<fmt:formatDate value="${firmBalance.b_Date}" pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="firmId" title="�����̴���" style="text-align:center;" ellipsis="true"/>
												<ec:column property="lastBalance" title="�ڳ����"  style="text-align:right;" width="120">
													<fmt:formatNumber value="${firmBalance.lastBalance}" pattern="#,##0.00"/>
												</ec:column>
												<c:set var="moduleMoney" value="0"></c:set>
												<c:forEach items="${fieldList}" var="lf">
												<c:set var="flag" value="-1"></c:set>
												<c:if test="${lf.fieldSign==-1}"><c:set var="name" value="-${lf.name}"></c:set></c:if>
												<c:if test="${lf.fieldSign==1}"><c:set var="name" value="+${lf.name}"></c:set></c:if>
									 				<ec:column property="_" title="${name}" style="text-align:right;" width="120">
														<c:forEach items="${firmBalance.clientLedger}" var="cl"> 
															<c:choose> 
																<c:when test="${lf.code == cl.code }">   
													               <fmt:formatNumber value="${cl.value}" pattern="#,##0.00"/>
													               <c:set var="flag" value="0"></c:set>
													               <c:set var="moduleMoney" value="${cl.value*lf.fieldSign+moduleMoney}"></c:set>
													            </c:when>  
													        </c:choose> 
												        </c:forEach> 
												        <c:if test="${flag==-1}"><fmt:formatNumber value="0" pattern="#,##0.00"/></c:if> 
												     </ec:column>
									 			</c:forEach>
									 			<c:if test="${moduleId!=null&&moduleId!=''}">
									 				<ec:column property="_" title="+��������ϵͳ"  style="text-align:right;" width="120">
													<fmt:formatNumber value="${firmBalance.todayBalance-firmBalance.lastBalance-moduleMoney}" pattern="#,##0.00"/>
												</ec:column>
									 			</c:if>
												<ec:column property="todayBalance" title="��ĩ���"  style="text-align:right;" width="120">
													<fmt:formatNumber value="${firmBalance.todayBalance}" pattern="#,##0.00"/>
												</ec:column>
												<c:if test="${moduleId==null||moduleId==''||moduleId=='15'}">
												<ec:column property="lastWarranty" title="�ڳ�������"  style="text-align:right;" width="120">
													<fmt:formatNumber value="${firmBalance.lastWarranty}" pattern="#,##0.00"/>
												</ec:column>
												<ec:column property="todayWarranty" title="��ĩ������" style="text-align:right;" width="120">
													<fmt:formatNumber value="${firmBalance.todayWarranty}" pattern="#,##0.00"/>
										  		</ec:column>
										  		</c:if>
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