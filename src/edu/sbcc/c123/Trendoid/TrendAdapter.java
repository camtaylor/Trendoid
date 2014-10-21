package edu.sbcc.c123.Trendoid;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TrendAdapter extends ArrayAdapter<Trend> {

	Activity context;
	private List<Trend> trends;

	/***
	 * Calls the super constructor and saves the adapter's context as well as
	 * the model data (i.e. the courses).
	 * 
	 * @param context
	 * @param trends
	 */
	public TrendAdapter(Activity context, List<Trend> trends) {
		super(context, R.layout.trend_layout, trends);

		this.context = context;
		this.trends = trends;
	}

	/**
	 * Returns the item at the given position.
	 */
	@Override
	public Trend getItem(int position) {
		return trends.get(position);
	}

	/**
	 * getView()'s job is to return a View that has been filled with data for
	 * the row given by position.
	 */
	public View getView(int position, View convertView, ViewGroup parent) {

		// Get the course that corresponds to the row given by position.
		Trend trend = trends.get(position);

		// Inflate our row layout into the view that that will be returned by
		// this function.
		LayoutInflater inflater = context.getLayoutInflater();
		View trendView = inflater.inflate(R.layout.trend_layout, null);

		// Display company logo
		ImageView imageView = (ImageView) trendView
				.findViewById(R.id.imageView);
		if (trend.getSource() == "google")
			imageView.setImageResource(R.drawable.googlelogo);
		else if (trend.getSource() == "twitter")
			imageView.setImageResource(R.drawable.twitterlogo);
		else if (trend.getSource() == "wikipedia")
			imageView.setImageResource(R.drawable.wikipedialogo);

		TextView nameView = (TextView) trendView.findViewById(R.id.nameView);
		nameView.setText(trend.toString());

		SharedPreferences prefs = context.getSharedPreferences("pref_general",
				0);
		boolean nightmode = prefs.getBoolean("checkBox", false);

		if (nightmode) {
			trendView.setBackgroundColor(Color.BLACK);
		}

		return (trendView);
	}
}