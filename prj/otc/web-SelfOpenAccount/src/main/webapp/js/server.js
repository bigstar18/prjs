var tips; var theTop = 50; var old = theTop;
function initFloatTips() {
tips = document.getElementById('QQFloat');
moveTips();
};
function moveTips() {
var tt=50;
if (window.innerHeight) {
    pos = window.pageYOffset + 200;
}
else if (document.documentElement && document.documentElement.scrollTop) {
    pos = document.documentElement.scrollTop + 200;
}
else if (document.body) {
    pos = document.body.scrollTop + 200;
}

pos=pos-tips.offsetTop+theTop;
pos=tips.offsetTop+pos/10;

if (pos < theTop) pos = theTop;
if (pos != old) {
    tips.style.top = pos+"px";
    tt=10;
}

old = pos;
setTimeout(moveTips,tt);
}
initFloatTips();