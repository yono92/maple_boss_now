document.addEventListener("DOMContentLoaded", () => {
    const toggleWeekly = document.getElementById("toggle-weekly");
    const toggleMonthly = document.getElementById("toggle-monthly");
    const bossList = document.getElementById("boss-list");

    toggleWeekly.addEventListener("click", () => loadBosses('주간보스'));
    toggleMonthly.addEventListener("click", () => loadBosses('월간보스'));

    function loadBosses(category) {
        fetch('/api/v1/bosses')
            .then(response => response.json())
            .then(bosses => {
                bossList.innerHTML = ''; // 기존 내용 제거
                const groupedBosses = groupBossesByName(bosses.filter(boss => boss.category === category));
                renderBossCards(groupedBosses);
            })
            .catch(error => console.error('Error fetching boss data:', error));
    }

    function groupBossesByName(bosses) {
        return bosses.reduce((acc, boss) => {
            if (!acc[boss.name]) acc[boss.name] = [];
            acc[boss.name].push(boss);
            return acc;
        }, {});
    }

    function renderBossCards(groupedBosses) {
        Object.keys(groupedBosses).forEach(bossName => {
            const difficulties = groupedBosses[bossName];
            const listItem = document.createElement('div');
            listItem.classList.add('boss-card', 'bg-gray-800', 'text-white', 'p-4', 'rounded-lg', 'shadow-md', 'flex', 'flex-col', 'justify-between', 'mb-4');

            const difficultiesHtml = difficulties.length > 1
                ? `<select id="${bossName}-difficulty" class="mt-2 p-1 border border-gray-600 rounded bg-gray-900 text-white">
                        ${difficulties.map((difficulty, index) => `
                            <option value="${difficulty.id}" ${index === 0 ? 'selected' : ''}>${difficulty.difficulty}</option>`).join('')}
                   </select>`
                : `<p class="text-sm mt-2">${difficulties[0].difficulty}</p>`;

            listItem.innerHTML = `
                <div class="flex justify-between items-center">
                    <h3 class="text-lg font-semibold">${bossName}</h3>
                </div>
                <div>
                    ${difficultiesHtml}
                </div>
                <button class="mt-4 bg-blue-500 text-white p-2 rounded" onclick="goToMatchPage('${bossName}', ${difficulties.length > 1})">
                    매칭 게시판
                </button>
            `;
            bossList.appendChild(listItem);
        });
    }

    window.goToMatchPage = (bossName, hasMultipleDifficulties) => {
        const selectedDifficulty = hasMultipleDifficulties ? document.getElementById(`${bossName}-difficulty`).value : null;
        const url = selectedDifficulty ? `/match.html?bossId=${selectedDifficulty}` : `/match.html?name=${bossName}`;
        window.location.href = url;
    };

    // 초기 로드: 주간 보스 로드
    loadBosses('주간보스');
});
