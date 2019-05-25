package com.example.tap.models;

public class NoteModel {
    private String headLine;
    private String body;
    private Integer color;
    private String id;
    private boolean isImportant;

    public NoteModel() {
    }

    public NoteModel(String headLine, String body, Integer color, String id, boolean isImportant) {
        this.id = id;
        this.headLine = headLine;
        this.body = body;
        this.color = color;
        this.isImportant = isImportant;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }
}

