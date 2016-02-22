<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html xmlns:MEBS>
	<head>
		<title>行情数据源维护</title>
		<link
			href="${basePath}/common/skinstyle/default/common/commoncss/mainstyle.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="${basePath}/js/jquery-1.5.2.min.js"></script>
		<script type="text/javascript">
		 function moveUp(obj)  
		    {  
		        var current=$(obj).parent().parent();  
		        var prev=current.prev(); 
		        if(prev)  
		        {  
		            current.insertBefore(prev);  
		        }  
		    }  
		    function moveDown(obj)  
		    {  
		        var current=$(obj).parent().parent();  
		        var next=current.next();  
		        if(next)  
		        {  
		            current.insertAfter(next);  
		        }  
		    }

		    function flush(){
			    frm.action="${basePath}/marketMaintenanceSet/hqServerInfo/list.action";
				frm.submit();
			 }  

			 function update(){
				 var tds=$("span[name='num']");
				 var idStr="";
				for(var i=0;i<tds.length;i++){
					if(idStr!=""){
						idStr+="_";
					}
					idStr+=tds[i].innerText;
				}
				frm.action="${basePath}/marketMaintenanceSet/hqServerInfo/update.action?idStr="+idStr;
				var vaild = affirm("您确定要操作吗？");
				if (vaild == true) {
					frm.submit();
				} else {
					return false;
				}
				
				
			}
		</script>
	</head>

	<body>
		 <form name="frm" action="${basePath}/marketMaintenanceSet/hqServerInfo/list.action" method="post">
		 </form>
		<div class="div_list">

			<div class="div_cxtj">
				<img
					src="${basePath }/common/skinstyle/default/common/commoncss/cssimg/13.gif" />
				数据源维护
			</div>
			<div class="div_tj">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="table2_style">
					<tr>
						<td class="table_top">
							<div id="memberlist">
								<table width="100%" class="tablesorter" border="0"
									cellpadding="0" cellspacing="0" id="res_tabprice">
									<thead>
										<tr>
											<th height="23"  width="25%" nowrap="nowrap" class="right_05bj_td">
												行情源名称
											</th>
											<th height="23"  width="25%" nowrap="nowrap" class="right_05bj_td">
												行情源IP
											</th>
											<th height="23"  width="*" nowrap="nowrap" class="right_05bj_td">
												操作
											</th>
										</tr>
									</thead>
									<c:forEach items="${resultList}" var="hq" varStatus="s">
									
										<tr id="templateprice">
											<td height="23" class="monileft_05_tdbj" style="text-align: right;">
												<span name="num" style="display:none;">${hq.id }</span>
												<span style="float:left;">${hq.serverName }</span>&nbsp;
												<c:if test="${s.index==0 }">
												<img  src="${basePath }/common/skinstyle/default/common/commoncss/images1/import.png"/>
												&nbsp;&nbsp;</c:if>
												</td>
											<td height="23" class="monileft_05_tdbj">
												${hq.serverAddr }&nbsp;
											</td>
											<td height="23" class="monileft_05_tdbj">
												<a href="javascript:void(0)" onClick="moveUp(this)">上移</a>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<a href="javascript:void(0)" onClick="moveDown(this)">下移</a>&nbsp;
											</td>
											
										</tr>
									</c:forEach>
									
								</table>
							</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			
			 <div style="margin-left: 20px; margin-top: 10px;">
	   				 <div style="float: left;">
			            <button class="anniu_btn" onclick="update()" id="update">确定</button>
			             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			             <button class="anniu_btn" onclick="flush()" id="flush">取消</button>
			          </div>
	 		 </div>
		</div>
	</body>
</html>
