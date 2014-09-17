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

		Socket client=null;
		
		try {
			// Construct a socket and connect it to the remote address and port given by the command line arguments:
			// client = new Socket...
			System.out.println("CONNECTED TO: " + client.getRemoteSocketAddress());
			
			// Build the writer & reader from the client's socket input and output streams: 
			PrintWriter out=null;
			BufferedReader in=null;

			String response=null;
			do {
				System.out.println("REQUESTING WORK");
				// send a get work message to the server (use "out" from above):
				
				// read its response (use "in" from above):
				// response = ...
				
				// if the response includes work:
				if(response.startsWith(AMP_WORK)) {
					// read the next line (which will contain the math problem):
					// response = ...
					System.out.println("  WORK: " + response);

					// solve the math problem (see MathProblem.solve, in this package)
					Double ans=null;
					
					// if ans is null, then the math problem was malformed.
					// you can choose how to handle this case, but make sure that
					// you don't break the server.
					if(ans == null) {
						ans = 0.0;
					}
										
					// send the answer to the server (don't forget the header, use "out"):
					System.out.println("  SENDING ANSWER: " + ans);
					
					// the server should respond with OK if everything went well
					// note: OK does not mean the answer is right, just that the 
					// server understood the message.
					
					// receive the response (use "in"):
					// response = ...
					if(response.startsWith(AMP_OK)) {
						System.out.println("  ANSWER RECEIVED");
					} else {
						System.out.println("  ANSWER MALFORMED");
					}
				}
			} while(!response.startsWith(AMP_NONE)); // loop until an AMP_NONE response is received
			
			System.out.println("NO WORK AVAILABLE; TERMINATING");

		} finally {
			// close the socket:
			if(client != null) {
				client.close();
			}
		}
	}
}
