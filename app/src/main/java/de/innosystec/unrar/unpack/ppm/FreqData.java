/*
 * Copyright (c) 2007 innoSysTec (R) GmbH, Germany. All rights reserved.
 * Original author: Edmund Wagner
 * Creation date: 04.06.2007
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

public class FreqData extends Pointer{

	public static final int size = 6;

	public FreqData(byte[]mem){
		super(mem);
	}

    public int getSummFreq() {
		return Raw.readShortLittleEndian(mem,  pos)&0xffff;
	}

	public void setSummFreq(int summFreq) {
        Raw.writeShortLittleEndian(mem, pos, (short)summFreq);
	}

    public void incSummFreq(int dSummFreq) {
        Raw.incShortLittleEndian(mem, pos, dSummFreq);
    }

    public int getStats() {
        return Raw.readIntLittleEndian(mem,  pos+2);
	}

	public void setStats(State state) {
		setStats(state.getAddress());
	}

    public void setStats(int state) {
        Raw.writeIntLittleEndian(mem, pos+2, state);
	}

    public String toString() {
        return "FreqData[\n  pos="+pos+"\n  size="+size+"\n  summFreq="+getSummFreq()+"\n  stats="+getStats()+"\n]";
    }
	
}
