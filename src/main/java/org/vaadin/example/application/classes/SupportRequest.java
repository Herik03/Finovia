package org.vaadin.example.application.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupportRequest {
    private String category;
    private String description;
    private String status;
    private String creationDate;
    private String ticketId;
    private List<String> comments = new ArrayList<>();
    private List<String> attachments = new ArrayList<>();
    
    // Konstruktor ohne die optionalen Felder
    public SupportRequest(String category, String description, String status, String creationDate) {
        this.category = category;
        this.description = description;
        this.status = status;
        this.creationDate = creationDate;
    }
    
    // Methode zum Hinzufügen von Kommentaren
    public void addComment(String comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
    }

    // Methode zum Hinzufügen von Anhängen
    public void addAttachment(String attachmentPath) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        attachments.add(attachmentPath);
}
}


