package com.example.hapifhir.bundle;
import com.example.hapifhir.bundle.composition.Composition;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

//@JsonSubTypes({@JsonSubTypes.Type(value = Composition.class, name = "composition")})
@JsonDeserialize(as = Composition.class)
public abstract class Resource {
    private String resourceType;
    private Identifier Identifier;
    private String confidentiality;

    public String getConfidentiality() {
        return confidentiality;
    }

    public void setConfidentiality(String confidentiality) {
        this.confidentiality = confidentiality;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public com.example.hapifhir.bundle.Identifier getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(com.example.hapifhir.bundle.Identifier identifier) {
        Identifier = identifier;
    }
}
