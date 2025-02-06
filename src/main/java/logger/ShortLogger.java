package logger;

import java.lang.reflect.Type;

public class ShortLogger implements Logger {
    private int integerElements = 0;
    private int floatElements = 0;
    private int stringElements = 0;

    @Override
    public void printStatistic() {
        System.out.println("Integer elements written: " + integerElements);
        System.out.println("Float elements written: " + floatElements);
        System.out.println("String elements written: " + stringElements);
    }

    @Override
    public void logString(String type, String element) {
        switch (type) {
            case "integers" -> integerElements++;
            case "floats" -> floatElements++;
            case "strings" -> stringElements++;
        }
    }
}
