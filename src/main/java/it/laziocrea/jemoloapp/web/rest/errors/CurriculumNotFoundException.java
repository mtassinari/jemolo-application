package it.laziocrea.jemoloapp.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class CurriculumNotFoundException extends AbstractThrowableProblem {
	
	private static final long serialVersionUID = 1L;
	public CurriculumNotFoundException() {
		super(null, "Curriculum not found", Status.NOT_FOUND);
	}

}
