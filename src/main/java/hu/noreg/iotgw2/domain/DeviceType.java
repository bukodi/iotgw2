package hu.noreg.iotgw2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DeviceType.
 */
@Entity
@Table(name = "iot_device_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeviceType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique name of the device type
     */
    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * Optional description
     */
    @Column(name = "description")
    private String description;

    @Column(name = "state_field_name_01")
    private String stateFieldName01;

    @Column(name = "state_field_name_02")
    private String stateFieldName02;

    @Column(name = "state_field_name_03")
    private String stateFieldName03;

    @Column(name = "state_field_name_04")
    private String stateFieldName04;

    @Column(name = "read_auth_pattern")
    private String readAuthPattern;

    @Column(name = "write_auth_pattern")
    private String writeAuthPattern;

    @ManyToOne
    private Processor enrollProcessor;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_iot_device_type__message_types",
        joinColumns = @JoinColumn(name = "iot_device_type_id"),
        inverseJoinColumns = @JoinColumn(name = "message_types_id")
    )
    @JsonIgnoreProperties(value = { "messageProcessor", "timeoutProcessor", "deviceTypes" }, allowSetters = true)
    private Set<MessageType> messageTypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeviceType id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public DeviceType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public DeviceType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStateFieldName01() {
        return this.stateFieldName01;
    }

    public DeviceType stateFieldName01(String stateFieldName01) {
        this.stateFieldName01 = stateFieldName01;
        return this;
    }

    public void setStateFieldName01(String stateFieldName01) {
        this.stateFieldName01 = stateFieldName01;
    }

    public String getStateFieldName02() {
        return this.stateFieldName02;
    }

    public DeviceType stateFieldName02(String stateFieldName02) {
        this.stateFieldName02 = stateFieldName02;
        return this;
    }

    public void setStateFieldName02(String stateFieldName02) {
        this.stateFieldName02 = stateFieldName02;
    }

    public String getStateFieldName03() {
        return this.stateFieldName03;
    }

    public DeviceType stateFieldName03(String stateFieldName03) {
        this.stateFieldName03 = stateFieldName03;
        return this;
    }

    public void setStateFieldName03(String stateFieldName03) {
        this.stateFieldName03 = stateFieldName03;
    }

    public String getStateFieldName04() {
        return this.stateFieldName04;
    }

    public DeviceType stateFieldName04(String stateFieldName04) {
        this.stateFieldName04 = stateFieldName04;
        return this;
    }

    public void setStateFieldName04(String stateFieldName04) {
        this.stateFieldName04 = stateFieldName04;
    }

    public String getReadAuthPattern() {
        return this.readAuthPattern;
    }

    public DeviceType readAuthPattern(String readAuthPattern) {
        this.readAuthPattern = readAuthPattern;
        return this;
    }

    public void setReadAuthPattern(String readAuthPattern) {
        this.readAuthPattern = readAuthPattern;
    }

    public String getWriteAuthPattern() {
        return this.writeAuthPattern;
    }

    public DeviceType writeAuthPattern(String writeAuthPattern) {
        this.writeAuthPattern = writeAuthPattern;
        return this;
    }

    public void setWriteAuthPattern(String writeAuthPattern) {
        this.writeAuthPattern = writeAuthPattern;
    }

    public Processor getEnrollProcessor() {
        return this.enrollProcessor;
    }

    public DeviceType enrollProcessor(Processor processor) {
        this.setEnrollProcessor(processor);
        return this;
    }

    public void setEnrollProcessor(Processor processor) {
        this.enrollProcessor = processor;
    }

    public Set<MessageType> getMessageTypes() {
        return this.messageTypes;
    }

    public DeviceType messageTypes(Set<MessageType> messageTypes) {
        this.setMessageTypes(messageTypes);
        return this;
    }

    public DeviceType addMessageTypes(MessageType messageType) {
        this.messageTypes.add(messageType);
        messageType.getDeviceTypes().add(this);
        return this;
    }

    public DeviceType removeMessageTypes(MessageType messageType) {
        this.messageTypes.remove(messageType);
        messageType.getDeviceTypes().remove(this);
        return this;
    }

    public void setMessageTypes(Set<MessageType> messageTypes) {
        this.messageTypes = messageTypes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceType)) {
            return false;
        }
        return id != null && id.equals(((DeviceType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", stateFieldName01='" + getStateFieldName01() + "'" +
            ", stateFieldName02='" + getStateFieldName02() + "'" +
            ", stateFieldName03='" + getStateFieldName03() + "'" +
            ", stateFieldName04='" + getStateFieldName04() + "'" +
            ", readAuthPattern='" + getReadAuthPattern() + "'" +
            ", writeAuthPattern='" + getWriteAuthPattern() + "'" +
            "}";
    }
}
