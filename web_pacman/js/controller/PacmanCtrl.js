/**
 * Class that implements the control of pacman
 */
class PacmanCtrl{

    /**
     * Only needs a pacman object
     * @param {Pacman} PacMan the pacman to work on
     */
    constructor(PacMan){
        this.PacMan = PacMan;
    }

    /**
     * Will change direction to the asked one when it's possible
     * @param {Direction} dir the asked direction
     */
    askToChangeDirection(dir){
        this.PacMan.askToChangeDirection(dir);
    }
}