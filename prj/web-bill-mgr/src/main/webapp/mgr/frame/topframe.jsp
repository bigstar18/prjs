<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>��ܼ�topҳ��</title>
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.min.js"></script>
		<script language="javascript">
		<!--
			/**
			 * �Ƴ�ϵͳ
			 */
			function logoutsys(){
				var vaild = window.confirm("��ȷ��Ҫ�˳���");
				if(vaild==true){
				    parent.window.location="<%=basePath %>/user/logout.action";
			    }else{
		           return false;
			    }
		    }
		    /**
		     * �޸��Լ��ĵ�½����
		     */
			function changePwd(id){
				var url = "<%=basePath %>/self/passwordSelfMod.action?entity.userId="+id;
				if(showDialog(url, "", 500, 300)){
					frm.submit();
				}
			}
			/**
			 * �޸��Լ�����ʽ���
			 */
			function shinStyle(id){
				var url = "<%=framePath %>/shinstyle.jsp?d="+Date();
				if(showDialog(url, "", 500, 300)){
					frm.submit();
					parent.window.location = "<%=framePath %>/mainframe.jsp";
				}
			}

			//ʱ���
			var timediffer=0;
			//ѭ��ִ��ʱ��ͬ����ʱ��
			var ajaxtime = 0;
			/**
			 * ajax ��ȡ��ǰ���ݿ�ʱ��
			 */
			function ajaxGetSysDate(){
				var url = "../../sysDate/getDate.action?t="+Math.random();
				$.getJSON(url,null,function call(result){
					var dateArr=result[0].split(" ");
					var yearOrMouthOrDay=dateArr[0].split("-");
					var hourOrMinOrSen=dateArr[1].split(":");
					var sysdate=new Date(yearOrMouthOrDay[0],yearOrMouthOrDay[1]-1,yearOrMouthOrDay[2],hourOrMinOrSen[0],hourOrMinOrSen[1],hourOrMinOrSen[2]);
					var date = new Date();
					timediffer = sysdate.getTime()-date.getTime();
					clock();
				});
			}
			/**
			 * ������ʱ������ 1 ��
			 */
			function clock() {
				ajaxtime = ajaxtime+1;
				var date = new Date();
				date.setTime(date.getTime()+timediffer);
				sysDate.innerHTML= date.toLocaleString();
				if(ajaxtime<(5*60)){
					setTimeout("clock()", 1000);
				}else{
					ajaxtime = 0;
					ajaxGetSysDate();
				}
			}

			jQuery(document).ready(function() {
				ajaxGetSysDate();
			});
		//-->
		</script>
	</head>
	<body>
		<div id="haeder">
			<form name=frm id=frm>
				<div class="logo"> 
					<div class="anniu_kuang">
						<ul>
							<li>
								<a href="#" onClick="shinStyle('${CurrentUser.userId }');">
									<img src="<%=skinPath%>/image/frame/topframe/skinStyle.png" border="0">
								</a>
							</li>
							<li>
								<a href="#" onClick="logoutsys();">
									<img src="<%=skinPath%>/image/frame/topframe/logout.png"border="0" >
								</a>
							</li>
							<li>
								<a href="#" onClick="changePwd('${CurrentUser.userId }');">
									<img src="<%=skinPath%>/image/frame/topframe/pwd.png" border="0" >
								</a>
							</li>
						</ul>
					</div>
					<div class="anniu_kuangCopy">
						����Ա����:${CurrentUser.userId }&nbsp;&nbsp;&nbsp;&nbsp;
						����Ա����:${CurrentUser.name }&nbsp;&nbsp;&nbsp;&nbsp;
						ϵͳʱ��Ϊ��<font id="sysDate">&nbsp;</font>
					</div>
				</div>
			</form>
		</div>
	</body>
</html>