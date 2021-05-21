/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import DeviceUpdateComponent from '@/entities/device/device-update.vue';
import DeviceClass from '@/entities/device/device-update.component';
import DeviceService from '@/entities/device/device.service';

import KeyPairService from '@/entities/key-pair/key-pair.service';

import DeviceTypeService from '@/entities/device-type/device-type.service';

import OrgUnitService from '@/entities/org-unit/org-unit.service';

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
  describe('Device Management Update Component', () => {
    let wrapper: Wrapper<DeviceClass>;
    let comp: DeviceClass;
    let deviceServiceStub: SinonStubbedInstance<DeviceService>;

    beforeEach(() => {
      deviceServiceStub = sinon.createStubInstance<DeviceService>(DeviceService);

      wrapper = shallowMount<DeviceClass>(DeviceUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          deviceService: () => deviceServiceStub,

          keyPairService: () => new KeyPairService(),

          deviceTypeService: () => new DeviceTypeService(),

          orgUnitService: () => new OrgUnitService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.device = entity;
        deviceServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(deviceServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.device = entity;
        deviceServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(deviceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDevice = { id: 123 };
        deviceServiceStub.find.resolves(foundDevice);
        deviceServiceStub.retrieve.resolves([foundDevice]);

        // WHEN
        comp.beforeRouteEnter({ params: { deviceId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.device).toBe(foundDevice);
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
