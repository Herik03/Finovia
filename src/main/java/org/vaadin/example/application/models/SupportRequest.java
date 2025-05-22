package org.vaadin.example.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupportRequest {
    private String category;
    private String description;
    private String status;
    private String creationDate;
}

