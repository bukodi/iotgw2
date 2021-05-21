import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { required } from 'vuelidate/lib/validators';

import { IProcessor, Processor } from '@/shared/model/processor.model';
import ProcessorService from './processor.service';

const validations: any = {
  processor: {
    name: {
      required,
    },
    example: {},
    description: {},
    processorIterface: {
      required,
    },
    implType: {
      required,
    },
    param01: {},
    param02: {},
    source: {},
    signerName: {},
    signature: {},
  },
};

@Component({
  validations,
})
export default class ProcessorUpdate extends mixins(JhiDataUtils) {
  @Inject('processorService') private processorService: () => ProcessorService;
  public processor: IProcessor = new Processor();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.processorId) {
        vm.retrieveProcessor(to.params.processorId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.processor.id) {
      this.processorService()
        .update(this.processor)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('iotgw2App.processor.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.processorService()
        .create(this.processor)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('iotgw2App.processor.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveProcessor(processorId): void {
    this.processorService()
      .find(processorId)
      .then(res => {
        this.processor = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
