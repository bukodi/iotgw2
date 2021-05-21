/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import OrgUnitUpdateComponent from '@/entities/org-unit/org-unit-update.vue';
import OrgUnitClass from '@/entities/org-unit/org-unit-update.component';
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
  describe('OrgUnit Management Update Component', () => {
    let wrapper: Wrapper<OrgUnitClass>;
    let comp: OrgUnitClass;
    let orgUnitServiceStub: SinonStubbedInstance<OrgUnitService>;

    beforeEach(() => {
      orgUnitServiceStub = sinon.createStubInstance<OrgUnitService>(OrgUnitService);

      wrapper = shallowMount<OrgUnitClass>(OrgUnitUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          orgUnitService: () => orgUnitServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.orgUnit = entity;
        orgUnitServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(orgUnitServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.orgUnit = entity;
        orgUnitServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(orgUnitServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOrgUnit = { id: 123 };
        orgUnitServiceStub.find.resolves(foundOrgUnit);
        orgUnitServiceStub.retrieve.resolves([foundOrgUnit]);

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
