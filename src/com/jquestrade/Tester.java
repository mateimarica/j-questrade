package com.jquestrade;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.jquestrade.exceptions.BadRefreshTokenException;

public class Tester {

	public static void main(String[] args) throws FileNotFoundException {
		String refreshToken = new Scanner(new File("C:\\Users\\Public\\token.txt")).nextLine();
		
		QuestradeAPI q = new QuestradeAPI(refreshToken);
		q.setAuthRelay(authorization -> saveToDatabase(authorization));
		
		try {
			q.activate();
			Authorization auth = q.getAuthorization();
			System.out.println("Refresh token: " + auth.getRefreshToken());
			
			System.out.println(q.getTime().toString());
			
			q.revokeAuthorization();
			
			System.out.println(q.getTime().toString());
			
		} catch (BadRefreshTokenException e) {
			System.out.println("Refresh token bad");
			return;
		}
		
		


	}
	
	public static void saveToDatabase(Authorization e) {
		try {
			PrintWriter out = new PrintWriter("C:\\Users\\Public\\token.txt");
			out.println(e.getRefreshToken());
			out.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
	}

}

