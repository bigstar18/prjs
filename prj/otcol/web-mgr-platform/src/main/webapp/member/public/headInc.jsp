<%@ page pageEncoding="GBK" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt' %>
<%@ page import='java.util.Date'%>
<%@ page import='gnnt.MEBS.base.query.*'%>
<%@ page import='java.util.*' %>
<%@ page import='gnnt.MEBS.finance.base.util.*' %>
<%@ include file="/common/public/session.jsp"%>
<%!
  public final String FIRMID=SysData.getConfig("firmId");
  public final String FIRMNAME="����������";
  public final String FULLNAME="������ȫ��";
  public final String BROKER=SysData.getConfig("brokerName");
  public final String BROKERID=BROKER+"���";
  public final String COMMODITYID="��Ʒ����";
  public final String BREEDID="Ʒ��";
  public final String BREEDNAME="Ʒ������";
  public final String MODULEID="ģ��";
  public final String TRADERID="����Ա����";
  public final String BROKERFRIM=SysData.getConfig("brokerAccount");
%>
<%
	//�����̹���
	String brokerControllerPath = request.getContextPath()+"/member/brokerController.mem?funcflg=";
    // ������Ȩ��
    String brokerRightControllerPath = request.getContextPath()+"/member/brokerRightController.mem?funcflg=";
	//������
	String feeDetailControllerPath = request.getContextPath()+"/member/feeDetailController.mem?funcflg=";
	//Ӷ������
	String brokerRewardControllerPath = request.getContextPath()+"/member/brokerRewardController.mem?funcflg=";
	String memberPath= request.getContextPath()+"/member/";
	String brokerPath= request.getContextPath()+"/member/broker/brokerDetail/";
	String errorLoginLogControllerPath= request.getContextPath()+"/member/errorLoginLogController.mem?funcflg=";

    /*
		�Ѿ���choSkin.jsp�ж���ı���:
		skinName-����ƥ����ļ������� 
		skin-ѡ�еķ�� 
		path-/mgr
		basePath1-http:/172.16.2.214:8888/mgr
		skinPath-�õ���css
		escideskinPath-ecside��Ӧ��css
	*/
	//String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/member";
	String basePathF = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/finance";
	String userSkinName = "default";
	//String skinPath = basePath + "/skin/" + userSkinName;
	String skinPathF = basePathF + "/skin/" + userSkinName;
	String nowDate = Utils.formatDate("yyyy-MM-dd",new Date());
	pageContext.setAttribute("nowDate",nowDate);
	
    PageInfo pageInfo=(PageInfo)request.getAttribute("pageInfo");
    String orderFiled="";
    String orderType="";
    if(pageInfo!=null)
    {
    List listf=pageInfo.getOrderFields();
    if(listf!=null&&listf.size()>0)
    {
        OrderField order=(OrderField)listf.get(0);
        orderFiled=order.getOrderField();
        orderType=order.isOrderDesc()+"";
    }
    }
    pageContext.setAttribute("orderFiled", orderFiled);
    pageContext.setAttribute("orderType", orderType);
 %>
<html>
	<base>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<META HTTP-EQUIV="pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
	<META HTTP-EQUIV="expires" CONTENT="0">
	
	<link rel="stylesheet" href="<%=skinPathF%>/css/style.css" type="text/css"/>
	<script language="javascript" src="<%=request.getContextPath()%>/common/timebargain/scripts/global.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/common/jslib/tools.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/common/jslib/common.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/common/jslib/frameCtrl.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/common/jslib/formInit.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/common/jslib/print.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/common/jslib/jquery.js"></script>

	
	<script language="javascript">
		var basePath = "<%=basePath%>";
		var skinPath = "<%=skinPath%>";
		var basePathF = "<%=basePathF%>";
		var skinPathF = "<%=skinPathF%>";	
		var sign=true;
		
		<c:if test='${not empty resultMsg}'>
			alert('<c:out value="${resultMsg}"/>');
		</c:if>
	</script>