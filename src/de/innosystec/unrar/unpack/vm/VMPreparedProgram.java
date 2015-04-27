/*
 * Copyright (c) 2007 innoSysTec (R) GmbH, Germany. All rights reserved.
 * Original author: Edmund Wagner
 * Creation date: 31.05.2007
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
package de.innosystec.unrar.unpack.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * DOCUMENT ME
 * 
 * @author $LastChangedBy$
 * @version $LastChangedRevision$
 */
public class VMPreparedProgram 
{
	private final List<VMPreparedCommand> Cmd  = new ArrayList<>();
	private List<VMPreparedCommand> AltCmd  =new ArrayList<>();
	private int CmdCount;


	
	private final Vector<Byte> GlobalData = new Vector<>();
	private Vector<Byte> StaticData = new Vector<>(); // static data contained in DB operators
	private final int[] InitR = new int[7];

	private int FilteredDataOffset;
	private int FilteredDataSize;
	
	public VMPreparedProgram() 
	{
		AltCmd=null;
	}

	

	public List<VMPreparedCommand> getAltCmd() {
		return AltCmd;
	}



	public void setAltCmd(List<VMPreparedCommand> altCmd) {
		AltCmd = altCmd;
	}



	public List<VMPreparedCommand> getCmd() {
		return Cmd;
	}

	public int getCmdCount() {
		return CmdCount;
	}

	public void setCmdCount(int cmdCount) {
		CmdCount = cmdCount;
	}

	

	public int getFilteredDataOffset() {
		return FilteredDataOffset;
	}



	public void setFilteredDataOffset(int filteredDataOffset) {
		FilteredDataOffset = filteredDataOffset;
	}



	public int getFilteredDataSize() {
		return FilteredDataSize;
	}

	public void setFilteredDataSize(int filteredDataSize) {
		FilteredDataSize = filteredDataSize;
	}

	public Vector<Byte> getGlobalData() {
		return GlobalData;
	}

	public int[] getInitR() {
		return InitR;
	}

	public Vector<Byte> getStaticData() {
		return StaticData;
	}

	public void setStaticData(Vector<Byte> staticData) {
		StaticData = staticData;
	}
	
	
}
