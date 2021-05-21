package hu.noreg.iotgw2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Device.
 */
@Entity
@Table(name = "iot_device")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique id of the IoT device
     */
    @NotNull
    @Column(name = "visual_id", nullable = false, unique = true)
    private String visualId;

    /**
     * Optional description
     */
    @Column(name = "description")
    private String description;

    /**
     * Unique random passphrase for first-time enrollment.
     */
    @Column(name = "enrollment_code")
    private String enrollmentCode;

    @Column(name = "enrollment_time")
    private ZonedDateTime enrollmentTime;

    @Column(name = "state_field_value_01")
    private String stateFieldValue01;

    @Column(name = "state_field_value_02")
    private String stateFieldValue02;

    @Column(name = "state_field_value_03")
    private String stateFieldValue03;

    @Column(name = "state_field_value_04")
    private String stateFieldValue04;

    @Lob
    @Column(name = "last_error")
    private String lastError;

    @Column(name = "read_auth_pattern")
    private String readAuthPattern;

    @Column(name = "write_auth_pattern")
    private String writeAuthPattern;

    @OneToOne
    @JoinColumn(unique = true)
    private KeyPair deviceSignKey;

    @OneToOne
    @JoinColumn(unique = true)
    private KeyPair deviceEncKey;

    @OneToOne
    @JoinColumn(unique = true)
    private KeyPair serverSignKey;

    @OneToOne
    @JoinColumn(unique = true)
    private KeyPair serverEncKey;

    @OneToOne
    @JoinColumn(unique = true)
    private KeyPair nextServerSignKey;

    @OneToOne
    @JoinColumn(unique = true)
    private KeyPair nextServerEncKey;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "enrollProcessor", "messageTypes" }, allowSetters = true)
    private DeviceType type;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "subUnits", "parent" }, allowSetters = true)
    private OrgUnit orgUnit;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Device id(Long id) {
        this.id = id;
        return this;
    }

    public String getVisualId() {
        return this.visualId;
    }

    public Device visualId(String visualId) {
        this.visualId = visualId;
        return this;
    }

    public void setVisualId(String visualId) {
        this.visualId = visualId;
    }

    public String getDescription() {
        return this.description;
    }

    public Device description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnrollmentCode() {
        return this.enrollmentCode;
    }

    public Device enrollmentCode(String enrollmentCode) {
        this.enrollmentCode = enrollmentCode;
        return this;
    }

    public void setEnrollmentCode(String enrollmentCode) {
        this.enrollmentCode = enrollmentCode;
    }

    public ZonedDateTime getEnrollmentTime() {
        return this.enrollmentTime;
    }

    public Device enrollmentTime(ZonedDateTime enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
        return this;
    }

    public void setEnrollmentTime(ZonedDateTime enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }

    public String getStateFieldValue01() {
        return this.stateFieldValue01;
    }

    public Device stateFieldValue01(String stateFieldValue01) {
        this.stateFieldValue01 = stateFieldValue01;
        return this;
    }

    public void setStateFieldValue01(String stateFieldValue01) {
        this.stateFieldValue01 = stateFieldValue01;
    }

    public String getStateFieldValue02() {
        return this.stateFieldValue02;
    }

    public Device stateFieldValue02(String stateFieldValue02) {
        this.stateFieldValue02 = stateFieldValue02;
        return this;
    }

    public void setStateFieldValue02(String stateFieldValue02) {
        this.stateFieldValue02 = stateFieldValue02;
    }

    public String getStateFieldValue03() {
        return this.stateFieldValue03;
    }

    public Device stateFieldValue03(String stateFieldValue03) {
        this.stateFieldValue03 = stateFieldValue03;
        return this;
    }

    public void setStateFieldValue03(String stateFieldValue03) {
        this.stateFieldValue03 = stateFieldValue03;
    }

    public String getStateFieldValue04() {
        return this.stateFieldValue04;
    }

    public Device stateFieldValue04(String stateFieldValue04) {
        this.stateFieldValue04 = stateFieldValue04;
        return this;
    }

    public void setStateFieldValue04(String stateFieldValue04) {
        this.stateFieldValue04 = stateFieldValue04;
    }

    public String getLastError() {
        return this.lastError;
    }

    public Device lastError(String lastError) {
        this.lastError = lastError;
        return this;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    public String getReadAuthPattern() {
        return this.readAuthPattern;
    }

    public Device readAuthPattern(String readAuthPattern) {
        this.readAuthPattern = readAuthPattern;
        return this;
    }

    public void setReadAuthPattern(String readAuthPattern) {
        this.readAuthPattern = readAuthPattern;
    }

    public String getWriteAuthPattern() {
        return this.writeAuthPattern;
    }

    public Device writeAuthPattern(String writeAuthPattern) {
        this.writeAuthPattern = writeAuthPattern;
        return this;
    }

    public void setWriteAuthPattern(String writeAuthPattern) {
        this.writeAuthPattern = writeAuthPattern;
    }

    public KeyPair getDeviceSignKey() {
        return this.deviceSignKey;
    }

    public Device deviceSignKey(KeyPair keyPair) {
        this.setDeviceSignKey(keyPair);
        return this;
    }

    public void setDeviceSignKey(KeyPair keyPair) {
        this.deviceSignKey = keyPair;
    }

    public KeyPair getDeviceEncKey() {
        return this.deviceEncKey;
    }

    public Device deviceEncKey(KeyPair keyPair) {
        this.setDeviceEncKey(keyPair);
        return this;
    }

    public void setDeviceEncKey(KeyPair keyPair) {
        this.deviceEncKey = keyPair;
    }

    public KeyPair getServerSignKey() {
        return this.serverSignKey;
    }

    public Device serverSignKey(KeyPair keyPair) {
        this.setServerSignKey(keyPair);
        return this;
    }

    public void setServerSignKey(KeyPair keyPair) {
        this.serverSignKey = keyPair;
    }

    public KeyPair getServerEncKey() {
        return this.serverEncKey;
    }

    public Device serverEncKey(KeyPair keyPair) {
        this.setServerEncKey(keyPair);
        return this;
    }

    public void setServerEncKey(KeyPair keyPair) {
        this.serverEncKey = keyPair;
    }

    public KeyPair getNextServerSignKey() {
        return this.nextServerSignKey;
    }

    public Device nextServerSignKey(KeyPair keyPair) {
        this.setNextServerSignKey(keyPair);
        return this;
    }

    public void setNextServerSignKey(KeyPair keyPair) {
        this.nextServerSignKey = keyPair;
    }

    public KeyPair getNextServerEncKey() {
        return this.nextServerEncKey;
    }

    public Device nextServerEncKey(KeyPair keyPair) {
        this.setNextServerEncKey(keyPair);
        return this;
    }

    public void setNextServerEncKey(KeyPair keyPair) {
        this.nextServerEncKey = keyPair;
    }

    public DeviceType getType() {
        return this.type;
    }

    public Device type(DeviceType deviceType) {
        this.setType(deviceType);
        return this;
    }

    public void setType(DeviceType deviceType) {
        this.type = deviceType;
    }

    public OrgUnit getOrgUnit() {
        return this.orgUnit;
    }

    public Device orgUnit(OrgUnit orgUnit) {
        this.setOrgUnit(orgUnit);
        return this;
    }

    public void setOrgUnit(OrgUnit orgUnit) {
        this.orgUnit = orgUnit;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Device)) {
            return false;
        }
        return id != null && id.equals(((Device) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Device{" +
            "id=" + getId() +
            ", visualId='" + getVisualId() + "'" +
            ", description='" + getDescription() + "'" +
            ", enrollmentCode='" + getEnrollmentCode() + "'" +
            ", enrollmentTime='" + getEnrollmentTime() + "'" +
            ", stateFieldValue01='" + getStateFieldValue01() + "'" +
            ", stateFieldValue02='" + getStateFieldValue02() + "'" +
            ", stateFieldValue03='" + getStateFieldValue03() + "'" +
            ", stateFieldValue04='" + getStateFieldValue04() + "'" +
            ", lastError='" + getLastError() + "'" +
            ", readAuthPattern='" + getReadAuthPattern() + "'" +
            ", writeAuthPattern='" + getWriteAuthPattern() + "'" +
            "}";
    }
}
