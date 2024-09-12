/**
 * Displays the maze dynamically
 */
class GameView{
    /**
     * Takes a game param and displays it's content
     * @param {Game} game the maze to display, the board
     */
    constructor(game, ctrl){

        this.game = game;
        this.ctrl = ctrl;
        for(let i = 0; i<game._labyrinthe.nbRows;i++){
            for(let j = 0; j<game._labyrinthe.nbColumns;j++){

                let nouveau = document.createElement("div");
                let pos = new Position(i,j);
                if(game._labyrinthe.layerWall.hasTile(pos)){
                    nouveau.classList.add("wall");
                }
                else if(game._labyrinthe.layerDot.hasTile(pos)){
                    nouveau.classList.add("gomme");
                    nouveau.id = "d" + String(i) + " " + String(j);
                }
                //trou de ver
                else if(i == 14 && (j==4 || j==23)){
                    nouveau.classList.add("warp");
                }
                nouveau.style.position = "absolute";
                let pos_x = j*TILE_SIZE +20;
                let pos_y = i*TILE_SIZE +132;
                if(game._labyrinthe.layerDot.hasTile(pos)){
                    pos_x += 4;
                    pos_y += 4;
                }
                nouveau.style.left = pos_x + "px";
    
                nouveau.style.top = pos_y + "px";
                document.getElementById("jeu").appendChild(nouveau);
            }
            
        }

        //spawn de pacman
        let pacboy = document.createElement("div");
        pacboy.id = "PacmanView";
        let pos_spawn = game._labyrinthe._pacSpawn;
        pacboy.style.position = "absolute";
        pacboy.style.left = pos_spawn._column * TILE_SIZE + 23 + 'px';
        pacboy.style.top = pos_spawn._row  * TILE_SIZE + 134 + 'px';
        pacboy.classList.add("Pacman");
        document.getElementById("jeu").appendChild(pacboy);

        //spawn des fantomes
        for(let i = 0; i<4; i++){
            let fantome = document.createElement("div");
            fantome.id = "ghost" + i;
            fantome.style.position = "absolute";
            fantome.style.left = game._labyrinthe._ghostSpawn.column * TILE_SIZE + 22 + 'px';
            fantome.style.top = game._labyrinthe._ghostSpawn.row * TILE_SIZE + 134 + 'px';
            if(i==0){
                fantome.classList.add("Blinky");
            }
            else if(i==1){
                fantome.classList.add("Pinky");
            }
            else if(i==2){
                fantome.classList.add("Inky");
            }
            else if(i==3){
                fantome.classList.add("Clyde");
            }
            document.getElementById("jeu").appendChild(fantome);
        }

        for(let i  = 1; i<=NB_LIVES;i++){
            let vie = document.createElement("div");
            vie.classList.add("Life");
            vie.id = "vie" + i;
            vie.style.position = "absolute";
            vie.style.left = 28 + (i-1) * 25 + 'px';
            vie.style.top = 613 + 'px';
            document.getElementById("jeu").appendChild(vie);
        }

        //displays the highscore
        document.getElementById("HighScore").innerHTML = this.game.highScore;

        //taille variables
        document.getElementById("jeu").style.width = game._labyrinthe.nbColumns * TILE_SIZE + 'px';
        document.getElementById("jeu").style.height = game._labyrinthe.nbRows * TILE_SIZE + 'px';

        //start button
        let bouton = document.createElement("button");
        bouton.id = "Start";
        bouton.innerHTML = "Start";
        $(bouton).on('click',{
            controller: this.ctrl
        }, this.startGame)
        bouton.style.position = "absolute";
        bouton.style.left = 460 + 'px';
        bouton.style.top = 338 + 'px'; 

        document.getElementById("corps").appendChild(bouton)


    }

