// URL에서 JWT 토큰을 추출하는 함수
function getTokenFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('token');
}

// 서버에 JWT 토큰을 저장하고 사용자 이름을 로컬 스토리지에 저장하는 함수
async function saveTokenToServer(token) {
    try {
        const response = await fetch('/api/v1/token/validate', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });
        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('jwtToken', token);
            localStorage.setItem('username', data.username); // 사용자 이름 저장
            return true;
        } else {
            console.error('Failed to validate token with server');
            return false;
        }
    } catch (error) {
        console.error('Error validating token with server:', error);
        return false;
    }
}

// 로그인 상태 확인 및 반영 함수
async function checkLoginStatus() {
    const token = localStorage.getItem('jwtToken');
    if (token) {
        const valid = await saveTokenToServer(token);
        if (valid) {
            const username = localStorage.getItem('username');
            document.getElementById('user-greeting').textContent = `Hello, ${username}`;
            document.getElementById('user-greeting').classList.remove('hidden');
            document.getElementById('auth-link').textContent = 'Logout';
            document.getElementById('auth-link').onclick = () => {
                localStorage.removeItem('jwtToken');
                localStorage.removeItem('username');
                window.location.href = '/login';
            };
        } else {
            localStorage.removeItem('jwtToken');
            localStorage.removeItem('username');
            window.location.href = '/login';
        }
    }
}

window.onload = async function() {
    const token = getTokenFromUrl();
    if (token) {
        const saved = await saveTokenToServer(token);
        if (saved) {
            window.history.replaceState({}, document.title, "/");
        }
    }
    await checkLoginStatus();
};
