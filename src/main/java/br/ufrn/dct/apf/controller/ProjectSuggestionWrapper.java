package br.ufrn.dct.apf.controller;

import java.util.List;

import br.ufrn.dct.apf.model.Project;

public class ProjectSuggestionWrapper {
    
    List<Project> suggestions;

    public List<Project> getSuggestions() {
      return suggestions;
    }

    public void setSuggestions(List<Project> suggestions) {
      this.suggestions = suggestions;
  }

}
