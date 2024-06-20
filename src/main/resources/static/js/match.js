document.addEventListener("DOMContentLoaded", () => {
    const bossDetails = document.getElementById("boss-details");
    const matchesContainer = document.getElementById("matches");
    const createMatchButton = document.getElementById("create-match-button");
    const createMatchModal = document.getElementById("create-match-modal");
    const closeModal = document.getElementById("close-modal");
    const matchForm = document.getElementById("match-form");
    let currentBossId = null;

    // URL에서 bossId를 가져옴
    const urlParams = new URLSearchParams(window.location.search);
    const bossId = urlParams.get('bossId');

    if (bossId) {
        currentBossId = bossId;
        loadBossDetails(bossId);
        loadMatches(bossId);
    }

    function loadBossDetails(bossId) {
        fetch(`/api/v1/boss/${bossId}`)
            .then(response => response.json())
            .then(boss => {
                bossDetails.classList.remove('hidden');
                matchesContainer.classList.remove('hidden');
                createMatchButton.classList.remove('hidden');

                bossDetails.innerHTML = `
                    <h2 class="text-2xl font-bold mb-4">${boss.name} (${boss.difficulty})</h2>
                    <p>카테고리: ${boss.category}</p>
                    <p>${boss.description || "보스에 대한 설명이 없습니다."}</p>
                `;
            })
            .catch(error => console.error('Error fetching boss data:', error));
    }

    function loadMatches(bossId) {
        fetch(`/api/v1/matches?bossId=${bossId}`)
            .then(response => response.json())
            .then(matches => {
                if (matches.length === 0) {
                    matchesContainer.innerHTML = '<p class="text-gray-500 dark:text-gray-400 mt-2">매칭 정보가 없습니다.</p>';
                } else {
                    matchesContainer.innerHTML = matches.map(match => `
                        <div class="match-item bg-gray-200 dark:bg-gray-700 p-4 rounded-lg shadow-md mb-4">
                            <p class="text-lg">${match.description}</p>
                            <p class="text-sm">가능 시간: ${new Date(match.matchTime).toLocaleString()}</p>
                        </div>
                    `).join('');
                }
            })
            .catch(error => console.error('Error fetching matches:', error));
    }

    // 모달 열기
    createMatchButton.addEventListener("click", () => {
        createMatchModal.classList.remove("hidden");
    });

    // 모달 닫기
    closeModal.addEventListener("click", () => {
        createMatchModal.classList.add("hidden");
    });

    // 모달 외부 클릭 시 닫기
    window.addEventListener("click", (event) => {
        if (event.target === createMatchModal) {
            createMatchModal.classList.add("hidden");
        }
    });

    // 매칭 생성 폼 제출 시 처리
    matchForm.onsubmit = (event) => {
        event.preventDefault();

        const description = document.getElementById('match-description').value;
        const matchTime = document.getElementById('match-availability').value;
        const leaderNickname = document.getElementById('leader-nickname').value;
        const leaderJob = document.getElementById('leader-job').value;
        const leaderLevel = parseInt(document.getElementById('leader-level').value, 10);

        const data = {
            bossId: currentBossId,
            description: description,
            matchTime: matchTime,
            leaderNickname: leaderNickname,
            leaderJob: leaderJob,
            leaderLevel: leaderLevel,
            members: [] // 빈 배열로 설정
        };

        fetch('/api/v1/matches', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.ok) {
                    showNotification('매칭 요청이 성공적으로 제출되었습니다.');
                    matchForm.reset();
                    loadMatches(currentBossId); // 새로 생성된 매칭 정보를 로드
                    createMatchModal.classList.add("hidden"); // 모달 닫기
                } else {
                    return response.json().then(err => {
                        showNotification('매칭 요청 제출에 실패했습니다: ' + (err.error || 'Unknown error'));
                    });
                }
            })
            .catch(error => console.error('Error submitting match request:', error));

    };

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

});

