/*
 * PropertyFile.java
 *
 * Created on Oct 8, 2007, 9:18:56 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package uk.co.dpawson;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;


/**
 * This class holds the data from a Property file.
 **/
public class PropertyFile {
    
    // ----- Private Fields -----
    /**
     *Comments from the file
     */
    private String comment;
    /**
     * Delimiter for individual fields
     */
    private String fieldDelimiter; 
    /**
     *   Delimiter for each row
     */
    private String rowDelimiter;
    /**
     * Root element to use for output XML
     */
    private String xmlRootName;
    /**
     * Element to use for each row
     */
    private String recordName;
    /**
     *How many fields are there -  Note: This is 1 based, not zero based.
     */
    private int fieldCount;
    /**
     * array of fields
     */
    private ArrayList<String> fields = new ArrayList<String>(88);
    /**
     *Set to int > 0 for debug output
     */
    private int  debug=0;
    
    /** A single instance of this will hold all the relavant details for ONE PropertyFile.
     *@param filePath String name of the property file.
     *
     *
     **/
    public  PropertyFile(String filePath) {
        
        //StreamReader reader = new StreamReader( filePath );
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            
            
            String line = null;
            
            while ( (line = reader.readLine()) != null ) {
                
                if ( line.length() != 0 )   //was != ""
                {
                    if (debug> 0)
                        System.err.println("String is: " + line + "lth: " + line.length());
                    if ( line.charAt(0) != '[' && !( line.startsWith("//") ) ) {
                        
                        String propertyValue = line.split("=")[1];
                        
                        // Assign Comment
                        if ( line.toUpperCase().startsWith("COMMENT=") )
                            this.comment = propertyValue;
                        
                        // Assign Field Delimter
                        if ( line.toUpperCase().startsWith("FIELDDELIMITER") )
                            //this.fieldDelimiter = propertyValue.substring(0);
                            this.fieldDelimiter = propertyValue;
                        
                        // Assign Row Delimiter
                        if ( line.toUpperCase().startsWith("ROWDELIMITER") ) {
                            if ( propertyValue.substring(0,1).toUpperCase() ==
                                    "\\" && propertyValue.toUpperCase().charAt(1) == 'N')
                                this.rowDelimiter = "\r\n";
                            else
                                this.rowDelimiter = propertyValue;
                        }
                        
                        // Assign Root Document Name
                        if ( line.toUpperCase().startsWith("ROOTNAME") )
                            this.xmlRootName = propertyValue;
                        
                        // Assign Record Name
                        if ( line.toUpperCase().startsWith("RECORDNAME") )
                            this.recordName = propertyValue;
                        
                        // Assign Field Count
                        if ( line.toUpperCase().startsWith("FIELDS") )
                            this.fieldCount =  Integer.parseInt(propertyValue);
                        
                        
                    } else {
                        
                        if ( line.toUpperCase().startsWith("[FIELDS]") ) {
                            
                            while ( (line = reader.readLine()) != null ) {
                                if ( line.length() == 0)
                                    break;
                                else{
                                    
                                    if (debug > 0)
                                        System.err.println("Adding: "+line.split("=")[1]);
                                    this.fields.add(line.split("=")[1] );
                                }
                            }
                            
                            break;
                            
                        }
                        
                    }
                }
            }
            reader.close();
        } catch (java.io.IOException e) {
            System.out.println("**** IO Error on input file. Quitting");
            System.exit(2);
            
        }
        
        
    }
    /**
     * Return the comment in the property file
     *@return String, the comment value, if any
     */
    public String comment() {
        return this.comment;
    }
    
    
    /**
     * The delimiter to be used for each field, often comma.
     *@return String, the character(s)
     */
    public String fieldDelimiter() {
        return this.fieldDelimiter;
    }
    
    /**
     * /**
     * Row Delimiter - often '\n'
     *@return String, the character(s)
     */
    public String rowDelimiter() {
        return this.rowDelimiter;
    }
    
    /**
     * The XML document root node.
     * @return String, the element name
     **/
    public String XMLRootName() {
        return this.xmlRootName;
    }
    
    /** <summary>
     ** The node name for each record
     * @return current record name
     **/
    public String recordName() {
        return this.recordName;
    }
    
    /**
     ** Number of Fields per record/node
     *@return integer count of number of fields, 1 based.
     **/
    public int fields() {
        return this.fieldCount;
    }
    
    // ----- Public Methods -----
    
    /**
     * The value of the nth field, 0 based.
     *
     * @param index Which field to return
     * @return String the field value
     **/
    public String fieldNames(int index) {
        if (index <this.fields.size())
            return (String)this.fields.get(index); //was .toString()
        else {
            System.err.println("PropertyFile: Trying to get idx of :"
                    + index
                    + "\n when only "
                    //+ (this.fields.size() -  1)
                    + this.fieldCount
                    + " available"
                    );
            System.exit(2);
        }
        return "";
    }
    
    
    
    
    /**
     *Test entry point, this class
     *@param argv  cmd line arg of property file
     *
     */
    public static void main(String argv[]) {
        if ( argv.length != 1) {
            System.out.println("Md5 <file>") ;
            System.exit(1) ;
        }
        PropertyFile p = new PropertyFile(argv[0]);
    }
    
}
