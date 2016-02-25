<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/vendue/manage/globalDef.jsp"%>

<OBJECT id=WebBrowser classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0 width=0></OBJECT>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<c:if test="${not empty param.opt}">
    <%
	    Timestamp timestamp=new Timestamp(System.currentTimeMillis());
	%>
    <c:if test="${param.opt=='print'}">
	  <c:catch var="exceError">
	    <!--收款人表id-->
		<c:set var="id" value=""/>
	    <db:select var="row" table="dual" columns="SP_V_ACCEPTPAYMENT.nextval as seqID">
            <c:set var="id" value="${row.seqID}"/>
        </db:select>
		<db:tranPrintTel
		id="${id}"
		contractid="${param.contractID}"
		name="${param.toName}"
		account="${param.toAccount}"
		province="${param.toProvince}"
		city="${param.toCity}"
		bank="${param.toBank}"
		money="${param.amount}"
		remark="${param.comments}"
		status="1"
		opertime="<%=disDate(timestamp)%>"
		/>
        </c:catch>
        <c:if test="${not empty exceError}">
            <%
	           //异常处理
	           String exceError=pageContext.getAttribute("exceError").toString();
		       log(request,exceError);
		       hintError(out);
	        %>
         </c:if>
	</c:if>
</c:if>
<%
        
        double delta_x = 0;
        double delta_y = 0;
        try{
            delta_x = new Double(request.getParameter("delta_x")).doubleValue()/10;
            delta_y = new Double(request.getParameter("delta_y")).doubleValue()/10;
        }catch(NumberFormatException ex){
        
        }
	double x =0.0 + delta_x;
	double y= -0.15 +delta_y;        
	CheckBean checkForm = new CheckBean();
        try{
            checkForm.populateBean(request);      
%>
<title>dianhui</title>
<style type="text/css">
	.check {
		position:absolute; left:<% out.print(x+0.0);%>cm ; top:0.0cm; width:17cm;height:9cm;text-align:left;border-width:thin;
	}
	.year {
		position:absolute;left:<% out.print(x+6.45);%>cm ; top:<% out.print(y+1.2);%>cm;width:1.2cm; height:0.5cm;text-align:right;
	}
	.month{
		position:absolute;left:<% out.print(x+8.1);%>cm; top:<% out.print(y+1.2);%>cm;width:0.6cm;height:0.5cm;text-align:right;
	}
	.day{
		position:absolute;left:<% out.print(x+9.0);%>cm; top:<% out.print(y+1.2);%>cm;width:0.6cm;height:0.5cm;text-align:right
	}
	.fromName{
		position:absolute;left:<% out.print(x+3.15);%>cm;	top:<% out.print(y+1.8);%>cm;	width:5.1cm;height:0.6cm;text-align:left;font-size:0.36cm;
	}
	.toName{
		position:absolute;	left:<% out.print(x+10.55);%>cm;top:<% out.print(y+1.8);%>cm;	width:5.1cm;height:0.6cm;text-align:left;font-size:0.36cm;
	}
	.fromAccount{
		position:absolute; left:<% out.print(x+3.15);%>cm;	top:<% out.print(y+2.43);%>cm;	width:5.1cm;height:0.6cm;text-align:left;
	}
	.toAccount{
		position:absolute;	left:<% out.print(x+10.55);%>cm;top:<% out.print(y+2.43);%>cm;	width:5.1cm;height:0.6cm;text-align:left;
	}
	.fromProvince{
		position:absolute;	left:<% out.print(x+2.9);%>cm; top:<% out.print(y+3.0);%>cm;	width:1.9cm;height:0.6cm;text-align:center;font-size:0.36cm;
	}
	.toProvince{
		position:absolute;	left:<% out.print(x+10.4);%>cm;top:<% out.print(y+3.0);%>cm;	width:1.9cm;height:0.6cm;text-align:center;font-size:0.36cm;
	}
	.fromCity{
		position:absolute;	left:<% out.print(x+5.2);%>cm;top:<% out.print(y+3.0);%>cm;	width:2.0cm;height:0.6cm;text-align:center;font-size:0.36cm;
	}
	.toCity{
		position:absolute;	left:<% out.print(x+12.8);%>cm;top:<% out.print(y+3.0);%>cm;	width:1.9cm;height:0.6cm;text-align:center;font-size:0.36cm;
	}
	.fromBank{
		position:absolute;left:<% out.print(x+3.15);%>cm; top:<% out.print(y+3.6);%>cm;	width:5.1cm;height:0.6cm;text-align:left;font-size:0.36cm;
	}
	.toBank{
		position:absolute;left:<% out.print(x+10.55);%>cm; top:<% out.print(y+3.6);%>cm;	width:5.1cm;height:0.6cm;text-align:left;font-size:0.36cm;
	}
	.cashInCH{
                position:absolute;left:<% out.print(x+2.65);%>cm; top:<% out.print(y+4.4);%>cm;width:8.5cm;height:0.9cm;text-align:left;font-size:<%=checkForm.getFontSize(8.5,0.5)%>cm;
	}
	.comments{
		position:absolute;left:<% out.print(x+8.89);%>cm; top:<% out.print(y+6.5);%>cm;width:4.9cm;height:0.5cm;text-align:left;font-size:0.36cm;
	}
	.cashYi{
		position:absolute;left:<% out.print(x+11.3);%>cm; top:<% out.print(y+4.7);%>cm;width:0.38cm;height:0.6;text-align:center;
	}
	.cashQianW{
		position:absolute;left:<% out.print(x+11.7);%>cm; top:<% out.print(y+4.7);%>cm;width:0.38cm;height:0.6;text-align:center;
	}
	.cashBaiW{
		position:absolute;left:<% out.print(x+12.08);%>cm; top:<% out.print(y+4.7);%>cm;width:0.38cm;height:0.6;text-align:center;
	}
	.cashShiW{
		position:absolute;left:<% out.print(x+12.48);%>cm; top:<% out.print(y+4.7);%>cm;width:0.38cm;height:0.6;text-align:center;
	}
	.cashWan{
		position:absolute;left:<% out.print(x+12.86);%>cm; top:<% out.print(y+4.7);%>cm;width:0.38cm;height:0.6;text-align:center;
	}
	.cashQian{
		position:absolute;left:<% out.print(x+13.25);%>cm; top:<% out.print(y+4.7);%>cm;width:0.38cm;height:0.6;text-align:center;
	}
	.cashBai{
		position:absolute;left:<% out.print(x+13.63);%>cm; top:<% out.print(y+4.7);%>cm;width:0.38cm;height:0.6;text-align:center;
	}
	.cashShi{
		position:absolute;left:<% out.print(x+14.0);%>cm; top:<% out.print(y+4.7);%>cm;width:0.38cm;height:0.6;text-align:center;
	}
	.cashYuan{
		position:absolute;left:<% out.print(x+14.4);%>cm; top:<% out.print(y+4.7);%>cm;width:0.38cm;height:0.6;text-align:center;
	}
	.cashJiao{
		position:absolute;left:<% out.print(x+14.75);%>cm; top:<% out.print(y+4.7);%>cm;width:0.38cm;height:0.6;text-align:center;
	}
	.cashFen{
		position:absolute;left:<% out.print(x+15.12);%>cm; top:<% out.print(y+4.7);%>cm;width:0.38cm;height:0.6;text-align:center;
	}
</style>
</head>
<body>
<div class="check"> 
  <div class="year"><%=checkForm.getYear()%></div>
  <div class="month"><%=checkForm.getMonth()%></div>
  <div class="day"><%=checkForm.getDay()%></div>
  <div class="fromName"><%=checkForm.getFromName()%></div> 
  <div class="toName"><%=checkForm.getToName()%></div>
  <div class="fromAccount"><%=checkForm.getFromAccount()%></div>
  <div class="toAccount"  ><%=checkForm.getToAccount()%></div>
  <div class="fromProvince"><%=checkForm.getFromProvince()%></div>
  <div class="fromCity"><%=checkForm.getFromCity()%></div>
  <div class="toProvince"><%=checkForm.getToProvince()%></div>
  <div class="toCity"><%=checkForm.getToCity()%></div>
  <div class="fromBank"><%=checkForm.getFromBank()%></div>
  <div class="toBank"><%=checkForm.getToBank()%></div>
  <div class="cashInCH"><%=checkForm.getCashInCH()%></div>
  <div class="cashYi"><%=checkForm.getCashYi()%></div>
  <div class="cashQianW"><%=checkForm.getCashQianW()%></div>
  <div class="cashBaiW"><%=checkForm.getCashBaiW()%></div>
  <div class="cashShiW"><%=checkForm.getCashShiW()%></div>
  <div class="cashWan"><%=checkForm.getCashWan()%></div>
  <div class="cashQian"><%=checkForm.getCashQian()%></div>
  <div class="cashBai"><%=checkForm.getCashBai()%></div>
  <div class="cashShi"><%=checkForm.getCashShi()%></div>
  <div class="cashYuan"><%=checkForm.getCashYuan()%></div>
  <div class="cashJiao"><%=checkForm.getCashJiao()%></div>
  <div class="cashFen"><%=checkForm.getCashFen()%></div>
  <div class="comments"><%=checkForm.getComments()%></div>
</div>
<br>
<%
     }catch(NumberFormatException ex){
         out.println(ex.getMessage());
     }        
%>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
  //window.close();
  //window.print();
  document.all.WebBrowser.ExecWB(6,1);
  window.close();
//-->
</SCRIPT>
