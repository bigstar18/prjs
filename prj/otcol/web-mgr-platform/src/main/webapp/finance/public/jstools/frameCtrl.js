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
    var frameElt = window.top.document.frames( strMainFrame );
    return frameElt;
}

//ˢ�����ݿ��ҳ��
function gotoUrl( sTargetUrl )
{
	var vFrame = oMainFrame();
	vFrame.location = sTargetUrl;
	
	//oNavFrame().collaspe( "hide" );
}