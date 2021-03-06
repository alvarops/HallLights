/*
 * Copyright (C) 2012 Alvaro Pereda - Robert Diamond
 */
package com.robertdiamond.light.view;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.robertdiamond.light.R;
import com.robertdiamond.light.model.Light;
import com.robertdiamond.light.util.ColorUtil;

/**
 * @author Alvaro Pereda
 * 
 */
public class LightsAdapter extends ArrayAdapter<Light> {

	private static final String TAG = "LightsAdapter";
	private Context context;
	private LayoutInflater inflator;
	private int layout;
	OnCheckedChangeListener radioGroupListener;

	/**
	 * @param context
	 * @param textViewResourceId
	 * @param objects
	 */
	public LightsAdapter(Context context, int resource, int textViewResourceId) {
		super(context, textViewResourceId);
		this.context = context;
		this.inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.layout = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Light light = getItem(position);
		ViewHolder holder;

		try {
			if (convertView == null) {
				convertView = this.inflator.inflate(layout, null);
				holder = new ViewHolder();
				holder.nodeId = (TextView) convertView.findViewById(R.id.node_id);
				holder.speed = (TextView) convertView.findViewById(R.id.speed);
				holder.speedBar = (SeekBar) convertView.findViewById(R.id.speedBar);
				holder.colorButton = (Button) convertView.findViewById(R.id.color_button);
				holder.toggleButton = (ToggleButton) convertView.findViewById(R.id.toggleLights);
				holder.speedBar.setOnTouchListener(new SeekBarListener(holder));
				
				/* Text Strings */
				holder.text_chooseALight = (TextView) convertView.findViewById(R.id.choose_a_light);
				holder.text_speed = (TextView) convertView.findViewById(R.id.txt_speed);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			String nodeId = light.getNodeId();
			int speed = light.getSpeed();
			Integer color = light.getColor();
			
			holder.nodeId.setText(nodeId);
			holder.speed.setText(Integer.toString(speed));
			holder.speedBar.setProgress(speed);
			holder.toggleButton.setChecked(color != Color.BLACK);
			
			holder.speedBar.setTag(position);
			holder.colorButton.setTag(position);
			holder.toggleButton.setTag(position);
			
			convertView.setBackgroundColor(color);
			
			/* Set contrasted color for text */
			int contrastColor = ColorUtil.getBestContrast(color);
			int contrastGrey = ColorUtil.getBestGrayContrast(color);
			
			holder.nodeId.setTextColor(contrastColor);
			holder.text_chooseALight.setTextColor(contrastColor);
			holder.text_speed.setTextColor(contrastColor);
			holder.speed.setTextColor(contrastGrey);
			
		} catch (Exception e) {
			Log.e(TAG, e.toString(), e);
		}
		return convertView;
	}

	static class ViewHolder {
		Button colorButton;
		TextView nodeId;
		ToggleButton toggleButton;
		TextView speed;
		SeekBar speedBar;
		TextView text_chooseALight;
		TextView text_speed;
		
	}

	class SeekBarListener implements OnTouchListener {

		private ViewHolder holder;

		public SeekBarListener(ViewHolder holder) {
			this.holder = holder;
		}

		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				holder.speed.setText(Integer.toString(holder.speedBar.getProgress()));
				holder.speed.setVisibility(View.VISIBLE);
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				holder.speed.setText(Integer.toString(holder.speedBar.getProgress()));
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				int position = (Integer) v.getTag();
				getItem(position).setSpeed(holder.speedBar.getProgress());
				holder.speed.setVisibility(View.INVISIBLE);
			}
			return false;
		}
	};

}
