package com.example.hapifhir.bundle;

import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Composition;

public class Utils {
    public static Bundle.BundleType getBundleType(String type){
        if(type.equals("transaction")){
            return Bundle.BundleType.TRANSACTION;
        }
        return Bundle.BundleType.COLLECTION;
    }

    public static Composition.CompositionStatus getStatus(String status){
        if(status.equals("final")){
            return Composition.CompositionStatus.FINAL;
        }
        return Composition.CompositionStatus.PRELIMINARY;
    }

    public static Composition.DocumentConfidentiality getConfidentiality(String confidentiality){
        if(confidentiality.equals("N")){
            return Composition.DocumentConfidentiality.N;
        }
        return Composition.DocumentConfidentiality.L;
    }

}
