var HIDDEN_FRAME_INDEX = 0;
function initForm(oldObjectGetKey)
{
    var collFrm = document.all.tags("FORM");
    if ( collFrm )
    {
        for( var i = 0 ; i < collFrm.length ; i++ )
        {
            setTarget( collFrm[i],oldObjectGetKey );
            if(oldObjectGetKey!='')
            {
            var e= document.createElement("input");
	        e.setAttribute("type","hidden");
	        e.setAttribute("id",'oldObjectGetKey');
	        e.setAttribute("name",'oldObjectGetKey');
	        e.setAttribute("value",oldObjectGetKey);
	        collFrm[i].appendChild(e);
            }
        }
        
    }
}

var loadedIframes = new Array();
function proxyIframeLoad()
{
    var obj = event.srcElement;
    var callback = obj.callback;
    
    var containsFrame = false;
    for (var i = 0; i < loadedIframes.length; i++)
    {
        if (loadedIframes[i] == obj.id)
        {
            containsFrame = true;
            break;
        }
    }
    
    if ( !containsFrame ){
        loadedIframes[loadedIframes.length] = obj.id;
    }
    else
    {
    	if(callback){
    		alert(callback);
        	callback = new Function("iframe", callback + "(iframe)");
        	callback(obj);
        }
    }
}

function setTarget( objForm ,oldObjectGetKey)
{
    if( objForm.targetType == "hidden" )
    {
        var iframeName = objForm.name + "_" + HIDDEN_FRAME_INDEX++;
        var iframeStr;
        if ( objForm.callback )
        {
            iframeStr = "<iframe id='" + iframeName + "' name='" + iframeName + "' width=0 height=0 style='display:none' src='' application='yes' onload='javascript:proxyIframeLoad();' callback='" + objForm.callback + "'></iframe>";
        } else {
        	iframeStr = "<iframe id='" + iframeName + "' name='" + iframeName + "' width=0 height=0 style='display:none' src='' application='yes' onload='javascript:proxyIframeLoad();'></iframe>";
        }
       	document.body.insertAdjacentHTML ( "beforeEnd", iframeStr );
        objForm.target = iframeName;
       
        //objForm.parentNode.insertBefore(e,objForm);
    }
}



