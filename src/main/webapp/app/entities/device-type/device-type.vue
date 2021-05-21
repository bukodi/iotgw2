<template>
  <div>
    <h2 id="page-heading" data-cy="DeviceTypeHeading">
      <span v-text="$t('iotgw2App.deviceType.home.title')" id="device-type-heading">Device Types</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('iotgw2App.deviceType.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'DeviceTypeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-device-type"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('iotgw2App.deviceType.home.createLabel')"> Create a new Device Type </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && deviceTypes && deviceTypes.length === 0">
      <span v-text="$t('iotgw2App.deviceType.home.notFound')">No deviceTypes found</span>
    </div>
    <div class="table-responsive" v-if="deviceTypes && deviceTypes.length > 0">
      <table class="table table-striped" aria-describedby="deviceTypes">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('iotgw2App.deviceType.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="$t('iotgw2App.deviceType.description')">Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('stateFieldName01')">
              <span v-text="$t('iotgw2App.deviceType.stateFieldName01')">State Field Name 01</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stateFieldName01'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('stateFieldName02')">
              <span v-text="$t('iotgw2App.deviceType.stateFieldName02')">State Field Name 02</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stateFieldName02'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('stateFieldName03')">
              <span v-text="$t('iotgw2App.deviceType.stateFieldName03')">State Field Name 03</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stateFieldName03'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('stateFieldName04')">
              <span v-text="$t('iotgw2App.deviceType.stateFieldName04')">State Field Name 04</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stateFieldName04'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('readAuthPattern')">
              <span v-text="$t('iotgw2App.deviceType.readAuthPattern')">Read Auth Pattern</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'readAuthPattern'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('writeAuthPattern')">
              <span v-text="$t('iotgw2App.deviceType.writeAuthPattern')">Write Auth Pattern</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'writeAuthPattern'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('enrollProcessor.name')">
              <span v-text="$t('iotgw2App.deviceType.enrollProcessor')">Enroll Processor</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'enrollProcessor.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="deviceType in deviceTypes" :key="deviceType.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DeviceTypeView', params: { deviceTypeId: deviceType.id } }">{{ deviceType.id }}</router-link>
            </td>
            <td>{{ deviceType.name }}</td>
            <td>{{ deviceType.description }}</td>
            <td>{{ deviceType.stateFieldName01 }}</td>
            <td>{{ deviceType.stateFieldName02 }}</td>
            <td>{{ deviceType.stateFieldName03 }}</td>
            <td>{{ deviceType.stateFieldName04 }}</td>
            <td>{{ deviceType.readAuthPattern }}</td>
            <td>{{ deviceType.writeAuthPattern }}</td>
            <td>
              <div v-if="deviceType.enrollProcessor">
                <router-link :to="{ name: 'ProcessorView', params: { processorId: deviceType.enrollProcessor.id } }">{{
                  deviceType.enrollProcessor.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DeviceTypeView', params: { deviceTypeId: deviceType.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DeviceTypeEdit', params: { deviceTypeId: deviceType.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(deviceType)"
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
        ><span id="iotgw2App.deviceType.delete.question" data-cy="deviceTypeDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-deviceType-heading" v-text="$t('iotgw2App.deviceType.delete.question', { id: removeId })">
          Are you sure you want to delete this Device Type?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-deviceType"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeDeviceType()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="deviceTypes && deviceTypes.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./device-type.component.ts"></script>
