// URL에서 JWT 토큰을 추출하는 함수
function getTokenFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('token');
}

// 서버에서 JWT 토큰을 확인하는 함수
async function isTokenValid() {
    const token = localStorage.getItem('jwtToken');
    if (!token) return false;

    try {
        const response = await fetch('/api/v1/token/validate', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });
        return response.ok;
    } catch (error) {
        console.error('Error validating token with server:', error);
        return false;
    }
}

// 메인 페이지가 로드될 때 JWT 토큰을 처리
window.onload = async function() {
    const token = getTokenFromUrl();
    if (token) {
        localStorage.setItem('jwtToken', token); // 저장 후 로컬 스토리지에 저장
        // URL에서 토큰을 제거하기 위해 페이지 리로드
        window.history.replaceState({}, document.title, window.location.pathname);
    } else if (!(await isTokenValid()) && window.location.pathname === "/profile") {
        // 로그인이 필요한 페이지에 접근하려고 할 때 로그인 페이지로 리다이렉트
        window.location.href = "/login";
    }
};
