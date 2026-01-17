package com.epm.gestepm.emailapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Data
public abstract class EmailGroup {

    @NotNull
    private List<String> emails;

    @NotNull
    private String subject;

    @NotNull
    private Locale locale;

    public abstract String getEmailTemplate();

    public abstract Map<String, Object> buildVariables();

    public abstract Boolean containsAttachments();

    @JsonIgnore
    public abstract List<MultipartFile> getAttachments();

}
