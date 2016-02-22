<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>持仓监控</title>
<link href="../common/skinstyle/default/common/commoncss/mainstyle.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="../js/jquery-1.5.2.min.js"></script>
<script language="javascript" type="text/javascript" src="../js/jquery.tablesorter.min.js"></script>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">

								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout:fixed">
													<tr>
														<td width="225" align="left">
															选择会员:&nbsp;
															<label>
																<input type="text" id="memberNames"
																	name="${GNNT_}memberNames"
																	value="${oldParams['memberNames']}"
																	readonly=true size="8" class="input_text">
																	<a href="javascript:clickText();"><img align="absmiddle" src="<%=skinPath%>/cssimg/kh.gif"></a>
															</label>
														</td>
														<td class="table3_td_1" align="left">
	
														</td>
														<td class="table3_td_1" align="left">

														</td>
														<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onClick="">监控</button>&nbsp;&nbsp;
														<button class="btn_cz" onClick="">重置</button>
													</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>

						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
                                <tr><td><div class="div_cxtj"><img src="../common/skinstyle/default/common/commoncss/cssimg/13.gif"  />&nbsp;&nbsp;持仓汇总</div></td></tr>
									<td class="table_top_h2">
										<!-- 表格放到这里 -->
                                        	<div id="total" style="display:none">
                                            <table id="res_tab" width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <thead>
                                                <tr>
                                                  <th rowspan="2" width="32" height="46" class="right_05bj_td_h2">序号</th>
                                              <th rowspan="2" width="195" height="46" class="right_05bj_td_h2">商品名称</th>
                                                  <th  colspan="3"  width="101" height="23" class="right_05bj_td">买单</th>
                                                  <th   colspan="3" width="134" height="23" class="right_05bj_td">卖单</th>
                                                  <th rowspan="2" width="112" height="46" class="right_05bj_td_h2">买卖单差</th>
                                                  <th rowspan="2" width="112" height="46" class="right_05bj_td_h2">盈亏</th>
                                                  </tr>
                                                <tr>
                                          
                                                  <th width="101" height="23" class="right_05bj_td">持仓均价</th>
                                                  <th width="112" height="23" class="right_05bj_td">数量</th>
                                                  <th width="101" height="23" class="right_05bj_td">盈亏</th>
                                                  <th width="134" height="23" class="right_05bj_td">持仓均价</th>
                                                  <th width="112" height="23" class="right_05bj_td">数量</th>
                                                  <th width="112" height="23" class="right_05bj_td">盈亏</th>
                                          
                                                  </tr>        
                                                  </thead>
                                                <tr id="template">
                                                  <td height="23" id="num" class="monileft_05_tdbj">&nbsp;</td>
                                                  <td height="23" id="commodityname" class="monileft_05_tdbj">&nbsp;</td>
                                                  <td height="23" id="envenpricebuy" class="moniright_05_tdbj">&nbsp;</td>
                                                  <td height="23" id="buyqty" class="moniright_05_tdbj">&nbsp;</td>
                                                  <td height="23" id="buyloss" class="moniright_05_tdbj">&nbsp;</td>
                                                  <td height="23" id="envenpricesell" class="moniright_05_tdbj">&nbsp;</td>
                                                  <td height="23" id="sellqty" class="moniright_05_tdbj">&nbsp;</td>
                                                  <td height="23" id="sellloss" class="moniright_05_tdbj">&nbsp;</td>
                                                  <td height="23" id="netqty" class="moniright_05_tdbj">&nbsp;</td>
                                                  <td height="23" id="netloss" class="moniright_05_tdbj">&nbsp;</td>
                                                  </tr>
                                              </table>
                                              </div><div id="tc"></div>
                                            
                                              
									</td>
								</tr>
                                

                                <tr>
                                <tr><td><div class="div_cxtj"><img src="../common/skinstyle/default/common/commoncss/cssimg/13.gif"  />&nbsp;&nbsp;持仓明细</div></td></tr>
                                  
                                  
                                  <td class="table_top">
                                  
                                  <div id="memberlist" style="display:none">
                                  <table  width="100%"  class="tablesorter" border="0" cellpadding="0" cellspacing="0" id="res_tab2">
                                  <thead>
                                    <tr>
                                      <th height="23" nowrap="nowrap" class="right_05bj_td">单号</th>
                                  <th height="23" nowrap="nowrap" class="right_05bj_td">会员编号</th>
                                      <th height="23" nowrap="nowrap" class="right_05bj_td">商品</th>
                                      <th height="23" nowrap="nowrap" class="right_05bj_td">数量</th>
                                      <th height="23" nowrap="nowrap" class="right_05bj_td">买卖方向</th>
                                      <th height="23" nowrap="nowrap" class="right_05bj_td">下单时间</th>
                                      <th height="23" nowrap="nowrap" class="right_05bj_td">接单特别会员</th>
                                      <th height="23" nowrap="nowrap" class="right_05bj_td">建仓价</th>
                                      <th height="23" nowrap="nowrap" class="right_05bj_td">持仓价</th>
                                      <th height="23" nowrap="nowrap" class="right_05bj_td">中间价</th>
                                      <th nowrap="nowrap" class="right_05bj_td">盈亏</th>
                                      <th nowrap="nowrap" class="right_05bj_td">手续费</th>
                                      <th nowrap="nowrap" class="right_05bj_td">延期费</th>
                                      </tr>
                                       </thead>
                                    <tr id="template2">
                                      <td height="23" id="holdno" class="monileft_05_tdbj">&nbsp;</td>
                                      <td height="23" id="firmid" class="monileft_05_tdbj">&nbsp;</td>
                                      <td height="23" id="commodityname" class="monileft_05_tdbj">&nbsp;</td>
                                      <td height="23" id="holdqty" class="moniright_05_tdbj">&nbsp;</td>
                                      <td height="23" id="bs_flag" class="monileft_05_tdbj">&nbsp;</td>
                                      <td height="23" id="holdtime" class="moniright_05_tdbj">&nbsp;</td>
                                      <td height="23" id="o_firmname" class="monileft_05_tdbj">&nbsp;</td>
                                      <td height="23" id="openprice" class="moniright_05_tdbj">&nbsp;</td>
                                      <td height="23" id="holdprice" class="moniright_05_tdbj">&nbsp;</td>
                                      <td height="23" class="moniright_05_tdbj" id="price">&nbsp;</td>
                                      <td height="23" class="moniright_05_tdbj" id="floatingloss">&nbsp;</td>
                                      <td height="23" class="moniright_05_tdbj" id="tradefee">&nbsp;</td>
                                      <td height="23" class="moniright_05_tdbj" id="delayfee">&nbsp;</td>
                                      </tr>
                                  </table></div><div id="tcl"></div></tr> 
                                  
                                  <tr>
                                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table1">
                                          <tr>
                                            <td width="16%" height="25" bgcolor="#eeeeee" class="moniright_05_tdbj1"><table width="100%" border="0"
                                        
                                        cellspacing="0" cellpadding="0">
                                              <tr>
                                                <td height="25" align="center" valign="middle"><a href="javascript:firstPage();"><img src="../common/skinstyle/default/common/commoncss/cssimg/20.gif" width="10"
                                        
                                        height="12" border="0" /></a></td>
                                                <td height="25" align="center" valign="middle"><a href="javascript:prevPage();"><img src="../common/skinstyle/default/common/commoncss/cssimg/21.gif" width="8"
                                        
                                        height="12" border="0" /></a></td>
                                                <td height="25" align="center" valign="middle"><a href="javascript:nextPage();"><img src="../common/skinstyle/default/common/commoncss/cssimg/22.gif" width="8"
                                        
                                        height="12" border="0" /></a></td>
                                                <td height="25" align="center" valign="middle"><a href="javascript:lastPage();"><img src="../common/skinstyle/default/common/commoncss/cssimg/23.gif" width="10"
                                        
                                        height="12" border="0" /></a></td>
                                              </tr>
                                            </table></td>
                                            <td width="10%" height="25" align="center" valign="middle" bordercolor="#D4D0C8" bgcolor="#eeeeee"
                                        
                                        class="moniright_05_tdbj1">
                                              <input name="page" type="text" class="from1" id="page" size="4" />
                                              /<span id="totalpage"></span>页     <a href="#"> <img src="../common/skinstyle/default/common/commoncss/cssimg/24.gif" alt="" width="13" height="12" border="0" /></a></td>
                                            <td width="15%" height="25" align="center" valign="middle" bordercolor="#D4D0C8" bgcolor="#eeeeee"
                                        
                                        class="moniright_05_tdbj1">每页
                                              <label>
                                              <select name="pagesize" id="pagesize">
                                                <option value="20" selected="selected">20</option>
                                                <option value="30">30</option>
                                                <option value="40">40</option>
                                                <option value="50">50</option>
                                                <option value="100">100</option>
                                                <option value="200">200</option>
                                              </select>
                                              条
                                              </label></td>
                                            <td height="25" align="center" bordercolor="#D4D0C8" bgcolor="#eeeeee" class="moniright_05_tdbj1">&nbsp;</td>
                                            <td width="17%" height="25" align="right" bordercolor="#D4D0C8" bgcolor="#eeeeee" class="moniright_05_tdbj1">
                                              
                                              共 <span id="totalline"></span> 条记录</td>
                                          </tr>
                                        </table>                                  
                                  </tr>                               
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
<script language="javascript"  type="text/javascript">
<!--

