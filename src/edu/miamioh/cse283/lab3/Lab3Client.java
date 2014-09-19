package edu.miamioh.cse283.lab3;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Template client for CSE283 Lab 3, FS2014.
 * 
 * This client should read the following from the command line:
 * 1) remote address of the server 
 * 2) remote port of the server
 * 
 * This client should process all available work on the server and then terminate.
 * 
 * @author dk
 */
public class Lab3Client {
	public static final String GET_WORK = "GET WORK";
	public static final String PUT_ANSWER = "PUT ANSWER";
	public static final String AMP_WORK = "AMP WORK";
	public static final String AMP_NONE = "AMP NONE";
	public static final String AMP_OK = "AMP OK";
	public static final String AMP_ERROR = "AMP ERROR";
	
	/**
	 * Runs the Lab3Client.
	 * 
	 * @param args is an array containing each of the command-line arguments.
	 * @throws IOException if there is a networking error.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Usage: java Lab3Client <remote address> <remote port>");
			return;
		}
		
		InetAddress i=null;
		Socket client=null;

		try {
			i = InetAddress.getByName(args[0]);
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		try {
			int port = Integer.parseInt(args[1]);
			// Construct a socket and connect it to the remote address and port given by the command line arguments:
			// client = new Socket...
			client = new Socket(i, port);
			System.out.println("CONNECTED TO: " + client.getRemoteSocketAddress());
			
			// Build the writer & reader from the client's socket input and output streams:
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

			String response=null;
			do {
				System.out.println("REQUESTING WORK");
				out.println(GET_WORK);
				
				response = in.readLine();
				System.out.println(response);
				
				if(response.startsWith(AMP_WORK)) {
					String prompt = in.readLine();
					System.out.println("  WORK: " + prompt);
					
					if (prompt.startsWith(AMP_NONE))
						break;

					Double ans = MathProblem.solve(prompt);
					
					if(ans == null)
						ans = 0.0;
					
					System.out.println("  SENDING ANSWER: " + ans);
					out.println(PUT_ANSWER);
					out.println(ans);
					
					response = in.readLine();
					if(response.startsWith(AMP_OK)) {
						System.out.println("  ANSWER RECEIVED");
					} else {
						System.out.println("  ANSWER MALFORMED");
					}
				}
			} while(!response.startsWith(AMP_NONE));
			
			System.out.println("NO WORK AVAILABLE; TERMINATING");

		} finally {
			if(client != null) {
				client.close();
			}
		}
	}
}
