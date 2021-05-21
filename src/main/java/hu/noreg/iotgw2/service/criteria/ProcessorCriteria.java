package hu.noreg.iotgw2.service.criteria;

import hu.noreg.iotgw2.domain.enumeration.ImplemntationType;
import hu.noreg.iotgw2.domain.enumeration.ProcessorInterface;
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

/**
 * Criteria class for the {@link hu.noreg.iotgw2.domain.Processor} entity. This class is used
 * in {@link hu.noreg.iotgw2.web.rest.ProcessorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /processors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProcessorCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ProcessorInterface
     */
    public static class ProcessorInterfaceFilter extends Filter<ProcessorInterface> {

        public ProcessorInterfaceFilter() {}

        public ProcessorInterfaceFilter(ProcessorInterfaceFilter filter) {
            super(filter);
        }

        @Override
        public ProcessorInterfaceFilter copy() {
            return new ProcessorInterfaceFilter(this);
        }
    }

    /**
     * Class for filtering ImplemntationType
     */
    public static class ImplemntationTypeFilter extends Filter<ImplemntationType> {

        public ImplemntationTypeFilter() {}

        public ImplemntationTypeFilter(ImplemntationTypeFilter filter) {
            super(filter);
        }

        @Override
        public ImplemntationTypeFilter copy() {
            return new ImplemntationTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter example;

    private StringFilter description;

    private ProcessorInterfaceFilter processorIterface;

    private ImplemntationTypeFilter implType;

    private StringFilter param01;

    private StringFilter param02;

    private StringFilter signerName;

    public ProcessorCriteria() {}

    public ProcessorCriteria(ProcessorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.example = other.example == null ? null : other.example.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.processorIterface = other.processorIterface == null ? null : other.processorIterface.copy();
        this.implType = other.implType == null ? null : other.implType.copy();
        this.param01 = other.param01 == null ? null : other.param01.copy();
        this.param02 = other.param02 == null ? null : other.param02.copy();
        this.signerName = other.signerName == null ? null : other.signerName.copy();
    }

    @Override
    public ProcessorCriteria copy() {
        return new ProcessorCriteria(this);
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

    public BooleanFilter getExample() {
        return example;
    }

    public BooleanFilter example() {
        if (example == null) {
            example = new BooleanFilter();
        }
        return example;
    }

    public void setExample(BooleanFilter example) {
        this.example = example;
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

    public ProcessorInterfaceFilter getProcessorIterface() {
        return processorIterface;
    }

    public ProcessorInterfaceFilter processorIterface() {
        if (processorIterface == null) {
            processorIterface = new ProcessorInterfaceFilter();
        }
        return processorIterface;
    }

    public void setProcessorIterface(ProcessorInterfaceFilter processorIterface) {
        this.processorIterface = processorIterface;
    }

    public ImplemntationTypeFilter getImplType() {
        return implType;
    }

    public ImplemntationTypeFilter implType() {
        if (implType == null) {
            implType = new ImplemntationTypeFilter();
        }
        return implType;
    }

    public void setImplType(ImplemntationTypeFilter implType) {
        this.implType = implType;
    }

    public StringFilter getParam01() {
        return param01;
    }

    public StringFilter param01() {
        if (param01 == null) {
            param01 = new StringFilter();
        }
        return param01;
    }

    public void setParam01(StringFilter param01) {
        this.param01 = param01;
    }

    public StringFilter getParam02() {
        return param02;
    }

    public StringFilter param02() {
        if (param02 == null) {
            param02 = new StringFilter();
        }
        return param02;
    }

    public void setParam02(StringFilter param02) {
        this.param02 = param02;
    }

    public StringFilter getSignerName() {
        return signerName;
    }

    public StringFilter signerName() {
        if (signerName == null) {
            signerName = new StringFilter();
        }
        return signerName;
    }

    public void setSignerName(StringFilter signerName) {
        this.signerName = signerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProcessorCriteria that = (ProcessorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(example, that.example) &&
            Objects.equals(description, that.description) &&
            Objects.equals(processorIterface, that.processorIterface) &&
            Objects.equals(implType, that.implType) &&
            Objects.equals(param01, that.param01) &&
            Objects.equals(param02, that.param02) &&
            Objects.equals(signerName, that.signerName)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, example, description, processorIterface, implType, param01, param02, signerName);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessorCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (example != null ? "example=" + example + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (processorIterface != null ? "processorIterface=" + processorIterface + ", " : "") +
            (implType != null ? "implType=" + implType + ", " : "") +
            (param01 != null ? "param01=" + param01 + ", " : "") +
            (param02 != null ? "param02=" + param02 + ", " : "") +
            (signerName != null ? "signerName=" + signerName + ", " : "") +
            "}";
    }
}
