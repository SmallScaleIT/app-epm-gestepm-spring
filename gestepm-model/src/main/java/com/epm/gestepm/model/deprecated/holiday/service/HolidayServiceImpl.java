package com.epm.gestepm.model.deprecated.holiday.service;

import com.epm.gestepm.model.deprecated.holiday.dao.HolidayRepository;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.holiday.dto.Holiday;
import com.epm.gestepm.modelapi.holiday.dto.HolidayTableDTO;
import com.epm.gestepm.modelapi.holiday.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class HolidayServiceImpl implements HolidayService {

	@Autowired
	private HolidayRepository holidayRepository;
	
	@Override
	public List<Holiday> findAll() {
		return (List<Holiday>) holidayRepository.findAll();
	}
	
	@Override
	public void save(Holiday holiday) {
		holidayRepository.save(holiday);	
	}
	
	@Override
	public void delete(Long id) {
		holidayRepository.deleteById(id);
	}
	
	@Override
	public List<HolidayTableDTO> getHolidaysDataTables(PaginationCriteria pagination) {
		return holidayRepository.findHolidaysDataTables(pagination);
	}
	
	@Override
	public Long getHolidaysCount() {
		return holidayRepository.findHolidaysCount();
	}
}
