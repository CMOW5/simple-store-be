package com.cristian.simplestore.persistence.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Data
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@ManyToOne
	private Category parentCategory;
	
	// @OneToMany(mappedBy = "parentCategory" , cascade = CascadeType.ALL, orphanRemoval = true)
	// @JsonIgnore
	// private List<Category> subcategories = new ArrayList<>();
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Product> products = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "image_id")
	private Image image;
	
	public void addSubCategory(Category subcategory) {
		// this.subcategories.add(subcategory);
		// subcategory.setParentCategory(this);
	}
	
	public void addProduct(Product product) {
		products.add(product);
		product.setCategory(this);
	}
	
	public void deleteImage() {
		image = null;
	}
	
	/**
	 * verify if the given category is a sub category of the current category
	 * @param category
	 * @return true if the given category is a sub category of the current object
	 */
    public boolean hasSubcategory(Category category) {
        if (category == null) return false;
        
        Category currentCategory = category;
        
        while(currentCategory.getParentCategory() != null) {
            if (currentCategory.getParentCategory().getId() == id) {
                return true;
            }
            currentCategory = currentCategory.getParentCategory();
        }
        return false;
    }
    
    @Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		Category category = (Category) o;
		return Objects.equals(name, category.name) && 
				Objects.equals(id, category.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, id);
	}
	
}
