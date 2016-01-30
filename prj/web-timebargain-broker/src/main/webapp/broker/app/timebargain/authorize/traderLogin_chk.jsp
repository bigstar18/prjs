<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<script language="javascript">
  if(${prompt!=null}){
  	showDialogRes("${basePath}/broker/app/timebargain/authorize/traderLogin.jsp",window,400,300);
  }else{
  	chkOk();
  }
    
function chkOk(){
	if(${mkName=='noTrade'}){
		parent.ListFrame.location.href = "${basePath}/timebargain/authorize/noTradeList.action";
	}
	else if(${mkName=='authorize'}){
		parent.ListFrame.location.href = "${basePath}/timebargain/authorize/editOrder.action";
	}
	else if(${mkName=='password'}){
		parent.ListFrame.location.href = "${basePath}/timebargain/authorize/updatePasswordForward.action";
	}
	else if(${mkName=='conferClose'}){
		parent.ListFrame.location.href = "${basePath}/timebargain/authorize/editOrder.action";
	}
	else if(${mkName=='forceClose'}){
		parent.ListFrame.location.href = "${basePath}/timebargain/authorize/searchForceClose.action";
	}else{
		parent.ListFrame.order();
	}
}

</script>

