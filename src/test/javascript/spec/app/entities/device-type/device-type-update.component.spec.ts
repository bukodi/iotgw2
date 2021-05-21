/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import DeviceTypeUpdateComponent from '@/entities/device-type/device-type-update.vue';
import DeviceTypeClass from '@/entities/device-type/device-type-update.component';
import DeviceTypeService from '@/entities/device-type/device-type.service';

import ProcessorService from '@/entities/processor/processor.service';

import MessageTypeService from '@/entities/message-type/message-type.service';

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
  describe('DeviceType Management Update Component', () => {
    let wrapper: Wrapper<DeviceTypeClass>;
    let comp: DeviceTypeClass;
    let deviceTypeServiceStub: SinonStubbedInstance<DeviceTypeService>;

    beforeEach(() => {
      deviceTypeServiceStub = sinon.createStubInstance<DeviceTypeService>(DeviceTypeService);

      wrapper = shallowMount<DeviceTypeClass>(DeviceTypeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          deviceTypeService: () => deviceTypeServiceStub,

          processorService: () => new ProcessorService(),

          messageTypeService: () => new MessageTypeService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.deviceType = entity;
        deviceTypeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(deviceTypeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.deviceType = entity;
        deviceTypeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(deviceTypeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDeviceType = { id: 123 };
        deviceTypeServiceStub.find.resolves(foundDeviceType);
        deviceTypeServiceStub.retrieve.resolves([foundDeviceType]);

        // WHEN
        comp.beforeRouteEnter({ params: { deviceTypeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.deviceType).toBe(foundDeviceType);
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
