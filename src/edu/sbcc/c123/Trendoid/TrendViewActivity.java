package edu.sbcc.c123.Trendoid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class TrendViewActivity extends Activity {

	private WebView trendWebView;
	private ShareActionProvider mShareActionProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trend_view);

		trendWebView = (WebView) findViewById(R.id.trend_webview);
		WebSettings webSettings = trendWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		// TODO may need change
		trendWebView.getSettings().setDomStorageEnabled(true);

		String itemText = (String) getIntent().getExtras().get("item");
		String sourceText = (String) getIntent().getExtras().get("source");
		setTitle(itemText);
		trendWebView.setWebViewClient(new MyWebViewClient());

		if (sourceText.equals("google") || sourceText.equals("aol"))
			trendWebView.loadUrl(createGoogleUrl(itemText));
		else if (sourceText.equals("twitter")) {
			trendWebView.loadUrl(createTwitterUrl(itemText));
		} else if (sourceText.equals("wikipedia")) {
			String wikiurl = "https://en.m.wikipedia.org/wiki/";
			String concat = itemText.replace(" ", "_");
			String url = wikiurl + concat;
			trendWebView.loadUrl(url);
		}

	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			boolean shouldOverride = false;
			if (url.startsWith("https://")) { // NON-NLS
				// DO SOMETHING
				shouldOverride = true;
			}
			return shouldOverride;
		}
	}

	@Override
	public void onBackPressed() {
		if (trendWebView.canGoBack())
			trendWebView.goBack();
		else
			super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trend_view, menu);

		// Set up ShareActionProvider's default share intent
		MenuItem item = menu.findItem(R.id.menu_item_share);
		mShareActionProvider = (ShareActionProvider) MenuItemCompat
				.getActionProvider(item);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if (id == R.id.menu_item_share) {
			String url = trendWebView.getUrl();
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_TEXT, url);
			shareIntent.setType("text/plain");
			startActivity(shareIntent);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public String createGoogleUrl(String trendtext) {

		trendtext.replace(' ', '+');
		String baseUrl = "https://www.google.com/search?q=";
		String url = baseUrl + trendtext;

		return url;
	}

	public String createTwitterUrl(String itemText) {

		String baseUrl = "https://mobile.twitter.com/search?q=";
		String url = "";

		if (itemText.startsWith("#")) {
			baseUrl += "%23";
			url = baseUrl + itemText.substring(1);
		} else {
			itemText.replace(" ", "+");
			url = baseUrl + itemText;
		}
		return url;
	}
}
