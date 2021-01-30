package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.util.Scanner;

import com.jquestrade.Account;
import com.jquestrade.Authorization;
import com.jquestrade.Execution;
import com.jquestrade.QuestradeAPI;
import com.jquestrade.exceptions.RefreshTokenException;

class Tester {

	public static void main(String[] args) throws FileNotFoundException {
		String refreshToken = new Scanner(new File("C:\\Users\\Public\\token.txt")).nextLine();

		QuestradeAPI q = new QuestradeAPI(refreshToken);
		q.setAuthRelay(authorization -> saveToDatabase(authorization));
		
		try {
			q.activate();
			System.out.println(q.getAuthorization().getRefreshToken() + "\n");
			
			//System.out.println(q.getTime().toString() + "\n");
			
			Account[] accounts = q.getAccounts();
			
			
			Execution[] executions = q.getExecutions(accounts[0].getNumber(), ZonedDateTime.parse("2021-01-01T00:00:00Z"), ZonedDateTime.parse("2021-01-25T00:00:00Z"));
			
			for(int i = 0; i < executions.length; i++) {
				System.out.println(executions[i].getSymbol());
			}
			
		} catch (RefreshTokenException e) {
			System.out.println("Refresh token bad");
			return;
		}

		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
		//ZonedDateTime z = ZonedDateTime.of(2020, 9, 6, 0, 0, 0, 0, ZoneId.of("GMT"));
		
		//System.out.println(z.format(formatter));

		// test: 2020-09-06T00:00:00Z

	}
	
	public static void saveToDatabase(Authorization e) {
		try {
			PrintWriter out = new PrintWriter("C:\\Users\\Public\\token.txt");
			out.println(e.getRefreshToken());
			out.print(e.getAccessToken());
			out.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
	}

}

