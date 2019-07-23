package com.cristian.simplestore.persistence.entities;

import java.io.Serializable;
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
import lombok.Data;
import lombok.NoArgsConstructor;

// @Entity
// @Scope("prototype")
// @Table(name = "images")
@Data
@NoArgsConstructor
public class Image implements Serializable {
  private static final long serialVersionUID = 3603066326555414036L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  @Transient
  private String url;

  @OneToMany(mappedBy = "image", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProductImage> owners = new ArrayList<>();

  public String getName() {
    if (isUrl()) {
      return name;
    } else {
      return StringUtils.getFilename(name);
    }
  }

  public String getUrl() {
    if (isUrl()) {
      return name;
    } else {
      return convertNameToUrl(getName());
    }
  }

  // TODO FIX THE HARCODED ROUTE
  private String convertNameToUrl(String name) {
    return "http://localhost:8000/api/images/" + name;
  }

  public boolean isUrl() {
    try {
      new URL(name);
      return true;
    } catch (MalformedURLException e) {
      return false;
    }
  }

}
