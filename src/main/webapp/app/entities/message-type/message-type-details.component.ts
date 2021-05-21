import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMessageType } from '@/shared/model/message-type.model';
import MessageTypeService from './message-type.service';

@Component
export default class MessageTypeDetails extends Vue {
  @Inject('messageTypeService') private messageTypeService: () => MessageTypeService;
  public messageType: IMessageType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.messageTypeId) {
        vm.retrieveMessageType(to.params.messageTypeId);
      }
    });
  }

  public retrieveMessageType(messageTypeId) {
    this.messageTypeService()
      .find(messageTypeId)
      .then(res => {
        this.messageType = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
