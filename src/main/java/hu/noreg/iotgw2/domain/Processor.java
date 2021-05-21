package hu.noreg.iotgw2.domain;

import hu.noreg.iotgw2.domain.enumeration.ImplemntationType;
import hu.noreg.iotgw2.domain.enumeration.ProcessorInterface;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Processor.
 */
@Entity
@Table(name = "iot_processor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Processor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique name of the processor
     */
    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "example")
    private Boolean example;

    /**
     * Optional description
     */
    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "processor_iterface", nullable = false)
    private ProcessorInterface processorIterface;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "impl_type", nullable = false)
    private ImplemntationType implType;

    @Column(name = "param_01")
    private String param01;

    @Column(name = "param_02")
    private String param02;

    @Lob
    @Column(name = "source")
    private String source;

    @Column(name = "signer_name")
    private String signerName;

    @Lob
    @Column(name = "signature")
    private byte[] signature;

    @Column(name = "signature_content_type")
    private String signatureContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Processor id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Processor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getExample() {
        return this.example;
    }

    public Processor example(Boolean example) {
        this.example = example;
        return this;
    }

    public void setExample(Boolean example) {
        this.example = example;
    }

    public String getDescription() {
        return this.description;
    }

    public Processor description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProcessorInterface getProcessorIterface() {
        return this.processorIterface;
    }

    public Processor processorIterface(ProcessorInterface processorIterface) {
        this.processorIterface = processorIterface;
        return this;
    }

    public void setProcessorIterface(ProcessorInterface processorIterface) {
        this.processorIterface = processorIterface;
    }

    public ImplemntationType getImplType() {
        return this.implType;
    }

    public Processor implType(ImplemntationType implType) {
        this.implType = implType;
        return this;
    }

    public void setImplType(ImplemntationType implType) {
        this.implType = implType;
    }

    public String getParam01() {
        return this.param01;
    }

    public Processor param01(String param01) {
        this.param01 = param01;
        return this;
    }

    public void setParam01(String param01) {
        this.param01 = param01;
    }

    public String getParam02() {
        return this.param02;
    }

    public Processor param02(String param02) {
        this.param02 = param02;
        return this;
    }

    public void setParam02(String param02) {
        this.param02 = param02;
    }

    public String getSource() {
        return this.source;
    }

    public Processor source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSignerName() {
        return this.signerName;
    }

    public Processor signerName(String signerName) {
        this.signerName = signerName;
        return this;
    }

    public void setSignerName(String signerName) {
        this.signerName = signerName;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    public Processor signature(byte[] signature) {
        this.signature = signature;
        return this;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSignatureContentType() {
        return this.signatureContentType;
    }

    public Processor signatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
        return this;
    }

    public void setSignatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Processor)) {
            return false;
        }
        return id != null && id.equals(((Processor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Processor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", example='" + getExample() + "'" +
            ", description='" + getDescription() + "'" +
            ", processorIterface='" + getProcessorIterface() + "'" +
            ", implType='" + getImplType() + "'" +
            ", param01='" + getParam01() + "'" +
            ", param02='" + getParam02() + "'" +
            ", source='" + getSource() + "'" +
            ", signerName='" + getSignerName() + "'" +
            ", signature='" + getSignature() + "'" +
            ", signatureContentType='" + getSignatureContentType() + "'" +
            "}";
    }
}
