/**
 * A tile is a fixed Pacman maze component like a wall or a dot.
 */
class Tile{
    /**
     * To be created, a Tile just need an id.
     *
     * @param {string} id unique tile's id
     */
    constructor(id) {
        this._id = id;
    }

    /**
     * @returns {string}
     */
    get id() { return this._id; }
}