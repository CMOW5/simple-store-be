package com.cristian.simplestore.infrastructure.database.image;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cristian.simplestore.domain.image.Image;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "image")
@Table(name = "images")
@Data
@NoArgsConstructor
public class ImageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String fileName;

	public ImageEntity(String name) {
		this.fileName = name;
	}

	public ImageEntity(Long id, String name) {
		this.id = id;
		this.fileName = name;
	}

	public static ImageEntity fromDomain(Image image) {
		if (image == null)
			return null;
		return new ImageEntity(image.getId(), image.getFileName());
	}

	public static Image toDomain(ImageEntity entity) {
		if (entity == null)
			return null;
		return new Image(entity.getId(), entity.getFileName());
	}
	
	public static List<Image> toDomain(List<ImageEntity> images) {
		return images.stream().map(ImageEntity::toDomain).collect(Collectors.toList());
	}

	public static List<ImageEntity> fromDomain(List<Image> images) {
		return images.stream().map(ImageEntity::fromDomain).collect(Collectors.toList());
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ImageEntity image = (ImageEntity) o;
		return Objects.equals(fileName, image.fileName) && Objects.equals(id, image.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fileName, id);
	}
}
