'use strict'; /* 厳格にエラーをチェック */

/* ローカルスコープ */

window.addEventListener('DOMContentLoaded', ()=>{
  const t0=60*60*1000;
  const t1=new Date().getTime();
  setInterval(()=>{
    const t2=new Date().getTime();
    const t3=t0+t1-t2
    const m=(parseInt(t3/60/1000)).toString().padStart(2,'0');
    const s=(parseInt(t3/1000)%60).toString().padStart(2,'0');
    const ms=(parseInt(t3/10)%100).toString().padStart(2,'0');
    timer.textContent=`残り時間：${m}分${s}秒${ms}`;
//    if(${m} == 59 && ${s} == 50 && ${ms} == 00){
//        document.getElementById('submit').onclick;
//    }
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
    for(let i = 1; i<=10; i++){
        let answer_1pt = 'answer_1pt' + i;
        let user_answer = document.getElementById(answer_1pt).value; // IDに対応する要素を取得
        let element = document.getElementById(i); // IDに対応する要素を取得
        if (element) {
            if(user_answer == 1){
                element.style.backgroundColor = '#9bc6f4'; // 背景色を変更
            }
            else{
                element.style.backgroundColor = '#f08787'; // 背景色を変更
            }
        }
    }

    for(let i = 1; i<=5; i++){
            let answer_2pt = 'answer_2pt' + i;
            let user_answer = document.getElementById(answer_2pt).value; // IDに対応する要素を取得

            let s = "9" + i
            let element = document.getElementById(s); // IDに対応する要素を取得
            if (element) {
                if(user_answer == 2){
                    element.style.backgroundColor = '#9bc6f4'; // 背景色を変更
                }
                else{
                    element.style.backgroundColor = '#f08787'; // 背景色を変更
                }
            }
        }

