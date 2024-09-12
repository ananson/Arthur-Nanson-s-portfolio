/**
 * Class that listens to the user's inputs
 */
class PacmanView{

    /**
     * Needs a PacmanCtrl and change direction accordingly to the user's will
     * @param {PacmanCtrl} pacmanCtrl 
     */
    constructor(pacmanCtrl){
        this.pacmanCtrl = pacmanCtrl;

        addEventListener('keydown', function(event) {
            let pushed = event.key;
             switch(pushed){
                 
                case "ArrowLeft":
                    pacmanCtrl.askToChangeDirection(Direction.WEST);
                    break;

                case "ArrowRight":
                    pacmanCtrl.askToChangeDirection(Direction.EAST);
                    break;

                case "ArrowUp":
                    pacmanCtrl.askToChangeDirection(Direction.NORTH);
                    break;

                case "ArrowDown":
                    pacmanCtrl.askToChangeDirection(Direction.SOUTH);
                    break;

                case "s":
                    pacmanCtrl.askToChangeDirection(new Direction(0,0));
                    
             }
        })
    }

    
}