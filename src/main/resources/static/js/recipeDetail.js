// TTS 관련 함수
function speakRecipe() {
	// SpeechSynthesis API 지원 여부 확인
	if ('speechSynthesis' in window) {
		// 음성 합성 객체 생성
		const synth = window.speechSynthesis;

		// 요소에서 텍스트 가져오기
		const title = document.getElementById('recipeTitle').innerText;
		const manual = document.getElementById('recipeManual').innerText;
		const tips = document.getElementById('recipeTips').innerText;

		// 읽을 내용 생성
		const textToSpeak = `${title}. 조리법: ${manual}. 팁: ${tips}`;

		// 음성 합성 객체 설정
		const utterance = new SpeechSynthesisUtterance(textToSpeak);

		// 선택적: 언어와 목소리 설정
		// utterance.lang = 'ko-KR'; // 예: 한국어
		// utterance.voice = synth.getVoices().find(voice => voice.name === 'Google UK English Female');

		// 음성 재생
		synth.speak(utterance);
	} else {
		alert('이 브라우저는 SpeechSynthesis API를 지원하지 않습니다.');
	}
}

// 메뉴얼 관련 함수
document.addEventListener("DOMContentLoaded", function() {
	// 요소에서 텍스트 가져오기
	const manualElement = document.getElementById('recipeManual');
	let manualText = manualElement.innerText;

	// NULL을 빈 문자열로 대체
	manualText = manualText.split(',').filter(item => item.trim() !== 'NULL' && item.trim() !== '').join('<br>');

	// 처리된 텍스트를 HTML로 설정
	manualElement.innerHTML = manualText;
});

document.addEventListener("DOMContentLoaded", function() {
    const imagesElement = document.getElementById('recipeImages');
    const imageUrlsText = document.getElementById('recipeImageUrls').innerText;

    // 쉼표로 텍스트 분리 및 NULL 제거
    const validUrls = imageUrlsText.split(',')
                                   .map(url => url.trim())  // 공백 제거
                                   .filter(url => url !== 'NULL' && url !== '');  // NULL과 빈 문자열 제거

    // 각 유효한 URL에 대해 <img> 태그 생성 및 추가
    validUrls.forEach(url => {
        const imgElement = document.createElement('img');
        imgElement.src = url;
        imgElement.alt = "레시피 이미지";
        imgElement.style.width = "100%";
        imgElement.style.maxWidth = "600px";
        imgElement.style.borderRadius = "8px";
        imgElement.style.marginBottom = "10px";
        
        imagesElement.appendChild(imgElement);
    });
});
