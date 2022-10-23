package org.kurron.gurps;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * Entry point for running Cucumber tests.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class CucumberTests {}

