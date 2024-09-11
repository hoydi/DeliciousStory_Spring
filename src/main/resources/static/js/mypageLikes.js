document.addEventListener('DOMContentLoaded', () => {
	const container = document.querySelector('.recipe-grid');
	if (container) {
		// Thymeleaf에서 삽입된 data-total-pages와 data-current-page 값 읽기
		const totalPages = parseInt(container.dataset.totalPages, 10);
		let currentPage = parseInt(container.dataset.currentPage, 10);

		// 디버깅: 데이터 속성 값 로그
		console.log('Total Pages:', totalPages);
		console.log('Current Page:', currentPage);

		function loadMoreRecipes() {
			if (currentPage >= totalPages) {
				return;
			}

			fetch(`/mypage/likes?page=${currentPage + 1}`)
				.then(response => response.text())
				.then(html => {
					const parser = new DOMParser();
					const doc = parser.parseFromString(html, 'text/html');
					const newRecipes = doc.querySelectorAll('.recipe-item');
					newRecipes.forEach(item => container.appendChild(item));
					currentPage++;
				})
				.catch(error => console.error('Error loading more recipes:', error));
		}

		window.addEventListener('scroll', () => {
			if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
				loadMoreRecipes();
			}
		});

		loadMoreRecipes(); // Load initial recipes
	} else {
		console.error('Recipe grid container not found!');
	}

	// 스크롤을 페이지 상단으로 이동시키는 함수
	function scrollToTop() {
		window.scrollTo({
			top: 0,
			behavior: 'smooth' // 부드러운 스크롤 효과
		});
	}

	// TTS 기능을 구현하는 함수
	function speakText(text) {
		if ('speechSynthesis' in window) {
			const speech = new SpeechSynthesisUtterance(text);
			speech.lang = 'ko-KR'; // 한국어 설정
			window.speechSynthesis.speak(speech);
			console.log(text);
		} else {
			console.error('TTS 기능을 지원하지 않는 브라우저입니다.');
		}
	}

	// 레시피 이름을 읽어주는 함수
	function speakRecipeName(element) {
		const recipeName = element.getAttribute('data-recipe-name');
		speakText(recipeName);
	}

	// 페이지 상단 이동 버튼 클릭 시 이벤트 리스너 추가
	const scrollToTopButton = document.querySelector('.scroll-to-top');
	if (scrollToTopButton) {
		scrollToTopButton.addEventListener('click', scrollToTop);
	} else {
		console.error('Scroll to top button not found!');
	}
});
