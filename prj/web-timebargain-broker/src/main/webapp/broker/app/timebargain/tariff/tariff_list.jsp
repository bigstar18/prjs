<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
	    <link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
	<title>default</title>
		<script type="text/javascript">

		jQuery(document).ready(function() {
			jQuery("#frm").validationEngine('attach', {promptPosition : "bottomRight"});
		});
		
		function dolistquery() {
			if(jQuery("#frm").validationEngine('validateform')){
				frm.submit();
			}
		}

		function modClick(v, t) {
			//��ȡ����Ȩ�޵� URL
			var detailUrl = "${basePath}/firm/tariff/detailTariff.action?entity.firmID=";
			//��ȡ������תURL
			var url = detailUrl + v + "&tariffID=" + t;

			document.location.href = url;
		}
		
		</script>
</head>
<body leftmargin="2" topmargin="0">
<div id="main_body">
	<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<div class="div_cx">
					<form id="frm" name="frm" action="${basePath}/firm/tariff/tariffList.action?sortColumns=order+by+firmID+asc" method="post">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
							<tr>
								<td class="table5_td_width">
									<div class="div2_top">
										<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="margin-top: 5px">
											<tr>
												<td align="right">
														�����̴��룺
												</td>
												<td>
													<input type="text" id="firmID" name="${GNNT_}primary.FirmID[=]" style="width:111;ime-mode:disabled" maxlength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />" title=""
														class="validate[onlyLetterNumber] input_text" value="${oldParams['primary.FirmID[=]']}"/>
												</td>
						                        <td align="right">
														���������ƣ�
												</td>
												<td>
													<input type="text" id="commodityID" name="${GNNT_}primary.name[=]"  style="width:111;ime-mode:disabled" maxlength="24"
														class="validate[onlyLetterNumber] input_text" value="${oldParams['primary.name[=]']}"/>
												</td>
						                        <td align="right"><label>�����������ײͣ�</label></td>
						                        <td>
							                        <select id="BS_Flag" name="${GNNT_}primary.tariffID[=]" style="width:111">
							                          <option value="">ȫ��</option>
							                          <c:forEach items="${tariffList}" var="result">
							                          <option value="${result.TARIFFID}" <c:if test="${oldParams['primary.tariffID[=]'] == result.TARIFFID}">selected</c:if>>${result.TARIFFNAME }</option>
								                      </c:forEach>
						                            </select>
						                        </td>		
						                        <td class="table3_td_1" align="left">
													<button class="btn_sec" id="view" onclick=dolistquery();>��ѯ</button>	
												    &nbsp;&nbsp;
												    <button class="btn_cz" onclick="myReset();">����</button>
												</td>																		
											</tr>
											<!-- 
											<tr>
												<td>&nbsp;</td>
												<td align="center">
													    <input type="checkbox" name="isQryHis" id="isQryHis" onclick="isQryHis_onclick()" value="${isQryHis}" <c:if test="${isQryHis == 'true' }">checked</c:if> class="NormalInput"/><label for="isQryHis" class="hand">����ʷ</label>
													    <input type="hidden" id="opr" name="opr" value="T_DelayTrade">
												</td>
												<td align="right">
													��ʼ���ڣ�
												</td>
												<td>
												<input type="text" style="width: 80px" id="beginDate"
													class="validate[required] wdate" maxlength="10"
													name="beginDate" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${beginDate}" />"
													onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td align="right">
													�������ڣ�
												</td>
												<td>
												<input type="text" style="width: 80px" id="endDate"
													class="validate[required] wdate" maxlength="10"
													name="endDate" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${endDate}" />"
													onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td></td>

																											
											</tr> -->
										</table>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<br />
				<div class="div_list">
					<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td>
								<ec:table items="pageInfo.result" var="unauditedFirm"
									action="${basePath}/firm/tariff/tariffList.action?sortColumns=order+by+firmID+asc"											
									autoIncludeParameters="${empty param.autoInc}"
									xlsFileName="�����б�.xls" csvFileName="�����б�.csv"
									showPrint="true" listWidth="100%"
									minHeight="345"  style="table-layout:fixed;">
									<ec:row>
										<%--<ec:column cell="checkbox" headerCell="checkbox" width="4%" alias="ids" value="${unauditedFirm.FIRMID }"  viewsAllowed="html" style="text-align:center;"/>--%>
										<ec:column property="FIRMID" title="�����̴���" width="12%" style="text-align:center;">
											<span style="text-decoration: underline;cursor: hand;" onclick="modClick('${unauditedFirm.FIRMID }','${unauditedFirm.TARIFFID}')"><c:out value="${unauditedFirm.FIRMID }"/></span>
										</ec:column>
										<ec:column property="NAME" title="����������" width="12%" style="text-align:center;" value="${unauditedFirm.NAME }"/>
										<ec:column property="TARIFFNAME" title="�����������ײ�" width="12%" style="text-align:center;" value="${unauditedFirm.TARIFFNAME }"/>
										<ec:column property="CONTACTMAN" title="��ϵ��" width="12%" style="text-align:center;" value="${unauditedFirm.CONTACTMAN }"/>
										<%--<ec:column property="expiryDate" title="��������" width="12%" style="text-align:center;" value="${unauditedFirm.expiryDate }"/>--%>
										<ec:column property="CREATETIME" title="����ʱ��" width="12%" style="text-align:center;">
											<fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${unauditedFirm.CREATETIME }"/>
										</ec:column>
										<ec:column property="TYPE" title="����" width="12%" style="text-align:center;">			
											 <c:choose>
											 	<c:when test="${unauditedFirm.TYPE==1 }">����</c:when>
											 	<c:when test="${unauditedFirm.TYPE==2 }">����</c:when>
											 	<c:when test="${unauditedFirm.TYPE==3 }">����</c:when>
											 </c:choose>
										</ec:column>
										<ec:column property="STATUS" title="״̬" width="12%" style="text-align:center;">
											 ${unauditedFirm_StatusMap[unauditedFirm.STATUS]}
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
</div>

</body>
</html>
