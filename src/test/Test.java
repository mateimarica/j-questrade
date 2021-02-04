package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Scanner;
import com.jquestrade.*;
import com.jquestrade.Candle.Interval;
import com.jquestrade.exceptions.*;

class Test {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner s = new Scanner(new File("C:\\Users\\Public\\token.txt"));

		QuestradeAPI q = new QuestradeAPI(s.nextLine(), s.nextLine(), s.nextLine());
		//QuestradeAPI q = new QuestradeAPI(s.nextLine());
		q.setAuthRelay(authorization -> saveToDatabase(authorization));
		
		ZonedDateTime t1 = ZonedDateTime.of(2020, 11, 1, 0, 0, 0, 0, ZoneId.of("GMT"));
		ZonedDateTime t2 = ZonedDateTime.of(2020, 12, 1, 3, 0, 0, 0, ZoneId.of("GMT"));

		try {
			q.activate();
			System.out.println(q.getAuthorization().getAccessTokenExpiry());
			Account[] accounts = q.getAccounts();

			int symbolId = q.getPositions(accounts[0].getNumber())[2].getSymbolId();
						
			Candle[] candles = q.getCandles(symbolId, t1, t2, Interval.OneWeek);
			
			for(int i = 0; i < candles.length; i++) {
				System.out.println(candles[i].getClose());
				System.out.println(candles[i].getEnd());
				System.out.println(candles[i].getHigh());
				System.out.println(candles[i].getLow());
				System.out.println(candles[i].getOpen());
				System.out.println(candles[i].getStart());
				System.out.println(candles[i].getVolume());
				System.out.println(candles[i].getVWAP());
				System.out.println("----------------------------------");
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

