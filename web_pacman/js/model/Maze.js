/**
 * Representation of the maze, has 2 layers,
 * one for the dots and the other for the walls
 */
class Maze{

    /**
     * Needs a maze to read from, initializes and fills the two layers with
     * their values.
     * @param {2D array} labyrinthe the maze to read from
     */
    constructor(labyrinthe){
        this._maze = labyrinthe;
        this.layerWall = new Layer(labyrinthe.table.length, labyrinthe.table[0].length);
        this.layerDot = new Layer(labyrinthe.table.length, labyrinthe.table[0].length);
        this._nbDots = 4;//for the energizers

        for(let i = 0; i<RAW_MAZE.table.length;i++){
            for(let j = 0; j<RAW_MAZE.table[i].length;j++){
                let current = RAW_MAZE.table[i][j];
                let pos = new Position(i,j)
                if(current == 1){
                    let wall = new Wall("w" + String(i) +" "+ String(j));
                    
                    this.layerWall.setTile(pos, wall);
                }
                else if(current == 2){
                    let dot = new Dot("d" + String(i) +" "+ String(j),false);
                    
                    this.layerDot.setTile(pos,dot);
                    this._nbDots++;

                }
                else if(current == 3){
                    let dot = new Dot("d" + String(i) + String(j),true);
                    this.layerDot.setTile(pos,dot);

                }
                else if(current == 4){
                    this._pacSpawn = pos;
                }

                else if(current == 5){
                    this._ghostSpawn = pos;
                }


            }
        }
    }

    /**
     * Give the wall that is in the asked position in the wall layer
     * @param {Position} pos the asked position
     * @returns the asked wall
     */
    getWallLayerTile(pos){
        if(!this.layerWall.contains(pos)){
            throw "Mauvaise position";
        }
        return this.layerWall.getTile(pos);
    }

    /**
     * Gives the dot that is in the asked position in the dot layer
     * @param {Position} pos the asked position
     * @returns the asked dot
     */
    getDotLayerTile(pos){
        if(!this.layerDot.contains(pos)){
            throw "Mauvaise position";
        }
        return this.layerDot.getTile(pos);
    }

    /**
     * Return the number of rows in the board
     */
    get nbRows(){
        return this.layerWall._layer.length;
    }

    /**
     * Return the number of columns in the board
     */
    get nbColumns(){
        return this.layerWall._layer[0].length;
    }

    /**
     * return the spawn of pacman
     */
    get pacSpawn(){
        return this._pacSpawn;
    }

    /**
     * Returns true if we can walk on the asked pos, which means no walls
     * and in bounds
     * @param {Position} pos the asked position
     * @returns true or false
     */
    canWalkOn(pos){
        if(this.layerWall.contains(pos) && !(this.layerWall.hasTile(pos))){
            return true;
        }
        return false;
    }

    /**
     * Check if there is a dot to pick at the asked position
     * @param {Position} pos the asked position
     * @returns true if there is, false if there is not
     */
    canPick(pos){
        if(this.layerDot.contains(pos) && (this.layerDot.hasTile(pos))){
            return true;
        }
        return false;
    }

    /**
     * returns the tile at the asked position and throws an error if there
     * is nothing at that position
     * @param {Position} pos the asked positon
     * @returns the tile at the asked position
     */
    pick(pos){
        let asked = this.layerDot.getTile(pos);
        if(typeof asked === "undefined"){
            throw "Aucune gomme à cette position";
        }
        this.layerDot.setTile(pos, undefined);
        this._nbDots--;
        return asked;
    }

    /**
     * 
     * @returns true if there are no dots left, false if there are
     */
    isEmpty(){
        return (this._nbDots == 0);
    }
}
