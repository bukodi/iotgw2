/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MessageTypeDetailComponent from '@/entities/message-type/message-type-details.vue';
import MessageTypeClass from '@/entities/message-type/message-type-details.component';
import MessageTypeService from '@/entities/message-type/message-type.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('MessageType Management Detail Component', () => {
    let wrapper: Wrapper<MessageTypeClass>;
    let comp: MessageTypeClass;
    let messageTypeServiceStub: SinonStubbedInstance<MessageTypeService>;

    beforeEach(() => {
      messageTypeServiceStub = sinon.createStubInstance<MessageTypeService>(MessageTypeService);

      wrapper = shallowMount<MessageTypeClass>(MessageTypeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { messageTypeService: () => messageTypeServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMessageType = { id: 123 };
        messageTypeServiceStub.find.resolves(foundMessageType);

        // WHEN
        comp.retrieveMessageType(123);
        await comp.$nextTick();

        // THEN
        expect(comp.messageType).toBe(foundMessageType);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMessageType = { id: 123 };
        messageTypeServiceStub.find.resolves(foundMessageType);

        // WHEN
        comp.beforeRouteEnter({ params: { messageTypeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.messageType).toBe(foundMessageType);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
