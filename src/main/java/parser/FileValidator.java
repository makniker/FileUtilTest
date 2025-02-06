package parser;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.util.regex.Pattern;

public class FileValidator implements IParameterValidator {
    private static final Pattern FILE_PATTERN = Pattern.compile("^/?([^/]+/)*[^/]*\\.txt$");
    @Override
    public void validate(String name, String value) throws ParameterException {
        if (!FILE_PATTERN.matcher(value).matches()) {
            throw new ParameterException(value + " should be a .txt file");
        }
    }
}
