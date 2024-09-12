
package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Takes the place of the DAO, goes into the DB to take information and adds informations to it.
 * Called by the model.
 * @author 56035
 */
public class Logs {
    
    private String dbUrl;
    private Statement stmt;
    
    public Logs(){
        try {
            ConfigManager.getInstance().load();
            this.dbUrl = ConfigManager.getInstance().getProperties("db.url");
            System.out.println("Base de données stockée : " + dbUrl);
            connection();
            
            


        } catch (IOException ex) {
            System.out.println("Erreur IO " + ex.getMessage());
        }
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
     * Prints on the terminal everything in the DB
     */
    public void print(){
        
        try{
            
            
            String query = "SELECT * FROM SIMULATION";
            ResultSet result = stmt.executeQuery(query);
            while(result.next()){
                System.out.println("sort " + result.getString(3) + " grandeur " + result.getInt(4));
                
            }
        } catch (SQLException ex) {
            System.out.println("DEMO_SELECT | Erreur " + ex.getMessage() + " SQLState " + ex.getSQLState());
        }
    }
    
    /**
     * Adds something to the DB
     * @param time current time
     * @param sort chosen sort
     * @param size size of the array to sort
     */
    public void addToDB(Timestamp time, String sort, int size){
        try{
            
            String query = "INSERT INTO SIMULATION(timestamp,sort_type,max_size)"
                    + " VALUES('"+time+"','"+sort+"','"+size+"')";
            int result = stmt.executeUpdate(query);
            
        
        } catch (SQLException ex) {
            System.out.println("DEMO_INSERT | Erreur " + ex.getMessage()
            + " SQLState " + ex.getSQLState());
        }

    }
    
    public List<LogsDto> getLogs(){
        List<LogsDto> logs = new ArrayList();
        try{
            
            
            String query = "SELECT * FROM SIMULATION";
            ResultSet result = stmt.executeQuery(query);
            while(result.next()){
                logs.add(new LogsDto(result.getTimestamp(2), result.getString(3), result.getInt(4)));
                
            }
        } catch (SQLException ex) {
            System.out.println("DEMO_SELECT | Erreur " + ex.getMessage() + " SQLState " + ex.getSQLState());
        }
        return logs;
    }
    
}
