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
           // console.info(data);
           $("#create-player-div").remove();
           }
       ],
       error: function (msg) {
           console.error(`Player wasn\'t created ${msg}`);
       }

    });
}