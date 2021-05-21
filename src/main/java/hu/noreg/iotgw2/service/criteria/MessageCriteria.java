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
 * Criteria class for the {@link hu.noreg.iotgw2.domain.Message} entity. This class is used
 * in {@link hu.noreg.iotgw2.web.rest.MessageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /messages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MessageCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter serverTime;

    private ZonedDateTimeFilter deviceTime;

    private StringFilter rawMessageSHA256;

    private BooleanFilter devToSrv;

    private StringFilter indexFieldValue01;

    private StringFilter indexFieldValue02;

    private StringFilter indexFieldValue03;

    private StringFilter indexFieldValue04;

    private LongFilter typeId;

    private LongFilter deviceId;

    public MessageCriteria() {}

    public MessageCriteria(MessageCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.serverTime = other.serverTime == null ? null : other.serverTime.copy();
        this.deviceTime = other.deviceTime == null ? null : other.deviceTime.copy();
        this.rawMessageSHA256 = other.rawMessageSHA256 == null ? null : other.rawMessageSHA256.copy();
        this.devToSrv = other.devToSrv == null ? null : other.devToSrv.copy();
        this.indexFieldValue01 = other.indexFieldValue01 == null ? null : other.indexFieldValue01.copy();
        this.indexFieldValue02 = other.indexFieldValue02 == null ? null : other.indexFieldValue02.copy();
        this.indexFieldValue03 = other.indexFieldValue03 == null ? null : other.indexFieldValue03.copy();
        this.indexFieldValue04 = other.indexFieldValue04 == null ? null : other.indexFieldValue04.copy();
        this.typeId = other.typeId == null ? null : other.typeId.copy();
        this.deviceId = other.deviceId == null ? null : other.deviceId.copy();
    }

    @Override
    public MessageCriteria copy() {
        return new MessageCriteria(this);
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

    public ZonedDateTimeFilter getServerTime() {
        return serverTime;
    }

    public ZonedDateTimeFilter serverTime() {
        if (serverTime == null) {
            serverTime = new ZonedDateTimeFilter();
        }
        return serverTime;
    }

    public void setServerTime(ZonedDateTimeFilter serverTime) {
        this.serverTime = serverTime;
    }

    public ZonedDateTimeFilter getDeviceTime() {
        return deviceTime;
    }

    public ZonedDateTimeFilter deviceTime() {
        if (deviceTime == null) {
            deviceTime = new ZonedDateTimeFilter();
        }
        return deviceTime;
    }

    public void setDeviceTime(ZonedDateTimeFilter deviceTime) {
        this.deviceTime = deviceTime;
    }

    public StringFilter getRawMessageSHA256() {
        return rawMessageSHA256;
    }

    public StringFilter rawMessageSHA256() {
        if (rawMessageSHA256 == null) {
            rawMessageSHA256 = new StringFilter();
        }
        return rawMessageSHA256;
    }

    public void setRawMessageSHA256(StringFilter rawMessageSHA256) {
        this.rawMessageSHA256 = rawMessageSHA256;
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

    public StringFilter getIndexFieldValue01() {
        return indexFieldValue01;
    }

    public StringFilter indexFieldValue01() {
        if (indexFieldValue01 == null) {
            indexFieldValue01 = new StringFilter();
        }
        return indexFieldValue01;
    }

    public void setIndexFieldValue01(StringFilter indexFieldValue01) {
        this.indexFieldValue01 = indexFieldValue01;
    }

    public StringFilter getIndexFieldValue02() {
        return indexFieldValue02;
    }

    public StringFilter indexFieldValue02() {
        if (indexFieldValue02 == null) {
            indexFieldValue02 = new StringFilter();
        }
        return indexFieldValue02;
    }

    public void setIndexFieldValue02(StringFilter indexFieldValue02) {
        this.indexFieldValue02 = indexFieldValue02;
    }

    public StringFilter getIndexFieldValue03() {
        return indexFieldValue03;
    }

    public StringFilter indexFieldValue03() {
        if (indexFieldValue03 == null) {
            indexFieldValue03 = new StringFilter();
        }
        return indexFieldValue03;
    }

    public void setIndexFieldValue03(StringFilter indexFieldValue03) {
        this.indexFieldValue03 = indexFieldValue03;
    }

    public StringFilter getIndexFieldValue04() {
        return indexFieldValue04;
    }

    public StringFilter indexFieldValue04() {
        if (indexFieldValue04 == null) {
            indexFieldValue04 = new StringFilter();
        }
        return indexFieldValue04;
    }

    public void setIndexFieldValue04(StringFilter indexFieldValue04) {
        this.indexFieldValue04 = indexFieldValue04;
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

    public LongFilter getDeviceId() {
        return deviceId;
    }

    public LongFilter deviceId() {
        if (deviceId == null) {
            deviceId = new LongFilter();
        }
        return deviceId;
    }

    public void setDeviceId(LongFilter deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MessageCriteria that = (MessageCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(serverTime, that.serverTime) &&
            Objects.equals(deviceTime, that.deviceTime) &&
            Objects.equals(rawMessageSHA256, that.rawMessageSHA256) &&
            Objects.equals(devToSrv, that.devToSrv) &&
            Objects.equals(indexFieldValue01, that.indexFieldValue01) &&
            Objects.equals(indexFieldValue02, that.indexFieldValue02) &&
            Objects.equals(indexFieldValue03, that.indexFieldValue03) &&
            Objects.equals(indexFieldValue04, that.indexFieldValue04) &&
            Objects.equals(typeId, that.typeId) &&
            Objects.equals(deviceId, that.deviceId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            serverTime,
            deviceTime,
            rawMessageSHA256,
            devToSrv,
            indexFieldValue01,
            indexFieldValue02,
            indexFieldValue03,
            indexFieldValue04,
            typeId,
            deviceId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (serverTime != null ? "serverTime=" + serverTime + ", " : "") +
            (deviceTime != null ? "deviceTime=" + deviceTime + ", " : "") +
            (rawMessageSHA256 != null ? "rawMessageSHA256=" + rawMessageSHA256 + ", " : "") +
            (devToSrv != null ? "devToSrv=" + devToSrv + ", " : "") +
            (indexFieldValue01 != null ? "indexFieldValue01=" + indexFieldValue01 + ", " : "") +
            (indexFieldValue02 != null ? "indexFieldValue02=" + indexFieldValue02 + ", " : "") +
            (indexFieldValue03 != null ? "indexFieldValue03=" + indexFieldValue03 + ", " : "") +
            (indexFieldValue04 != null ? "indexFieldValue04=" + indexFieldValue04 + ", " : "") +
            (typeId != null ? "typeId=" + typeId + ", " : "") +
            (deviceId != null ? "deviceId=" + deviceId + ", " : "") +
            "}";
    }
}
