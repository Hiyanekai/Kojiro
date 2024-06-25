'use strict'; /* 厳格にエラーをチェック */

/* ローカルスコープ */

window.addEventListener('DOMContentLoaded', ()=>{
  const t0=50*60*1000;
  const t1=new Date().getTime();
  const form = document.querySelector('form'); // フォーム要素を取得

  const intervalId = setInterval(()=>{
    const t2=new Date().getTime();
    const t3=t0+t1-t2
    if (t3 <= 0) {
        clearInterval(intervalId); // タイマー停止
        form.submit(); // フォームを自動的に送信
    }
    else{
        const m=(parseInt(t3/60/1000)).toString().padStart(2,'0');
        const s=(parseInt(t3/1000)%60).toString().padStart(2,'0');
        const ms=(parseInt(t3/10)%100).toString().padStart(2,'0');
        timer.textContent=`残り時間：${m}分${s}秒${ms}`;
    }
  },10);
});

/*if ('addEventListener' in document) {
    document.addEventListener('DOMContentLoaded', function() {
        FastClick.attach(document.body);
    }, false);
}*/

function radio_func(data) {
    let element = document.getElementById(data); // IDに対応する要素を取得
    if (element) {
        element.style.backgroundColor = '#FFFF66'; // 背景色を変更
    }
}

function radio_funcP2(data) {
    let P2data = '9' + data;
    console.log(P2data);
    let element = document.getElementById(P2data); // IDに対応する要素を取得
    if (element) {
        element.style.backgroundColor = '#FFFF66'; // 背景色を変更
    }
}



