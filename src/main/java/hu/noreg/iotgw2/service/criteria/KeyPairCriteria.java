package hu.noreg.iotgw2.service.criteria;

import hu.noreg.iotgw2.domain.enumeration.PkAlgorithm;
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
 * Criteria class for the {@link hu.noreg.iotgw2.domain.KeyPair} entity. This class is used
 * in {@link hu.noreg.iotgw2.web.rest.KeyPairResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /key-pairs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class KeyPairCriteria implements Serializable, Criteria {

    /**
     * Class for filtering PkAlgorithm
     */
    public static class PkAlgorithmFilter extends Filter<PkAlgorithm> {

        public PkAlgorithmFilter() {}

        public PkAlgorithmFilter(PkAlgorithmFilter filter) {
            super(filter);
        }

        @Override
        public PkAlgorithmFilter copy() {
            return new PkAlgorithmFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter keyId;

    private PkAlgorithmFilter algorithm;

    private StringFilter certSubjectDN;

    private StringFilter certIssuerDN;

    private StringFilter certSerial;

    private ZonedDateTimeFilter certNotBefore;

    private ZonedDateTimeFilter certNotAfter;

    private ZonedDateTimeFilter certRevoked;

    public KeyPairCriteria() {}

    public KeyPairCriteria(KeyPairCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.keyId = other.keyId == null ? null : other.keyId.copy();
        this.algorithm = other.algorithm == null ? null : other.algorithm.copy();
        this.certSubjectDN = other.certSubjectDN == null ? null : other.certSubjectDN.copy();
        this.certIssuerDN = other.certIssuerDN == null ? null : other.certIssuerDN.copy();
        this.certSerial = other.certSerial == null ? null : other.certSerial.copy();
        this.certNotBefore = other.certNotBefore == null ? null : other.certNotBefore.copy();
        this.certNotAfter = other.certNotAfter == null ? null : other.certNotAfter.copy();
        this.certRevoked = other.certRevoked == null ? null : other.certRevoked.copy();
    }

    @Override
    public KeyPairCriteria copy() {
        return new KeyPairCriteria(this);
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

    public StringFilter getKeyId() {
        return keyId;
    }

    public StringFilter keyId() {
        if (keyId == null) {
            keyId = new StringFilter();
        }
        return keyId;
    }

    public void setKeyId(StringFilter keyId) {
        this.keyId = keyId;
    }

    public PkAlgorithmFilter getAlgorithm() {
        return algorithm;
    }

    public PkAlgorithmFilter algorithm() {
        if (algorithm == null) {
            algorithm = new PkAlgorithmFilter();
        }
        return algorithm;
    }

    public void setAlgorithm(PkAlgorithmFilter algorithm) {
        this.algorithm = algorithm;
    }

    public StringFilter getCertSubjectDN() {
        return certSubjectDN;
    }

    public StringFilter certSubjectDN() {
        if (certSubjectDN == null) {
            certSubjectDN = new StringFilter();
        }
        return certSubjectDN;
    }

    public void setCertSubjectDN(StringFilter certSubjectDN) {
        this.certSubjectDN = certSubjectDN;
    }

    public StringFilter getCertIssuerDN() {
        return certIssuerDN;
    }

    public StringFilter certIssuerDN() {
        if (certIssuerDN == null) {
            certIssuerDN = new StringFilter();
        }
        return certIssuerDN;
    }

    public void setCertIssuerDN(StringFilter certIssuerDN) {
        this.certIssuerDN = certIssuerDN;
    }

    public StringFilter getCertSerial() {
        return certSerial;
    }

    public StringFilter certSerial() {
        if (certSerial == null) {
            certSerial = new StringFilter();
        }
        return certSerial;
    }

    public void setCertSerial(StringFilter certSerial) {
        this.certSerial = certSerial;
    }

    public ZonedDateTimeFilter getCertNotBefore() {
        return certNotBefore;
    }

    public ZonedDateTimeFilter certNotBefore() {
        if (certNotBefore == null) {
            certNotBefore = new ZonedDateTimeFilter();
        }
        return certNotBefore;
    }

    public void setCertNotBefore(ZonedDateTimeFilter certNotBefore) {
        this.certNotBefore = certNotBefore;
    }

    public ZonedDateTimeFilter getCertNotAfter() {
        return certNotAfter;
    }

    public ZonedDateTimeFilter certNotAfter() {
        if (certNotAfter == null) {
            certNotAfter = new ZonedDateTimeFilter();
        }
        return certNotAfter;
    }

    public void setCertNotAfter(ZonedDateTimeFilter certNotAfter) {
        this.certNotAfter = certNotAfter;
    }

    public ZonedDateTimeFilter getCertRevoked() {
        return certRevoked;
    }

    public ZonedDateTimeFilter certRevoked() {
        if (certRevoked == null) {
            certRevoked = new ZonedDateTimeFilter();
        }
        return certRevoked;
    }

    public void setCertRevoked(ZonedDateTimeFilter certRevoked) {
        this.certRevoked = certRevoked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final KeyPairCriteria that = (KeyPairCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(keyId, that.keyId) &&
            Objects.equals(algorithm, that.algorithm) &&
            Objects.equals(certSubjectDN, that.certSubjectDN) &&
            Objects.equals(certIssuerDN, that.certIssuerDN) &&
            Objects.equals(certSerial, that.certSerial) &&
            Objects.equals(certNotBefore, that.certNotBefore) &&
            Objects.equals(certNotAfter, that.certNotAfter) &&
            Objects.equals(certRevoked, that.certRevoked)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, keyId, algorithm, certSubjectDN, certIssuerDN, certSerial, certNotBefore, certNotAfter, certRevoked);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KeyPairCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (keyId != null ? "keyId=" + keyId + ", " : "") +
            (algorithm != null ? "algorithm=" + algorithm + ", " : "") +
            (certSubjectDN != null ? "certSubjectDN=" + certSubjectDN + ", " : "") +
            (certIssuerDN != null ? "certIssuerDN=" + certIssuerDN + ", " : "") +
            (certSerial != null ? "certSerial=" + certSerial + ", " : "") +
            (certNotBefore != null ? "certNotBefore=" + certNotBefore + ", " : "") +
            (certNotAfter != null ? "certNotAfter=" + certNotAfter + ", " : "") +
            (certRevoked != null ? "certRevoked=" + certRevoked + ", " : "") +
            "}";
    }
}
