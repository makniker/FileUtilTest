package parser;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import logger.FullLogger;
import logger.ShortLogger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgsTest {
    @Test
    void provideLogger() {
        Args cliArgs = new Args();
        JCommander parser = JCommander.newBuilder().addObject(cliArgs).build();
        String args = "-f file1.txt";
        parser.parse(args.split(" "));
        assertInstanceOf(FullLogger.class, cliArgs.provideLogger());

        cliArgs = new Args();
        parser = JCommander.newBuilder().addObject(cliArgs).build();
        args = "-s file1.txt";
        parser.parse(args.split(" "));
        assertInstanceOf(ShortLogger.class, cliArgs.provideLogger());

        cliArgs = new Args();
        JCommander parserBothNegative = JCommander.newBuilder().addObject(cliArgs).build();
        String argsBoth = "-f -s file1.txt";
        Exception e = assertThrows(ParameterException.class, () -> {
            parserBothNegative.parse(argsBoth.split(" "));});

        assertEquals("-s and -f are mutually exclusive", e.getMessage());

        cliArgs = new Args();
        JCommander parserNone = JCommander.newBuilder().addObject(cliArgs).build();
        String argsNone = "file1.txt";
        e = assertThrows(ParameterException.class, () -> {
            parserNone.parse(argsNone.split(" "));});
        assertEquals("-s or -f not presented", e.getMessage());
    }

    @Test
    void provideStringTemplate() {
        Args cliArgs = new Args();
        JCommander parser = JCommander.newBuilder().addObject(cliArgs).build();
        String args = "-s file1.txt";
        parser.parse(args.split(" "));
        assertEquals("%s.txt", cliArgs.provideStringTemplate());

        cliArgs = new Args();
        parser = JCommander.newBuilder().addObject(cliArgs).build();
        args = "-s -o /mypath file1.txt";
        parser.parse(args.split(" "));
        assertEquals("mypath/%s.txt", cliArgs.provideStringTemplate());

        cliArgs = new Args();
        JCommander parserWrong = JCommander.newBuilder().addObject(cliArgs).build();
        Exception e = assertThrows(ParameterException.class, () -> {
            parserWrong.parse("-s -o mypath file1.txt".split(" "));});
        assertEquals("Parameter -o should be directory", e.getMessage());

        cliArgs = new Args();
        JCommander parserWrong2 = JCommander.newBuilder().addObject(cliArgs).build();
        Exception e2 = assertThrows(ParameterException.class, () -> {
            parserWrong2.parse("-s -o //mypath file1.txt".split(" "));});
        assertEquals("Parameter -o should be directory", e2.getMessage());

        cliArgs = new Args();
        parser = JCommander.newBuilder().addObject(cliArgs).build();
        args = "-s -o /my/long/path file1.txt";
        parser.parse(args.split(" "));
        assertEquals("my/long/path/%s.txt", cliArgs.provideStringTemplate());

        cliArgs = new Args();
        parser = JCommander.newBuilder().addObject(cliArgs).build();
        args = "-s -p prefix_ file1.txt";
        parser.parse(args.split(" "));
        assertEquals("prefix_%s.txt", cliArgs.provideStringTemplate());
    }

    @Test
    void isOverride() {
        Args cliArgs = new Args();
        JCommander parser = JCommander.newBuilder().addObject(cliArgs).build();
        String args = "-s file1.txt";
        parser.parse(args.split(" "));
        assertFalse(cliArgs.isOverride());

        cliArgs = new Args();
        parser = JCommander.newBuilder().addObject(cliArgs).build();
        args = "-s -a file1.txt";
        parser.parse(args.split(" "));
        assertTrue(cliArgs.isOverride());
    }
}