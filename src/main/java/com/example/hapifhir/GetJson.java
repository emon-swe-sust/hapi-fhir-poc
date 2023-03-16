package com.example.hapifhir;

import ca.uhn.fhir.context.FhirContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.hl7.fhir.dstu3.model.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.dsig.XMLObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class GetJson {

    @PostMapping("/json")
    public String createBundle(){
//  Create Bundle
        Bundle bundle = new Bundle();
        bundle.setId("bcf6c340-d04a-4c29-b50c-2552beeee037");
        bundle.setType(Bundle.BundleType.TRANSACTION);
//  Create meta of the bundle
        Meta meta = new Meta();
        meta.setLastUpdated(new Date());
        bundle.setMeta(meta);

        List<Bundle.BundleEntryComponent> entries = new ArrayList<>();
//      Create Entry of the bundle
        Bundle.BundleEntryComponent entry = new Bundle.BundleEntryComponent();
        entry.setFullUrl("urn:uuid:d1624f29-6962-4d15-9931-09dd5c85b9cf");

//      Create composition Resource of the entry
        Composition resource1 = new Composition();
        Identifier identifier = new Identifier();
        identifier.setValue("urn:uuid:d1624f29-6962-4d15-9931-09dd5c85b9cf");

//      Set identifiers
        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(identifier);
        resource1.setIdentifier(identifier);


        resource1.setDate(new Date());
        resource1.setTitle("PatientClinicalEncounter");
        resource1.setStatus(Composition.CompositionStatus.FINAL);
        resource1.setConfidentiality(Composition.DocumentConfidentiality.N);
        resource1.setEncounter(new Reference("urn:uuid:14278c1c-95b4-43f0-aa35-66e366fb45dc"));
        resource1.setSubject(new Reference("http://localhost:8081/api/default/patients/98000106255"));


//      Set section on resource
        List<Composition.SectionComponent> sections = new ArrayList<>();
        Composition.SectionComponent sectionComponent = new Composition.SectionComponent();
        List<Reference> sectionEntries = new ArrayList<>();
        sectionEntries.add(new Reference("urn:uuid:14278c1c-95b4-43f0-aa35-66e366fb45dc"));
        sectionComponent.setEntry(sectionEntries);

        Composition.SectionComponent section1 = new Composition.SectionComponent();
        section1.setEntry(sectionEntries);
        sections.add(section1);
        resource1.setSection(sections);


//      Set Author on resource
        List<Reference> resourceAutors = new ArrayList<>();
        resourceAutors.add(new Reference("http://localhost:8080/facilities/10019842.json"));
        resource1.setAuthor(resourceAutors);
        System.out.println(resource1);

//      Set Type on resource
        CodeableConcept codeableConcept = new CodeableConcept();
        List<Coding> typeCoding = new ArrayList<>();
        Coding coding1 = new Coding();
        coding1.setCode("51899-3");
        coding1.setDisplay("DetailsDocument");
        coding1.setSystem("http://hl7.org/fhir/vs/doc-typecodes");
        typeCoding.add(coding1);
        codeableConcept.setCoding(typeCoding);
        resource1.setType(codeableConcept);


        entry.setResource(resource1);

        entries.add(entry);
        bundle.setEntry(entries);

        FhirContext ctx = FhirContext.forDstu3();
        String json = ctx.newJsonParser().encodeResourceToString(bundle).toString();
        String encodedXml = ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(bundle);
        Content content = new Content(json);
//        String outputString = new Content(json);


        ObjectMapper objectMapper  = new ObjectMapper();
        try {
            String json1 = objectMapper.writeValueAsString(content);
            return json1;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return "Error";
    }
}
