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
///////////////////////여기부터 더미
// 레시피 데이터를 추가하는 함수
function addRecipe(recipe) {
    const recipeList = document.getElementById('recipe-list');
    const recipeElement = document.createElement('div');
    recipeElement.className = 'box-container';
    recipeElement.onclick = () => window.location.href = `/galleryPost?id=${recipe.id}`;
    recipeElement.onmouseover = () => speakText(recipe.name);

    recipeElement.innerHTML = `
        <div class="image-container">
            <img src="${recipe.image}" alt="${recipe.name}" width="250" height="250" style="border-radius: 4px;">
            <div class="image-overlay">
                <p style="font-size: 18px; font-weight: bold;">상세 보기</p>
            </div>
        </div>
        <p style="font-size: 12px; margin-top: 8px; color: #8C8C8C;">${recipe.calories} kcal</p>
        <p style="font-size: 14px; font-weight: bold; margin-top: 2px;">${recipe.name}</p>
    `;
    
    recipeList.appendChild(recipeElement);
}
// 더미 레시피 데이터
const dummyRecipes = [
    {
        "id": "1",
        "name": "레시피 1",
        "image": "/images/recipeImg1.png",
        "ingredients": "재료 1, 재료 2",
        "manual": "조리법 1",
        "calories": "200"
    },
    {
        "id": "2",
        "name": "레시피 2",
        "image": "/images/recipeImg2.png",
        "ingredients": "재료 3, 재료 4",
        "manual": "조리법 2",
        "calories": "300"
    },
    {
        "id": "3",
        "name": "레시피 3",
        "image": "/images/recipeImg3.png",
        "ingredients": "재료 5, 재료 6",
        "manual": "조리법 3",
        "calories": "150"
    },
    {
        "id": "4",
        "name": "레시피 4",
        "image": "/images/recipeImg4.png",
        "ingredients": "재료 7, 재료 8",
        "manual": "조리법 4",
        "calories": "250"
    }
];

// 페이지가 로드되면 더미 데이터를 추가합니다.
document.addEventListener('DOMContentLoaded', () => {
    dummyRecipes.forEach(recipe => addRecipe(recipe));
});

///////////////////////여기까지 더미




// ** 텍스트영역

// Join 버튼 클릭 핸들러
        function handleJoinClick() {
            window.location.href = '/register'; // 페이지를 /signup으로 이동
        }
		
		function handleFreeClick() {
		    window.location.href = '/boards'; // 페이지를 /signup으로 이동
		}
		
		function handleUserRecipeClick() {
		    window.location.href = '/user_recipe'; // 페이지를 /signup으로 이동
		}
		
		
	