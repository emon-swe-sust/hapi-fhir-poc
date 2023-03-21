package com.example.hapifhir.bundle.composition;

import com.example.hapifhir.bundle.Resource;
import com.example.hapifhir.bundle.resourceProperty.Reference;
import com.example.hapifhir.bundle.resourceProperty.Section;
import com.example.hapifhir.bundle.resourceProperty.Type;


public class Composition extends Resource {
    private String date;
    private String title;
    private String status;
    private Reference author;
    private Reference encounter;
    private Section section;
    private Reference subject;
    private Type type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Reference getAuthor() {
        return author;
    }

    public void setAuthor(Reference author) {
        this.author = author;
    }

    public Reference getEncounter() {
        return encounter;
    }

    public void setEncounter(Reference encounter) {
        this.encounter = encounter;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Reference getSubject() {
        return subject;
    }

    public void setSubject(Reference subject) {
        this.subject = subject;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