    /**
     * Updates the position of pacman on screen
     */
    updateFrame(){

        let pacView = document.getElementById("PacmanView");
        pacView.style.left = this.game._Pacman._position._column * TILE_SIZE + 23 + 'px';
        pacView.style.top = this.game._Pacman._position._row * TILE_SIZE + 134 + 'px';
        for(let i = 0; i<this.game.ghostLength; i++){
            console.log(i)
            let fantomeView = document.getElementById("ghost" + i);
            console.log(this.game._ghosts[i])
            fantomeView.style.left = this.game._ghosts[i].position.column * TILE_SIZE + 22 + 'px';
            fantomeView.style.top = this.game._ghosts[i].position.row * TILE_SIZE + 134 + 'px';
        }
        //removes dots
        let toremove = document.getElementById("d" + this.game._Pacman.position.row + " " + this.game._Pacman.position.column);

        if(toremove != null){
            document.getElementById("jeu").removeChild(toremove);
        }
        document.getElementById("currentScore").innerHTML = this.game.score; 

        
    }

    /**
     * displays the highscore
     */
    displayGameOver(){
        console.log(this.game.highScore);
    }

    /**
     * displays the next level, don't know how to call back the constructor
     */
    nextLevel(){
        let jeu = document.getElementById("jeu");
        while(jeu.firstChild){
            jeu.removeChild(jeu.lastChild);
        }
        this.game.nextLevel();
        for(let i = 0; i<this.game._labyrinthe.nbRows;i++){
            for(let j = 0; j<this.game._labyrinthe.nbColumns;j++){

                let nouveau = document.createElement("div");
                let pos = new Position(i,j);
                if(this.game._labyrinthe.layerWall.hasTile(pos)){
                    nouveau.classList.add("wall");
                }
                else if(this.game._labyrinthe.layerDot.hasTile(pos)){
                    nouveau.classList.add("gomme");
                    nouveau.id = "d" + String(i) + " " + String(j);
                }
                nouveau.style.position = "absolute";
                let pos_x = j*TILE_SIZE +20;
                let pos_y = i*TILE_SIZE +132;
                if(this.game._labyrinthe.layerDot.hasTile(pos)){
                    pos_x += 4;
                    pos_y += 4;
                }
                nouveau.style.left = pos_x + "px";
    
                nouveau.style.top = pos_y + "px";
                document.getElementById("jeu").appendChild(nouveau);
            }
            
        }

        //spawn de pacman
        
        let pacboy = document.createElement("div");
        pacboy.id = "PacmanView";
        let pos_spawn = this.game._labyrinthe._pacSpawn;
        pacboy.style.position = "absolute";
        pacboy.style.left = pos_spawn._column * TILE_SIZE + 23 + 'px';
        pacboy.style.top = pos_spawn._row  * TILE_SIZE + 134 + 'px';
        pacboy.classList.add("Pacman");
        document.getElementById("jeu").appendChild(pacboy);

        //spawn des fantomes
        for(let i = 0; i<4; i++){
            let fantome = document.createElement("div");
            fantome.id = "ghost" + i;
            fantome.style.position = "absolute";
            fantome.style.left = this.game._labyrinthe._ghostSpawn.column * TILE_SIZE + 22 + 'px';
            fantome.style.top = this.game._labyrinthe._ghostSpawn.row * TILE_SIZE + 134 + 'px';
            if(i==0){
                fantome.classList.add("Blinky");
            }
            else if(i==1){
                fantome.classList.add("Pinky");
            }
            else if(i==2){
                fantome.classList.add("Inky");
            }
            else if(i==3){
                fantome.classList.add("Clyde");
            }
            document.getElementById("jeu").appendChild(fantome);
        }

        for(let i  = 1; i<=this.game._Pacman.nbLives;i++){
            let vie = document.createElement("div");
            vie.classList.add("Life");
            vie.id = "vie" + i;
            vie.style.position = "absolute";
            vie.style.left = 28 + (i-1) * 25 + 'px';
            vie.style.top = 613 + 'px';
            document.getElementById("jeu").appendChild(vie);
        }

    }

    /**
     * Will call the start game function from the controller to start the game
     * when the button is pressed
     * @param {Event} event used to give the controller has a parameter
     */
    startGame(event){
        this.style.visibility = "hidden";

        event.data.controller.startHasBeenRequested();
    }

}