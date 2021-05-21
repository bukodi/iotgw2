import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import MessageTypeService from '@/entities/message-type/message-type.service';
import { IMessageType } from '@/shared/model/message-type.model';

import DeviceService from '@/entities/device/device.service';
import { IDevice } from '@/shared/model/device.model';

import { IMessage, Message } from '@/shared/model/message.model';
import MessageService from './message.service';

const validations: any = {
  message: {
    serverTime: {
      required,
    },
    deviceTime: {},
    rawMessageSHA256: {
      required,
    },
    rawMessage: {},
    decryptedPayload: {},
    devToSrv: {
      required,
    },
    indexFieldValue01: {},
    indexFieldValue02: {},
    indexFieldValue03: {},
    indexFieldValue04: {},
    processingError: {},
    type: {
      required,
    },
    device: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class MessageUpdate extends mixins(JhiDataUtils) {
  @Inject('messageService') private messageService: () => MessageService;
  public message: IMessage = new Message();

  @Inject('messageTypeService') private messageTypeService: () => MessageTypeService;

  public messageTypes: IMessageType[] = [];

  @Inject('deviceService') private deviceService: () => DeviceService;

  public devices: IDevice[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.messageId) {
        vm.retrieveMessage(to.params.messageId);
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
    if (this.message.id) {
      this.messageService()
        .update(this.message)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('iotgw2App.message.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.messageService()
        .create(this.message)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('iotgw2App.message.created', { param: param.id });
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

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.message[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.message[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.message[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.message[field] = null;
    }
  }

  public retrieveMessage(messageId): void {
    this.messageService()
      .find(messageId)
      .then(res => {
        res.serverTime = new Date(res.serverTime);
        res.deviceTime = new Date(res.deviceTime);
        this.message = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.messageTypeService()
      .retrieve()
      .then(res => {
        this.messageTypes = res.data;
      });
    this.deviceService()
      .retrieve()
      .then(res => {
        this.devices = res.data;
      });
  }
}
