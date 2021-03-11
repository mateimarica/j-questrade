import java.io.*;
import java.util.Scanner;
import com.jquestrade.*;
import com.jquestrade.Balances.Currency;
import com.jquestrade.exceptions.*;

/**
 * An example app using jQuestrade. 
 * @author Matei Marica
 * @see <a href="https://github.com/mateimarica/j-questrade/wiki/Using-an-authorization-relay">This tutorial</a>
 * for some more explanation/context on a similar example app.
 */
class ExampleApp {

	// The file that contains the values needed for authorization (refresh token, access token, API server)
	private static final File AUTH_FILE = new File("C:/Users/Public/auth.txt");
	
	public static void main(String[] args) {
		
		// Create a scanner to read from our auth.txt file
		Scanner scan;
		try {
			scan = new Scanner(AUTH_FILE);
		} catch (FileNotFoundException e) {
			System.err.println("Auth file not found");
			e.printStackTrace();
			return;
		}
		
		// Use the first line auth.txt as the refresh token
		Questrade q = new Questrade(
				scan.nextLine(), // refresh token
				scan.nextLine(), // access token
				scan.nextLine()  // API server
		);
		scan.close();

		// Set it so the Authorization is sent to our custom method every time it's updated.
		q.setAuthRelay(auth -> saveAuthorization(auth));
		
		try {
			q.activate();
	    	
			// Test that something is retrieved properly from the Questrade servers.
			System.out.println(q.getTime());
		    
			// Print out the total amount of CAD in the given account
			System.out.println(q.getBalances(q.getAccounts()[0].getNumber()).getPerCurrencyBalances(Currency.CAD).getTotalEquity());
			
			// Make other requests to the Questrade API here . . .
	    	
		} catch (RefreshTokenException e) {
			System.err.println("Invalid refresh token");
			e.printStackTrace();
		}
	    
	}
    
	// Here we save our new refresh token to auth.txt, so we can reuse it next time this app runs
	private static void saveAuthorization(Authorization auth) {
		try {
			// Print the refresh token, access token, and API server to auth.txt
			PrintWriter out = new PrintWriter(AUTH_FILE);
			out.println(auth.getRefreshToken()); 
			out.println(auth.getAccessToken());
			out.println(auth.getApiServer());	
			out.close();
		} catch (FileNotFoundException e) {
			System.err.println("File could not be found");
			e.printStackTrace();
		}
	}
    
}