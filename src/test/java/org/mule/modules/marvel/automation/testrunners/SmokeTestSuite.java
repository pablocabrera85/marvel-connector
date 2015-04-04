
package org.mule.modules.marvel.automation.testrunners;

import org.junit.runner.RunWith;
import org.mule.modules.marvel.automation.SmokeTests;
import org.mule.modules.marvel.automation.testcases.GetCharactersTestCases;

@RunWith(org.junit.experimental.categories.Categories.class)
@org.junit.experimental.categories.Categories.IncludeCategory(SmokeTests.class)
@org.junit.runners.Suite.SuiteClasses({
    GetCharactersTestCases.class
})
public class SmokeTestSuite {


}
