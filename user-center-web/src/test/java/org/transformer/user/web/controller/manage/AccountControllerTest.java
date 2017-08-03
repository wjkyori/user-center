package org.transformer.user.web.controller.manage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.transformer.user.BaseWebControllerTest;
import org.transformer.user.service.UserService;
import org.transformer.user.web.controller.manage.AccountController;

import javax.annotation.Resource;

public class AccountControllerTest extends BaseWebControllerTest {

  protected MockMvc mvc;

  @Mock
  private UserService userService;

  @InjectMocks
  @Resource
  private AccountController userController;

  @Before
  public void setUp() {
    mvc = MockMvcBuilders.standaloneSetup(userController).build();

  }

  @Test
  public void testFindUser() throws Exception {
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/manage/accounts");
    MvcResult result = mvc.perform(request).andReturn();
    logger.info(result.getResponse().getContentAsString());
    Assert.assertNotEquals(result.getResponse().getContentAsString(), null);
  }

}
