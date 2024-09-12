
package View;

/**
 *
 * @author 56035
 */
public class TableWay {
    private int index;
    private String name;
    
    TableWay(int index, String name){
        this.index = index;
        this.name = name;
    }
    
    public int getIndex(){
        return this.index;
    }
    public String getName(){
        return this.name;
    }
}
