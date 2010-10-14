/*
 * Version.java
 *
 * Created on Oct 8, 2007, 9:18:28 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package uk.co.dpawson;


/**
 * The Version class holds the javSvg version information.
 */
public class Version {
    public static String javaVersion = System.getProperty("java.version");
    public static boolean preJDK12 =
            javaVersion.startsWith("1.1") ||
            javaVersion.startsWith("1.0") ||
            javaVersion.startsWith("3.1.1 (Sun 1.1");   // special for IRIX 6.5 Java
   /**
    * return true if old
    * @return true, if per 1.2
    * */ 
    public static final boolean isPreJDK12() {
        return preJDK12;
    }
    
    /**
     *Returns the version of this program
     *@return the current version
     */
    public final static String getVersion() {
        return "1.2";
    }
    
    
    /**
     *Returns the product name
     *@return product name
     */
    public static String getProductName() {
        return "CSVToXML " + getVersion() + " from Dave Pawson";
    }
    /**
     *Returns the website of the software
     *@return homepage 
     */
    public static String getWebSiteAddress() {
        return "http://www.dpawson.co.uk/java/csv2xml/";
    }
}




