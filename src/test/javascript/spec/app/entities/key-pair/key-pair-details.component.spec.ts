/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import KeyPairDetailComponent from '@/entities/key-pair/key-pair-details.vue';
import KeyPairClass from '@/entities/key-pair/key-pair-details.component';
import KeyPairService from '@/entities/key-pair/key-pair.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('KeyPair Management Detail Component', () => {
    let wrapper: Wrapper<KeyPairClass>;
    let comp: KeyPairClass;
    let keyPairServiceStub: SinonStubbedInstance<KeyPairService>;

    beforeEach(() => {
      keyPairServiceStub = sinon.createStubInstance<KeyPairService>(KeyPairService);

      wrapper = shallowMount<KeyPairClass>(KeyPairDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { keyPairService: () => keyPairServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundKeyPair = { id: 123 };
        keyPairServiceStub.find.resolves(foundKeyPair);

        // WHEN
        comp.retrieveKeyPair(123);
        await comp.$nextTick();

        // THEN
        expect(comp.keyPair).toBe(foundKeyPair);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundKeyPair = { id: 123 };
        keyPairServiceStub.find.resolves(foundKeyPair);

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
