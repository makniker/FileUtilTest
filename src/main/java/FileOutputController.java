import logger.Logger;
import parser.Args;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

public class FileOutputController {
    private final boolean isOverride;
    private final Logger logger;
    private final String outputTemplate;

    public FileOutputController(Logger logger, Args cliArgs) {
        this.logger = logger;
        this.isOverride = cliArgs.isOverride();
        this.outputTemplate = cliArgs.provideStringTemplate();
    }

    public FileOutputController(Logger logger, String outputTemplate, boolean isOverride) {
        this.logger = logger;
        this.isOverride = isOverride;
        this.outputTemplate = outputTemplate;
    }

    public void passIntoFile(Map<String, List<String>> files) throws IOException {
        for (var entry : files.entrySet()) {
            String name = entry.getKey();
            String key = String.format(outputTemplate, name);
            List<String> lines = entry.getValue();

            if (!lines.isEmpty()) {
                Path filePath = Paths.get(key);
                Path parentDir = filePath.getParent();
                if (parentDir != null) {
                    Files.createDirectories(parentDir);
                }
                boolean fileExists = Files.exists(filePath);
                boolean append = !isOverride && fileExists;
                StandardOpenOption[] options = append ? new StandardOpenOption[]{StandardOpenOption.APPEND} : new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};
                try (BufferedWriter writer = Files.newBufferedWriter(filePath, options)) {
                    for (String line : lines) {
                        writer.write(line);
                        writer.newLine();
                        writer.flush();
                        logger.logString(name, line);
                    }
                }
            }
        }
    }
}
