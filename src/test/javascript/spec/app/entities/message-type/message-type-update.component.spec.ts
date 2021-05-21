/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import MessageTypeUpdateComponent from '@/entities/message-type/message-type-update.vue';
import MessageTypeClass from '@/entities/message-type/message-type-update.component';
import MessageTypeService from '@/entities/message-type/message-type.service';

import ProcessorService from '@/entities/processor/processor.service';

import DeviceTypeService from '@/entities/device-type/device-type.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('MessageType Management Update Component', () => {
    let wrapper: Wrapper<MessageTypeClass>;
    let comp: MessageTypeClass;
    let messageTypeServiceStub: SinonStubbedInstance<MessageTypeService>;

    beforeEach(() => {
      messageTypeServiceStub = sinon.createStubInstance<MessageTypeService>(MessageTypeService);

      wrapper = shallowMount<MessageTypeClass>(MessageTypeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          messageTypeService: () => messageTypeServiceStub,

          processorService: () => new ProcessorService(),

          deviceTypeService: () => new DeviceTypeService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.messageType = entity;
        messageTypeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(messageTypeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.messageType = entity;
        messageTypeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(messageTypeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMessageType = { id: 123 };
        messageTypeServiceStub.find.resolves(foundMessageType);
        messageTypeServiceStub.retrieve.resolves([foundMessageType]);

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
