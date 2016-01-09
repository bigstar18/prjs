<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
request.setAttribute("today",new java.util.Date());
%>
<html>
	<head>
		<title>���г������ˮ����</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${basePath }/mgr/app/bank/js/jquery.validationEngine-zh_CN.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript"></script>
		<script>
			jQuery(document).ready(function() {
				$("#subfrm").validationEngine('attach');

				$("#update").click(function(check) {<%//ȡ��������%>
					if ($("#subfrm").validationEngine('validate')) {
						if(affirm("��ȷ��Ҫ������")){
							frmsubmit($(this).attr("action"));
						}
					}
				});

				$("#tradeDayRT").click(function(){<%//����ˮ��ϸ%>
					if ($("#subfrm").validationEngine('validate')) {
						if(affirm("��ȷ��Ҫ������")){
							frmsubmit($(this).attr("action"));
						}
					}
				});
				$("#qs").click(function(){<%//������%>
					if ($("#subfrm").validationEngine('validate')) {
						if(affirm("��ȷ��Ҫ������")){
							frmsubmit($(this).attr("action"));
						}
					}
				});
				$("#dz").click(function(){<%//�ʽ�˶�%>
					if ($("#subfrm").validationEngine('validate')) {
					    if($("#bankID").val()!=17){
						alert('�����в�֧���ʽ�˶�');
						}
						else if(affirm("��ȷ��Ҫ������")){
							frmsubmit($(this).attr("action"));
						}
					}
				});
			});
			function frmsubmit(action){
				$("#subfrm").attr("action","${basePath}"+action);
				$("#subfrm").submit();
			}
		</script>
	</head>
	<body>
		<form id="subfrm" action="" method="post">
			<input type="hidden" id="pageSize" name="pageSize" value="3"/>
		    <table border="0" width="100%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								��ܰ��ʾ :���г������ˮ����
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="div_cxtj">
					    	<div class="div_cxtjL"></div>
					        <div class="div_cxtjC">ִ�в���</div>
					        <div class="div_cxtjR"></div>
		   				</div>
						<div style="clear: both;"></div>
						<div class="div_tj">
							<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
								<tr>
									<td align="right" width="40%">
										ѡ�����У�
									</td>
									<td>
										<select id="bankID" name="bankID" class="validate[required] input_text">
											<option value="">��ѡ��</option>
											<c:forEach var="bank" items="${bankList}">
											<option value="${bank.bankID}" <c:if test="${bankID eq bank.bankID }">selected="selected"</c:if>>${bank.bankName}</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td align="right">
										�������ڣ�
									</td>
									<td>
										<input id="s_time" name="s_time" value="<fmt:formatDate value="${(empty s_time) ? today : s_time}" pattern="yyyy-MM-dd"/>" class="wdate validate[required] datepicker" onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
									</td>
								</tr>
								<tr>
									<td align="right">
										���˽����
									</td>
									<td>
										<font <c:if test="${!(ReturnValue.result>=0)}">color="#FF0000"</c:if><c:if test="${ReturnValue.result>=0}">color="#00FF00"</c:if>>${fn:replace(ReturnValue.info,"\\n"," ")}<c:if test="${empty ReturnValue.info}">${errormessage}</c:if></font>
							 		</td>
								</tr>
								<tr>
									<td align="right">
									</td>
									<td>
										<rightButton:rightButton name="ȡ��������" onclick="" id="update" className="btn_sec" action="/bank/verification/getVerificationFile.action"></rightButton:rightButton>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<rightButton:rightButton name="����ˮ��ϸ" onclick="" id="tradeDayRT" className="btn_sec" action="/bank/verification/getErrorCapitalInfo.action"></rightButton:rightButton>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<rightButton:rightButton name="���������ļ�" onclick="" id="qs" className="btn_sec" action="/bank/verification/sendQsFile.action"></rightButton:rightButton>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<rightButton:rightButton name="�ʽ�˶�" onclick="" id="dz" className="btn_sec" action="/bank/verification/sentDZ.action"></rightButton:rightButton>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<c:if test="${not empty compareResultList}"><%//����ж�����Ϣ������Ϊ��Ҫչʾ%>
				<tr>
					<td>
						<div class="div_cxtj">
					    	<div class="div_cxtjL"></div>
					        <div class="div_cxtjC">��ˮ��ƽ��Ϣ</div>
					        <div class="div_cxtjR"></div>
		   				</div>
						<div style="clear: both;"></div>
						<div class="div_tj">
							<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
								<thead>
								<tr style="background-color: #CCCCCC;">
									<th align="center">������ˮ��</th>
									<th align="center">�г���ˮ��</th>
									<th align="center">�����̴���</th>
									<th align="center">���д���</th>
									<!-- <th align="center">�����ʺ�</th> -->
									<th align="center">��������</th>
									<th align="center">�г�����</th>
									<th align="center">���н��</th>
									<th align="center">�г����</th>
									<th align="center">��������</th>
									<th align="center">��������</th>
								</tr>
								</thead>
								<tbody>
								<c:forEach var="compareResult" items="${compareResultList}">
								<tr>
									<td align="right">${compareResult.id}</td>
									<td align="right">${compareResult.m_Id}</td>
									<td align="center">${compareResult.firmID}</td>
									<td align="center">${compareResult.bankID}</td>
									<!-- <td align="center">${compareResult.account}</td> -->
									<td align="center"><c:if 
										test="${!(compareResult.errorType==3)}">${capitalInfoType[compareResult.type]}</c:if></td>
									<td align="center"><c:if 
										test="${!(compareResult.errorType==2)}">${capitalInfoType[compareResult.m_type]}</c:if></td>
									<td align="right"><fmt:formatNumber value="${compareResult.money}" pattern="#,##0.00" /></td>
									<td align="right"><fmt:formatNumber value="${compareResult.m_money}" pattern="#,##0.00" /></td>
									<td align="center">${compareResultErrorType[compareResult.errorType]}</td>
									<td align="center"><fmt:formatDate value="${compareResult.compareDate}" pattern="yyyy-MM-dd"/></td>
								</tr>
								</c:forEach>
								</tbody>
								<tfoot>
								<tr style="background-color: #EEEEEE;">
									<td colspan="11" align="right"><input type="hidden" id="pageNum" name="pageNum"/>
										<c:if test="${pageNum<=1}">��ҳ</c:if><c:if test="${pageNum>1}"><a href="#" onclick="gotopage(1)">��ҳ</a></c:if>&nbsp;
										<c:if test="${pageNum<=1}">��һҳ</c:if><c:if test="${pageNum>1}"><a href="#" onclick="gotopage(${pageNum-1})">��һҳ</a></c:if>&nbsp;
										��&nbsp;${rowSize}&nbsp;��&nbsp;${page}&nbsp;ҳ����ǰ��&nbsp;${pageNum}ҳ&nbsp;
										<c:if test="${pageNum>=page}">��һҳ</c:if><c:if test="${pageNum<page}"><a href="#" onclick="gotopage(${pageNum+1})">��һҳ</a></c:if>&nbsp;
										<c:if test="${pageNum>=page}">βҳ</c:if><c:if test="${pageNum<page}"><a href="#" onclick="gotopage(${page})">βҳ</a></c:if>&nbsp;
									</td>
								</tr>
								</tfoot>
							</table>
							<script>
								function gotopage(pageNum){
									$("#pageNum").attr("value",pageNum);
									frmsubmit($("#tradeDayRT").attr("action"));
								}
							</script>
						</div>
					</td>
				</tr>
				</c:if>
			</table>
		</form>
	</body>
</html>
