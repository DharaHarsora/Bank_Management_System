    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package comrehensive;

    import java.io.FileWriter;
    import java.io.IOException;
    import java.io.Serializable;
    import java.text.DateFormat;
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.Date;

    /**
     *
    // * @author Dhara Harsora
    // */
    //
    //// Bank ATM, Passbook, Cheque

    public class Bank implements Serializable {
        private ArrayList<Card> atms = new ArrayList();
        private ArrayList<Account> accs = new ArrayList();

        public void addAcc(Account e){
            accs.add(e);
        }

        public void rmAcc(Account e){
            accs.remove(e);
        }

        public int getatmsize(){
            return atms.size();
        }
        public int getaccountssize(){
            return accs.size();
        }

        // Here, CardNumber = AccountNumber
        public Account findAcc(int cardNumber){
            Account ans = null;
            for(Account x: accs){
                if(x.getaccNumber() == cardNumber){
                    ans = x;
                    break;
                }
            }
            return ans;
        }

        public Account findaccbaseonCqnum(int chequenum){
            Account ans;
            for(Account e: accs){
                if(e.getchequeBook() != null){
                    if(e.getchequeBook().getnum() == chequenum){
                        return e;
                    }
                }
            }
            System.out.println("No such Chequebook of This ChequeBook Number");
            return null;
        }

        public void printallAcc(){
            if(accs.size() == 0){
                System.out.println("Empty, No Has account in the bank");
            }
            for(Account e: accs){
                // Balance is private
                // System.out.println(e.getName() + " Has " + e.getbalance() + " Balance.");
                System.out.println("Name: " + e.getName());
            }
            System.out.println();
        }

        public void addtofiledetail(){
            for(Account e : accs){
                try{
                    FileWriter fw = new FileWriter("Account.txt",true);
                    e.print(fw);
                    fw.append("\n");
                    fw.close();
                }catch(IOException ex){
                    System.out.println("IOExcpetion");
                }
            }
        }

        public void printallatms(){
            if(atms.size() == 0){
                System.out.println("Bank has no atm card");
            }
            int i = 0;
            for(Card e: atms){
                System.out.println((i + 1) + ": Account Number " + e.getaccnumber() + " Has ATM num" + e.getCardNumber());
            }
        }

        public Boolean checkCardpwd(int cardNumber, String pwd){
            return findAcc(cardNumber).getpwd().equals(pwd);
        }

        public void pinChange(Account e, String newpwd){
            if(e.getCashCardAssociated() == null){
                System.out.println("This account has no ATM card");
            }

            Card c = e.getCashCardAssociated();
            c.setPwd(newpwd);
        }

        public void deposit(Account e, double bal, String memo, int state){
            e.setBal(bal + e.getbalance());
            e.addtransaction(bal, memo, state);
            System.out.println(bal + " is Deposited to your account succesfully");
        }

        // 500 minbal
        public String withdraw(Account acc, double amt, String memo, int state){

            double currbal = acc.getbalance();
            if(currbal - amt < 500){
                return "UnsuccessFul Withdrawing!! Sorry Minimum 500 Rupee is required Currently you have only" + currbal + "Balance.";
            }
            acc.addtransaction(amt, memo, state);
            boolean b = false;
            {
                acc.setBal(currbal - amt);
                return amt + " is withdraw from your account succesfully";
            }
        }

        public void addATMcard(Account e, String pwd){
            Date curr = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(curr);
            c.add(Calendar.DATE,1);
            Date modified = c.getTime();
            // validate for 2 year;
            DateFormat df = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
            String s = df.format(modified);
            Card card = new Card(e.getaccNumber(),pwd, s);
            e.setCard(card);
            atms.add(card);
        }

        public Card Getcard(int cardNumber){
            for(Card e: atms){
                if(e.getCardNumber() == (cardNumber)){
                    return e;
                }
            }
            return null;
        }
         public Card GetCardFromAccnumber(int accnumber){
            for(Card e: atms){
                if(e.getaccnumber() == accnumber){
                    return e;
                }
            }
            return null;
        }
        public Boolean validation(int cardNumber, String pwd) throws Exception{

            if(Getcard(cardNumber) == null){
                System.out.println("ATM card is not exist");
                return false;
            }
            String expdate = Getcard(cardNumber).getExpirationDate();
            String pd = Getcard(cardNumber).getpassword();
            if(pd.equals(pwd) == false){
                System.out.println("OOPs! ATM password is wrong.");
                return false;
            }
            try{
                if(isExpired(expdate)){
                    return false;
                }else{
                    return true;
                }
            }catch(Exception e){
                System.out.println("Parse Exception" + e.getMessage());
            }
            return false;
        }

        public boolean isExpired(String expirationDate)throws ParseException{
            boolean expired;
            SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
            dateFormat.setLenient(false);
            Date formatedExpiryDate = dateFormat.parse(expirationDate);
            expired = formatedExpiryDate.before(new Date());
            return expired;
        }

        public boolean authenticateCard(int cardNumb,String password){
            Card c = Getcard(cardNumb);
            if(c.getpassword().equals(password) == true){
                return true;
            }
            return false;
        }



    }
