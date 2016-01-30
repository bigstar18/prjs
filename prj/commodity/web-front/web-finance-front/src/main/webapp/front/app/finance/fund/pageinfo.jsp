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
 * ��ʼʱִ�з���
 * (Ϊ�б������ҳ��ť)
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
 * ͨ����ʼ�����ص����������ɷ�ҳ��ť��䵽��ҳ��ť����
 */
function addDiv(from,to){
	var pageNo = [];
	pageNo.push("��<span class='font_red_12b'>${pageInfo.totalPage}</span>ҳ");
	pageNo.push("<span class='font_red_12b'>${pageInfo.totalCount}</span>��&nbsp;");
	pageNo.push("��ҳ��ʾ<span class='font_red_12b'>"+from+"-"+to+"</span>��&nbsp;");
	
	if(parseInt(${pageInfo.pageNumber})>1){
		pageNo.push("<a href='javascript:gotoPage(1);' title='��ҳ'>��ҳ</a>&nbsp;");
		pageNo.push("<a href='javascript:gotoPage(${pageInfo.pageNumber-1});' title='�� ${pageInfo.pageNumber-1} ҳ'>��һҳ</a>&nbsp;");
	}else{
		pageNo.push("��ҳ&nbsp;��һҳ&nbsp;");
	}
	if(parseInt(${pageInfo.pageNumber})<parseInt(${pageInfo.totalPage})){
		pageNo.push("<a href='javascript:gotoPage(${pageInfo.pageNumber+1});' title='�� ${pageInfo.pageNumber+1} ҳ'>��һҳ</a>&nbsp;");
		pageNo.push("<a href='javascript:gotoPage(${pageInfo.totalPage});' title='�� ${pageInfo.totalPage} ҳ'>βҳ</a>&nbsp;");
	}else{
		pageNo.push("��һҳ&nbsp;βҳ&nbsp;");
	}
	pageNo.push("���� <input name='inputpagenum' value='${pageInfo.pageNumber}' type='text' size='4' onkeydown='keydown(this.value)'/> ҳ");
	pageNo.push("&nbsp;<img style='cursor: pointer;' src='${skinPath}/image/memberadmin/turned.gif' width='39' height='22'align='absmiddle' onclick='gotoPage(inputpagenum.value)'/>");
	document.getElementById("pageinfoDIV").innerHTML=pageNo.join("")+document.getElementById("pageinfoDIV").innerHTML;//���ť��
}
/**
 * ��ת����Ӧҳ
 */
function gotoPage(n){
	var num = 1;
	if(Util.ISEmpty(n)){
		alert("������ҳ�룡");
		return;
	}
	if(Util.ISInteger(n)){
		num=n;
	}else{
		alert("��������ȷ��ҳ��");
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
 * ��갴���¼�
 */
function keydown(pagenum){
	if(event.keyCode == 13){
		gotoPage(pagenum);
	}
}
/**
 * �����������������еĸ������ܵ�˳���ܸı䣬�����ʹ��������ʧЧ
 */
	var i=1;
$(document).ready(function() {
	var orderby =  '${sortColumns}';
	$("div#.ordercol").click(function() {
		i++;
		if(i==2){
			var name = $(this).attr("id");
			var orderType = "asc";
			if (orderby.length>0 &&��orderby.indexOf(name)!=-1 && orderby.indexOf(orderType)!=-1) {
				orderType = "desc";
			}
			//$("#orderby").attr("value", "order by "+name + " " + orderType);
			$("#frm").attr("action",$("#frm").attr("action") + "?sortColumns=order by " +name + " " + orderType)
			//$("#view").click();
			gotoPage(1);//�����Ҫ��¼��ǰҳ��
			}
		
		
	});
	if(orderby.length>0){
		var array = orderby.split(" ");
		document.getElementById(array[2]).className="ordercol_on";
		if(array[3]=="asc"){
			//$("#"+array[2]).append("��");/RealMardridFront/WebRoot/front/image/flag.png
			document.getElementById(array[2]).innerHTML = document.getElementById(array[2]).innerHTML+"<img src='${skinPath }/image/display/flag_up.png' width='10' height='15'>";
		}else{
			//$("#"+array[2]).append("��");
			document.getElementById(array[2]).innerHTML = document.getElementById(array[2]).innerHTML+"<img src='${skinPath }/image/display/flag_down.png' width='10' height='15'>";
		}
		$("#orderby").attr("value", orderby);
	}
	
	//������ʽΪ maxsize �� td�����ݵĳ��ȣ�ָ����������Ϊ lang ����
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