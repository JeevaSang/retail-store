package com.store.retail.controller;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.store.retail.App;

@RunWith(SpringRunner.class)
@WebMvcTest(BillGeneratorController.class)
@ContextConfiguration(classes= {App.class})
public class BillGeneratorControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	private BillGeneratorController billGeneratorController;
	

	@Test
	public void testcase() throws Exception {
		String jsonStr = new String(
				Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("input/Product.json").toURI())));
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/bill-generate/REG01")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonStr)).andReturn();
		String content = mvcResult.getResponse().getContentAsString();
		Assert.assertEquals(content, "28240");
	}
}