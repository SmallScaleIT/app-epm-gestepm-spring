package com.epm.gestepm.model.deprecated.holiday.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.holiday.dto.Holiday;

public interface HolidayRepository extends CrudRepository<Holiday, Long>, HolidayRepositoryCustom {

}
