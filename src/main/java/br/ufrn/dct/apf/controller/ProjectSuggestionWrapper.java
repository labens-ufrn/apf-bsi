package br.ufrn.dct.apf.controller;

import java.util.List;

public class ProjectSuggestionWrapper {

    private List<ProjectSuggestion> suggestions;

    public List<ProjectSuggestion> getSuggestions() {
      return suggestions;
    }

    void setSuggestions(List<ProjectSuggestion> suggestions) {
      this.suggestions = suggestions;
  }

}
