import { Component, Vue, Inject } from 'vue-property-decorator';

import { IOrgUnit } from '@/shared/model/org-unit.model';
import OrgUnitService from './org-unit.service';

@Component
export default class OrgUnitDetails extends Vue {
  @Inject('orgUnitService') private orgUnitService: () => OrgUnitService;
  public orgUnit: IOrgUnit = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.orgUnitId) {
        vm.retrieveOrgUnit(to.params.orgUnitId);
      }
    });
  }

  public retrieveOrgUnit(orgUnitId) {
    this.orgUnitService()
      .find(orgUnitId)
      .then(res => {
        this.orgUnit = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
