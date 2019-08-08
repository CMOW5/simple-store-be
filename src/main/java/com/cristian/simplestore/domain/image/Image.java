package com.cristian.simplestore.domain.image;

import java.util.Objects;

public class Image {

	private Long id;

	private String fileName;

	public Image(String name) {
		this.fileName = name;
	}

	public Image(Long id, String name) {
		this.id = id;
		this.fileName = name;
	}

	public Long getId() {
		return id;
	}

	public String getFileName() {
		return fileName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Image image = (Image) o;
		return Objects.equals(fileName, image.fileName) && Objects.equals(id, image.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fileName, id);
	}
}
