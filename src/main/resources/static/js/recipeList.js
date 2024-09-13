document.addEventListener('DOMContentLoaded', () => {
	const container = document.querySelector('.recipe-grid');
	if (container) {
		const recipeItems = document.querySelectorAll('.recipe-item'); // 모든 레시피 아이템을 선택

		// 레시피 이름과 링크를 객체로 저장
		const recipeMap = {};
		recipeItems.forEach(item => {
			const recipeName = item.getAttribute('data-recipe-name').trim();
			const recipeUrl = item.getAttribute('data-recipe-url'); // 페이지 링크는 data-recipe-url 속성으로 추가
			recipeMap[recipeName] = recipeUrl;
		});

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
					.join("").trim(); // 음성 인식 결과
				console.log("인식된 내용:", transcript);

				// 인식된 텍스트가 레시피 이름과 일치하는지 확인
				if (recipeMap[transcript]) {
					console.log(`레시피 ${transcript} 페이지로 이동 중...`);
					window.location.href = recipeMap[transcript]; // 해당 레시피 페이지로 이동
				} else {
					console.log(`레시피 '${transcript}'을 찾을 수 없습니다.`);
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
		startSpeechRecognition();

		// 스크롤을 페이지 상단으로 이동시키는 함수
		function scrollToTop() {
			window.scrollTo({
				top: 0,
				behavior: 'smooth' // 부드러운 스크롤 효과
			});
		}
	} else {
		console.error('Recipe grid container not found!');
	}
});

let currentSpeech = null; // 현재 재생 중인 SpeechSynthesisUtterance 객체를 추적

// TTS 기능을 구현하는 함수
function speakText(text) {
	// 만약 이전에 재생 중인 TTS가 있다면 취소
	if (window.speechSynthesis.speaking || window.speechSynthesis.pending) {
		console.log("이전 TTS 중지");
		window.speechSynthesis.cancel(); // 현재 재생 중인 TTS를 중지
	}

	if ('speechSynthesis' in window) {
		// 새로운 음성 재생
		const speech = new SpeechSynthesisUtterance(text);
		speech.lang = 'ko-KR'; // 한국어 설정

		speech.onstart = function() {
			console.log("TTS 시작: " + text);
		};

		speech.onend = function() {
			console.log("TTS 종료: " + text);
			currentSpeech = null; // 현재 재생 중인 음성 종료 후 초기화
		};

		speech.onerror = function(event) {
			console.error('TTS 오류:', event.error);
		};

		currentSpeech = speech; // 현재 재생 중인 음성을 추적
		window.speechSynthesis.speak(speech); // 음성 재생
	} else {
		console.error('TTS 기능을 지원하지 않는 브라우저입니다.');
	}
}

// 레시피 이름을 읽어주는 함수
function speakRecipeName(element) {
	const recipeName = element.getAttribute('data-recipe-name');
	speakText(recipeName); // TTS로 레시피 이름 읽기
}

document.addEventListener('DOMContentLoaded', () => {
	const container = document.querySelector('.recipe-grid');
	if (container) {
		const recipeItems = document.querySelectorAll('.recipe-item'); // 모든 레시피 아이템 선택

		// 각 레시피 아이템에 마우스 올렸을 때 TTS 재생
		recipeItems.forEach(item => {
			item.addEventListener('mouseenter', function() {
				speakRecipeName(item); // 마우스를 올린 카드의 이름을 읽음
			});
		});
	} else {
		console.error('Recipe grid container not found!');
	}
});

