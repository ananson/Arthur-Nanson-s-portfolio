/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nanso
 */
public class Model {
    private List<StationDTO> stations;
    private List<List<StationDTO>> lignes;
    private Repository repository;
    
    
    public Model(){
        this.repository = new Repository();
        stations = new ArrayList();
        
        getLines();
        
    }
        
    /**
     * Gives every station a list of their adjacent stations
     */
    public void getLines(){
        this.lignes = this.repository.getLinesStations();
        for(List<StationDTO> l :this.lignes){
            StationDTO last = new StationDTO(-1,-1,-1);
            for(StationDTO d : l){
                boolean found = false;
                
                for(StationDTO d2 : this.stations){
                    if(d.getStation() == d2.getStation()){
                        found = true;
                        if(last.getLine() != -1){
                            d2.addNext(last);
                            last.addNext(d2);
                            last = d2;
                            break;
                        }
                    }
                }
                if(!found){
                    if(last.getLine() != -1){
                        d.addNext(last);
                        last.addNext(d);
                    }
                    
                    this.stations.add(d);
                    last = d;
                    
                }
                
            }
        }

    }
    /**
     * Creates the shortest way for every station for the given start point
     * @param depart 
     */
    public void createShorter(StationDTO depart){
        List<StationDTO> unsettle = new ArrayList();
        List<StationDTO> settle = new ArrayList();
        this.emptyWays();
        for(StationDTO s : this.stations){
            if(depart.getStation() == s.getStation()){
                s.setDistance(0);
            }
            else{
                s.setDistance(Integer.MAX_VALUE);
            }
        }
        unsettle.add(depart);
        while(unsettle.size() >0){
 
                StationDTO current = unsettle.get(0);
                unsettle.remove(current);
                for(StationDTO adj : current.getNext()){
                    
                    if(!findSettle(adj, settle)){
                        minDistance(current, adj);
                        if(!findSettle(adj, unsettle)){
                            unsettle.add(adj);
                        }
                        
                        
                    }
                    
                }
                settle.add(current);
                        
        }
        
    }
    public void emptyWays(){
        for(StationDTO s : this.stations){
            List<StationDTO> nouveau = new ArrayList();
            nouveau.add(s);
            s.setChemin(nouveau);
        }
    }
    /**
     * Updates the minimum distance to reach a station
     * @param current
     * @param evaluate 
     */
    public void minDistance(StationDTO current, StationDTO evaluate){
        
        if(current.getDistance() + 1 < evaluate.getDistance()){
            evaluate.setDistance(current.getDistance()+1);
            List<StationDTO> nouveauMin = new ArrayList();
            for(StationDTO s : current.getChemin()){
                nouveauMin.add(s);
            }
            nouveauMin.add(evaluate);
            evaluate.setChemin(nouveauMin);
        }
    }
    
    public void printShorter(StationDTO s){
        createShorter(s);
        for(StationDTO i : this.stations){
            System.out.println(i.getStation() + " " + i.getDistance() + " " + i.getChemin().size());
        }
    }
    public boolean findSettle(StationDTO search, List<StationDTO> settle){
        
        for(StationDTO s : settle){
            if(s.getStation() == search.getStation()){
                
                return true;
            }
        }
        return false;
    }
    /**
     * Searches for the station we want for the shortest way
     * @param depart
     * @param arrivee
     * @return 
     */
    public List<StationNameDto> makeWays(String depart, String arrivee){
        int depart_id = 0;
        int arrivee_id = 0;
        for(StationNameDto s : this.getNames()){

            if(depart.equals(s.getName())){


                depart_id = s.getId();
            }
            if(arrivee.equals(s.getName())){
                arrivee_id  = s.getId();
            }
        }
        List<StationDTO> shortest = new ArrayList();
        StationDTO departDto = null;
        StationDTO arriveeDto = null;
        int found = 0;
        for(StationDTO s : this.getStations()){
            if(depart_id == s.getStation()){
                departDto = s;
                found++;
            }
            if(arrivee_id == s.getStation()){
                arriveeDto = s;
                found++;
            }
        }
        this.createShorter(departDto);
        if(found<2){
            List<StationNameDto> notFound = new ArrayList();
            return notFound;
        }
        shortest = arriveeDto.getChemin();
        List<StationNameDto> shortestName = new ArrayList();
        for(StationDTO s : shortest){
            for(StationNameDto n : this.getNames()){
                if(s.getStation() == n.getId()){
                    shortestName.add(n);
                }
            }
        }
        return shortestName;
    }
    public List<StationNameDto> getNames(){
        return this.repository.getNames();
    }
    public List<StationDTO> getStations(){
        return this.stations;
    }
}
