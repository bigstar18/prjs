/**
 *
 *  ���´��ڣ�������ʾ
 *  location: �´����ļ���URL
 *  width   : ���ڿ��
 *  hight   : ���ڸ߶�
 */
function p(location, width, height)
{
	//open(location,"cx","resizable=no,scrollbars=0,status=no,toolbar=no,directories=no,location=no,menu=no,width="+width+",height="+height+",left="+(screen.width-width)/2+",top="+(screen.height-height)/2,"scrollbars");
	p(location, width, height,"no");
}

/**
 *
 *  ���´��ڣ�������ʾ
 *  location: �´����ļ���URL
 *  width   : ���ڿ��
 *  hight   : ���ڸ߶�
 *  scroll  : �Ƿ������yes��Ҫ���� no��������
 */
function p(location, width, height,scroll)
{
	open(location,"cx","resizable=no,scrollbars=" + scroll + ",status=no,toolbar=no,directories=no,location=no,menu=no,width="+width+",height="+height+",left="+(screen.width-width)/2+",top="+(screen.height-height)/2,"scrollbars");
}

function pFull(location)
{
	pFull(location,"no");
}

function pFull(location,scroll)
{
	var width  = screen.width;
	var height = screen.height;
	p(location, width, height,scroll);
}

function pTop(location, width, height)
{
	return showModalDialog(location,window,'dialogWidth:'+width+'px;dialogHeight:'+height+'px;dialogLeft:'+(screen.width-width)/2+'px;dialogTop:'+(screen.height-height)/2+'px;center:yes;help:no;resizable:no;status:no;scrollbars:no');
}

function pTopFull(location)
{
	var width  = screen.width;
	var height = screen.height;
	return pTop(location, width, height);
}

function encodeParameter(para)
{
	var tmp = para;
	var rgExp = "+";
	return tmp.replace(rgExp, "|"); 
}
function decodeParameter(para)
{
	var tmp = para;
	var rgExp = "|";
	return tmp.replace(rgExp, "+"); 
}

function openDialogLess(url, args, width, height) {
	if (!width) width = 600;
	if (!height) height = 400;
    window.showModelessDialog(url, args, "dialogWidth=" + width + "px; dialogHeight=" + height + "px; dialogTop:150px;center:yes;help:yes;resizable:yes;status:yes");
}