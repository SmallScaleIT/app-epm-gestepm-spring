package com.epm.gestepm.modelapi.holiday.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayDTO {

	private String name;

	private int day;

	private int month;

	private Long country;

	private Long activityCenter;

}
