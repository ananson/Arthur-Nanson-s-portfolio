/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Timestamp;

/**
 * A class that takes all we need to know about a log to use it later easily
 * @author 56035
 */
public class LogsDto {
    private Timestamp time;
    private String sort;
    private int size;
    
    public LogsDto(Timestamp t, String sort, int size){
        this.time = t;
        this.sort = sort;
        this.size = size;
    }
    
    public Timestamp getTime(){
        return this.time;
    }
    public String getSort(){
        return this.sort;
    }
    public int getSize(){
        return this.size;
    }
}
