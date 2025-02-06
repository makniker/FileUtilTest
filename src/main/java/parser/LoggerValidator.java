package parser;

import com.beust.jcommander.IParametersValidator;
import com.beust.jcommander.ParameterException;

import java.util.Map;

public class LoggerValidator implements IParametersValidator {

    @Override
    public void validate(Map<String, Object> parameters) throws ParameterException {
        if (parameters.get("--short") != null && parameters.get("--full") != null)
            throw new ParameterException("-s and -f are mutually exclusive");
        if (parameters.get("--short") == null && parameters.get("--full") == null)
            throw new ParameterException("-s or -f not presented");
    }
}
