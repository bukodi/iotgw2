package hu.noreg.iotgw2.domain;

import hu.noreg.iotgw2.domain.enumeration.PkAlgorithm;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A KeyPair.
 */
@Entity
@Table(name = "iot_keypair")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KeyPair implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * SHA-1 hash of the ANS.1 encoded publicKey. Lowercase hex string without spaces.
     */
    @NotNull
    @Column(name = "key_id", nullable = false)
    private String keyId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "algorithm", nullable = false)
    private PkAlgorithm algorithm;

    /**
     * ANS.1 encoded public key
     */

    @Lob
    @Column(name = "public_key", nullable = false)
    private byte[] publicKey;

    @Column(name = "public_key_content_type", nullable = false)
    private String publicKeyContentType;

    @Lob
    @Column(name = "private_key_descriptor")
    private String privateKeyDescriptor;

    /**
     * ANS.1 encoded public key
     */
    @Lob
    @Column(name = "attestation_data")
    private byte[] attestationData;

    @Column(name = "attestation_data_content_type")
    private String attestationDataContentType;

    /**
     * ANS.1 encoded certificate
     */
    @Lob
    @Column(name = "associated_cert")
    private byte[] associatedCert;

    @Column(name = "associated_cert_content_type")
    private String associatedCertContentType;

    /**
     * ANS.1 encoded PKCS#10
     */
    @Lob
    @Column(name = "certificate_request")
    private byte[] certificateRequest;

    @Column(name = "certificate_request_content_type")
    private String certificateRequestContentType;

    @Column(name = "cert_subject_dn")
    private String certSubjectDN;

    @Column(name = "cert_issuer_dn")
    private String certIssuerDN;

    @Column(name = "cert_serial")
    private String certSerial;

    @Column(name = "cert_not_before")
    private ZonedDateTime certNotBefore;

    @Column(name = "cert_not_after")
    private ZonedDateTime certNotAfter;

    @Column(name = "cert_revoked")
    private ZonedDateTime certRevoked;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public KeyPair id(Long id) {
        this.id = id;
        return this;
    }

    public String getKeyId() {
        return this.keyId;
    }

    public KeyPair keyId(String keyId) {
        this.keyId = keyId;
        return this;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public PkAlgorithm getAlgorithm() {
        return this.algorithm;
    }

    public KeyPair algorithm(PkAlgorithm algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public void setAlgorithm(PkAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public byte[] getPublicKey() {
        return this.publicKey;
    }

    public KeyPair publicKey(byte[] publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKeyContentType() {
        return this.publicKeyContentType;
    }

    public KeyPair publicKeyContentType(String publicKeyContentType) {
        this.publicKeyContentType = publicKeyContentType;
        return this;
    }

    public void setPublicKeyContentType(String publicKeyContentType) {
        this.publicKeyContentType = publicKeyContentType;
    }

    public String getPrivateKeyDescriptor() {
        return this.privateKeyDescriptor;
    }

    public KeyPair privateKeyDescriptor(String privateKeyDescriptor) {
        this.privateKeyDescriptor = privateKeyDescriptor;
        return this;
    }

    public void setPrivateKeyDescriptor(String privateKeyDescriptor) {
        this.privateKeyDescriptor = privateKeyDescriptor;
    }

    public byte[] getAttestationData() {
        return this.attestationData;
    }

    public KeyPair attestationData(byte[] attestationData) {
        this.attestationData = attestationData;
        return this;
    }

    public void setAttestationData(byte[] attestationData) {
        this.attestationData = attestationData;
    }

    public String getAttestationDataContentType() {
        return this.attestationDataContentType;
    }

    public KeyPair attestationDataContentType(String attestationDataContentType) {
        this.attestationDataContentType = attestationDataContentType;
        return this;
    }

    public void setAttestationDataContentType(String attestationDataContentType) {
        this.attestationDataContentType = attestationDataContentType;
    }

    public byte[] getAssociatedCert() {
        return this.associatedCert;
    }

    public KeyPair associatedCert(byte[] associatedCert) {
        this.associatedCert = associatedCert;
        return this;
    }

    public void setAssociatedCert(byte[] associatedCert) {
        this.associatedCert = associatedCert;
    }

    public String getAssociatedCertContentType() {
        return this.associatedCertContentType;
    }

    public KeyPair associatedCertContentType(String associatedCertContentType) {
        this.associatedCertContentType = associatedCertContentType;
        return this;
    }

    public void setAssociatedCertContentType(String associatedCertContentType) {
        this.associatedCertContentType = associatedCertContentType;
    }

    public byte[] getCertificateRequest() {
        return this.certificateRequest;
    }

    public KeyPair certificateRequest(byte[] certificateRequest) {
        this.certificateRequest = certificateRequest;
        return this;
    }

    public void setCertificateRequest(byte[] certificateRequest) {
        this.certificateRequest = certificateRequest;
    }

    public String getCertificateRequestContentType() {
        return this.certificateRequestContentType;
    }

    public KeyPair certificateRequestContentType(String certificateRequestContentType) {
        this.certificateRequestContentType = certificateRequestContentType;
        return this;
    }

    public void setCertificateRequestContentType(String certificateRequestContentType) {
        this.certificateRequestContentType = certificateRequestContentType;
    }

    public String getCertSubjectDN() {
        return this.certSubjectDN;
    }

    public KeyPair certSubjectDN(String certSubjectDN) {
        this.certSubjectDN = certSubjectDN;
        return this;
    }

    public void setCertSubjectDN(String certSubjectDN) {
        this.certSubjectDN = certSubjectDN;
    }

    public String getCertIssuerDN() {
        return this.certIssuerDN;
    }

    public KeyPair certIssuerDN(String certIssuerDN) {
        this.certIssuerDN = certIssuerDN;
        return this;
    }

    public void setCertIssuerDN(String certIssuerDN) {
        this.certIssuerDN = certIssuerDN;
    }

    public String getCertSerial() {
        return this.certSerial;
    }

    public KeyPair certSerial(String certSerial) {
        this.certSerial = certSerial;
        return this;
    }

    public void setCertSerial(String certSerial) {
        this.certSerial = certSerial;
    }

    public ZonedDateTime getCertNotBefore() {
        return this.certNotBefore;
    }

    public KeyPair certNotBefore(ZonedDateTime certNotBefore) {
        this.certNotBefore = certNotBefore;
        return this;
    }

    public void setCertNotBefore(ZonedDateTime certNotBefore) {
        this.certNotBefore = certNotBefore;
    }

    public ZonedDateTime getCertNotAfter() {
        return this.certNotAfter;
    }

    public KeyPair certNotAfter(ZonedDateTime certNotAfter) {
        this.certNotAfter = certNotAfter;
        return this;
    }

    public void setCertNotAfter(ZonedDateTime certNotAfter) {
        this.certNotAfter = certNotAfter;
    }

    public ZonedDateTime getCertRevoked() {
        return this.certRevoked;
    }

    public KeyPair certRevoked(ZonedDateTime certRevoked) {
        this.certRevoked = certRevoked;
        return this;
    }

    public void setCertRevoked(ZonedDateTime certRevoked) {
        this.certRevoked = certRevoked;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KeyPair)) {
            return false;
        }
        return id != null && id.equals(((KeyPair) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KeyPair{" +
            "id=" + getId() +
            ", keyId='" + getKeyId() + "'" +
            ", algorithm='" + getAlgorithm() + "'" +
            ", publicKey='" + getPublicKey() + "'" +
            ", publicKeyContentType='" + getPublicKeyContentType() + "'" +
            ", privateKeyDescriptor='" + getPrivateKeyDescriptor() + "'" +
            ", attestationData='" + getAttestationData() + "'" +
            ", attestationDataContentType='" + getAttestationDataContentType() + "'" +
            ", associatedCert='" + getAssociatedCert() + "'" +
            ", associatedCertContentType='" + getAssociatedCertContentType() + "'" +
            ", certificateRequest='" + getCertificateRequest() + "'" +
            ", certificateRequestContentType='" + getCertificateRequestContentType() + "'" +
            ", certSubjectDN='" + getCertSubjectDN() + "'" +
            ", certIssuerDN='" + getCertIssuerDN() + "'" +
            ", certSerial='" + getCertSerial() + "'" +
            ", certNotBefore='" + getCertNotBefore() + "'" +
            ", certNotAfter='" + getCertNotAfter() + "'" +
            ", certRevoked='" + getCertRevoked() + "'" +
            "}";
    }
}
