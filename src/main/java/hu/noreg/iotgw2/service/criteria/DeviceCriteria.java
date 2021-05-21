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
import tech.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link hu.noreg.iotgw2.domain.Device} entity. This class is used
 * in {@link hu.noreg.iotgw2.web.rest.DeviceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /devices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DeviceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter visualId;

    private StringFilter description;

    private StringFilter enrollmentCode;

    private ZonedDateTimeFilter enrollmentTime;

    private StringFilter stateFieldValue01;

    private StringFilter stateFieldValue02;

    private StringFilter stateFieldValue03;

    private StringFilter stateFieldValue04;

    private StringFilter readAuthPattern;

    private StringFilter writeAuthPattern;

    private LongFilter deviceSignKeyId;

    private LongFilter deviceEncKeyId;

    private LongFilter serverSignKeyId;

    private LongFilter serverEncKeyId;

    private LongFilter nextServerSignKeyId;

    private LongFilter nextServerEncKeyId;

    private LongFilter typeId;

    private LongFilter orgUnitId;

    public DeviceCriteria() {}

    public DeviceCriteria(DeviceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.visualId = other.visualId == null ? null : other.visualId.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.enrollmentCode = other.enrollmentCode == null ? null : other.enrollmentCode.copy();
        this.enrollmentTime = other.enrollmentTime == null ? null : other.enrollmentTime.copy();
        this.stateFieldValue01 = other.stateFieldValue01 == null ? null : other.stateFieldValue01.copy();
        this.stateFieldValue02 = other.stateFieldValue02 == null ? null : other.stateFieldValue02.copy();
        this.stateFieldValue03 = other.stateFieldValue03 == null ? null : other.stateFieldValue03.copy();
        this.stateFieldValue04 = other.stateFieldValue04 == null ? null : other.stateFieldValue04.copy();
        this.readAuthPattern = other.readAuthPattern == null ? null : other.readAuthPattern.copy();
        this.writeAuthPattern = other.writeAuthPattern == null ? null : other.writeAuthPattern.copy();
        this.deviceSignKeyId = other.deviceSignKeyId == null ? null : other.deviceSignKeyId.copy();
        this.deviceEncKeyId = other.deviceEncKeyId == null ? null : other.deviceEncKeyId.copy();
        this.serverSignKeyId = other.serverSignKeyId == null ? null : other.serverSignKeyId.copy();
        this.serverEncKeyId = other.serverEncKeyId == null ? null : other.serverEncKeyId.copy();
        this.nextServerSignKeyId = other.nextServerSignKeyId == null ? null : other.nextServerSignKeyId.copy();
        this.nextServerEncKeyId = other.nextServerEncKeyId == null ? null : other.nextServerEncKeyId.copy();
        this.typeId = other.typeId == null ? null : other.typeId.copy();
        this.orgUnitId = other.orgUnitId == null ? null : other.orgUnitId.copy();
    }

    @Override
    public DeviceCriteria copy() {
        return new DeviceCriteria(this);
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

    public StringFilter getVisualId() {
        return visualId;
    }

    public StringFilter visualId() {
        if (visualId == null) {
            visualId = new StringFilter();
        }
        return visualId;
    }

    public void setVisualId(StringFilter visualId) {
        this.visualId = visualId;
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

    public StringFilter getEnrollmentCode() {
        return enrollmentCode;
    }

    public StringFilter enrollmentCode() {
        if (enrollmentCode == null) {
            enrollmentCode = new StringFilter();
        }
        return enrollmentCode;
    }

    public void setEnrollmentCode(StringFilter enrollmentCode) {
        this.enrollmentCode = enrollmentCode;
    }

    public ZonedDateTimeFilter getEnrollmentTime() {
        return enrollmentTime;
    }

    public ZonedDateTimeFilter enrollmentTime() {
        if (enrollmentTime == null) {
            enrollmentTime = new ZonedDateTimeFilter();
        }
        return enrollmentTime;
    }

    public void setEnrollmentTime(ZonedDateTimeFilter enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }

    public StringFilter getStateFieldValue01() {
        return stateFieldValue01;
    }

    public StringFilter stateFieldValue01() {
        if (stateFieldValue01 == null) {
            stateFieldValue01 = new StringFilter();
        }
        return stateFieldValue01;
    }

    public void setStateFieldValue01(StringFilter stateFieldValue01) {
        this.stateFieldValue01 = stateFieldValue01;
    }

    public StringFilter getStateFieldValue02() {
        return stateFieldValue02;
    }

    public StringFilter stateFieldValue02() {
        if (stateFieldValue02 == null) {
            stateFieldValue02 = new StringFilter();
        }
        return stateFieldValue02;
    }

    public void setStateFieldValue02(StringFilter stateFieldValue02) {
        this.stateFieldValue02 = stateFieldValue02;
    }

    public StringFilter getStateFieldValue03() {
        return stateFieldValue03;
    }

    public StringFilter stateFieldValue03() {
        if (stateFieldValue03 == null) {
            stateFieldValue03 = new StringFilter();
        }
        return stateFieldValue03;
    }

    public void setStateFieldValue03(StringFilter stateFieldValue03) {
        this.stateFieldValue03 = stateFieldValue03;
    }

    public StringFilter getStateFieldValue04() {
        return stateFieldValue04;
    }

    public StringFilter stateFieldValue04() {
        if (stateFieldValue04 == null) {
            stateFieldValue04 = new StringFilter();
        }
        return stateFieldValue04;
    }

    public void setStateFieldValue04(StringFilter stateFieldValue04) {
        this.stateFieldValue04 = stateFieldValue04;
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

    public LongFilter getDeviceSignKeyId() {
        return deviceSignKeyId;
    }

    public LongFilter deviceSignKeyId() {
        if (deviceSignKeyId == null) {
            deviceSignKeyId = new LongFilter();
        }
        return deviceSignKeyId;
    }

    public void setDeviceSignKeyId(LongFilter deviceSignKeyId) {
        this.deviceSignKeyId = deviceSignKeyId;
    }

    public LongFilter getDeviceEncKeyId() {
        return deviceEncKeyId;
    }

    public LongFilter deviceEncKeyId() {
        if (deviceEncKeyId == null) {
            deviceEncKeyId = new LongFilter();
        }
        return deviceEncKeyId;
    }

    public void setDeviceEncKeyId(LongFilter deviceEncKeyId) {
        this.deviceEncKeyId = deviceEncKeyId;
    }

    public LongFilter getServerSignKeyId() {
        return serverSignKeyId;
    }

    public LongFilter serverSignKeyId() {
        if (serverSignKeyId == null) {
            serverSignKeyId = new LongFilter();
        }
        return serverSignKeyId;
    }

    public void setServerSignKeyId(LongFilter serverSignKeyId) {
        this.serverSignKeyId = serverSignKeyId;
    }

    public LongFilter getServerEncKeyId() {
        return serverEncKeyId;
    }

    public LongFilter serverEncKeyId() {
        if (serverEncKeyId == null) {
            serverEncKeyId = new LongFilter();
        }
        return serverEncKeyId;
    }

    public void setServerEncKeyId(LongFilter serverEncKeyId) {
        this.serverEncKeyId = serverEncKeyId;
    }

    public LongFilter getNextServerSignKeyId() {
        return nextServerSignKeyId;
    }

    public LongFilter nextServerSignKeyId() {
        if (nextServerSignKeyId == null) {
            nextServerSignKeyId = new LongFilter();
        }
        return nextServerSignKeyId;
    }

    public void setNextServerSignKeyId(LongFilter nextServerSignKeyId) {
        this.nextServerSignKeyId = nextServerSignKeyId;
    }

    public LongFilter getNextServerEncKeyId() {
        return nextServerEncKeyId;
    }

    public LongFilter nextServerEncKeyId() {
        if (nextServerEncKeyId == null) {
            nextServerEncKeyId = new LongFilter();
        }
        return nextServerEncKeyId;
    }

    public void setNextServerEncKeyId(LongFilter nextServerEncKeyId) {
        this.nextServerEncKeyId = nextServerEncKeyId;
    }

    public LongFilter getTypeId() {
        return typeId;
    }

    public LongFilter typeId() {
        if (typeId == null) {
            typeId = new LongFilter();
        }
        return typeId;
    }

    public void setTypeId(LongFilter typeId) {
        this.typeId = typeId;
    }

    public LongFilter getOrgUnitId() {
        return orgUnitId;
    }

    public LongFilter orgUnitId() {
        if (orgUnitId == null) {
            orgUnitId = new LongFilter();
        }
        return orgUnitId;
    }

    public void setOrgUnitId(LongFilter orgUnitId) {
        this.orgUnitId = orgUnitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DeviceCriteria that = (DeviceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(visualId, that.visualId) &&
            Objects.equals(description, that.description) &&
            Objects.equals(enrollmentCode, that.enrollmentCode) &&
            Objects.equals(enrollmentTime, that.enrollmentTime) &&
            Objects.equals(stateFieldValue01, that.stateFieldValue01) &&
            Objects.equals(stateFieldValue02, that.stateFieldValue02) &&
            Objects.equals(stateFieldValue03, that.stateFieldValue03) &&
            Objects.equals(stateFieldValue04, that.stateFieldValue04) &&
            Objects.equals(readAuthPattern, that.readAuthPattern) &&
            Objects.equals(writeAuthPattern, that.writeAuthPattern) &&
            Objects.equals(deviceSignKeyId, that.deviceSignKeyId) &&
            Objects.equals(deviceEncKeyId, that.deviceEncKeyId) &&
            Objects.equals(serverSignKeyId, that.serverSignKeyId) &&
            Objects.equals(serverEncKeyId, that.serverEncKeyId) &&
            Objects.equals(nextServerSignKeyId, that.nextServerSignKeyId) &&
            Objects.equals(nextServerEncKeyId, that.nextServerEncKeyId) &&
            Objects.equals(typeId, that.typeId) &&
            Objects.equals(orgUnitId, that.orgUnitId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            visualId,
            description,
            enrollmentCode,
            enrollmentTime,
            stateFieldValue01,
            stateFieldValue02,
            stateFieldValue03,
            stateFieldValue04,
            readAuthPattern,
            writeAuthPattern,
            deviceSignKeyId,
            deviceEncKeyId,
            serverSignKeyId,
            serverEncKeyId,
            nextServerSignKeyId,
            nextServerEncKeyId,
            typeId,
            orgUnitId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (visualId != null ? "visualId=" + visualId + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (enrollmentCode != null ? "enrollmentCode=" + enrollmentCode + ", " : "") +
            (enrollmentTime != null ? "enrollmentTime=" + enrollmentTime + ", " : "") +
            (stateFieldValue01 != null ? "stateFieldValue01=" + stateFieldValue01 + ", " : "") +
            (stateFieldValue02 != null ? "stateFieldValue02=" + stateFieldValue02 + ", " : "") +
            (stateFieldValue03 != null ? "stateFieldValue03=" + stateFieldValue03 + ", " : "") +
            (stateFieldValue04 != null ? "stateFieldValue04=" + stateFieldValue04 + ", " : "") +
            (readAuthPattern != null ? "readAuthPattern=" + readAuthPattern + ", " : "") +
            (writeAuthPattern != null ? "writeAuthPattern=" + writeAuthPattern + ", " : "") +
            (deviceSignKeyId != null ? "deviceSignKeyId=" + deviceSignKeyId + ", " : "") +
            (deviceEncKeyId != null ? "deviceEncKeyId=" + deviceEncKeyId + ", " : "") +
            (serverSignKeyId != null ? "serverSignKeyId=" + serverSignKeyId + ", " : "") +
            (serverEncKeyId != null ? "serverEncKeyId=" + serverEncKeyId + ", " : "") +
            (nextServerSignKeyId != null ? "nextServerSignKeyId=" + nextServerSignKeyId + ", " : "") +
            (nextServerEncKeyId != null ? "nextServerEncKeyId=" + nextServerEncKeyId + ", " : "") +
            (typeId != null ? "typeId=" + typeId + ", " : "") +
            (orgUnitId != null ? "orgUnitId=" + orgUnitId + ", " : "") +
            "}";
    }
}
