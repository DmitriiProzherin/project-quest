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