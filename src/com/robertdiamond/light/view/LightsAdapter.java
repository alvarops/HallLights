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
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.TextView;

import com.robertdiamond.light.R;
import com.robertdiamond.light.model.Light;

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
		this.radioGroupListener = (OnCheckedChangeListener) context;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Light light = getItem(position);
		ViewHolder holder;

		try {
			if (convertView == null) {
				convertView = this.inflator.inflate(layout, null);
				holder = new ViewHolder();
				holder.nodeId = (TextView) convertView
						.findViewById(R.id.node_id);
				holder.speed = (TextView) convertView.findViewById(R.id.speed);
				holder.speedBar = (SeekBar) convertView
						.findViewById(R.id.speedBar);
				holder.colorButton = (Button) convertView
						.findViewById(R.id.color_button);
				holder.radioGroup = (RadioGroup) convertView
						.findViewById(R.id.rg_all_lights);
				holder.radioGroup
						.setOnCheckedChangeListener(radioGroupListener);
				holder.speedBar.setOnTouchListener(new SeekBarListener(holder));
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			String nodeId = light.getNodeId();
			int speed = light.getSpeed();
			Integer color = Color.rgb(light.getRed(), light.getGreen(),
					light.getBlue());

			holder.nodeId.setText(nodeId);
			holder.speed.setText(Integer.toString(speed));
			holder.speedBar.setProgress(speed);
			holder.speedBar.setTag(position);
			holder.colorButton.setTag(position);
			convertView.setBackgroundColor(color);
			
		} catch (Exception e) {
			Log.e(TAG, e.toString(), e);
		}
		return convertView;
	}

	static class ViewHolder {
		RadioGroup radioGroup;
		Button colorButton;
		TextView nodeId;
		TextView speed;
		SeekBar speedBar;

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
