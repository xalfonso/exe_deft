package eas.com;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by eduardo on 12/12/2016.
 */
public class App {

    public static void main(String[] args) throws IOException {
       /* try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Write your Name: ");
            String data = reader.readLine();

            System.out.println("Your name is: " + data);
        }*/

       /*String text = "Hola,";

        String [] parts = text.split(", ");

        System.out.println(parts[1].isEmpty());
        System.out.println(parts[1]);
        System.out.println(parts[1].length());
        //System.out.println(parts[1]);*/


       String line = "$3.75   ";


        System.out.println(line.trim());
       // float value = Float.valueOf(line);
        //System.out.println(value);

    }
}
