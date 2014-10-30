package com.japanzai.koroshiya.cache;

import com.japanzai.koroshiya.reader.Reader;

/**
 * Purpose: Thread for parsing the an entry in a directory at the corresponding index.
 * This acts as a secondary thread, but parses the image at a specified index.
 * */
public class IndexThread extends CacheThread {
	
	private final int index; //Index of the image to parse
	
	public IndexThread(Steppable steppable, int index, Reader r) {
	     super(steppable, true, r);
	     this.index = index;
	}
	
	@Override
	public void run(){
		
		getEntry().setCacheSecondary(parseImage(index)); 
			
	}
	
}
