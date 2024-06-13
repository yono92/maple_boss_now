// URL에서 JWT 토큰을 추출하는 함수
function getTokenFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('token');
}

// 서버에 JWT 토큰을 저장하는 함수
async function saveTokenToServer(token) {
    try {
        const response = await fetch('/api/token', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ token: token })
        });
        if (response.ok) {
            return true;
        } else {
            console.error('Failed to save token to server');
            return false;
        }
    } catch (error) {
        console.error('Error saving token to server:', error);
        return false;
    }
}

// 서버에서 JWT 토큰을 확인하는 함수
async function isTokenValid() {
    try {
        const response = await fetch('/api/token/validate', {
            method: 'GET',
            headers: {
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
        const saved = await saveTokenToServer(token);
        if (saved) {
            // URL에서 토큰을 제거하기 위해 페이지 리로드
            window.history.replaceState({}, document.title, "/");
        }
    } else if (!(await isTokenValid()) && window.location.pathname === "/profile") {
        // 로그인이 필요한 페이지에 접근하려고 할 때 로그인 페이지로 리다이렉트
        window.location.href = "/login";
    }
};
