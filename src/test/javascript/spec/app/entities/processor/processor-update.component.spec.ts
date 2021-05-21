/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ProcessorUpdateComponent from '@/entities/processor/processor-update.vue';
import ProcessorClass from '@/entities/processor/processor-update.component';
import ProcessorService from '@/entities/processor/processor.service';

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
  describe('Processor Management Update Component', () => {
    let wrapper: Wrapper<ProcessorClass>;
    let comp: ProcessorClass;
    let processorServiceStub: SinonStubbedInstance<ProcessorService>;

    beforeEach(() => {
      processorServiceStub = sinon.createStubInstance<ProcessorService>(ProcessorService);

      wrapper = shallowMount<ProcessorClass>(ProcessorUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          processorService: () => processorServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.processor = entity;
        processorServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(processorServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.processor = entity;
        processorServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(processorServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundProcessor = { id: 123 };
        processorServiceStub.find.resolves(foundProcessor);
        processorServiceStub.retrieve.resolves([foundProcessor]);

        // WHEN
        comp.beforeRouteEnter({ params: { processorId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.processor).toBe(foundProcessor);
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
