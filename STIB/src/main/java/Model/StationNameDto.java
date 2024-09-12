/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * represents a staion with it's name
 * @author 56035
 */
public class StationNameDto {
    private List<Integer> lignes;
    private String name;
    private int id;
    
    public StationNameDto(ArrayList<Integer> lignes, String name, int id){
        this.lignes = lignes;
        this.name = name;
        this.id = id;
    }
    
    public List<Integer> getLignes(){
        return this.lignes;
    }
    
    public String getName(){
        return this.name;
    }
    
    public boolean equals(StationNameDto other){
        return other.name.equals(this.name);
    }
    
    public boolean sameName(String other){
        return this.name.equals(other);
    }
    
    public int getId(){
        return this.id;
    }
}
