$(function () {

});

function getPlayer(){
    $.ajax({
       url: "player",
       type: "GET",
       success: [
           function (data) {
                console.info(JSON.stringify(data));
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
       contentType: 'application/json',
       data: JSON.stringify(data),
       success: [
           function (data, textStatus, xhr) {
           if (xhr.status === 201) {
               console.info('Player created');
           }
           else {
               console.log('Player edited');
           }
           }
       ],
       error: function (msg) {
           console.error(`Player wasn\'t updated ${msg}`);
       }

    });
}

function deletePlayer(){
    $.ajax({
       url: "player",
       type: "DELETE",
       success: function (data, text, xhs) {
           if (xhs === 204) {
               console.info('No player')
           }
           else {
               console.info('Player deleted')
           }
       }
    });
}