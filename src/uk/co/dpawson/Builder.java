/*
 * Builder.java
 *
 * Created on Oct 7, 2007, 2:23:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package uk.co.dpawson;
import nu.xom.*;
/**
 *
 * @author dpawson
 */
public class Builder {
    
    /**
     *The built response when required
     **/
    
    private Element el = null;
    
    /**
     * Constructor, Set up the new element with elName as wrapper element
     * @param elName String to use for element name
     * */
    public Builder(String elName){
        el = new Element(elName);
        
    }
    
    
    /**
     *Retrieve the collected PCData, and wrap in wrapper
     *@return built nodelist
     **/
    public Element getBuiltPCData(){
        //Element res = wrapper;
        //Elements x = el.getChildElements();
        //for (int j=0; j < x.size(); j++){
        //    res.appendChild(x.get(j));
        //}
        return el;
    }
    /**
     * Add CDATA to the nodeset
     * @param str String to add
     * */
    public void addText(String str){
        this.el.appendChild(str);
    }
    
    
        /**
     * Add an element to the nodeset
     * @param el Element to add
     * */
    public void addEl(Element el){
        this.el.appendChild(el);
    }
    
    /**
     * Clean out the element to restart building
     * @param elName name to use for wrapper element
     * */
    public void clean(String elName){
        this.el = new Element(elName);
    }
}

/**
 * Copyright (C) 2006,2007  Dave Pawson
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 **/