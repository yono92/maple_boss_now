document.addEventListener("DOMContentLoaded", () => {
    const searchForm = document.getElementById("search-form");
    const searchQuery = document.getElementById("search-query");
    const resultContainer = document.getElementById("result-container");
    const noResults = document.getElementById("no-results");

    const guildSearchButton = document.getElementById("guild-search");
    const guildSearchSection = document.getElementById("guild-search-section");
    const guildSearchForm = document.getElementById("guild-search-form");
    const guildName = document.getElementById("guild-name");
    const worldName = document.getElementById("world-name");

    // Toggle between profile search and guild search
    guildSearchButton.addEventListener("click", () => {
        searchForm.classList.toggle("hidden");
        guildSearchSection.classList.toggle("hidden");
    });

    searchForm.addEventListener("submit", async (event) => {
        event.preventDefault();
        const query = searchQuery.value.trim();
        if (query) {
            try {
                const response = await fetch(`/api/v1/search/character?characterName=${encodeURIComponent(query)}`);
                if (response.ok) {
                    const data = await response.json();
                    displayResults(data);
                } else {
                    displayNoResults();
                }
            } catch (error) {
                displayNoResults();
            }
        }
    });

    guildSearchForm.addEventListener("submit", async (event) => {
        event.preventDefault();
        const guild = guildName.value.trim();
        const world = worldName.value.trim();
        if (guild && world) {
            try {
                const response = await fetch(`/api/v1/guild/basic-info?guildName=${encodeURIComponent(guild)}&worldName=${encodeURIComponent(world)}`);
                if (response.ok) {
                    const data = await response.json();
                    displayGuildResults(data);
                } else {
                    displayNoResults();
                }
            } catch (error) {
                displayNoResults();
            }
        }
    });

    function displayResults(data) {
        // 기존 캐릭터 결과 표시 코드
    }

    function displayGuildResults(data) {
        resultContainer.innerHTML = `
        <div class="bg-white dark:bg-gray-800 rounded-lg shadow-md p-6">
            <div class="flex flex-col items-center justify-center mb-4">
                <img src="${data.guildMarkCustom}" alt="Guild Mark" class="w-24 h-24 rounded-full">
                <div class="text-center mt-4">
                    <h4 class="text-xl font-bold text-gray-800 dark:text-gray-100">${data.guildName}</h4>
                    <p class="text-gray-600 dark:text-gray-400">${data.worldName}</p>
                </div>
            </div>
            <div class="grid grid-cols-2 gap-4 mb-6">
                <div>
                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">Guild Master</p>
                    <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${data.guildMasterName}</p>
                </div>
                <div>
                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">Guild Level</p>
                    <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${data.guildLevel}</p>
                </div>
                <div>
                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">Guild Fame</p>
                    <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${data.guildFame}</p>
                </div>
                <div>
                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">Guild Point</p>
                    <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${data.guildPoint}</p>
                </div>
                <div>
                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">Member Count</p>
                    <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${data.guildMemberCount}</p>
                </div>
                <div>
                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">Date</p>
                    <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${data.date}</p>
                </div>
            </div>
            <div class="mt-6">
                <h4 class="text-lg font-bold text-gray-800 dark:text-gray-100">Guild Skills</h4>
                <div class="grid grid-cols-2 gap-4 mt-4">
                    ${data.guildSkill.map(skill => `
                        <div class="flex flex-col bg-gray-100 dark:bg-gray-700 p-4 rounded-md shadow-sm">
                            <div class="flex items-center">
                                <img src="${skill.skillIcon}" alt="${skill.skillName}" class="w-10 h-10 mr-2">
                                <div>
                                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">${skill.skillName}</p>
                                    <p class="text-xs text-gray-500 dark:text-gray-400">${skill.skillDescription}</p>
                                </div>
                            </div>
                            <p class="mt-2 text-sm text-gray-600 dark:text-gray-300">${skill.skillEffect}</p>
                            <p class="mt-2 text-sm text-gray-600 dark:text-gray-300">Level: ${skill.skillLevel}</p>
                        </div>
                    `).join('')}
                </div>
            </div>
            <div class="mt-6">
                <h4 class="text-lg font-bold text-gray-800 dark:text-gray-100">Guild Noblesse Skills</h4>
                <div class="grid grid-cols-2 gap-4 mt-4">
                    ${data.guildNoblesseSkill.map(skill => `
                        <div class="flex flex-col bg-gray-100 dark:bg-gray-700 p-4 rounded-md shadow-sm">
                            <div class="flex items-center">
                                <img src="${skill.skillIcon}" alt="${skill.skillName}" class="w-10 h-10 mr-2">
                                <div>
                                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">${skill.skillName}</p>
                                    <p class="text-xs text-gray-500 dark:text-gray-400">${skill.skillDescription}</p>
                                </div>
                            </div>
                            <p class="mt-2 text-sm text-gray-600 dark:text-gray-300">${skill.skillEffect}</p>
                            <p class="mt-2 text-sm text-gray-600 dark:text-gray-300">Level: ${skill.skillLevel}</p>
                        </div>
                    `).join('')}
                </div>
            </div>
        </div>
        `;

        resultContainer.classList.remove("hidden");
        noResults.classList.add("hidden");
    }

    function displayNoResults() {
        resultContainer.innerHTML = "";
        resultContainer.classList.add("hidden");
        noResults.classList.remove("hidden");
    }
});
