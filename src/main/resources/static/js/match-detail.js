document.addEventListener("DOMContentLoaded", () => {
    const matchDetails = document.getElementById("match-details");
    const joinMatchForm = document.getElementById("join-match-form");

    // URL에서 matchId를 가져옴
    const urlParams = new URLSearchParams(window.location.search);
    const matchId = urlParams.get('matchId');

    if (matchId) {
        loadMatchDetails(matchId);
    }

    function loadMatchDetails(matchId) { // 함수 이름 수정
        fetch(`/api/v1/matches/${matchId}`)
            .then(response => response.json())
            .then(match => {
                matchDetails.innerHTML = `
                    <h2>${match.description}</h2>
                    <p>매칭 시간: ${new Date(match.matchTime).toLocaleString()}</p>
                    <p>파티장: ${match.leader.nickname} (${match.leader.job}, ${match.leader.level})</p>
                    <p>파티원:</p>
                    <ul>
                        ${match.members.map(member => `<li>${member.nickname} (${member.job}, ${member.level})</li>`).join('')}
                    </ul>
                `;
            })
            .catch(error => console.error('Error fetching match details:', error));
    }

    // 매칭 참여 폼 제출 시 처리
    joinMatchForm.onsubmit = (event) => {
        event.preventDefault();

        const nickname = document.getElementById('member-nickname').value;
        const job = document.getElementById('member-job').value;
        const level = parseInt(document.getElementById('member-level').value, 10);

        const data = {
            matchId: matchId,
            nickname: nickname,
            job: job,
            level: level
        };

        fetch(`/api/v1/matches/${matchId}/join`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.ok) {
                    alert('매칭에 성공적으로 참여했습니다.');
                    loadMatchDetails(matchId); // 파티원 정보 갱신
                } else {
                    return response.json().then(err => {
                        alert('매칭 참여에 실패했습니다: ' + (err.error || 'Unknown error'));
                    });
                }
            })
            .catch(error => console.error('Error joining match:', error));
    };
});
