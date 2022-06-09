$("#submit").click(function () {
    if (!$("#username").val()) {
        swal.fire('请输入用户名!')
        return;
    }
    if (!$("#password").val()) {
        swal.fire('请输入密码!')
        return;
    }

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
                )
            }
        },
        fail: function () {
            window.location.href = getRealPath() + "/500";

        }
    });
})