import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDeviceType } from '@/shared/model/device-type.model';
import DeviceTypeService from './device-type.service';

@Component
export default class DeviceTypeDetails extends Vue {
  @Inject('deviceTypeService') private deviceTypeService: () => DeviceTypeService;
  public deviceType: IDeviceType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.deviceTypeId) {
        vm.retrieveDeviceType(to.params.deviceTypeId);
      }
    });
  }

  public retrieveDeviceType(deviceTypeId) {
    this.deviceTypeService()
      .find(deviceTypeId)
      .then(res => {
        this.deviceType = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
