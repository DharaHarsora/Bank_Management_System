/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comrehensive;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 *
 * @author Dhara Harsora
 */
public class Account implements java.io.Serializable{
    private Card c;
    private String pwd;
    private double balance;
    private int accNumber;
    private String name;
    private ArrayList<Transaction> transaction = new ArrayList();
    private CheckBook ck;
    // accnum, pwd, balance, name, card
    public Account(int accnum, String pwd, double initdepo, String nm, Card c, CheckBook ck){
        this.c = c;
        this.pwd = pwd;
        name = nm;
        accNumber = accnum;
        balance = initdepo;
        this.ck = ck;
    }
    public void setTransaction(ArrayList<Transaction> transaction) {
        this.transaction = transaction;
    }
    
    void print(FileWriter fw) throws IOException{
        fw.write("Name: " + name + " Account Number: " + accNumber + " Balance: " + balance);
        if(c != null){
            fw.write(c.toString());
        }
    }
    
    public void printTransHistory() {
        System.out.print("\nTransaction History for Account\n");
        if(this.transaction.size() == 0 ) {
                System.out.println("\nThere is No Transaction History for this Account.\n\n ");
        }
        int remainingrupee = 0;
        for ( int t = this.transaction.size()-1 ; t >=0 ; t--) {
            System.out.println(this.transaction.get(t).getSumaryLine());
            // remainingrupee += this.transaction.get(t).
        }
        System.out.println("You Have Balance: " + this.getbalance());
        System.out.println();
    }
    
    public void addtransaction(double money, String memo, int state) {
        Transaction trans = new Transaction(money, memo , this, state);
        transaction.add(trans);
    }
    
    public boolean authenticatechequebook(String pwd){
        if(this.getchequeBook().getpass().equals(pwd)){
            return true;
        }
        return false;
    }
    
    void printallcheque(){
        ck.print();
    }
    
    Check findcheckFromToacc(Account to, String pwd){
        if(ck == null){
            System.out.println("ChequeBook not exist");
            return null;
        }
        Check ckk = ck.findchequeToacc(this,to,pwd);
        if(ckk == null){
            System.out.println("This cheque is not exitst");
            return null;
        }
        if(ckk.getState() == 1){
            System.out.println("This cheque is already issued");
            return null;
        }
        return ckk;
    }
    
    void issueCheque(Account to, int money, String pwd, LocalDate dt){
        
        ck.addinfotocheck(to, this, money, pwd, dt);
        
    }
    
    void submitCheque(Check c, String memo, String memo1, Bank bank){
        String s = bank.withdraw(this, c.getmoney(), memo, 2);
        System.out.println(s);
        if(this.getbalance() - c.getmoney() < 500){
            System.out.println("Cheque Return!!");
            return;
        }
        int accnum = c.getAcc();
        
        Account e1 = bank.findAcc(accnum);
        bank.deposit(e1, c.getmoney(), memo1, 1);
        
        Check temp = ck.findchequeToacc(this, e1, c.getpwd());
        temp.setState(1);
    }
    
    void cancelation(String ckno){
        if(ck == null){
            System.out.println("No checkBook");
            return;
        }
        ck.cancel(ckno);
    }
    
    LocalDate getdatefromacc(String pwd){
        LocalDate d = ck.finddatecheque(pwd);
        return d;
    }
    
    public Card getCashCardAssociated() {
        return c;
    }
    public void setCB(CheckBook chequebook){
        ck = chequebook;
    }
    public void setCard(Card card){
        c = card;
    }
    public CheckBook getchequeBook(){
        return ck;
    }
    public int getaccNumber(){
        return accNumber;
    }
    
    public String getName(){
        return name;
    }
    
    public String getpwd(){
        return pwd;
    }
    
    public double getbalance(){
        return balance;
    }
    
    public void setBal(double balance){
        this.balance = balance;
    }
    
}
