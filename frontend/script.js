document.addEventListener("DOMContentLoaded", function() {
    loadClasses();

    document.getElementById('style-filter').addEventListener('change', filterClasses);
    document.getElementById('level-filter').addEventListener('change', filterClasses);
});

let allClasses = [];

function loadClasses() {
    fetch('http://localhost:8080/api/classes')
        .then(response => {
            if (!response.ok) throw new Error('Błąd pobierania danych');
            return response.json();
        })
        .then(data => {
            allClasses = data;
            renderClasses(allClasses);
        })
        .catch(error => {
            console.error('Błąd:', error);
        });
}

function renderClasses(classes) {
    const container = document.getElementById('calendar-container');
    container.innerHTML = '';

    classes.forEach(danceClass => {
        const isFull = danceClass.spotsAvailable <= 0;
        const card = document.createElement('div');
        card.className = `dance-card ${isFull ? 'card-full' : ''}`;

        let statusClass = 'status-free';
        let statusText = `Wolne miejsca: ${danceClass.spotsAvailable}`;
        let buttonHTML = `<button class="btn-primary" onclick="bookClass(${danceClass.id})">Zapisz się</button>`;

        if (isFull) {
            statusClass = 'status-full';
            statusText = 'Brak miejsc';
            buttonHTML = `<button class="btn-secondary" disabled>Lista rezerwowa</button>`;
        } else if (danceClass.spotsAvailable <= 3) {
            statusClass = 'status-low';
            statusText = `Ostatnie miejsca: ${danceClass.spotsAvailable}`;
        }

        let badgeClass = 'badge-jazz';
        if (danceClass.style.toLowerCase() === 'high heels') badgeClass = 'badge-heels';
        if (danceClass.style.toLowerCase() === 'hip hop') badgeClass = 'badge-hiphop';

        card.innerHTML = `
            <div class="card-time">${danceClass.dayOfWeek}, ${danceClass.time}</div>
            <div class="card-info">
                <span class="badge ${badgeClass}">${danceClass.style}</span>
                <span class="level-text">${danceClass.level}</span>
                <h3>${danceClass.name}</h3>
                <p class="instructor-name">Instruktor: ${danceClass.instructorName}</p>
            </div>
            <div class="card-footer">
                <div class="spots-status ${statusClass}">${statusText}</div>
                ${buttonHTML}
            </div>
        `;
        container.appendChild(card);
    });
}

function filterClasses() {
    const styleVal = document.getElementById('style-filter').value.toLowerCase();
    const levelVal = document.getElementById('level-filter').value.toLowerCase();

    const filtered = allClasses.filter(c => {
        const matchStyle = styleVal === 'all' || c.style.toLowerCase().replace(' ', '-') === styleVal;
        const matchLevel = levelVal === 'all' || c.level.toLowerCase() === levelVal;
        return matchStyle && matchLevel;
    });

    renderClasses(filtered);
}

function bookClass(classId) {
    const userId = localStorage.getItem('userId');
    
    if (!userId) {
        alert('Aby zapisać się na zajęcia, musisz się zalogować!');
        window.location.href = 'login.html';
        return;
    }

    fetch('http://localhost:8080/api/bookings', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            userId: parseInt(userId),
            classId: parseInt(classId)
        })
    })
    .then(response => {
        if (response.ok) {
            alert('Pomyślnie zapisano na zajęcia!');
            loadClasses();
        } else {
            throw new Error('Nie udało się dokonać rezerwacji.');
        }
    })
    .catch(error => {
        alert(error.message);
    });
}