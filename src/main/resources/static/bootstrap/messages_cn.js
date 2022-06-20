jQuery.extend(jQuery.validator.messages, {
   required: "必选字段",
   remote: "请修正该字段",
   email: "请输入正确格式的电子邮件",
   url: "请输入合法的网址",
   date: "请输入合法的日期",
   dateISO: "请输入合法的日期 (ISO).",
   number: "请输入合法的数字",
   digits: "只能输入整数",
   creditcard: "请输入合法的信用卡号",
   equalTo: "请再次输入相同的值",
   accept: "请输入拥有合法后缀名的字符串",
   maxlength: jQuery.validator.format("请输入一个长度最多是 {0} 的字符串"),
   minlength: jQuery.validator.format("请输入一个长度最少是 {0} 的字符串"),
   rangelength: jQuery.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
   range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
   max: jQuery.validator.format("请输入一个最大为 {0} 的值"),
   min: jQuery.validator.format("请输入一个最小为 {0} 的值")
});


jQuery.validator.addMethod("money", function(value, element) {
	var reg=/^(([1-9]{1}\d*)|(0{1}))(\.\d{2})$/;
	return this.optional(element) || (reg.test(value));
}, "请输入合理的金额，必须保留两位小数！");


jQuery.validator.addMethod( "account",function(value,element){
	var reg = /^\d+(\.\d{1,2})?$/;
	var reg2 = /^[0]*[1-9]+[0]*$/;
	var flag = false;
	
	if(reg.test(value) && reg2.test(value) && (value <= 100000)) flag = true;
	if(value != ''){
		if(!flag){	
			return false;
		}	
	}
	return true;
},"请输入合理的金额（最高金额为100000）");



jQuery.validator.addMethod( "mobile",function(value,element){
	var reg =/^\+?\d{0,12}$/ ;
	var flag = false;
	if(reg.test(value)) flag = true;
	if(value != ''){
		if(!flag){	
			return false;
		}	
	}
	return true;
},"请输入有效的号码");

jQuery.validator.addMethod( "chineseMobile",function(value,element){     
    var reg0 = /^13\d{5,9}$/;   
    var reg1 = /^15\d{5,9}$/;   
    var reg2 = /^189\d{4,8}$/;
    var reg3 =/^\+86?\d{0,11}$/ ;
    var my = false;   
    if (reg0.test(value))my=true;   
    if (reg1.test(value))my=true;   
    if (reg2.test(value))my=true;
    if (reg3.test(value))my=true;
    if(value!=''){if(!my){return false;}};   
   return true;    
} ,  "请输入有效的中国手机号码");

jQuery.validator.addMethod( "checkPost",function(value,element){      
    var pattern =/^[0-9]{6}$/;   
    if(value !=''){if(!pattern.exec(value)){return false;}};   
    return true;    
} ,  "请输入有效的中国邮政编码");

jQuery.validator.addMethod("qq", function(value, element) { 
	var tel = /^[1-9]\d{4,9}$/; 
	return this.optional(element) || (tel.test(value)); 
}, "qq号码格式错误");


jQuery.validator.addMethod("chrnum", function(value, element) { 
	var chrnum = /^([a-zA-Z0-9]+)$/; 
	return this.optional(element) || (chrnum.test(value)); 
}, "只能输入数字和字母");

jQuery.validator.addMethod("certno", function(value, element) {
	var certno1 = /^([a-zA-Z0-9()（）]+)$/;
	var certno2 = /^[A-Za-z]+$/;
	var flag1 = certno1.test(value);
	var flag2 = certno2.test(value);
	return this.optional(element) || (flag1 && !flag2 );
}, "身份证号码只能输入数字和字母的组合");


jQuery.validator.addMethod("regularName", function(value, element) { 
	var chinese = /^[0-9a-zA-Z\u4e00-\u9fa5]+$/; 
	return this.optional(element) || (chinese.test(value)); 
}, "只能输入汉字、字母或数字组合");

jQuery.validator.addMethod("regular", function(value, element) {
    var chinese = /^[0-9a-zA-Z\u4e00-\u9fa5\s\,\，\-]+$/;
    return this.optional(element) || (chinese.test(value));
}, "只能输入汉字、字母或数字组合");



