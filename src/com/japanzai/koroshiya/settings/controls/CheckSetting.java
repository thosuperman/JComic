package com.japanzai.koroshiya.settings.controls;

import android.content.Context;
import android.widget.CheckBox;

/**
 * StateBasedSetting with two states. Defined by a CheckBox.
 * */
public class CheckSetting extends CheckBox {
	
	public CheckSetting(int setting, Context context){

		this(context.getString(setting), true, context);
		
	}
	
	/**
	 * @param setting Name of this setting. 
	 * 				This text will be displayed next to the setting.
	 * @param enabledByDefault If true, the setting is checked by default.
	 * @param context The context within which this class was instantiated.
	 * */
	public CheckSetting(String setting, boolean enabledByDefault, Context context){

		super(context);
		super.setChecked(enabledByDefault);
		super.setText(setting);
		
	}

	public int getState() {
		return this.isChecked() ? 1 : 0;
	}

	public void setState(int state){
		this.setChecked(state == 1);
	}
	
}
