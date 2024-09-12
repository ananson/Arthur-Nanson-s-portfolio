/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Model.Model;
import Model.Repository;
import java.util.List;
import Model.StationNameDto;
/**
 *
 * @author nanso
 */
public class TestModel {
    //@Mock
    private Model mock;
    public TestModel() {
        this.mock = new Model();
    }
    
    @BeforeAll
    public static void setUpClass() {
        List<StationNameDto> result;
        //Mockito.lenient().when(mock.getNames()).thenReturn(result);

    }
    
    //@Test 
    public void modelGoodCall(){
        assertEquals(mock.getNames(),0);
        //Mockito.verify(mock, times(1)).getNames();

    }


    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
