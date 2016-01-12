<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
	    <base target="_self"/>
		<title>修改加盟商信息</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
        <script src="${basePath}/mgr/app/broker/js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				//修改按钮注册点击事件
				$("#update").click(function(){
					//验证信息
					if(jQuery("#frm").validationEngine('validate')){
						var vaild = affirm("您确定要操作吗？");
						if(vaild){
							//添加信息URL
							var updateDemoUrl = $(this).attr("action");
							//全 URL 路径
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
						}
					}
				});
			});

			// 验证邮箱是否正确
//			function validateEmail(obj) {
//				var objValue = obj.value;
//				if(objValue.length==0){ 
//					return false;
//				}
//				var regExp = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
//				if(regExp.test(objValue)){
//					return true;
//				}else{
//					alert("邮箱格式不合法！！");
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
									温馨提示 :加盟商修改
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
												修改加盟商
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
												<div>
											<table border="0" cellspacing="7" cellpadding="2" width="100%" align="center" class="table2_style">
											
											     <tr>
													<td align="right">
														<span class="required"></span>
														加盟商编号：
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
									                   <span class="required"></span> 加盟商帐号 ：
								                   </td>
								                   <td>
									                   ${entity.firmId}
								                  </td>						
							                  </tr>
							                    <input type="hidden" name="entity.firmId" value="${entity.firmId}" />
							<tr>
								<td align="right"  width="20%">
									<span class="required">*</span> 加盟商名称 ：
								</td>
								<td align="left" width="45%">
									<input id="name" style="width: 160px;" name="entity.name"
										type="text" class="validate[required,maxSize[32]] input_text" value="${entity.name}"/>
								</td>
								<td align="left">
									<div class="onfocus">
										不能为空！
									</div>
								</td>
							</tr>
							<tr>
			                  <td align="right"><span class="required">*</span>会员类型：</td>
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
										不能为空！
									</div>
								</td>
							 </tr>
							<tr>
								<td align="right"  width="20%">
									 电话 ：
								</td>
								<td align="left" width="45%">
									<input id="telephone" style="width: 160px;" name="entity.telephone"
										type="text" class="validate[maxSize[20],custom[phone]] input_text" value="${entity.telephone}"/>
								</td>
							</tr>
							<tr>
								<td align="right"  width="20%">
									 手机 ：
								</td>
								<td align="left" width="45%">
									<input id="mobile" style="width: 160px;" name="entity.mobile"
										type="text" class="validate[maxSize[11],custom[mobile]] input_text" value="${entity.mobile}"/>
								</td>
							</tr>
							<tr>
								<td align="right" width="20%">
									电子邮件 ：
								</td>
								<td align="left" width="45%">
									<input id="email" style="width: 160px;" name="entity.email" 
										type="text" class="validate[maxSize[20],custom[email]] input_text" value="${entity.email}"/>
								</td>
							</tr>
							<tr>
								<td align="right" width="20%">
									 地址 ：
								</td>
								<td align="left" width="45%">
									<input id="address" style="width: 160px;" name="entity.address"
										type="text" class="validate[maxSize[32]] input_text" value="${entity.address}"/>
								</td>
							</tr>
							<tr>
								<td align="right" width="20%">
									市场开发人员 ：
								</td>
								<td align="left" width="45%">
									<input id="marketManager" style="width: 160px;" name="entity.marketManager"
										type="text" class="validate[maxSize[32]] input_text" value="${entity.marketManager}"/>
								</td>
							</tr>
							<tr>
								<td align="right" width="20%">
									会籍期限 ：
								</td>
								<td align="left" >
								   <input type="text" class="wdate" id="timeLimit" style="width: 100px;" 
									       name="entity.timeLimit" onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" value="<fmt:formatDate value="${entity.timeLimit}" pattern="yyyy-MM-dd" />"/>										     											       										
								</td>
							</tr>
							<tr>
            	                  <td align="right">区域 ：</td>
                                     <td align="left">
                	                    <select id="areaIds" name="entity.areaIds" class="validate[]" style="width:100px;">
                	                      <option value="">请选择</option>
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
									备注 ：
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
							<rightButton:rightButton name="提交" onclick="" className="btn_sec" action="/broker/brokerManagement/updateBroker.action" id="update"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">关闭</button>
						</td>
					</tr>
				</table>
		</div>
	</form>
</body>