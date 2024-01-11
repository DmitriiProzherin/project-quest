$(function () {

});

function getPlayer(){
    $.ajax({
       url: "player",
       type: "GET",
       success: [
           function (data) {

           }
       ]
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
       // contentType: 'application/json',
       data: JSON.stringify(data),
       success: [
           function () {
               let button = document.createElement('button');
               //document.body.append(button);
               button.textContent = "Посмотреть игрока";
               button.id = "#get-player-button";
               button.onclick(getPlayer());
               $("#create-player-div").append(button);
           }
       ],
       error: function (msg) {
           console.error(`Player wasn\'t created ${msg}`);
       }

    });
}