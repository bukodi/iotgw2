<template>
  <div>
    <h2 id="page-heading" data-cy="OrgUnitHeading">
      <span v-text="$t('iotgw2App.orgUnit.home.title')" id="org-unit-heading">Org Units</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('iotgw2App.orgUnit.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'OrgUnitCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-org-unit"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('iotgw2App.orgUnit.home.createLabel')"> Create a new Org Unit </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && orgUnits && orgUnits.length === 0">
      <span v-text="$t('iotgw2App.orgUnit.home.notFound')">No orgUnits found</span>
    </div>
    <div class="table-responsive" v-if="orgUnits && orgUnits.length > 0">
      <table class="table table-striped" aria-describedby="orgUnits">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('iotgw2App.orgUnit.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="$t('iotgw2App.orgUnit.description')">Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('readAuthPattern')">
              <span v-text="$t('iotgw2App.orgUnit.readAuthPattern')">Read Auth Pattern</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'readAuthPattern'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('writeAuthPattern')">
              <span v-text="$t('iotgw2App.orgUnit.writeAuthPattern')">Write Auth Pattern</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'writeAuthPattern'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('parent.name')">
              <span v-text="$t('iotgw2App.orgUnit.parent')">Parent</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'parent.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="orgUnit in orgUnits" :key="orgUnit.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'OrgUnitView', params: { orgUnitId: orgUnit.id } }">{{ orgUnit.id }}</router-link>
            </td>
            <td>{{ orgUnit.name }}</td>
            <td>{{ orgUnit.description }}</td>
            <td>{{ orgUnit.readAuthPattern }}</td>
            <td>{{ orgUnit.writeAuthPattern }}</td>
            <td>
              <div v-if="orgUnit.parent">
                <router-link :to="{ name: 'OrgUnitView', params: { orgUnitId: orgUnit.parent.id } }">{{ orgUnit.parent.name }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'OrgUnitView', params: { orgUnitId: orgUnit.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'OrgUnitEdit', params: { orgUnitId: orgUnit.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(orgUnit)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="iotgw2App.orgUnit.delete.question" data-cy="orgUnitDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-orgUnit-heading" v-text="$t('iotgw2App.orgUnit.delete.question', { id: removeId })">
          Are you sure you want to delete this Org Unit?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-orgUnit"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeOrgUnit()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="orgUnits && orgUnits.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./org-unit.component.ts"></script>
