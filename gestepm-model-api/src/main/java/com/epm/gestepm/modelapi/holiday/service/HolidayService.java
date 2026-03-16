package com.epm.gestepm.modelapi.holiday.service;

import java.util.List;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.holiday.dto.Holiday;
import com.epm.gestepm.modelapi.holiday.dto.HolidayTableDTO;

public interface HolidayService {

	List<Holiday> findAll();

	void save(Holiday holiday);

	void delete(Long id);

	List<HolidayTableDTO> getHolidaysDataTables(PaginationCriteria pagination);

	Long getHolidaysCount();

}
