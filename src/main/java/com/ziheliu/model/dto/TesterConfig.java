package com.ziheliu.model.dto;

import java.util.List;

public class TesterConfig {
  public static class TestCase {
    String inputFile;
    String outputFile;
    int score;

    public String getInputFile() {
      return inputFile;
    }

    public String getOutputFile() {
      return outputFile;
    }

    public int getScore() {
      return score;
    }
  }

  boolean isSpecial;

  List<TestCase> testCases;

  public boolean isSpecial() {
    return isSpecial;
  }

  public void setSpecial(boolean special) {
    isSpecial = special;
  }

  public List<TestCase> getTestCases() {
    return testCases;
  }

  public void setTestCases(List<TestCase> testCases) {
    this.testCases = testCases;
  }
}
