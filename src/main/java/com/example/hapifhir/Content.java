package com.example.hapifhir;

public class Content {
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

    public Content(String content){
        this.content = content;
    }
}
