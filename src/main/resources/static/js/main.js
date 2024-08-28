// ** 슬라이더
document.addEventListener('DOMContentLoaded', () => {
    let currentSlide = 0;
    const slides = document.querySelectorAll('.slide');
    const dotsContainer = document.getElementById('dots');

    // 도트 생성
    slides.forEach((_, index) => {
        const dot = document.createElement('div');
        dot.className = 'dot';
        dot.addEventListener('click', () => goToSlide(index));
        dotsContainer.appendChild(dot);
    });

    function updateDots() {
        document.querySelectorAll('.dot').forEach((dot, index) => {
            dot.classList.toggle('active', index === currentSlide);
        });
    }

    function showSlide(index) {
        const slider = document.getElementById('slider');
        slider.style.transform = `translateX(-${index * 100}%)`;
        currentSlide = index;
        updateDots();
    }

    function nextSlide() {
        showSlide((currentSlide + 1) % slides.length);
    }

    function prevSlide() {
        showSlide((currentSlide - 1 + slides.length) % slides.length);
    }

    function goToSlide(index) {
        showSlide(index);
    }

    document.querySelector('.arrow-button.right').addEventListener('click', nextSlide);
    document.querySelector('.arrow-button.left').addEventListener('click', prevSlide);

    // 초기 도트 업데이트
    updateDots();
});



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




// ** 텍스트영역

// Join 버튼 클릭 핸들러
        function handleJoinClick() {
            window.location.href = '/signup'; // 페이지를 /signup으로 이동
        }