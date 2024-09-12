/**
 * Class that represents a direction, on x or y.
 */
class Direction{

    /**
     * Needs a x and a y
     * @param {number} deltaRow the x param
     * @param {number} deltaColumn the y param
     */
    constructor(deltaRow, deltaColumn){
        this._deltaRow = deltaRow;
        this._deltaColumn = deltaColumn;
    }

    /**
     * return the x value
     */
    get deltaRow(){
        return this._deltaRow;
    }

    /**
     * return the y value
     */
    get deltaColumn(){
        return this._deltaColumn;
    }
}

// Some const
Direction.NORTH = new Direction(-1,0);
Direction.SOUTH = new Direction(1,0);
Direction.WEST = new Direction(0,-1);
Direction.EAST = new Direction(0,1);