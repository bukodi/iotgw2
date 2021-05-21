import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import { IKeyPair, KeyPair } from '@/shared/model/key-pair.model';
import KeyPairService from './key-pair.service';

const validations: any = {
  keyPair: {
    keyId: {
      required,
    },
    algorithm: {
      required,
    },
    publicKey: {
      required,
    },
    privateKeyDescriptor: {},
    attestationData: {},
    associatedCert: {},
    certificateRequest: {},
    certSubjectDN: {},
    certIssuerDN: {},
    certSerial: {},
    certNotBefore: {},
    certNotAfter: {},
    certRevoked: {},
  },
};

@Component({
  validations,
})
export default class KeyPairUpdate extends mixins(JhiDataUtils) {
  @Inject('keyPairService') private keyPairService: () => KeyPairService;
  public keyPair: IKeyPair = new KeyPair();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.keyPairId) {
        vm.retrieveKeyPair(to.params.keyPairId);
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
    if (this.keyPair.id) {
      this.keyPairService()
        .update(this.keyPair)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('iotgw2App.keyPair.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.keyPairService()
        .create(this.keyPair)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('iotgw2App.keyPair.created', { param: param.id });
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
      this.keyPair[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.keyPair[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.keyPair[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.keyPair[field] = null;
    }
  }

  public retrieveKeyPair(keyPairId): void {
    this.keyPairService()
      .find(keyPairId)
      .then(res => {
        res.certNotBefore = new Date(res.certNotBefore);
        res.certNotAfter = new Date(res.certNotAfter);
        res.certRevoked = new Date(res.certRevoked);
        this.keyPair = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
