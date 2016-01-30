<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>品种特殊设置选项卡页</title>
  </head>
  
  <script type="text/javascript">
    function window_onload()
    {
      changeColor("tradeMargin");
      query();
    }
    
    function tradeMargin_onclick()
    {
      changeColor("tradeMargin");
      query();
    }
    
    function settleMargin_onclick()
    {
      changeColor("settleMargin");
      query();
    }

    function qty_onclick()
    {
      changeColor("qty");    
      query();
    }

    function tradeFee_onclick()
    {
      changeColor("tradeFee");    
      query();
    }

    function settleFee_onclick()
    {
      changeColor("settleFee");    
      query();
    }

    function changeColor(name)
    {
      oper.value = name;
      if(name == "tradeMargin")
      {
    	tradeMargin.style.color = "red";
    	settleMargin.style.color = "#26548B";
    	qty.style.color = "#26548B";
    	tradeFee.style.color = "#26548B";
    	settleFee.style.color = "#26548B";
      }
      else if(name == "settleMargin")
      {
    	tradeMargin.style.color = "#26548B";
      	settleMargin.style.color = "red";
      	qty.style.color = "#26548B";
      	tradeFee.style.color = "#26548B";
      	settleFee.style.color = "#26548B";
      }
      else if(name == "qty")
      {
    	tradeMargin.style.color = "#26548B";
      	settleMargin.style.color = "#26548B";
      	qty.style.color = "red";
      	tradeFee.style.color = "#26548B";
      	settleFee.style.color = "#26548B";
      } 
      else if(name == "tradeFee")
      {
    	tradeMargin.style.color = "#26548B";
      	settleMargin.style.color = "#26548B";
      	qty.style.color = "#26548B";
      	tradeFee.style.color = "red";
      	settleFee.style.color = "#26548B";
      }
      else if(name == "settleFee")
      {
    	tradeMargin.style.color = "#26548B";
      	settleMargin.style.color = "#26548B";
      	qty.style.color = "#26548B";
      	tradeFee.style.color = "#26548B";
      	settleFee.style.color = "red";
      }  
    }

    function query()
    {
      if(oper.value == "tradeMargin")
      {
    	parent.ListFrame.location.href = "${basePath}/timebargain/firmSet/breedSpecial/listTradeMargin.action?sortColumns=order+by+modifyTime+desc";
      }
      else if(oper.value == "settleMargin")
      {
    	parent.ListFrame.location.href = "${basePath}/timebargain/firmSet/breedSpecial/listSettleMargin.action?sortColumns=order+by+modifyTime+desc";
      }
      else if(oper.value == "qty")
      {
    	parent.ListFrame.location.href = "${basePath}/timebargain/firmSet/breedSpecial/listMaxHoldQty.action?sortColumns=order+by+modifyTime+desc";
      } 
      else if(oper.value == "tradeFee")
      {
    	parent.ListFrame.location.href = "${basePath}/timebargain/firmSet/breedSpecial/listTradeFee.action?sortColumns=order+by+modifyTime+desc";
      } 
      else if(oper.value == "settleFee")
      {
    	parent.ListFrame.location.href = "${basePath}/timebargain/firmSet/breedSpecial/listSettleFee.action?sortColumns=order+by+modifyTime+desc";
      } 
  }
  
  </script>
  
  <body leftmargin="0" topmargin="0" onLoad="return window_onload()">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="11" height="31" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>"></td>
        <td width="17" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>"><div align="left"><img src="<c:url value="${skinPath }/image/app/timebargain/line.gif"/>" width="3" height="31"></div></td>   
        <td width="120" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>">
          <%--   <a href="#" id="tradeMargin" class="common" onclick="tradeMargin_onclick()">特殊交易保证金</a>--%>
            <rightHyperlink:rightHyperlink href="#" id="tradeMargin" text="特殊交易保证金" className="common" onclick="tradeMargin_onclick()" action="/timebargain/firmSet/breedSpecial/listTradeMargin.action" />
        </td>
         <td width="17" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>"><div align="left"><img src="<c:url value="${skinPath }/image/app/timebargain/line.gif"/>" width="3" height="31"></div></td>   
        <td width="120" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>">
           <%--  <a href="#" id="settleMargin" class="common" onclick="settleMargin_onclick()">特殊交收保证金</a>--%>
            <rightHyperlink:rightHyperlink href="#" id="settleMargin" text="特殊交收保证金" className="common" onclick="settleMargin_onclick()" action="/timebargain/firmSet/breedSpecial/listSettleMargin.action" />
        </td>
        <td width="17" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>"><div align="left"><img src="<c:url value="${skinPath }/image/app/timebargain/line.gif"/>" width="3" height="31"></div></td>
        <td width="120" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" >
             <%-- <a href="#" id="qty" class="common" onclick="qty_onclick()">特殊订货量</a>--%>
             <rightHyperlink:rightHyperlink href="#" id="qty" text="特殊订货量" className="common" onclick="qty_onclick()" action="/timebargain/firmSet/breedSpecial/listMaxHoldQty.action" />
        </td>
        <td width="13" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" ><img src="<c:url value="${skinPath }/image/app/timebargain/line.gif"/>" width="3" height="31"></td>
        <td width="120" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" >
            <%--  <a href="#" id="tradeFee" class="common" onclick="tradeFee_onclick()">特殊交易手续费</a>--%>
             <rightHyperlink:rightHyperlink href="#" id="tradeFee" text="特殊交易手续费" className="common" onclick="tradeFee_onclick()" action="/timebargain/firmSet/breedSpecial/listTradeFee.action" />
        </td>
        <td width="13" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" ><img src="<c:url value="${skinPath }/image/app/timebargain/line.gif"/>" width="3" height="31"></td>
        <td width="120" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" >
            <%--  <a href="#" id="settleFee" class="common" onclick="settleFee_onclick()">特殊交收手续费</a>--%>
             <rightHyperlink:rightHyperlink href="#" id="settleFee" text="特殊交收手续费" className="common" onclick="settleFee_onclick()" action="/timebargain/firmSet/breedSpecial/listSettleFee.action" />
        </td>
        <td width="13" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" ><img src="<c:url value="${skinPath }/image/app/timebargain/line.gif"/>" width="3" height="31"></td>
        <td width="50" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" >&nbsp;</td>
        <td width="267" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" >&nbsp; </td>
      </tr>
    </table>
    <input type="hidden" name="oper" value="" >
  </body>
</html>
