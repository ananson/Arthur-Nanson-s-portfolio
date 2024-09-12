
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents what we need to store for a given station
 * @author 56035
 */
public class StationDTO {
    
    private int id_line;
    private int id_station;
    private int id_order;
    private List<StationDTO> nextTo;
    private int distance;
    private List<StationDTO> chemin;
    
    public StationDTO(int line, int station, int order){
        
        this.id_line = line;
        this.id_order = order;
        this.id_station = station;
        this.nextTo = new ArrayList();
    }
    
    public int getLine(){
        return this.id_line;
    }
    
    public int getOrder(){
        return this.id_order;
    }
    
    public int getStation(){
        return this.id_station;
    }
    
    public void addNext(StationDTO next){
        this.nextTo.add(next);
    }
    
    public List<StationDTO> getNext(){
        return this.nextTo;
    }
    
    public int getDistance(){
        return this.distance;
    }
    
    public void setDistance(int dist){
        this.distance = dist;
    }
    
    public List<StationDTO> getChemin(){
        return this.chemin;
    }
    
    public void setChemin(List<StationDTO> nouveau){
        this.chemin = nouveau;
    }
}
