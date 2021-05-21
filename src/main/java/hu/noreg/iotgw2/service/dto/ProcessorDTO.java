package hu.noreg.iotgw2.service.dto;

import hu.noreg.iotgw2.domain.enumeration.ImplemntationType;
import hu.noreg.iotgw2.domain.enumeration.ProcessorInterface;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link hu.noreg.iotgw2.domain.Processor} entity.
 */
public class ProcessorDTO implements Serializable {

    private Long id;

    /**
     * Unique name of the processor
     */
    @NotNull
    @ApiModelProperty(value = "Unique name of the processor", required = true)
    private String name;

    private Boolean example;

    /**
     * Optional description
     */
    @ApiModelProperty(value = "Optional description")
    private String description;

    @NotNull
    private ProcessorInterface processorIterface;

    @NotNull
    private ImplemntationType implType;

    private String param01;

    private String param02;

    @Lob
    private String source;

    private String signerName;

    @Lob
    private byte[] signature;

    private String signatureContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getExample() {
        return example;
    }

    public void setExample(Boolean example) {
        this.example = example;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProcessorInterface getProcessorIterface() {
        return processorIterface;
    }

    public void setProcessorIterface(ProcessorInterface processorIterface) {
        this.processorIterface = processorIterface;
    }

    public ImplemntationType getImplType() {
        return implType;
    }

    public void setImplType(ImplemntationType implType) {
        this.implType = implType;
    }

    public String getParam01() {
        return param01;
    }

    public void setParam01(String param01) {
        this.param01 = param01;
    }

    public String getParam02() {
        return param02;
    }

    public void setParam02(String param02) {
        this.param02 = param02;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSignerName() {
        return signerName;
    }

    public void setSignerName(String signerName) {
        this.signerName = signerName;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSignatureContentType() {
        return signatureContentType;
    }

    public void setSignatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessorDTO)) {
            return false;
        }

        ProcessorDTO processorDTO = (ProcessorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, processorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessorDTO{" +
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
            "}";
    }
}
