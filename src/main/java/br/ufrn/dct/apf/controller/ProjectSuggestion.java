package br.ufrn.dct.apf.controller;

public class ProjectSuggestion {

    String value;
    String data;

    ProjectSuggestion(String name) {
          this.value = name;
          this.data = "";
        }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
