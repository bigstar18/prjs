<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
	<head>
		<title>��Ʒ��ѯ</title>
		<SCRIPT type="text/javascript">

		
		
		

			//ִ�в�ѯ�б�
			function dolistquery() {

		         if(frm.isQryHis.checked)
				  {
					  frm.isQryHisHidd.value = "yes";
					 
				  }else{
					  frm.isQryHisHidd.value = "no";
				  }
				  
		        
				
		         frm.submit();
			}

			function updateForward(id) {
				
				var updateUrl="";
				if(frm.isQryHis.checked){
				//��ȡ����Ȩ�޵� URL
				
				 updateUrl = "/timebargain/dataquery/detailCommodityHis.action";
				}else{
				 
				 updateUrl = "/timebargain/dataquery/detailCommodity.action";
				}
				//��ȡ������תURL
				var url = "${basePath}"+updateUrl;
				//�� URL ��Ӳ���
				url += "?commodityID="+id+"&opr=his";
				
				if (frm.isQryHis.checked) {
					var date=document.getElementById("ProcessDate"+id).value;
					url += "&d="+date;
				}
				document.location.href = url;
			}
		</SCRIPT>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm" action="${basePath}/timebargain/dataquery/listQueryCommodity.action" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table4_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														
													   <td class="table3_td_1" align="center">
											               &nbsp;&nbsp;��:
											             <input type="text"  id="year"  style="width: 106px" 
												             name="year"/>					   			
							                           </td>
							                           <td class="table3_td_1" align="center">
											               &nbsp;&nbsp;��:
											             <input type="text"  id="month"  style="width: 106px" 
												             name="month"/>					   			
							                           </td>
							                           <SCRIPT type="text/javascript">
							                              document.getElementById("year").value = '${year}';
							                              document.getElementById("month").value = '${month}';
							                           
							                           </SCRIPT>
							                           

									                 
													
														<td class="table3_td_1" align="center">
														   <input type="checkbox" name="isQryHis" id="isQryHis" <c:if test="${isQryHisHidd=='yes' }">checked</c:if>  class="NormalInput"/><label for="isQryHis" class="hand">����</label>
														   <input type="hidden" id="isQryHisHidd" name="isQryHisHidd" >
														</td>
									         
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
										<ec:table items="pageInfo.result" var="hold"
											action="${basePath}/timebargain/dataquery/listQueryCommodity.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												
												<ec:column property="name" title="��Ʒ����" width="10%" style="text-align:center;">  
												<c:if test="${isHis == 'no'}">
												   <rightHyperlink:rightHyperlink text="${hold.name}" className="blank_a" onclick="return updateForward('${hold.commodityId}');" title="�޸�" action="/timebargain/dataquery/detailCommodity.action" />
							  		               <%-- <a href="#" class="blank_a" onclick="return updateForward('${hold.commodityId}');" title="�޸�"><c:out value="${hold.name}"/></a> --%>
				           		                </c:if>
				           		                <c:if test="${isHis != 'no'}">
				           			                <%-- <a href="#" class="blank_a" onclick="return updateForward('${hold.commodityId}', '<fmt:formatDate pattern="yyyy-MM-dd" value="${hold.settleProcessDate }" />');" title="�޸�"><c:out value="${hold.name}"/></a> --%>
				           			                <input id="ProcessDate${hold.commodityId}" type="hidden" value="<fmt:formatDate value="${hold.settleProcessDate }" pattern="yyyy-MM-dd" />"/>
				           			                <rightHyperlink:rightHyperlink text="${hold.name}" className="blank_a" onclick="return updateForward('${hold.commodityId}')" title="�޸�" action="/timebargain/dataquery/detailCommodityHis.action" />
				           		                </c:if>      
												</ec:column>
												<ec:column property="commodityId" title="��Ʒ����" width="10%" style="text-align:center;"/>
												<c:if test="${isHis == 'no'}">
												   <ec:column property="status" title="״̬" width="10%" style="text-align:center;">
												        ${statusMap[hold.status]}
												   </ec:column>
												</c:if>
												<ec:column property="marginRate_B" title="��֤��ϵ��" width="10%" style="text-align:center;">
												    <c:if test="${hold.marginAlgr =='1'}">
												     
												      <fmt:formatNumber value="${hold.marginRate_B*100 }"/>%
												    </c:if>
												    <c:if test="${hold.marginAlgr =='2'}">
												     
												      <fmt:formatNumber value="${hold.marginRate_B}" pattern="#,##0.00"/>
												    </c:if>
												</ec:column>
												<ec:column property="marginRate_S" title="����֤��ϵ��" width="10%" style="text-align:center;">
												    <c:if test="${hold.marginAlgr =='1'}">
												     
												      <fmt:formatNumber value="${hold.marginRate_S*100 }"/>%
												    </c:if>
												    <c:if test="${hold.marginAlgr =='2'}">
												     
												      <fmt:formatNumber value="${hold.marginRate_S}" pattern="#,##0.00"/>
												    </c:if>
												</ec:column>
												<ec:column property="marketDate" title="��������" width="10%" style="text-align:center;">
												        <fmt:formatDate value="${hold.marketDate }" pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="settleDate" title="�������" width="10%" style="text-align:center;">
												        <fmt:formatDate value="${hold.settleDate }" pattern="yyyy-MM-dd" />
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
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>