var strTopFrame = "topFrame";       //top�������
var strNavFrame = "leftFrame";       //�����������
var rightFrame = "workspace";       //�Ҳ������ƣ�������ǰλ�á�main��ܣ�
var positionFrame = "topFrame1";    //��ǰλ�ÿ������
var strMainFrame = "mainFrame";     //main������ƣ����ݿ�ܣ�

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
    var frameElt = window.top.frames(rightFrame).frames( strMainFrame );
    return frameElt;
}

//ˢ�����ݿ��ҳ��
function gotoUrl( sTargetUrl )
{
	var vFrame = oMainFrame();
	vFrame.location = sTargetUrl;
	
	//oNavFrame().collaspe( "hide" );
}