package it.laziocrea.jemoloapp.web.rest.errors;

public class CodiceFiscaleAlreadyUsedException extends BadRequestAlertException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodiceFiscaleAlreadyUsedException() {
		super(ErrorConstants.CODICE_FISCALE_ALREADY_USED_TYPE, "Codice fiscale already used!", "ManagedCandidatoVM", "codicefiscaleExists");
	}

}
