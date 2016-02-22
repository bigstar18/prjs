<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/basecommon.jsp"%>
<html>
<body>
 <div id="haeder" style="height:65px;">
 <form name=frm id=frm>
			<input type="hidden" name="obj.id" value="">
   <div class="logo"> <div class="anniu_kuang">
    <div class="anniu_01Copy"><a href="#" onclick="logout();"><img src="<%=skinPath%>/cssimg/03.gif" width="91" height="25" border="0" style="-moz-opacity:0.5; filter:alpha(opacity=80);cursor:hand;" onmouseover="this.style.MozOpacity=1;
this.filters.alpha.opacity=100" onmouseout="this.style.MozOpacity=0.5;
this.filters.alpha.opacity=80"></a></div>
    <div class="anniu_01"><a href="#" onclick="changePwd('${CURRENUSERID}');"><img src="<%=skinPath%>/cssimg/02.gif" width="91" height="25" border="0" style="-moz-opacity:0.5; filter:alpha(opacity=80);cursor:hand;" onmouseover="this.style.MozOpacity=1;
this.filters.alpha.opacity=100" onmouseout="this.style.MozOpacity=0.5;
this.filters.alpha.opacity=80"></a></div>
   </div>
    <div class="anniu_kuangCopy">
      <div class="user_name">µÇÂ¼ÕËºÅ:${CURRENUSERID }</div>
    </div>
  </div></form>
 </div>
 </body>
</html>
<script language="javascript">
	function logout()
	{
	  var vaild = window.confirm("ÄúÈ·¶¨ÒªÍË³öÂð£¿");
		if(vaild==true){
		    parent.window.location="<%=basePath %>/user/commonUserLogout.action";
	    }else{
           return false;
	    }
    }
	function changePwd(id)
	{
		window.showModalDialog("<%=basePath %>/user/forwardUpdatePassword.action?obj.brokerageNo="+id+"&d="+Date(),"", "dialogWidth=450px; dialogHeight=300px; status=no;scroll=no;help=no;");
	}
	function versionInfo()
	{
		window.showModalDialog("<%=surfacePath %>/version.jsp?d="+Date(),"", "dialogWidth=385px; dialogHeight=270px; status=no;scroll=no;help=no;")
	}
	function shinStyle(id){
		var result = window.showModalDialog("<%=basePath %>/user/commonUserModStyle.action?obj.userId="+id+"&d="+Date(),"","dialogWidth=400px; dialogHeight=250px; status=no;scroll=no;help=no;resizable=no");
		if(result)
		{		
			parent.window.location = "<%=surfacePath %>/index.jsp";
		}
	}
</script>