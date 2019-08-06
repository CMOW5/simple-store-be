package com.cristian.simplestore.application.product.command;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateProductCommand {
  private Long id;	
	
  private String name;
  
  private String description;

  private double price;

  private double priceSale;

  private boolean inSale;

  private boolean active;

  private Long categoryId;

  private List<MultipartFile> images = new ArrayList<>();
  
  private List<Long> imagesIdsToDelete = new ArrayList<>();

  private Long stock;
}
