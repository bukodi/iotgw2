package hu.noreg.iotgw2.service.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link hu.noreg.iotgw2.domain.OrgUnit} entity.
 */
public class OrgUnitDTO implements Serializable {

    private Long id;

    /**
     * Unique name of the device type
     */
    @NotNull
    @ApiModelProperty(value = "Unique name of the device type", required = true)
    private String name;

    /**
     * Optional description
     */
    @ApiModelProperty(value = "Optional description")
    private String description;

    private String readAuthPattern;

    private String writeAuthPattern;

    private OrgUnitDTO parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReadAuthPattern() {
        return readAuthPattern;
    }

    public void setReadAuthPattern(String readAuthPattern) {
        this.readAuthPattern = readAuthPattern;
    }

    public String getWriteAuthPattern() {
        return writeAuthPattern;
    }

    public void setWriteAuthPattern(String writeAuthPattern) {
        this.writeAuthPattern = writeAuthPattern;
    }

    public OrgUnitDTO getParent() {
        return parent;
    }

    public void setParent(OrgUnitDTO parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrgUnitDTO)) {
            return false;
        }

        OrgUnitDTO orgUnitDTO = (OrgUnitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orgUnitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrgUnitDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", readAuthPattern='" + getReadAuthPattern() + "'" +
            ", writeAuthPattern='" + getWriteAuthPattern() + "'" +
            ", parent=" + getParent() +
            "}";
    }
}
