document.getElementById('signup-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const username = document.getElementById('signup-username').value;
    const phoneNumber = document.getElementById('signup-phone').value;
    const email = document.getElementById('signup-email').value;
    const password = document.getElementById('signup-password').value;
    const passwordConfirm = document.getElementById('signup-password-confirm').value;

    if (password !== passwordConfirm) {
        showNotification('Passwords do not match', 'error');
        return;
    }

    const data = {
        username: username,
        phoneNumber: phoneNumber,
        email: email,
        password: password
    };

    fetch('/api/v1/signup', {  // 엔드포인트를 /api/v1/signup으로 설정
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                return response.text(); // 응답을 텍스트로 파싱
            } else {
                return response.json().then(data => {
                    throw new Error(data.error || 'Signup failed');
                });
            }
        })
        .then(message => {
            showNotification(message, 'success');
            setTimeout(() => {
                window.location.href = '/login';
            }, 2000); // 2초 후 로그인 페이지로 리다이렉트
        })
        .catch(error => {
            console.error('Error during signup:', error);
            showNotification(error.message || 'An error occurred during signup', 'error');
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
