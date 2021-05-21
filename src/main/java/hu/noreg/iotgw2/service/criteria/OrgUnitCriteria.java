package hu.noreg.iotgw2.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link hu.noreg.iotgw2.domain.OrgUnit} entity. This class is used
 * in {@link hu.noreg.iotgw2.web.rest.OrgUnitResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /org-units?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrgUnitCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter readAuthPattern;

    private StringFilter writeAuthPattern;

    private LongFilter subUnitsId;

    private LongFilter parentId;

    public OrgUnitCriteria() {}

    public OrgUnitCriteria(OrgUnitCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.readAuthPattern = other.readAuthPattern == null ? null : other.readAuthPattern.copy();
        this.writeAuthPattern = other.writeAuthPattern == null ? null : other.writeAuthPattern.copy();
        this.subUnitsId = other.subUnitsId == null ? null : other.subUnitsId.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
    }

    @Override
    public OrgUnitCriteria copy() {
        return new OrgUnitCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getReadAuthPattern() {
        return readAuthPattern;
    }

    public StringFilter readAuthPattern() {
        if (readAuthPattern == null) {
            readAuthPattern = new StringFilter();
        }
        return readAuthPattern;
    }

    public void setReadAuthPattern(StringFilter readAuthPattern) {
        this.readAuthPattern = readAuthPattern;
    }

    public StringFilter getWriteAuthPattern() {
        return writeAuthPattern;
    }

    public StringFilter writeAuthPattern() {
        if (writeAuthPattern == null) {
            writeAuthPattern = new StringFilter();
        }
        return writeAuthPattern;
    }

    public void setWriteAuthPattern(StringFilter writeAuthPattern) {
        this.writeAuthPattern = writeAuthPattern;
    }

    public LongFilter getSubUnitsId() {
        return subUnitsId;
    }

    public LongFilter subUnitsId() {
        if (subUnitsId == null) {
            subUnitsId = new LongFilter();
        }
        return subUnitsId;
    }

    public void setSubUnitsId(LongFilter subUnitsId) {
        this.subUnitsId = subUnitsId;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public LongFilter parentId() {
        if (parentId == null) {
            parentId = new LongFilter();
        }
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrgUnitCriteria that = (OrgUnitCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(readAuthPattern, that.readAuthPattern) &&
            Objects.equals(writeAuthPattern, that.writeAuthPattern) &&
            Objects.equals(subUnitsId, that.subUnitsId) &&
            Objects.equals(parentId, that.parentId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, readAuthPattern, writeAuthPattern, subUnitsId, parentId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrgUnitCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (readAuthPattern != null ? "readAuthPattern=" + readAuthPattern + ", " : "") +
            (writeAuthPattern != null ? "writeAuthPattern=" + writeAuthPattern + ", " : "") +
            (subUnitsId != null ? "subUnitsId=" + subUnitsId + ", " : "") +
            (parentId != null ? "parentId=" + parentId + ", " : "") +
            "}";
    }
}
