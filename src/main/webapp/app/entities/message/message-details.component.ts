import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IMessage } from '@/shared/model/message.model';
import MessageService from './message.service';

@Component
export default class MessageDetails extends mixins(JhiDataUtils) {
  @Inject('messageService') private messageService: () => MessageService;
  public message: IMessage = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.messageId) {
        vm.retrieveMessage(to.params.messageId);
      }
    });
  }

  public retrieveMessage(messageId) {
    this.messageService()
      .find(messageId)
      .then(res => {
        this.message = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
