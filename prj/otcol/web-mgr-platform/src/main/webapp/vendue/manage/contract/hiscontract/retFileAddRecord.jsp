<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/vendue/manage/globalDef.jsp"%>

<html>
  <head>
	<title>出库单信息</title>
  </head>
   <%
	int this_tradeunit = Integer.valueOf(request.getParameter("this_tradeunit"));
	BigDecimal tradeunit=new BigDecimal(request.getParameter("this_tradeunit"));
%>
<c:if test="${not empty param.add}">
</c:if>

<body>
<form name=frm id=frm action="addRecord.jsp" method="post" targetType="hidden" callback='refreshDialog();'>
		<fieldset width="100%">
		<legend>出库信息</legend>
		<BR>
		<span>
		<c:set var="contractID" value=""/>
		<c:set var="payment" value=""/>
		<c:set var="price" value=""/>
		<!--商品号-->
		<c:set var="commodityID" value=""/>
		<!--存储点-->
		<c:set var="str3" value=""/>
		<!--合同状态-->
		<c:set var="status" value=""/>
		<!--出库单号-->
        <c:set var="outId" value=""/>
		<db:select var="v_result" table="v_outlog" columns="outid" where="id=${param.outlogId}">
            <c:set var="outId" value="${v_result.outid}"/>
		</db:select>
		<db:select_HisBarDetail var="row" where="u1.commodityid=${param.commodityID} and u1.commodityid=u3.id and u3.id=u4.commid and u2.firmid=u1.userid">
		  <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
		  	<c:set var="contractID" value="${row.contractid}"/>
		  	<c:set var="price" value="${row.price}"/>
		  	<c:set var="commodityID" value="${row.id}"/>
		  	<input type="hidden" name="outID" value="${outId}">
			<input type="hidden" name="outlogId" value="${param.outlogId}">
		  	<input type="hidden" name="LOGINID" value="${LOGINID}">
		  	<input type="hidden" name="commodityID" value="${param.commodityID}">
		  	<input type="hidden" name="capital" value="${param.capital}">
		  	<input type="hidden" name="amountTotal" value="${param.amountTotal}">
			<input type="hidden" name="str3" value="${row.str3}">
			<input type="hidden" name="contractID" value="${row.contractid}">
			<input type="hidden" name="codePro" value="${row.str6}">
			<input type="hidden" name="belongMarket" value="${row.str7}">
			<input type="hidden" name="tradeunit" value="${row.tradeunit}">
			<c:set var="str3" value="${row.str3}"/>
			<c:set var="str4" value="${row.str4}"/>
			<c:set var="status" value="${row.status}"/>
            <tr height="25">
                <td align="right"> 编号 ：</td>
                <td align="left">
                	${outId}
                </td>
                <td align="right"> 标的号 ：</td>
                <td align="left">
                	${row.code}
                </td>
        </tr>
        <tr height="25">
                <td align="right"> 买方编号 ：</td>
                <td align="left">
                <c:choose>
				<c:when test="${row.trademode==0}">
                ${row.userid}
				</c:when>
				<c:when test="${row.trademode==1}">
				${row.bluserid}  
				</c:when>
				<c:when test="${row.trademode==2}">
				${row.bluserid}  
				</c:when>
				<c:otherwise>
				</c:otherwise>
				</c:choose>
                </td>
                <td align="right">卖方编号 ：</td>
                <td align="left">
				<c:choose>
				<c:when test="${row.trademode==0}">
                ${row.bluserid}
				</c:when>
				<c:when test="${row.trademode==1}">
				${row.userid}  
				</c:when>
				<c:when test="${row.trademode==2}">
				${row.userid}  
				</c:when>
				<c:otherwise>
				</c:otherwise>
				</c:choose>
				</td>
        </tr>
        <tr height="25">
				<td align="right"> 本次出库总数量(${param.str6}) ：</td>
                <td align="left">
                	${param.amountTotal*row.tradeunit}
                </td>
               
                <td align="right"> 对应的货款数 ：</td>
                <td align="left">
                	<fmt:formatNumber value="${param.capital}" pattern="<%=FUNDPATTERN%>"/>
                </td>
        </tr>
        </table>	
    </db:select_HisBarDetail>
	<!--查出库单状态-->
	<c:set var="finished" value=""/>
	<c:set var="remark" value=""/>
	<db:select var="v_result" table="v_outlog" columns="finished,amount,remark" where="id=${param.outlogId}">
      <c:set var="finished" value="${v_result.finished}"/>
	  <c:set var="remark" value="${v_result.remark}"/>
	</db:select>
			<BR>
        </span>
		</fieldset>
		<fieldset width="100%">
		<legend>生成出库单</legend>
		<BR>
		<span>
		<table id="table1" border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
          <tr height="25">
          <td align="center"><b>货物存放地</b></td>
          <td align="center"><b>品种</b></td>
		  <td align="center"><b>提货数量(${param.str6})</b></td>
          <td align="center"><b>总数量(${param.str6})</b></td>
		  <td align="center"><b>已提数量(${param.str6})</b></td>

          </tr>
          <c:set var="delRow" value="0"/>
		  <!--添加时直接从商品表把对应的存储库点,品种从商品表查出来,下面变量是做是添加还是已经添加过的标志-->
		  <c:set var="modFlag" value="false"/>
   <%
		  
		    String str3=pageContext.getAttribute("str3").toString();
			String str4=pageContext.getAttribute("str4").toString();
			String contractID=pageContext.getAttribute("contractID").toString();
			String outLogId=delNull(request.getParameter("outlogId"));
			//if(pageContext.getAttribute("outId")!=null){
			//    outID=pageContext.getAttribute("outId").toString();
			//}
	%>
	<%
		   DBBean bean=null;
		   ResultSet rsAmount=null;
		   ResultSet rs=null;
		   try{
		     //将组合字符串组合存放到hashmap中
			 HashMap map=new HashMap();
			 //if(pageContext.getAttribute("str3")!=null){//判断取到值是否为空
			    /*String[] semi=str3.split(";");
                  for(int i=0;i<semi.length;i++){
                    String[] chirld=semi[i].split(",");
                    if(chirld!=null){ if(chirld[0]!=null&&!"".equals(chirld[0])){//判断存储库点是否为空,如是为空,则不显示
					   Double oriAmount=null;
					   Double total=null;
					   if(map.containsKey(chirld[0])){
					     oriAmount=(Double)map.get(chirld[0]);
						 map.put(chirld[0],new Double(oriAmount.doubleValue()+Double.parseDouble(chirld[8])));
					   }else{
					     map.put(chirld[0],new Double(Double.parseDouble(chirld[8])));
					   }
					}
					}
				  }*/
			   //}
			 map=ManaUtil.addPlaceAmount(str3,tradeunit);
			 if(ManaUtil.checkStr(outLogId)){
			   bean=new DBBean(JNDI);
			   rs=bean.executeQuery("select place,variety,amount from v_outrecord where outlogid="+outLogId+"");
			   while(rs.next()){
			     String tempPlace=rs.getString("place");
				 BigDecimal tempTotal=new BigDecimal(0);
				 if(map.get(tempPlace)!=null){
			         tempTotal=new BigDecimal(map.get(tempPlace).toString());
				 }
			%>
          	 <c:set var="delRow" value="${delRow+1}"/>
			 <c:set var="modFlag" value="true"/>
          	 <tr height="25">
          	 	<td align="center"><input name="place" type="text" class="text" style="width: 150px;" value="<%=delNull(tempPlace)%>" readonly></td>
          	    <td align="center"><input name="variety" type="text" class="text" style="width: 150px;" value="<%=delNull(str4)%>" readonly></td>
          	    <td align="center"><input name="amount" type="text" class="text" style="width: 150px;" value="<%=ManaUtil.disBD(rs.getBigDecimal("amount"))%>">
				</td>
				<!--记录原来的提货数量，以便判断所输入的数量不能大于(总数量减去已提货数量)-->
				<input type="hidden" name="oldAmount" value="<%=ManaUtil.disBD(rs.getBigDecimal("amount"))%>">
				<td align="center"><%=ManaUtil.accuracyNum(tempTotal,".####")%>
				<input type="hidden" name="tempTotal" value="<%=ManaUtil.disBD(tempTotal)%>"></td>
				<td align="center">
				<%
				  //求整个合同在同一个存储地点上已提货数量
				  BigDecimal tempAmount=null;
				  StringBuffer sqlAmount=new StringBuffer();
				  sqlAmount.append("select sum(t2.amount) as n from v_outlog t1,v_outrecord t2 where");
				  sqlAmount.append(" t1.contractid="+contractID+" and t1.id=t2.outlogid ");
				  sqlAmount.append("and t2.place='"+tempPlace+"'");
				  rsAmount=bean.executeQuery(sqlAmount.toString());
				  if(rsAmount.next()){
				    tempAmount=rsAmount.getBigDecimal("n");
				  }
				  rsAmount.close();
				  bean.closeStmt();
				%>
				<%=ManaUtil.disBD(tempAmount)%>
				
				<input type="hidden" name="templiftAmount" value="<%=ManaUtil.disBD(tempAmount)%>"></td>
				<!--
				<td align="center"><span onclick="this.parentNode.parentNode.removeNode(true);" style="cursor:hand">删除</span></td>-->
          	 </tr>
         <%
		   }
		   //关闭数据源
		   rs.close();
		   bean.close();
		}
		%>
		  <c:if test="${modFlag=='false'}">
			<%
				  Set entries=map.entrySet();
		          Iterator ite=entries.iterator();
			      //DBBean beanAmount=new DBBean(JNDI);
			      //ResultSet rsAmount=null;
				  while(ite.hasNext()){
					Map.Entry entry=(Map.Entry)ite.next();
				    String tempPlace=entry.getKey().toString();
					String tempAmount=entry.getValue().toString();
			%>
                <tr height="25">
                   <td align="center"><input name="place" type="text" class="text" style="width: 150px;" value="<%=delNull(tempPlace)%>" readonly></td>
				   4<td align="center"><input name="variety" type="text" class="text" style="width: 150px;" value="<%=str4%>" readonly></td>
				   <td align="center"><input name="amount" type="text" class="text" style="width: 150px;" value="0">
				   </td>
				   <!--记录原来的提货数量，以便判断所输入的数量不能大于(总数量减去已提货数量)-->
				   <input type="hidden" name="oldAmount" value="0">
				   <td align="center"><%=delNull(tempAmount)%>
				   <input type="hidden" name="tempTotal" value="<%=delNull(tempAmount)%>">
				   </td>
				   <td align="center">
				   <%
				    //求整个合同在同一个存储地点上已提货数量
				    BigDecimal tempLiftAmount=null;
				    StringBuffer sqlAmount=new StringBuffer();
				    sqlAmount.append("select sum(t2.amount) as n from v_outlog t1,v_outrecord t2 where");
				    sqlAmount.append(" t1.contractid="+contractID+" and t1.id=t2.outlogid ");
				    sqlAmount.append("and t2.place='"+tempPlace+"'");
				    rsAmount=bean.executeQuery(sqlAmount.toString());
				    if(rsAmount.next()){
				      tempLiftAmount=rsAmount.getBigDecimal("n");
				    }
				    rsAmount.close();
				    bean.closeStmt();
				   %>
				   <%=ManaUtil.disBD(tempLiftAmount)%>
				   <input type="hidden" name="templiftAmount" value="<%=ManaUtil.disBD(tempLiftAmount)%>">   
				   </td>
			    </tr>
			    <%}
				  //关闭数据源
				  bean.close();   
				%>
		  </c:if>
	<%
	       }
            catch(Exception e)
           {
  	           e.printStackTrace();
               errOpt();
           }
		   finally{
               try{if(rs!=null)rs.close();}catch(Exception ex){}
			   try{if(rsAmount!=null)rsAmount.close();}catch(Exception ex){}
               if(bean!=null)bean.close();
           }
	%>
     </table>

     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="25">
        <td width="40%"><div align="center" class="Noprint">
        <input type="hidden" name="add">
        <input type="hidden" name="addRecordFlag">
        </div></td>
        </tr>
     </table>
     <BR>
        </span>
     </fieldset>
		<br>
		<fieldset>
		<legend>出库单操作状态</legend>
		<BR>
		<span>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
     	    <tr height="25">
     		     <td>出库单操作状态：
				 <input type="radio" name="finished" <c:if test="${finished==0}">checked</c:if> value="0" <c:if test="${finished>0}">disabled</c:if>>未生成出库单&nbsp;&nbsp;
     		     <input type="radio" name="finished" id="r_1" <c:if test="${finished==1}">checked</c:if> value="1"  <c:if test="${finished>1}">disabled</c:if>>已生成出库单&nbsp;&nbsp;
				 <input type="radio" name="finished" id="r_2"  <c:if test="${finished==2}">checked</c:if> value="2">已审核
     		     </td>
				 <input type="hidden" name="outlogStatus" value="${finished}">
     	    </tr>
          <tr height="25">
          	<td width="40%"><div align="center" class="Noprint">
			     
            </div></td>
          </tr>
     </table>
    </span>
  </fieldset>
  <fieldset>
	 <legend>保存备注</legend>
	 <BR>
	 <span>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
     	<tr height="35">
           <td align="center" valign="middle"> 备注 ：
             <textarea name="remark" class="normal" readonly>${remark}</textarea>
           </td>
        </tr>
		<tr height="25">
          	<td width="100%"><div align="center" class="Noprint">
			</td>
		</tr>
	 </table>
    </span>
  </fieldset>
  <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="25">
          	<td width="40%"><div align="center" class="Noprint">
			      <br><br><br>
			      <input name="back" type="button" onclick="window.close()" class="btn" value="取消">
            </div>
            </td>
          </tr>
  </table>
