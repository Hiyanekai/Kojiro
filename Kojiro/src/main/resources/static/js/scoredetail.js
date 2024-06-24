'use strict'; /* 厳格にエラーをチェック */

/* ローカルスコープ */

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
    for(let i = 1; i<=95; i++){
            let answer = 'answer' + i;
            let question = 'question' + i;
            let user_answer = document.getElementById(answer).value; // IDに対応する要素を取得

            console.log(user_answer);

            let s = i;
            let element = document.getElementById(s); // IDに対応する要素を取得
            let elementP2 = document.getElementById(question); // IDに対応する要素を取得

            if (element) {
                if(user_answer == 2 || user_answer ==1){
                    console.log("true");
                    element.style.backgroundColor = '#9bc6f4'; // 背景色を変更
                    elementP2.style.backgroundColor = '#9bc6f4'; // 背景色を変更
                }
                else{
                console.log("false");
                    element.style.backgroundColor = '#f08787'; // 背景色を変更
                    elementP2.style.backgroundColor = '#f3b5ab'; // 背景色を変更
                }
            }
        }


