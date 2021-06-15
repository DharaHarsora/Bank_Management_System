/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comrehensive;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;


/**
 *
 * @author Dhara Harsora
 */
public class Comrehensive {

    /**
     * @param args the command line arguments
     */
    
    public static void printmenu(){
        System.out.println("\n1. Login In Bank");
        System.out.println("2. Login In ATM");
        System.out.println("3. CheQue Book");
        System.out.println("4. Admistrator"); 
        System.out.println("5. Exit");
        System.out.println("\nEnter Choice: ");
    }
    
    public static void loginmenuofatm(){
        System.out.println("\n-----Welcome to ATM--------------\n"
                         + "1. WithDraw");
        System.out.println("2. Deposit");
        System.out.println("3. Pin Change\n------------------------------------\n");
    }
    
    public static void loginmenuofbank(){
        System.out.println("\n-----Welcome User In Bank-----\n"
                         + "1. Withdraw");
        System.out.println("2. Deposit");
        System.out.println("3. Balance enquiry/ Check Current Balance");
        // For Passbook
        System.out.println("4. Passbook / print History\n------------------------------------\n");
    }
    
    public static void adminmenu(){
        System.out.println("\n-----Welcome to admin portal---------\n"
                + "1. Add account");
        System.out.println("2. Remove Account");
        System.out.println("3. Display all Accounts");
        System.out.println("4. Get ATM Card");
        System.out.println("5. Get CheQue Book");
        System.out.println("6. Print all ATMs\n------------------------------------\n");
    }
    
    public static void printchequemenu(){
        System.out.println("\n-----Welcome to Cheque Book-------\n"
                + "1. Issue Cheque \n"
                        + "2. Submit Cheque \n"
                        + "3. Cancel Cheque \n"
                        + "4. Print all cheques\n------------------------------------\n" 
                        + "\nEnter Choice: ");
    }
    
    public static int getNumber() throws NagativeAmount{
        Scanner sc = new Scanner(System.in);
        int maybeaccoratm = 0;
        boolean flag = true;
        
        try{
            maybeaccoratm = Integer.parseInt(sc.next());
            if(maybeaccoratm < 0){
                throw new NagativeAmount("Not be Nagative");
            }
        }
        catch(NagativeAmount ng){
            System.out.println(ng.toString());
        }
        catch(NumberFormatException nfe){
            flag = false;
            System.out.println("Must be an Integer");
        }
        while(flag == false){
            try{
                System.out.println("Enter Again: ");
                flag = true;
                maybeaccoratm = Integer.parseInt(sc.next());
            }catch(NumberFormatException nfe){
                flag = false;
                System.out.println("Must Be An Integer!!");
            }
        }
        return maybeaccoratm;
    }
    
    public static void loginfunofbank(Account e, int ch, Bank bank){
        Scanner sc = new Scanner(System.in);
        if(ch == 1){
            // In bank Withdraw
            int cn;
            double amt = 0;
           
            boolean temp = true;
//            try{
                System.out.println("Enter Amount: ");
//                amt = Integer.parseInt(sc.next());
//            }catch(NumberFormatException ex){
//                temp = false;
//                System.out.println("Number Format Exception must be Integer");
//            }

            try {
                amt = getNumber();
            } catch (NagativeAmount ex) {
                System.out.println("Can't be nagative");
            }
            System.out.println("Enter Memo Which you wanna remember: ");
            String memo = sc.next();
            
            String s = bank.withdraw(e, amt, memo, 2);
            System.out.println(s);
        }
        else if(ch == 2){
            // Deposit
            int cn, bal = 0;
            System.out.println("How many Amount: ");
            try {
                bal = getNumber();
            } catch (NagativeAmount ex) {
                System.out.println("Exception Arises");
            }

            String s;
            System.out.println("Enter memo which you wanna remember: ");
            s = sc.next();
            bank.deposit(e, bal, s, 1);
        }

        else if(ch == 3){
            // Current balance 
            double ans = e.getbalance();
            System.out.println("Your balance is: " + ans);
        }

        else if(ch == 4){
            e.printTransHistory();
        }
    }
    