jQuery.validator.addMethod("checkPWD", function(value,element){
	var reg = /^.*(?=.{12,20})(?=.*\d)(?=.*[A-Z]{1,})(?=.*[a-z]{1,})(?=.*[!@#$%^&*?\(\)]).*$/;
	return reg.test(value);
},'请输入12~20位数字、小写字母、大写字母、特殊符号4类');

jQuery.validator.addMethod("username", function(value, element) { 
	var chinese = /^([a-zA-Z0-9_]+)$/; 
	return this.optional(element) || (chinese.test(value)); 
}, "只能输入字母、数字和下划线组合");

jQuery.validator.addMethod("chinesename", function(value, element) { 
	var chinese = /^[\u4e00-\u9fa5]+$/; 
	return this.optional(element) || (chinese.test(value)); 
}, "只能输入中文汉字");

jQuery.validator.addMethod("englishname", function(value, element) { 
	var chinese = /^[a-zA-Z]+$/; 
	value=value.replace(/\s/g, "");
	return this.optional(element) || (chinese.test(value)); 
}, "只能输入英文字母");

jQuery.validator.addMethod("namer", function(value, element) {
	var chinese =/^[\u4e00-\u9fa5]+$/;
	var english = /^[a-zA-Z]+$/;
	value=value.replace(/\s/g, "");
	return this.optional(element) || (chinese.test(value))||(english.test(value));
}, "只能输入英文字母或者中文");

jQuery.validator.addMethod("checkPWDNew", function(value,element){
	var reg = /^((?=.*[a-z])(?=.*[A-Z])(?=.*[1-9])(?=.*[\-_\.!@#\$%\\\^&\*\)\(\+=\{\}\[\]\/",'<>~\·`\?:;\|#]) |(?=.*[a-z])(?=.*[A-Z])(?=.*[1-9])|(?=.*[a-z])(?=.*[A-Z])(?=.*[\-_\.!@#\$%\\\^&\*\)\(\+=\{\}\[\]\/",'<>~\·`\?:;\|#])|(?=.*[a-z])(?=.*[1-9])(?=.*[\-_\.!@#\$%\\\^&\*\)\(\+=\{\}\[\]\/",'<>~\·`\?:;\|#])|(?=.*[A-Z])(?=.*[1-9])(?=.*[\-_\.!@#\$%\\\^&\*\)\(\+=\{\}\[\]\/",'<>~\·`\?:;\|#])).{12,20}$/;
	return reg.test(value);
},'密码必须是12-20位，包含数字、小写字母、大写字母、特殊符号4类中至少3类');


jQuery.validator.addMethod("isKeyBoardContinuousChar", function(value,element){
	var result=true;
	var c1 = [
		["!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+"],
		["q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "{", "}", "|"],
		["a", "s", "d", "f", "g", "h", "j", "k", "l", ":", '"'],
		["z", "x", "c", "v", "b", "n", "m", "<", ">", "?"]
	];
	var c2 = [
		["1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "="],
		["q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "[", "]", "\\"],
		["a", "s", "d", "f", "g", "h", "j", "k", "l", ";", "'"],
		["z", "x", "c", "v", "b", "n", "m", ",", ".", "/"]
	];
	var str = value.toLowerCase().split("");
	//获取坐标位置
	var y = [];
	var x = [];
	for (var c = 0; c < str.length; c++) {
		y[c] = 0; //当做~`键处理
		x[c] = -1;
		for (var i = 0; i < c1.length; i++) {
			for (var j = 0; j < c1[i].length; j++) {
				if (str[c] == c1[i][j]) {
					y[c] = i;
					x[c] = j;
				}
			}
		}
		if (x[c] != -1) continue;
		for (var i = 0; i < c2.length; i++) {
			for (var j = 0; j < c2[i].length; j++) {
				if (str[c] == c2[i][j]) {
					y[c] = i;
					x[c] = j;
				}
			}
		}
	}
	//匹配坐标连线
	for (var c = 1; c < str.length - 1; c++) {
		if (y[c - 1] == y[c] && y[c] == y[c + 1]) {
			if (
				(x[c - 1] + 1 == x[c] && x[c] + 1 == x[c + 1]) ||
				(x[c + 1] + 1 == x[c] && x[c] + 1 == x[c - 1])
			) {
				result=false;
			}
		} else if (x[c - 1] == x[c] && x[c] == x[c + 1]) {
			if (
				(y[c - 1] + 1 == y[c] && y[c] + 1 == y[c + 1]) ||
				(y[c + 1] + 1 == y[c] && y[c] + 1 == y[c - 1])
			) {
				result=false;
			}
		}
	}
	return result;
},'避免键盘排序（横向，斜向）密码');


jQuery.validator.addMethod("isContainsError", function(value,element){
	var result=true;
	var c1 = ["qwa","CTG","2020","2021","banana","admin","root","huawei","cisco","123456","11111","test"];
	var str = value.toLowerCase();
	for (var c = 0; c < c1.length; c++) {
		if(str.indexOf(c1[c])>-1){
			console.log(value)
			result=false;
			break;
		}
	}
	return result;
},'易被猜测的密码');

jQuery.validator.addMethod("prices", function(value, element) {
	var chinese =/^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
	// value=value.replace(/\s/g, "");
	return this.optional(element) || (chinese.test(value));
}, "请输入正确金额");
