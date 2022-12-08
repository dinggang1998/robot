function test() {
    swal.fire({
        title: '都跟你讲了开发中，点个锤子',
        width: 600,
        padding: '3em',
        color: '#716add',
        background: '#fff url(/images/trees.png)',
        backdrop: `rgba(0,0,123,0.4) url("/images/nyan-cat.gif") left top no-repeat`
    })
}

function test2() {
    swal.fire({
        title: '这个功能也在开发中，再点就禁用你',
        width: 600,
        padding: '3em',
        color: '#716add',
        background: '#fff url(/images/trees.png)',
        backdrop: `rgba(0,0,123,0.4) url("/images/nyan-cat.gif") left top no-repeat`
    })
}

function test3() {
    swal.showLoading();
}

function test1() {

    let timerInterval
    swal.fire({
        title: '炸死你个鳖孙',
        html: '炸弹倒计时 <b></b> 秒.',
        timer: 5000,
        timerProgressBar: true,
        didOpen: () => {
            Swal.showLoading()
            const b = Swal.getHtmlContainer().querySelector('b')
            timerInterval = setInterval(() => {
                b.textContent = Swal.getTimerLeft()
            }, 100)
        },
        willClose: () => {
            clearInterval(timerInterval)
        }
    }).then((result) => {
        /* Read more about handling dismissals below */
        if (result.dismiss === Swal.DismissReason.timer) {
            console.log('I was closed by the timer')
        }
    })

}

function test4() {
    swal.fire({
        title: '您的邮箱',
        width: 600,
        padding: '3em',
        text:'邮箱里只有一封邮件：来自小叮当发送的（你是大沙壁）'
    })
}