package hu.noreg.iotgw2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrgUnit.
 */
@Entity
@Table(name = "iot_org_unit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrgUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique name of the device type
     */
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Optional description
     */
    @Column(name = "description")
    private String description;

    @Column(name = "read_auth_pattern")
    private String readAuthPattern;

    @Column(name = "write_auth_pattern")
    private String writeAuthPattern;

    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "subUnits", "parent" }, allowSetters = true)
    private Set<OrgUnit> subUnits = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "subUnits", "parent" }, allowSetters = true)
    private OrgUnit parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrgUnit id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public OrgUnit name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public OrgUnit description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReadAuthPattern() {
        return this.readAuthPattern;
    }

    public OrgUnit readAuthPattern(String readAuthPattern) {
        this.readAuthPattern = readAuthPattern;
        return this;
    }

    public void setReadAuthPattern(String readAuthPattern) {
        this.readAuthPattern = readAuthPattern;
    }

    public String getWriteAuthPattern() {
        return this.writeAuthPattern;
    }

    public OrgUnit writeAuthPattern(String writeAuthPattern) {
        this.writeAuthPattern = writeAuthPattern;
        return this;
    }

    public void setWriteAuthPattern(String writeAuthPattern) {
        this.writeAuthPattern = writeAuthPattern;
    }

    public Set<OrgUnit> getSubUnits() {
        return this.subUnits;
    }

    public OrgUnit subUnits(Set<OrgUnit> orgUnits) {
        this.setSubUnits(orgUnits);
        return this;
    }

    public OrgUnit addSubUnits(OrgUnit orgUnit) {
        this.subUnits.add(orgUnit);
        orgUnit.setParent(this);
        return this;
    }

    public OrgUnit removeSubUnits(OrgUnit orgUnit) {
        this.subUnits.remove(orgUnit);
        orgUnit.setParent(null);
        return this;
    }

    public void setSubUnits(Set<OrgUnit> orgUnits) {
        if (this.subUnits != null) {
            this.subUnits.forEach(i -> i.setParent(null));
        }
        if (orgUnits != null) {
            orgUnits.forEach(i -> i.setParent(this));
        }
        this.subUnits = orgUnits;
    }

    public OrgUnit getParent() {
        return this.parent;
    }

    public OrgUnit parent(OrgUnit orgUnit) {
        this.setParent(orgUnit);
        return this;
    }

    public void setParent(OrgUnit orgUnit) {
        this.parent = orgUnit;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrgUnit)) {
            return false;
        }
        return id != null && id.equals(((OrgUnit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrgUnit{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", readAuthPattern='" + getReadAuthPattern() + "'" +
            ", writeAuthPattern='" + getWriteAuthPattern() + "'" +
            "}";
    }
}
