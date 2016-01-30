<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>�����̻�����Ϣ</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<SCRIPT type="text/javascript">
		<!-- 

		    $(document).ready(function() {
		    	jQuery("#frm").validationEngine('attach');
				//�޸İ�ťע�����¼�
				$("#update").click(function(){
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validateform')){
						var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							//�����ϢURL
							var updateDemoUrl = $(this).attr("action");
							//ȫ URL ·��
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
							$(this).attr("disabled",true);
						}
					}
				});

				$("#back").click(function(){
					//�����ϢURL
					var updateDemoUrl = $(this).attr("action");
					//ȫ URL ·��
					var url = "${basePath}"+updateDemoUrl+"?sortColumns=order+by+firmID+asc";

					document.location.href = url;
				});

				$("#firmId").attr("readonly", true).css("background-color", "#C0C0C0");
				$("#name").attr("readonly", true).css("background-color", "#C0C0C0");
				$("#fullName").attr("readonly", true).css("background-color", "#C0C0C0");
				$("#email").attr("readonly", true).css("background-color", "#C0C0C0");
				$("#postCode").attr("readonly", true).css("background-color", "#C0C0C0");
				$("#type").val("${entity.type}");
				viewChange();
			});

		function viewChange(){
			if("${entity.type}"==3)
				document.getElementById("viewText").innerText="���֤�� ��";
			else
				document.getElementById("viewText").innerText="��֯�������� ��";
			
		}

		function showTariff() {
			//��ȡ����Ȩ�޵� URL
			var addUrl="/firm/tariff/detailTariffCommodity.action";
			//��ȡ������תURL
			if ($("#firmTID").val() != "none") {
				var url = "${basePath}"+addUrl+"?tariffID="+$("#firmTID").val();

				window.open(url, "", "width=750,height=500");
			}
		}
		//-->
		</SCRIPT>
	</head>
	<body>
		<form id="frm" name="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
			<div class="div_cx">
				<table border="0" width="800" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :�޸Ľ������ײ���Ϣ
									<br/>
									<span class="required">ֻ�����ö����������ײ�</span>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" width="800" align="center">
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												�����̻�����Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												  <tr height="35">
									                <td align="right"> �����̴��� ��</td>
									                <td align="left">
									                	<input class="readonly" id="firmId" name="entity.firmID" value="${entity.firmID }" style="width: 150px;" readonly>&nbsp;<font color="#ff0000">*</font>
									                </td>
									                
									                <td align="right"> �����������ײ� ��
									                <td align="left" >
									                
									                  <select id="firmTID" name="tariffID">
									                    <c:if test="${tariffID == 'none'}">
									                      <option value="none" >��ѡ��</option>
									                    </c:if>
									                    <c:forEach items="${tariffList}" var="result">
							                          		<option value="${result.TARIFFID}" <c:if test="${tariffID == result.TARIFFID}">selected</c:if>>${result.TARIFFNAME }</option>
								                      	</c:forEach>
									                    </select>&nbsp;&nbsp;
									                    <span id="tariffview" name="tariffview" onclick='showTariff()'  style="color:#00008B;text-decoration:underline;cursor: hand;">�鿴�ײ���ϸ��Ϣ</span> 
									                </td>
									              </tr>
									              <tr height="35">
									                <td align="right"> ���������� ��</td>
									                <td align="left">
									                	<input id="name" name="entity.name" type="text" value="<c:out value='${entity.name }'/>" class="text" style="width: 150px;" reqfv="required;�û�����">&nbsp;<font color="#ff0000">*</font>
									                </td>
									      
									            	<td align="right"> ������ȫ�� ��</td>
									                <td align="left">
									                	<input id="fullName" name="entity.fullName" type="text" class="text" style="width: 150px;" value="<c:out value='${entity.fullName}'/>">
									                </td>
									              </tr>
									              <tr height="35">
									            	<td align="right"> EMail ��</td>
									                <td align="left">
									                	<input id="email" name="entity.email" value="<c:out value='${entity.email}'/>"  type="text" class="text" style="width: 150px;">
									                </td>
									            	<td align="right"> �������� ��</td>
									                <td align="left">
									                	<input id="postCode" name="entity.postCode" type="text" value="<c:out value='${entity.postCode}'/>" class="text" style="width: 150px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="6">
									                </td>
									              </tr>
									             <tr height="35">
									           	 	<td align="right"> ���� ��</td>
									                <td align="left">
									               	<select id="type" name="entity.type" class="validate[required] normal" onchange="viewChange();" style="width: 150px" reqfv="required;���">
													<OPTION value=""></OPTION>
													<option value="1">����</option>
													<option value="2">����</option>
									           		<option value="3">����</option>
										    		</select>
										    		&nbsp;<font color="#ff0000">*</font>
									                </td>            	
									            	<td align="right"> 
									                  <div id="viewText" name="viewText">
									                    <c:if test="${entity.type == 3}">
									                                                        ���֤�� ��
									                    </c:if>
									                    <c:if test="${entity.type != 3}">
									                                                         ��֯�������� ��
									                    </c:if>                                  
									                  </div>
									                </td>
									                <td align="left">
									                	<input name="entity.organizationCode" value="<c:out value='${entity.organizationCode}'/>"  type="text" class="text" style="width: 150px;" >
									                	&nbsp;<font color="#ff0000">*</font>
									                </td>
									              </tr>
									              <tr height="35">
									            	<td align="right"> ��&nbsp;ϵ&nbsp;�� ��</td>
									                <td align="left">
									                	<input name="entity.contactMan" value="<c:out value='${entity.contactMan}'/>"  type="text" class="text" style="width: 150px;" >
									                </td>
									            	<td align="right"> �ֻ����� ��</td>
									                <td align="left">
									                	<input name="entity.mobile" value="<c:out value='${entity.mobile}'/>"  type="text" class="text" style="width: 150px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="11">
									                </td>
									              </tr>
									              <tr height="35">
									            	<td align="right"> ��ϵ�绰 ��</td>
									                <td align="left">
									                	<input name="entity.phone" type="text" value="<c:out value='${entity.phone}'/>" class="text" style="width: 150px;" >
									                </td>
									            	<td align="right"> ��&nbsp;&nbsp;&nbsp;&nbsp;�� ��</td>
									                <td align="left">
									                	<input name="entity.fax" type="text" value="<c:out value='${entity.fax}'/>" class="text" style="width: 150px;" >
									                </td>
									              </tr>
									              
									              <tr height="35">
									            	<td align="right">��ϸ��ַ ��</td>
									                <td align="left" colspan="3">
									                	<textarea name="entity.address" cols="64" rows="3"><c:out value='${entity.address}'/></textarea>
									                </td>
									              </tr>
									              <tr height="35">
									              <td align="right"> ��ע ��</td>
									              <td align="left" colspan="3">
									                <textarea name="entity.note" cols="64" rows="3" ><c:out value='${entity.note}'/></textarea>
									              </td>
									              </tr>
									              <tr height="35">
									              	<td colspan="4" align="center">
									                	<rightButton:rightButton name="�ύ" onclick="" className="btn_sec" action="/firm/tariff/updateTariff.action" id="update"></rightButton:rightButton>
														&nbsp;&nbsp;
														<rightButton:rightButton name="����" onclick="" className="btn_sec" action="/firm/tariff/tariffList.action" id="back"></rightButton:rightButton>
									             	</td>
									              </tr>
									          </table>
												
												
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>