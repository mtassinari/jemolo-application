package it.laziocrea.jemoloapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * Entity Allegato\n@author Marco Tassinari
 */
@Entity
@Table(name = "allegato")
public class Allegato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "allegatoSequence")
    @SequenceGenerator(name = "allegatoSequence", sequenceName = "allegato_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Lob
    @Column(name = "data")
    @NotNull
    private byte[] data;

    @Column(name = "data_content_type")
    private String dataContentType;

    @OneToOne(mappedBy = "allegato")
    @JsonIgnore
    private Curriculum curriculum;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public Allegato data(byte[] data) {
        this.data = data;
        return this;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return dataContentType;
    }

    public Allegato dataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
        return this;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public Allegato curriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
        return this;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Allegato)) {
            return false;
        }
        return id != null && id.equals(((Allegato) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Allegato{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", dataContentType='" + getDataContentType() + "'" +
            "}";
    }
}
