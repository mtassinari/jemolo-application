/**
 * 
 */
package it.laziocrea.jemoloapp.web.rest.vm;

import javax.validation.constraints.Pattern;

import it.laziocrea.jemoloapp.config.Constants;
import it.laziocrea.jemoloapp.service.dto.CandidatoDTO;

/**
 * @author mtassinari
 * View Model extending the CandidatoDTO, which is meant to be used in the candidato management UI.
 */
public class ManagedCandidatoVM extends CandidatoDTO {
	
	@Pattern(regexp = Constants.PASSWORD_REGEX)
	private String password;

	public ManagedCandidatoVM() {
		// Empty constructor needed for Jackson.
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
    @Override
    public String toString() {
        return "ManagedCandidatoVM{" + super.toString() + "} ";
    }
}
