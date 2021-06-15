/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editonumr.
 */
package comrehensive;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Dhara Harsora
 */
class Check implements java.io.Serializable{
    String pwd;
    int money;
    LocalDate date; // Validation date of cheque
    int fromnum;
    int tonum; 
    int state;
    Check(String pwd, int money, int fromnum, int tonum, LocalDate date, int state){
        this.pwd = pwd;
        this.tonum = tonum;
        this.fromnum = fromnum;
        this.money = money;
        this.date = date;
        this.state = state;
    }
    void setState(int states){
        this.state = states;
    }
    
    int getState(){
        return state;
    }
    
    
    void setDate(LocalDate dt){
        date = dt;
    }
    int getFromnum(){
        return fromnum;
    }
    int getTonum(){
        return tonum;
    }
    String getpwd(){
        return pwd;
    }
    LocalDate getDate(){
        return date;
    }
    int getmoney(){
        return money;
    }
    int getAcc(){
        return tonum;
    }
    void setaccfromnum(int fromnum){
        this.fromnum = fromnum;
    }
    void setacc(int tonumnum){
        this.tonum = tonumnum;
    }
    void setmoney(int money){
        this.money =  money;
    }
    void setpwd(String pwd){
        this.pwd = pwd;
    }
}
