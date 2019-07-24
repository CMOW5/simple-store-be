package com.cristian.simplestore.infrastructure.adapters.repository.entities;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.cristian.simplestore.domain.models.Image;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
public class ImageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	public ImageEntity(String name) {
		this.name = name;
	}

	public ImageEntity(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public static ImageEntity fromDomain(Image image) {
		if (image == null)
			return null;
		return new ImageEntity(image.getId(), image.getName());
	}

	public static Image toDomain(ImageEntity entity) {
		if (entity == null)
			return null;
		return new Image(entity.getId(), entity.getName());
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
		return Objects.equals(name, image.name) && Objects.equals(id, image.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, id);
	}
}
