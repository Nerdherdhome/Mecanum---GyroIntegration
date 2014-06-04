package nerdHerd.util;


import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.microedition.io.FileConnection;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import javax.microedition.io.Connector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author student
 */
public class Filer {

    private OutputStream theFile;
    private FileConnection fc;
    private String fileName;
    private BufferedReader input;
    private StringBuffer currentString;
    private int matchNumber = 0;
    
    public Filer(){
        matchNumber = SmartDashboard.getInt("Match Number", matchNumber);
    }
    public void connect(){
        try {
                try{
                    fileName = "file:///Match" + SmartDashboard.getInt("Match Number", matchNumber) + ".txt";
                    fc = (FileConnection)Connector.open(fileName, Connector.WRITE);
                    input = new BufferedReader(new InputStreamReader(Connector.openInputStream(fileName)));
                    char[] charArray = new char[4096];
                    int charLength = input.read(charArray);
                    String previousString = "";
                    for(int i = 0; i < charLength; i++){
                        previousString += charArray[i];
                    }
                    currentString = new StringBuffer(previousString);  
                }catch(IOException e){                    
                }
                fc.create();
                theFile = Connector.openOutputStream(fileName);
                theFile.write(currentString.toString().getBytes());
            } catch (Exception e) {
            }
    }
    
    public void println(String line){
        byte bytes[] = (line + "\n").getBytes();
        try{
            theFile.write(bytes);
        }catch(Exception e){
        }
    }
    
    public void print(String line){
        byte bytes[] = (line).getBytes();
        try{
            theFile.write(bytes);
        }catch(Exception e){
        }
    }
    
    public void close(){
        try{
            theFile.close();
        }catch(Exception e){
        }
    }
}
