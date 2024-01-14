$(function () {
    let inGame = false;

    $.ajax({
        url: "/game-status",
        type: "GET",
        async: false,
        success: function (data) {
            inGame = 'true' === data;
        }
    })

    if (!inGame) {
        buildLoginPage();
        console.info("Not in the game!");
    } else {
        buildGamePage();
        console.info("In the game!");
    }
});


function getPlayer() {
    let player;
    $.ajax({
        url: "player",
        type: "GET",
        async: false,
        success: [
            function (data) {
                player = JSON.stringify(data);
            }
        ],
        error: function (msg) {
            console.error(msg);
        }
    });
    return player;
}

function createPlayer() {
    let data = {
        'name': `${$("#player_name").val()}`,
        'class': `${$("#player_class").val()}`
    }

    $.ajax({
        url: 'player',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: [
            function (data, textStatus, xhr) {
                if (xhr.status === 201) {
                    console.info('Player created');
                } else {
                    console.info('Player edited');
                }
            }
        ],
        error: function (msg) {
            console.error(msg);
        }

    });
}

function deletePlayer() {
    $.ajax({
        url: "player",
        type: "DELETE",
        success: function () {
            console.info('Player deleted');
        },
        error: function (msg) {
            console.error(msg);
        }
    });
}

function getLocationText() {
    let text = "";
    $.ajax({
        url: "location",
        type: "GET",
        async: false,
        success: function (data) {
            text = data;
        },
        error: function (msg) {
            console.error(msg);
        }
    });
    return text;
}

function getDirections() {
    let directions;
    $.ajax({
        url: "directions",
        type: "GET",
        async: false,
        success: function (data) {
            directions = data;
        },
        error: function (msg) {
            console.error(msg);
        }
    });
    return directions;
}

function restartGame() {
    $.ajax({
        url: "restart",
        type: "GET",
        success: [
            function () {
                window.location.reload();
                console.info('Game restarted');

            }
        ],
        error: function (msg) {
            console.error(msg);
        }
    });
}

function startGame() {
    $.ajax({
        url: "init",
        type: "GET",
        success: [
            function (data) {
                buildGamePage();
                console.info('Game started');
            }
        ],
        error: function (msg) {
            console.error(msg);
        }
    });
}

function buildLoginPage() {
    document.body.innerHTML = '';

    let tempContainer = document.createElement("div");
    tempContainer.innerHTML = createForm;
    document.body.appendChild(tempContainer);
}

function buildGamePage() {
    document.body.innerHTML = '';

    let tempContainer = document.createElement("div");
    tempContainer.innerHTML = gameForm;
    tempContainer.style.width = "80%";
    document.body.appendChild(tempContainer);

    updateView();
}

function updateControls(directions) {
    $('.button-row').empty();

    $.each(directions, function (key, val) {
        let button = $('<button>').text(val);
        button.addClass('ingame-button');
        button.on('click', () => goTo(val));
        $('.button-row').append(button);
    });
}

function updatePlayer(player) {
    let json = JSON.parse(player);
    $('#player-name-p').text(json.name);
    $('#player-class-p').text(json.class);
}

function updateView() {
    let text = getLocationText();
    let directions = getDirections();
    let player = getPlayer();

    $('#main-text-field').text(text);
    updateControls(directions);

    updatePlayer(player);
}

function goTo(location) {

    let data = {
        'location': `${location}`,
    }

    $.ajax({
        url: 'location',
        type: 'POST',
        contentType: 'application/json',
        async: false,
        data: JSON.stringify(data),
        success: [
            () => {
                updateView();

                console.info('Location updated');
            }
        ],
        error: function (msg) {
            console.error(msg);
        }

    });
}

let createForm = "<div id=\"menu-container-div\">\n" +
    "    <h2>Создание персонажа</h2>\n" +
    "    <label for=\"player_name\">Имя персонажа:</label>\n" +
    "    <input type=\"text\" id=\"player_name\" name=\"player_name\" placeholder=\"Имя\" style='height: 30px' required>\n" +
    "\n" +
    "    <label for=\"player_class\">Класс персонажа:</label>\n" +
    "    <select id=\"player_class\" name=\"player_class\" style='height: 30px' required>\n" +
    "        <option value=\"warrior\">Воин</option>\n" +
    "        <option value=\"mage\">Маг</option>\n" +
    "        <option value=\"rogue\">Разбойник</option>\n" +
    "    </select>\n" +
    "\n" +
    "    <button id=\"create-player-button\" type=\"submit\" onclick=\"createPlayer()\">Создать персонажа</button>\n" +
    "    <button type=\"submit\" onclick=\"startGame()\">Начать игру</button>\n" +
    "</div>";

let gameForm = `<div id="game-container-div">
    <div id="playerInfoContainer">
        <div>
            <p id="player-name-p"></p>
            <p id="player-class-p"></p>
        </div>
        <button onclick="restartGame()" id="infoButton">Рестарт</button>
    </div>
    
    <div id="main-content-div">
        <p id='main-text-field' style='text-align: left'></p>
    </div>

    <div class="button-row">
    </div>

    <p id="message"></p>
</div>`;