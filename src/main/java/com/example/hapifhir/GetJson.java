package com.example.hapifhir;

import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.dstu3.model.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(identifier);
        resource1.setIdentifier(identifier);
        resource1.setDate(new Date());
        resource1.setTitle("PatientClinicalEncounter");
        resource1.setStatus(Composition.CompositionStatus.FINAL);
        resource1.setConfidentiality(Composition.DocumentConfidentiality.N);
//        resource1.setSubject()
//        resource1.setType(new CodeableConcept().setCoding());
        resource1.setEncounter(new Reference("urn:uuid:14278c1c-95b4-43f0-aa35-66e366fb45dc"));


        entry.setResource(resource1);

        entries.add(entry);
        bundle.setEntry(entries);
//        resource1.setType(Bundle.BundleType.COMPOSITION);


        FhirContext ctx = FhirContext.forDstu3();
        String json = ctx.newJsonParser().encodeResourceToString(bundle);

        return json;
    }
}

//{
//        "content": "
//        {\"resourceType\":\"Bundle\",
//        \"id\":\"example-bundle\",
//        \"meta\"
//        :{\"lastUpdated\":\"2014-08-18T01:43:30Z\"},
//        \"type\":\"transaction\",
//        \"entry\"
//        :[
//        {\"fullUrl\":\"urn:uuid:d1624f29-6962-4d15-9931-09dd5c85b9cf\"
//        ,\"resource\":{
//        \"resourceType\":\"Composition\",\
//        "identifier\":
//        {\"value\":\"urn:uuid:d1624f29-6962-4d15-9931-09dd5c85b9cf\"},
//        \"date\":\"2017-03-14T11:39:10.000+05:30\",\"type\":{\"coding\":{\"system\":\"http://hl7.org/fhir/vs/doc-typecodes\",\"code\":\"51899-3\",\"display\":\"DetailsDocument\"}},\"title\":\"PatientClinicalEncounter\",\"status\":\"final\",\"confidentiality\":\"N\",\"subject\":{\"reference\":\"http://localhost:8081/api/default/patients/98000106255\",\"display\":\"98076749129\"},\"author\":{\"reference\":\"http://localhost:8080/facilities/10019842.json\"},\"encounter\":{\"reference\":\"urn:uuid:14278c1c-95b4-43f0-aa35-66e366fb45dc\"},\"section\":{\"entry\":{\"reference\":\"urn:uuid:14278c1c-95b4-43f0-aa35-66e366fb45dc\",\"display\":\"Encounter\"}}}}]}"
//        }