function reqTotalData() {
	var memid = $.trim($('#memid').val());
	var pid =  $.trim($('#pmemid').val());

	$.ajax({
		type:'POST',
		url:"memberhold!sum.action",
		data:'pid='+pid+'&mid='+memid,
	    cache: false,
		dataType:"json",
		timeout: 3000,
		success: response
	});
}


function response(data) {
	
  
	// json数据处理    
	var tb = $("#res_tab").clone();
        var mtrow = tb.find("#template");
		
		$.each(data, function(i, n) {
		  var row = mtrow.clone();
		  row.find("#num").text(i+1);
		  row.find("#commodityname").text(n.commodityname);
		  row.find("#envenpricesell").text(n.envenpricesell);
		  row.find("#sellqty").text(n.sellqty);
		  row.find("#sellloss").text(n.sellloss);
		  row.find("#sellprice").text(n.sellprice);
		  
		  row.find("#envenpricebuy").text(n.envenpricebuy);
		  row.find("#buyqty").text(n.buyqty);
		  row.find("#buyloss").text(n.buyloss);
		  row.find("#buyprice").text(n.buyloss);
		  
		  row.find("#netqty").text(n.netqty);
		  row.find("#netloss").text(n.netloss);
		  row.attr("id","ready");//改变绑定好数据的行的
		  row.appendTo(tb);
		});
        mtrow.remove();
        tb.attr("id","tab_total");
	$('#tc').html(tb);
}
$(reqTotalData())
var sitotalid = setInterval ('reqTotalData()',3000);




