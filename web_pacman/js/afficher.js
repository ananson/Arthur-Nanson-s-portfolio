/**
 * Displays the maze with the dots.
 */
function afficher(){

    for(i = 0; i<RAW_MAZE.table.length;i++){
        for(j = 0; j<RAW_MAZE.table[i].length;j++){
            let nouveau = document.createElement("div");
            let current = RAW_MAZE.table[i][j];
            if(current == 1){
                nouveau.classList.add("wall");
            }
            else if(current == 2){
                nouveau.classList.add("gomme");
            }
            nouveau.style.position = "absolute";
            let pos_x = j*15 +20;
            let pos_y = i*15 +132;
            if(current==2){
                pos_x += 4;
                pos_y += 4;
            }
            nouveau.style.left = pos_x + "px";

            nouveau.style.top = pos_y + "px";
            document.getElementById("jeu").appendChild(nouveau);
        }
        
    }
}