package com.cristian.simplestore.persistence.entities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Scope("prototype")
@Table(name = "images")
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String name;
	
	@Transient
	private String url;
	
	@OneToMany(
			mappedBy = "image",
			cascade = CascadeType.ALL,
			orphanRemoval = true
		)
	private List<ProductImage> owners = new ArrayList<>();

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
	
	@JsonIgnore
	public boolean isAUrl() {
		return this.isValidUrl(this.name);
	}
	
	private boolean isValidUrl(String url) {
		try {
			new URL(this.name);
			return true;
		} catch (MalformedURLException e) {
			return false;
		}
	}

	@JsonIgnore
	public List<ProductImage> getOwners() {
		return owners;
	}
}
