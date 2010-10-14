/*
 * CSVFile.java
 *
 * Created on Oct 8, 2007, 9:21:13 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package uk.co.dpawson;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
/**
 * holds the file object of records
 **/
public class CSVFile {
    
    /**
     * arraylist of records, each one containing a single record
     */
    private ArrayList <CSVRecord>records = new ArrayList<CSVRecord>();
    /**
     * What to replace a row delimiter with, on output.
     **/
    private String replacementForRowDelimiterInTextField = " "; // Change if needed.
    
    
    /**
     * debug, > 0 for output.
     **/
    private int debug = 0;
    
    /**
     * true when debugging load cycle
     * */
    private boolean debugLoading = false; //
    
    /**
     *Return the required record
     *@param index the index of the required record
     *@return a CSVRecord, see #CSVRecord
     ***/
    public CSVRecord getRecord(int index) {
        if (this.debug > 6 && !debugLoading) {
            System.err.println("CSVFile getRecord ["+index+"]"+
                    (this.records.get(index)).getFields(3));
        }
        return this.records.get(index);
    }
    
    /**
     *Get the number of records in the file
     *@return 1 based count of records.
     *
     ***/
    public int count() {
        return this.records.size();
    }
    
    // ----- Constructor -----
    
    /**
     *Constructor; create a file object
     *@param properties  a propertyFile object, see #propertyFile
     *@param csvFile filename of csv file
     *
     **/
    public CSVFile(PropertyFile properties, String csvFile) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            //StringBuilder sbBuffer = new StringBuilder( reader.ReadToEnd() );
            StringBuffer buf=new StringBuffer();
            String text;
            try {
                while ((text=reader.readLine()) != null)
                    buf.append(text + "\n");
                reader.close();
            }catch (java.io.IOException e) {
                System.err.println("Unable to read from csv file "+ csvFile);
                System.exit(2);
                
            }
            String buffer;
            buffer = buf.toString();
            
            //Escape xml special characters
            buffer = buffer.replaceAll("&","&amp;");
            buffer = buffer.replaceAll("<","&lt;");
            
            
            boolean inQuote = false;
            
            String savedRecord = "";
            String curRecord = "";
            
            
            if (debug > 2) {
                System.err.print("csvFile: setup. ");
                System.err.println("Read in from src CSV file");
                
            }
            
            //Split entire input file into array records, using row delim.
            
            String records[] =  buffer.split( properties.rowDelimiter() );
            
            //Iterate over each record, looking for incomplete quoted strings.
            for (int rec=0; rec <records.length; rec++) {
                curRecord = savedRecord + records[rec]; //cater for split line
                if (debug > 4) {
                      System.out.println();
                    if (!savedRecord.isEmpty()) {
                        System.out.println("csvFile: saved rec" + savedRecord);
                    }
                    System.out.print("csvFile: current rec " + curRecord);
                    System.out.println("[len= " + curRecord.length()+"]");
                }
                /**
                 *
                 *
                 *
                 *
                 **/
                for (int i = 0; i < curRecord.length(); i ++ ) {
                    char ch = curRecord.charAt(i);
                    char prev = ( i != 0? curRecord.charAt(i-1): ' ');
                    char nxt = ( i < (curRecord.length()-2)? curRecord.charAt(i+1): ' ');
                    if ( !inQuote && ch == '"' )
                        inQuote = true;
                    else
                        if ( inQuote && ch == '"' )
                            if ( i + 1 < curRecord.length() )
                                inQuote = (nxt == '"')
                                        || (prev == '"');
                            else
                                inQuote = false;
                    
                }
                
                if ( inQuote ) {
                    // A space is currently used to replace the row delimiter
                    //when found within a text field
                    savedRecord = curRecord + replacementForRowDelimiterInTextField;
                    inQuote = false;
                } else {
                    this.records.add( new CSVRecord(properties, curRecord) );
                    savedRecord = "";
                }
                
            }
        }
        
        catch (java.io.FileNotFoundException e) {
            System.out.println("Unable to read CSV file, quitting");
            System.exit(2);
        }
    }
    
    // ----- Private Methods -----
    /**
     * Split text into parts
     * @param textIn text to be split
     * @param splitString to use to split the input
     * @return an array of string, the split input
     *
     * */
    private String[] SplitText(String textIn, String splitString) {
        
        String [] arrText = textIn.split(splitString);
        return arrText;
    }
    
    
    
    /**
     *Get all records in the csvfile
     *@return array of CSVRecords, see #CSVRecord
     *
     ***/
    public CSVRecord[] GetAllRecords() {
        CSVRecord[] allRecords = new CSVRecord[ this.records.size() ];
        
        for (int i = 0; i < this.records.size(); i++ ) {
            allRecords[i] = (CSVRecord)this.records.get(i);
        }
        
        return allRecords;
    }
    
    
}

