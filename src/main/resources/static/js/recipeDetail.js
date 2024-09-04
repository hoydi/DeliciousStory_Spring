// TTS 관련 함수
function speakText(elementId) {
	if ('speechSynthesis' in window) {
		const synth = window.speechSynthesis;
		const textToSpeak = document.getElementById(elementId).innerText;
		const utterance = new SpeechSynthesisUtterance(textToSpeak);
		synth.speak(utterance);
	} else {
		alert('이 브라우저는 SpeechSynthesis API를 지원하지 않습니다.');
	}
}

// TTS 기능을 위한 JavaScript 코드
let tts = null;
let isPlaying = false;

function speakTextAll(textId) {
	if (tts) {
		tts.cancel();  // 현재 재생 중인 TTS를 취소
	}

	const text = document.getElementById(textId).textContent;
	tts = new SpeechSynthesisUtterance(text);
	speechSynthesis.speak(tts);
}

function toggleTTS() {
	const manualText = document.getElementById('recipeManual').textContent;

	if (isPlaying) {
		speechSynthesis.cancel();  // TTS 중지
	} else {
		tts = new SpeechSynthesisUtterance(manualText);
		speechSynthesis.speak(tts);  // TTS 시작
	}

	isPlaying = !isPlaying;  // 상태 토글
}


// 메뉴얼 관련 함수
document.addEventListener("DOMContentLoaded", function() {
	const manualElement = document.getElementById('recipeManual');
	const manualText = manualElement ? manualElement.innerText : '';

	// NULL을 빈 문자열로 대체
	const lines = manualText.split('**').filter(item => item.trim() !== 'NULL' && item.trim() !== '');

	const manualContainer = document.getElementById('recipeManualContainer');
	manualContainer.innerHTML = '';

	lines.forEach((line, index) => {
		const span = document.createElement('span');
		span.id = `recipeManualLine${index}`;
		span.classList.add('clickable-text');
		span.innerText = line;
		span.onclick = () => speakText(span.id); // 클릭 시 음성 합성 호출
		manualContainer.appendChild(span);
		manualContainer.appendChild(document.createElement('br')); // 줄 바꿈 추가
	});

	// 이미지 URL 처리
	const imagesElement = document.getElementById('recipeImages');
	const imageUrlsText = document.getElementById('recipeImageUrls').innerText;
	const validUrls = imageUrlsText.split('**')
		.map(url => url.trim())
		.filter(url => url !== 'NULL' && url !== '');

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
