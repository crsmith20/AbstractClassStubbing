

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/root-context.xml")
public class BugTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(BugTest.class);
	
	private MockMvc mockMvc;
	
	@Spy
	private AbstractClazz abstractClazz;
	
	@InjectMocks
	private ClazzController controller;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testBug() throws Exception {
		when(abstractClazz.getResults(Mockito.anyString(), Mockito.anyString(), Mockito.anyListOf(Long.class),
				Mockito.anyListOf(String.class), Mockito.anyListOf(String.class), Mockito.anyListOf(String.class),
				Mockito.anyListOf(Boolean.class), Mockito.anyListOf(Long.class)))
						.thenReturn("Success");
		
		MvcResult result = mockMvc.perform(get("/productFinder?g=something")
				.header("Content-type", "application/json")
				.header("website", "somewebsite.com"))
				.andExpect(status().isOk()) // if this is toggled off, it verifies that everything was called as expected
				.andReturn();
				
		verify(abstractClazz).getResults("something", "somewebsite.com", new ArrayList<Long>(), null, null, null, null, new ArrayList<Long>());
		LOG.info("Verification Successful!");
		assertEquals("Stub happens", "Success", result.getResponse().getContentAsString());
	}
}
