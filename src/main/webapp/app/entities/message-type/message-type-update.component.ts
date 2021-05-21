import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required } from 'vuelidate/lib/validators';

import ProcessorService from '@/entities/processor/processor.service';
import { IProcessor } from '@/shared/model/processor.model';

import DeviceTypeService from '@/entities/device-type/device-type.service';
import { IDeviceType } from '@/shared/model/device-type.model';

import { IMessageType, MessageType } from '@/shared/model/message-type.model';
import MessageTypeService from './message-type.service';

const validations: any = {
  messageType: {
    typeCode: {
      required,
      numeric,
    },
    devToSrv: {
      required,
    },
    name: {
      required,
    },
    description: {},
    timeout: {},
    retentionTime: {},
    indexFieldName01: {},
    indexFieldName02: {},
    indexFieldName03: {},
    indexFieldName04: {},
    readAuthPattern: {},
    writeAuthPattern: {},
  },
};

@Component({
  validations,
})
export default class MessageTypeUpdate extends Vue {
  @Inject('messageTypeService') private messageTypeService: () => MessageTypeService;
  public messageType: IMessageType = new MessageType();

  @Inject('processorService') private processorService: () => ProcessorService;

  public processors: IProcessor[] = [];

  @Inject('deviceTypeService') private deviceTypeService: () => DeviceTypeService;

  public deviceTypes: IDeviceType[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.messageTypeId) {
        vm.retrieveMessageType(to.params.messageTypeId);
      }
      vm.initRelationships();
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
    if (this.messageType.id) {
      this.messageTypeService()
        .update(this.messageType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('iotgw2App.messageType.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.messageTypeService()
        .create(this.messageType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('iotgw2App.messageType.created', { param: param.id });
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

  public retrieveMessageType(messageTypeId): void {
    this.messageTypeService()
      .find(messageTypeId)
      .then(res => {
        this.messageType = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.processorService()
      .retrieve()
      .then(res => {
        this.processors = res.data;
      });
    this.deviceTypeService()
      .retrieve()
      .then(res => {
        this.deviceTypes = res.data;
      });
  }
}
