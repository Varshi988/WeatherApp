document.getElementById("searchBtn").addEventListener("click", async () => {
  const city = document.getElementById("cityInput").value.trim();
  const result = document.getElementById("result");

  if (city === "") {
    result.innerHTML = "<p>Please enter a city name.</p>";
    return;
  }

  try {
    const response = await fetch(`http://localhost:8080/weather?city=${city}`);
    if (!response.ok) {
      throw new Error("City not found");
    }
    const data = await response.json();

    result.innerHTML = `
      <h2>${data.city}</h2>
      <p>🌡 Temperature: ${data.temperature} °C</p>
      <p>🌥 Condition: ${data.condition}</p>
    `;
  } catch (error) {
    result.innerHTML = `<p>Error: ${error.message}</p>`;
  }
});
