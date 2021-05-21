package hu.noreg.iotgw2.service.dto;

import hu.noreg.iotgw2.domain.enumeration.PkAlgorithm;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link hu.noreg.iotgw2.domain.KeyPair} entity.
 */
public class KeyPairDTO implements Serializable {

    private Long id;

    /**
     * SHA-1 hash of the ANS.1 encoded publicKey. Lowercase hex string without spaces.
     */
    @NotNull
    @ApiModelProperty(value = "SHA-1 hash of the ANS.1 encoded publicKey. Lowercase hex string without spaces.", required = true)
    private String keyId;

    @NotNull
    private PkAlgorithm algorithm;

    /**
     * ANS.1 encoded public key
     */

    @ApiModelProperty(value = "ANS.1 encoded public key", required = true)
    @Lob
    private byte[] publicKey;

    private String publicKeyContentType;

    @Lob
    private String privateKeyDescriptor;

    /**
     * ANS.1 encoded public key
     */
    @ApiModelProperty(value = "ANS.1 encoded public key")
    @Lob
    private byte[] attestationData;

    private String attestationDataContentType;

    /**
     * ANS.1 encoded certificate
     */
    @ApiModelProperty(value = "ANS.1 encoded certificate")
    @Lob
    private byte[] associatedCert;

    private String associatedCertContentType;

    /**
     * ANS.1 encoded PKCS#10
     */
    @ApiModelProperty(value = "ANS.1 encoded PKCS#10")
    @Lob
    private byte[] certificateRequest;

    private String certificateRequestContentType;
    private String certSubjectDN;

    private String certIssuerDN;

    private String certSerial;

    private ZonedDateTime certNotBefore;

    private ZonedDateTime certNotAfter;

    private ZonedDateTime certRevoked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public PkAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(PkAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKeyContentType() {
        return publicKeyContentType;
    }

    public void setPublicKeyContentType(String publicKeyContentType) {
        this.publicKeyContentType = publicKeyContentType;
    }

    public String getPrivateKeyDescriptor() {
        return privateKeyDescriptor;
    }

    public void setPrivateKeyDescriptor(String privateKeyDescriptor) {
        this.privateKeyDescriptor = privateKeyDescriptor;
    }

    public byte[] getAttestationData() {
        return attestationData;
    }

    public void setAttestationData(byte[] attestationData) {
        this.attestationData = attestationData;
    }

    public String getAttestationDataContentType() {
        return attestationDataContentType;
    }

    public void setAttestationDataContentType(String attestationDataContentType) {
        this.attestationDataContentType = attestationDataContentType;
    }

    public byte[] getAssociatedCert() {
        return associatedCert;
    }

    public void setAssociatedCert(byte[] associatedCert) {
        this.associatedCert = associatedCert;
    }

    public String getAssociatedCertContentType() {
        return associatedCertContentType;
    }

    public void setAssociatedCertContentType(String associatedCertContentType) {
        this.associatedCertContentType = associatedCertContentType;
    }

    public byte[] getCertificateRequest() {
        return certificateRequest;
    }

    public void setCertificateRequest(byte[] certificateRequest) {
        this.certificateRequest = certificateRequest;
    }

    public String getCertificateRequestContentType() {
        return certificateRequestContentType;
    }

    public void setCertificateRequestContentType(String certificateRequestContentType) {
        this.certificateRequestContentType = certificateRequestContentType;
    }

    public String getCertSubjectDN() {
        return certSubjectDN;
    }

    public void setCertSubjectDN(String certSubjectDN) {
        this.certSubjectDN = certSubjectDN;
    }

    public String getCertIssuerDN() {
        return certIssuerDN;
    }

    public void setCertIssuerDN(String certIssuerDN) {
        this.certIssuerDN = certIssuerDN;
    }

    public String getCertSerial() {
        return certSerial;
    }

    public void setCertSerial(String certSerial) {
        this.certSerial = certSerial;
    }

    public ZonedDateTime getCertNotBefore() {
        return certNotBefore;
    }

    public void setCertNotBefore(ZonedDateTime certNotBefore) {
        this.certNotBefore = certNotBefore;
    }

    public ZonedDateTime getCertNotAfter() {
        return certNotAfter;
    }

    public void setCertNotAfter(ZonedDateTime certNotAfter) {
        this.certNotAfter = certNotAfter;
    }

    public ZonedDateTime getCertRevoked() {
        return certRevoked;
    }

    public void setCertRevoked(ZonedDateTime certRevoked) {
        this.certRevoked = certRevoked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KeyPairDTO)) {
            return false;
        }

        KeyPairDTO keyPairDTO = (KeyPairDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, keyPairDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KeyPairDTO{" +
            "id=" + getId() +
            ", keyId='" + getKeyId() + "'" +
            ", algorithm='" + getAlgorithm() + "'" +
            ", publicKey='" + getPublicKey() + "'" +
            ", privateKeyDescriptor='" + getPrivateKeyDescriptor() + "'" +
            ", attestationData='" + getAttestationData() + "'" +
            ", associatedCert='" + getAssociatedCert() + "'" +
            ", certificateRequest='" + getCertificateRequest() + "'" +
            ", certSubjectDN='" + getCertSubjectDN() + "'" +
            ", certIssuerDN='" + getCertIssuerDN() + "'" +
            ", certSerial='" + getCertSerial() + "'" +
            ", certNotBefore='" + getCertNotBefore() + "'" +
            ", certNotAfter='" + getCertNotAfter() + "'" +
            ", certRevoked='" + getCertRevoked() + "'" +
            "}";
    }
}
