package it.laziocrea.jemoloapp.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "curriculum_file")
public class CurriculumFile {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "curriculumfile_id_seq")
    @SequenceGenerator(name = "curriculumfile_id_seq",initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "size", nullable = false)
    private Long size;
    
    @Column(name = "mime_type")
    private String mimeType;
    
    @CreatedDate
    @JsonIgnore
    @Column(name = "data_caricamento", updatable = false)
    private Instant dataCaricamento = Instant.now();
    
    @Column(name = "nome_file")
    private String nomeFile;
    
    @Column(name = "url")
    private String url;
    
    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "curriculum_id", nullable = false)
    @JsonIgnoreProperties({"curriculumfiles"})
    public Curriculum curriculum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public Instant getDataCaricamento() {
		return dataCaricamento;
	}

	public void setDataCaricamento(Instant dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Curriculum getCurriculum() {
		return curriculum;
	}
	
	public CurriculumFile curriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
		return this;
	}
	
	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}
}
