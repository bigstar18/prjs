var strTopFrame = "topFrame";       //top�������
var strMainFrame = "mainFrame";     //main������ƣ��������������ݿ�ܣ�
var strNavFrame = "leftFrame";       //�����������

//������ܣ����ص���window����
function oNavFrame()
{
    var frameElt = window.top.document.frames( strNavFrame );
    return frameElt;
}

//������ܣ����ص���window����
function oTopFrame()
{
    var frameElt = window.top.document.frames( strTopFrame );
    return frameElt;
}

//main��ܣ����ص���window����
function oMainFrame()
{
	alert("oMainFrame");
	alert(window.top.document);
    var frameElt = window.top.document.frames( strMainFrame );
    alert(window.top.document);
    return frameElt;
}

//ˢ�����ݿ��ҳ��
function gotoUrl( sTargetUrl )
{
	var vFrame = oMainFrame();
	vFrame.location = sTargetUrl;
	
	//oNavFrame().collaspe( "hide" );
}