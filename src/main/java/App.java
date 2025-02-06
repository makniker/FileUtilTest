import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import logger.Logger;
import parser.Args;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

    private static void provideArgs(String[] args, Args cliArgs) throws ParameterException {
        JCommander parser = JCommander.newBuilder().addObject(cliArgs).build();
        parser.parse(args);
    }

    private static Map<String, List<String>> provideFilteredData(Stream<Path> filePaths) {
        return filePaths.flatMap(file -> {
            try {
                return Files.lines(file, StandardCharsets.UTF_8);
            } catch (Exception e) {
                System.out.println("File " + file + " not presented there");
                return Stream.empty();
            }
        }).collect(Collectors.groupingBy(it -> {
            try {
                Integer.parseInt(it);
                return "integers";
            } catch (Exception e) {
                try {
                    Float.parseFloat(it);
                    return "floats";
                } catch (Exception e1) {
                    return "strings";
                }
            }
        }));
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        Args cliArgs = new Args();
        try {
            provideArgs(args, cliArgs);
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
            return;
        }
        var filePaths = cliArgs.provideFiles();
        Logger logger = cliArgs.provideLogger();
        FileOutputController out = new FileOutputController(logger, cliArgs);
        var files = provideFilteredData(filePaths);
        try {
            out.passIntoFile(files);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        logger.printStatistic();
    }
}
