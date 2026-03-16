package com.epm.gestepm.model.deprecated.holiday.service.mapper;

import com.epm.gestepm.modelapi.deprecated.activitycenter.service.ActivityCenterService;
import com.epm.gestepm.modelapi.deprecated.country.service.CountryServiceOld;
import com.epm.gestepm.modelapi.holiday.dto.Holiday;
import com.epm.gestepm.modelapi.holiday.dto.HolidayDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class HolidayMapper {

	public static Holiday mapDTOToHoliday(HolidayDTO holidayDTO, CountryServiceOld countryServiceOld, ActivityCenterService activityCenterService) {
		
		final Holiday holiday = new Holiday();

		holiday.setName(holidayDTO.getName());
		holiday.setDay(holidayDTO.getDay());
		holiday.setMonth(holidayDTO.getMonth());
		holiday.setCountry(holidayDTO.getCountry() != null ? countryServiceOld.getById(holidayDTO.getCountry()) : null);
		holiday.setActivityCenter(holidayDTO.getActivityCenter() != null ? activityCenterService.getById(holidayDTO.getActivityCenter()) : null);
		
		return holiday;
	}
}
