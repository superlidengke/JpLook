<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="resources/jquery-1.8.2.min.js"></script>
<style type="text/css">
body{
background-color:#F5F5F5;
}
#news{

font-size: 20px;

word-spacing:2px;
line-height:35px;
margin:80px;
}
#meaning{
position:absolute;
background-color:#F8F8F8;
 border: 1px solid #cedd97;
    font-family: verdana, arial;
    font-size: 14px;
    line-height:20px;
    color: #494949;
    z-index: 9000005;
    background: #fff;
    width: 258px;
    padding: 10px;
    text-align: left;
}
</style>
<script type="text/javascript">
var wordMap={
		MAX_NUM:50,
		_keys:new Array(),
		_values:new Array()
   }

	var is_exist=function(key){
		for(var i=0;i<wordMap._keys.length;i++){
			if(wordMap._keys[i]==key){
				return true;//包含此元素
			}
		}
		return false;//不包含此元素
	};
	var add= function(key, value) {
		//若已存在，则不进行任何操作
		if(is_exist(key)){
			return;
		}
		//若不存在,并且已保存的元素达到上限，则删除第一个元素
		if(wordMap._keys.length>=wordMap.MAX_NUM){
			var firstKeys=wordMap._keys.shift();
			delete wordMap._values[firstKeys];
		}
		//保存元素
		wordMap._keys.push(key);
		wordMap._values[key]=value;
	};
	var getValue=function(key){
		return wordMap._values[key];
	}




var funGetSelectTxt = function() {
	var txt = "";
	if(document.selection) {
		txt = document.selection.createRange().text;	// IE
	} else {
		txt = document.getSelection();
	}
	return txt.toString();
};
var boxleft;
var boxtop;
var leftPt;
var topPt;
	
	function showMeaning(data){
		 $("#meaning").html(data);
	     var meaningBox=document.getElementById("meaning");
	     meaningBox.style.display = "inline";
			meaningBox.style.left =leftPt;
			meaningBox.style.top = topPt;
			$("#meaning").show();
	}
	
	function lookup(param){
		var nameVal=getValue(param);
		if(nameVal){
			showMeaning(nameVal);
		}else{
			$.post("http://jp.pickshell.com:8080/jplook/ajaxwordlookup", { wordName: param },
					   function(data){
						 showMeaning(data);
						 add(param,data);
					   });
		}
		
	}
	var selectedTranslate = function(eleContainer) {
		eleContainer = eleContainer || document;
		var funGetSelectTxt = function() {
			var txt = "";
			if(document.selection) {
				txt = document.selection.createRange().text;	// IE
			} else {
				txt = document.getSelection();
			}
			return txt.toString();
		};
		eleContainer.onmouseup = function(e) {
			e = e || window.event;
			var txt = funGetSelectTxt(), sh = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
			var left = (e.clientX - 40 < 0) ? e.clientX + 20 : e.clientX - 40, top = (e.clientY - 40 < 0) ? e.clientY + sh + 20 : e.clientY + sh - 40;
			if (txt) {
			//有文字选中时
				boxleft=left;
				boxtop=top;
				leftPt= left + "px";
				topPt=top + "px";
				lookup(txt);//ajax是异步的，发须要在数据返回后，才能执行的要放在回调函数里	
				
			} else {
			
			}
		};
	}
	$(function(){
		$("body").append('<div id="meaning">单词解释</div>');
		$("#meaning").hide();
		selectedTranslate();
		
		//注册单击事件
		$("body").mousedown(function(e){
			e = e || window.event;
			//如果点击单词解释以外的页面，则隐藏解释框
			var mx=e.clientX;
			var my=e.clientY;
			if(mx>=boxleft&&mx<=boxleft+280&&my>=boxtop){
				
			}else{
				$("#meaning").hide();
			}
			console.debug("mx:"+mx+",my:"+my+",leftPt:"+leftPt+",topPt:"+topPt);
			
		/*	用到插件中这种方法，不起作用
		if(e.srcElement.id != "meaning"){
			$("#meaning").hide();
			}
			*/
			});
	});
	
	
</script>
</head>
<body id="body">
	
	
	<div id="news">
	千葉市「突風で３人けが ６５棟被害」
9月7日 9時55分

６日夜、千葉市中央区のＪＲ蘇我駅周辺で激しい突風が吹き、女性３人が軽いけがをしたほか、これまでに６５棟の建物で被害が確認されました。ＪＲの電車の運転士が「竜巻のようなものを見た」と話したということで、気象台は、現地に職員を派遣して調査を行うことにしています。
６日午後９時半ごろ、千葉市中央区のＪＲ蘇我駅周辺で激しい突風が吹き、ＮＨＫが取材したところ、中央区の今井地区で数百メートルにわたって建物などに多数の被害が確認されました。
屋根の一部や瓦が飛ばされたり窓ガラスが割れたりした住宅が数多く見られたほか、ブロック塀が長さ５メートル以上にわたって倒れる被害も出ていました。また、風で飛ばされたものが当たったとみられる車の被害も相次いだほか、自動販売機が横倒しになったり、電柱が根元から倒れるといった被害も見られました。
消防によりますと、中央区宮崎で２人、中央区今井で１人の合わせて女性３人が、突風で転倒するなどして軽いけがをし、このうち２人が病院で手当てを受けたということです。
千葉市によりますと、午前８時半の時点で、被害が出た住宅が、中央区今井を中心に６５棟確認されたということで、千葉市などは被害の状況をさらに詳しく調べています。
また、６日午後９時半すぎ、ＪＲ蘇我駅に到着する直前の上りの快速電車に、風に飛ばされたとみられるものが当たり、車両の窓ガラスが割れました。ＪＲ東日本によりますと、運転士から「竜巻のようなものを見たため、緊急停車した」と連絡があったということです。電車内でけがをした人はいないということです。
銚子地方気象台は、竜巻が発生した可能性があるとして、職員を現地に派遣して調査を行うことにしています。
	</div>
</body>
</html>