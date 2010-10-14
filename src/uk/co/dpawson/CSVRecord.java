/*
 * CSVRecord.java
 *
 * Created on Oct 8, 2007, 9:20:46 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package uk.co.dpawson;

import  java.util.ArrayList;

/**
 *
 *Represents a single record of a CSV file
 *
 **/
public class CSVRecord {
    
    /**
     *Debug
     **/
    private int debug = 3;
    /**
     * Arraylist of String fields of the record
     **/
    private ArrayList<String> fields = new ArrayList<String>();
    
    
    
    /**
     *get the field with index index
     *@param index of field required
     *@return String value of that field
     **/
    public String getFields(int index) {
        if ( index < fields.size())
            return this.fields.get(index);
        else return ("");
    }
    
    /**
     *get the number of fields
     *@return int number of fields in this file
     **/
    public int count() {
        return this.fields.size();
    }
    
    
    /**
     *Create a csv record from the input String, using the propertyfile.
     *@param  details , the property file
     *@see <a href="propertyFile.html">propertyFile</a>
     *@param  recordText , the record to be added to the arraylist of records
     **/
    public  CSVRecord(PropertyFile details, String recordText) {
        
        /**
         * true if within a quote
         **/
        boolean inQuote = false;
        /**
         * temp saved field value
         **/
        String savedField = "";
        /**
         * current field value
         **/
        String curField = "";
        /**
         * field being built
         **/
        String field = "";
        /**
         * array of records.
         * split it according to the field delimiter.
         * The default String.split() is not accurate according to the M$ view.
         **/
        String records[] =  recordText.split( details.fieldDelimiter() );
        for (int rec=0; rec <records.length; rec++) {
            
            field = records[rec];
            //Add this field to currently saved field.
            curField = savedField + field;
            
            
            //Iterate over current field.
            for (int i = 0; i < curField.length(); i ++ ){
                char ch = curField.charAt(i); //current char
                char nxt = ((i==
                        curField.length() -1)
                        ? ' ' : curField.charAt(i+1)); //next char
                char prev = (i==0? ' ': curField.charAt(i-1)); //prev char
                
                if ( !inQuote && ch == '"' )
                    inQuote = true;
                else
                    if ( inQuote && ch == '"' )
                        if ( (i + 1) < curField.length() )
                            inQuote = (nxt == '"') || (prev == '"');
                        else
                            inQuote = (prev == '"');
                
            }//end of current field
            
            //Deal with quoted fields
            if ( inQuote ) {
                savedField = curField + details.fieldDelimiter() + "";
                inQuote = false;
            } else if (!inQuote && curField.length() > 0) {
                char ch = curField.charAt(0); //current char
                char lst = curField.charAt(curField.length()-1);
                if (ch   == '"' &&
                        lst == '"') {
                    //Strip leading quotes
                    curField = curField.substring(1);
                    //Strip any trailing quotes
                    if (curField.endsWith("\""))
                        System.out.println("Stripping tr");
                        curField = curField.substring(0,curField.length()-1);
                    
                    //replace two double quote with one double quote
                    curField =curField.replaceAll("\"\"", "\"");
                }
                // add current field to record
                this.fields.add( curField );
                savedField = "";
                if (debug > 2){
                    System.err.println("CSVRecord: added["+curField+"]");
                }
            } else if(curField.length() == 0){
                this.fields.add("");
            }
            
            
        }//   end of for each record
    }
    
    
    
    
    
}

