package com.cristian.simplestore.infrastructure.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.cristian.simplestore.application.category.create.CreateCategoryCommand;
import com.cristian.simplestore.application.category.update.UpdateCategoryCommand;
import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.repository.CategoryRepository;
import com.cristian.simplestore.infrastructure.controllers.e2e.category.databuilder.CreateCategoryCommandDataBuilder;
import com.cristian.simplestore.infrastructure.controllers.e2e.category.databuilder.UpdateCategoryCommandDataBuilder;

public class CategoryControllerHexTest {

  private static final String CATEGORIES_API = "/api/hex/admin/categories";
  
  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  private CategoryRepository categoryRepo;
    
  @Before
  public void setUp() {
    categoryRepo.deleteAll();
  }
  
  public Category saveToDb() {
	  CreateCategoryCommand command = new CreateCategoryCommandDataBuilder().build();
	  Category category = new Category(command.getName(), null, null);
	  return categoryRepo.save(category);
  }

  public void testItCreatesACategory() throws Exception {
    // arrange
    CreateCategoryCommand command = new CreateCategoryCommandDataBuilder().build();
    
    MockHttpServletRequestBuilder builder =
      MockMvcRequestBuilders.multipart(CATEGORIES_API)
                            .file((MockMultipartFile)command.getImage())
                            .param("name", command.getName());
    
//    builder.with(new RequestPostProcessor() {
//        @Override
//        public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
//            request.setMethod("POST");
//            return request;
//        }
//    });
    
    mockMvc.perform(builder).andExpect(status().isCreated());  
  }
  
  public void testItUpdatesACategory() throws Exception {
    // arrange
	Category category = saveToDb();
	  
	UpdateCategoryCommand command = new UpdateCategoryCommandDataBuilder(category.getId()).build();
    
    MockHttpServletRequestBuilder builder =
      MockMvcRequestBuilders.multipart(CATEGORIES_API)
                            .file((MockMultipartFile)command.getImage())
                            .param("id", String.valueOf(command.getId()))
                            .param("name", command.getName());
    
    
    builder.with(new RequestPostProcessor() {
        @Override
        public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
            request.setMethod("PUT");
            return request;
        }
    });
    
    mockMvc.perform(builder).andExpect(status().isOk());  
  }
}
