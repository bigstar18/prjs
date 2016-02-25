var strTopFrame = "topFrame";       //top框架名称
var strMainFrame = "mainFrame";     //main框架名称（包含导航和数据框架）
var strNavFrame = "leftFrame";       //导航框架名称

//导航框架，返回的是window对象
function oNavFrame()
{
    var frameElt = window.top.document.frames( strNavFrame );
    return frameElt;
}

//顶部框架，返回的是window对象
function oTopFrame()
{
    var frameElt = window.top.document.frames( strTopFrame );
    return frameElt;
}

//main框架，返回的是window对象
function oMainFrame()
{
    var frameElt = window.top.document.frames( strMainFrame );
    return frameElt;
}

//刷新数据框架页面
function gotoUrl( sTargetUrl )
{
	var vFrame = oMainFrame();
	vFrame.location = sTargetUrl;
	
	//oNavFrame().collaspe( "hide" );
}