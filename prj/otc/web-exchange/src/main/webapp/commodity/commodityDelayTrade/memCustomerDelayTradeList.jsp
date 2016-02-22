<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/commodity/memCustomerDelayTrade/list.action?sortName=primary.commodityId"
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
															��Ʒ����:&nbsp;
															<label>
																<input type="text" class="input_text"
																	name="${GNNT_}commodity.name[like]" id="commodityName"
																	value="${oldParams['commodity.name[like]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															������Ա:&nbsp;
															<label>
																<input type="text" id="memberInfoName"
																	class="input_text" name="${GNNT_}memberInfo.name[like]"
																	value="${oldParams['memberInfo.name[like]'] }" />
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
											               <button class="btn_sec" onclick="submitMember()">
																��ѯ
														  </button>&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset()">
																����
															</button>
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
							<button class="anniu_btn" onclick="addDelayTradePoint()" id="update">
								���
							</button>
							<button class="anniu_btn" onclick="deleteDelayTrade()">
								����ɾ��
							</button>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="commodityDelayTrade"
											action="${basePath}/commodity/memCustomerDelayTrade/list.action?sortName=primary.commodityId"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv" style="table-layout:fixed">
											<ec:row recordKey="${commodityDelayTrade.f_FirmId}">
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													style="text-align:center; " value="${commodityDelayTrade.f_FirmId},${commodityDelayTrade.commodityId}" width="4%"
													viewsAllowed="html" />
												<ec:column width="4%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="commodity.name[Like]" title="��Ʒ����" width="19%" style="text-align:left;" ellipsis="true" > <a href="#" class="blank_a" onclick="return update('${commodityDelayTrade.f_FirmId}','${commodityDelayTrade.commodityId}');"><font color="#880000">${commodityDelayTrade.commodityName}</font> </a> </ec:column>
												<ec:column property="memberInfo.name[Like]" title="������Ա"
													width="15%" style="text-align:left;" ellipsis="true"
													value="${commodityDelayTrade.memberName}" />
												<ec:column property="primary.delayTradeType[=][int]"
													title="�ӳٳɽ�����" width="18%" style="text-align:left;">
		  											<c:set var="delayTradeKey">
														<c:out value="${commodityDelayTrade.delayTradeType}"></c:out>
													</c:set>
											  		${delayTradeMap[delayTradeKey]}
           										</ec:column>
												<ec:column property="primary.delayTradeTime[=][int]"
													title="�ӳٳɽ�ʱ�䣨���룩" width="18%" style="text-align:right; "
													value="${commodityDelayTrade.delayTradeTime}" filterable="false"/>
												<ec:column property="primary.isslipPoint[like]"
													title="�Ƿ񻬵�" width="18%" style="text-align:right; "  filterable="false">
													<c:set var="isslipPointKey">
														<c:out value="${commodityDelayTrade.isslipPoint}"></c:out>
													</c:set>
		  											${isslipPointMap[isslipPointKey]}
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
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
		
		<SCRIPT type="text/javascript">
		
		function update(f_FirmId,commodityId){
			var url = "${basePath}/commodity/memCustomerDelayTrade/forwardUpdate.action?obj.commodityId="+commodityId+"&obj.f_FirmId="+f_FirmId;
			ecsideDialogDelayTrade(url,"",580,520);
		}
		function submitMember(){
			frm.submit();
		}
		function addDelayTradePoint() {
			var urlFee = "${basePath}/commodity/memCustomerDelayTrade/forwardAdd.action";
			ecsideDialogDelayTrade(urlFee, "", 580, 415);
		}
		function deleteDelayTrade(){
			var collCheck=document.getElementsByName("ids");
			var url = "${basePath}/commodity/memCustomerDelayTrade/delete.action";
			if(isSelNothing(collCheck) == -1)
			{
				alert ( "û�п��Բ��������ݣ�" );
				return false;
			}
			if(isSelNothing(collCheck))
			{
				alert ( "��ѡ����Ҫ���������ݣ�" );
				return false;
			}
			if(confirm("��ȷ��Ҫɾ����"))
			{
				ec.action=url;
				ec.submit();
			}
		}
		function ecsideDialogDelayTrade(url, args, width, height){
			if (!width) width = 600;
			if (!height) height = 400;
			if(checkie()){
				height=height+50;
			}
			if (!args) args='';
			var urlArray=url.split("?");
			if(urlArray.length==1){
				url=url+'?AUsessionId='+AUsessionId+'&d='+new Date();
			}else if(urlArray.length==2){
				url=url+'&AUsessionId='+AUsessionId+'&d='+new Date();
			}
			var result=window.showModalDialog(url, args, "dialogWidth=" + width + "px; dialogHeight=" + height + "px; status=yes;scroll=yes;help=no;");
			if(result>0)
			{
			   submitMember();
			}
		}
		</SCRIPT>
	</body>
</html>