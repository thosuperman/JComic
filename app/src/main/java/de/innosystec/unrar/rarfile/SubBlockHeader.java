/*
 * Copyright (c) 2007 innoSysTec (R) GmbH, Germany. All rights reserved.
 * Original author: Edmund Wagner
 * Creation date: 21.11.2007
 *
 * Source: $HeadURL$
 * Last changed: $LastChangedDate$
 * 
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
package de.innosystec.unrar.rarfile;

import java.io.IOException;

import de.innosystec.unrar.io.Raw;

public class SubBlockHeader 
extends BlockHeader 
{
	
	public static final short SubBlockHeaderSize = 3;
	
	private final short subType;
	private byte level;
	
	public SubBlockHeader(SubBlockHeader sb) throws IOException {
		super(sb);
		subType = sb.getSubType().getSubblocktype();
		level = sb.getLevel();
	}
	
	public SubBlockHeader(BlockHeader bh, byte[] subblock) throws IOException {
		super(bh);
		int position = 0;
		subType = Raw.readShortLittleEndian(subblock, position);
		position +=2;
		level |= subblock[position]&0xff;
	}

	public byte getLevel() {
		return level;
	}

	public SubBlockHeaderType getSubType() {
		return SubBlockHeaderType.findSubblockHeaderType(subType);
	}

}
