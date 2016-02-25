<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
	String firmID = Tool.delNull(request.getParameter("firmID"));
	String type = Tool.delNull(request.getParameter("type"));
	BankDAO DAO = BankDAOFactory.getDAO();
	if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("do"))
	{
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		String filter=" where type= '"+type+"' and userID='"+firmID+"'";
		DAO.delFeeInfo(filter);
		String[] downLimit = request.getParameterValues("downLimit");
		String[] upLimit = request.getParameterValues("upLimit");
		String[] tMode = request.getParameterValues("tMode");
		String[] rate = request.getParameterValues("rate");
		String[] minRateValue = request.getParameterValues("minRateValue");
		String[] maxRateValue = request.getParameterValues("maxRateValue");
		
		if(downLimit!=null)
		{
			for(int i = 0; i < downLimit.length; i++)
			{
				FeeInfoVO feeInfoVO=new FeeInfoVO();
				feeInfoVO.downLimit=Tool.strToInt(downLimit[i]);
				feeInfoVO.upLimit=Tool.strToInt(upLimit[i]);
				feeInfoVO.tMode=Tool.strToInt(tMode[i]);
				feeInfoVO.rate=Tool.strToDouble(rate[i]);
				feeInfoVO.minRateValue=Tool.strToDouble(minRateValue[i]);
				feeInfoVO.maxRateValue=Tool.strToDouble(maxRateValue[i]);
				feeInfoVO.type=type;
				feeInfoVO.userID=firmID;
				DAO.addFeeInfo(feeInfoVO);
				lv.setLogcontent("�޸Ľ����������ѣ������̴��룺"+firmID+"��ͽ�"+Tool.strToInt(downLimit[i])+"ʱ�䣺"+Tool.fmtTime(new java.util.Date()));
				DAO.log(lv);
			}
		}
	}
	
	Vector dicList=new Vector();
	Vector<FeeInfoVO> feeList=new Vector<FeeInfoVO>();
	try 
	{
		if(!firmID.trim().equals(""))
		{
			dicList = DAO.getDicList("where type=3 and  ( bankid IS  NULL or length(bankid)=0 ) ");	
			if(!type.trim().equals(""))
			{
				feeList=DAO.getFeeInfoList(" where type='"+type+"' and userID='"+firmID+"' order by id ");
			}
			else
			{
				if(dicList.size()>0)
				{
					DicValue dVal = (DicValue)dicList.get(0);
					feeList=DAO.getFeeInfoList(" where type='"+dVal.value+"' and userID='"+firmID+"' order by id ");
				}
			}
		}
	}
	catch(Exception e) 
	{
		e.printStackTrace();
	}
	finally
	{

	}
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>����������</title>
  </head>
  
  <body <%if(dicList.size()>0)%>onload="initData();">
  	<form id="frm" action="" method="post">
		<fieldset>
			<legend>����������</legend>
			<%if(dicList.size()>0){%>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right" width="100">
						���������ͣ�&nbsp;
					</td>
					<td align="left">
						<select name="type" class="normal" style="width: 200px" OnChange="SelChange(this)">
							<%
							for(int i=0;i<dicList.size();i++)
							{
								DicValue dVal = (DicValue)dicList.get(i);
								%>
								<OPTION value="<%=dVal.value%>" <%=type.equals(dVal.value)?"selected":""%>><%=dVal.note%></OPTION>
							<%}%>
						</select>
					</td>
				</tr>
			</table>
							  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="200" align="center">
								<tHead>
									<tr height="25">
										<td class="panel_tHead_LB">&nbsp;</td>
										<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'entryId_check')"></td>
										<td class="panel_tHead_MB">��ʼ���</td>
										<td class="panel_tHead_MB">�������</td>
										<td class="panel_tHead_MB">����������</td>
										<td class="panel_tHead_MB">������</td>
										<td class="panel_tHead_MB">���������</td>
										<td class="panel_tHead_MB">���������</td>
										<td class="panel_tHead_RB">&nbsp;</td>
									</tr>
								</tHead>
								<tBody>
									<tr onclick="selectTr();">
										<td class="panel_tBody_LB">&nbsp;</td>
										<td class="underLine"><input name="entryId_check" type="checkbox" onkeydown="if(event.keyCode==13)event.keyCode=9"><input name="entryId" type="hidden"></td>
										<td class="underLine"><input name="downLimit" type="text" class="text" style="width: 100px;" reqfv = "req_num;1;0;��ʼ���" value="0"  onkeydown="if(event.keyCode==13)event.keyCode=9"></td>
										<td class="underLine"><input name="upLimit" type="text" class="text" style="width: 100px;" reqfv = "req_num;1;0;�������" value="0"  onkeydown="if(event.keyCode==13)event.keyCode=9"></td>
										<td align="left">
											<select name="tMode" class="normal" style="width: 80px" onkeydown="if(event.keyCode==13)event.keyCode=9">
												<option value="0">�ٷֱ�</option>
												<option value="1">����ֵ</option>
											</select>
										</td>
										<td class="underLine"><input name="rate" type="text" class="text" style="width: 100px;" reqfv = "req_num;1;0;������" value="0"  onkeydown="if(event.keyCode==13)event.keyCode=9"></td>
										<td class="underLine"><input name="minRateValue" type="text" class="text" style="width: 100px;" reqfv = "req_num;1;0;���������" value="0"  onkeydown="if(event.keyCode==13)event.keyCode=9"></td>
										<td class="underLine"><input name="maxRateValue" type="text" class="text" style="width: 100px;" reqfv = "req_num;1;0;���������" value="0"  onkeydown="if(event.keyCode==13)event.keyCode=9"></td>
										<td class="panel_tBody_RB">&nbsp;</td>
									</tr>
							
								<%
								    for(int i=0;i<feeList.size();i++)
									{
										FeeInfoVO feeInfoVO=feeList.get(i);
									%>
									<tr onclick="selectTr();">
										<td class="panel_tBody_LB">&nbsp;</td>
										<td class="underLine"><input name="entryId_check" type="checkbox" onkeydown="if(event.keyCode==13)event.keyCode=9"><input name="entryId" type="hidden"></td>
										<td class="underLine"><input name="downLimit" type="text" class="text" style="width: 100px;" reqfv = "req_num;1;0;��ʼ���" value="<%=feeInfoVO.downLimit%>"  onkeydown="if(event.keyCode==13)event.keyCode=9"></td>
										<td class="underLine"><input name="upLimit" type="text" class="text" style="width: 100px;" reqfv = "req_num;1;0;�������" value="<%=feeInfoVO.upLimit%>"  onkeydown="if(event.keyCode==13)event.keyCode=9"></td>
										<td align="left">
											<select name="tMode" class="normal" style="width: 80px" onkeydown="if(event.keyCode==13)event.keyCode=9">
												<option value="0" <%=feeInfoVO.tMode==0?"selected":""%>>�ٷֱ�</option>
												<option value="1" <%=feeInfoVO.tMode==1?"selected":""%>>����ֵ</option>
											</select>
										</td>
										<td class="underLine"><input name="rate" type="text" class="text" style="width: 100px;" reqfv = "req_num;1;0;������" value="<%=feeInfoVO.rate%>"  onkeydown="if(event.keyCode==13)event.keyCode=9"></td>
										<td class="underLine"><input name="minRateValue" type="text" class="text" style="width: 100px;" reqfv = "req_num;1;0;���������" value="<%=feeInfoVO.minRateValue%>"  onkeydown="if(event.keyCode==13)event.keyCode=9"></td>
										<td class="underLine"><input name="maxRateValue" type="text" class="text" style="width: 100px;" reqfv = "req_num;1;0;���������" value="<%=feeInfoVO.maxRateValue%>"  onkeydown="if(event.keyCode==13)event.keyCode=9"></td>
										<td class="panel_tBody_RB">&nbsp;</td>
									</tr>
									<%}%>
								</tBody>
								<tFoot>
									<tr height="100%">
										<td class="panel_tBody_LB">&nbsp;</td>
										<td colspan="7">&nbsp;</td>
										<td class="panel_tBody_RB">&nbsp;</td>
									</tr>
									<tr height="22">
										<td class="panel_tBody_LB">&nbsp;</td>
										<td colspan="7" align="right" class="pager"><button class="smlbtn" type="button" onclick="addEntry();" onkeydown="if(event.keyCode==13)event.keyCode=9">���ӷֶ�</button>&nbsp;&nbsp;
								  <button class="smlbtn" type="button" onclick="delEntry();" onkeydown="if(event.keyCode==13)event.keyCode=9">ɾ���ֶ�</button>&nbsp;&nbsp;
								  <button id="submitBtn" class="smlbtn" type="button" onclick="this.disabled=true;if(!voucherSubmit()) this.disabled=false;" onkeydown="kd('downLimit')">�ύ</button><input type=hidden name=submitFlag value="">&nbsp;&nbsp;
								  <button class="smlbtn" type="button" onclick="window.location.href='list.jsp';">����</button>
								  </td>
										<td class="panel_tBody_RB">&nbsp;</td>
									</tr>
									<tr height="22">
										<td class="panel_tFoot_LB">&nbsp;</td>
										<td class="panel_tFoot_MB" colspan="7"></td>
										<td class="panel_tFoot_RB">&nbsp;</td>
									</tr>
								</tFoot>
						</table>
						<%}%>
		</fieldset>	  
	</from>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--

		function initData(){
			//��ʼ���еĿ�¡��������һ�У�Ĭ��һ��һ����
			trClone = document.all.tableList.children[1].children[0].cloneNode(true);
			document.all.tableList.children[1].children[0].removeNode(true);//ɾ����һ��
			//addEntry();
		}
	
		function kd(nxtobjid)
		{
			var nxtobjid=nxtobjid;
			var nxtobj=document.getElementById(nxtobjid);
			var keycode=event.keyCode;
			//alert(event.keyCode);
			if (keycode==13)
			{nxtobj.focus();
			}
		}
		//�������ӷ�¼��tr������ʱ��¡һ����
	    var trClone = null;
		
		function voucherSubmit()
		{
			if(!checkValue("frm"))
				return false;
			else
			{
				frm.submitFlag.value = "do";
				frm.submit();
			}
		}
		
		function addEntry()
		{
			var tabBody = document.all.tableList.children[1];
			var tr = trClone.cloneNode(true);
			tabBody.appendChild(tr);
		}
		
		function delEntry()
		{
		  var tabBody = document.all.tableList.children[1];
		  for(i=tabBody.children.length-1; i>=0; i--)
		  {
			if(tabBody.children[i].children[1].children[0].checked)
			{
				tabBody.children[i].removeNode(true);
			}			
		  }
	    }

		function SelChange(type)
		{
			var firmID='<%=firmID%>';
			window.location.href="feeSet.jsp?firmID="+firmID+"&type="+type.options[type.selectedIndex].value;
		}
//-->
</SCRIPT>