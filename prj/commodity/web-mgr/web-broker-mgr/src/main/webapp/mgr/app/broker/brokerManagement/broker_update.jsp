<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
	    <base target="_self"/>
		<title>�޸ļ�������Ϣ</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
        <script src="${basePath}/mgr/app/broker/js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				//�޸İ�ťע�����¼�
				$("#update").click(function(){
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validate')){
						var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							//�����ϢURL
							var updateDemoUrl = $(this).attr("action");
							//ȫ URL ·��
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
						}
					}
				});
			});

			// ��֤�����Ƿ���ȷ
//			function validateEmail(obj) {
//				var objValue = obj.value;
//				if(objValue.length==0){ 
//					return false;
//				}
//				var regExp = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
//				if(regExp.test(objValue)){
//					return true;
//				}else{
//					alert("�����ʽ���Ϸ�����");
//					obj.value="";
//					obj.focus();
//					return false;
//				}
//			}
						
		</script>
	</head>
<body>
		<form id="frm" method="post" >
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :�������޸�
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" width="100%" align="center" cellpadding="10" >
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												�޸ļ�����
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
												<div>
											<table border="0" cellspacing="7" cellpadding="2" width="100%" align="center" class="table2_style">
											
											     <tr>
													<td align="right">
														<span class="required"></span>
														�����̱�ţ�
													</td>
													<td>
													 ${ entity.brokerId}												   
													</td>
													<input type="hidden" id="password" name="entity.password" value="${entity.password }" />
													<input type="hidden" id="modifyTime" name="entity.modifyTime" value="${entity.modifyTime }" /
											     </tr>
												 <input type="hidden" name="entity.brokerId" value="${ entity.brokerId}" />
												<tr>
								                   <td align="right">
									                   <span class="required"></span> �������ʺ� ��
								                   </td>
								                   <td>
									                   ${entity.firmId}
								                  </td>						
							                  </tr>
							                    <input type="hidden" name="entity.firmId" value="${entity.firmId}" />
							<tr>
								<td align="right"  width="20%">
									<span class="required">*</span> ���������� ��
								</td>
								<td align="left" width="45%">
									<input id="name" style="width: 160px;" name="entity.name"
										type="text" class="validate[required,maxSize[32]] input_text" value="${entity.name}"/>
								</td>
								<td align="left">
									<div class="onfocus">
										����Ϊ�գ�
									</div>
								</td>
							</tr>
							<tr>
			                  <td align="right"><span class="required">*</span>��Ա���ͣ�</td>
								 <td align="left">											
										<select id="borkerType" name="entity.borkerType"  class="validate[required]" style="width:100px;">
											<option value=""></option>
											<%--
												<c:forEach items="${memberTypeMap}" var="map">
														<option value="${map.key}">${map.value}</option>
												</c:forEach>	
												--%>
												
												<c:forEach items="${brokerTypeList}" var="result">
														<option value="${result.borkerType}" <c:if test="${entity.borkerType == result.borkerType}">selected</c:if>>${result.brokerName}</option>
												</c:forEach>																
										</select>	
										<%--
										<script >
										   document.getElementById("memberType").value="${entity.memberType}";
					  					</script>
					  					--%>															 
							  </td>
							  <td align="left">
									<div class="onfocus">
										����Ϊ�գ�
									</div>
								</td>
							 </tr>
							<tr>
								<td align="right"  width="20%">
									 �绰 ��
								</td>
								<td align="left" width="45%">
									<input id="telephone" style="width: 160px;" name="entity.telephone"
										type="text" class="validate[maxSize[20],custom[phone]] input_text" value="${entity.telephone}"/>
								</td>
							</tr>
							<tr>
								<td align="right"  width="20%">
									 �ֻ� ��
								</td>
								<td align="left" width="45%">
									<input id="mobile" style="width: 160px;" name="entity.mobile"
										type="text" class="validate[maxSize[11],custom[mobile]] input_text" value="${entity.mobile}"/>
								</td>
							</tr>
							<tr>
								<td align="right" width="20%">
									�����ʼ� ��
								</td>
								<td align="left" width="45%">
									<input id="email" style="width: 160px;" name="entity.email" 
										type="text" class="validate[maxSize[20],custom[email]] input_text" value="${entity.email}"/>
								</td>
							</tr>
							<tr>
								<td align="right" width="20%">
									 ��ַ ��
								</td>
								<td align="left" width="45%">
									<input id="address" style="width: 160px;" name="entity.address"
										type="text" class="validate[maxSize[32]] input_text" value="${entity.address}"/>
								</td>
							</tr>
							<tr>
								<td align="right" width="20%">
									�г�������Ա ��
								</td>
								<td align="left" width="45%">
									<input id="marketManager" style="width: 160px;" name="entity.marketManager"
										type="text" class="validate[maxSize[32]] input_text" value="${entity.marketManager}"/>
								</td>
							</tr>
							<tr>
								<td align="right" width="20%">
									�Ἦ���� ��
								</td>
								<td align="left" >
								   <input type="text" class="wdate" id="timeLimit" style="width: 100px;" 
									       name="entity.timeLimit" onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" value="<fmt:formatDate value="${entity.timeLimit}" pattern="yyyy-MM-dd" />"/>										     											       										
								</td>
							</tr>
							<tr>
            	                  <td align="right">���� ��</td>
                                     <td align="left">
                	                    <select id="areaIds" name="entity.areaIds" class="validate[]" style="width:100px;">
                	                      <option value="">��ѡ��</option>
                	                       <c:forEach items="${brokerAreaList}" var="result">
	                                         <option value="${result.areaId}">${result.name}</option>
		                                   </c:forEach>
                	                    </select>
                	                    <script >
										   document.getElementById("areaIds").value="${entity.areaIds}";
					  					</script>
                                   </td>                                  
			                </tr>
			                
							<tr>
								<td align="right" >
									��ע ��
								</td>
								<td align="left" width="45%">
									<textarea id="note" name="entity.note" cols="50"
										rows="4" class="validate"></textarea>
									   <script >
										   document.getElementById("note").value="${entity.note}";
					  					</script>
								</td>
								<td>
									&nbsp;
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
		 <div class="tab_pad" >
				<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
					<tr>
						<td align="center">
							<rightButton:rightButton name="�ύ" onclick="" className="btn_sec" action="/broker/brokerManagement/updateBroker.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">�ر�</button>
						</td>
					</tr>
				</table>
		</div>
	</form>
</body>