window.mymenudiv = function(){//�ҵĲ˵��޸�ʱ������
	var html = "<div class=\"mymenu-mymenuWrap2\" id=\"mymenuWrap2-<%=r%>\">\
					<div class=\"clear\"></div>\
					<div class=\"set_bg\"><div class=\"set_close\" id=\"ljClose<%=r%>\">&nbsp;</div><div></div></div>\
					<span class=\"mymenu-in mymenu-<%=p%>\"><span class=\"mymenu-in\"></span></span>\
				</div>";
	var dg = function(id){return document.getElementById(id);};//ͨ��ID��ȡ����
	var dt = function(parent, nodeName){return  parent.getElementsByTagName(nodeName);};
	var db = document.body;
	var dd = document.documentElement;
	var of = 15;// ƫ����
	var prefix = 'mymenu';// ǰ׺
	var isie = /msie\s([\d\.]+)/.test(navigator.userAgent.toLowerCase());
	var w = window;
	var lock = true;// ����ͬһ���� ��ε�����ʾ
	return function(id){
		var elem	= id ? typeof id == "string" ? dg(id) : id : this;
		var offset	= null;
		var	width	= elem.offsetWidth;
		var	height	= elem.offsetHeight;
		var rand	= 0;// �漴ֵ
		var func	= null;// ���ڱ仯�󶨵ĺ���
		var	_this	= {};
		var pos		= {
			left	: function(w, h){return {top:offset.top , left:offset.left - w - of}},
			top		: function(w, h){return {top:offset.top - h - of, left:offset.left}},
			right	: function(w, h){return {top:offset.top , left:offset.left + width + of}},
			bottom	: function(w, h){return {top:offset.top + height + of , left:offset.left}}
		};

		_this.show = function(obj){
			if(elem.lock){
				elem.lock=false;return;
			}else elem.lock=true;
			offset	= elem.getBoundingClientRect();
			db=document.body;
			var top	= db.scrollTop + dd.scrollTop;
			var left= db.scrollLeft + dd.scrollLeft;
			obj.p = obj.p || 'right';
			var wrap = _this.append(obj.p, obj.closeBtn || false);
			dt(wrap, "DIV")[3].innerHTML = obj.content;//����Ϣ���뵽����
			var p = pos[obj.p](wrap.offsetWidth,wrap.offsetHeight);
			wrap.style.top = p.top + top + "px";
			wrap.style.left = p.left + left + "px";
			obj.time && setTimeout(function(){_this.clear(dg(prefix+rand));}, obj.time);
			obj.fn && obj.fn.call(elem, dg(prefix+rand));
			//--��ⴰ�ڷ����仯
			func = function(a, b){
				return function(){
					var top	= db.scrollTop + dd.scrollTop;
					var left= db.scrollLeft + dd.scrollLeft;
					offset = elem.getBoundingClientRect();
					var c = pos[obj.p](wrap.offsetWidth, wrap.offsetHeight);
					b.style.top = c.top + top + 'px';
					b.style.left = c.left + left + 'px';
				}
			}(elem, wrap);
			isie ? w.attachEvent('onresize', func) : w.addEventListener('resize', func, false);
		}
		_this.append = function(p,closeBtn){
			var r = rand = Math.floor(Math.random() * 10000);
			var x = document.createElement("DIV");
			x.id = prefix + r;
			x.innerHTML = html.replace("<%=p%>",p).replace(/<%=r%>/g,r);
			document.body.appendChild(x);
			if(closeBtn){
				dg("ljClose"+r).onclick = _this.hide;
			}else{
				dg("ljClose"+r).style.display = "none";
			}
			return dg("mymenuWrap2-" + r);
		}
		
		_this.clear = function(a){
			a && a.parentNode && a.parentNode.removeChild(a);
			isie ? w.detachEvent('onresize',func) : w.removeEventListener('resize', func, false);
			elem.lock = false;// �������
		}
		_this.hide = function(){
			_this.clear(dg(prefix + rand));
			menudiv=false;
		}
		return _this;
	}
}();
var map=new Map();
var mymenu={
show:function(id,msg,where){
	var place = "right";
	if(where){place=where}
	var obj = {content:msg,p:place,closeBtn:true};
	if(map.get(id)){
		map.get(id)
	}
	var menudiv = mymenudiv(id);
	map.add(id,menudiv);
	menudiv.show(obj);
},
close:function(id){
	if(map.get(id)){
		map.get(id).hide();
	}
}
}



/**
 * Map������ݵ�ʵ����
 */
function struct(key, value) {
	this.key = key;//map ��
	this.value = value;//map ֵ
}
/**
 * map ��
 */
function Map(){
	//map ������ݵ�����
	this.map = new Array();
	/**
	 * ��map����������
	 * @param key ��
	 * @param value ֵ
	 */
	this.add = function(key,value){
		for(var i=0;i<this.map.length;i++){
			if(this.map[i].key==key){
				this.map[i].value = value;
				return;
			}
		}
		this.map[this.map.length] = new struct(key,value);
	},
	/**
	 * ͨ���������ȡ���ֵ
	 * @param key ��
	 * @return value ֵ
	 */
	this.get = function(key){
		for(var i=0;i<this.map.length;i++){
			if(this.map[i].key==key){
				return this.map[i].value;
			}
		}
		return null;
	},
	/**
	 * �жϵ�ǰmap�Ƿ�Ϊ��
	 * return boolean (true:��ǰ�ǿյ�;false:��ǰ���ǿյ�)
	 */
	this.isEmpty = function(){
		return this.map.length<=0;
	}
}
