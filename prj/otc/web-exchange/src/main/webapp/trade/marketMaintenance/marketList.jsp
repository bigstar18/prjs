<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html xmlns:MEBS>
  <head>
    <title>�����쳣</title>
	<%@ include file="/public/ecsideLoad.jsp"%>
	<IMPORT namespace="MEBS" implementation="${basePath}/common/jslib/calendar.htc"/>
	
	<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'></script>
	<script type='text/javascript' src='<%=basePath%>/dwr/util.js'></script>
    <script type="text/javascript" src='<%=basePath%>/dwr/interface/checkPrice.js' /></script>
  </head>
  
  <body>
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		  
		    <div class="div_tj">
			  <form name="frm" action="${basePath}/marketMaintenance/marketLog/list.action" method="post">
			    <table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				  <tr>
				    <td style="height: 40px; width: 100%;">
					  <div class="div2_top">
						<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" width="100%">
						  <tr>
					        <td class="table3_td_1" align="left">
					                            ��Ʒ����:&nbsp;
							   <label>
							     <input type="text" class="input_text"  name="${GNNT_}commodityId[like]" size="14" 
							            value="${oldParams['commodityId[like]'] }"/>
							   </label>
							</td>
							<td align="left" width="265">
							  &nbsp;����۸�:&nbsp;
							  <label>
								<input type="text" name="${GNNT_}quoprice[=][double]"  class="input_text"
								 value="${oldParams['quoprice[=][double]'] }"/>
							  </label>
							  &nbsp;<font color="red">(��Ԫ/��˾)</font>
							</td>
							<td  align="left" >
									��ʼʱ�䣺
							  <input type="text" style="width: 160px" id="startDate"
									 class="wdate" maxlength="10"
									 name="${GNNT_}actionTime[>=][timestemp]"
									 value='${oldParams["actionTime[>=][timestemp]"]}'
									 onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})">
							</td>

							<td align="left">
									����ʱ�䣺
							  <input type="text" style="width: 160px" id="endDate"
									 class="wdate" maxlength="10"
									 name="${GNNT_}actionTime[<=][timestemp]"
									 value='${oldParams["actionTime[<=][timestemp]"]}'
							         onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})">
							</td>
							<td class="table3_td_anniu" align="right">
							  <button class="btn_sec" onClick="search1()">��ѯ</button>
							  &nbsp;&nbsp;
							  <button class="btn_cz" onClick="myReset()">����</button>
							</td>
						  </tr>
						</table>
					  </div>
					</td>
				  </tr>
			    </table>
			  </form>
		    </div>
		    
		    <div class="div_gn">
		      <div style="float: left;">
	            <button class="anniu_btn" onclick="addLog()" id="add">���</button>
	          </div>
	          
	          <div style="float: left; margin-left: 50px;">
	          <form name="frm_add" action="${basePath}/marketMaintenance/marketLog/add.action" method="post">
	                                ��Ʒ��
	            <select id="commodityId" name="commodityId" class="input_textmin" >
				  <option value="">��ѡ��</option>
				  <c:forEach items="${commodityList}" var="commodity">
					<option value="${commodity.id}">${commodity.id }</option>
				  </c:forEach>
				</select>
	             &nbsp;&nbsp;&nbsp;&nbsp;
	                                    ����۸�
	               <input type="text" id="quoprice"  name="quoprice" class="input_text"  onchange="price('quoprice')"  />
	                &nbsp;<font color="red">(��Ԫ/��˾)</font> 
	                <input style="display:none"/>
	          </form>
	           
	           </div>
	        </div>
	                 
	        <div class="div_list">
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="resultList"
							  autoIncludeParameters="${empty param.autoInc}"
							  var="market"
							  action="${basePath}/marketMaintenance/marketLog/list.action"
							  xlsFileName="marketLogList.xls" 
			                  csvFileName="marketLogList.csv"
							  title="" minHeight="345" listWidth="100%"
							  retrieveRowsCallback="limit" sortRowsCallback="limit"
							  filterRowsCallback="limit" 	style="table-layout:fixed" >
					  <ec:row >

						<ec:column width="4%" property="_0" title="���"
								   value="${GLOBALROWCOUNT}" sortable="false"
								   filterable="false" style="text-align:center; "/>		 
						<ec:column width="20%" property="commodityId[like]" title="��Ʒ����"
							       style="text-align:center; ">
								   ${market.commodityId }
					    </ec:column>
						<ec:column property="quoprice[=][double]" title="����۸�(��Ԫ/��˾)"
								   width="20%" style="text-align:left; " >
						    <fmt:formatNumber value="${market.quoprice}" pattern="#,##0.0000" />
						</ec:column>
						<ec:column property="actionTime[=][timestemp]" title="����ʱ��"
								   width="20%" style="text-align:center; ">
								 ${ datefn:formatdate(market.actionTime)}
						</ec:column>
						<ec:column width="20%" property="operator[like]" title="¼��Ա"
							       style="text-align:center; ">
								   ${market.operator }
					    </ec:column>
												
					  </ec:row>
					</ec:table>
				  </td>
				</tr>
			  </table>
			</div>
	          
		  </td>
		</tr>
	  </table>
    </div>
    
        <!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
		<!-- �༭�͹�����ʹ�õĲ���ģ�� -->
		<textarea id="esc_fundFlowType" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="fundFlowType[=]">
			<ec:options items="fundFlowMap" />
		</select>
	    </textarea>
	    
	  <div style="margin-left: 20px; margin-top: 10px;">
	    <font color="red" size="2">
	                   ��ҳ�油¼���ݾ�Ϊ�������飬�����ز���!
	    </font>
	  </div>
  </body>
