package app.application.adapters.persistence.mongodb.documents;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.List;

@Getter
@Setter
@Document(collection = "clinical_records")
public class ClinicalRecordDocument {

    @Id
    private String id;

    @Indexed(unique = true)
    private String patientDocument;

    /**
     * Key: date in format "yyyy-MM-dd"
     * Value: a list of clinical record entries for that day
     */
    private Map<String, List<RecordEntry>> records;

    @Getter
    @Setter
    public static class RecordEntry {
        private String doctorDocument;
        private String doctorName;
        private String reason;
        private String symptoms;
        private String diagnosis;
        private Long orderId;
    }
}
