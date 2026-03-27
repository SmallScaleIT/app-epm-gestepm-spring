package com.epm.gestepm.modelapi.common.utils.smtp;

import com.epm.gestepm.modelapi.common.utils.smtp.dto.OpenPersonalExpenseSheetMailTemplateDto;
import com.epm.gestepm.modelapi.deprecated.project.dto.Project;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrective;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigning;

import java.util.Locale;

public interface SMTPService {

	void sendPersonalExpenseSheetSendMail(final OpenPersonalExpenseSheetMailTemplateDto dto);

	void sendSigningManualMail(String to, UserManualSigning userManualSigning, Locale locale);

	void sendCorrectiveTeamLeaderMail(String to, User user, ExpenseCorrective corrective, Project project, Locale locale);

}
