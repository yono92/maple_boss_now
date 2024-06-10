document.addEventListener("DOMContentLoaded", () => {
    const searchForm = document.getElementById("search-form");
    const searchQuery = document.getElementById("search-query");
    const resultContainer = document.getElementById("result-container");
    const noResults = document.getElementById("no-results");

    searchForm.addEventListener("submit", async (event) => {
        event.preventDefault();
        const query = searchQuery.value.trim();
        if (query) {
            const response = await fetch(`/api/v1/search/character?characterName=${encodeURIComponent(query)}`);
            if (response.ok) {
                const data = await response.json();
                displayResults(data);
            } else {
                displayNoResults();
            }
        }
    });

    function displayResults(data) {
        resultContainer.innerHTML = `
            <div class="bg-gray-100 dark:bg-gray-700 p-4 rounded-lg shadow-md">
                <h4 class="text-lg font-semibold">${data.characterName}</h4>
                <p><strong>World:</strong> ${data.worldName}</p>
                <p><strong>Gender:</strong> ${data.characterGender}</p>
                <p><strong>Class:</strong> ${data.characterClass}</p>
                <p><strong>Level:</strong> ${data.characterLevel}</p>
                <p><strong>EXP:</strong> ${data.characterExp}</p>
                <p><strong>EXP Rate:</strong> ${data.characterExpRate}</p>
                <p><strong>Guild:</strong> ${data.characterGuildName}</p>
                <img src="${data.characterImage}" alt="Character Image" class="w-24 h-24 rounded-lg mt-2">
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
