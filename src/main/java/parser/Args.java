package parser;


import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import logger.FullLogger;
import logger.Logger;
import logger.ShortLogger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Parameters(parametersValidators = LoggerValidator.class)
public class Args {

    @Parameter(names = {"-a", "--add"})
    private boolean isOverride = false;
    @Parameter(names = {"-o", "--output"}, description = "Path to output directory", validateWith = DirValidator.class)
    private String output = "";
    @Parameter(names = {"-p", "--prefix"}, description = "Prefix for output files")
    private String prefix = "";
    @Parameter(names = {"-s", "--short"}, description = "Short statistics output")
    boolean sPresent = false;
    @Parameter(names = {"-f", "--full"}, description = "Full statistics output")
    boolean fPresent = false;
    @Parameter(description = "Files", validateWith = FileValidator.class)
    private List<String> files;

    public Logger provideLogger() throws ParameterException {
        if (fPresent) return new FullLogger();
        else return new ShortLogger();
    }

    public Stream<Path> provideFiles() {
        return files.stream().map(Paths::get);
    }


    public String provideStringTemplate() {
        StringBuilder sb = new StringBuilder(output);
        if (!output.isEmpty() && output.charAt(0) == '/') sb.deleteCharAt(0).append('/');
        return sb.append(prefix).append("%s").append(".txt").toString();
    }

    public boolean isOverride() {
        return isOverride;
    }
}
