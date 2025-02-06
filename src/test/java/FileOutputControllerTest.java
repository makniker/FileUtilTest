import logger.ShortLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FileOutputControllerTest {

    private final Path tempDir = Paths.get("test");
    private final Map<String, List<String>> files = Map.of("strings", List.of("Line 1", "Line 2"), "integers", List.of("1", "2"));

    private void createFiles(Map<String, List<String>> files) throws IOException {
        for (var entry : files.entrySet()) {
            Path filePath = tempDir.resolve(entry.getKey() + ".txt");
            Path parentDir = filePath.getParent();
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }
            boolean fileExists = Files.exists(filePath);
            StandardOpenOption[] options = fileExists ? new StandardOpenOption[]{StandardOpenOption.APPEND} : new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};
            Files.write(filePath, entry.getValue(), options);
        }
    }

    @BeforeEach
    void cleanDir() {
        if (Files.exists(tempDir)) {
            try (Stream<Path> tmp = Files.walk(tempDir)) {
                tmp.sorted(Comparator.reverseOrder()).forEach(path -> path.toFile().delete());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    void testWriteToNewFile() {
        assertDoesNotThrow(() -> {
            FileOutputController controller = new FileOutputController(new ShortLogger(), "test/%s.txt", false);
            controller.passIntoFile(files);

            for (var entry : files.entrySet()) {
                Path filePath = tempDir.resolve(entry.getKey() + ".txt");
                assertTrue(Files.exists(filePath));
                assertEquals(Files.readAllLines(filePath), entry.getValue());
            }
        });
    }

    @Test
    void testWriteToNewFileOverride() {
        assertDoesNotThrow(() -> {
            FileOutputController controller = new FileOutputController(new ShortLogger(), "test/%s.txt", true);
            controller.passIntoFile(files);

            for (var entry : files.entrySet()) {
                Path filePath = tempDir.resolve(entry.getKey() + ".txt");
                assertTrue(Files.exists(filePath));
                assertEquals(Files.readAllLines(filePath), entry.getValue());
            }
        });
    }

    @Test
    void testWriteToExistedFile() {
        assertDoesNotThrow(() -> {
            Map<String, List<String>> testMap = Map.of("strings", List.of("Line 1", "Line 2", "Line 1", "Line 2"), "integers", List.of("1", "2", "1", "2"));
            createFiles(files);

            FileOutputController controller = new FileOutputController(new ShortLogger(), "test/%s.txt", false);
            controller.passIntoFile(files);

            for (var entry : testMap.entrySet()) {
                Path filePath = tempDir.resolve(entry.getKey() + ".txt");
                assertTrue(Files.exists(filePath));
                assertEquals(entry.getValue(), Files.readAllLines(filePath));
            }
        });
    }

    @Test
    void testWriteToExistedFileOverride() {
        assertDoesNotThrow(() -> {
            createFiles(files);

            FileOutputController controller = new FileOutputController(new ShortLogger(), "test/%s.txt", true);
            controller.passIntoFile(files);

            for (var entry : files.entrySet()) {
                Path filePath = tempDir.resolve(entry.getKey() + ".txt");
                assertTrue(Files.exists(filePath));
                assertEquals(Files.readAllLines(filePath), entry.getValue());
            }
        });
    }
}