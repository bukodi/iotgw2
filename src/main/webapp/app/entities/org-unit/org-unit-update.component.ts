import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import { IOrgUnit, OrgUnit } from '@/shared/model/org-unit.model';
import OrgUnitService from './org-unit.service';

const validations: any = {
  orgUnit: {
    name: {
      required,
    },
    description: {},
    readAuthPattern: {},
    writeAuthPattern: {},
  },
};

@Component({
  validations,
})
export default class OrgUnitUpdate extends Vue {
  @Inject('orgUnitService') private orgUnitService: () => OrgUnitService;
  public orgUnit: IOrgUnit = new OrgUnit();

  public orgUnits: IOrgUnit[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.orgUnitId) {
        vm.retrieveOrgUnit(to.params.orgUnitId);
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
    if (this.orgUnit.id) {
      this.orgUnitService()
        .update(this.orgUnit)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('iotgw2App.orgUnit.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.orgUnitService()
        .create(this.orgUnit)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('iotgw2App.orgUnit.created', { param: param.id });
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

  public retrieveOrgUnit(orgUnitId): void {
    this.orgUnitService()
      .find(orgUnitId)
      .then(res => {
        this.orgUnit = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.orgUnitService()
      .retrieve()
      .then(res => {
        this.orgUnits = res.data;
      });
  }
}
