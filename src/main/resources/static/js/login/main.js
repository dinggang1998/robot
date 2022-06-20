function getVerify() {
    $.ajax({
        type: "GET",
        url: getRealPath() + "/api/v1/common/captcha/kaptcha",
        data: {},
        dataType: "json",
        success: function (data) {
            if (data.code == "200") {
                $("[name='imgVerify']").attr('src', 'data:image/jpg;base64,' + data.data.codeImg);
                $("[name='sessionId']").val(data.data.sessionId)
            } else {
                swal.fire(
                    '失败!',
                    '验证码获取失败',
                    'error'
                )
            }
        },
        fail: function () {
            window.location.href = getRealPath() + "/500";

        }
    });


}

$(function () {

    getVerify();

    $.validator.addMethod("checkCode", function (value, element) {
        var a = true;
        $.ajax({
            type: "POST",
            url: getRealPath() + "/api/v1/common/codeVerify",
            data: {imageCode: value, sessionId: $("#sessionId").val()},
            dataType: "json",
            async:false,
            success: function (data) {
                if (data.code != "200") {
                    a = false;
                    $("#imgVerify").attr('');
                }
            }
        });
        return a;
    }, "输入的验证码有误");


    $("#loginForm").validate({
        rules: {
            "imagecode": {
                checkCode: true
            }
        },
        submitHandler: function (loginForm) {
            $.ajax({
                type: "POST",
                url: getRealPath() + "/api/v1/login/loginWeb",
                data: {username: $("#username").val(), pwd: $("#password").val()},
                dataType: "json",
                success: function (data) {
                    if (data.code == "200") {
                        window.location.href = getRealPath() + '/index';
                    } else {
                        swal.fire(
                            '登录失败!',
                            data.message,
                            'error'
                        );
                        $("#username").attr('');
                        $("#password").attr('');
                        $("#imgVerify").attr('');

                    }
                },
                error: function () {
                    window.location.href = getRealPath() + "/500";
                }
            });
        },
        focusInvalid: true
    });
});