<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>公告列表</title>
	</head>
	<body onload="change();getFocus('id');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/trade/notice/noticeList.action?sortColumns=order+by+createTime+desc"
								method="post" >
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style"  width="80%">
									<tr>
										<td  width="600" height="40">
											<div class="div2_top" >
												<table class="table3_style" border="0" cellspacing="0" 
													cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;公告标题:&nbsp;
															<label>
																<input type="text" class="input_text" id="id"
																	name="${GNNT_}primary.title[allLike][String]" 
																	value="${oldParams['primary.title[allLike][String]']}" maxLength="<fmt:message key='noticeTitle_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															开始日期:&nbsp;
															<label>
																<input type="text" class="input_text wdate" id="beginDate"
																	name="${GNNT_}createTime[>=][date]"
																	value="${oldParams['createTime[>=][date]']}" 
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;结束日期:&nbsp;
															<label>
																<input type="text" class="input_text wdate" id="endDate"
																	name="${GNNT_}createTime[<=][datetime]"
																	value="${oldParams['createTime[<=][datetime]'] }" 
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
															</label>
														</td>
														</tr>
														<tr>
															
														<td class="table3_td_1" align="left">
															管理员代码:&nbsp;
															<label>
																<input type="text" class="input_text" id="id"
																	name="${GNNT_}primary.userId[allLike][string]"
																	value="${oldParams['primary.userId[allLike][string]']}" maxLength="<fmt:message key='userID_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick=select1();>
																查询
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick=myReset();>
																重置
															</button>
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
						<rightButton:rightButton name="添加" onclick="toAdd()" id="noticeAdd"
								className="anniu_btn"    action="/trade/notice/addNoticeToPage.action"></rightButton:rightButton>
								&nbsp;&nbsp;
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="notice"
											action="${basePath}/trade/notice/noticeList.action?sortColumns=order+by+createTime+desc" 
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="arbitration.xls" csvFileName="arbitration.csv"
											showPrint="true"  listWidth="100%"
											minHeight="345">
											<ec:row>
											
												<ec:column width="1%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													style="text-align:center;"
													filterable="false" />
												<ec:column property="noticeId" title="公告序号" width="2%"
													style="text-align:left;">
												</ec:column>
												<ec:column property="title" title="公告标题" width="5%"
													style="text-align:left;">
												</ec:column>
												<ec:column property="createTime" title="创建时间" width="3%"
													style="text-align:center;">
													<fmt:formatDate value="${notice.createTime}"
														pattern="yyyy-MM-dd HH:mm:ss" ></fmt:formatDate>
												</ec:column>
												<ec:column property="userId" title="管理员代码" width="2%"
													style="text-align:center;"/>
												<ec:column property="0" title="查看信息" width="4%"
													style="text-align:center;" sortable="false"
													filterable="false">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/trade/notice/noticeDetail.action" id="detail" text="<font color='#880000'>详情</font>" onclick="detail(${notice.noticeId})"/>
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


			<!-- 编辑和过滤所使用的 通用的文本框模板 -->
			<textarea id="ecs_t_input" rows="" cols="" style="display: none">
		<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="" />
	</textarea>

<SCRIPT type="text/javascript">
	function detail(id){
		var a=document.getElementById("detail").action;
		var url="${basePath}"+a+"?entity.noticeId="+id;
		var result = showDialogRes(url, '', 600, 350);
		
	}

	function toAdd(){
		var url = "${basePath}" + document.getElementById('noticeAdd').action;
		var result  = showDialogRes(url, '', 600, 450);
		if(result == 1){
			frm.submit();
		}
	}
	function select1() {
		checkQueryDate(frm.beginDate.value,frm.endDate.value);
	}
	
	function change(){
		
		
	}
</SCRIPT>
	</body>
</html>