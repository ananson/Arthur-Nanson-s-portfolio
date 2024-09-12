class addGhosts{

    constructor(game){
        this.game = game;

        addEventListener('keydown', function(event) {
            let pushed = event.key;
             switch(pushed){
                 case "f":
                     game.addGhost();
                     console.log("a")
                     break;
             }
    })

    }
}