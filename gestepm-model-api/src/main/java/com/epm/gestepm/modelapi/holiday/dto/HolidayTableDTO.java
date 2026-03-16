package com.epm.gestepm.modelapi.holiday.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayTableDTO {

	@JsonProperty("ho_id")
	private Long id;
	
	@JsonProperty("ho_name")
	private String name;
	
	@JsonProperty("ho_date")
	private String date;
	
	@JsonProperty("ho_activityCenter")
	private String activityCenter;
	
	@JsonProperty("ho_country")
	private String country;

}
