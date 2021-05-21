/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import KeyPairUpdateComponent from '@/entities/key-pair/key-pair-update.vue';
import KeyPairClass from '@/entities/key-pair/key-pair-update.component';
import KeyPairService from '@/entities/key-pair/key-pair.service';

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
  describe('KeyPair Management Update Component', () => {
    let wrapper: Wrapper<KeyPairClass>;
    let comp: KeyPairClass;
    let keyPairServiceStub: SinonStubbedInstance<KeyPairService>;

    beforeEach(() => {
      keyPairServiceStub = sinon.createStubInstance<KeyPairService>(KeyPairService);

      wrapper = shallowMount<KeyPairClass>(KeyPairUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          keyPairService: () => keyPairServiceStub,
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
        comp.keyPair = entity;
        keyPairServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(keyPairServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.keyPair = entity;
        keyPairServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(keyPairServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundKeyPair = { id: 123 };
        keyPairServiceStub.find.resolves(foundKeyPair);
        keyPairServiceStub.retrieve.resolves([foundKeyPair]);

        // WHEN
        comp.beforeRouteEnter({ params: { keyPairId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.keyPair).toBe(foundKeyPair);
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
