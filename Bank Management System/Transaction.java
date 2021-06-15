/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comrehensive;

/**
 *
 * @author Dhara Harsora
 */

import java.util.Date;


public class Transaction implements java.io.Serializable{
	
	private double amount ;
	//for what this transfer
	private String memo ;
	private Date transferHour ;
	private Account inAccount ;
        private int _State;
	public Transaction(double amount, String memo, Account inAccount, int _State) {
		this.amount = amount;
		this.memo = memo;
		this.transferHour = new Date();
		this.inAccount = inAccount;
                this._State = _State;
	}

	public double getAmount() {
		return amount;
	}

	public String getMemo() {
		return memo;
	}
        
	public Date getTransferHour() {
		return transferHour;
	}

	public Account getInAccount() {
		return inAccount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
        
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setTransferHour(Date transferHour) {
		this.transferHour = transferHour;
	}

	public void setInAccount(Account inAccount) {
		this.inAccount = inAccount;
	}
	
	public String getSumaryLine() {
            if(_State == 1){
                return String.format("%s : %.02f :  Deposit : %s", this.transferHour.toString() , this.amount , this.memo);
            }else{
                return String.format("%s : %.02f :  Withdraw : %s", this.transferHour.toString() , this.amount , this.memo);
            }
	}
}
