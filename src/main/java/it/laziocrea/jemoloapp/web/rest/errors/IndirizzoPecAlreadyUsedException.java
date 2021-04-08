package it.laziocrea.jemoloapp.web.rest.errors;

public class IndirizzoPecAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public IndirizzoPecAlreadyUsedException() {
        super(ErrorConstants.PEC_ALREADY_USED_TYPE, "PEC address is already in use!", "userManagement", "pecexists");
    }
}
