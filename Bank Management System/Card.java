/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comrehensive;

import java.util.Random;

/**
 *
 * @author Dhara Harsora
 */

public class Card implements java.io.Serializable{
    // cardNumber = ATM Number
    
    private int cardNumber;
    private int accnumber;
    private String password;
    private String expirationDate;
    Random rand = new Random();
    public Card(int cardNumber, String password, String expirationDate){
        this.cardNumber = Math.abs(cardNumber + (rand.nextInt() % 1000));
        accnumber = cardNumber;
        this.password = password;
        this.expirationDate = expirationDate;
    }
    
    public String toString(){
        return " CardNumber: " + cardNumber + " Expiration Date: " + expirationDate;
    }
    
    public int getCardNumber(){
        return cardNumber;
    }
    
    public int getaccnumber(){
        return accnumber;
    }
    
    public String getpassword(){
        return password;
    }
    
    public String getExpirationDate(){
        return expirationDate;
    } 
    
    public void setPwd(String real){
        password = real;
    }
}
