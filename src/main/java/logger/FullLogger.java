package logger;

public class FullLogger implements Logger {
    private int integerElements = 0;
    private int floatElements = 0;
    private int stringElements = 0;
    private int minInt = Integer.MAX_VALUE;
    private int maxInt = Integer.MIN_VALUE;
    private int sumInt = 0;
    private float minFloat = Float.MAX_VALUE;
    private float maxFloat = Float.MIN_VALUE;
    private float sumFloat = 0;
    private int longest = Integer.MIN_VALUE;
    private int shortest = Integer.MAX_VALUE;

    @Override
    public void printStatistic() {
        System.out.println("Integer elements written: " + integerElements);
        System.out.println("Min Integer element written: " + minInt);
        System.out.println("Max Integer element written: " + maxInt);
        System.out.println("Sum of Integer elements written: " + sumInt);
        System.out.println("Mean of Integer elements written: " + ((integerElements != 0) ? sumInt / integerElements : 0));
        System.out.println("Float elements written: " + floatElements);
        System.out.println("Min Float element written: " + minFloat);
        System.out.println("Max Float element written: " + maxFloat);
        System.out.println("Sum of Float elements written: " + sumFloat);
        System.out.println("Mean of Float elements written: " + ((integerElements != 0) ? sumFloat / floatElements : 0));
        System.out.println("String elements written: " + stringElements);
        System.out.println("Longest String elements written: " + longest);
        System.out.println("Shortest String elements written: " + shortest);
    }

    @Override
    public void logString(String type, String element) {
        switch (type) {
            case "integers" -> {
                int tmp = Integer.parseInt(element);
                integerElements++;
                if (minInt > tmp) minInt = tmp;
                if (maxInt < tmp) maxInt = tmp;
                sumInt += tmp;
            }
            case "floats" -> {
                float tmp = Float.parseFloat(element);
                floatElements++;
                if (minFloat > tmp) minFloat = tmp;
                if (maxFloat < tmp) maxFloat = tmp;
                sumFloat += tmp;
            }
            case "strings" -> {
                stringElements++;
                int tmp = element.length();
                if (shortest > tmp) shortest = tmp;
                if (longest < tmp) longest = tmp;
            }
        }
    }
}
