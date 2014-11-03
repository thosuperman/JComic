package com.japanzai.koroshiya.reader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipException;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.japanzai.koroshiya.R;
import com.japanzai.koroshiya.archive.steppable.SteppableArchive;
import com.japanzai.koroshiya.cache.FileCache;
import com.japanzai.koroshiya.interfaces.ModalReturn;
import com.japanzai.koroshiya.interfaces.archive.ReadableArchive;
import com.japanzai.koroshiya.io_utils.ArchiveParser;
import com.japanzai.koroshiya.io_utils.ImageParser;
import com.japanzai.koroshiya.settings.SettingsManager;

import de.innosystec.unrar.exception.RarException;

/**
 * Purpose: Parses directories, image files and archives.
 * 			Essentially initializes the main activity's cache.
 * */
public class Progress extends SherlockActivity implements ModalReturn{
	
	private File f;
	private int index;
	private Reader reader;
	private ReadableArchive temp;
	
	public static boolean isVisible = false;
	public static Progress self;
	
	@Override
	public void onPause(){
		super.onPause();
		isVisible = false;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		isVisible = true;
	}
	
	private class ProgressThread extends Thread{
		
		@Override
		public void run(){
			
	    	if (f.isDirectory()) { 
	    		reader.setCache(new FileCache(reader, f.getAbsolutePath()));
	    		parseDir(f, 0);
	    		reader.setCacheIndex(index == -1 ? 0 : index);
	    	}else if (ArchiveParser.isSupportedArchive(f)){
		    	if (!parseArchive()){
		    		reader.clearTempFile();
		    		decline();
		    		reader.runOnUiThread(new MessageThread(R.string.archive_read_error, reader));
		        	return;
		    	}
	    	}else { 
	    		reader.setCache(new FileCache(reader, f.getAbsolutePath()));
	    		File parentDir = new File(f.getParent());
	    		
	        	File[] list = parentDir.listFiles();
	        	Arrays.sort(list);
	    		
	    		for (int i = 0; i < list.length; i++){
	    			parseFile(list[i]);
	    			if (list[i].getName().equals(f.getName())){
	    				reader.setCacheIndex(index == -1 ? 0 : index);
	    			}
	    		}
	    	}
	    	if (reader.getCache().getMax() > 0){
	    		finish();
	    	}else{
	    		reader.runOnUiThread(new MessageThread(R.string.no_images, reader));
	    		decline();
	    	}
		}
	}

	@Override
	public void onBackPressed() {
		
		runOnUiThread(new ToastThread(R.string.loading_progress, this, Toast.LENGTH_SHORT));
		//TODO: double tap to cancel

	}
    
    @Override
    public void onCreate(Bundle savedInstanceState){
    	
        super.onCreate(savedInstanceState);
        self = this;
        SettingsManager.setFullScreen(this);
        setContentView(R.layout.progress);
        this.getSupportActionBar().hide();

        Bundle b = getIntent().getExtras();
        int i = b.getInt("index", 0);
        
	    if (i >= 0){
	        this.index = i;
	        this.f = new File(b.getString("file"));
	        reader = Reader.reader;
	        
	        Log.d("Progress", "Reading file "+f.getAbsolutePath());
	        
	        ProgressThread thread = new ProgressThread();
	        thread.start();
        }
        
    }
    
    /**
     * @param file File to be tested. If supported, the file is processed.
     * */
    public void parseFile(File file){
    	
    	if (ImageParser.isSupportedImage(file)){
    		if (file.length() > 0) reader.addImageToCache(file.getAbsolutePath(), file.getAbsolutePath());
    		//Log.e("New file", file.getAbsolutePath());
    	}else{
    		Log.d("Progress", getString(R.string.unsupported_file) + file.getName());
    	}
    	
    }
    
    /**
     * @param dir Directory to process the contents of
     * */
    public void parseDir(File dir, int curLevel){
    	SettingsManager settings = MainActivity.mainActivity.getSettings();
    	File[] files = dir.listFiles();
    	Arrays.sort(files);
    	for (File f : files){
    		if (f.isFile()){
    			parseFile(f);
    		}else{
    			if (curLevel < settings.getRecursionLevel() || settings.getRecursionLevel() == SettingsManager.RECURSION_ALL){
    				parseDir(f, curLevel + 1);
    			}
    		}
    	}
    }
    
    /**
     * @param file File to parse; tests if the file is a supported archive
     * */
    public boolean parseArchive(){
    	    	
    	try {
    		
    		this.temp = ArchiveParser.parseArchive(f, reader);
    		int archiveIndex = reader.getSettings().getArchiveModeIndex(); //0 = do as I please, 1 = Index only, 2 = progressive
    		
    		if (temp == null){
    			reader.runOnUiThread(new MessageThread(reader.getString(R.string.archive_read_error), reader));
    			return true;
    		}
    		
    		if (temp instanceof SteppableArchive){
    			reader.setCache((SteppableArchive) this.temp);
    		}else if (archiveIndex == 1){
    			reader.confirm(R.string.file_confirm, R.string.file_deny, 
    					reader.getString(R.string.archive_index_error) + "\n" + 
    							reader.getString(R.string.archive_index_error2), this);
    			return false;
    		}else{
    			extract(temp);
    		}
        	
    		reader.setCacheIndex(this.index == -1 ? 0 : this.index);
    		
		} catch (ZipException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (RarException e) {
			e.printStackTrace();
			return false;
		}
    	
    	return true;
    	    	
    }
    
    @Override
    public void accept(){
    	reader.setCache((SteppableArchive) this.temp);
    	finish();
    }
    
    @Override
    public void decline(){
    	finish();
    }
    
    /**
     * Called once processing is done. 
     * Has the parent process the temp file already sent to it.
     * */
    public void finish(){
    	reader.clearTempFile();
    	super.finish();
    }
    
    public void oldFinish(){
    	super.finish();
    }
    
    /**
     * @param temp Archive to extract
     * */
    private void extract(ReadableArchive temp){
    	
    	File tmpDir = reader.getCacheDir();
    	File jTmp = new File(tmpDir + File.separator + "JComic");
    	if (jTmp.exists()){
        	File[] files = jTmp.listFiles();
        	Arrays.sort(files);
        	for (File f : files){
        		f.delete();
        	}
    	}else{
    		jTmp.mkdirs();
    	}
    	
    	temp.extractContentsToDisk(jTmp, null);
    	File[] files = jTmp.listFiles();
    	Arrays.sort(files);
    	for (File f : files){
    		f.deleteOnExit();
    	}
    	jTmp.deleteOnExit();
    	reader.setCache(new FileCache(reader, f.getAbsolutePath()));
    	parseDir(jTmp, 0);
    	
    }
	
}