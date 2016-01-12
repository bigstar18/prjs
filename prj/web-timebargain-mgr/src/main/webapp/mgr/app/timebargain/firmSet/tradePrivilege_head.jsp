<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>


<html>
  <head>
    <title>交易权限选项卡页</title>
  </head>
  
  <script type="text/javascript">
    function window_onload()
    {
      changeColor("firmInfo");
      query();
    }

    function firmInfo_onclick()
    {
      changeColor("firmInfo");
      query();

    }

    function typePrivilege_onclick(){
  	  changeColor("privilege");
  	  query();
    }

    function changeColor(name)
    {
      oper.value = name;
      if(name == "firmInfo")
      {
    	  firmInfo.style.color = "red";
    	  privilege.style.color = "#26548B";
      }
      else if(name == "privilege")
      {
    	  privilege.style.color = "red";
    	  firmInfo.style.color = "#26548B";
      }
    }

    function query()
    {
      if(oper.value == "firmInfo")
      {
    	  parent.ListFrame.location.href = "${basePath}/timebargain/firmSet/tradePrivilege/listFirmInfo.action?sortColumns=order+by+createTime+desc";
      }
      else if(oper.value == "privilege")
      {
    	  parent.ListFrame.location.href = "${basePath}/timebargain/firmSet/tradePrivilege/listTypePrivilege.action";
      } 
  
    }
  
  </script>
  
  <body leftmargin="0" topmargin="0" onLoad="return window_onload()">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="11" height="31" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>"></td>
        <td width="17" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>"><div align="left"><img src="<c:url value="${skinPath }/image/app/timebargain/line.gif"/>" width="3" height="31"></div></td>   
        <td width="120" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>">
           <%-- <a href="#" id="firmInfo" class="common" onclick="firmInfo_onclick()">交易商信息</a> --%> 
           <rightHyperlink:rightHyperlink href="#" id="firmInfo" text="交易商信息" className="common" onclick="firmInfo_onclick()" action="/timebargain/firmSet/tradePrivilege/listFirmInfo.action" />
         </td>
        <td width="29" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>"><div align="left"><img src="<c:url value="${skinPath }/image/app/timebargain/line.gif"/>" width="3" height="31"></div></td>
        <td width="120" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" >
           <%-- <a href="#" id="privilege" class="common" onclick="typePrivilege_onclick()">权限信息</a>--%>
           <rightHyperlink:rightHyperlink href="#" id="privilege" text="权限信息" className="common" onclick="typePrivilege_onclick()" action="/timebargain/firmSet/tradePrivilege/listTypePrivilege.action" />
        </td>
        <td width="13" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" ><img src="<c:url value="${skinPath }/image/app/timebargain/line.gif"/>" width="3" height="31"></td>
        <td width="333" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" >&nbsp;</td>
        <td width="267" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" >&nbsp; </td>
      </tr>
    </table>
     <input type="hidden" name="oper" value="" >
  </body>
</html>
