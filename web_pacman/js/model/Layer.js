/**
 * Represents a layer of the game, dots or walls
 */
class Layer{

    /**
     * Needs the number of rows and the number of columns of the board
     * @param {number} nbRows the number of rows
     * @param {number} nbColumns the number of columns
     */
    constructor(nbRows, nbColumns){
        this._nbRows = nbRows;
        this._nbColumns = nbColumns;
        this._layer = Array(nbRows).fill().map(() => Array(nbColumns));
        
    }

    /**
     * Check if the given position is in the bounds of the board
     * @param {Position} pos the position to check
     * @returns true if it's in, false if it's not
     */
    contains(pos){
        if((pos.row >=0 && pos.row <this._nbRows) 
        && (pos.column >=0 && pos.column < this._nbColumns)){
            return true;
        }
        return false;
    }

    /**
     * Set the given tile in the given position of the board
     * @param {Position} pos the asked position
     * @param {Tile} tile the tile to put
     */
    setTile(pos, tile){
        if(!this.contains(pos)){
            throw "Mauvaise position";
        }
        this._layer[pos.row][pos.column] = tile;
    }

    /**
     * Give the tile to the given position
     * @param {Position} pos the position that we take the tile from
     * @returns the tile at the given position
     */
    getTile(pos){
        if(!this.contains(pos)){
            throw "Mauvaise position";
        }
        return this._layer[pos.row][pos.column];
    }

    /**
     * Check if there is a tile at the given position
     * @param {Position} pos the asked position
     * @returns true if there is a tile, false if there is not.
     */
    hasTile(pos){
        if(!this.contains(pos)){
            throw "Mauvaise position";
        }
        let searched = this._layer[pos.row][pos.column];
        if(typeof searched !== "undefined"){
            return true;
        }
        return false;
    }
}