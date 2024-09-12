/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.List;

/**
 *
 * @author 56035
 */
public interface Dao<T> {
    
    
    T get(int item);
    List<T> getAll();
}
