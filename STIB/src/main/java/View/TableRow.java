
package View;

import java.util.List;

/**
 *
 * @author 56035
 */
public class TableRow {
    
    private String name;
    private List<Integer> stops;
    
    public TableRow(String name, List<Integer> stops){
        this.name = name;
        this.stops = stops;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getStops(){
        return this.stops.toString();
    }
}
