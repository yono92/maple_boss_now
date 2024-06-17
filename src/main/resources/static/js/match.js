document.addEventListener("DOMContentLoaded", () => {
    const bossDetails = document.getElementById('boss-details');
    const matchForm = document.getElementById('match-form');

    const urlParams = new URLSearchParams(window.location.search);
    const bossId = urlParams.get('bossId');

    fetch(`/api/v1/boss/${bossId}`)
        .then(response => response.json())
        .then(boss => {
            bossDetails.innerHTML = `
                <h2 class="text-2xl font-bold mb-4">${boss.name} (${boss.category})</h2>
                <p>난이도: ${boss.difficulty}</p>
                <p>${boss.description || "보스에 대한 설명이 없습니다."}</p>
            `;
        })
        .catch(error => console.error('Error fetching boss data:', error));

    matchForm.addEventListener('submit', (event) => {
        event.preventDefault();

        const description = document.getElementById('match-description').value;
        const availability = document.getElementById('match-availability').value;

        const data = {
            bossId: bossId,
            description: description,
            availability: availability
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
                } else {
                    alert('매칭 요청 제출에 실패했습니다.');
                }
            })
            .catch(error => console.error('Error submitting match request:', error));
    });
});
