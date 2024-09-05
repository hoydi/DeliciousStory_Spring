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

	let text = document.getElementById(textId).textContent;

	// ** 및 NULL을 제외하고 텍스트를 필터링
	let filteredText = text
		.split('**') // ** 구분자로 분리
		.filter(line => line.trim() !== 'NULL' && line.trim() !== '') // NULL 및 빈 줄 필터링
		.join('. '); // 각 문장을 마침표로 이어붙이기 (선택 사항)

	tts = new SpeechSynthesisUtterance(filteredText);
	speechSynthesis.speak(tts);
}

function toggleTTS() {
	const manualText = document.getElementById('recipeManual').textContent;

	// ** 및 NULL을 제외하고 텍스트를 필터링
	let filteredText = manualText
		.split('**') // ** 구분자로 분리
		.filter(line => line.trim() !== 'NULL' && line.trim() !== '') // NULL 및 빈 줄 필터링
		.join('. '); // 각 문장을 마침표로 이어붙이기 (선택 사항)

	if (isPlaying) {
		speechSynthesis.cancel();  // TTS 중지
	} else {
		tts = new SpeechSynthesisUtterance(filteredText);
		speechSynthesis.speak(tts);  // TTS 시작
	}

	isPlaying = !isPlaying;  // 상태 토글
}

// 조리법 및 이미지 매칭 처리 함수
document.addEventListener("DOMContentLoaded", function() {
	const manualElement = document.getElementById('recipeManual');
	const manualText = manualElement ? manualElement.innerText : '';

	// **를 기준으로 조리법을 나누기
	const lines = manualText.split('**').filter(item => item.trim() !== 'NULL' && item.trim() !== '');

	// 이미지 URL 텍스트 처리
	const imageUrlsText = document.getElementById('recipeImageUrls').innerText;
	const validUrls = imageUrlsText.split('**')
		.map(url => url.trim())
		.filter(url => url !== 'NULL' && url !== '');

	// 조리법 컨테이너에 텍스트와 이미지를 순서대로 추가
	const manualContainer = document.getElementById('recipeManualContainer');
	manualContainer.innerHTML = '';

	lines.forEach((line, index) => {
		// 조리법 텍스트 추가
		const stepDiv = document.createElement('div');
		stepDiv.classList.add('recipe-step');

		const span = document.createElement('span');
		span.id = `recipeManualLine${index}`;
		span.classList.add('clickable-text');
		span.innerText = `${line.trim()}`; // 번호 붙이기
		span.onclick = () => speakText(span.id);
		stepDiv.appendChild(span);

		// 해당 단계의 이미지 추가 (이미지가 있으면 추가)
		if (validUrls[index]) {
			const imgElement = document.createElement('img');
			imgElement.src = validUrls[index];
			imgElement.alt = `레시피 단계 ${index + 1} 이미지`;
			imgElement.style.width = "100%";
			imgElement.style.maxWidth = "600px";
			imgElement.style.borderRadius = "8px";
			imgElement.style.marginBottom = "10px";
			stepDiv.appendChild(imgElement);
		}

		// 조리법 및 이미지를 컨테이너에 추가
		manualContainer.appendChild(stepDiv);
	});
});


// 좋아요 관련 함수