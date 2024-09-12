/**
 * Representation of a position
 */
class Position{
    /**
     * Needs a row and a column, starts from 0
     * @param {number} row The number of the row it's in
     * @param {number} column The number of the column it's in
     */
    constructor(row,column){

        this._row = row;
        this._column = column;
    }
    /**
     * return the row
     */
    get row(){
        return this._row;
    }
    /**
     * return the column
     */
    get column(){
        return this._column;
    }

    /**
     * gives the next position given by the direction
     * @param {Direction} dir the next direction to take
     * @returns the next Position
     */
    nextPosition(dir){
        
        return new Position(this._row + dir._deltaRow, this._column + dir.deltaColumn);

    }
}