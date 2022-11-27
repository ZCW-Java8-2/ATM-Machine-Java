import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class OptionMenu {
	Scanner menuInput = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");
	ArrayList <Account> users = new ArrayList<>();
	FileWriter logWriter;
	PrintWriter logFile;

	String strDate;

	HashMap<Integer, Account> data = new HashMap<Integer, Account>();

	public void getLogin() throws IOException {
		boolean end = false;
		int customerNumber = 0;
		int pinNumber = 0;
		Account acc;
		while (!end) {
			try {
				System.out.print("\nEnter your customer number: ");
				customerNumber = menuInput.nextInt();
				System.out.print("\nEnter your PIN number: ");
				pinNumber = menuInput.nextInt();
				Iterator it = data.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					acc = (Account) pair.getValue();

					if (data.containsKey(customerNumber) && pinNumber == acc.getPinNumber()) {
						getAccountType(acc);

						end = true;
						break;
					}
				}
				if (!end) {
					System.out.println("\nWrong Customer Number or Pin Number");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Character(s). Only Numbers.");
			}
		}
	}

	public void getAccountType(Account acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nSelect the account you want to access: ");
				System.out.println(" Type 1 - Checkings Account");
				System.out.println(" Type 2 - Savings Account");
				System.out.println(" Type 3 - Show Statement");
				System.out.println(" Type 4 - Log Out");
				System.out.println(" Type 5 - Exit");
				System.out.print("\nChoice: ");

				int selection = menuInput.nextInt();

				switch (selection) {
					case 1:
						getChecking(acc);
						break;
					case 2:
						getSaving(acc);
						break;
					case 3:
						showStatement(acc);
						break;
					case 4:
						try{
							getLogin();
						}catch (IOException e){

						}
						break;
					case 5:
						allAccount();
						end = true;
						break;
					default:
						System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
	}

	public void getChecking(Account acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nCheckings Account: ");
				System.out.println(" Type 1 - View Balance");
				System.out.println(" Type 2 - Withdraw Funds");
				System.out.println(" Type 3 - Deposit Funds");
				System.out.println(" Type 4 - Transfer Funds");
				System.out.println(" Type 5 - Exit");
				System.out.print("\nChoice: ");

				int selection = menuInput.nextInt();

				switch (selection) {
					case 1:
						System.out.println("\nCheckings Account Balance: " + moneyFormat.format(acc.getCheckingBalance()));
						break;
					case 2:
						acc.getCheckingWithdrawInput(logFile);
						break;
					case 3:
						acc.getCheckingDepositInput(logFile);
						break;

					case 4:
						acc.getTransferInput("Checkings", logFile);
						break;
					case 5:
						end = true;
						break;
					default:
						System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
	}

	public void getSaving(Account acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nSavings Account: ");
				System.out.println(" Type 1 - View Balance");
				System.out.println(" Type 2 - Withdraw Funds");
				System.out.println(" Type 3 - Deposit Funds");
				System.out.println(" Type 4 - Transfer Funds");
				System.out.println(" Type 5 - Exit");
				System.out.print("Choice: ");
				int selection = menuInput.nextInt();
				switch (selection) {
					case 1:
						System.out.println("\nSavings Account Balance: " + moneyFormat.format(acc.getSavingBalance()));
						break;
					case 2:
						acc.getsavingWithdrawInput(logFile);
						break;
					case 3:
						acc.getSavingDepositInput(logFile);
						break;
					case 4:
						acc.getTransferInput("Savings", logFile);
						break;
					case 5:
						end = true;
						break;
					default:
						System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
	}

	public void createAccount() throws IOException {
		int cst_no = 0;
		boolean end = false;
		Account newAcc;
		while (!end) {
			try {
				System.out.println("\nEnter your customer number ");
				cst_no = menuInput.nextInt();
				Iterator it = data.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					if (!data.containsKey(cst_no)) {
						end = true;
					}
				}
				if (!end) {
					System.out.println("\nThis customer number is already registered");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		System.out.println("\nEnter PIN to be registered");
		int pin = menuInput.nextInt();
		newAcc = new Account(cst_no, pin);
		data.put(cst_no, newAcc);
		users.add(newAcc);
		System.out.println("\nYour new account has been successfuly registered!");
		System.out.println("\nRedirecting to login.............");
		try{
			logFile.println(""+strDate+" New Account "+newAcc.getCustomerNumber()+" has been successfully registered.");
		}catch(Exception e){}
		getLogin();
	}

	public void mainMenu() throws IOException {

		logWriter = new FileWriter("./log.txt");
		logFile = new PrintWriter(logWriter);

		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		strDate = dateFormat.format(date);

		Account temp = new Account(952141, 191904, 1000, 5000);
		data.put(952141, temp);
		users.add(temp);

		temp = new Account(123, 123, 20000, 50000);
		data.put(123, temp);
		users.add(temp);

		boolean end = false;
		while (!end) {
			try {
				System.out.println("\n Type 1 - Login");
				System.out.println(" Type 2 - Create Account");
				System.out.print("\nChoice: ");
				int choice = menuInput.nextInt();
				switch (choice) {
					case 1:
						getLogin();
						end = true;
						break;
					case 2:
						createAccount();
						end = true;
						break;
					default:
						System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		System.out.println("\nThank You for using this ATM.\n");
		menuInput.close();
		System.exit(0);
	}

	public void showStatement(Account acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println(acc.getStatement());
				System.out.println(" Type 1 - Main menu");
				System.out.println(" Type 2 - Exit");
				System.out.print("\nChoice: ");

				int selection = menuInput.nextInt();

				switch (selection) {
					case 1:
						getAccountType(acc);
						break;
					case 2:
						//end = true;
						allAccount();
						System.exit(0);
						break;
					default:
						System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
	}
	public void allAccount(){
		try {
			FileWriter fileWriter = new FileWriter("./userInfo.txt");
			PrintWriter printWriter = new PrintWriter(fileWriter);
			for (int i = 0; i < users.size(); i++) {
				printWriter.println(users.get(i).getCustomerNumber()+",\t\t\t"+users.get(i).getCheckingBalance()+",\t\t\t"+users.get(i).getSavingBalance());

			}
			printWriter.close();
			logFile.close();
		}catch (Exception e){

		}
	}
}
