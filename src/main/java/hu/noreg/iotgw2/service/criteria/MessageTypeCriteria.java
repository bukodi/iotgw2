package hu.noreg.iotgw2.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.DurationFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link hu.noreg.iotgw2.domain.MessageType} entity. This class is used
 * in {@link hu.noreg.iotgw2.web.rest.MessageTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /message-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MessageTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter typeCode;

    private BooleanFilter devToSrv;

    private StringFilter name;

    private StringFilter description;

    private DurationFilter timeout;

    private DurationFilter retentionTime;

    private StringFilter indexFieldName01;

    private StringFilter indexFieldName02;

    private StringFilter indexFieldName03;

    private StringFilter indexFieldName04;

    private StringFilter readAuthPattern;

    private StringFilter writeAuthPattern;

    private LongFilter messageProcessorId;

    private LongFilter timeoutProcessorId;

    private LongFilter deviceTypesId;

    public MessageTypeCriteria() {}

    public MessageTypeCriteria(MessageTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.typeCode = other.typeCode == null ? null : other.typeCode.copy();
        this.devToSrv = other.devToSrv == null ? null : other.devToSrv.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.timeout = other.timeout == null ? null : other.timeout.copy();
        this.retentionTime = other.retentionTime == null ? null : other.retentionTime.copy();
        this.indexFieldName01 = other.indexFieldName01 == null ? null : other.indexFieldName01.copy();
        this.indexFieldName02 = other.indexFieldName02 == null ? null : other.indexFieldName02.copy();
        this.indexFieldName03 = other.indexFieldName03 == null ? null : other.indexFieldName03.copy();
        this.indexFieldName04 = other.indexFieldName04 == null ? null : other.indexFieldName04.copy();
        this.readAuthPattern = other.readAuthPattern == null ? null : other.readAuthPattern.copy();
        this.writeAuthPattern = other.writeAuthPattern == null ? null : other.writeAuthPattern.copy();
        this.messageProcessorId = other.messageProcessorId == null ? null : other.messageProcessorId.copy();
        this.timeoutProcessorId = other.timeoutProcessorId == null ? null : other.timeoutProcessorId.copy();
        this.deviceTypesId = other.deviceTypesId == null ? null : other.deviceTypesId.copy();
    }

    @Override
    public MessageTypeCriteria copy() {
        return new MessageTypeCriteria(this);
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

    public IntegerFilter getTypeCode() {
        return typeCode;
    }

    public IntegerFilter typeCode() {
        if (typeCode == null) {
            typeCode = new IntegerFilter();
        }
        return typeCode;
    }

    public void setTypeCode(IntegerFilter typeCode) {
        this.typeCode = typeCode;
    }

    public BooleanFilter getDevToSrv() {
        return devToSrv;
    }

    public BooleanFilter devToSrv() {
        if (devToSrv == null) {
            devToSrv = new BooleanFilter();
        }
        return devToSrv;
    }

    public void setDevToSrv(BooleanFilter devToSrv) {
        this.devToSrv = devToSrv;
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

    public DurationFilter getTimeout() {
        return timeout;
    }

    public DurationFilter timeout() {
        if (timeout == null) {
            timeout = new DurationFilter();
        }
        return timeout;
    }

    public void setTimeout(DurationFilter timeout) {
        this.timeout = timeout;
    }

    public DurationFilter getRetentionTime() {
        return retentionTime;
    }

    public DurationFilter retentionTime() {
        if (retentionTime == null) {
            retentionTime = new DurationFilter();
        }
        return retentionTime;
    }

    public void setRetentionTime(DurationFilter retentionTime) {
        this.retentionTime = retentionTime;
    }

    public StringFilter getIndexFieldName01() {
        return indexFieldName01;
    }

    public StringFilter indexFieldName01() {
        if (indexFieldName01 == null) {
            indexFieldName01 = new StringFilter();
        }
        return indexFieldName01;
    }

    public void setIndexFieldName01(StringFilter indexFieldName01) {
        this.indexFieldName01 = indexFieldName01;
    }

    public StringFilter getIndexFieldName02() {
        return indexFieldName02;
    }

    public StringFilter indexFieldName02() {
        if (indexFieldName02 == null) {
            indexFieldName02 = new StringFilter();
        }
        return indexFieldName02;
    }

    public void setIndexFieldName02(StringFilter indexFieldName02) {
        this.indexFieldName02 = indexFieldName02;
    }

    public StringFilter getIndexFieldName03() {
        return indexFieldName03;
    }

    public StringFilter indexFieldName03() {
        if (indexFieldName03 == null) {
            indexFieldName03 = new StringFilter();
        }
        return indexFieldName03;
    }

    public void setIndexFieldName03(StringFilter indexFieldName03) {
        this.indexFieldName03 = indexFieldName03;
    }

    public StringFilter getIndexFieldName04() {
        return indexFieldName04;
    }

    public StringFilter indexFieldName04() {
        if (indexFieldName04 == null) {
            indexFieldName04 = new StringFilter();
        }
        return indexFieldName04;
    }

    public void setIndexFieldName04(StringFilter indexFieldName04) {
        this.indexFieldName04 = indexFieldName04;
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

    public LongFilter getMessageProcessorId() {
        return messageProcessorId;
    }

    public LongFilter messageProcessorId() {
        if (messageProcessorId == null) {
            messageProcessorId = new LongFilter();
        }
        return messageProcessorId;
    }

    public void setMessageProcessorId(LongFilter messageProcessorId) {
        this.messageProcessorId = messageProcessorId;
    }

    public LongFilter getTimeoutProcessorId() {
        return timeoutProcessorId;
    }

    public LongFilter timeoutProcessorId() {
        if (timeoutProcessorId == null) {
            timeoutProcessorId = new LongFilter();
        }
        return timeoutProcessorId;
    }

    public void setTimeoutProcessorId(LongFilter timeoutProcessorId) {
        this.timeoutProcessorId = timeoutProcessorId;
    }

    public LongFilter getDeviceTypesId() {
        return deviceTypesId;
    }

    public LongFilter deviceTypesId() {
        if (deviceTypesId == null) {
            deviceTypesId = new LongFilter();
        }
        return deviceTypesId;
    }

    public void setDeviceTypesId(LongFilter deviceTypesId) {
        this.deviceTypesId = deviceTypesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MessageTypeCriteria that = (MessageTypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(typeCode, that.typeCode) &&
            Objects.equals(devToSrv, that.devToSrv) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(timeout, that.timeout) &&
            Objects.equals(retentionTime, that.retentionTime) &&
            Objects.equals(indexFieldName01, that.indexFieldName01) &&
            Objects.equals(indexFieldName02, that.indexFieldName02) &&
            Objects.equals(indexFieldName03, that.indexFieldName03) &&
            Objects.equals(indexFieldName04, that.indexFieldName04) &&
            Objects.equals(readAuthPattern, that.readAuthPattern) &&
            Objects.equals(writeAuthPattern, that.writeAuthPattern) &&
            Objects.equals(messageProcessorId, that.messageProcessorId) &&
            Objects.equals(timeoutProcessorId, that.timeoutProcessorId) &&
            Objects.equals(deviceTypesId, that.deviceTypesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            typeCode,
            devToSrv,
            name,
            description,
            timeout,
            retentionTime,
            indexFieldName01,
            indexFieldName02,
            indexFieldName03,
            indexFieldName04,
            readAuthPattern,
            writeAuthPattern,
            messageProcessorId,
            timeoutProcessorId,
            deviceTypesId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageTypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (typeCode != null ? "typeCode=" + typeCode + ", " : "") +
            (devToSrv != null ? "devToSrv=" + devToSrv + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (timeout != null ? "timeout=" + timeout + ", " : "") +
            (retentionTime != null ? "retentionTime=" + retentionTime + ", " : "") +
            (indexFieldName01 != null ? "indexFieldName01=" + indexFieldName01 + ", " : "") +
            (indexFieldName02 != null ? "indexFieldName02=" + indexFieldName02 + ", " : "") +
            (indexFieldName03 != null ? "indexFieldName03=" + indexFieldName03 + ", " : "") +
            (indexFieldName04 != null ? "indexFieldName04=" + indexFieldName04 + ", " : "") +
            (readAuthPattern != null ? "readAuthPattern=" + readAuthPattern + ", " : "") +
            (writeAuthPattern != null ? "writeAuthPattern=" + writeAuthPattern + ", " : "") +
            (messageProcessorId != null ? "messageProcessorId=" + messageProcessorId + ", " : "") +
            (timeoutProcessorId != null ? "timeoutProcessorId=" + timeoutProcessorId + ", " : "") +
            (deviceTypesId != null ? "deviceTypesId=" + deviceTypesId + ", " : "") +
            "}";
    }
}
