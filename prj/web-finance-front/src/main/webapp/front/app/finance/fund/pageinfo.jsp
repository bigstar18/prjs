<%@ page trimDirectiveWhitespaces="true" contentType="text/html;charset=GBK"%>
<%@ include file="/front/public/includefiles/taglib.jsp" %>
<%@ include file="/front/public/includefiles/path.jsp" %>
<script type="text/javascript" src="${publicPath}/js/tool.js"></script>
<input type="hidden" id="orderby" name="sortColumns" value="" />
<input type="hidden" id="pageNo" name="pageNo" value="1"/>
<input type="hidden" id="pageSize" name="pageSize" value="${pageInfo.pageSize }"/>
<div id="pageinfoDIV" class="page"></div>
<script>
/**
 * 初始时执行方法
 * (为列表增添分页按钮)
 */
function start(){
	//if(parseInt(${pageInfo.totalPage})<=1){
	//	document.getElementById("pageinfoDIV").style.display="none";
	//}else{
		if(${pageInfo.pageNumber}==0){
			this.addDiv(0,0);
		}else{
			var pagelength = ${fn:length(pageInfo.result)};
			var from = ${pageInfo.pageNumber-1}*${pageInfo.pageSize};
			this.addDiv(from+1,from+pagelength);
		}
	//}
}
start();
/**
 * 通过初始方法回掉函数，生成分页按钮填充到分页按钮栏中
 */
function addDiv(from,to){
	var pageNo = [];
	pageNo.push("共<span class='font_red_12b'>${pageInfo.totalPage}</span>页");
	pageNo.push("<span class='font_red_12b'>${pageInfo.totalCount}</span>条&nbsp;");
	pageNo.push("本页显示<span class='font_red_12b'>"+from+"-"+to+"</span>条&nbsp;");
	
	if(parseInt(${pageInfo.pageNumber})>1){
		pageNo.push("<a href='javascript:gotoPage(1);' title='首页'>首页</a>&nbsp;");
		pageNo.push("<a href='javascript:gotoPage(${pageInfo.pageNumber-1});' title='第 ${pageInfo.pageNumber-1} 页'>上一页</a>&nbsp;");
	}else{
		pageNo.push("首页&nbsp;上一页&nbsp;");
	}
	if(parseInt(${pageInfo.pageNumber})<parseInt(${pageInfo.totalPage})){
		pageNo.push("<a href='javascript:gotoPage(${pageInfo.pageNumber+1});' title='第 ${pageInfo.pageNumber+1} 页'>下一页</a>&nbsp;");
		pageNo.push("<a href='javascript:gotoPage(${pageInfo.totalPage});' title='第 ${pageInfo.totalPage} 页'>尾页</a>&nbsp;");
	}else{
		pageNo.push("下一页&nbsp;尾页&nbsp;");
	}
	pageNo.push("到第 <input name='inputpagenum' value='${pageInfo.pageNumber}' type='text' size='4' onkeydown='keydown(this.value)'/> 页");
	pageNo.push("&nbsp;<img style='cursor: pointer;' src='${skinPath}/image/memberadmin/turned.gif' width='39' height='22'align='absmiddle' onclick='gotoPage(inputpagenum.value)'/>");
	document.getElementById("pageinfoDIV").innerHTML=pageNo.join("")+document.getElementById("pageinfoDIV").innerHTML;//回填按钮栏
}
/**
 * 跳转到相应页
 */
function gotoPage(n){
	var num = 1;
	if(Util.ISEmpty(n)){
		alert("请输入页码！");
		return;
	}
	if(Util.ISInteger(n)){
		num=n;
	}else{
		alert("请输入正确的页码");
		return;
	}
	var totalpage = parseInt(${pageInfo.totalPage});
	if(num>totalpage){
		num = totalpage;
	}
	if(num<=0){
		num=1;
	}
	frm.method="post";
	frm.pageNo.value = num;
	frm.submit();
}
/**
 * 鼠标按下事件
 */
function keydown(pagenum){
	if(event.keyCode == 13){
		gotoPage(pagenum);
	}
}
/**
 * 设置排序条件，其中的各个功能的顺序不能改变，否则会使得排序功能失效
 */
	var i=1;
$(document).ready(function() {
	var orderby =  '${sortColumns}';
	$("div#.ordercol").click(function() {
		i++;
		if(i==2){
			var name = $(this).attr("id");
			var orderType = "asc";
			if (orderby.length>0 &&　orderby.indexOf(name)!=-1 && orderby.indexOf(orderType)!=-1) {
				orderType = "desc";
			}
			//$("#orderby").attr("value", "order by "+name + " " + orderType);
			$("#frm").attr("action",$("#frm").attr("action") + "?sortColumns=order by " +name + " " + orderType)
			//$("#view").click();
			gotoPage(1);//如果需要记录当前页码
			}
		
		
	});
	if(orderby.length>0){
		var array = orderby.split(" ");
		document.getElementById(array[2]).className="ordercol_on";
		if(array[3]=="asc"){
			//$("#"+array[2]).append("↑");/RealMardridFront/WebRoot/front/image/flag.png
			document.getElementById(array[2]).innerHTML = document.getElementById(array[2]).innerHTML+"<img src='${skinPath }/image/display/flag_up.png' width='10' height='15'>";
		}else{
			//$("#"+array[2]).append("↓");
			document.getElementById(array[2]).innerHTML = document.getElementById(array[2]).innerHTML+"<img src='${skinPath }/image/display/flag_down.png' width='10' height='15'>";
		}
		$("#orderby").attr("value", orderby);
	}
	
	//设置样式为 maxsize 的 td中内容的长度，指定保留长度为 lang 属性
	$("td.maxsize").each(function(){
		var length=$(this).attr("lang");
		if(!length || !parseInt(length) || length<3){
			length=30;
		}
		if($(this).children().size() > 0){
			var a = $(this).children().first();
			if(a.text().length>length){
				$(this).attr("title",$(this).text());
				a.text(a.text().substring(0,length-2)+"...");
				
			}
		}else{
			if($(this).text().length>length){
				var strTmp = $(this).text();
				var str = strTmp;
				if(strTmp.length > 30){
					str = "";
					for(var i=30;i<strTmp.length;i+=30){
						str += strTmp.substring(i-30,i) + "\n";
					}
				}
				
			 	$(this).attr("title",str);
			//	$(this).attr("title",$(this).text() +'&#10;' +$(this).text());
				$(this).text($(this).text().substring(0,length-2)+"...");
			}
		}
		
	});
});
</script>