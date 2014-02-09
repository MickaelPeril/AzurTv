package com.azurtv.podcast;

// cette classe represente un podcast et contient les informations relatives a celui ci.
public class Podcast {

	private String title = null;
	private String description = null;
	
	public Podcast() {
	}
	
	public Podcast(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
