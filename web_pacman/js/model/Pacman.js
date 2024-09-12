/**
* The powerful, the pleasurable, the indestructible Pacman.
*/
class Pacman extends Sprite {
    /**
    * @param {Position} position the initial position
    * @param {Direction} direction the initial direction
    */
    constructor(position, direction) {
        super(position, direction, PACMAN_ID);
        this._nbLives = NB_LIVES;
    }


    /**
     * returns the number of lives left for pacman
     */
    get nbLives(){
        return this._nbLives;
    }

    /**
     * pacman has been eaten, his number of lives decreases by 1
     */
    hasBeenEaten(){
        this._isDead = true;
        this._nbLives -= 1;
    }

    addlive(){
        this._nbLives++;
    }
}
PACMAN_ID = "PacMan";
