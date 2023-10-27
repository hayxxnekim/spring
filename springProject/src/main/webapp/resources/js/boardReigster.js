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
    } else if(regExp.test(file)) {
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
        //input file element에 저장된 file의 정보를 가져오는 property
        //실제 파일 정보 가져오기
        const fileObj = document.getElementById('files').files;
        console.log(fileObj);
    }
})