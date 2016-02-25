<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/vendue/manage/globalDef.jsp"%>

<%  //xieying
try
{
	float tradeunit=Float.valueOf(request.getParameter("tradeunit"));//批吨系数

%>
<!--取得参数-->
<html>
  <head>
	<title class="Noprint">合同状态设置</title>
</head>
<c:if test="${not empty param.opt}">
</c:if>
<body>
<form name=frm id=frm action="" targetType="hidden" callback='closeRefreshDialog();' method="post">
        <!--市场号-->
		<input type="hidden" name="marketId" value="${param.marketId}">
		<fieldset width="100%">
		<legend>合同状态设置</legend>
		<BR>
		<span>
		<c:set var="contractID" value=""/>
	    <!--已交货款-->
		<c:set var="payment" value=""/>
		<!--成交数量-->
		<c:set var="amount" value=""/>
		<!--成交价格-->
		<c:set var="price" value=""/>
		<!--商品号-->
		<c:set var="commodityID" value=""/>
		<!--剩余货量-->
		<c:set var="lastAmount" value=""/>
		<!--保证金-->
		<c:set var="security" value=""/>
		<!--合同状态-->
		<c:set var="status" value=""/>
		<!--执行结果-->
		<c:set var="result" value=""/>
		<!--实际提货数量-->
		<c:set var="actualAmount" value=""/>
		<!--违约数量-->
		<c:set var="fellBackAmount" value=""/>
		<!--备注-->
		<c:set var="remark" value=""/>
		<!--交易商代码-->
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
                <td align="right" width="20%"> 标的号 ：</td>
                <td align="left" width="20%">
                	${row.code}
                </td>
                <td align="right" width="20%"> 合同号 ：</td>
                <td align="left" width="20%">
                	${row.contractid}
                </td>
        </tr>
		<tr height="25">
        <td align="right"> 交易用户代码 ：</td>
        <!--<td align="left">${row.usercode}</td>-->
		<td align="left">${row.userid}</td>
        <td align="right"> 交易用户全称 ：</td>
        <td align="left">${row.name}</td>
        </tr>
        </table>	
    </db:select_HisBarDetail>
    <%
      /*double tempPayment=0d;//已交货款
      long tempAmount=0l;//成交数量
      long tempLastAmount=0l;//剩余货量
      double tempPrice=0d;//成交价格
      double tempSecurity=0d;//保证金系数
	  double curAmount=0d;//本次提货有效货款数
      double listGoodsTotal=0d;//本次允许最大提货数量
	  String userCode=null;//交易代码
	  int marketId=0;//所属市场
	  String contractId=null;//合同号
	  ResultSet rs=null;
	  DBBean bean=null;
	  StringBuffer sql=null;
	  try{
	  bean=new DBBean(JNDI);
	  //合同号
      if(pageContext.getAttribute("contractID")!=null&&!"".equals(pageContext.getAttribute("contractID"))){
         contractId=pageContext.getAttribute("contractID").toString();
      }

	  //查合同货款
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
		<legend>合同状态操作</legend>
		<BR>
		<span>
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
     	    <input type="hidden" name="id" value="${param.id}">
     	    <tr height="25">
     		     <td align="right">合同状态：</td>
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
               <td align="right">执行结果：</td>
			   <td align="left">
				 <input type="radio" name="result" value="0" id="0" <c:if test="${result==0}">checked</c:if>  onclick="changeResult(this.value);">正常履约&nbsp;&nbsp;
				 <input type="radio" name="result" value="1"  id="1" <c:if test="${result==1}">checked</c:if>  onclick="changeResult(this.value);">买方违约&nbsp;&nbsp;
				 <input type="radio" name="result" value="2"  id="2" <c:if test="${result==2}">checked</c:if>  onclick="changeResult(this.value);">卖方违约&nbsp;&nbsp;
			   </td>
			</tr>
            <tr height="25" id="amountSpan">
				<td align="right">
			     实际提货数量(${param.str6})：
			   </td>
			   <td align="left">
			    <input type="text" class="text" name="amount" value="${actualAmount}" onchange="controlExauAmountExt();">
			   </td>
			</tr>
			<tr height="25" id="breakSpan">
               <td align="right">
			     违约数量(${param.str6})：
			   </td>
			   <td align="left">
                 <input type="text" class="text" name="breakAmount" value="${fellBackAmount}" onchange="controlExauAmountExt();">
			   </td>
			</tr>
			<tr height="25" id="breakSpan">
               <td align="right">
			     损耗数量(${param.str6})：
			   </td>
			
			   <td align="left">
                 <input type="text" class="text" name="exauAmount" <c:if test="${status!=1}">value="0"</c:if> readonly>
			   </td>
			</tr>
			<tr height="25" id="amountSpan">
               <td align="right">
			     备注：
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
			<input name="back" type="button" onclick="window.close()" class="btn" value="取消">
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
//添加货物
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
    alert("本次允许最大提货值为0,不能添加货物记录!");
	return false;
  }else{
var a=openDialog("addGoods.jsp?contractID="+contractID+"&price="+price+"&listGoodsTotal="+mostLiftAmount+"","_blank","600","450");
  if(1==a)
    window.location.reload();
  }
}

//查看出库单信息
function viewRecord(commodityID,outID,capital,amountTotal,id,finished){  window.open("addRecord.jsp?commodityID="+commodityID+"&outID="+outID+"&capital="+capital+"&amountTotal="+amountTotal+"&id="+id+"&finished="+finished+"","_blank","width=700,height=550,scrollbars=yes");	
}


function refreshParent() {
	window.opener.location.href = window.opener.location.href;
	if (window.opener.progressWindow) window.opener.progressWindow.close();
	window.close();
	}

//判断执行结果选择
function judgeResult(){
  if(document.getElementById("0").checked==false&&document.getElementById("1").checked==false
	  &&document.getElementById("2").checked==false){
    alert("执行结果不能为空!");
	return false;
  }else if(document.getElementById("1").checked==true
	  ||document.getElementById("2").checked==true){
    if(Trim(frm.breakAmount.value)==""){
	  alert("违约数量不能为空!");
	  frm.breakAmount.focus();
	  return false;
	}else if(Trim(frm.amount.value)==""){
	  alert("实际提货数量不能为空!");
	  frm.amount.focus();
	  return false;
	}else{
	  return true;
	}
  }else{
    if(Trim(frm.amount.value)==""){
	  alert("实际提货数量不能为空!");
	  frm.amount.focus();
	  return false;
	}else{
	  return true;
	}
  }
}
//设置合同状态
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

//控制只有卖方履约与买方履约才可以填写数量
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

//控制执行结果有效无效1:有效0:无效
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

//控制选择执行结果
function changeResult(v){
  if(parseInt(v)==0){
    frm.breakAmount.readOnly=true;
	frm.breakAmount.value="0";
	frm.amount.readOnly=false;
	//控制损耗数量
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

//控制加减损耗数量(当页面刷新时用取数据库合同状态)
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

//控制加减损耗数量(当页面操作时用取页面合同状态)
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
<!--当刷新页面时,控制只有提货完毕才可以填写数量-->
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
  //控制加减损耗数量
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