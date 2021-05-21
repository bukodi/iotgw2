/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DeviceTypeDetailComponent from '@/entities/device-type/device-type-details.vue';
import DeviceTypeClass from '@/entities/device-type/device-type-details.component';
import DeviceTypeService from '@/entities/device-type/device-type.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('DeviceType Management Detail Component', () => {
    let wrapper: Wrapper<DeviceTypeClass>;
    let comp: DeviceTypeClass;
    let deviceTypeServiceStub: SinonStubbedInstance<DeviceTypeService>;

    beforeEach(() => {
      deviceTypeServiceStub = sinon.createStubInstance<DeviceTypeService>(DeviceTypeService);

      wrapper = shallowMount<DeviceTypeClass>(DeviceTypeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { deviceTypeService: () => deviceTypeServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDeviceType = { id: 123 };
        deviceTypeServiceStub.find.resolves(foundDeviceType);

        // WHEN
        comp.retrieveDeviceType(123);
        await comp.$nextTick();

        // THEN
        expect(comp.deviceType).toBe(foundDeviceType);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDeviceType = { id: 123 };
        deviceTypeServiceStub.find.resolves(foundDeviceType);

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
