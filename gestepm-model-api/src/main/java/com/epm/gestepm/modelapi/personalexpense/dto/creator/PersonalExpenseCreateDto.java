package com.epm.gestepm.modelapi.personalexpense.dto.creator;

import com.epm.gestepm.modelapi.personalexpense.dto.PaymentTypeEnumDto;
import com.epm.gestepm.modelapi.personalexpense.dto.PriceTypeEnumDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PersonalExpenseCreateDto {

    @NotNull
    private Integer personalExpenseSheetId;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private String description;

    @NotNull
    private PriceTypeEnumDto priceType;

    private Double quantity;

    @NotNull
    private Double amount;

    @NotNull
    private PaymentTypeEnumDto paymentType;

    @JsonIgnore
    private List<MultipartFile> files;

}
