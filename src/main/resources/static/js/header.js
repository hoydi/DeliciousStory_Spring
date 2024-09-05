document.addEventListener("DOMContentLoaded", function () {
    // 메뉴 토글 코드
    const menuToggle = document.querySelector("#toggleButton");
    const mobileMenu = document.querySelector("#mobileMenu");

    menuToggle.addEventListener("click", function (event) {
        event.stopPropagation(); // 클릭 이벤트 전파 방지
        mobileMenu.classList.toggle("show");
    });

    document.addEventListener("click", function (event) {
        if (!menuToggle.contains(event.target) && !mobileMenu.contains(event.target)) {
            mobileMenu.classList.remove("show");
        }
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
        const keyword = document.getElementById('search-input').value.toLowerCase();
        const resultsDropdown = document.getElementById('search-results');
        resultsDropdown.innerHTML = '';

        // 키워드와 일치하는 레시피 필터링
        const filteredRecipes = allRecipes.filter(recipe =>
            recipe.name.toLowerCase().includes(keyword)
        );

        // 필터링된 레시피를 드롭다운에 표시
        filteredRecipes.forEach(recipe => {
            const li = document.createElement('li');
            li.textContent = recipe.name;
            li.onclick = () => window.location.href = `/siteRecipe/${recipe.id}`; // 클릭 시 레시피 상세 페이지로 이동
            resultsDropdown.appendChild(li);
        });
    }

    // 검색창이 포커스될 때 모든 레시피 불러오기
    document.getElementById('search-input').addEventListener('focus', loadAllRecipes);

    // 입력할 때마다 필터링 함수 호출
    document.getElementById('search-input').addEventListener('input', filterRecipes);
});
