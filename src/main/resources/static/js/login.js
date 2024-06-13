document.getElementById('login-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const email = document.getElementById('login-email').value;
    const password = document.getElementById('login-password').value;

    const data = {
        email: email,
        password: password
    };

    fetch('/api/v1/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json().then(data => ({ status: response.status, body: data })))
        .then(result => {
            if (result.status === 200) {
                showNotification('Login successful!', 'success');
                localStorage.setItem('jwtToken', result.body.token); // JWT 토큰을 로컬 스토리지에 저장
                setTimeout(() => {
                    window.location.href = '/'; // 로그인 성공 후 홈 페이지로 리다이렉트
                }, 2000); // 2초 후 리다이렉트
            } else {
                showNotification(result.body || 'Login failed', 'error');
            }
        })
        .catch(error => {
            console.error('Error during login:', error);
            showNotification('An error occurred during login', 'error');
        });
});

function showNotification(message, type) {
    const container = document.getElementById('notification-container');
    const notification = document.createElement('div');
    notification.className = `p-4 mb-4 text-sm rounded-lg shadow-md ${type === 'success' ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}`;
    notification.textContent = message;

    container.appendChild(notification);

    setTimeout(() => {
        container.removeChild(notification);
    }, 3000); // 3초 후 자동으로 사라짐
}
