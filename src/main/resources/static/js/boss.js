document.addEventListener("DOMContentLoaded", () => {
    const toggleWeekly = document.getElementById("toggle-weekly");
    const toggleMonthly = document.getElementById("toggle-monthly");
    const bossList = document.getElementById("boss-list");
    const bossContent = document.getElementById("boss-content");

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
            const bosses = groupedBosses[bossName];
            const bossCard = document.createElement('div');
            bossCard.className = 'bg-white dark:bg-gray-800 p-4 rounded-lg shadow-md mb-4';
            bossCard.innerHTML = `
                <h3 class="text-lg font-bold text-gray-800 dark:text-gray-100 mb-2">${bossName}</h3>
                ${bosses.length > 1 ? `
                    <label for="${bossName}-difficulty" class="block mb-1">난이도 선택:</label>
                    <select id="${bossName}-difficulty" class="bg-white dark:bg-gray-700 text-gray-800 dark:text-gray-100 p-2 rounded-md w-full">
                        ${bosses.map(boss => `<option value="${boss.id}">${boss.difficulty}</option>`).join('')}
                    </select>
                ` : `
                    <p>난이도: ${bosses[0].difficulty}</p>
                `}
                <button class="bg-blue-600 text-white py-1 px-2 rounded hover:bg-blue-500" onclick='showBossContent("${bossName}", ${bosses.length > 1})'>자세히 보기</button>
            `;
            bossList.appendChild(bossCard);
        });
    }

    window.showBossContent = (bossName, hasMultipleDifficulties) => {
        const selectedDifficulty = hasMultipleDifficulties ? document.getElementById(`${bossName}-difficulty`).value : null;
        const url = selectedDifficulty ? `/api/v1/boss/${selectedDifficulty}` : `/api/v1/bosses?name=${bossName}`;
        fetch(url)
            .then(response => response.json())
            .then(boss => {
                bossContent.classList.remove('hidden');
                bossContent.innerHTML = `
                    <h2 class="text-2xl font-bold mb-4">${boss.name} (${boss.category})</h2>
                    <p>난이도: ${boss.difficulty}</p>
                    <p>${boss.description || "보스에 대한 설명이 없습니다."}</p>
                    <div>
                        <h3 class="text-xl font-semibold mt-6">게시판</h3>
                        <ul>
                            ${boss.boards.map(board => `
                                <li>
                                    <a href="/boss/board/${board.id}" class="text-blue-600 hover:underline">${board.title}</a>
                                    <p>${board.content}</p>
                                    <small>작성자: ${board.author}</small>
                                </li>
                            `).join('')}
                        </ul>
                    </div>
                `;
            })
            .catch(error => console.error('Error fetching boss content:', error));
    }
});
