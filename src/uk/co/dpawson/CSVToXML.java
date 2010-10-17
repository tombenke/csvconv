/*
 * CSVToXML.java
 *
 * Created on Oct 8, 2007, 9:19:52 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package uk.co.dpawson;
import nu.xom.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Main class functionality is to convert a M$ CSV file to XML as defined by props file.
 *
 *
 *
 */
public class CSVToXML {
    /**
     *The writer object is used for all output.
     */
    protected MyWriter writer;
    
    /**
     *Set level of debug.
     **/
    public static int debug = 0;
    
    /**
     *Serialize the output to xml
     *@param  csvFile csv input file
     *@see <a href="CSVFile.html">CSVFile</a>
     *@param  details , property file
     *@see <a href="propertyFile.html">propertyFile</a>
     *@param  writer , output file handle
     *
     **/
    public static void ProduceXML(CSVFile csvFile, PropertyFile details, MyWriter writer) {
        /**
         *buffer into which output data is built prior to writing
         *
         **/
        StringBuffer xmlFileData = new StringBuffer();
        
        // Add document node
        xmlFileData.append( "<!-- " + details.comment() + " -->\n");
        
        // Document root node
        xmlFileData.append( "<" + details.XMLRootName() + ">\n" );
        
        
        // Loop through csv records in input file
        for (int i = 0; i < csvFile.count(); i++ ) {
            // New record
            xmlFileData.append( "<" + details.recordName() + ">\n" );
            
            // Loop through fields in each record
            for (int x = 0; x < csvFile.getRecord(i).count(); x++) {
                if (debug > 2){
                    //System.out.print("csv2xml: record size is: "+
                    //        csvFile.getRecord(i).count()+"\n");
                    System.out.print("\ncsv2xml:[" + i+" "+ + x +"] " );
                    System.out.print("[" + csvFile.getRecord(i).getFields(x)+"]");
                }
                // Write the field to the file
                xmlFileData.append( "<" + details.fieldNames(x) + ">"
                        + (csvFile.getRecord(i)).getFields(x)
                        + "</"
                        + details.fieldNames(x) + ">" );
                
            } //end of record
            if (debug > 2) System.err.println();
            xmlFileData.append( "</" + details.recordName() + ">\n" );
        }//end of document
        xmlFileData.append( "</" + details.XMLRootName() + ">" );
        
        // ----- Now take the String and write to file
        String st = xmlFileData.toString();
        writer.write(st);
        writer.quit();
    }
    
