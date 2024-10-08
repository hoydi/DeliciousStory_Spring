document.addEventListener("DOMContentLoaded", function () {
    const menuToggle = document.querySelector("#toggleButton");
    const mobileMenu = document.querySelector("#mobileMenu");

    menuToggle.addEventListener("click", function (event) {
        event.stopPropagation(); // 클릭 이벤트 전파 방지
        console.log('Menu toggle button clicked');
        mobileMenu.classList.toggle("show");
    });
    mobileMenu.addEventListener("click", function (event) {
        if (mobileMenu.classList.contains("show")) {
            mobileMenu.classList.remove("show");
        }
        event.stopPropagation(); // 클릭 이벤트 전파 방지
    });

    // 실시간 검색 관련 코드
    let allRecipes = [];

    // 모든 레시피를 서버에서 한 번만 불러오는 함수
    function loadAllRecipes() {
        if (allRecipes.length === 0) {
            fetch('/search')
                .then(response => response.json())
                .then(data => {
                    allRecipes = data; // 모든 레시피 데이터를 저장
                });
        }
    }

    // 입력값에 따라 레시피를 필터링하는 함수
    function filterRecipes() {
        const keyword = document.getElementById('h-search-input').value.toLowerCase();
        const resultsDropdown = document.getElementById('h-search-results');
        resultsDropdown.innerHTML = '';

        // 키워드와 일치하는 레시피 필터링
        const filteredRecipes = allRecipes.filter(recipe =>
            recipe.name.toLowerCase().includes(keyword)
        );

        // 필터링된 레시피를 드롭다운에 표시
        if (filteredRecipes.length > 0) {
            resultsDropdown.style.display = 'block'; // 드롭다운 표시
        } else {
            resultsDropdown.style.display = 'none'; // 드롭다운 숨김
        }

        filteredRecipes.forEach(recipe => {
            const li = document.createElement('li');
            li.textContent = recipe.name;
            li.classList.add('dropdown-item'); // 클래스 추가
            li.onclick = () => window.location.href = `/siteRecipe/${recipe.id}`; // 클릭 시 레시피 상세 페이지로 이동
            resultsDropdown.appendChild(li);
        });
    }

    // 검색창이 포커스될 때 모든 레시피 불러오기
    document.getElementById('h-search-input').addEventListener('focus', loadAllRecipes);

    // 입력할 때마다 필터링 함수 호출
    document.getElementById('h-search-input').addEventListener('input', filterRecipes);

    // 검색창 포커스가 벗어날 때 드롭다운 숨기기
    document.getElementById('h-search-input').addEventListener('blur', function () {
        setTimeout(() => {
            document.getElementById('h-search-results').style.display = 'none';
        }, 200); // 짧은 지연 시간 후 드롭다운 숨김
    });
});
