document.addEventListener("DOMContentLoaded", () => {
    const toggleDaily = document.getElementById("toggle-daily");
    const toggleWeekly = document.getElementById("toggle-weekly");
    const toggleMonthly = document.getElementById("toggle-monthly");
    const dailyBossList = document.getElementById("daily-bosses");
    const weeklyBossList = document.getElementById("weekly-bosses");
    const monthlyBossList = document.getElementById("monthly-bosses");

    toggleDaily.addEventListener("click", () => toggleList(dailyBossList, toggleDaily));
    toggleWeekly.addEventListener("click", () => toggleList(weeklyBossList, toggleWeekly));
    toggleMonthly.addEventListener("click", () => toggleList(monthlyBossList, toggleMonthly));

    fetch('/api/v1/bosses')
        .then(response => response.json())
        .then(bosses => {
            const groupedBosses = bosses.reduce((acc, boss) => {
                if (!acc[boss.category]) acc[boss.category] = {};
                if (!acc[boss.category][boss.name]) acc[boss.category][boss.name] = [];
                acc[boss.category][boss.name].push(boss);
                return acc;
            }, {});

            renderBosses(groupedBosses, "일일 보스", dailyBossList);
            renderBosses(groupedBosses, "주간 보스", weeklyBossList);
            renderBosses(groupedBosses, "월간 보스", monthlyBossList);
        })
        .catch(error => console.error('Error fetching boss data:', error));
});

function toggleList(list, toggle) {
    const arrowIcon = toggle.querySelector("svg");
    const isHidden = list.classList.toggle("hidden");
    arrowIcon.classList.toggle("rotate-180", !isHidden);
}

function renderBosses(groupedBosses, category, list) {
    if (groupedBosses[category]) {
        Object.keys(groupedBosses[category]).forEach(bossName => {
            const listItem = document.createElement('li');
            listItem.innerHTML = `
                <h4 class="text-lg font-bold text-gray-800 dark:text-gray-100 flex items-center cursor-pointer" onclick="toggleSubList('${category}', '${bossName}')">
                    ${bossName}
                    <svg class="h-6 w-6 ml-2 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M19 9l-7 7-7-7" />
                    </svg>
                </h4>
                <ul id="${category}-${bossName}" class="hidden pl-4 space-y-1">
                    ${groupedBosses[category][bossName].map(boss => `
                        <li class="cursor-pointer hover:underline" onclick="showBossContent(${JSON.stringify(boss)})">
                            ${boss.difficulty}
                        </li>
                    `).join('')}
                </ul>
            `;
            list.appendChild(listItem);
        });
    }
}

function toggleSubList(category, bossName) {
    const subList = document.getElementById(`${category}-${bossName}`);
    const arrowIcon = subList.previousElementSibling.querySelector("svg");
    const isHidden = subList.classList.toggle("hidden");
    arrowIcon.classList.toggle("rotate-180", !isHidden);
}

function showBossContent(boss) {
    const bossContent = document.getElementById('boss-content');
    bossContent.innerHTML = `
        <h2 class="text-2xl font-bold mb-4">${boss.name} (${boss.category})</h2>
        <p>난이도: ${boss.difficulty}</p>
        <p>${boss.description}</p>
        <!-- 난이도별 게시판 목록을 여기에 추가 -->
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
}
