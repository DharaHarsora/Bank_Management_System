/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comrehensive;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Dhara Harsora
 */
class CheckBook implements java.io.Serializable{
    private int cbn;
//    private int accnumber;
    private String password;
    private ArrayList<Check> c = new ArrayList<Check>(10);
    
    Random rand = new Random();
    int i = 0;
    public CheckBook(String password){
        this.cbn = Math.abs((rand.nextInt() % 1000));
        this.password = password;
        for(int j = 0; j < 10; ++j){
            Check ck = new Check("",0,-1,-1,null,0);
            c.add(ck);
        }
    }
    public int getnum(){
        return cbn;
    }
    public String getpass(){
        return password;
    }
    public void addCheck(Check e){
        c.add(e);
    }
    public ArrayList<Check> allcheck(){
        return c;
    }
    
    LocalDate finddatecheque(String pwd){
        for(Check e: c){
            if(e.getpwd().equals(pwd)){
                return e.getDate();
            }
        }
        return null;
    }
    
    void cancel(String cknum){
        boolean done = false;
        for(Check e: c){
            if(e.getpwd().equals(cknum)){
                e.setDate(null);
                e.setState(0);
                e.setacc(-1);
                e.setaccfromnum(-1);
                e.setmoney(0);
                e.setpwd("");
                done = true;
            }
        }
        if(done == true){
            System.out.println("Succesfully cancel.");
        }else{
            System.out.println("UnsuccessFul.");
        }
    }
    
    void addinfotocheck(Account to, Account from, int money, String pwd, LocalDate dt){
        i++;
        c.get(i).setaccfromnum(from.getaccNumber());
        c.get(i).setacc(to.getaccNumber());
        c.get(i).setmoney(money);
        c.get(i).setpwd(pwd);
        c.get(i).setState(0);
        c.get(i).setDate(dt);
    }
    
    Check findchequeToacc(Account from, Account e, String pwd){
        for(Check ckk: c){
            if(ckk.getFromnum() == from.getaccNumber() && 
                    ckk.getpwd().equals(pwd) == true && ckk.getTonum() == e.getaccNumber()){
                return ckk;
            }
        }
        return null;
    }
    
    void print(){
        for(Check e: c){
            System.out.println("To: " + e.getTonum() + " Money: " + e.getmoney());
        }
    }
    
}
