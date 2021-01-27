class AsyncPageError extends Error{
    response;
    constructor(response){
        super();
        this.response = response;
    }

    alertMessage(){
        //이 페이지에 잇는 모든 scripts 태그들을 불러온다.
        let scripts = document.scripts;

        /* response로 받은 html코드를 new function을 사용해 실행  */
        this.response.then((text) => {
            console.dir(text);
            document.querySelector('html').innerHTML = text;
            //마지막 스크립트 태그 안의 자바스크립트코드를 문자열로 가져와
            //new Function을 사용해 실행
            new Function(scripts[scripts.length - 1].innerHTML)();

        });
    }
}