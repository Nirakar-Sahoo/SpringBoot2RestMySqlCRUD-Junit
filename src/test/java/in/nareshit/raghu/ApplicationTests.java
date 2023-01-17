package in.nareshit.raghu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
@DisplayName("TEST PRODUCT REST SERVICE")
@TestMethodOrder(OrderAnnotation.class)
class ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Order(1)
	@DisplayName("SAVE ONE PRODUCT")
	//@Disabled
	public void testSaveOneProduct() throws Exception {
		//create http request
		MockHttpServletRequestBuilder request=
				MockMvcRequestBuilders.post("/rest/product/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"prodName\":\"Table\",\"prodCost\":20943.45,\"prodVendor\":\"IKEA\"}");

		//execute request and get result
		MvcResult result=mockMvc.perform(request).andReturn();

		//process the result and read response
		MockHttpServletResponse response=result.getResponse();

		//perform validation/assert it
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertNotNull(response.getContentAsString());
		if(!response.getContentAsString().contains("Product Created")) {
			fail("may not be saved");
		}

	}

	@Test
	@DisplayName("GET ALL PRODUCTS")
	@Order(2)
	public void testFetchAllProducts() throws Exception {
		//create http request
		MockHttpServletRequestBuilder request=
				MockMvcRequestBuilders.get("/rest/product/all");
		//execute request and get result
		MvcResult result=mockMvc.perform(request).andReturn();
		//read response from MvcResult
		MockHttpServletResponse response=result.getResponse();
		//validate it
		assertEquals(HttpStatus.OK.value(),response.getStatus());
		assertNotNull(response.getContentAsString());
		assertEquals("application/json",response.getContentType());

	}

	@Test
	@DisplayName("GET ONE PRODUCT BY ID-EXIST")
	@Order(3)
	public void testGetOneProductExist() throws Exception {
		//create http request
		MockHttpServletRequestBuilder request=
				MockMvcRequestBuilders.get("/rest/product/find/{id}",1);
		//execute request and get result
		MvcResult result=mockMvc.perform(request).andReturn();
		//read response from MvcResult
		MockHttpServletResponse response=result.getResponse();
		//validate it
		assertEquals(HttpStatus.OK.value(),response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON_VALUE,response.getContentType());

	}

	@Test
	@DisplayName("GET ONE PRODUCT BY ID-NOT EXIST")
	@Order(4)
	public void testGetOneProductNotExist() throws Exception {
		//create http request
		MockHttpServletRequestBuilder request=
				MockMvcRequestBuilders.get("/rest/product/find/{id}",999);
		//execute request and get result
		MvcResult result=mockMvc.perform(request).andReturn();
		//read response from MvcResult
		MockHttpServletResponse response=result.getResponse();
		//validate it
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON_VALUE,response.getContentType());
		if(!response.getContentAsString().contains("PRODUCT NOT FOUND")) {
			fail("may be given id product exist");
		}
	}

	@Test
	@DisplayName("UPDATE FULLY PRODUCT-EXIST")
	@Order(5)
	public void testUpdateFullProductExist() throws Exception {
		//create http request
		MockHttpServletRequestBuilder request=
				MockMvcRequestBuilders.put("/rest/product/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"prodId\":1,\"prodName\":\"Table1\",\"prodCost\":35943.45,\"prodVendor\":\"IKEA1\"}");

		//execute request and get result
		MvcResult result=mockMvc.perform(request).andReturn();

		//process the result and read response
		MockHttpServletResponse response=result.getResponse();

		//perform validation/assert it
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(response.getContentAsString());
		assertEquals("text/plain;charset=UTF-8",response.getContentType());
		if(!response.getContentAsString().contains("Product updated")) {
			fail("may be id not exist");
		}
	}

	@Test
	@DisplayName("UPDATE FULLY PRODUCT-NOT EXIST")
	@Order(6)
	public void testUpdateFullProductNotExist() throws Exception {
		//create http request
		MockHttpServletRequestBuilder request=
				MockMvcRequestBuilders.put("/rest/product/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"prodId\":99,\"prodName\":\"Table1\",\"prodCost\":35943.45,\"prodVendor\":\"IKEA1\"}");

		//execute request and get result
		MvcResult result=mockMvc.perform(request).andReturn();

		//process the result and read response
		MockHttpServletResponse response=result.getResponse();

		//perform validation/assert it
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
		assertNotNull(response.getContentAsString());
		assertEquals(MediaType.APPLICATION_JSON_VALUE,response.getContentType());
		if(!response.getContentAsString().contains("PRODUCT NOT FOUND")) {
			fail("may be product available");
		}
	}

	@Test
	@DisplayName("UPDATE PARTIALLY ONE PRODUCT-EXIST")
	@Order(7)
	public void testPartialUpdateProductExist() throws Exception {
		//create http request
		MockHttpServletRequestBuilder request=
				MockMvcRequestBuilders.patch("/rest/product/modify/{id}/{name}",1,"Table3");

		//execute request and get result
		MvcResult result=mockMvc.perform(request).andReturn();

		//process the result and read response
		MockHttpServletResponse response=result.getResponse();

		//perform validation/assert it
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(response.getContentAsString());
		if(!response.getContentAsString().contains("Product Name updated")) {
			fail("may not be updated");
		}
	}

	@Test
	@DisplayName("UPDATE PARTIALLY ONE PRODUCT-NOT EXIST")
	@Order(8)
	public void testPartialUpdateProductNotExist() throws Exception {
		//create http request
		MockHttpServletRequestBuilder request=
				MockMvcRequestBuilders.patch("/rest/product/modify/{id}/{name}",599,"Table5");

		//execute request and get result
		MvcResult result=mockMvc.perform(request).andReturn();

		//process the result and read response
		MockHttpServletResponse response=result.getResponse();

		//perform validation/assert it
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
		assertNotNull(response.getContentAsString());
		assertEquals(MediaType.APPLICATION_JSON_VALUE,response.getContentType());
		if(!response.getContentAsString().contains("PRODUCT NOT FOUND")) {
			fail("may be product available");
		}
	}

	//@Disabled
	@Test
	@DisplayName("REMOVE ONE PRODUCT BY ID-EXIST")
	@Order(9)
	public void testDeleteOneProductExist() throws Exception {
		//create http request
		MockHttpServletRequestBuilder request=
				MockMvcRequestBuilders.delete("/rest/product/remove/{id}",1);
		//execute request and get result
		MvcResult result=mockMvc.perform(request).andReturn();
		//read response from MvcResult
		MockHttpServletResponse response=result.getResponse();
		//validate it
		assertEquals(HttpStatus.OK.value(),response.getStatus());
		assertEquals("text/plain;charset=UTF-8",response.getContentType());
		if(!response.getContentAsString().contains("product deleted")) {
			fail("may be given id not exist");
		}
	}

	//@Disabled
	@Test
	@DisplayName("REMOVE ONE PRODUCT BY ID-NOT EXIST")
	@Order(10)
	public void testDeleteOneProductNotExist() throws Exception {
		//create http request
		MockHttpServletRequestBuilder request=
				MockMvcRequestBuilders.delete("/rest/product/remove/{id}",999);
		//execute request and get result
		MvcResult result=mockMvc.perform(request).andReturn();
		//read response from MvcResult
		MockHttpServletResponse response=result.getResponse();
		//validate it
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),response.getStatus());
		assertEquals("application/json",response.getContentType());
		if(!response.getContentAsString().contains("PRODUCT NOT FOUND")) {
			fail("may be given id product exist");
		}
	}


}
