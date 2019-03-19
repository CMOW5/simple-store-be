package com.cristian.simplestore.persistence.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product_image")
public class ProductImage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3567125929224490136L;

	@Id
	@ManyToOne
	private Product product;

	@Id
	@ManyToOne
	private Image image;
	
	public ProductImage() {}

	public ProductImage(Product product, Image image) {
		this.product = product;
		this.image = image;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product person) {
		this.product = person;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		ProductImage that = (ProductImage) o;
		return Objects.equals(product, that.product) &&
				Objects.equals(image, that.image);
	}

	@Override
	public int hashCode() {
		return Objects.hash(product, image);
	}
}
