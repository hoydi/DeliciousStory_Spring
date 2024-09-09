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

// 좋아요 상태 확인 함수
function checkLikeStatus(recipeId) {
	const likeButton = document.getElementById("likeButton");

	// 좋아요 상태 확인
	fetch(`/siteRecipe/${recipeId}/like/status`, {
		method: 'GET'
	})
		.then(response => response.json()) // JSON 응답으로 파싱
		.then(status => {
			if (status.status === "alreadyLiked") {
				likeButton.innerText = "좋아요 취소";
				likeButton.setAttribute('onclick', 'handleLikeCancel()');
			} else if (status.status === "notLoggedIn") {
				likeButton.innerText = "로그인이 필요합니다";
				likeButton.removeAttribute('onclick');
			} else {
				likeButton.innerText = "좋아요";
				likeButton.setAttribute('onclick', 'handleLike()');
			}
		})
		.catch(error => console.error('Error:', error));
}

// 좋아요 등록 처리
function handleLike() {
	const likeButton = document.getElementById("likeButton");
	const recipeId = likeButton.getAttribute("data-recipe-id");

	fetch(`/siteRecipe/${recipeId}/like`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			'X-Requested-With': 'XMLHttpRequest'  // AJAX 요청임을 명시
		}
	})
		.then(response => {
			// HTML 응답인지 확인
			const contentType = response.headers.get("content-type");
			if (contentType && contentType.includes("application/json")) {
				return response.json(); // JSON이면 파싱
			} else {
				return response.text(); // HTML일 경우 텍스트로 처리
			}
		})
		.then(data => {
			if (data.status === 'liked') {
				alert('좋아요가 등록되었습니다.');
				likeButton.innerText = "좋아요 취소";
				likeButton.setAttribute('onclick', 'handleLikeCancel()');
			} else if (data.status === 'alreadyLiked') {
				alert('이미 좋아요를 누른 상태입니다.');
			} else if (data.status === 'notLoggedIn') {
				alert('로그인이 필요합니다.');
				window.location.href = '/login';  // 로그인 페이지로 리다이렉트
			}
		})
		.catch(error => console.error('Error:', error));
}
// 좋아요 취소 처리
function handleLikeCancel() {
	const likeButton = document.getElementById("likeButton");
	const recipeId = likeButton.getAttribute("data-recipe-id");

	fetch(`/siteRecipe/${recipeId}/like/cancel`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		}
	})
		.then(response => {
			// HTML 응답인지 확인
			const contentType = response.headers.get("content-type");
			if (contentType && contentType.includes("application/json")) {
				return response.json(); // JSON이면 파싱
			} else {
				return response.text(); // HTML일 경우 텍스트로 처리
			}
		})
		.then(data => {
			if (data.status === 'cancelled') {
				alert('좋아요가 취소되었습니다.');
				likeButton.innerText = "좋아요";
				likeButton.setAttribute('onclick', 'handleLike()');
			} else if (data.status === 'notLiked') {
				alert('좋아요 상태가 아닙니다.');
			} else if (data.status === 'notLoggedIn') {
				alert('로그인이 필요합니다.');
				window.location.href = '/login';  // 로그인 페이지로 리다이렉트
			}
		})
		.catch(error => console.error('Error:', error));
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
		span.innerText = `${line.trim()}`;
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

	// 페이지 로드 시 좋아요 상태 확인
	const likeButton = document.getElementById("likeButton");
	const recipeId = likeButton.getAttribute("data-recipe-id");
	checkLikeStatus(recipeId);  // 좋아요 상태 확인
});
