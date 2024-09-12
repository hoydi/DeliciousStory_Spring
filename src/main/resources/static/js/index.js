// 음성 인식 함수
function startSpeechRecognition() {
    const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
    const recognition = new SpeechRecognition();

    recognition.interimResults = false; // 중간 결과는 제외
    recognition.lang = 'ko-KR'; // 한국어로 설정

    recognition.onstart = function() {
        console.log("음성 인식 시작");
    };

    recognition.onresult = function(event) {
        const transcript = Array.from(event.results)
            .map(result => result[0].transcript)
            .join("");
        console.log("인식된 내용:", transcript);

        // "공식레시피"라는 단어가 포함되었을 때 페이지 이동
        if (transcript.includes('레시피')) {
            console.log("공식레시피 인식됨. 페이지 이동 중...");
            window.location.href = '/siteRecipe'; // 공식레시피 페이지로 이동
        }
    };

    recognition.onerror = function(event) {
        console.error("음성 인식 오류:", event.error);
    };

    recognition.onend = function() {
        console.log("음성 인식 종료");
        // 음성 인식이 끝난 후 자동으로 재시작
        recognition.start();
    };

    recognition.start(); // 음성 인식 시작
}

// 페이지 로드 후 자동으로 음성 인식 시작
window.onload = function() {
    startSpeechRecognition();
};
