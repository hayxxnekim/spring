console.log("boardRegister.js test");

//트리거 버튼 처리(trigger 클릭 => files 클릭)
document.getElementById('trigger').addEventListener('click', ()=>{
    document.getElementById('files').click();
});

//실행 파일, 이미지 파일에 대한 정규 표헌식 작성
//확장자이기 때문에 .으로 시작
const regExp = new RegExp("\.(exe|sh|bat|js|msi|dll)$"); //실행 파일 막기
const regExpImg = new RegExp("\.(jpg|jpeg|png|gif)$"); //이미지 파일만
const maxSize = 1024*1024*20; //파일 최대 사이즈

function fileValidation(fileName, fileSize) {
    if(!regExpImg.test(fileName)) {
        //이미지가 아닌 것을 거름
        return 0;
    } else if(regExp.test(fileName)) {
        //실행 파일 거름
        return 0;
    } else if(fileSize>maxSize) {
        //사이즈 초과 거름
        return 0;
    } else {
        //그외 나머지 1 return
        return 1;
    }
}

//변화가 생겼다면, 해당 객체 받아옴
document.addEventListener('change',(e)=> {
    //객체의 아이디가 files인 경우
    if(e.target.id=='files') {
        //파일을 다시 추가할 때는 버튼 상태를 원래대로 변경
        document.getElementById('regBtn').disabled = false;

        //input file element에 저장된 file의 정보를 가져오는 property
        //실제 파일 정보 가져오기
        const fileObj = document.getElementById('files').files;
        console.log(fileObj);
        //FileList {0: File, 1: File, 2: File, length: 3}0: FilelastModified: 1688088814271lastModifiedDate: Fri Jun 30 2023 10:33:34 GMT+0900 (한국 표준시) {}name: "steve.jpg"size: 13271type: "image/jpeg"webkitRelativePath: ""[[Prototype]]: File1: File {name: 'villager.png', lastModified: 1688088809155, lastModifiedDate: Fri Jun 30 2023 10:33:29 GMT+0900 (한국 표준시), webkitRelativePath: '', size: 616, …}2: File {name: '랏소.jpg', lastModified: 1697440176386, lastModifiedDate: Mon Oct 16 2023 16:09:36 GMT+0900 (한국 표준시), webkitRelativePath: '', size: 7569, …}length: 3[[Prototype]]: FileList
        
        //첨부 파일에 대한 정보를 fileZone에 기록
        let div = document.getElementById('fileZone');
        //기존 값이 있다면 삭제
        div.innerHTML = "";
        //ul => li로 첨부 파일 추가
        //<ul class="list-group list-group-flush">
        //<li class="list-group-item">An item</li>
        let isOk = 1; //여러 파일이 모두 검증에 통과해야 하기 때문에 *로 각 파일마다 통과여부 확인
        let ul = `<ul class="list-group list-group-flush">`;
            //ul 안에 누적
            for(let file of fileObj) {
                let validResult = fileValidation(file.name, file.size); //0 또는 1로 리턴
                isOk *= validResult; //모든 파일 통과 여부
                ul += `<li class="list-group-item">`; 
                ul += `<div class="mb-3">`;
                //결과에 따라 업로드 여부 표시
                //1 : ture
                ul += `${validResult ? '<div class="fw-bold text-success">업로드 가능</div>' : '<div class="fw-bold text-danger">업로드 불가능</div>'}`;
                ul += `${file.name}</div>`;
                //1이면 초록색, 0이면 빨간색
                ul += `<span class="badge rounded-pill text-bg-${validResult ? 'success' :'danger'}">${file.size}Bytes</span></li>`;
            }
            ul += `</ul>`;
            div.innerHTML = ul;

            //파일 등록 불가 => 등록 버튼 안보이게
            if(isOk==0) {
                document.getElementById('regBtn').disabled = true;
            }
    }
})