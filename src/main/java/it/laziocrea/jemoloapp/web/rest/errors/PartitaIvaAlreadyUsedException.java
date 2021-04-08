package it.laziocrea.jemoloapp.web.rest.errors;

public class PartitaIvaAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public PartitaIvaAlreadyUsedException() {
        super(ErrorConstants.PIVA_ALREADY_USED_TYPE, "Partita iva is already in use!", "anagraficaManagement", "pivaexists");
    }
}
