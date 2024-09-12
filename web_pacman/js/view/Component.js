/**
 * Link with tiles, represents a tile
 */
class Component{

    /**
     * Needs the id of the tile
     * @param {String} id 
     */
    constructor(id){
        this._id = id;
    }
    
    /**
    * @returns {string}
    */
    get id() { return this._id; }
}