package it.laziocrea.jemoloapp.domain;

public class ProvinciaList {
	
	private String siglaProvincia;
	private String nomeProvincia;
	
	public ProvinciaList(String siglaProvincia, String nomeProvincia) {
		super();
		this.siglaProvincia = siglaProvincia;
		this.nomeProvincia = nomeProvincia;
	}

	public String getSiglaProvincia() {
		return siglaProvincia;
	}

	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}

	public String getNomeProvincia() {
		return nomeProvincia;
	}

	public void setNomeProvincia(String nomeProvincia) {
		this.nomeProvincia = nomeProvincia;
	}
	
}
