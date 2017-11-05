/*
	ZZZClient.java

	Copyright 2017 ZZZ Ltd. - Bulgaria

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/

package zzzclientj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author ZZZ Ltd. - Bulgaria
 */
public class MainApp {

    public static String ZZZProgram(String serverHost, int serverPort,
            String program)
    {
        String result = "";
        
        try {
        	if(serverHost.equals("localhost"))
        		serverHost = "127.0.0.1";
            Socket socket = new Socket(serverHost, serverPort);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(
                    socket.getOutputStream(), "UTF-8"), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), "UTF-8"));
            
            out.print(program + '\0');
            out.flush();
            
            StringBuilder sb = new StringBuilder();
            int DEFAULT_BUFFER_SIZE = 1000;
            char buf[] = new char[DEFAULT_BUFFER_SIZE];
            int n;
            while ((n = in.read(buf)) > 0) {
                sb.append(buf, 0, n);
            }
            result = sb.toString();

            out.close();
            in.close();
            socket.close();
        } catch(Exception e) {
            e.printStackTrace();
        }    
        
        return result;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	long startTime = System.currentTimeMillis();
    	
        System.out.println(ZZZProgram("localhost", 3333, "#[cout;Hello world from ZZZServer!]"));
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime + " milliseconds");
	}
}
