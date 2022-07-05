package consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Env {

    PROD("PROD", "http://automationpractice.com/index.php");

    private final String env;
    private final String link;

    public static Env getByEnvName(String envName) {
        for (Env env : Env.values()) {
            if (env.getEnv().equals(envName)) {
                return env;
            }
        }
        throw new IllegalArgumentException("Unknown env type: " + envName);
    }

}