</form>
</body>
</html>
<script language="javascript">
//var tempRow=0; 
//var maxRows=0; 
function insertRows(){ 
tempRow=table1.rows.length; 
//maxRows=tempRow; 
//tempRow=tempRow+1;
if(table1.rows.length>5){
  alert("最多只能添加5行");
  return;	
}
var Rows=table1.rows;//类似数组的Rows 
var newRow=table1.insertRow(table1.rows.length);//插入新的一行 
var Cells=newRow.cells;//类似数组的Cells
for (i=0;i<4;i++)//每行的3列数据 
{ 
var newCell=Rows(newRow.rowIndex).insertCell(Cells.length); 
newCell.align="center"; 
switch (i) 
{ 
case 0 : newCell.innerHTML='<td align="center"><input name="place" type="text" class="text" style="width: 150px;"></td>';break; 
case 1 : newCell.innerHTML='<td align="center"><input name="variety" type="text" class="text" style="width: 150px;"></td>'; break; 
case 2 : newCell.innerHTML='<td align="center"><input name="amount" type="text" class="text" style="width: 150px;"></td>'; break;
case 3 : newCell.innerHTML='<td align="center"><span onclick="this.parentNode.parentNode.removeNode(true);" style="cursor:hand">删除</span></td>'; break;
} 
} 
//maxRows+=1; 
} 
function delTableRow(rowNum){
if (table1.rows.length >rowNum){ 
table1.deleteRow(rowNum); 
} 
} 
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--

