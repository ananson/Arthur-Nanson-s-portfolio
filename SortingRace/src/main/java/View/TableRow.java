/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

/**
 * Used to put it in a tableView
 * @author 56035
 */
public class TableRow {
    
    private String tri;
    private int taille;
    private int operation;
    private int duree;
    public TableRow(String tri, int taille, int operation, int duree){
        this.tri = tri;
        this.taille = taille;
        this.operation = operation;
        this.duree = duree;
    }
    
    public String getTri(){
        return this.tri;
        
    }
    
    public int getTaille(){
        return this.taille;
    }
    
    public int getOperation(){
        return this.operation;
    }
    
    public int getDuree(){
        return this.duree;
    }
}
