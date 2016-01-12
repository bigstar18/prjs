<%@ page contentType="text/html;charset=GBK" trimDirectiveWhitespaces="true"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<script src="<%=basePath%>/mgr/public/js/jquery-1.6.min.js"></script>
<script type="text/javascript">
<!--
jQuery(document).ready(function() {
	if($("#div${module.moduleId}mo input[checked='true']").length>0){
		$("#r${module.moduleId}").attr('checked',true);
	}
});
//-->
</script>
<div id="modulebase">
<!-- 单模块div -->
<div class="modulediv" id="div${module.moduleId}mo" name="modulediv">
<!-- 模块编号 -->
<div>
	<input class="boxcla" type="checkbox" id="r${module.moduleId}" onclick="uncheckchild(this.checked,'div${module.moduleId}mo');"/>
	<label onclick="r${module.moduleId}.click();" class="modulelab">${module.shortName}权限</label>
	<div style="display: none;">
	<input name="chmoduleIds" id="module${module.moduleId}" value="${module.moduleId}"/>
	</div>
</div>
	<c:forEach var="fright" items="${frightList}">
	<!-- 一级菜单 div -->
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	 
		
	 <!-- 对经济会员的判断 -->
	 
		<c:if test="${fn:length(fright.childRightSet)>0}">
		<c:if test="${brotype != 1 and brotype != 2 && fright.name != '承销会员端' && fright.name != '发行会员端'}"> 
		<div id="div${fright.id}" style="margin-left: 20;">
		<input class="boxcla" type="checkbox" alt="r${module.moduleId}" name="ck" id="r${fright.id}" value="${fright.id}" onclick="uncheckchild(this.checked,'ch${fright.id}div');checkedfather(this.checked,this.alt);" <c:if test="${brokerRightMap[fright.id]}">checked="true"</c:if>/>
		<label onclick="r${fright.id}.click();" class="fright"><strong>${fright.name}</strong></label>
		
		<label onclick="rightdivClick('${fright.id}')" id="lab${fright.id}" style="color: #0000FF;"><img src='${skinPath}/image/app/broker/del.gif'/></label>
		
		  
		<div id="ch${fright.id}div" style="margin-left: 20;">
		
			<c:forEach var="sright" items="${fright.childRightSet}">
			<div id="div${sright.id}">
				
				<input class="boxcla" type="checkbox" alt="r${fright.id}" name="ck" id="r${sright.id}" value="${sright.id}" onclick="uncheckchild(this.checked,'ch${sright.id}div');checkedfather(this.checked,this.alt);" <c:if test="${brokerRightMap[sright.id]}">checked="true"</c:if>/>
				<label onclick="r${sright.id}.click();"  class="sright">${sright.name}</label>
				<c:if test="${fn:length(sright.childRightSet)>0}">
				<label onclick="rightdivClick('${sright.id}')" id="lab${sright.id}" style="color: #0000FF;"><img src='${skinPath}/image/app/broker/del.gif'/></label>
				</c:if>
				<!-- 三级菜单 div -->
				<div id="ch${sright.id}div" style="margin-left: 20;">
					<c:forEach var="tright" items="${sright.childRightSet}">
						<div id="div${tright.id}">
							<input class="boxcla" type="checkbox" alt="r${sright.id}" name="ck" id="r${tright.id}" value="${tright.id}" onclick="uncheckchild(this.checked,'ch${tright.id}div');checkedfather(this.checked,this.alt);" <c:if test="${brokerRightMap[tright.id]}">checked="true"</c:if>/>
							<label onclick="r${tright.id}.click();" class="tright">${tright.name}</label>
							<c:if test="${fn:length(tright.childRightSet)>0}">
							<label onclick="rightdivClick('${tright.id}')" id="lab${tright.id}" style="color: #0000FF;"><img src='${skinPath}/image/app/broker/del.gif'/></label>
							</c:if>
							<!-- 一级按钮 div -->
							<div id="ch${tright.id}div" style="margin-left: 20;">
								<c:forEach var="fbright" items="${tright.childRightSet}">
									<div id="div${fbright.id}">
										<input class="boxcla" type="checkbox" alt="r${tright.id}" name="ck" id="r${fbright.id}" value="${fbright.id}" onclick="uncheckchild(this.checked,'ch${fbright.id}div');checkedfather(this.checked,this.alt);" <c:if test="${brokerRightMap[fbright.id]}">checked="true"</c:if>/>
										<label onclick="r${fbright.id}.click();" class="fbright">${fbright.name}</label>
										<c:if test="${fn:length(fbright.childRightSet)>0}">
										<label onclick="rightdivClick('${fbright.id}')" id="lab${fbright.id}" style="color: #0000FF;"><img src='${skinPath}/image/app/broker/del.gif'/></label>
										</c:if>
										<!-- 二级按钮 div -->
										<div id="ch${fbright.id}div" style="margin-left: 20;">
											<c:forEach var="sbright" items="${fbright.childRightSet}">
												<div id="div${sbright.id}">
													<input class="boxcla" type="checkbox" alt="r${fbright.id}" name="ck" id="r${sbright.id}" value="${sbright.id}" onclick="uncheckchild(this.checked,'ch${sbright.id}div');checkedfather(this.checked,this.alt);" <c:if test="${brokerRightMap[sbright.id]}">checked="true"</c:if>/>
													<label onclick="r${sbright.id}.click();" class="sbright">${sbright.name}</label>
													<c:if test="${fn:length(sbright.childRightSet)>0}">
													<label onclick="rightdivClick('${sbright.id}')" id="lab${sbright.id}" style="color: #0000FF;"><img src='${skinPath}/image/app/broker/del.gif'/></label>
													</c:if>
													<!-- 三级按钮 div -->
													<div id="ch${sbright.id}div" style="margin-left: 20;">
														<c:forEach var="tbright" items="${sbright.childRightSet}">
															<div id="div${tbright.id}">
																<input class="boxcla" type="checkbox" alt="r${sbright.id}" name="ck" id="r${tbright.id}" value="${tbright.id}" onclick="checkedfather(this.checked,this.alt);" <c:if test="${brokerRightMap[tbright.id]}">checked="true"</c:if>/>
																<label onclick="r${tbright.id}.click();" class="tbright">${tbright.name}</label>
															</div>
														</c:forEach>
													</div>
												</div>
											</c:forEach>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			</c:forEach>
		</div>
		</div>
		</c:if><!-- 对经济会员判断的结束 -->
		</c:if>
		
		 
				
			
	 <!-- 对承销会员的判断 -->
	 <c:if test="${fn:length(fright.childRightSet)>0}">
	 <c:if test="${fright.id ==1802000000 and brotype != 0 and brotype != 2}"> 
	 <div id="div${fright.id}" style="margin-left: 20;">
		<input class="boxcla" type="checkbox" alt="r${module.moduleId}" name="ck" id="r${fright.id}" value="${fright.id}" onclick="uncheckchild(this.checked,'ch${fright.id}div');checkedfather(this.checked,this.alt);" <c:if test="${brokerRightMap[fright.id]}">checked="true"</c:if>/>
		<label onclick="r${fright.id}.click();" class="fright"><strong>${fright.name}</strong></label>
		
		<label onclick="rightdivClick('${fright.id}')" id="lab${fright.id}" style="color: #0000FF;"><img src='${skinPath}/image/app/broker/del.gif'/></label>
		
		  
		
		<div id="ch${fright.id}div" style="margin-left: 20;">
		
			<c:forEach var="sright" items="${fright.childRightSet}">
			<div id="div${sright.id}">
				
				<input class="boxcla" type="checkbox" alt="r${fright.id}" name="ck" id="r${sright.id}" value="${sright.id}" onclick="uncheckchild(this.checked,'ch${sright.id}div');checkedfather(this.checked,this.alt);" <c:if test="${brokerRightMap[sright.id]}">checked="true"</c:if>/>
				<label onclick="r${sright.id}.click();"  class="sright">${sright.name}</label>
				<c:if test="${fn:length(sright.childRightSet)>0}">
				<label onclick="rightdivClick('${sright.id}')" id="lab${sright.id}" style="color: #0000FF;"><img src='${skinPath}/image/app/broker/del.gif'/></label>
				</c:if>
				<!-- 三级菜单 div -->
				<div id="ch${sright.id}div" style="margin-left: 20;">
					<c:forEach var="tright" items="${sright.childRightSet}">
						<div id="div${tright.id}">
							<input class="boxcla" type="checkbox" alt="r${sright.id}" name="ck" id="r${tright.id}" value="${tright.id}" onclick="uncheckchild(this.checked,'ch${tright.id}div');checkedfather(this.checked,this.alt);" <c:if test="${brokerRightMap[tright.id]}">checked="true"</c:if>/>
							<label onclick="r${tright.id}.click();" class="tright">${tright.name}</label>
							<c:if test="${fn:length(tright.childRightSet)>0}">
							<label onclick="rightdivClick('${tright.id}')" id="lab${tright.id}" style="color: #0000FF;"><img src='${skinPath}/image/app/broker/del.gif'/></label>
							</c:if>
							<!-- 一级按钮 div -->
							<div id="ch${tright.id}div" style="margin-left: 20;">
								<c:forEach var="fbright" items="${tright.childRightSet}">
									<div id="div${fbright.id}">
										<input class="boxcla" type="checkbox" alt="r${tright.id}" name="ck" id="r${fbright.id}" value="${fbright.id}" onclick="uncheckchild(this.checked,'ch${fbright.id}div');checkedfather(this.checked,this.alt);" <c:if test="${brokerRightMap[fbright.id]}">checked="true"</c:if>/>
										<label onclick="r${fbright.id}.click();" class="fbright">${fbright.name}</label>
										<c:if test="${fn:length(fbright.childRightSet)>0}">
										<label onclick="rightdivClick('${fbright.id}')" id="lab${fbright.id}" style="color: #0000FF;"><img src='${skinPath}/image/app/broker/del.gif'/></label>
										</c:if>
										<!-- 二级按钮 div -->
										<div id="ch${fbright.id}div" style="margin-left: 20;">
											<c:forEach var="sbright" items="${fbright.childRightSet}">
												<div id="div${sbright.id}">
													<input class="boxcla" type="checkbox" alt="r${fbright.id}" name="ck" id="r${sbright.id}" value="${sbright.id}" onclick="uncheckchild(this.checked,'ch${sbright.id}div');checkedfather(this.checked,this.alt);" <c:if test="${brokerRightMap[sbright.id]}">checked="true"</c:if>/>
													<label onclick="r${sbright.id}.click();" class="sbright">${sbright.name}</label>
													<c:if test="${fn:length(sbright.childRightSet)>0}">
													<label onclick="rightdivClick('${sbright.id}')" id="lab${sbright.id}" style="color: #0000FF;"><img src='${skinPath}/image/app/broker/del.gif'/></label>
													</c:if>
													<!-- 三级按钮 div -->
													<div id="ch${sbright.id}div" style="margin-left: 20;">
														<c:forEach var="tbright" items="${sbright.childRightSet}">
															<div id="div${tbright.id}">
																<input class="boxcla" type="checkbox" alt="r${sbright.id}" name="ck" id="r${tbright.id}" value="${tbright.id}" onclick="checkedfather(this.checked,this.alt);" <c:if test="${brokerRightMap[tbright.id]}">checked="true"</c:if>/>
																<label onclick="r${tbright.id}.click();" class="tbright">${tbright.name}</label>
															</div>
														</c:forEach>
													</div>
												</div>
											</c:forEach>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			</c:forEach>
		</div>
		</div>
		</c:if><!-- 对承销会员判断的结束 -->
		</c:if>
		
		
	 
			
	 <!-- 对发行会员的判断 -->
	  <c:if test="${fn:length(fright.childRightSet)>0}">
	  <c:if test="${fright.id ==1803000000 and brotype != 0 and brotype != 1}"> 
	  <div id="div${fright.id}" style="margin-left: 20;">
		<input class="boxcla" type="checkbox" alt="r${module.moduleId}" name="ck" id="r${fright.id}" value="${fright.id}" onclick="uncheckchild(this.checked,'ch${fright.id}div');checkedfather(this.checked,this.alt);" <c:if test="${brokerRightMap[fright.id]}">checked="true"</c:if>/>
		<label onclick="r${fright.id}.click();" class="fright"><strong>${fright.name}</strong></label>
		
		<label onclick="rightdivClick('${fright.id}')" id="lab${fright.id}" style="color: #0000FF;"><img src='${skinPath}/image/app/broker/del.gif'/></label>
		
		
		<div id="ch${fright.id}div" style="margin-left: 20;">
		
			<c:forEach var="sright" items="${fright.childRightSet}">
			<div id="div${sright.id}">
				
				<input class="boxcla" type="checkbox" alt="r${fright.id}" name="ck" id="r${sright.id}" value="${sright.id}" onclick="uncheckchild(this.checked,'ch${sright.id}div');checkedfather(this.checked,this.alt);" <c:if test="${brokerRightMap[sright.id]}">checked="true"</c:if>/>
				<label onclick="r${sright.id}.click();"  class="sright">${sright.name}</label>
				<c:if test="${fn:length(sright.childRightSet)>0}">
				<label onclick="rightdivClick('${sright.id}')" id="lab${sright.id}" style="color: #0000FF;"><img src='${skinPath}/image/app/broker/del.gif'/></label>
				</c:if>
				<!-- 三级菜单 div -->
				<div id="ch${sright.id}div" style="margin-left: 20;">
					<c:forEach var="tright" items="${sright.childRightSet}">
						<div id="div${tright.id}">
							<input class="boxcla" type="checkbox" alt="r${sright.id}" name="ck" id="r${tright.id}" value="${tright.id}" onclick="uncheckchild(this.checked,'ch${tright.id}div');checkedfather(this.checked,this.alt);" <c:if test="${brokerRightMap[tright.id]}">checked="true"</c:if>/>
							<label onclick="r${tright.id}.click();" class="tright">${tright.name}</label>
							<c:if test="${fn:length(tright.childRightSet)>0}">
							<label onclick="rightdivClick('${tright.id}')" id="lab${tright.id}" style="color: #0000FF;"><img src='${skinPath}/image/app/broker/del.gif'/></label>
							</c:if>
							<!-- 一级按钮 div -->
							<div id="ch${tright.id}div" style="margin-left: 20;">
								<c:forEach var="fbright" items="${tright.childRightSet}">
									<div id="div${fbright.id}">
										<input class="boxcla" type="checkbox" alt="r${tright.id}" name="ck" id="r${fbright.id}" value="${fbright.id}" onclick="uncheckchild(this.checked,'ch${fbright.id}div');checkedfather(this.checked,this.alt);" <c:if test="${brokerRightMap[fbright.id]}">checked="true"</c:if>/>
										<label onclick="r${fbright.id}.click();" class="fbright">${fbright.name}</label>
										<c:if test="${fn:length(fbright.childRightSet)>0}">
										<label onclick="rightdivClick('${fbright.id}')" id="lab${fbright.id}" style="color: #0000FF;"><img src='${skinPath}/image/app/broker/del.gif'/></label>
										</c:if>
										<!-- 二级按钮 div -->
										<div id="ch${fbright.id}div" style="margin-left: 20;">
											<c:forEach var="sbright" items="${fbright.childRightSet}">
												<div id="div${sbright.id}">
													<input class="boxcla" type="checkbox" alt="r${fbright.id}" name="ck" id="r${sbright.id}" value="${sbright.id}" onclick="uncheckchild(this.checked,'ch${sbright.id}div');checkedfather(this.checked,this.alt);" <c:if test="${brokerRightMap[sbright.id]}">checked="true"</c:if>/>
													<label onclick="r${sbright.id}.click();" class="sbright">${sbright.name}</label>
													<c:if test="${fn:length(sbright.childRightSet)>0}">
													<label onclick="rightdivClick('${sbright.id}')" id="lab${sbright.id}" style="color: #0000FF;"><img src='${skinPath}/image/app/broker/del.gif'/></label>
													</c:if>
													<!-- 三级按钮 div -->
													<div id="ch${sbright.id}div" style="margin-left: 20;">
														<c:forEach var="tbright" items="${sbright.childRightSet}">
															<div id="div${tbright.id}">
																<input class="boxcla" type="checkbox" alt="r${sbright.id}" name="ck" id="r${tbright.id}" value="${tbright.id}" onclick="checkedfather(this.checked,this.alt);" <c:if test="${brokerRightMap[tbright.id]}">checked="true"</c:if>/>
																<label onclick="r${tbright.id}.click();" class="tbright">${tbright.name}</label>
															</div>
														</c:forEach>
													</div>
												</div>
											</c:forEach>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			</c:forEach>
		</div>
		</div>
		</c:if><!-- 对发行会员判断的结束 -->
		</c:if>
		
		
	<!-- 二级菜单 end -->	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	</c:forEach>
</div>
</div>