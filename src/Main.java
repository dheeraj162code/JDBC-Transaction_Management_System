import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception{
        int id;
        double amount,balance;

        while(true){
            System.out.print("\n#########################################################################################");
            System.out.println("\n1.Create Account.\n2.Check Balance\n3.Deposit\n4.Withdraw\n5.Transfer\n6.To get Transaction History\n0.Exit");
            Scanner sc=new Scanner(System.in);
            int option=sc.nextInt();
            if(option==0){
                break;
            }
            switch (option){
                case 1:
                    System.out.print("\nEnter Account Holder Name:");

                    String name=sc.nextLine();

                    AccountController.createAccount(name);
                    break;
                case 2:
                    System.out.print("\nEnter Account id:");
                    id=sc.nextInt();
                    System.out.println("Balance:"+AccountController.getBalance(id));
                    break;
                case 3:
                    System.out.print("\nEnter Account id:");
                    id=sc.nextInt();
                    System.out.print("\nEnter Amount:");
                    amount=sc.nextDouble();
                    balance=AccountController.getBalance(id);
                    balance+=amount;


                    if(AccountController.updateAmount(id,balance)){
                        System.out.print("\n Total balance :"+balance);
                        Transaction.transact(id,"deposit",amount);
                    }

                    break;
                case 4:
                    System.out.print("\nEnter Account id:");
                    id=sc.nextInt();
                    System.out.print("\nEnter Amount:");
                    amount=sc.nextDouble();
                    balance=AccountController.getBalance(id);
                    if(balance<amount){
                        System.out.print("\nLow Balance");
                        break;
                    }
                    balance-=amount;

                    if(AccountController.updateAmount(id,balance)){
                        Transaction.transact(id,"withdraw",-amount);
                        System.out.print("\n Total balance :"+(balance));
                    }

                    break;
                case 5:
                    System.out.print("\nEnter from Account id:");
                    int from=sc.nextInt();
                    System.out.print("\nEnter to Account id:");
                    int to=sc.nextInt();
                    System.out.print("\nEnter the amount:");
                    amount=sc.nextDouble();

                    if(AccountController.transfer(from,to,amount)){
                        System.out.print("\n Transaction Successful");
                        Transaction.transact(from,"transferred to "+to+".",-amount);
                        Transaction.transact(to,"received from"+from+".",amount);
                    }

                    break;
                case 6:
                    System.out.print("\nEnter Account id:");
                    id=sc.nextInt();
                    Transaction.transact_history(id);
                    break;

                default:
                    System.out.println("Entered wrong option");
                    break;

            }
        }




    }
}
