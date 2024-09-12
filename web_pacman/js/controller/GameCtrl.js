/**
 * Use this class to display the game
 */
class GameCtrl{

    /**
     * Doesn't need anything and creates the needed objects
     */
     constructor(){
         this.game = new Game(RAW_MAZE);
         this.view = new GameView(this.game, this);
         let pacCtrl = new PacmanCtrl(this.game._Pacman);
         let addGhost = new addGhosts(this.game);
         let pacmanView = new PacmanView(pacCtrl);
         
     }

     /**
      * Function called every 0.3 seconds to move the sprites
      * and update everything on screen
      */
     run() {
        this._timer = setInterval(() => {
            if(this.game.lvlSucceed()){
                this.game.nextLevel();
                this.view.nextLevel();
            }
        this.game.moveSprites();
        this.view.updateFrame();
        if(this.game.isGameOver()){
            console.log("Game Over");
            this.game.saveScore();
            this.view.displayGameOver();
            clearInterval(this._timer);
        }
        }, RUN_INTERVAL);
    }

    /**
     * The start button has been pressed, the game will begin
     */
    startHasBeenRequested(){
        this.run();
    }
        
}
RUN_INTERVAL = 300;