/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import KeyPairComponent from '@/entities/key-pair/key-pair.vue';
import KeyPairClass from '@/entities/key-pair/key-pair.component';
import KeyPairService from '@/entities/key-pair/key-pair.service';

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
  describe('KeyPair Management Component', () => {
    let wrapper: Wrapper<KeyPairClass>;
    let comp: KeyPairClass;
    let keyPairServiceStub: SinonStubbedInstance<KeyPairService>;

    beforeEach(() => {
      keyPairServiceStub = sinon.createStubInstance<KeyPairService>(KeyPairService);
      keyPairServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<KeyPairClass>(KeyPairComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          keyPairService: () => keyPairServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      keyPairServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllKeyPairs();
      await comp.$nextTick();

      // THEN
      expect(keyPairServiceStub.retrieve.called).toBeTruthy();
      expect(comp.keyPairs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      keyPairServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(keyPairServiceStub.retrieve.called).toBeTruthy();
      expect(comp.keyPairs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      keyPairServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(keyPairServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      keyPairServiceStub.retrieve.reset();
      keyPairServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(keyPairServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.keyPairs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      keyPairServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeKeyPair();
      await comp.$nextTick();

      // THEN
      expect(keyPairServiceStub.delete.called).toBeTruthy();
      expect(keyPairServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
