package com.cristian.simplestore.application.command;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.domain.models.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateProductCommand {

  private String name;
  
  private String description;

  private double price;

  private double priceSale;

  private boolean inSale;

  private boolean active;

  private Category category;

  private List<MultipartFile> images = new ArrayList<>();

  private Long stock;
}
