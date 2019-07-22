package com.cristian.simplestore.infrastructure.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.cristian.simplestore.application.command.CreateCategoryCommand;
import com.cristian.simplestore.domain.ports.repository.CategoryRepository;
import com.cristian.simplestore.infrastructure.controllers.databuilder.CreateCategoryCommandDataBuilder;

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

  @Test
  public void testItCreatesACategory() throws Exception {
    // arrange
    CreateCategoryCommand command = new CreateCategoryCommandDataBuilder().build();
    
    MockHttpServletRequestBuilder builder =
      MockMvcRequestBuilders.multipart(CATEGORIES_API)
                            .file((MockMultipartFile)command.getImage())
                            .param("name", command.getName());
    
    mockMvc.perform(builder).andExpect(status().isCreated());  
  }
}
