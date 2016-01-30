<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
	<head>
		<title>��ӽ��׿ͻ�</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				//��Ӱ�ťע�����¼�
				$("#add").click(function(){
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validate')){
						
					  if(onSave()){
						  
						var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							//�����ϢURL
							var updateUrl = $(this).attr("action");
							//ȫ URL ·��
							var url = "${basePath}"+updateUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
						}
						
					  }
					}
				});
			});

			function onSave(){
				var code = document.getElementById('code').value;
				var startCode = document.getElementById('startCode').value;
				var endCode = document.getElementById('endCode').value;

				if(code == "" && startCode == "" && endCode == ""){

					alert("�����ͻ����벻��Ϊ�գ�");
					code = document.getElementById('code').focus();
					return false;
				}else if(startCode == "" && endCode != "" || startCode != "" && endCode == ""){
					alert("�����ͻ�������ʼ�����Ϊ�գ�");
					return false;
				}
				else if(startCode > endCode ){
					alert("��ʼ���벻�ܴ��ڽ�������");
					return false;
				}

				return true;
			}
		</script>
		
	    <script type="text/javascript">
	      //���������ֺͶ���
	      function suffixNamePress()
	      {
	        if (event.keyCode == 44 || event.keyCode == 13 || (event.keyCode>=48 && event.keyCode<=57) ) 
	        {
	          event.returnValue=true;
	        }
	        else
	        {
	          event.returnValue=false;
	        }
	      }
	      
	      //����������
		  function onlyNumberInput() {
		  	if (event.keyCode>=48 && event.keyCode<=57) {
		  		event.returnValue=true;
		  	} else {
		  		event.returnValue=false;
		  	}
		  }
	    
	    </script>
	</head>
	<body >
		<form id="frm" method="post" action="" targetType="hidden">
			<input type="hidden" name="firmID" value="${firmID}"/>
			
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :��ӽ��׿ͻ�
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" width="100%" align="center">
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												�����ͻ�����ά��
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="10" width="100%" align="center" class="table2_style">
												
												<tr>
													<td align="right">
													<span class="required">*</span>
														״̬��
													</td>
													<td>
														<select id="status" name="status" class="validate[required] input_text" >
														  <option value="">��ѡ��</option>
														    <c:forEach items="${firm_statusMap}" var="map">
					                                          <option value="${map.key}">${map.value}</option>
				                                            </c:forEach>		
														</select>
														
													</td>
													
												</tr>
												<tr>
													<td align="right">
													<span class="required">*</span>
														�����ͻ����룺
													</td>
													<td>
													  
													  <textarea id="code" name="code" rows="3" cols="55"  onkeypress="return suffixNamePress()" style="width:150" /></textarea>
													  (��ʽ���ŷָ�����01,02,99)
													</td>
													
												</tr>
												<tr>
												  <td>&nbsp;</td>
												  <td >
													      ��ʼ&nbsp;&nbsp;<input type="text" id="startCode" name="startCode"  maxlength="2" onkeypress="return onlyNumberInput()" style="ime-mode:disabled;width:20;" class="input_text"  />&nbsp;&nbsp;
													      ����&nbsp;&nbsp;<input type="text" id="endCode" name="endCode" maxlength="2" onkeypress="return onlyNumberInput()" style="ime-mode:disabled;width:20;" class="input_text" />
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
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
					<tr>
						<td align="center">
							<rightButton:rightButton name="���" onclick="" className="btn_sec" action="/timebargain/firmSet/tradePrivilege/addCustomer.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">�ر�</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>