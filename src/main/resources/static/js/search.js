document.addEventListener("DOMContentLoaded", () => {
    const searchForm = document.getElementById("search-form");
    const searchQuery = document.getElementById("search-query");
    const resultContainer = document.getElementById("result-container");
    const noResults = document.getElementById("no-results");
    const guildPopup = document.getElementById("guild-popup");
    const guildPopupContent = document.getElementById("guild-popup-content");
    const guildPopupClose = document.getElementById("guild-popup-close");

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

    // 길드 정보를 검색하는 함수
    async function searchGuild(guildName, worldName) {
        try {
            const response = await fetch(`/api/v1/guild/basic-info?guildName=${encodeURIComponent(guildName)}&worldName=${encodeURIComponent(worldName)}`);
            if (response.ok) {
                const data = await response.json();
                displayGuildResultsInPopup(data);  // 팝업에 길드 결과를 표시
            } else {
                alert("Guild information not found.");
            }
        } catch (error) {
            console.error("Error fetching guild info:", error);
        }
    }


    function displayResults(data) {
        const basicInfo = data.characterBasicInfo;
        const statInfo = data.characterStatInfo;
        const finalStat = statInfo?.final_stat || [];
        const equipment = data.characterEquipInfo?.item_equipment || [];

        const excludedStats = ['전투력', '최소 스탯공격력', '최대 스탯공격력', 'STR', 'DEX', 'INT', 'LUK', 'HP', 'MP'];
        const filteredStats = finalStat.filter(stat => !excludedStats.includes(stat.stat_name));

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
                        <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">Level</p>
                        <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${basicInfo.character_level}</p>
                    </div>
                    <div>
                        <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">Guild</p>
                        <p class="text-lg font-medium text-blue-500 dark:text-blue-400 cursor-pointer" id="guild-name" data-world="${basicInfo.world_name}">
                            ${basicInfo.character_guild_name}
                        </p>
                    </div>
                </div>
                <div class="grid grid-cols-2 gap-4 mb-6">
                    <div>
                        <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">전투력</p>
                        <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${finalStat.find(stat => stat.stat_name === '전투력').stat_value}</p>
                    </div>
                    <div>
                        <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">공격력</p>
                        <p class="text-lg font-medium text-gray-800 dark:text-gray-100">
                            ${finalStat.find(stat => stat.stat_name === '최소 스탯공격력').stat_value} ~ 
                            ${finalStat.find(stat => stat.stat_name === '최대 스탯공격력').stat_value}
                        </p>
                    </div>
                    <div>
                        <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">STR</p>
                        <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${finalStat.find(stat => stat.stat_name === 'STR').stat_value}</p>
                    </div>
                    <div>
                        <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">DEX</p>
                        <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${finalStat.find(stat => stat.stat_name === 'DEX').stat_value}</p>
                    </div>
                    <div>
                        <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">INT</p>
                        <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${finalStat.find(stat => stat.stat_name === 'INT').stat_value}</p>
                    </div>
                    <div>
                        <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">LUK</p>
                        <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${finalStat.find(stat => stat.stat_name === 'LUK').stat_value}</p>
                    </div>
                    <div>
                        <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">HP</p>
                        <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${finalStat.find(stat => stat.stat_name === 'HP').stat_value}</p>
                    </div>
                    <div>
                        <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">MP</p>
                        <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${finalStat.find(stat => stat.stat_name === 'MP').stat_value}</p>
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
                        ${filteredStats.map(stat => `
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

        // 길드 이름 클릭 이벤트 추가 (캐릭터 정보가 렌더된 후)
        const guildNameElement = document.getElementById('guild-name');
        if (guildNameElement) {
            guildNameElement.addEventListener('click', () => {
                const guildName = guildNameElement.textContent.trim();
                const worldName = guildNameElement.getAttribute('data-world');
                searchGuild(guildName, worldName);
            });
        }

        resultContainer.classList.remove("hidden");
        noResults.classList.add("hidden");

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

    resultContainer.classList.remove("hidden");
    noResults.classList.add("hidden");


// 길드 정보를 팝업에 표시하는 함수
    function displayGuildResultsInPopup(data) {
        const guildPopupContent = document.getElementById('guild-popup-content');
        const guildPopup = document.getElementById('guild-popup');
        const guildPopupClose = document.getElementById('guild-popup-close');

        // 길드 스킬 섹션을 토글할 수 있도록 처리
        guildPopupContent.innerHTML = `
        <div class="bg-white dark:bg-gray-800 rounded-lg shadow-md p-6 w-full max-w-lg">
            <div class="flex flex-col items-center justify-center mb-4">
                <img src="${data.guild_mark_custom || 'https://via.placeholder.com/96'}" alt="Guild Mark" class="w-24 h-24 rounded-full">
                <div class="text-center mt-4">
                    <h4 class="text-xl font-bold text-gray-800 dark:text-gray-100">${data.guild_name || '길드 이름 없음'}</h4>
                    <p class="text-gray-600 dark:text-gray-400">${data.world_name || '서버 이름 없음'}</p>
                </div>
            </div>
            <div class="grid grid-cols-2 gap-4 mb-6">
                <div>
                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">Guild Master</p>
                    <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${data.guild_master_name || 'N/A'}</p>
                </div>
                <div>
                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">GUild Level</p>
                    <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${data.guild_level || 0}</p>
                </div>
                <div>
                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">Guild Fame</p>
                    <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${data.guild_fame || 0}</p>
                </div>
                <div>
                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">Guild Point</p>
                    <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${data.guild_point || 0}</p>
                </div>
                <div>
                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">Guild Member</p>
                    <p class="text-lg font-medium text-gray-800 dark:text-gray-100">${data.guild_member_count || 0}</p>
                </div>
            </div>
            
            <!-- 길드 스킬 토글 -->
            <div class="mt-6">
                <h4 class="text-lg font-bold text-gray-800 dark:text-gray-100 flex items-center cursor-pointer" id="toggle-skills">
                    길드 스킬
                    <svg id="skills-arrow-icon" xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 ml-2 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M19 9l-7 7-7-7" />
                    </svg>
                </h4>
                <div class="grid grid-cols-2 gap-4 mt-4 max-h-60 overflow-y-auto hidden" id="skills-container">
                    ${data.guild_skill && data.guild_skill.length > 0 ? data.guild_skill.map(skill => `
                        <div class="flex flex-col bg-gray-100 dark:bg-gray-700 p-4 rounded-md shadow-sm">
                            <div class="flex items-center">
                                <img src="${skill.skill_icon || 'https://via.placeholder.com/40'}" alt="${skill.skill_name}" class="w-10 h-10 mr-2">
                                <div>
                                    <p class="text-sm font-semibold text-gray-600 dark:text-gray-300">${skill.skill_name}</p>
                                    <p class="text-xs text-gray-500 dark:text-gray-400">${skill.skill_description}</p>
                                </div>
                            </div>
                            <p class="mt-2 text-sm text-gray-600 dark:text-gray-300">${skill.skill_effect}</p>
                            <p class="mt-2 text-sm text-gray-600 dark:text-gray-300">레벨: ${skill.skill_level}</p>
                        </div>
                    `).join('') : '<p class="text-gray-600 dark:text-gray-400">길드 스킬이 없습니다</p>'}
                </div>
            </div>
        </div>
    `;

        // 팝업을 보여줍니다
        guildPopup.classList.remove('hidden');

        // 팝업을 닫는 기능
        guildPopupClose.addEventListener('click', () => {
            guildPopup.classList.add('hidden');
        });

        // 길드 스킬 토글 기능 추가
        document.getElementById("toggle-skills").addEventListener("click", () => {
            const skillsContainer = document.getElementById("skills-container");
            const skillsArrowIcon = document.getElementById("skills-arrow-icon");
            const isHidden = skillsContainer.classList.toggle("hidden");
            skillsArrowIcon.classList.toggle("rotate-180", !isHidden);
        });
    }

});
