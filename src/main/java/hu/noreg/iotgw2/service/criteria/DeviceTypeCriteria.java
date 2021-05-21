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
 * Criteria class for the {@link hu.noreg.iotgw2.domain.DeviceType} entity. This class is used
 * in {@link hu.noreg.iotgw2.web.rest.DeviceTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /device-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DeviceTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter stateFieldName01;

    private StringFilter stateFieldName02;

    private StringFilter stateFieldName03;

    private StringFilter stateFieldName04;

    private StringFilter readAuthPattern;

    private StringFilter writeAuthPattern;

    private LongFilter enrollProcessorId;

    private LongFilter messageTypesId;

    public DeviceTypeCriteria() {}

    public DeviceTypeCriteria(DeviceTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.stateFieldName01 = other.stateFieldName01 == null ? null : other.stateFieldName01.copy();
        this.stateFieldName02 = other.stateFieldName02 == null ? null : other.stateFieldName02.copy();
        this.stateFieldName03 = other.stateFieldName03 == null ? null : other.stateFieldName03.copy();
        this.stateFieldName04 = other.stateFieldName04 == null ? null : other.stateFieldName04.copy();
        this.readAuthPattern = other.readAuthPattern == null ? null : other.readAuthPattern.copy();
        this.writeAuthPattern = other.writeAuthPattern == null ? null : other.writeAuthPattern.copy();
        this.enrollProcessorId = other.enrollProcessorId == null ? null : other.enrollProcessorId.copy();
        this.messageTypesId = other.messageTypesId == null ? null : other.messageTypesId.copy();
    }

    @Override
    public DeviceTypeCriteria copy() {
        return new DeviceTypeCriteria(this);
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

    public StringFilter getStateFieldName01() {
        return stateFieldName01;
    }

    public StringFilter stateFieldName01() {
        if (stateFieldName01 == null) {
            stateFieldName01 = new StringFilter();
        }
        return stateFieldName01;
    }

    public void setStateFieldName01(StringFilter stateFieldName01) {
        this.stateFieldName01 = stateFieldName01;
    }

    public StringFilter getStateFieldName02() {
        return stateFieldName02;
    }

    public StringFilter stateFieldName02() {
        if (stateFieldName02 == null) {
            stateFieldName02 = new StringFilter();
        }
        return stateFieldName02;
    }

    public void setStateFieldName02(StringFilter stateFieldName02) {
        this.stateFieldName02 = stateFieldName02;
    }

    public StringFilter getStateFieldName03() {
        return stateFieldName03;
    }

    public StringFilter stateFieldName03() {
        if (stateFieldName03 == null) {
            stateFieldName03 = new StringFilter();
        }
        return stateFieldName03;
    }

    public void setStateFieldName03(StringFilter stateFieldName03) {
        this.stateFieldName03 = stateFieldName03;
    }

    public StringFilter getStateFieldName04() {
        return stateFieldName04;
    }

    public StringFilter stateFieldName04() {
        if (stateFieldName04 == null) {
            stateFieldName04 = new StringFilter();
        }
        return stateFieldName04;
    }

    public void setStateFieldName04(StringFilter stateFieldName04) {
        this.stateFieldName04 = stateFieldName04;
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

    public LongFilter getEnrollProcessorId() {
        return enrollProcessorId;
    }

    public LongFilter enrollProcessorId() {
        if (enrollProcessorId == null) {
            enrollProcessorId = new LongFilter();
        }
        return enrollProcessorId;
    }

    public void setEnrollProcessorId(LongFilter enrollProcessorId) {
        this.enrollProcessorId = enrollProcessorId;
    }

    public LongFilter getMessageTypesId() {
        return messageTypesId;
    }

    public LongFilter messageTypesId() {
        if (messageTypesId == null) {
            messageTypesId = new LongFilter();
        }
        return messageTypesId;
    }

    public void setMessageTypesId(LongFilter messageTypesId) {
        this.messageTypesId = messageTypesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DeviceTypeCriteria that = (DeviceTypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(stateFieldName01, that.stateFieldName01) &&
            Objects.equals(stateFieldName02, that.stateFieldName02) &&
            Objects.equals(stateFieldName03, that.stateFieldName03) &&
            Objects.equals(stateFieldName04, that.stateFieldName04) &&
            Objects.equals(readAuthPattern, that.readAuthPattern) &&
            Objects.equals(writeAuthPattern, that.writeAuthPattern) &&
            Objects.equals(enrollProcessorId, that.enrollProcessorId) &&
            Objects.equals(messageTypesId, that.messageTypesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            description,
            stateFieldName01,
            stateFieldName02,
            stateFieldName03,
            stateFieldName04,
            readAuthPattern,
            writeAuthPattern,
            enrollProcessorId,
            messageTypesId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceTypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (stateFieldName01 != null ? "stateFieldName01=" + stateFieldName01 + ", " : "") +
            (stateFieldName02 != null ? "stateFieldName02=" + stateFieldName02 + ", " : "") +
            (stateFieldName03 != null ? "stateFieldName03=" + stateFieldName03 + ", " : "") +
            (stateFieldName04 != null ? "stateFieldName04=" + stateFieldName04 + ", " : "") +
            (readAuthPattern != null ? "readAuthPattern=" + readAuthPattern + ", " : "") +
            (writeAuthPattern != null ? "writeAuthPattern=" + writeAuthPattern + ", " : "") +
            (enrollProcessorId != null ? "enrollProcessorId=" + enrollProcessorId + ", " : "") +
            (messageTypesId != null ? "messageTypesId=" + messageTypesId + ", " : "") +
            "}";
    }
}
