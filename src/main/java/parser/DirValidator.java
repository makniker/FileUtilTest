package parser;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.io.File;
import java.util.regex.Pattern;

public class DirValidator implements IParameterValidator {
    private static final Pattern PATH_PATTERN = Pattern.compile("^(/[^/]+){1,}$");
    @Override
    public void validate(String name, String value) throws ParameterException {
        if (!PATH_PATTERN.matcher(value).matches()) {
            throw new ParameterException("Parameter " + name + " should be directory");
        }
    }
}