function reqMemberData() {
	
	 var sortLists = getSortList2(); 
	
	var meta =["holdno", "firmid", "commodityname", "holdqty" , "bs_flag" , "holdtime" , "o_firmname" , "openprice" , "holdprice" , "price", "floatingloss", "tradefee", "delayfee"];

	var memid = $.trim($('#memid').val());
	var pid =  $.trim($('#pmemid').val());
	
	var pagesize = $("#pagesize").val();
	var curpage = $("#page").val();
	
	var desc = sortLists[0][1];
	var order = meta[sortLists[0][0]];
	
	var params = 'pid='+pid+'&mid='+memid +'&pagesize='+pagesize+'&page='+curpage+'&order='+order+'&desc='+desc;
	
	$.ajax({
		type:'POST',
		url:"./memberhold!hold.action",
		data: params,
	    cache: false,
		dataType:"json",
		timeout: 3000,
		success: response2
	});
}

function getSortList2() {
		    //排序默认值处理
		// see: http://tablesorter.com/docs/
        var sortLists = new Array();
        sortLists[0] = new Array();
        var sth = null;
        var sup =$("#tab_total2").find(".headerSortUp")[0];
        var sud = $("#tab_total2").find(".headerSortDown")[0];
        if(typeof(sud)!='undefined') {
            sth = sud;
            sortLists[0][1] = 0;
        } else if(typeof(sup)!='undefined') {
            sth = sup;
            sortLists[0][1] = 1;
        } else {
            sortLists[0][0] = 0;
            sortLists[0][1] = 0;
        }
        var tdSeq = $("#tab_total2").find("th").index(sth);
        if(tdSeq>-1) {
           sortLists[0][0] = tdSeq;
		}
		return sortLists;
}

function response2(data) {

	 var sortLists = getSortList2();
   
	// json数据处理    
	var tb = $("#res_tab2").clone();
        var mtrow = tb.find("#template2");
		if(data.data!=null && data.data!="") {
			$.each(data.data, function(i, n) {
			  var row = mtrow.clone();
			  row.find("#holdno").text(n.holdno);
			  row.find("#firmid").text(n.firmid);
			  row.find("#commodityname").text(n.commodityname);
			  row.find("#holdqty").text(n.holdqty);
			  row.find("#bs_flag").text(n.bs_flag);
			  row.find("#holdtime").text(n.holdtime);
			  row.find("#o_firmname").text(n.o_firmname);
			  row.find("#openprice").text(n.openprice);
			  row.find("#holdprice").text(n.holdprice);
			  row.find("#price").text(n.price);
			  
			  row.find("#floatingloss").text(n.floatingloss);
			  row.find("#tradefee").text(n.tradefee);
			  row.find("#delayfee").text(n.delayfee);
			  
			  row.attr("id","ready");//改变绑定好数据的行的
			  row.appendTo(tb);
			});
			mtrow.remove();

		}
        
        tb.attr("id","tab_total2");
	$('#tcl').html(tb);
	$("#tab_total2").tablesorter({sortList: sortLists });
	
	//分页处理
	$("#totalline").text(''+data.pageInfo.totalRecords);
	$("#totalpage").text(''+data.pageInfo.totalPages);
	$("#page").val(''+data.pageInfo.pageNo);
	
	maxpage = data.pageInfo.totalPages;

}



function nextPage() {
	clearInterval(simemberid);
	var pg = $("#page").val();
	if (pg>maxpage) {
		$("#page").val(pg+1);
	}
	reqMemberData();
	simemberid = setInterval ('reqMemberData()',3000);
}

function prevPage() {
	clearInterval(simemberid);
	var pg = $("#page").val();
	if (pg>1) {
		$("#page").val(pg-1);
	}
	reqMemberData();
	simemberid = setInterval ('reqMemberData()',3000);
}

function firstPage() {
	clearInterval(simemberid);
	$("#page").val(1);
	reqMemberData();
	simemberid = setInterval ('reqMemberData()',3000);
}
function lastPage() {
	clearInterval(simemberid);
	$("#page").val(maxpage);
	reqMemberData();
	simemberid = setInterval ('reqMemberData()',3000);
}


var simemberid = setInterval ('reqMemberData()',3000);

$(reqMemberData())

var maxpage = 0;

//-->
</script>
	</body>
</html>