    /**
     *Serialize the output to xml
     *@param  csvFile csv input file
     *@see <a href="CSVFile.html">CSVFile</a>
     *@param  details , property file
     *@see <a href="propertyFile.html">propertyFile</a>
     *@param  outputFileName , output file 
     *
     **/
    public static void ProduceXML2(CSVFile csvFile, 
            PropertyFile details, 
            String outputFileName) {
        
        Builder b = new Builder("");
        Element root = new Element(details.XMLRootName());
        
        /**
         *buffer into which output data is built prior to writing
         *
         **/
        StringBuffer xmlFileData = new StringBuffer();
        Comment c = new Comment(details.comment());
        root.appendChild(c);
        c = new Comment(Version.getProductName()+Version.getWebSiteAddress());
        root.appendChild(c);
        
        
        Element record = new Element(details.recordName());
        
        // Loop through csv records in input file
        for (int i = 0; i < csvFile.count(); i++ ) {
            // New record
            
            record = new Element(details.recordName());
            // Loop through fields in each record
            for (int x = 0; x < csvFile.getRecord(i).count(); x++) {
                if (debug > 2){
                    //System.out.print("csv2xml: record size is: "+
                    //        csvFile.getRecord(i).count()+"\n");
                    System.out.print("\ncsv2xml:[" + i+" "+ + x +"] " );
                    System.out.print("[" + csvFile.getRecord(i).getFields(x)+"]");
                }
                // Add the field to the record
                Element field = new Element(details.fieldNames(x));
                field.appendChild((csvFile.getRecord(i)).getFields(x));
                record.appendChild(field);
            } //end of record
            if (debug > 2) System.err.println();
            root.appendChild(record);
        }//end of document
        
        
        
        // ----- Now take the String and write to file
        Document doc = new Document(root);
        try {
            java.io.File tmp = new File("tmp.xml");
            FileOutputStream fos = new FileOutputStream(tmp);
            Serializer serializer = new Serializer(fos, "utf-8");
            serializer.setIndent(4);
            serializer.setMaxLength(64);
            serializer.write(doc);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    

    public static void ProduceJSON(
            CSVFile csvFile,
            PropertyFile details,
            MyWriter writer )
    {
        /* buffer into which output data is built prior to writing */
        StringBuffer jsonFileData = new StringBuffer();

        // Add document node
        //jsonFileData.append( "/* " + details.comment() + " */\n");

        // Document root node
        jsonFileData.append( "{ \"" + details.XMLRootName() + "\": [\n" );


        // Loop through csv records in input file
        for (int i = 0; i < csvFile.count(); i++ )
        {
            // New record
            jsonFileData.append( "  {\n" );

            // Loop through fields in each record
            for( int x = 0; x < csvFile.getRecord(i).count(); x++ )
            {
                if( debug > 2 )
                {
                    //System.out.print("csv2xml: record size is: "+
                    //        csvFile.getRecord(i).count()+"\n");
                    System.out.print("\ncsv2xml:[" + i+" "+ + x +"] " );
                    System.out.print("[" + csvFile.getRecord(i).getFields(x)+"]");
                }

                // Write the field to the file
                jsonFileData.append( "    \"" + details.fieldNames(x) + "\": "
                        + "\"" + (csvFile.getRecord(i)).getFields(x) +
                        (x == (csvFile.getRecord(i).count() - 1)
                            ? "\"\n"
                            : "\",\n") );
            }
            //end of record
            if( debug > 2 )
                System.err.println();
            jsonFileData.append( (i == (csvFile.count() - 1)
                    ? "  }\n" : "  },\n" ) );
        }

        //end of document
        jsonFileData.append( "]\n}\n" );

        // ----- Now take the String and write to file
        String st = jsonFileData.toString();
        writer.write(st);
        writer.quit();
    }

    /**
     *null constructor.
     *
     **/
    public  CSVToXML() {
        //  This is here to prevent an instance of this class being instantiated, as that would be pointless
    }
        
    /**
     *Standard Entry point: Calls doMain for action.
     *@param argv
     *@see <a href="#doMain">doMain</a>
     **/
    public static void main(String[] argv)
    {
        // the real work is delegated to another routine
        // so that it can be used in a subclass
        (new CSVToXML()).doMain( argv, new CSVToXML(), "java CSVToXML" );
        
    }
    
    /**
     * Resolves command line parameters and calls draw class.
     * @param args Command line arguments
     * @param app class to be constructed
     * @param name Application name
     * <a name='doMain' />
     */
    
    protected void doMain(String args[], CSVToXML app, String name)
    {
        File outputFile = null;

        //ParameterSet params = new ParameterSet();
        //Properties outputProperties = new Properties();
        String outputFormat = "xml";
        String outputFileName = null;
        String propertyFileName=null;
        String CSVFileName=null;
        
        // Check the command-line arguments.
        try
        {
            int i = 0;
            while( true )
            {
                if( i >= args.length )
                    break;

                if( debug > 2 )
                    System.err.println( "i: " + i + args.length + " args[i]=" + args[i] );

                if( args[i].charAt(0)=='-' )
                {
                    if( args[i].equals( "-t" ) )
                    {
                        System.err.println( Version.getProductName() );
                        System.err.println( "Java version " + System.getProperty( "java.version" ) );
                        i++;
                    }

                    else if( args[i].equals( "-p" ) )
                    {
                        i++;
                        if( args.length < i+1 )
                            badUsage( name, "No Property file Specified" );

                        propertyFileName = args[i++];
                        if( debug > 1 )
                            System.err.println( "props file: " + propertyFileName );
                    }

                    else if( args[i].equals( "-f" ) )
                    {
                        i++;
                        if( args.length < i+1 )
                            badUsage(name, "No format is specified" );

                        outputFormat = args[i++];
                        if( debug > 1 )
                            System.err.println( "props file: " + outputFormat );
                    }

                    else if( args[i].equals( "-i" ) )
                    {
                        i++;
                        if( args.length < i + 1 )
                            badUsage( name, "No input file Specified" );

                        CSVFileName = args[i++];
                        if( debug > 1 )
                            System.err.println( "input file: " + CSVFileName );
                    }
                    else if( args[i].equals( "-o" ) )
                    {
                        i++;
                        if( args.length < i+1 )
                            badUsage( name, "No output file name" );

                        outputFileName = args[i++];

                        if(debug > 1 )
                            System.err.println( "output file: " + outputFileName );
                        
                        
                    }
                    else
                        badUsage( name, "Unknown option " + args[i] );
                }
                else
                    break;

            } //end while

            if( outputFileName!=null )
            {
                outputFile = new File( outputFileName );
                if( outputFile.isDirectory() )
                {
                    quit( "Output is a directory" );
                }
            }
        }
        catch( Exception err2 )
        {
            err2.printStackTrace();
        }

        //Check for files available: input, property and output
        if( propertyFileName == null )
            badUsage(name, "No property File available; Quitting");

        if( CSVFileName == null )
            badUsage(name, "No Input File available; Quitting");

        if( outputFileName == null )
            badUsage(name, "No Output File available; Quitting");
        
        writer = new MyWriter();
        writer.openFile( outputFile );

        if( debug > 1 )
        {
            System.err.println(
                    "CSVToXML: Producing "
                    + outputFileName
                    + " from " + CSVFileName
                    + " Using " + propertyFileName );
        }

        // Create new properties file
        PropertyFile pf = new PropertyFile( propertyFileName );

        // go Convert it
        CSVFile iFile = new CSVFile( pf,CSVFileName );

        //Now serialize it
        if( outputFormat.matches( "xml" ) )
            ProduceXML( iFile, pf, writer );
        else
            ProduceJSON( iFile, pf, writer );
        
        //end the root calls
        
        writer.quit();
        System.exit(0);
    } //end of domain.

    
    /**
     * Exit with a message
     *@param message Message to be output prior to quitting.
     */
    protected static void quit(String message)
    {
        System.err.println(message);

        // Need to call svgOutput.closeFile
        System.exit(2);
    }


    /**
     * Output the command line help.
     *@param name option in errror
     *@param message associated message
     *
     */
    protected void badUsage( String name, String message )
    {
        System.err.println(message);
        System.err.println(Version.getProductName());
        System.err.println("Usage: " + name + " [options] {param=value}...");
        System.err.println("Options: ");
        System.err.println("  -p filename       Take properties from named file  ");
        System.err.println("  -o filename       Send output to named file  ");
        System.err.println("  -i filename       Take CSV input from named file  ");
        System.err.println("  -f output-format  format of output file: xml | json ");
        
        System.err.println("  -t              Display version and timing information ");
        //System.err.println("  -w0             Recover silently from recoverable errors ");
        //System.err.println("  -w1             Report recoverable errors and continue (default)");
        //System.err.println("  -w2             Treat recoverable errors as fatal");
        System.err.println("  -?              Display this message ");
        System.exit(2);
    }
    
    
}

