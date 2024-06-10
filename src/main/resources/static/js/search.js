document.addEventListener("DOMContentLoaded", () => {
    const searchForm = document.getElementById("search-form");
    const searchQuery = document.getElementById("search-query");
    const resultContainer = document.getElementById("result-container");
    const noResults = document.getElementById("no-results");

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
                console.error("Fetch error:", error);
                displayNoResults();
            }
        }
    });

    function displayResults(data) {
        const basicInfo = data.characterBasicInfo;
        const statInfo = data.characterStatInfo;
        const finalStat = statInfo?.finalStat || [];
        resultContainer.innerHTML = `
        <div class="bg-white dark:bg-gray-800 rounded-lg shadow-md p-6">
            <div class="flex items-center justify-center mb-4">
                <img src="${basicInfo.character_image}" alt="Character Image" class="w-24 h-24 rounded-full">
            </div>
            <div class="text-center mb-6">
                <h4 class="text-xl font-bold text-gray-800 dark:text-gray-100">${basicInfo.character_name}</h4>
                <p class="text-gray-600 dark:text-gray-400">${basicInfo.world_name}</p>
            </div>
            <div class="grid grid-cols-2 gap-4 mb-6">
                <div>
                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">Gender</p>
                    <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${basicInfo.character_gender}</p>
                </div>
                <div>
                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">Class</p>
                    <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${basicInfo.character_class}</p>
                </div>
                <div>
                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">Class Level</p>
                    <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${basicInfo.character_class_level}</p>
                </div>
                <div>
                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">Level</p>
                    <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${basicInfo.character_level}</p>
                </div>
                <div>
                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">EXP</p>
                    <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${basicInfo.character_exp}</p>
                </div>
                <div>
                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">EXP Rate</p>
                    <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${basicInfo.character_exp_rate}</p>
                </div>
                <div class="col-span-2">
                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">Guild</p>
                    <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${basicInfo.character_guild_name}</p>
                </div>
            </div>
                <div class="mt-6">
                <h4 class="text-lg font-bold text-gray-800 dark:text-gray-100">Character Stats</h4>
                    <div class="grid grid-cols-2 gap-4 mt-4">
                        ${finalStat.map(stat => `
                            <div>
                                <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">${stat.stat_name}</p>
                                <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${stat.stat_value}</p>
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
