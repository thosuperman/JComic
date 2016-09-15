/*
 * Copyright (c) 2007 innoSysTec (R) GmbH, Germany. All rights reserved.
 * Original author: Edmund Wagner
 * Creation date: 05.06.2007
 *
 * Source: $HeadURL$
 * Last changed: $LastChangedDate$
 * 
 * the unrar licence applies to all junrar source and binary distributions 
 * you are not allowed to use this source to re-create the RAR compression algorithm
 * 
 * Here some html entities which can be used for escaping javadoc tags:
 * "&":  "&#038;" or "&amp;"
 * "<":  "&#060;" or "&lt;"
 * ">":  "&#062;" or "&gt;"
 * "@":  "&#064;" 
 */
package de.innosystec.unrar.unpack.ppm;

import de.innosystec.unrar.io.Raw;



/**
 * DOCUMENT ME
 *
 * @author $LastChangedBy$
 * @version $LastChangedRevision$
 */
public class RarNode extends Pointer{
	private int next; //rarnode pointer

	public static final int size = 4;

	public RarNode(byte[] mem){
		super(mem);
	}
	
	public int getNext() {
		if(mem!=null){
			next = Raw.readIntLittleEndian(mem,  pos);
		}
		return next;
	}

	public void setNext(RarNode next) {
		setNext(next.getAddress());
	}
	
	public void setNext(int next) {
		this.next = next;
		if(mem!=null){
			Raw.writeIntLittleEndian(mem, pos, next);
		}
	}

    public String toString() {
        return "State[\n  pos="+pos+"\n  size="+size+"\n  next="+getNext()+"\n]";
    }
}