function frmChk()
{ if(Trim(frm.tradePartition.value) == "")
	{
		alert("交易模板不能为空！");
		frm.tradePartition.focus();
		return false;
	}
	else 
	{ frm.add.value="true";
		return true;
	}
}

function addRecord(v)
{   var amount=frm.place;
    var amountTotal=0;
	if(!amount){
	  alert("没有分录,不能生成出库单!");
	  return false;	
	}
	if(amount.length>1){
	   for(i=0;i<amount.length;i++){
		   if(Trim(frm.amount[i].value) == ""){
  	         alert("提货数量不能为空！");
		     frm.amount[i].focus();
		     return false;	
  	       }else if(Trim(frm.place[i].value) == ""){
  	         alert("存粮地点不能为空！");
		     frm.place[i].focus();
		     return false;	
  	       }else if(Trim(frm.variety[i].value) == ""){
  	         alert("品种不能为空！");
		     frm.variety[i].focus();
		     return false;	
  	       }
		   else if(parseFloat(frm.amount[i].value)>parseFloat(frm.tempTotal[i].value)){
		      alert("单行提货数量大于总数量!");
		      frm.amount[i].focus();
		      return false;
		   }
		   else if(parseFloat(frm.amount[i].value)>(parseFloat(isFormat((parseFloat(frm.tempTotal[i].value)-parseFloat(frm.templiftAmount[i].value)+parseFloat(frm.oldAmount[i].value)),4)))){
		     alert("所输入的数量大于剩余提货数量!");
		    frm.amount[i].focus();
		    return false;
		   }
		   else{
		     amountTotal=amountTotal+parseFloat(frm.amount[i].value);
		   }
	     }
		 //alert(parseFloat(isFormat(amountTotal,4))+","+frm.amountTotal.value);
		 if(parseFloat(isFormat(amountTotal,4))!=parseFloat(frm.amountTotal.value)){
		   alert("提货数量必须等于本次出库总数量!");
		   return false;
		 }
	     if(userConfirm()){
	       frm.add.value="addRecord";
		   if(v==1){
		       frm.addRecordFlag.value="1";
		   }else if(v==2){
		       frm.addRecordFlag.value="2";
		   }
	       frm.submit();
	     }else{
	       return false;
	     }
  }else{
  	 if(Trim(frm.amount.value) == ""||parseFloat(frm.amount.value)==0){
  	     alert("提货数量不能为空,并且不能为0！");
		 frm.amount.focus();
		 return false;	
  	 }else if(Trim(frm.place.value) == ""){
  	     alert("存粮地点不能为空！");
		 frm.place.focus();
		 return false;	
  	 }else if(Trim(frm.variety.value) == ""){
  	     alert("品种不能为空！");
		 frm.variety.focus();
		 return false;	
  	 }else if(parseFloat(frm.amount.value)>parseFloat(frm.tempTotal.value)){
	     alert("单行提货数量大于总数量!");
	     frm.amount.focus();
	     return false;
	 }else if(parseFloat(frm.amount.value)>(parseFloat(isFormat((parseFloat(frm.tempTotal.value)-parseFloat(frm.templiftAmount.value)+parseFloat(frm.oldAmount.value)),4)))){
	  	     alert("所输入的数量大于剩余提货数量!");
	  		 frm.amount.focus();
	  	     return false;
	 }
	 else{
	   amountTotal=parseFloat(frm.amount.value);
	   if(amountTotal!=parseFloat(frm.amountTotal.value)){
	      alert("提货数量必须等本次出库总数量!");
	      return false;
	   }
  	   if(userConfirm()){
         frm.add.value="addRecord";
		 if(v==1){
		     frm.addRecordFlag.value="1";
		 }else if(v==2){
		     frm.addRecordFlag.value="2";
		 }
         frm.submit();
       }else{
         return false;
       }
     }
  }
}

