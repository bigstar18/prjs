<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<html>
  <head>
	<title><%=TITLE%></title>
</head>
<body>
	<form id="frm" action="<%=basePath%>servlet/warehouseController.${POSTFIX}?funcflg=commodityWarehouseRelate" method="POST" targetType="hidden" >
		<input type="hidden" name="warehouseId" value="<c:out value="${warehouse.id}"/>">
		<fieldset width="100%">
		<legend>关联商品信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%" align="center">
			  <tr height="35">
                <td align="right" width="15%">编号：</td>
                <td align="left" width="20%">
                	<c:out value="${warehouse.id}"></c:out>&nbsp;&nbsp;
                </td>
                
                <td align="right" width="15%">简称：</td>
                <td align="left" width="20%">
                	<c:out value="${warehouse.name}"></c:out>&nbsp;&nbsp;
                </td>
              </tr>
			 
			  <tr height="35">
			  	<td align="right" width="15%">全称：</td>
                <td align="left" width="20%">
                	<c:out value="${warehouse.fullName}"></c:out>&nbsp;&nbsp;
                </td>
			  
			  	<td align="right" width="15%">地址：</td>
                <td align="left" width="20%">
                	<c:out value="${warehouse.address}"></c:out>&nbsp;&nbsp;
                </td>
			  
            	
              </tr>
			  
			  <tr height="35">
			  	<td align="right" width="15%">负责人：</td> 
                <td align="left" width="20%">
                	<c:out value="${warehouse.linkman}"></c:out>&nbsp;&nbsp;
                </td>
                
                <td align="right">电话：</td>
                <td align="left">
                	<c:out value="${warehouse.tel}"></c:out>&nbsp;&nbsp;
                </td>
			  </tr>
              
              <tr height="35">
                <td align="right" colspan="1">关联品种：</td>
                <td align="left" colspan="3">
                    <c:set var="count" value="0"/>
                    <c:set var="add" value="1"/>
                	<c:forEach items="${resultList}" var="result">
                		<c:choose>
		  					<c:when test="${count > 3}">
		  					<br><br>&nbsp;<input type="checkbox" name="commodityId" value="${result.id}">${result.name}&nbsp;&nbsp;
		  						<c:set var="count" value="0"/>
		  					</c:when>
		  					<c:otherwise>
								<input type="checkbox" name="commodityId" value="${result.id}">${result.name}&nbsp;&nbsp;
								<c:set var="count" value="${count+add}"/>
							</c:otherwise>
		  				</c:choose>
		        	</c:forEach>
		        	<script>
                    var allCheckbox = frm.commodityId;
                    if(allCheckbox)
                    { 
	                    if(allCheckbox.length)
	                    {
							for (var i=0; i<allCheckbox.length; i++){
								if (allCheckbox[i].type == "checkbox"){
								    var commodityID = "," + allCheckbox[i].value + ",";
									if('${permissions}'.indexOf(commodityID) >= 0){
										allCheckbox[i].checked = true; 
									}
								}
							}
						}else{
						  	var commodityID = "," + allCheckbox.value + ",";
							if('${permissions}'.indexOf(commodityID) >= 0){
								allCheckbox.checked = true; 
							}
						}
					} 

					</script>
                </td>
              </tr>
               <table height="150" border="0"> 
				<tr height="150">&nbsp;</tr>
			   </table>
               
              <tr height="35">
                <td colspan="4"><div align="center">
                  <button class="smlbtn" type="button" onclick="doSubmit();">提交</button>&nbsp;
      			  <button class="smlbtn" type="button" onclick="freturn()">返回</button>
                </div></td>
              </tr>
          </table>
		</fieldset>
        </form>
</body>
</html>
<script>
	function doSubmit()
	{
		if(confirm("确定执行此操作？")){
			frm.submit();
		}
	}
	function freturn(){
		frm.action = "<%=basePath%>servlet/warehouseController.${POSTFIX}?funcflg=warehouseReturn";
		frm.submit();
	}
</script> 