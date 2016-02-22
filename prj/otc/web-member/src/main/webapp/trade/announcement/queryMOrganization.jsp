<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<script language="javascript">
		var rightMap=${sessionScope.rightMap};
</script>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
			defer="defer"></script>
	<script type="text/javascript">
if('${msg}'=='success'){
	alert("操作成功！");
}
</script>		
<script type="text/javascript">
	function sub(){
		frm.action="${basePath}/tradeManage/mBrokerOrganization/updateMOrganization.action";
		if(confirm('确定更新?')){
			
			frm.submit();
		}
	}
	function selAll(){
		var temp = document.getElementById("temp").checked;
		 var obj = document.getElementsByName("organizationNo");
		 for(var i=0;i<obj.length;i++){  
             obj[i].checked = temp;
        }  
	}
	function chage(){
		document.getElementById("temp").checked=false;
	}
	
	function load(){
		var a = "${selFlag}";
		var obj = document.getElementsByName("organizationNo");
		 for(var i=0;i<obj.length;i++){  
			 var val = obj[i].value+";";
			 if(a.indexOf(val)>=0){
				 obj[i].checked = true;
			}
            
       }  
		
	}
</script>
<html>
	<head>
		<title>会员的机构信息的维护</title>
	</head>
	<body style="overflow-y: hidden" onload="load()">
		<form action="${basePath}/tradeManage/memberInfor/queryMOrganization.action" method="post" name="frm" id ="frm">
		<br/><br/>
			<table align="center" border="0">
				<tr class="table3_style" >
					<th colspan="2" width="200"><h3><input type="checkbox" onclick="selAll()" id="temp"/>全选</h3></th>
					<th colspan="2" width="200"><h3>机构编号</h3></th>
					<th colspan="2" width="200"><h3>机构名称</h3></th>
				</tr>
						<c:forEach items="${list}" var="org" >
							<tr align="center">
								<td colspan="2"><input type="checkbox"  name="organizationNo" onclick="chage()" value="${org.organizationNO}" /></td>
								<td colspan="2">${org.organizationNO}</td>
								<td colspan="2">${org.name}</td>
							</tr>
						</c:forEach>
				
				<tr align="center">	<td colspan="6">&nbsp;</td></tr>
				<tr align="center">
								<td colspan="6"><input type="button" value="更新" class="btn_sec" onclick="sub()"/></td>
								
				</tr>
			</table>
		</form>
	</body>

</html>

<%@ include file="/public/footInc.jsp"%>

