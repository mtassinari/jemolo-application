package it.laziocrea.jemoloapp.domain;

public class SearchParam {
	private String nome;
	private String cognome;
	private Long tipotitolodistudioId;
	private Long titolostudioDal;
	private Long titolostudioAl;
	private Long areaCompetenzaId;
	private Long areaCompetenzaDa;
	private Long areaCompetenzaA;
	
	/**
	 * @return the cognome
	 */
	public String getCognome() {
		return cognome;
	}
	/**
	 * @param cognome the cognome to set
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the tipotitolodistudioId
	 */
	public Long getTipotitolodistudioId() {
		return tipotitolodistudioId;
	}
	/**
	 * @param tipotitolodistudioId the tipotitolodistudioId to set
	 */
	public void setTipotitolodistudioId(Long tipotitolodistudioId) {
		this.tipotitolodistudioId = tipotitolodistudioId;
	}
	/**
	 * @return the titolostudioDal
	 */
	public Long getTitolostudioDal() {
		return titolostudioDal;
	}
	/**
	 * @param titolostudioDal the titolostudioDal to set
	 */
	public void setTitolostudioDal(Long titolostudioDal) {
		this.titolostudioDal = titolostudioDal;
	}
	/**
	 * @return the titolostudioAl
	 */
	public Long getTitolostudioAl() {
		return titolostudioAl;
	}
	/**
	 * @param titolostudioAl the titolostudioAl to set
	 */
	public void setTitolostudioAl(Long titolostudioAl) {
		this.titolostudioAl = titolostudioAl;
	}
	/**
	 * @return the areaCompetenzaId
	 */
	public Long getAreaCompetenzaId() {
		return areaCompetenzaId;
	}
	/**
	 * @param areaCompetenzaId the areaCompetenzaId to set
	 */
	public void setAreaCompetenzaId(Long areaCompetenzaId) {
		this.areaCompetenzaId = areaCompetenzaId;
	}
	/**
	 * @return the areaCompetenzaDa
	 */
	public Long getAreaCompetenzaDa() {
		return areaCompetenzaDa;
	}
	/**
	 * @param areaCompetenzaDa the areaCompetenzaDa to set
	 */
	public void setAreaCompetenzaDa(Long areaCompetenzaDa) {
		this.areaCompetenzaDa = areaCompetenzaDa;
	}
	/**
	 * @return the areaCompetenzaA
	 */
	public Long getAreaCompetenzaA() {
		return areaCompetenzaA;
	}
	/**
	 * @param areaCompetenzaA the areaCompetenzaA to set
	 */
	public void setAreaCompetenzaA(Long areaCompetenzaA) {
		this.areaCompetenzaA = areaCompetenzaA;
	}
	@Override
	public String toString() {
		return "SearchParam [nome=" + nome + ", cognome=" + cognome + ", tipotitolodistudioId=" + tipotitolodistudioId
				+ ", titolostudioDal=" + titolostudioDal + ", titolostudioAl=" + titolostudioAl + ", areaCompetenzaId="
				+ areaCompetenzaId + ", areaCompetenzaDa=" + areaCompetenzaDa + ", areaCompetenzaA=" + areaCompetenzaA
				+ "]";
	}
	
}
