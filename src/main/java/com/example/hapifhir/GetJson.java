package com.example.hapifhir;

import ca.uhn.fhir.context.FhirContext;
import com.example.hapifhir.bundle.Entry;
import com.example.hapifhir.bundle.FhirBundle;
import com.example.hapifhir.bundle.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hl7.fhir.dstu3.model.*;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



@RestController
public class GetJson {

    @PostMapping("/encounter-mediator/{id}")
    public String createBundle(@RequestBody String inputBody, @PathVariable String id,
                               @RequestHeader("X-Auth-Token") String token,
                               @RequestHeader("client_id") String client_id,
                               @RequestHeader("From") String from)
            throws JsonProcessingException, ParseException {
//        System.out.println(id);

        ObjectMapper objectMapper = new ObjectMapper();
        FhirBundle fhirBundle = objectMapper.readValue(inputBody, FhirBundle.class);

        Bundle bundle = new Bundle();
        bundle.setId(fhirBundle.getId());
        bundle.setType(Utils.getBundleType(fhirBundle.getType()));

        Meta meta = new Meta();
        meta.setLastUpdated(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(fhirBundle.getMeta().getLastUpdated()));
        bundle.setMeta(meta);

        List<Bundle.BundleEntryComponent> entries = new ArrayList<>();
        Bundle.BundleEntryComponent entry;


        for(Entry newEntry: fhirBundle.getEntry()){
            entry = new Bundle.BundleEntryComponent();
            entry.setFullUrl(newEntry.getFullUrl());
            String resourceType = newEntry.getResource().getResourceType();
            if(resourceType.equals("Composition")){
                com.example.hapifhir.bundle.composition.Composition newResource = (com.example.hapifhir.bundle.composition.Composition) newEntry.getResource();
                Composition resource = new Composition();
                resource.setIdentifier(new Identifier().setValue(newResource.getIdentifier().getValue()));
                resource.setDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(newResource.getDate()));

                resource.setTitle(newResource.getTitle());
                resource.setStatus(Utils.getStatus(newResource.getStatus()));
                resource.setConfidentiality(Utils.getConfidentiality(newResource.getConfidentiality()));
                resource.setEncounter(new Reference(newResource.getEncounter().getReference()));
                resource.setSubject(new Reference(newResource.getSubject().getReference()));


//              Add Section
                List<Composition.SectionComponent> sections = new ArrayList<>();
                Composition.SectionComponent section = new Composition.SectionComponent();

                List<Reference> sectionEntries = new ArrayList<>();
                sectionEntries.add(new Reference(newResource.getSection().getEntry().getReference()));

                section.setEntry(sectionEntries);
                sections.add(section);

                resource.setSection(sections);


//              Add Author
                List<Reference> authors = new ArrayList<>();
                authors.add(new Reference(newResource.getAuthor().getReference()));
                resource.setAuthor(authors);


//              Set Type
                CodeableConcept codeableConcept = new CodeableConcept();
                List<Coding> typeCoding = new ArrayList<>();

                Coding coding = new Coding();
                coding.setCode(newResource.getType().getCoding().getCode());
                coding.setDisplay(newResource.getType().getCoding().getDisplay());
                coding.setSystem(newResource.getType().getCoding().getSystem());

                typeCoding.add(coding);
                codeableConcept.setCoding(typeCoding);
                resource.setType(codeableConcept);

                entry.setResource(resource);
            }
            entries.add(entry);
        }

        bundle.setEntry(entries);

        FhirContext ctx = FhirContext.forDstu3();
        String json = ctx.newJsonParser().encodeResourceToString(bundle);
//      String encodedXml = ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(bundle);
        JsonContent jsonContent = new JsonContent(json);
//      String outputString = new Content(json);

        try {
            String requestBody = objectMapper.writeValueAsString(jsonContent);
            return APIContact.createEncounterFreeSHR(requestBody, id, token, client_id, from);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return "Error";
    }
}
