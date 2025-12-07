package com.epm.gestepm.controller;

import com.epm.gestepm.emailapi.service.EmailService;
import com.epm.gestepm.model.process.MigrationStorageDelegator;
import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlDto;
import com.epm.gestepm.modelapi.timecontrol.dto.filter.TimeControlFilterDto;
import com.epm.gestepm.modelapi.timecontrol.service.TimeControlService;
import com.epm.gestepm.task.PersonalSigningTask;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Controller
@RequiredArgsConstructor
public class MainController {

	private final MigrationStorageDelegator migrationStorageDelegator;

    @GetMapping("/")
	public String loginMessage() {
		return "redirect:/signing/personal"; 
	}

	@GetMapping("/v1/test")
	public void toTest() {
		this.migrationStorageDelegator.runMigration();
	}
}