//设置状态
function controlStatus(){
  var outlogStatus=frm.outlogStatus.value;
  if(parseInt(outlogStatus)==0&&(document.getElementById("r_1").checked==true||document.getElementById("r_2").checked==true)){
    alert("请点击生成出库单置已生成出库单状态!");
	return false;
  }else{
    if(userConfirm()){
      frm.add.value="controlStatus";	
      frm.submit();
     }else{
      return false;
     }	
 }
}

//保存备注
function saveRemark(){
  if(Trim(frm.remark.value) == ""){
    alert("备注不能为空!");
	return false;
  }else{
    if(userConfirm()){
      frm.add.value="remark";	
      frm.submit();
     }else{
      return false;
     }	
 }
}

function addGoodsView(contractID,price){
  var a=openDialog("addGoods.jsp?contractID="+contractID+"&price="+price+"","_blank","420","350");
  if(1==a)
    window.location.reload();
}

//查看出库单信息
function viewRecord(commodityID){
  var a=openDialog("addRecord.jsp?commodityID="+commodityID+"","_blank","600","550");
    if(1==a)
      window.location.reload();	
}

//-->
<!--
function refreshParent() {
	window.opener.location.href = window.opener.location.href;
	if (window.opener.progressWindow) window.opener.progressWindow.close();
	window.close();
	}
//-->
</SCRIPT>
<script language="javascript">
	if(table1.rows.length==1){
	  frm.conStatusBtn.disabled=true;
	  frm.printBtn.disabled=true;	
	}
</script>
<%@ include file="/vendue/manage/public/footInc.jsp" %>