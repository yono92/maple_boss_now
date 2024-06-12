// URL에서 토큰을 추출하는 함수
function getTokenFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('code');
}

// 토큰을 저장하는 함수 (로컬 스토리지 예시)
function saveToken(token) {
    localStorage.setItem('jwtToken', token);
}

// 로그인 상태를 확인하는 함수
function isLoggedIn() {
    return !!localStorage.getItem('jwtToken');
}

// 메인 페이지가 로드될 때 code를 처리
window.onload = function() {
    const token = getTokenFromUrl();
    if (token) {
        saveToken(token);
        // URL에서 code를 제거하기 위해 페이지 리로드
        window.history.replaceState({}, document.title, "/");
    } else if (!isLoggedIn() && window.location.pathname === "/profile") {
        // 로그인이 필요한 페이지에 접근하려고 할 때 로그인 페이지로 리다이렉트
        window.location.href = "/login";
    }
};