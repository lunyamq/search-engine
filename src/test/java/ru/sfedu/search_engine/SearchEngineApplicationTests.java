package ru.sfedu.search_engine;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.sfedu.search_engine.repository.ProductRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SearchEngineApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductRepository repository;

	private final long ID = 998L;

    @BeforeAll
	void setup() throws Exception {
		final String PRODUCT_JSON = String.format("""
        {
            "id": %d,
            "name": "Электрогитара",
            "price": 4599.5
        }
        """, ID);
        mockMvc.perform(post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(PRODUCT_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testSearchExactMatch() throws Exception {
		// exact match
		mockMvc.perform(get("/search")
						.param("query", "электрогитара")
						.param("maxDistance", "0")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].productName").value("Электрогитара"))
				.andExpect(jsonPath("$[0].price").value(4599.5));
	}

	@Test
	void testSearchWithTypo() throws Exception {
		// search w/ type-error
		mockMvc.perform(get("/search")
						.param("query", "электрагетара")
						.param("maxDistance", "2")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].correctedWord").value("электрогитара"));
	}

	@AfterAll
	void cleanup() {
		repository.deleteById(ID);
	}
}
