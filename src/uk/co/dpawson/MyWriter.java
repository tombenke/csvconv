/*
 * MyWriter.java
 *
 * Created on Oct 8, 2007, 9:19:09 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package uk.co.dpawson;

import java.io.*;
import javax.swing.*;
import java.awt.*;

/**
 * myWriter acts on all io for the program
 */
class MyWriter {
    
    /*
     * output object. used to put output to the output stream
     *
     */
    //private FileOutputStream output;
    private BufferedWriter output;
    
    /**
     * Open the output file.
     *@param  opFile Output file handle to be written to.
     */
    public void openFile( java.io.File opFile) {
        //File opFile = new File(fName);
        
        if ( opFile == null ||
                opFile.getName().equals( "" )) {
            System.err.println("\nOpening console for output\n");
        } else {
            // Open the file
            try {
                //output =  new FileOutputStream( opFile );
                output =new BufferedWriter(
                        new OutputStreamWriter(
                        new FileOutputStream(opFile), "UTF-8"));
            } catch ( IOException e ) {
                System.err.println("Error opening file" + e);
            }
        }
    }
    /**
     *  Quit. Close the output file
     *
     */
    public void quit(){
        closeFile();
        
    }
    
    /**
     * Close the output file.
     *
     */
    private void closeFile() {
        if (output == null){
            return;
        }else{
            try {
                output.close();
            } catch( IOException ex ) {
                System.err.println("Error closing file");
            }
        }
    }
    
    /**
     * write to the output stream
     * @param  s string to be written
     */
    public void write(String s) {
        if (output == null){
            System.out.print(s);
            
        }else{
            /*	    byte[] bytes=null;
            bytes = s.getBytes();
            try {
                output.write(bytes);
                output.flush();
            }catch(java.io.IOException e) {
                System.err.println("svgOutput: IO exception" );
                System.exit(1);
                }*/
            try {
                output.write(s);
            }catch(java.io.IOException e) {
                System.err.println("myWriter.write: IO exception" );
                System.exit(1);
                
            }
        }
        
    }// end of writer
    
 /*
  * write to the output stream
  *
  */
    public void writeln(String s) {
        if (output == null){
            System.out.print(s);
            
        }else{
            String str="\n"+ s;
            /* byte[] bytes=null;
            bytes = s.getBytes();
            try {
                output.write(bytes);
                output.flush();
            }catch(java.io.IOException e) {
                System.err.println("svgOutput: IO exception" );
                System.exit(1);
            }
             */
            try {
                output.write(str);
                output.flush();
            }catch(java.io.IOException e) {
                System.err.println("myWriter.writeln: IO exception" );
                System.exit(1);
            }
        }
        
    }// end of writer
    
    
}
    
    
    
 

