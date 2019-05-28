package com.store.retail.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.store.retail.service.StoreService;

@RunWith(SpringJUnit4ClassRunner.class)
public class BillGeneratorControllerTest {

	private MockMvc mvc;

	@Mock
	StoreService service;

	@InjectMocks
	BillGeneratorController billGeneratorController;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(billGeneratorController).build();
	}

	@Test
	public void generateNetPayment() throws Exception {
		String jsonStr = new String(
				Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("input/Product.json").toURI())));
		// when(service.getNetPayment(users.get(0), products)).thenReturn(28240.0);
		this.mvc.perform(MockMvcRequestBuilders.post("/bill-generate/REG01")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonStr)).andExpect(status().isOk()).andReturn();
	}
}