
package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Goes into the database to retreive informations based on what we want
 * @author 56035
 */
public class StationDao implements Dao<StationDTO>{
    
    private String dbUrl;
    private Statement stmt;
    public StationDao(){
        
        try {
            ConfigManager.getInstance().load();
            this.dbUrl = ConfigManager.getInstance().getProperties("db.url");
            System.out.println("Base de données stockée : " + dbUrl);
            connection();


        } catch (IOException ex) {
            System.out.println("Erreur IO " + ex.getMessage());
        }
    }
    @Override
    public StationDTO get(int id){
        return new StationDTO(1,1,1);
    }
    /**
     * Returns the stations
     * @return 
     */
    @Override
    public List<StationDTO> getAll(){
        List<StationDTO> all = new ArrayList();
        try{
            
            
            String query = "SELECT * FROM STOPS";
            ResultSet result = stmt.executeQuery(query);
            while(result.next()){
                System.out.println("id " + result.getInt(1) + " name " + result.getString(2));
                all.add(new StationDTO(result.getInt(1), result.getInt(2), result.getInt(3)));
            }
        } catch (SQLException ex) {
            System.out.println("DEMO_SELECT | Erreur " + ex.getMessage() + " SQLState " + ex.getSQLState());
        }


        
        return all;
    }
    /**
     * Connecting to the database
     */
    public void connection(){
        try{
        Connection connexion = DriverManager.getConnection("jdbc:sqlite:" + this.dbUrl);
        this.stmt = connexion.createStatement();

        } catch (SQLException ex) {
            System.out.println("DEMO_SELECT | Erreur " + ex.getMessage() + " SQLState " + ex.getSQLState());
        }

    }
    
    /**
     * Gets the stations with their name
     * @return 
     */
    public List<StationNameDto> getNames(){
        List<StationNameDto> all = new ArrayList();
        try{
            
            
            String query = "SELECT STATIONS.name, STOPS.id_line, STATIONS.id FROM STOPS JOIN STATIONS ON"
                    + " STOPS.id_station = STATIONS.id";
            ResultSet result = stmt.executeQuery(query);
            while(result.next()){
                boolean found = false;
                for(StationNameDto s : all){
                    if(s.sameName(result.getString(1))){
                        s.getLignes().add(result.getInt(2));
                        found = true;
                    }
                }
                if(!found){
                    ArrayList<Integer> selected = new ArrayList();
                    selected.add(result.getInt(2));
                    all.add(new StationNameDto(selected, result.getString(1), result.getInt(3)));
                    
                }
                
            }
            
            
        } catch (SQLException ex) {
            System.out.println("DEMO_SELECT | Erreur " + ex.getMessage() + " SQLState " + ex.getSQLState());
        }
        return all;
        
    }
    
    /**
     * Makes every lines to know the order of the stations
     * @return 
     */
    public List<List<StationDTO>> makeLines(){
        List<List<StationDTO>> lines = new ArrayList();
        try{
            
            String query = "SELECT id_line, id_station, id_order FROM STOPS";
            ResultSet result = stmt.executeQuery(query);
            while(result.next()){
                boolean found = false;
                for(int i = 0; i<lines.size();i++){
                    if(lines.get(i).get(0).getLine() == result.getInt(1)){
                        found = true;
                        lines.get(i).add(new StationDTO(result.getInt(1), result.getInt(2), result.getInt(3)));
                        
                    }
                }
                if(!found){
                    List<StationDTO> nouveau = new ArrayList();
                    nouveau.add(new StationDTO(result.getInt(1), result.getInt(2), result.getInt(3)));
                    lines.add(nouveau);
                }
            }
        } catch (SQLException ex) {
            System.out.println("DEMO_SELECT | Erreur " + ex.getMessage() + " SQLState " + ex.getSQLState());
        }
        return lines;
    }
}
