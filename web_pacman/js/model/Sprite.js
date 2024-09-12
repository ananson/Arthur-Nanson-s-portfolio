/**
 * Implements the movements of the Sprites
 */
class Sprite extends Component{

    /**
     * Needs the id of the sprite, it's position, current direction
     * and initialize variables that will tell us if the user wants to move or not
     * @param {Position} position The current position
     * @param {Direction} direction The current direction
     * @param {String} id unique id of the current sprite
     */
    constructor(position, direction, id){
        super(id);
        this._position = position;
        this._direction = direction;
        this._askedToChangeDirection = false;
        this._askedDirection = Direction.NORTH;
        this.previousPosition = position;
        this._isDead = false;
        this._firstPosition = position;
        this._firstDirection = direction;
    }

    /**
     * return the current position
     */
    get position(){
        return this._position;
    }

    /**
     * return the current direction
     */
    get direction(){
        return this._direction;
    }

    /**
     * return true if the user wants to change direction, false if they don't want to
     */
    get askedToChangeDirection(){
        return this._askedToChangeDirection;
    }

    /**
     * The next direction, when the game will allow a change
     */
    get askedDirection(){
        return this._askedDirection;
    }

    /**
     * Moves the sprite by looking at the current direction
     */
    move(){
        this.previousPosition = this._position;
        this._position = this._position.nextPosition(this._direction);
        
        //trou de ver
        if(this._position._row == 14 && this._position._column == 4 && this._direction == Direction.WEST){
            this._position = new Position(14,22);
        }
        else if(this._position._row == 14 && this._position._column == 23){
            
            this._position = new Position(14,5);
        }
    }

    /**
     * The user wants to change direction
     * @param {Direction} dir the next direction
     */
    askToChangeDirection(dir){
        this._askedToChangeDirection = true;
        this._askedDirection = dir;
    }

    /**
     * Change the current direction to what the user asked
     */
    changeDirection(){
        this._askedToChangeDirection = false;
        this._direction = this._askedDirection;
    }

    /**
     * Notifies the sprite if he is blocked by a wall
     */
    notifyIsBlocked(){

    }

    /**
     * returns isDead, true if the sprite is dead, false if it's alive
     */
    get isDead(){
        return this._idDead;
    }

    /**
     * the sprite has been eaten
     */
    hasBeenEaten(){
        this._isDead = true;
    }

    /**
     * the sprite has been eaten, makes it respawn
     */
    respawn(){
        this._isDead = false;
        this._position = this._firstPosition;
        this._direction = this._firstDirection;
    }

    setPosition(pos){
        this._position = pos;
    }
}