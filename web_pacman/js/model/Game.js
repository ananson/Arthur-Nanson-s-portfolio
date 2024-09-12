/**
 * Representation of the game
 */
class Game{

    /**
     * 
     * @param {*} labyrinthe the board to build
     */
    constructor(labyrinthe){
        this._labyrinthe = new Maze(labyrinthe);
        this._beginMaze = labyrinthe;
        this._Pacman = new Pacman(this._labyrinthe.pacSpawn, Direction.WEST);
        this._ghosts = [];
        let possible = [Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST];
        this._compteur = 0;
        this._localScore = 0;
        for(let i = 0; i<4; i++){
            this._ghosts[i] = new Ghost(this._labyrinthe._ghostSpawn, possible[i], "ghost" + i );
        }
        this._score = 0;
        this._removedDot = undefined;
        this._highScore = localStorage.getItem("highScore");
        if(!this._highScore){
            this._highScore = 0;
        }
        

    }

    /**
     * return the maze/board
     */
    get labyrinthe(){
        return this._labyrinthe;
    }

    /**
     * return pacman
     */
    get Pacman(){
        return this._Pacman;
    }

    /**
     * returns the score
     */
    get score(){
        return this._score;
    }

    /**
     * returns the last removed dot
     */
    get removedDot(){
        return this._removedDot;
    }

    /**
     * returns the current best score
     */
    get highScore(){
        return this._highScore;
    }

    get ghostLength(){
        return this._ghosts.length;
    }
    /**
     * Moves the sprites according to the current direction
     * May change the current direction to the asked one if possible
     * @returns nothing
     */
    moveSprites(){
        if(this._Pacman._askedToChangeDirection && this._labyrinthe.canWalkOn(this._Pacman._position.nextPosition(this._Pacman._askedDirection))){
            this._Pacman.changeDirection();
            this._Pacman.move();
            
            
        }
        else if(this._labyrinthe.canWalkOn(this._Pacman._position.nextPosition(this._Pacman._direction))){
            this._Pacman.move();
            }
        else{
            let colors = ["blue", "green", "red"];
            
            
            document.getElementById("PacmanView").style.backgroundColor = colors[this._compteur];
 
            this._compteur++;
            if(this._compteur == 3){
                this._compteur = 0;
            }
            


        }
        
        //mange les gommes
        if(this._labyrinthe.canPick(this._Pacman._position)){
            let picked = this._labyrinthe.pick(this._Pacman._position);
            this._removedDot = picked;
            
            if(picked.isEnergizer){
                this._score += 100;
                this._localScore += 100;
            }
            else{
                this._score+=10;
                this._localScore += 10;
            }
            if(this._localScore >=50){
                console.log(this._localScore)
                this._localScore = 0;
                this._Pacman.addlive();
                console.log(this._Pacman.nbLives)
            }
            
        }
        let currentTurn = false; //so that pacman can't lose multiple lives when stacked fantoms
        for(let g in this._ghosts){
            
            if(this._ghosts[g]._askedToChangeDirection){
                if(this._labyrinthe.canWalkOn(this._ghosts[g]._position.nextPosition(this._ghosts[g]._askedDirection))){
                    this._ghosts[g].changeDirection();
                    this._ghosts[g].move();
                    return;
                }
            }
            if(this._labyrinthe.canWalkOn(this._ghosts[g]._position.nextPosition(this._ghosts[g]._direction))){
                this._ghosts[g].move();
            }
            else{
                this._ghosts[g].notifyIsBlocked();
            }
            if(this._ghosts[g].canEat(this._Pacman) && !(currentTurn)){
                console.log("Pacman has been eaten")
                currentTurn = true;
                this._Pacman.hasBeenEaten();
                if(!(this.isGameOver())){
                    let vie = document.getElementById("vie" + (this._Pacman.nbLives +1));
                    document.getElementById("jeu").removeChild(vie);

                    
                    this.respawn();
                }

            }

        }
    }

    addGhost(){
        let fantome = document.createElement("div");
        fantome.id = "ghost" + this.ghostLength;

        fantome.style.position = "absolute";
        fantome.style.left = this._labyrinthe._ghostSpawn.column * TILE_SIZE + 22 + 'px';
        fantome.style.top = this._labyrinthe._ghostSpawn.row * TILE_SIZE + 134 + 'px';
        fantome.classList.add("New");
        this._ghosts[this._ghosts.length] = new Ghost(this._labyrinthe._ghostSpawn, Direction.NORTH, "ghost" + this._ghosts.length );
        document.getElementById("jeu").appendChild(fantome);
    }


    /**
     * Checks the remaining lives of pacman to see if the game is over
     * @returns true if the game is over, false if not
     */
    isGameOver(){
        
        return (this._Pacman.nbLives < 0)
               
    }

    /**
     * 
     * @returns true if pacman is dead, false if not
     */
    pacmanHasBeenEaten(){
        return this._Pacman.isDead;
    }

    /**
     * All the sprites go back to their first position when pacman has been eaten
     */
    respawn(){
        this._Pacman.respawn();
        for(let g in this._ghosts){
            this._ghosts[g].respawn();
        }

    }

    /**
     * is this the new highscore ?
     */
    saveScore(){
        if(this._score > Number(this._highScore)){
            localStorage.setItem("highScore", this._score);
            this._highScore = this._score;
        }
    }

    /**
     * 
     * @returns true if the lvl is complete
     */
    lvlSucceed(){
        return this._labyrinthe.isEmpty();
    }

    /**
     * goes to the next level
     */
    nextLevel(){
        this._labyrinthe = new Maze(this._beginMaze);
        this._Pacman.askToChangeDirection(Direction.WEST);
        this._Pacman.changeDirection();
        this._Pacman.setPosition(this._labyrinthe.pacSpawn);
        
        let possible = [Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST];
        for(let i = 0; i<4; i++){
            this._ghosts[i].askToChangeDirection(possible[i]);
            this._ghosts[i].setPosition(this._labyrinthe._ghostSpawn);
        }

    }
}