import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import KeyPairService from '@/entities/key-pair/key-pair.service';
import { IKeyPair } from '@/shared/model/key-pair.model';

import DeviceTypeService from '@/entities/device-type/device-type.service';
import { IDeviceType } from '@/shared/model/device-type.model';

import OrgUnitService from '@/entities/org-unit/org-unit.service';
import { IOrgUnit } from '@/shared/model/org-unit.model';

import { IDevice, Device } from '@/shared/model/device.model';
import DeviceService from './device.service';

const validations: any = {
  device: {
    visualId: {
      required,
    },
    description: {},
    enrollmentCode: {},
    enrollmentTime: {},
    stateFieldValue01: {},
    stateFieldValue02: {},
    stateFieldValue03: {},
    stateFieldValue04: {},
    lastError: {},
    readAuthPattern: {},
    writeAuthPattern: {},
    type: {
      required,
    },
    orgUnit: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class DeviceUpdate extends mixins(JhiDataUtils) {
  @Inject('deviceService') private deviceService: () => DeviceService;
  public device: IDevice = new Device();

  @Inject('keyPairService') private keyPairService: () => KeyPairService;

  public keyPairs: IKeyPair[] = [];

  @Inject('deviceTypeService') private deviceTypeService: () => DeviceTypeService;

  public deviceTypes: IDeviceType[] = [];

  @Inject('orgUnitService') private orgUnitService: () => OrgUnitService;

  public orgUnits: IOrgUnit[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.deviceId) {
        vm.retrieveDevice(to.params.deviceId);
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
    if (this.device.id) {
      this.deviceService()
        .update(this.device)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('iotgw2App.device.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.deviceService()
        .create(this.device)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('iotgw2App.device.created', { param: param.id });
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
      this.device[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.device[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.device[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.device[field] = null;
    }
  }

  public retrieveDevice(deviceId): void {
    this.deviceService()
      .find(deviceId)
      .then(res => {
        res.enrollmentTime = new Date(res.enrollmentTime);
        this.device = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.keyPairService()
      .retrieve()
      .then(res => {
        this.keyPairs = res.data;
      });
    this.deviceTypeService()
      .retrieve()
      .then(res => {
        this.deviceTypes = res.data;
      });
    this.orgUnitService()
      .retrieve()
      .then(res => {
        this.orgUnits = res.data;
      });
  }
}
