
package Presenter;

import Model.Model;
import View.FxmlController;
import Model.StationNameDto;
import java.util.List;

/**
 *
 * @author 56035
 */
public class Presenter {
    private FxmlController view;
    private Model model;
    
    public Presenter(FxmlController view){
        
        this.view = view;
        this.model = new Model();
        //this.model.run();
        
    }
    
    public List<StationNameDto> getNames(){
        return model.getNames();
    }
    
    public List<StationNameDto> makeWays(String depart, String arrivee){
        return this.model.makeWays(depart, arrivee);
    }
}