    public static void loginfunatm(Card card, int ch, Bank bank){
        Scanner sc = new Scanner(System.in);
        int accnum = card.getaccnumber();
        Account e = bank.findAcc(accnum);
        if(ch == 1){
            int cn = e.getCashCardAssociated().getCardNumber();
            String pwd = e.getCashCardAssociated().getpassword();
            try{
                boolean aa = bank.validation(cn,pwd);
//                    boolean bb = bank.authenticateCard(cn, pwd);
                if(aa == true){
                    System.out.println("Authorization is accepted. Start transaction by entering the amount of withdraw.");
                    System.out.println("Enter amount which you wonna borrow: ");
                    int amt = Integer.parseInt(sc.next());
                    System.out.println("Enter Memo: ");
                    String memo = sc.next();
                    String s = bank.withdraw(e, amt, memo, 2);
                    System.out.println(s);
                }else{
                    System.out.println("Not valid");
                }
            }
            catch(Exception ex){
                System.out.println(e);
            }
        }

        else if(ch == 2){
            // deposit
            int cn = e.getCashCardAssociated().getCardNumber();
            String pwd = e.getCashCardAssociated().getpassword();
            
            try{
                boolean aa = bank.validation(cn,pwd);
                if(aa == true){
                    System.out.println("Authorization is accepted. Start transaction by entering the amount of withdraw.");
                    System.out.println("Enter amount which you wonna Deposit: ");
                    int amt = getNumber();
                    System.out.println("Enter Memo: ");
                    String memo = sc.next();
                    bank.deposit(e, amt, memo, 1);
                }else{
                    System.out.println("Not valid");
                }
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        }

        else if(ch == 3){
            // Pin Change
            String oldpwd, newpwd;
            int atmnum = e.getCashCardAssociated().getCardNumber();
            oldpwd = bank.Getcard(atmnum).getpassword();
            try{
                boolean aa = bank.validation(atmnum,oldpwd);
                if(aa == true){
                    System.out.println("Enter New Password: ");
                    newpwd = sc.next();
                    bank.pinChange(e, newpwd);
                }
            }catch(Exception ex){
                System.out.println("Not validate");
            }
        }
    }
    
    public static void adminfun(int ch, Bank bank) throws NagativeAmount{
        Scanner sc = new Scanner(System.in);
        if(ch == 1){
            // Add Account
            int bal = 0;
            String name, pwd;
            int rd = bank.getaccountssize();
            System.out.println("Your Account Number is: " + (rd+1));
            System.out.println("Enter Name: ");
            name = sc.next();
            System.out.println("Enter pwd: ");
            pwd = sc.next();
            boolean tempf = false;
            try{
                System.out.print("Enter Initial cash which u wanna pay for all acoount min bal required is 500: ");
                bal = Integer.parseInt(sc.next());
            }catch(NumberFormatException e){
                System.out.println("Must be integer");
                tempf = true;
            }
            if(tempf == true){
                boolean flag = false;
                while(flag == false){
                    try{
                        while(bal < 500){
                            System.out.print("PLZ Enter Amount greater than or equal to minimum balance: ");
                            bal = Integer.parseInt(sc.next());
                            flag = true;
                        }
                    }catch(NumberFormatException ex){
                        flag = false;
                        System.out.println("Must be integer / Not be nagative");
                    }
                }
            }
            if(bal < 500){
                boolean flag = false;
                while(flag == false){
                    try{
                        while(bal < 500 || bal < 0){
                            System.out.print("PLZ Enter Amount greater than or equal to minimum balance: ");
                            bal = Integer.parseInt(sc.next());
                            flag = true;
                        }
                    }catch(NumberFormatException  ex){
                        flag = false;
                        System.out.println("Must be integer");
                    }
                }
            }
            
            Account a = new Account(rd + 1, pwd, bal,name, null, null);
            bank.addAcc(a);
            System.out.println("SuccessFully Created With Name: " + a.getName() + " and Account Number: " + a.getaccNumber());
        } 
        
        else if(ch == 2){
            System.out.println("Enter your AccountNumber which u wonna delete: ");
            int accnum = 0;
            try {
                accnum = getNumber();
            } catch (NagativeAmount ex) {
                System.out.println("Exception Arises");
            }
            Account del = bank.findAcc(accnum);
            while(del == null){
                System.out.println("No such Account Exist\nEnter Account number: ");
                accnum = getNumber();
                del = bank.findAcc(accnum);
            }
            
            System.out.print("Enter Password: ");
            String pwd = sc.next();
            while(pwd.equals(del.getpwd()) == false){
                System.out.println("Wrong Password");
                System.out.print("Plz enter right Enter Password: ");
                pwd = sc.next();
            }
            bank.rmAcc(del);
            System.out.println("SuccesFully Removed");
        }

        else if(ch == 3){
            // Print all Accounts
            bank.printallAcc();
        }
        
        else if(ch == 4){
                // Add ATM
            Account e = null;
            boolean temp = true;
            int an = -1;
            do{
                try{    
                    System.out.println("Enter Account Number: ");
                    an = Integer.parseInt(sc.next());

                    e = bank.findAcc(an);
                    if(e == null){
                        System.out.println("No such Account");
                        continue;
                    }else{
                        temp = true;
                    }

                }catch(NumberFormatException ex){
                    System.out.println("Must be an int");
                    temp = false;
                }
            }while(temp == false); 


            System.out.println("Enter Password of Bank: ");
            String pwdb = sc.next();

            while(pwdb.equals(e.getpwd()) == false){
                System.out.println("Wrong Password");
                System.out.print("Plz enter right Enter Password: ");
                pwdb = sc.next();
            }
            if(e.getCashCardAssociated() != null){
                System.out.println("You already have ATM Card.");
                return;
            }

            System.out.println("Enter Password Of ATM Card: ");
            String pwd = sc.next();

            bank.addATMcard(e, pwd);

            int ans = bank.GetCardFromAccnumber(an).getCardNumber();
            System.out.println("Your ATM number is: " + ans);
        }
        
        else if(ch == 5){
            Account e = null;
            boolean temp = true;
            int an = -1;
            do{
                try{    
                    System.out.println("Enter Account Number: ");
                    an = Integer.parseInt(sc.next());

                    e = bank.findAcc(an);
                    if(e == null){
                        System.out.println("No such Account");
                        continue;
                    }else{
                        temp = true;
                    }

                }catch(NumberFormatException ex){
                    System.out.println("Must be an int");
                    temp = false;
                }
            }while(temp == false); 
            
            if(e.getchequeBook() != null){
                System.out.println("You already have Cheque Book.");
            }else{
                System.out.println("Enter Password for cheque book: ");
                String pwd = sc.next();
                
                CheckBook c = new CheckBook(pwd);
                e.setCB(c);
                
                int cknum = e.getchequeBook().getnum();
                System.out.println("Your chequeBook Number is: " + cknum);
            }
        }
        
        else if(ch == 6){
            bank.printallatms();
        }
    }
    
    public static void cheques(int choice, Bank bank){
        Scanner sc = new Scanner(System.in);
        if(choice == 1){
            Account from;
            boolean temp = true;
            do{
                System.out.println("Enter Cheque Book Number: ");
                int chequebooknum = 0;
                try {
                    chequebooknum = getNumber();
                } catch (NagativeAmount ex) {
                    System.out.println("Exception Arises");
                }

                from = bank.findaccbaseonCqnum(chequebooknum);
                if(from == null){
                    temp = false;
                    return;
                }
            }while(temp == false);

            
            System.out.println("Enter ChequeBook Password: ");
            String chequebookpwd = sc.next();

            boolean done = from.authenticatechequebook(chequebookpwd);
            if(done == false){
                System.out.println("Password is wrong.");
                return;
            }else{
                System.out.println("Authenticate SuccessFully.");
            }
            
            boolean conti = false, no = false;
            Account to;
            do{
                System.out.println("In which Account number you want to give cheque: ");
                int acctonum = sc.nextInt();

                to = bank.findAcc(acctonum);
                char tmp = 'y';
                if(to == null){
                    System.out.println("No such Account Exist.\nDo you want to enter Again(y/n): ");
                    tmp = sc.next().charAt(0);
                    if(tmp == 'y'){
                        conti = true;
                    }
                    else{
                        conti = false;
                        no = true;
                    }
                }else{
                    conti = false;
                }
            }while(conti == true);

            if(no == true){
                return;
            }

            System.out.println("Enter Money: ");
            int money = 0;
            try {
                money = getNumber();
            } catch (NagativeAmount ex) {
                System.out.println("Exception Arises");
            }

            System.out.println("Enter Current cheque password: ");
            String pwd = sc.next();

            System.out.println("Enter Date and Month (for which time cheque is valid !!! it Must within Year)");
            int d = -1,m = -1;
            // aa date avi date ke jyar pachi user paisa upadi sake
            System.out.println("Date of cheque ");
            try {
                d = getNumber();
            } catch (NagativeAmount ex) {
                System.out.println("Exception Arises");
            }
            
            System.out.println("Enter Month of cheque: ");
            try {
                m = getNumber();
            } catch (NagativeAmount ex) {
                System.out.println("Exception Arises");
            }
            LocalDate currentdate = LocalDate.now();
            LocalDate dt = LocalDate.of(currentdate.getYear(), m ,d); 
            
            from.issueCheque(to, money, pwd, dt);
            
        }else if(choice == 2){
            // bank mathi leva jay to manas
            boolean temp = true;
            Account e;
            do{
                System.out.println("Enter Acc Number From: ");
                int accnum = 0;
                try {
                    accnum = getNumber();
                } catch (NagativeAmount ex) {
                    System.out.println(ex.toString());
                }

                e = bank.findAcc(accnum);
                
                if(e == null){
                    temp = false;
                }
            }while(temp == false);
            
            System.out.println("Enter Password of cheque: ");
            String s = sc.next();

            boolean temp1 = true;
            Account e1;
            do{
                System.out.println("Enter Account To: ");
                int accnum = 0;
                try {
                    accnum = getNumber();
                } catch (NagativeAmount ex) {
                    System.out.println("Exception Arises");
                }

                e1 = bank.findAcc(accnum);
                
                if(e1 == null){
                    temp1 = false;
                }
            }while(temp1 == false);

            Check c = e.findcheckFromToacc(e1, s);
            
            if(c == null){
                return;
            }
            LocalDate d = c.getDate();
            LocalDate fromdate = d;
            LocalDate currentdate = LocalDate.now();
            LocalDate todate = LocalDate.of(currentdate.getYear(), currentdate.getMonth(), currentdate.getDayOfMonth());
            // 23 -> from
            // 22 -> cur - to
            Period period = Period.between(todate, fromdate);
            
            if(period.getMonths() > 0 || period.getDays() > 0){
                System.out.println("You can't able to upad bcoz from acc e tamne date aapi ana karta vaela 6o tame.");
                return;
            }
            
            e.submitCheque(c, "via cheque withdraw", "via Cheque deposit", bank);
            
        }else if(choice == 3){
            // cancel cheque
            System.out.println("Enter Cheque Book Number: ");
            int chequebooknum = 0;
            try {
                chequebooknum = getNumber();
            } catch (NagativeAmount ex) {
                System.out.println("Exception Arises");
            }
            
            Account from = bank.findaccbaseonCqnum(chequebooknum);
            if(from == null){
                return;
            }
            
            System.out.println("Enter Check PassWord: ");
            String pwd = sc.next();
//            
            LocalDate d = from.getdatefromacc(pwd);

            LocalDate fromdate = d;
            LocalDate currentdate = LocalDate.now();
            LocalDate todate = LocalDate.of(currentdate.getYear(), currentdate.getMonth(), currentdate.getDayOfMonth());

            Period period = Period.between(todate, fromdate);
            
            if(period.getMonths() >= 0 && period.getDays() >= 0){
                System.out.println("Check Was expired you can't cancel.");
                return;
            }
            
            from.cancelation(pwd);
        }else if(choice == 4){
            boolean temp = true;
            Account e;
            do{
                System.out.println("Enter Acc Number");
                int accnum = 0;
                try {
                    accnum = getNumber();
                } catch (NagativeAmount ex) {
                    System.out.println("Exception Arises");
                }

                e = bank.findAcc(accnum);
                
                if(e == null){
                    temp = false;
                }
            }while(temp == false);
            e.printallcheque();
        }

    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        System.out.println("---------------------------------------------------------------\n"
                            + "\t\tWelcome to Kalupur Bank"
                            + "\n------------------------------------------------------------");
        Boolean b = true;
        int ch = 0;
        Bank bank = new Bank();
        
        // Load data
        try{
            File newFile = new File("data.txt");
            if(newFile.length() != 0){
                FileInputStream fis = new FileInputStream("data.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);
                bank = (Bank)ois.readObject();
            }
        }
        catch(ClassNotFoundException e){
            System.out.println("Class Not Found");
        }
        catch(IOException e){
            System.out.print(e);
        }

        while(b){
            printmenu();
            try{
                ch = Integer.parseInt(sc.next());
            }catch(NumberFormatException e){
                System.out.println("Mismatch");
                continue;
            }
            if(ch == 1){
                // login
                char c = 'n';

                int an;
                Account e = null;
                do{
                    System.out.println("Enter Account number: ");
                    an = sc.nextInt();
                    e = bank.findAcc(an);
                }while(e == null);

                System.out.println("Enter Password: ");
                String pwd = sc.next();

                while(pwd.equals(e.getpwd()) == false){
                    System.out.println("Wrong Password");
                    System.out.print("Plz Again Enter Password: ");
                    pwd = sc.next();
                }
                do{
                    loginmenuofbank();
                    System.out.println("Enter choice: ");
                    try {
                        ch = getNumber();
                    } catch (NagativeAmount ex) {
                        System.out.println("Exception Arises");
                    }
                    loginfunofbank(e, ch, bank);
                    System.out.println("\nDo you wanna continue(y/n): ");
                    c = sc.next().charAt(0);
                    System.out.println();
                }while(c == 'y');
            }
            else if(ch == 2){
                char c = 'n';
                int an;
                Account e = null;
                System.out.println("Enter ATM number: ");
                an = sc.nextInt();
                Card card = null;

                card = bank.Getcard(an);

                if(card == null){
                    System.out.println("ATM not exist\n");
                    continue;
                }

                String pwd;
                System.out.println("Enter Password of ATM: ");
                pwd = sc.next();
                while(card.getpassword().equals(pwd) == false){
                    System.out.print("Wrong password Enter again: ");
                    pwd = sc.next();
                }

                do{
                    loginmenuofatm();
                    System.out.println("\nEnter choice: ");
                    try {
                        ch = getNumber();
                    } catch (NagativeAmount ex) {
                        System.out.println("Exception Arises");
                    }
                    loginfunatm(card, ch, bank);
                    System.out.println("\nDo you wanna continue(y/n): ");
                    c = sc.next().charAt(0);
                    System.out.println();
                }while(c == 'y');
            }
            else if(ch == 3){
                char c = 'n';
                do{
                    printchequemenu();
                    int choice = -1;
                    try {
                        choice = getNumber();
                    } catch (NagativeAmount ex) {
                        System.out.println("Exception Arises");
                    }

                    cheques(choice, bank);
                    System.out.print("\nDo you wanna continue(y/n): ");
                    c = sc.next().charAt(0);
                    System.out.println();
                }while(c == 'y');
            }
            else if(ch == 4){
                char c = 'n';
                
                String pwd = "";
                boolean notdone = false;
                while(pwd.equals("Kalpur") == false){
                    System.out.println("Enter Password: ");
                    pwd = sc.next();
                    if(pwd.equals("Kalpur")){
                        break;
                    }
                    System.out.println("Wrong Password plz Enter Again(y/n)");
                    c = sc.next().charAt(0);
                    if(c == 'y'){
                        continue;
                    }else{
                        notdone = true;
                        break;
                    }
                }
                if(notdone == true){
                    continue;
                }
                
                do{
                    adminmenu();
                    System.out.println("\nEnter choice: ");
                    try {
                        ch = getNumber();
                        adminfun(ch, bank);
                    } catch (NagativeAmount ex) {
                        System.out.println("Exception Arises");
                    }
                    System.out.println("\nDo you wanna continue(y/n): ");
                    c = sc.next().charAt(0);
                    System.out.println();
                }while(c == 'y');
            }
            
            else if(ch == 5){
                b = false;
            }
            else{
                System.out.println("Not in choice.");
            }
        }
        
        // Save data()
        try {
            FileOutputStream fileOut = new FileOutputStream("data.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(bank);
            out.close();
            fileOut.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        {
            try
            { 
                Files.deleteIfExists(Paths.get("Account.txt")); 
            } 
            catch(NoSuchFileException e) 
            { 
                System.out.println("No such file/directory exists"); 
            } 
            catch(IOException e) 
            { 
                System.out.println("Invalid permissions"); 
            } 
            
            try {
                File myObj = new File("Account.txt");
                if (!myObj.createNewFile()){
                  System.out.println("File already exists.");
                }
            }catch (IOException e) {
                System.out.println("An error occurred.");
            }
        }
        bank.addtofiledetail();
    }
}
