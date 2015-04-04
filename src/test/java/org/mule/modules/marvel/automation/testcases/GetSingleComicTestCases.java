package org.mule.modules.marvel.automation.testcases;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.marvel.automation.MarvelTestParent;
import org.mule.modules.marvel.automation.RegressionTests;
import org.mule.modules.marvel.automation.SmokeTests;

import com.pcab.marvel.model.Comic;

public class GetSingleComicTestCases extends MarvelTestParent {

	@Before
	public void setup() throws Exception {
		initializeTestRunMessage("getComicTestData");
	}

	@Category({ RegressionTests.class, SmokeTests.class })
	@Test
	public void testGetCharacters() throws Exception {
		Comic result = runFlowAndGetPayload("get-comic");
		assertNotNull(result);
	}

}
