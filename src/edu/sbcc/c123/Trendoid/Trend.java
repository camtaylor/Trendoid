package edu.sbcc.c123.Trendoid;

public class Trend {
	
	private String Url;
	private String Source;
	private String Text;
	
	
	public Trend(String source, String text, String url){
		this.Source = source;
		this.Url = url;
		this.Text = text;
	}
	
	public String getUrl() {
		return this.Url;
	}
	public void setUrl(String url) {
		this.Url = url;
	}
	public String getText() {
		return this.Text;
	}
	public void setText(String text) {
		this.Text = text;
	}
	public String getSource() {
		return this.Source;
	}
	public void setSource(String source) {
		this.Source = source;
	}
	
	public String toString() {
		return this.getText();
	}

}
