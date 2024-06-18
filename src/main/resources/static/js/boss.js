document.addEventListener("DOMContentLoaded", () => {
    const toggleWeekly = document.getElementById("toggle-weekly");
    const toggleMonthly = document.getElementById("toggle-monthly");
    const weeklyBossList = document.getElementById("weekly-bosses");
    const monthlyBossList = document.getElementById("monthly-bosses");
    const bossDetails = document.getElementById("boss-details");
    const matchesContainer = document.getElementById("matches");
    const createMatchForm = document.getElementById("create-match");

    toggleWeekly.addEventListener("click", () => toggleList(weeklyBossList, toggleWeekly));
    toggleMonthly.addEventListener("click", () => toggleList(monthlyBossList, toggleMonthly));

    function toggleList(list, toggleButton) {
        const arrowIcon = toggleButton.querySelector("svg");
        const isHidden = list.classList.toggle("hidden");
        arrowIcon.classList.toggle("rotate-180", !isHidden);
    }

    function loadBosses(category) {
        fetch('/api/v1/bosses')
            .then(response => response.json())
            .then(bosses => {
                const bossList = category === '주간보스' ? weeklyBossList : monthlyBossList;
                bossList.innerHTML = ''; // 기존 내용 제거
                const groupedBosses = groupBossesByName(bosses.filter(boss => boss.category === category));
                renderBossItems(groupedBosses, bossList);
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

    function renderBossItems(groupedBosses, bossList) {
        Object.keys(groupedBosses).forEach(bossName => {
            const difficulties = groupedBosses[bossName];
            const listItem = document.createElement('li');
            listItem.classList.add('mb-2');

            const difficultiesHtml = difficulties.map(difficulty => `
                <li class="pl-4 hover:underline cursor-pointer" onclick="showBossDetails(${difficulty.id})">
                    ${difficulty.difficulty}
                </li>
            `).join('');

            listItem.innerHTML = `
                <div class="flex justify-between items-center cursor-pointer" onclick="toggleSubList('${bossName}')">
                    <span>${bossName}</span>
                    <svg class="h-4 w-4 ml-2 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M19 9l-7 7-7-7" />
                    </svg>
                </div>
                <ul id="${bossName}-difficulties" class="hidden mt-1">
                    ${difficultiesHtml}
                </ul>
            `;
            bossList.appendChild(listItem);
        });
    }

    window.toggleSubList = (bossName) => {
        const subList = document.getElementById(`${bossName}-difficulties`);
        const arrowIcon = subList.previousElementSibling.querySelector("svg");
        const isHidden = subList.classList.toggle("hidden");
        arrowIcon.classList.toggle("rotate-180", !isHidden);
    };

    window.showBossDetails = (bossId) => {
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

                loadMatches(bossId);
            })
            .catch(error => console.error('Error fetching boss data:', error));
    };

    function loadMatches(bossId) {
        fetch(`/api/v1/matches?bossId=${bossId}`)
            .then(response => response.json())
            .then(matches => {
                matchesContainer.innerHTML = matches.map(match => `
                    <div class="match-item bg-gray-200 dark:bg-gray-700 p-4 rounded-lg shadow-md mb-4">
                        <p class="text-lg">${match.description}</p>
                        <p class="text-sm">가능 시간: ${new Date(match.availability).toLocaleString()}</p>
                    </div>
                `).join('');
            })
            .catch(error => console.error('Error fetching matches:', error));
    }

    // 초기 로드: 주간 보스 로드
    loadBosses('주간보스');
});
