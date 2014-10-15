package com.japanzai.koroshiya.controls.thread;

import com.japanzai.koroshiya.reader.MainActivity;

/**
 * Purpose: Thread for swapping to the next view of a ViewFlipper
 * 			Used so flipView could be called on a separate thread.
 * 			This means a processing thread can make the UI call
 * 			runOnUiThread on this class, thereby allowing a processing
 * 			thread to update the view.
 * */
public class ViewFlipThreadForward implements Runnable{

	private final MainActivity parent;
	
	public ViewFlipThreadForward(MainActivity parent){
		this.parent = parent;
	}
	
	@Override
	public void run() {
		this.parent.flipView(true);
	}

}
