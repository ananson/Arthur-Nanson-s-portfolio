/**
 * Represents a dot that pacman can eat, can be simple or energizer
 */

class Dot extends Tile{

    /**
     * Needs an unique id and an energizer attibute
     * @param {string} id unique id
     * @param {boolean} isEnergizer dot's function
     */
    constructor(id, isEnergizer){
        super(id);
        this._isEnergizer = isEnergizer;
    }
    /**
     * return isEnergizer
     */
    get isEnergizer(){
        return this._isEnergizer;
    }
}