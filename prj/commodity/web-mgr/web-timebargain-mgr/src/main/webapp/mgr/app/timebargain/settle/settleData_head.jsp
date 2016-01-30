<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
  <script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
    <title>商品特殊设置选项卡页</title>
  </head>
  
  <script type="text/javascript">
    function window_onload()
    {
      changeColor("settleDataDetail");
      query();
    }
    
    function settleDataDetail_onclick()
    {
      changeColor("settleDataDetail");
      query();
    }
    
    function notPairTotal_onclick()
    {
      changeColor("notPairTotal");
      query();
    }

    function alreadyPairTotal_onclick()
    {
      changeColor("alreadyPairTotal");    
      query();
    }



    function changeColor(name)
    {
      oper.value = name;
      if(name == "settleDataDetail")
      {
    	  	$("#settleDataDetail").css("color","red");
	    	$("#notPairTotal").css("color","#26548B");
	    	$("#alreadyPairTotal").css("color","#26548B");
      }
      else if(name == "notPairTotal")
      { 
    		$("#settleDataDetail").css("color","#26548B");
	    	$("#notPairTotal").css("color","red");
	    	$("#alreadyPairTotal").css("color","#26548B");
	    	
      }
      else if(name == "alreadyPairTotal")
      {
    	  	$("#settleDataDetail").css("color","#26548B");
	    	$("#notPairTotal").css("color","#26548B");
	    	$("#alreadyPairTotal").css("color","red");
      } 
    }

    function query()
    {
      if(oper.value == "settleDataDetail")
      {
    	parent.ListFrame.location.href = "${basePath}/timebargain/bill/listSettleDataDetail.action";
      }
      else if(oper.value == "notPairTotal")
      {
    	parent.ListFrame.location.href = "${basePath}/timebargain/bill/notPairTotal.action";
      }
      else if(oper.value == "alreadyPairTotal")
      {
    	parent.ListFrame.location.href = "${basePath}/timebargain/bill/alreadyPairTotal.action";
      } 
  }
  
  </script>
  
  <body leftmargin="0" topmargin="0" onLoad="return window_onload()">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="11" height="31" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>"></td>
        <td width="17" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>"><div align="left"><img src="<c:url value="${skinPath }/image/app/timebargain/line.gif"/>" width="3" height="31"></div></td>   
        <td width="120" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>">
           <%--  <a href="#" id="settleDataDetail" class="common" onclick="settleDataDetail_onclick()">交收数据明细</a>--%>
            <rightHyperlink:rightHyperlink href="#" id="settleDataDetail" text="交收数据明细" className="common" onclick="settleDataDetail_onclick()" action="/timebargain/bill/listSettleDataDetail.action" />
        </td>
         <td width="17" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>"><div align="left"><img src="<c:url value="${skinPath }/image/app/timebargain/line.gif"/>" width="3" height="31"></div></td>   
        <td width="120" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>">
            <%-- <a href="#" id="notPairTotal" class="common" onclick="notPairTotal_onclick()">未配对合计</a>--%>
            <rightHyperlink:rightHyperlink href="#" id="notPairTotal" text="未配对合计" className="common" onclick="notPairTotal_onclick()" action="/timebargain/bill/notPairTotal.action" />
        </td>
        <td width="17" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>"><div align="left"><img src="<c:url value="${skinPath }/image/app/timebargain/line.gif"/>" width="3" height="31"></div></td>
        <td width="120" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" >
           <%--  <a href="#" id="alreadyPairTotal" class="common" onclick="alreadyPairTotal_onclick()">已配对合计</a>--%>
            <rightHyperlink:rightHyperlink href="#" id="alreadyPairTotal" text="已配对合计" className="common" onclick="alreadyPairTotal_onclick()" action="/timebargain/bill/alreadyPairTotal.action" />
        </td>
        <td width="13" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" ><img src="<c:url value="${skinPath }/image/app/timebargain/line.gif"/>" width="3" height="31"></td>
        <td width="50" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" >&nbsp;</td>
        <td width="267" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" >&nbsp; </td>
      </tr>
    </table>
    <input type="hidden" name="oper" value="" >
  </body>
</html>
