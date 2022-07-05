package util;

import consts.Env;
import lombok.Getter;
import org.testng.Assert;

public class ExecutionVariables {

    private ExecutionVariables() {
        throw new IllegalStateException("Utility class");
    }

    @Getter
    private static Env env;

    public static void setEnv() {
        env = Env.getByEnvName(getMavenEnvName());
    }

    /**
     property described in pom.xml  <test.environment>${env.NAME}</test.environment>
     */
    public static String getMavenEnvName() {
        String env;
        if (isTestFromLocalMachine()) {
            env = PropertyLoader.loadProperty("/local.properties", "local-env");
        } else {
            env = System.getProperty("test.environment");
        }
        Assert.assertNotNull(env, "ERROR: 'env' variable is null in ExecutionVariables.getMavenEnvName(). Make sure that env.NAME is provided.");
        return env;
    }

    private static boolean isTestFromLocalMachine() {
        return Boolean.parseBoolean(PropertyLoader.loadProperty("/local.properties", "is-test-from-local"));
    }

    public static boolean isRemoteExecution() {
        String localRemote = PropertyLoader.loadProperty("/local.properties", "local-remote-execution");
        if (localRemote == null) {
            return true;
        }
        return Boolean.parseBoolean(localRemote);
    }

}