</html>

<script type="text/javascript" >
   function search1(){
	 
	   frm.submit();
   }
  
   function addLog(){
		  var flag = true;
			flag = myblur("all")
			if (flag) {
				if(!isFormChanged(frm_add,null)){
					alert("û���޸�����");
					return false;
				}
				var vaild = affirm("��ȷ��Ҫ������");
				if (vaild == true) {
					frm_add.submit();
				} else {
					return false;
				}
			}
	  }
    function myblur(userID) {
		var flag = true;
		if ("commodityId" == userID) {
			flag = commodity(userID);
		} else if ("quoprice" == userID) {
			flag = price(userID);
		} else {
			if (!commodity("commodityId")){
				flag = false;
			}else if (!price("quoprice")){
				flag = false;
			}
			
		}
		
		return flag;
	}
    function commodity(userID){
		var flag = false;
		var str = "��Ʒ";
		var innerHTML = "";
		var user = document.getElementById(userID);
		if (isEmpty(user.value)) {
			innerHTML = str + "����Ϊ��";
			alert(innerHTML);
			user.focus();
		} else {
			flag = true;
		}
		return flag;
	}
  function price(userID){
		var flag = false;
		var str = "����۸�";
		var innerHTML = "";
		var user = document.getElementById(userID);
		if (isEmpty(user.value)) {
			innerHTML = str + "����Ϊ��";
			alert(innerHTML);
			user.focus();
		}else if(!IsIntOrFloat(user.value)){
			innerHTML = "���ǷǸ���";	
			alert(innerHTML);
			user.focus();
		}else if(intByNum(user.value,12)){
			innerHTML = "С����ǰ�������12λ";	
			alert(innerHTML);
		}else if(!flote(user.value,4)){                                                       
			innerHTML = "���4λС��������";	
			alert(innerHTML);
			user.focus();
		} else {
			flag = true;
		}
		priceScope();
		
		return flag;
	}

	function priceScope(){
		var id = document.getElementById("commodityId").value;
		var prices = document.getElementById("quoprice").value;

		  if(prices){
				checkPrice.judgePrice(id,prices, function(isExist){
					if(!isExist){
						alert('������ϱ�׼������۸�');
						document.getElementById("quoprice").value="";
						document.getElementById("quoprice").focus();
					}
				});
		 }	 
	}
 
    //�ж��Ƿ�Ϊ�Ǹ�����������������������
	function IsIntOrFloat(num){
	  var reNum=/(^\d)\d*(\.?)\d*$/;
	  return (reNum.test(num));
	}
    //�ж�����λ
	function intByNum(str,n){
		var flag=false;
		if(str.length>0){
			var strs=new Array();
			strs=str.split(".");
			if(strs.length==1){
				if(str.length>n){
					flag=true;
				}
			}else if(strs.length==2){
				var s=strs[0];
				if(s.length>n){
					flag=true;
				}
			}
		}
		return flag;
	}

</script>