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
        const finalStat = statInfo?.final_stat || [];
        const equipment = data.characterEquipInfo?.item_equipment || [];

        resultContainer.innerHTML = `
        <div class="bg-white dark:bg-gray-800 rounded-lg shadow-md p-6">
            <div class="flex flex-col items-center justify-center mb-4">
                <img src="${basicInfo.character_image}" alt="Character Image" class="w-24 h-24 rounded-full">
                <div class="text-center mt-4">
                    <h4 class="text-xl font-bold text-gray-800 dark:text-gray-100">${basicInfo.character_name}</h4>
                    <p class="text-gray-600 dark:text-gray-400">${basicInfo.world_name}</p>
                </div>
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
                <h4 class="text-lg font-bold text-gray-800 dark:text-gray-100 flex items-center cursor-pointer" id="toggle-stats">
                    Character Stats
                    <svg id="arrow-icon" xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 ml-2 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M19 9l-7 7-7-7" />
                    </svg>
                </h4>
                <div class="grid grid-cols-2 gap-4 mt-4 hidden" id="stats-container">
                    ${finalStat.map(stat => `
                        <div class="flex flex-col bg-gray-100 dark:bg-gray-700 p-4 rounded-md shadow-sm">
                            <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">${stat.stat_name}</p>
                            <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${stat.stat_value}</p>
                        </div>
                    `).join('')}
                </div>
                <div class="mt-6">
                    <h4 class="text-lg font-bold text-gray-800 dark:text-gray-100 flex items-center cursor-pointer" id="toggle-equipment">
                        Character Equipment
                        <svg id="equipment-arrow-icon" xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 ml-2 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M19 9l-7 7-7-7" />
                        </svg>
                    </h4>
                    <!-- 장비 아이템을 표시하는 섹션 -->
                    <div class="grid grid-cols-4 gap-4 mt-4 hidden" id="equipment-container">
                        ${equipment.map(item => `
                            <div class="relative group text-center">
                                <!-- 아이템 이미지 -->
                                <img src="${item.item_icon}" alt="${item.item_name}" class="w-16 h-16 rounded mx-auto">
                                <!-- 아이템 기본 정보 -->
                                <div class="mt-1">
                                    <p class="text-sm font-bold text-gray-800 dark:text-gray-100">${item.item_name}</p>
                                    <p class="text-xs text-gray-600 dark:text-gray-400 flex justify-center items-center">
                                        <i class="fas fa-star text-yellow-500"></i> ☆ × ${item.starforce || 'N/A'}
                                    </p>
                                </div>
                                <!-- 팝업 정보 -->
                                <div class="absolute inset-0 bg-black bg-opacity-70 text-white text-sm p-2 rounded hidden group-hover:block z-10 overflow-auto" style="max-height: 200px;">
                                    <p class="font-bold">${item.item_name}</p>
                                    <p>Starforce: ${item.starforce || 'N/A'}</p>
                                    <p>Part: ${item.item_equipment_part}</p>
                                    <p>Slot: ${item.item_equipment_slot}</p>
                                    ${item.item_description ? `<p>Description: ${item.item_description}</p>` : ''}
                                    ${item.item_total_option.str ? `<p>STR: ${item.item_total_option.str}</p>` : ''}
                                    ${item.item_total_option.dex ? `<p>DEX: ${item.item_total_option.dex}</p>` : ''}
                                    ${item.item_total_option.int ? `<p>INT: ${item.item_total_option.int}</p>` : ''}
                                    ${item.item_total_option.luk ? `<p>LUK: ${item.item_total_option.luk}</p>` : ''}
                                    ${item.item_total_option.attack_power ? `<p>Attack Power: ${item.item_total_option.attack_power}</p>` : ''}
                                    ${item.item_total_option.magic_power ? `<p>Magic Power: ${item.item_total_option.magic_power}</p>` : ''}
                                    ${item.item_total_option.boss_damage ? `<p>Boss Damage: ${item.item_total_option.boss_damage}</p>` : ''}
                                    ${item.item_total_option.ignore_monster_armor ? `<p>Ignore Monster Armor: ${item.item_total_option.ignore_monster_armor}</p>` : ''}
                                </div>
                            </div>
                        `).join('')}
                    </div>
                </div>
            </div>
        </div>
        `;


        // Toggle visibility of the stats container
        document.getElementById("toggle-stats").addEventListener("click", () => {
            const statsContainer = document.getElementById("stats-container");
            const arrowIcon = document.getElementById("arrow-icon");
            const isHidden = statsContainer.classList.toggle("hidden");
            arrowIcon.classList.toggle("rotate-180", !isHidden);
        });

        // Toggle visibility of the equipment container
        document.getElementById("toggle-equipment").addEventListener("click", () => {
            const equipmentContainer = document.getElementById("equipment-container");
            const equipmentArrowIcon = document.getElementById("equipment-arrow-icon");
            const isHidden = equipmentContainer.classList.toggle("hidden");
            equipmentArrowIcon.classList.toggle("rotate-180", !isHidden);
        });

        resultContainer.classList.remove("hidden");
        noResults.classList.add("hidden");
    }

    function displayNoResults() {
        resultContainer.innerHTML = "";
        resultContainer.classList.add("hidden");
        noResults.classList.remove("hidden");
    }
});
