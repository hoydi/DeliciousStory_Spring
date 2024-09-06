document.addEventListener("DOMContentLoaded", function () {
    // 메뉴 토글 코드
    const menuToggle = document.querySelector("#toggleButton");
    const mobileMenu = document.querySelector("#mobileMenu");
    const searchBar = document.querySelector("#searchBar");
    const searchOverlay = document.createElement('div');  // 검색 오버레이 요소 생성
    const searchToggleButton = document.querySelector("#searchToggleButton");

    // 검색 오버레이 스타일
    searchOverlay.style.position = 'fixed';
    searchOverlay.style.top = 0;
    searchOverlay.style.left = 0;
    searchOverlay.style.width = '100%';
    searchOverlay.style.height = '100%';
    searchOverlay.style.backgroundColor = 'rgba(255, 255, 255, 0.8)';
    searchOverlay.style.display = 'none';  // 초기에는 숨김
    searchOverlay.style.zIndex = 1000;
    document.body.appendChild(searchOverlay);  // 오버레이를 body에 추가

    // 메뉴 토글 이벤트
    menuToggle.addEventListener("click", function (event) {
        event.stopPropagation(); // 클릭 이벤트 전파 방지
        mobileMenu.classList.toggle("show");

        // 메뉴가 열리면 검색 창과 오버레이 닫기
        if (mobileMenu.classList.contains("show")) {
            searchBar.classList.remove("show");
            searchOverlay.style.display = 'none';
        }
    });

    document.addEventListener("click", function (event) {
        if (!menuToggle.contains(event.target) && !mobileMenu.contains(event.target)) {
            mobileMenu.classList.remove("show");
        }
    });

    // 검색창 토글 이벤트
    searchToggleButton.addEventListener("click", function () {
        searchBar.classList.toggle("show");
        searchOverlay.style.display = searchBar.classList.contains("show") ? 'block' : 'none';

        // 검색창이 열리면 메뉴 닫기
        if (searchBar.classList.contains("show")) {
            mobileMenu.classList.remove("show");
        }
    });

    // 오버레이 클릭 시 검색창 닫기
    searchOverlay.addEventListener("click", function () {
        searchBar.classList.remove("show");
        searchOverlay.style.display = 'none';
    });

    // 모바일 화면일 경우 기존 검색바 숨김 처리
    function checkScreenWidth() {
        if (window.innerWidth <= 826) {
            searchBar.classList.remove("show");  // 작은 화면에서는 검색창 숨김
        } else {
			            searchBar.classList.add("show");  // 큰 화면에서는 검색창 보임
			        }
			    }

			    window.addEventListener('resize', checkScreenWidth);
			    checkScreenWidth();  // 초기 로드 시 화면 크기 확인

			    // 레시피 관련 기능
			    let allRecipes = [];

			    function loadAllRecipes() {
			        if (allRecipes.length === 0) {
			            fetch('/search')
			                .then(response => response.json())
			                .then(data => {
			                    allRecipes = data; // 모든 레시피 데이터를 저장
			                });
			        }
			    }

				function filterRecipes() {
				    const keyword = document.getElementById('search-input').value.toLowerCase();
				    const resultsDropdown = document.getElementById('search-results');
				    resultsDropdown.innerHTML = '';

				    if (keyword) {
				        resultsDropdown.style.display = 'block';  // 검색어 입력 시 dropdown 표시
				    } else {
				        resultsDropdown.style.display = 'none';   // 검색어 없으면 dropdown 숨김
				        return;
				    }

				    const filteredRecipes = allRecipes.filter(recipe =>
				        recipe.name.toLowerCase().includes(keyword)
				    );

				    filteredRecipes.forEach(recipe => {
				        const li = document.createElement('li');
				        li.textContent = recipe.name;
				        li.onclick = () => window.location.href = `/siteRecipe/${recipe.id}`;

				        // 마우스 오버 시 색상 변경을 위해 클래스 추가
				        li.classList.add('dropdown-item');

				        resultsDropdown.appendChild(li);
				    });
				}
				
			    document.getElementById('search-input').addEventListener('focus', loadAllRecipes);
			    document.getElementById('search-input').addEventListener('input', filterRecipes);
			});

