import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IDevice } from '@/shared/model/device.model';
import DeviceService from './device.service';

@Component
export default class DeviceDetails extends mixins(JhiDataUtils) {
  @Inject('deviceService') private deviceService: () => DeviceService;
  public device: IDevice = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.deviceId) {
        vm.retrieveDevice(to.params.deviceId);
      }
    });
  }

  public retrieveDevice(deviceId) {
    this.deviceService()
      .find(deviceId)
      .then(res => {
        this.device = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
