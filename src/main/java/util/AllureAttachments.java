package util;

import io.qameta.allure.Allure;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AllureAttachments {

    @SneakyThrows
    public static void attachScreen(String description, String filePath) {
        Path content = Paths.get(filePath);
        try (InputStream is = Files.newInputStream(content)) {
            Allure.addAttachment(description, is);
        }
    }

}