// 슬라이더의 인스턴스를 관리하기 위한 객체
const sliders = {
    1: {
        currentSlide: 0,
        slides: document.querySelectorAll('#slider-wrapper1 .slide'),
        dotsContainer: document.getElementById('dots1'),
        sliderWrapper: document.getElementById('slider-wrapper1')
    },
    2: {
        currentSlide: 0,
        slides: document.querySelectorAll('#slider-wrapper2 .slide'),
        dotsContainer: document.getElementById('dots2'),
        sliderWrapper: document.getElementById('slider-wrapper2')
    }
};

// 도트 생성 함수
function createDots(sliderId) {
    const slider = sliders[sliderId];
    slider.slides.forEach((_, index) => {
        const dot = document.createElement('div');
        dot.className = 'dot';
        dot.addEventListener('click', () => goToSlide(sliderId, index));
        slider.dotsContainer.appendChild(dot);
    });
}

// 도트 업데이트 함수
function updateDots(sliderId) {
    const slider = sliders[sliderId];
    document.querySelectorAll(`#dots${sliderId} .dot`).forEach((dot, index) => {
        dot.classList.toggle('active', index === slider.currentSlide);
    });
}

// 슬라이드 표시 함수
function showSlide(sliderId, index) {
    const slider = sliders[sliderId];
    slider.sliderWrapper.style.transform = `translateX(-${index * 100}%)`;
    slider.currentSlide = index;
    updateDots(sliderId);
}

// 다음 슬라이드 함수
function nextSlide(sliderId) {
    const slider = sliders[sliderId];
    showSlide(sliderId, (slider.currentSlide + 1) % slider.slides.length);
}

// 이전 슬라이드 함수
function prevSlide(sliderId) {
    const slider = sliders[sliderId];
    showSlide(sliderId, (slider.currentSlide - 1 + slider.slides.length) % slider.slides.length);
}

// 슬라이드로 이동 함수
function goToSlide(sliderId, index) {
    showSlide(sliderId, index);
}

// 초기화 함수
function initializeSliders() {
    Object.keys(sliders).forEach(sliderId => {
        createDots(sliderId);
        updateDots(sliderId);
    });
}

// 이벤트 리스너 설정
document.addEventListener('DOMContentLoaded', initializeSliders);


// ** 공식레시피

// TTS 기능을 구현하는 함수
function speakText(text) {
    if ('speechSynthesis' in window) {
        const speech = new SpeechSynthesisUtterance(text);
        speech.lang = 'ko-KR'; // 한국어 설정
        window.speechSynthesis.speak(speech);
    } else {
        console.error('TTS 기능을 지원하지 않는 브라우저입니다.');
    }
}


        function handleJoinClick() {
            window.location.href = '/register'; // 페이지를 /signup으로 이동
        }
		
		function handleFreeClick() {
		    window.location.href = '/boards'; // 페이지를 /signup으로 이동
		}
		
		function handleUserRecipeClick() {
		    window.location.href = '/userRecipe'; // 페이지를 /signup으로 이동
		}
		
		
	