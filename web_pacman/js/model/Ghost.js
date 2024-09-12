/**
 * Class that implements the ennemies of Pacman
 */
class Ghost extends Sprite{

    /**
     * Needs a position, a direction and the ghost's id, choses a new direction
     * every 4 seconds
     * @param {Position} position the current position
     * @param {Direction} direction the current direction
     * @param {String} id the ghost's id
     */
    constructor(position, direction, id){
        super(position, direction, id)
        let change = setInterval(() => {
            this._choiceNewDirection();
            }, 4000);
    }

    /**
     * choses a random direction and asks to change it
     */
    _choiceNewDirection(){
        let aleatoire = Math.floor(Math.random()*4);
        let possible = [Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST];
        this.askToChangeDirection(possible[aleatoire])

    }

    /**
     * Check if a ghost can eat pacman
     * @param {Pacman} Pacman 
     * @returns true if the ghost can eat pacman, false if he can't
     */
    canEat(Pacman){
        if((Pacman.previousPosition.row == this.position.row &&
            Pacman.previousPosition.column == this.position.column &&
            Pacman.position.row == this.previousPosition.row &&
            Pacman.position.column == this.previousPosition.column)
            || (Pacman.position.row == this.position.row &&
            Pacman.position.column == this.position.column)){
            return true;
        }
        return false;
    }

    /**
     * When the ghost is blocked by a wall
     */
    notifyIsBlocked(){
        this._choiceNewDirection();
    }
}