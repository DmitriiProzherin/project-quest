$(function() {
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
        console.log("Not in the game!");
    }
    else {
        buildGamePage();
        console.log("In the game!");
    }
});


function getPlayer(){
    $.ajax({
       url: "player",
       type: "GET",
       success: [
           function (data) {
                console.info(JSON.stringify(data));

           }
       ],
       error: function (msg) {
           console.error(msg);
       }
    });
}
function createPlayer(){
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
           }
           else {
               console.info('Player edited');
           }
           }
       ],
       error: function (msg) {
           console.error(msg);
       }

    });
}

function deletePlayer(){
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

function getLocationText(){
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

function restartGame(){
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

function startGame(){
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

function buildLoginPage(){
    document.body.innerHTML = '';

    let tempContainer = document.createElement("div");
    tempContainer.innerHTML = createForm;
    document.body.appendChild(tempContainer);
}

function buildGamePage(){
    document.body.innerHTML = '';

    let tempContainer = document.createElement("div");
    tempContainer.innerHTML = gameForm;
    tempContainer.style.width = "80%";
    document.body.appendChild(tempContainer);

    let text = getLocationText();

    $('#main-text-field').text(text);
}

let createForm = "<div id=\"create-player-div\">\n" +
    "    <h2>Создание персонажа</h2>\n" +
    "    <label for=\"player_name\">Имя персонажа:</label>\n" +
    "    <input type=\"text\" id=\"player_name\" name=\"player_name\" placeholder=\"Имя\" required>\n" +
    "\n" +
    "    <label for=\"player_class\">Класс персонажа:</label>\n" +
    "    <select id=\"player_class\" name=\"player_class\" required>\n" +
    "        <option value=\"warrior\">Воин</option>\n" +
    "        <option value=\"mage\">Маг</option>\n" +
    "        <option value=\"rogue\">Разбойник</option>\n" +
    "    </select>\n" +
    "\n" +
    "    <button id=\"create-player-button\" type=\"submit\" onclick=\"createPlayer()\">Создать персонажа</button>\n" +
    "    <button type=\"submit\" onclick=\"startGame()\">Начать игру</button>\n" +
    "</div>";

let gameForm = "<div class=\"game-container-div\">\n" +
    "    <h1>Добро пожаловать на нашу страницу!</h1>\n" +
    "    <p id='main-text-field'></p>\n" +
    "\n" +
    "    <div class=\"button-row\">\n" +
    "        <button class='ingame-button' onclick=\"showMessage('Вы выбрали первую кнопку')\">Кнопка 1</button>\n" +
    "        <button class='ingame-button' onclick=\"restartGame()\">Начать сначала</button>\n" +
    "        <button class='ingame-button' onclick=\"showMessage('Вы выбрали третью кнопку')\">Кнопка 3</button>\n" +
    "    </div>\n" +
    "\n" +
    "    <p id=\"message\"></p>\n" +
    "</div>";