package com.cristian.simplestore.infrastructure.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.cristian.simplestore.application.command.CreateCategoryCommand;
import com.cristian.simplestore.application.command.UpdateCategoryCommand;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.ports.repository.CategoryRepository;
import com.cristian.simplestore.infrastructure.controllers.e2e.category.databuilder.CreateCategoryCommandDataBuilder;
import com.cristian.simplestore.infrastructure.controllers.e2e.category.databuilder.UpdateCategoryCommandDataBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
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

  @Test
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
  
  @Test
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
