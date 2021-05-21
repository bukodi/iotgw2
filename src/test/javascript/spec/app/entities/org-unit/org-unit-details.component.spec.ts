/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import OrgUnitDetailComponent from '@/entities/org-unit/org-unit-details.vue';
import OrgUnitClass from '@/entities/org-unit/org-unit-details.component';
import OrgUnitService from '@/entities/org-unit/org-unit.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('OrgUnit Management Detail Component', () => {
    let wrapper: Wrapper<OrgUnitClass>;
    let comp: OrgUnitClass;
    let orgUnitServiceStub: SinonStubbedInstance<OrgUnitService>;

    beforeEach(() => {
      orgUnitServiceStub = sinon.createStubInstance<OrgUnitService>(OrgUnitService);

      wrapper = shallowMount<OrgUnitClass>(OrgUnitDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { orgUnitService: () => orgUnitServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOrgUnit = { id: 123 };
        orgUnitServiceStub.find.resolves(foundOrgUnit);

        // WHEN
        comp.retrieveOrgUnit(123);
        await comp.$nextTick();

        // THEN
        expect(comp.orgUnit).toBe(foundOrgUnit);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOrgUnit = { id: 123 };
        orgUnitServiceStub.find.resolves(foundOrgUnit);

        // WHEN
        comp.beforeRouteEnter({ params: { orgUnitId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.orgUnit).toBe(foundOrgUnit);
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
