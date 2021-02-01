package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import com.jquestrade.Account;
import com.jquestrade.Authorization;
import com.jquestrade.Balances;
import com.jquestrade.Balances.Currency;
import com.jquestrade.Position;
import com.jquestrade.QuestradeAPI;
import com.jquestrade.exceptions.RefreshTokenException;

class Test {

	public static void main(String[] args) throws FileNotFoundException {
		
		String refreshToken = new Scanner(new File("C:\\Users\\Public\\token.txt")).nextLine();

		QuestradeAPI q = new QuestradeAPI(refreshToken);
		q.setAuthRelay(authorization -> saveToDatabase(authorization));

		try {
			q.activate();
			System.out.println(q.getAuthorization().getApiServer());
			Account[] accounts = q.getAccounts();
	
			Balances balances = q.getBalances(accounts[0].getNumber());
			
			System.out.println(balances.getPerCurrencyBalances(Currency.CAD).getTotalEquity());
				
			
			Position[] positions = q.getPositions(accounts[0].getNumber());
			
			for(int i = 0; i < positions.length; i++) {
				System.out.println(positions[i].getSymbol());
			}
			
		} catch (RefreshTokenException e) {
			System.out.println("Refresh token bad");
			return;
		}


	}
	
	public static void saveToDatabase(Authorization e) {
		try {
			PrintWriter out = new PrintWriter("C:\\Users\\Public\\token.txt");
			out.println(e.getRefreshToken());
			out.println(e.getAccessToken());
			out.print(e.getApiServer());
			out.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
	}

}

