import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IKeyPair } from '@/shared/model/key-pair.model';
import KeyPairService from './key-pair.service';

@Component
export default class KeyPairDetails extends mixins(JhiDataUtils) {
  @Inject('keyPairService') private keyPairService: () => KeyPairService;
  public keyPair: IKeyPair = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.keyPairId) {
        vm.retrieveKeyPair(to.params.keyPairId);
      }
    });
  }

  public retrieveKeyPair(keyPairId) {
    this.keyPairService()
      .find(keyPairId)
      .then(res => {
        this.keyPair = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
