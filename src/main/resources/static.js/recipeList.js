// TTS 기능을 구현하는 함수
function speakText(text) {
    if ('speechSynthesis' in window) {
        const speech = new SpeechSynthesisUtterance(text);
        speech.lang = 'ko-KR'; // 한국어 설정 (필요에 따라 변경 가능)
        window.speechSynthesis.speak(speech);
    } else {
        console.error('TTS 기능을 지원하지 않는 브라우저입니다.');
    }
}

// 스크롤을 페이지 상단으로 이동시키는 함수
function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: 'smooth', // 부드러운 스크롤 효과
    });
}
