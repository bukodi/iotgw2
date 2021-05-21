import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IProcessor } from '@/shared/model/processor.model';
import ProcessorService from './processor.service';

@Component
export default class ProcessorDetails extends mixins(JhiDataUtils) {
  @Inject('processorService') private processorService: () => ProcessorService;
  public processor: IProcessor = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.processorId) {
        vm.retrieveProcessor(to.params.processorId);
      }
    });
  }

  public retrieveProcessor(processorId) {
    this.processorService()
      .find(processorId)
      .then(res => {
        this.processor = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
