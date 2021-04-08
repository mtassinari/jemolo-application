package it.laziocrea.jemoloapp.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class CodiceFiscaleNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public CodiceFiscaleNotFoundException() {
        super(ErrorConstants.CF_NOT_FOUND_TYPE, "Codice fiscale not registered", Status.BAD_REQUEST);
    }
}
