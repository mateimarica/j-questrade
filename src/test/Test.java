package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Scanner;
import com.jquestrade.*;
import com.jquestrade.exceptions.*;

class Test {

	public static void main(String[] args) throws FileNotFoundException {
		
		String refreshToken = new Scanner(new File("C:\\Users\\Public\\token.txt")).nextLine();

		QuestradeAPI q = new QuestradeAPI(refreshToken);
		q.setAuthRelay(authorization -> saveToDatabase(authorization));
		
		ZonedDateTime t1 = ZonedDateTime.of(2020, 11, 1, 0, 0, 0, 0, ZoneId.of("GMT"));
		ZonedDateTime t2 = ZonedDateTime.of(2020, 12, 1, 3, 0, 0, 0, ZoneId.of("GMT"));

		try {
			q.activate();
			System.out.println(q.getAuthorization().getAccessTokenExpiry());
			Account[] accounts = q.getAccounts();

			/*Position[] positions = q.getPositions(accounts[0].getNumber());
			
			for(int i = 0; i < positions.length; i++) {
				System.out.print(positions[i].getSymbol());
				System.out.printf("\t%s\n", positions[i].getSymbolId());
			}*/
			
			q.getExecutions(accounts[0].getNumber(), t1, t2);
			
			Activity[] activities = q.getActivities(accounts[0].getNumber(), t1, t2);
			
			for(int i = 0; i < activities.length; i++) {
				System.out.println(activities[i].getDescription());
			}
			
		} catch (RefreshTokenException e) {
			System.out.println("Refresh token bad");
			e.printStackTrace();
			System.out.println(q.getLastRequest());
		} catch (StatusCodeException e) {
			System.out.println("Bad request. Status code: " + e.getStatusCode());
			e.printStackTrace();
		} catch (ArgumentException e) {
			e.printStackTrace();
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

