document.addEventListener("DOMContentLoaded", () => {
    const bossDetails = document.getElementById("boss-details");
    const matchesContainer = document.getElementById("matches");
    const createMatchForm = document.getElementById("create-match");
    const matchForm = document.getElementById("match-form");

    // URL에서 bossId를 가져옴
    const urlParams = new URLSearchParams(window.location.search);
    const bossId = urlParams.get('bossId');

    if (bossId) {
        loadBossDetails(bossId);
        loadMatches(bossId);
    }

    function loadBossDetails(bossId) {
        fetch(`/api/v1/boss/${bossId}`)
            .then(response => response.json())
            .then(boss => {
                bossDetails.classList.remove('hidden');
                createMatchForm.classList.remove('hidden');
                matchesContainer.classList.remove('hidden');

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
                matchesContainer.innerHTML = matches.map(match => `
                    <div class="match-item bg-gray-200 dark:bg-gray-700 p-4 rounded-lg shadow-md mb-4">
                        <p class="text-lg">${match.description}</p>
                        <p class="text-sm">가능 시간: ${new Date(match.matchTime).toLocaleString()}</p>
                    </div>
                `).join('');
            })
            .catch(error => console.error('Error fetching matches:', error));
    }

    // 매칭 생성 폼 제출 시 처리
    matchForm.onsubmit = (event) => {
        event.preventDefault();

        const description = document.getElementById('match-description').value;
        const matchTime = document.getElementById('match-availability').value;
        const leaderNickname = document.getElementById('leader-nickname').value;
        const leaderJob = document.getElementById('leader-job').value;
        const leaderLevel = parseInt(document.getElementById('leader-level').value, 10);

        const data = {
            bossId: bossId,
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
                    alert('매칭 요청이 성공적으로 제출되었습니다.');
                    matchForm.reset();
                    loadMatches(bossId); // 새로 생성된 매칭 정보를 로드
                } else {
                    return response.json().then(err => {
                        alert('매칭 요청 제출에 실패했습니다: ' + (err.error || 'Unknown error'));
                    });
                }
            })
            .catch(error => console.error('Error submitting match request:', error));
    };
});
