package com.epm.gestepm.rest.front;

import com.epm.gestepm.lib.user.UserProvider;
import com.epm.gestepm.lib.user.data.UserData;
import com.epm.gestepm.lib.user.data.UserLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class FrontendErrorLogController {

    @Value("${gestepm.front-error-logs}")
    private String logPath;

    private final UserProvider userProvider;

    @PostMapping("/frontend-error-log")
    public void logError(@RequestBody Map<String, Object> error) {
        try {
            final Optional<UserLogin> userLogin = this.userProvider.get(UserLogin.class);
            final String userId = String.valueOf(userLogin.map(UserData::getValue).orElse(null));

            final Path logFile = Paths.get(logPath);

            final String line = "---- " + LocalDateTime.now() + " ----\nUserId: " + userId + " => " + error.toString() + "\n\n";

            Files.writeString(logFile, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
