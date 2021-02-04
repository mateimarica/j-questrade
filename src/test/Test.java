package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Scanner;
import com.jquestrade.*;
import com.jquestrade.Balances.Currency;
import com.jquestrade.exceptions.*;

class Test {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner s = new Scanner(new File("C:\\Users\\Public\\token.txt"));

		Questrade q = new Questrade(s.nextLine(), s.nextLine(), s.nextLine());
		//Questrade q = new Questrade(s.nextLine());
		q.setAuthRelay(auth -> saveToDatabase(auth));
		
		final ZonedDateTime t1 = ZonedDateTime.of(2021, 01, 1, 0, 0, 0, 0, ZoneId.of("GMT"));
		final ZonedDateTime t2 = ZonedDateTime.of(2021, 01, 31, 0, 0, 0, 0, ZoneId.of("GMT"));
		
		try {
			q.activate();
			
			Account[] accs = q.getAccounts();

			Activity[] acts = q.getActivities(accs[0].getNumber(), t1, t2);
			
			for(int i = 0 ; i < acts.length; i++) {
				print(acts[i].getAction());
				print(acts[i].getCommission());
				print(acts[i].getCurrency());
				print(acts[i].getDescription());
				print(acts[i].getGrossAmount());
				print(acts[i].getNetAmount());
				print(acts[i].getPrice());
				print(acts[i].getSettlementDate());
				print(acts[i].getSymbol());
				
				print("------------------------------");
			}
			
		} catch (RefreshTokenException e) {
			System.out.println("Refresh token bad");
			e.printStackTrace();
			System.out.println(q.getLastRequest());
		} catch (StatusCodeException e) {
			System.out.println("Bad request. Status code: " + e.getStatusCode());
			e.printStackTrace();
			System.out.println(q.getLastRequest());
		} catch (ArgumentException e) {
			e.printStackTrace();	
			System.out.println(q.getLastRequest());
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
	
	public static void print(Object a) {
		System.out.println(a);
	}

}

