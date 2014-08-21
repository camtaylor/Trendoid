package edu.sbcc.c123.Trendoid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TrendListActivity extends Activity implements OnItemClickListener {

	ArrayAdapter<Trend> trendAdapter;
	ArrayList<Trend> trendArray = new ArrayList<Trend>();
	ListView trendView;



	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setTitle("Trending Now");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trend_list);

		GoogleTrends google = new GoogleTrends();
		AolTrends aol = new AolTrends();
		TwitterTrends twitter = new TwitterTrends();
		WikipediaTrends wikipedia = new WikipediaTrends();

		google.execute();
		twitter.execute();
		wikipedia.execute();
		//aol.execute();

	}


	// async task to get google trends
	private class GoogleTrends extends
			AsyncTask<ArrayList<Trend>, ArrayList<Trend>, ArrayList<Trend>> {

		@Override
		protected ArrayList<Trend> doInBackground(ArrayList<Trend>... params) {
			// Gets the jsoup document
			try {
				Document doc = Jsoup.connect(
						"http://google.com/trends/hottrends/atom/hourly").get();
				Elements content = doc.getElementsByTag("content");
				String html = content.text();

				Document body = Jsoup.parseBodyFragment(html);
				Elements trends = body.getElementsByTag("a");

				for (Element el : trends) {
					trendArray.add(new Trend("google", el.text(), ""));
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			ArrayList<Trend> trendsList = trendArray;
			return trendsList;
		}

		@Override
		protected void onPostExecute(ArrayList<Trend> result) {
			// TODO Auto-generated method stub
			createList();
		}

	}

	private class AolTrends extends
			AsyncTask<ArrayList<Trend>, ArrayList<Trend>, ArrayList<Trend>> {

		@Override
		protected ArrayList<Trend> doInBackground(ArrayList<Trend>... params) {
			// Gets the jsoup document
			try {
				Document doc = Jsoup
						.connect("http://search.aol.com/aol/trends").get();
				Elements content = doc.getElementsByTag("ol");
				Elements trends = content.select("a");

				int i = 0;
				for (Element el : trends) {
					if (i < 20) {
						trendArray.add(new Trend("aol", el.text(), ""));
						i++;
					} else {
						break;
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			ArrayList<Trend> trendsList = trendArray;
			return trendsList;
		}

		@Override
		protected void onPostExecute(ArrayList<Trend> result) {
			// TODO Auto-generated method stub
			createList();
		}

	}

	// TODO delete below *************
	private class TwitterTrends extends
			AsyncTask<ArrayList<Trend>, ArrayList<Trend>, ArrayList<Trend>> {

		@Override
		protected ArrayList<Trend> doInBackground(ArrayList<Trend>... params) {
			// Gets the jsoup document
			try {
				Document doc = Jsoup.connect("http://feedtwit.com/trends.rss")
						.get();
				Elements content = doc.getElementsByTag("item");
				Elements trends = content.select("title");

				int i = 0;
				for (Element el : trends) {
					if (i < 10) {
						trendArray.add(new Trend("twitter", el.text(), ""));
						i++;
					} else {
						break;
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			ArrayList<Trend> trendsList = trendArray;
			return trendsList;
		}

		@Override
		protected void onPostExecute(ArrayList<Trend> result) {
			// TODO Auto-generated method stub
			createList();
		}

	}

	// TODO delete below *********
	private class WikipediaTrends extends
			AsyncTask<ArrayList<Trend>, ArrayList<Trend>, ArrayList<Trend>> {

		@Override
		protected ArrayList<Trend> doInBackground(ArrayList<Trend>... params) {
			// Gets the jsoup document
			try {
				Document doc = Jsoup
						.connect(
								"http://tools.wmflabs.org/wikitrends/english-uptrends-today.html")
						.get();
				Elements content = doc.getElementsByTag("h3");
				Elements trends = content.select("a");

				for (Element el : trends) {
					trendArray.add(new Trend("wikipedia", el.text(), ""));
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			ArrayList<Trend> trendsList = trendArray;
			return trendsList;
		}

		@Override
		protected void onPostExecute(ArrayList<Trend> result) {
			// TODO Auto-generated method stub
			createList();
		}

	}

	public void createList() {
		trendAdapter = new TrendAdapter(this, trendArray);
		trendView = (ListView) findViewById(R.id.trend_view);
		trendView.setAdapter(trendAdapter);
		trendView.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trend_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			startActivity(new Intent(this, SetPreferenceActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this, TrendViewActivity.class);
		intent.putExtra("position", position);
		intent.putExtra("item", parent.getItemAtPosition(position).toString());
		intent.putExtra("source", trendArray.get(position).getSource());
		startActivityForResult(intent, 0);
	}

}
