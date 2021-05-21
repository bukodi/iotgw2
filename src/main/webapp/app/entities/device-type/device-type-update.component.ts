import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import ProcessorService from '@/entities/processor/processor.service';
import { IProcessor } from '@/shared/model/processor.model';

import MessageTypeService from '@/entities/message-type/message-type.service';
import { IMessageType } from '@/shared/model/message-type.model';

import { IDeviceType, DeviceType } from '@/shared/model/device-type.model';
import DeviceTypeService from './device-type.service';

const validations: any = {
  deviceType: {
    name: {
      required,
    },
    description: {},
    stateFieldName01: {},
    stateFieldName02: {},
    stateFieldName03: {},
    stateFieldName04: {},
    readAuthPattern: {},
    writeAuthPattern: {},
  },
};

@Component({
  validations,
})
export default class DeviceTypeUpdate extends Vue {
  @Inject('deviceTypeService') private deviceTypeService: () => DeviceTypeService;
  public deviceType: IDeviceType = new DeviceType();

  @Inject('processorService') private processorService: () => ProcessorService;

  public processors: IProcessor[] = [];

  @Inject('messageTypeService') private messageTypeService: () => MessageTypeService;

  public messageTypes: IMessageType[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.deviceTypeId) {
        vm.retrieveDeviceType(to.params.deviceTypeId);
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
    this.deviceType.messageTypes = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.deviceType.id) {
      this.deviceTypeService()
        .update(this.deviceType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('iotgw2App.deviceType.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.deviceTypeService()
        .create(this.deviceType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('iotgw2App.deviceType.created', { param: param.id });
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

  public retrieveDeviceType(deviceTypeId): void {
    this.deviceTypeService()
      .find(deviceTypeId)
      .then(res => {
        this.deviceType = res;
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
    this.messageTypeService()
      .retrieve()
      .then(res => {
        this.messageTypes = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
