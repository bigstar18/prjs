<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/vendue/manage/globalDef.jsp"%>

<%  //xieying
try
{
	float tradeunit=Float.valueOf(request.getParameter("tradeunit"));//����ϵ��

%>
<!--ȡ�ò���-->
<html>
  <head>
	<title class="Noprint">��ͬ״̬����</title>
</head>
<c:if test="${not empty param.opt}">
</c:if>
<body>
<form name=frm id=frm action="" targetType="hidden" callback='closeRefreshDialog();' method="post">
        <!--�г���-->
		<input type="hidden" name="marketId" value="${param.marketId}">
		<fieldset width="100%">
		<legend>��ͬ״̬����</legend>
		<BR>
		<span>
		<c:set var="contractID" value=""/>
	    <!--�ѽ�����-->
		<c:set var="payment" value=""/>
		<!--�ɽ�����-->
		<c:set var="amount" value=""/>
		<!--�ɽ��۸�-->
		<c:set var="price" value=""/>
		<!--��Ʒ��-->
		<c:set var="commodityID" value=""/>
		<!--ʣ�����-->
		<c:set var="lastAmount" value=""/>
		<!--��֤��-->
		<c:set var="security" value=""/>
		<!--��ͬ״̬-->
		<c:set var="status" value=""/>
		<!--ִ�н��-->
		<c:set var="result" value=""/>
		<!--ʵ���������-->
		<c:set var="actualAmount" value=""/>
		<!--ΥԼ����-->
		<c:set var="fellBackAmount" value=""/>
		<!--��ע-->
		<c:set var="remark" value=""/>
		<!--�����̴���-->
		<c:set var="usercode" value=""/>
		<db:select_HisBarDetail var="row" where="u1.commodityid=${param.commodityID} and u1.commodityid=u3.id and u3.id=u4.commid and u2.firmid=u1.userid">
		  <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
		  	<c:set var="contractID" value="${row.contractid}"/>
		  	<c:set var="amount" value="${row.amount*row.tradeunit}"/>
		  	<c:set var="price" value="${row.price}"/>
		  	<c:set var="commodityID" value="${row.id}"/>
		  	<c:set var="lastAmount" value="${row.lastAmount}"/>
		  	<c:set var="security" value="${row.bail}"/>
		    <c:set var="status" value="${row.status}"/>
			<c:set var="result" value="${row.result}"/>
			<c:set var="usercode" value="${row.usercode}"/>
			<c:set var="actualAmount" value="${row.actualamount}"/>
			<c:set var="fellBackAmount" value="${row.fellbackamount}"/>
			<c:set var="remark" value="${row.note}"/>
			<input type="hidden" name="commodityID" value="${row.id}">
			<input type="hidden" name="contractId" value="${row.contractid}">
        <tr height="25">
                <td align="right" width="20%"> ��ĺ� ��</td>
                <td align="left" width="20%">
                	${row.code}
                </td>
                <td align="right" width="20%"> ��ͬ�� ��</td>
                <td align="left" width="20%">
                	${row.contractid}
                </td>
        </tr>
		<tr height="25">
        <td align="right"> �����û����� ��</td>
        <!--<td align="left">${row.usercode}</td>-->
		<td align="left">${row.userid}</td>
        <td align="right"> �����û�ȫ�� ��</td>
        <td align="left">${row.name}</td>
        </tr>
        </table>	
    </db:select_HisBarDetail>
    <%
      /*double tempPayment=0d;//�ѽ�����
      long tempAmount=0l;//�ɽ�����
      long tempLastAmount=0l;//ʣ�����
      double tempPrice=0d;//�ɽ��۸�
      double tempSecurity=0d;//��֤��ϵ��
	  double curAmount=0d;//���������Ч������
      double listGoodsTotal=0d;//������������������
	  String userCode=null;//���״���
	  int marketId=0;//�����г�
	  String contractId=null;//��ͬ��
	  ResultSet rs=null;
	  DBBean bean=null;
	  StringBuffer sql=null;
	  try{
	  bean=new DBBean(JNDI);
	  //��ͬ��
      if(pageContext.getAttribute("contractID")!=null&&!"".equals(pageContext.getAttribute("contractID"))){
         contractId=pageContext.getAttribute("contractID").toString();
      }

	  //���ͬ����
	  sql=new StringBuffer();
	  sql.append("select sum(money) as m from (select ID,InfoDate,FirmID,Operation,ContractNo,Money");
	  sql.append(",Balance,Overdraft,FrozenCapital from dailymoney where contractno="+contractId+" ");
	  sql.append("and operation=506 UNION select ID,InfoDate,FirmID,Operation,ContractNo,Money");
	  sql.append(",Balance,Overdraft,FrozenCapital from hismoney  where contractno="+contractId+" ");
	  sql.append("and operation=506)");
	  rs=bean.executeQuery(sql.toString());
	  if(rs.next()){
	      tempPayment=rs.getDouble("m");
	  }
	  rs.close();

      if(pageContext.getAttribute("amount")!=null&&!"".equals(pageContext.getAttribute("amount"))){
        tempAmount=Long.parseLong(pageContext.getAttribute("amount").toString());
      }
      if(pageContext.getAttribute("lastAmount")!=null&&!"".equals(pageContext.getAttribute("lastAmount"))){
        tempLastAmount=Long.parseLong(pageContext.getAttribute("lastAmount").toString());
      }
      if(pageContext.getAttribute("price")!=null&&!"".equals(pageContext.getAttribute("price"))){
        tempPrice=Double.parseDouble(pageContext.getAttribute("price").toString());
      }
      if(pageContext.getAttribute("security")!=null&&!"".equals(pageContext.getAttribute("security"))){
        tempSecurity=Double.parseDouble(pageContext.getAttribute("security").toString());
      }
	  
      //curAmount=tempPayment-(tempAmount-tempLastAmount)*tempPrice;
	  curAmount=tempPayment;
	  listGoodsTotal=curAmount/(tempPrice-tempSecurity/tempAmount);
	  listGoodsTotal=ManaUtil.round(listGoodsTotal,4);
      if(listGoodsTotal<0){
          listGoodsTotal=0;
      }
      
	  if(listGoodsTotal>tempAmount){
	      listGoodsTotal=tempAmount;
	  }

	  }
	  catch(Exception e)
      {
  	      e.printStackTrace();
          errOpt();
      }
	  finally{
         try{if(rs!=null)rs.close();}catch(Exception ex){}
    	 if(bean!=null)bean.close();
      }*/
    %>
	<BR>
    </span>
	</fieldset>
	<br>
	<fieldset>
		<legend>��ͬ״̬����</legend>
		<BR>
		<span>
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
     	    <input type="hidden" name="id" value="${param.id}">
     	    <tr height="25">
     		     <td align="right">��ͬ״̬��</td>
				 <td align="left">
				 <%
				    int status=Integer.parseInt(pageContext.getAttribute("status").toString());
				 %>
				 <input type="radio" name="finished" <c:if test="${status==0}">checked</c:if>  checked id="f_0" value="0" onclick="changeStatusType(this.value);"><%=ManaUtil.retConStatus(0)%>&nbsp;&nbsp;
				 <input type="radio" name="finished" <c:if test="${status==1}">checked</c:if> id="f_1" value="1" onclick="changeStatusType(this.value);"><%=ManaUtil.retConStatus(1)%>&nbsp;&nbsp;
				 <input type="radio" name="finished"  <c:if test="${status==2}">checked</c:if> id="f_2" value="<%=2%>" onclick="changeStatusType(this.value);"><%=ManaUtil.retConStatus(2)%>
     		     </td>
				 <td>
     	    </tr>
			<tr height="25" id="resultSpan">
               <td align="right">ִ�н����</td>
			   <td align="left">
				 <input type="radio" name="result" value="0" id="0" <c:if test="${result==0}">checked</c:if>  onclick="changeResult(this.value);">������Լ&nbsp;&nbsp;
				 <input type="radio" name="result" value="1"  id="1" <c:if test="${result==1}">checked</c:if>  onclick="changeResult(this.value);">��ΥԼ&nbsp;&nbsp;
				 <input type="radio" name="result" value="2"  id="2" <c:if test="${result==2}">checked</c:if>  onclick="changeResult(this.value);">����ΥԼ&nbsp;&nbsp;
			   </td>
			</tr>
            <tr height="25" id="amountSpan">
				<td align="right">
			     ʵ���������(${param.str6})��
			   </td>
			   <td align="left">
			    <input type="text" class="text" name="amount" value="${actualAmount}" onchange="controlExauAmountExt();">
			   </td>
			</tr>
			<tr height="25" id="breakSpan">
               <td align="right">
			     ΥԼ����(${param.str6})��
			   </td>
			   <td align="left">
                 <input type="text" class="text" name="breakAmount" value="${fellBackAmount}" onchange="controlExauAmountExt();">
			   </td>
			</tr>
			<tr height="25" id="breakSpan">
               <td align="right">
			     �������(${param.str6})��
			   </td>
			
			   <td align="left">
                 <input type="text" class="text" name="exauAmount" <c:if test="${status!=1}">value="0"</c:if> readonly>
			   </td>
			</tr>
			<tr height="25" id="amountSpan">
               <td align="right">
			     ��ע��
			   </td>
			   <td align="left">
                 <textarea name="remark" class="normal" readonly>${remark}</textarea>
			   </td>
			</tr>

			<input type="hidden" name="tradeAmount" value="${amount}">

			<tr>
          	<td width="100%" colspan="2"><br>
			<c:if test="${status<3}">
			<div align="center">
			<input name="back" type="button" onclick="window.close()" class="btn" value="ȡ��">
            </div>
			</c:if>
			</td>
          </tr>
     </table>
	 </span>
     </fieldset>
     <table border="0" cellspacing="0" cellpadding="0" width="100%" class="Noprint">
          <tr height="25">
          	<td width="40%"><div align="center">
             <input type="hidden" name="opt">
            </div></td>
          </tr>
     </table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
//��ӻ���
function addGoodsView(contractID,price,listGoodsTotal){
  var tempLiftAmount=frm.tempLiftAmount;
  var liftedAmount=0;
  var mostLiftAmount=0;
  if(!tempLiftAmount){
    liftedAmount=0;
  }else{
    if(tempLiftAmount.length>1){
      for(i=0;i<tempLiftAmount.length;i++){
	    liftedAmount=liftedAmount+parseInt(tempLiftAmount[i].value);
	  }
    }else{
      liftedAmount=parseInt(tempLiftAmount.value);
    }
   }
  mostLiftAmount=parseInt(listGoodsTotal)-liftedAmount;
  if(mostLiftAmount<=0){
    alert("��������������ֵΪ0,������ӻ����¼!");
	return false;
  }else{
var a=openDialog("addGoods.jsp?contractID="+contractID+"&price="+price+"&listGoodsTotal="+mostLiftAmount+"","_blank","600","450");
  if(1==a)
    window.location.reload();
  }
}

//�鿴���ⵥ��Ϣ
function viewRecord(commodityID,outID,capital,amountTotal,id,finished){  window.open("addRecord.jsp?commodityID="+commodityID+"&outID="+outID+"&capital="+capital+"&amountTotal="+amountTotal+"&id="+id+"&finished="+finished+"","_blank","width=700,height=550,scrollbars=yes");	
}


function refreshParent() {
	window.opener.location.href = window.opener.location.href;
	if (window.opener.progressWindow) window.opener.progressWindow.close();
	window.close();
	}

//�ж�ִ�н��ѡ��
function judgeResult(){
  if(document.getElementById("0").checked==false&&document.getElementById("1").checked==false
	  &&document.getElementById("2").checked==false){
    alert("ִ�н������Ϊ��!");
	return false;
  }else if(document.getElementById("1").checked==true
	  ||document.getElementById("2").checked==true){
    if(Trim(frm.breakAmount.value)==""){
	  alert("ΥԼ��������Ϊ��!");
	  frm.breakAmount.focus();
	  return false;
	}else if(Trim(frm.amount.value)==""){
	  alert("ʵ�������������Ϊ��!");
	  frm.amount.focus();
	  return false;
	}else{
	  return true;
	}
  }else{
    if(Trim(frm.amount.value)==""){
	  alert("ʵ�������������Ϊ��!");
	  frm.amount.focus();
	  return false;
	}else{
	  return true;
	}
  }
}
//���ú�ͬ״̬
function controlStatus(){
  if(!document.getElementById("f_1")){
    if(userConfirm()){
       frm.opt.value="controlStatus";	
       frm.submit();
    }else{
       return false;
    }
  }else{
    if(document.getElementById("f_1").checked==true){
	  if(judgeResult()){
	    if(userConfirm()){
          frm.opt.value="controlStatus";	
          frm.submit();
        }else{
          return false;
        }
	  }else{
	    return false;
	  }
    }else{
	  if(userConfirm()){
         frm.opt.value="controlStatus";	
         frm.submit();
      }else{
        return false;
      }
	}
  }
}

//����ֻ��������Լ������Լ�ſ�����д����
function changeStatusType(v){
  //if(parseInt(v)==1){
    //document.getElementById("resultSpan").style.display="inline";
    //document.getElementById("breakSpan").style.display="inline";
	//document.getElementById("amountSpan").style.display="inline";
  //}else{
	//document.getElementById("resultSpan").style.display="none";
    //document.getElementById("breakSpan").style.display="none";
	//document.getElementById("amountSpan").style.display="none";
  //}
  if(parseInt(v)==0){
    document.getElementById("f_2").disabled=true;
	document.getElementById("f_3").disabled=true;
	controlResult(3);
  }else if(parseInt(v)==1){
    //document.getElementById("f_0").disabled=true;
	document.getElementById("f_3").disabled=true;
	//document.getElementById("f_2").disabled=false;
	controlResult(1);
	controlExauAmountExt();
  }else if(parseInt(v)==2){
    document.getElementById("f_0").disabled=true;
	//document.getElementById("f_1").disabled=true;
	//document.getElementById("f_3").disabled=false;
	controlResult(0);
  }else if(parseInt(v)==3){
    document.getElementById("f_0").disabled=true;
	document.getElementById("f_1").disabled=true;
	//document.getElementById("f_2").disabled=true;
	controlResult(0);
  }
}

//����ִ�н����Ч��Ч1:��Ч0:��Ч
function controlResult(v){
  if(parseInt(v)==0){
    document.getElementById("1").disabled=true;
	document.getElementById("2").disabled=true;
	document.getElementById("0").disabled=true;
	frm.breakAmount.readOnly=true;
	frm.amount.readOnly=true;
	frm.exauAmount.value=isFormat(parseFloat(frm.tradeAmount.value)-
		(parseFloat(frm.breakAmount.value)+parseFloat(frm.amount.value)),4);
  }else if(parseInt(v)==1){
    document.getElementById("1").disabled=false;
	document.getElementById("2").disabled=false;
	document.getElementById("0").disabled=false;
	if(document.getElementById("0").checked==true){
	  frm.amount.readOnly=false;
	  frm.breakAmount.value="0";
	  frm.breakAmount.readOnly=true;
	}else if(document.getElementById("1").checked==true){
	  frm.amount.readOnly=false;
	  frm.breakAmount.readOnly=false;
	}else if(document.getElementById("2").checked==true){
	  frm.amount.readOnly=false;
	  frm.breakAmount.readOnly=false;
	}
  }else if(parseInt(v)==3){
    document.getElementById("1").disabled=true;
	document.getElementById("2").disabled=true;
	document.getElementById("0").disabled=true;
	frm.breakAmount.readOnly=true;
	frm.amount.readOnly=true;
	frm.breakAmount.value="0";
	frm.amount.value="0";
	frm.exauAmount.value="0";
  }
}

//����ѡ��ִ�н��
function changeResult(v){
  if(parseInt(v)==0){
    frm.breakAmount.readOnly=true;
	frm.breakAmount.value="0";
	frm.amount.readOnly=false;
	//�����������
	controlExauAmount();
  }else if(parseInt(v)==1){
    frm.breakAmount.readOnly=false;
	frm.amount.readOnly=false;
	//frm.breakAmount.value="";
	//document.getElementById("0").disabled=true;
	//document.getElementById("2").disabled=false;
  }else if(parseInt(v)==2){
    frm.breakAmount.readOnly=false;
	frm.amount.readOnly=false;
	//frm.breakAmount.value="";
	//document.getElementById("0").disabled=true;
	//document.getElementById("1").disabled=true;
  }
}

//���ƼӼ��������(��ҳ��ˢ��ʱ��ȡ���ݿ��ͬ״̬)
function controlExauAmount(){
  tradeAmount=0;
  amount=0;
  breakAmount=0;
  status='${status}';
  if(parseInt(status)==1){
      if(Trim(frm.tradeAmount.value)!=""){
          tradeAmount=frm.tradeAmount.value
      }
      if(Trim(frm.amount.value)!=""){
          amount=frm.amount.value
      }
      if(Trim(frm.breakAmount.value)!=""){
          breakAmount=frm.breakAmount.value
      }
      frm.exauAmount.value=parseInt(tradeAmount)-(parseInt(breakAmount)+parseInt(amount));
  }
}

//���ƼӼ��������(��ҳ�����ʱ��ȡҳ���ͬ״̬)
function controlExauAmountExt(){
  tradeAmount=0;
  amount=0;
  breakAmount=0;
  if(document.getElementById("f_1").checked==true){
      if(Trim(frm.tradeAmount.value)!=""){
          tradeAmount=frm.tradeAmount.value
      }
      if(Trim(frm.amount.value)!=""){
          amount=frm.amount.value
      }
      if(Trim(frm.breakAmount.value)!=""){
          breakAmount=frm.breakAmount.value
      }
      frm.exauAmount.value=parseInt(tradeAmount)-(parseInt(breakAmount)+parseInt(amount));
  }
}
</SCRIPT>
<!--��ˢ��ҳ��ʱ,����ֻ�������ϲſ�����д����-->
<script language="javascript">
   var status='${status}';
   //if(parseInt(status)==1){
   // document.getElementById("resultSpan").style.display="inline";
   // document.getElementById("breakSpan").style.display="inline";
   // document.getElementById("amountSpan").style.display="inline";
   //}else{
   //  document.getElementById("resultSpan").style.display="none";
   //  document.getElementById("breakSpan").style.display="none";
   // document.getElementById("amountSpan").style.display="none";
   // }
 //frm.exauAmount.value=parseInt(frm.tradeAmount.value)-(parseInt(frm.breakAmount.value)+parseInt(frm.amount.value));
  //���ƼӼ��������
  //controlExauAmount();
  if(parseInt(status)==0){
    document.getElementById("f_2").disabled=true;
	document.getElementById("f_3").disabled=true;
	controlResult(3);
  }else if(parseInt(status)==1){
    document.getElementById("f_0").disabled=true;
	document.getElementById("f_3").disabled=true;
	document.getElementById("f_2").disabled=false;
	controlResult(1);
  }else if(parseInt(status)==2){
    document.getElementById("f_0").disabled=true;
	document.getElementById("f_1").disabled=true;
	controlResult(0);
  }
</script>
<%@ include file="/vendue/manage/public/footInc.jsp" %>
<%
}
catch(Exception e)
{
	System.out.println(e.toString());
}
%>