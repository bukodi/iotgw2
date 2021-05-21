/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DeviceTypeComponent from '@/entities/device-type/device-type.vue';
import DeviceTypeClass from '@/entities/device-type/device-type.component';
import DeviceTypeService from '@/entities/device-type/device-type.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('DeviceType Management Component', () => {
    let wrapper: Wrapper<DeviceTypeClass>;
    let comp: DeviceTypeClass;
    let deviceTypeServiceStub: SinonStubbedInstance<DeviceTypeService>;

    beforeEach(() => {
      deviceTypeServiceStub = sinon.createStubInstance<DeviceTypeService>(DeviceTypeService);
      deviceTypeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DeviceTypeClass>(DeviceTypeComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          deviceTypeService: () => deviceTypeServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      deviceTypeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDeviceTypes();
      await comp.$nextTick();

      // THEN
      expect(deviceTypeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.deviceTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      deviceTypeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(deviceTypeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.deviceTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      deviceTypeServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(deviceTypeServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      deviceTypeServiceStub.retrieve.reset();
      deviceTypeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(deviceTypeServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.deviceTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      deviceTypeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDeviceType();
      await comp.$nextTick();

      // THEN
      expect(deviceTypeServiceStub.delete.called).toBeTruthy();
      expect(deviceTypeServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
