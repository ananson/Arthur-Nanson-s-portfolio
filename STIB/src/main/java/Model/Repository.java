
package Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Takes data and calls the DAO to take the wanted date and DTOs to store it
 * @author 56035
 */
public class Repository{
    
    private StationDao dao;

    public Repository(){
        this.dao = new StationDao();
    }
    

    
    public List<StationNameDto> getNames(){
        return this.dao.getNames();
    }
    public List<List<StationDTO>> getLinesStations(){
        return this.dao.makeLines();
    }



}
