package org.mule.modules.marvel.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.marvel.automation.MarvelTestParent;
import org.mule.modules.marvel.automation.RegressionTests;
import org.mule.modules.marvel.automation.SmokeTests;

import com.pcab.marvel.model.Character;

public class GetCharactersTestCases extends MarvelTestParent {

	@Before
	public void setup() throws Exception {
		initializeTestRunMessage("getCharactersTestData");
	}

	@Category({ RegressionTests.class, SmokeTests.class })
	@Test
	public void testGetCharacters() throws Exception {
		org.mule.streaming.ConsumerIterator result = runFlowAndGetPayload("get-characters");
		int counter = 0;
		int limit = 150;
		while (result.hasNext() && counter < limit) {
			counter++;
			Character item = (Character) result.next();
			assertNotNull(item);
		}
		assertEquals(limit, counter);
	}

}
