/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ProcessorDetailComponent from '@/entities/processor/processor-details.vue';
import ProcessorClass from '@/entities/processor/processor-details.component';
import ProcessorService from '@/entities/processor/processor.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Processor Management Detail Component', () => {
    let wrapper: Wrapper<ProcessorClass>;
    let comp: ProcessorClass;
    let processorServiceStub: SinonStubbedInstance<ProcessorService>;

    beforeEach(() => {
      processorServiceStub = sinon.createStubInstance<ProcessorService>(ProcessorService);

      wrapper = shallowMount<ProcessorClass>(ProcessorDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { processorService: () => processorServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProcessor = { id: 123 };
        processorServiceStub.find.resolves(foundProcessor);

        // WHEN
        comp.retrieveProcessor(123);
        await comp.$nextTick();

        // THEN
        expect(comp.processor).toBe(foundProcessor);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundProcessor = { id: 123 };
        processorServiceStub.find.resolves(foundProcessor);

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
