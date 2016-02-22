<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>�����б�</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/broke/organization/list.action"
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
														��������:&nbsp;
														<label>
															<input type="text" id="parentNo"
																name="${GNNT_}primary.name[like]"
																value="${oldParams['primary.name[like]'] }"
																class="input_text" />
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="select1()">��ѯ</button>&nbsp;&nbsp;
														<button class="btn_cz" onclick="myReset()">����</button>
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
							<button class="anniu_btn" onclick="addOrganization()" id="add">���</button>
							&nbsp;&nbsp;
							<!-- 
							<button  class="anniu_btn" onclick="deleteOrganization()" id="delete">ɾ��</button>
							 -->
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}" var="organization"
											action="${basePath}/broke/organization/list.action"
											title="" minHeight="330" listWidth="100%" height="460"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv"   style="table-layout:fixed">

											<ec:row recordKey="${organization.organizationNO}">
											<%-- 
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													style="text-align:center; " value="${organization.organizationNO}" width="4%" viewsAllowed="html" />
											--%>
												<ec:column width="4%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
											
												<ec:column property="primary.organizationNO[like]" width="10%"
													title="��������" style="text-align:left; ">
													<a href="#" class="blank_a"
														onclick="forwardUpdate('${organization.organizationNO}');"><font
														color="#880000">${organization.organizationNO }</font>
													</a>
												</ec:column>
												
												<ec:column property="primary.name[Like]" width="11%"
													title="��������" style="text-align:left;overflow:hidden;text-overflow:ellipsis" tipTitle="${organization.name}">
													<a href="#" class="blank_a"
														onclick="forwardUpdate('${organization.organizationNO}');"><font
														color="#880000">${organization.name}</font>
													</a>
												</ec:column>
												<ec:column property="primary.parentOrganization.name[like]" width="10%"
													title="�ϼ���������" style="text-align:left;"  value="${organization.parentOrganization.name}" tipTitle="${organization.parentOrganization.name}"  sortable="false">
												</ec:column>
												<ec:column property="primary.telephone[like]" width="9%"
													title="��ϵ�绰" style="text-align:left;"
													value="${organization.telephone}" />
												<ec:column property="primary.mobile[like]" width="11%"
													title="�ֻ�" style="text-align:left;"
													value="${organization.mobile}" />
												<ec:column property="primary.email[like]" width="11%"
													title="��������" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${organization.email}" tipTitle="${organization.email}"/>
												<ec:column property="primary.address[like]" width="11%"
													title="ͨѶ��ַ" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${organization.address}" tipTitle="${organization.address}"/>
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
		function forwardUpdate(id){
			var url="${basePath}/broke/organization/forwardUpdate.action?obj.organizationNO="+id;
			ecsideDialog(url,"",580,422);
		}
		function forwardUpdatePW(id){
			var url="${basePath}/broke/organization/forwardUpdatePassward.action?obj.organizationNO="+id;
			ecsideDialog(url,"",580,280);
		}
		
		function forwardUpdateBrokerage(id){
			var url="${basePath}/broke/organization/forwardUpdateCotactBroke.action?obj.organizationNO="+id;
			ecsideDialog(url,"",580,422);
		}
		
		function addOrganization(){
			var url="${basePath}/broke/organization/forwardAdd.action";
			ecsideDialog(url,"",580,422);
		}
		function select1(){
			
			frm.submit();
		}
		
	function deleteOrganization(){
	      var url="${basePath}/broke/organization/delete.action";
		  var collCheck=document.getElementsByName("ids");
		  for(var i=0;i < collCheck.length;i++ )
			{ 
				if( collCheck[i].checked == true)
				{
					var id=collCheck[i].value;
					if(id==ORGANIZATIONID){
						alert('�����б�ɾ�����Ϸ���');
						return false;
					}
				}
			}
			deleteEcside(ec.ids,url);
		}
		</SCRIPT>
	</body>
</html>