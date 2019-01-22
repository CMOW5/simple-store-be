package com.cristian.simplestore.image;

import java.net.MalformedURLException;
import java.net.URL;

import javax.naming.directory.InvalidAttributesException;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

@Entity
@Table(name = "images")
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		if (isValidUrl(this.name)) {
			return this.name;
		} else {
			return StringUtils.getFilename(name);
		}
	}
	
	public String getUrl() {
		if (isValidUrl(this.name)) {
			return this.name;
		} else {
			return convertNameToUrl(this.getName());
		}
	}

	public void setName(String name) {
		this.name = name;
	}
	
	// TODO FIX THE HARCODED ROUTE
	private String convertNameToUrl(String name) {
		return "http://localhost:8000/api/images/" + name;
	}
	
	private boolean isValidUrl(String url) {
		try {
			new URL(this.name);
			return true;
		} catch (MalformedURLException e) {
			return false;
		}
	}
}
