
package org.mule.modules.marvel.automation.testrunners;

import org.junit.runner.RunWith;
import org.mule.modules.marvel.automation.RegressionTests;
import org.mule.modules.marvel.automation.testcases.GetCharactersTestCases;

@RunWith(org.junit.experimental.categories.Categories.class)
@org.junit.experimental.categories.Categories.IncludeCategory(RegressionTests.class)
@org.junit.runners.Suite.SuiteClasses({
    GetCharactersTestCases.class
})
public class RegressionTestSuite {


}
