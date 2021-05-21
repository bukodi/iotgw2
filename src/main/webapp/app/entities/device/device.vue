<template>
  <div>
    <h2 id="page-heading" data-cy="DeviceHeading">
      <span v-text="$t('iotgw2App.device.home.title')" id="device-heading">Devices</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('iotgw2App.device.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'DeviceCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-device"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('iotgw2App.device.home.createLabel')"> Create a new Device </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && devices && devices.length === 0">
      <span v-text="$t('iotgw2App.device.home.notFound')">No devices found</span>
    </div>
    <div class="table-responsive" v-if="devices && devices.length > 0">
      <table class="table table-striped" aria-describedby="devices">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('visualId')">
              <span v-text="$t('iotgw2App.device.visualId')">Visual Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'visualId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="$t('iotgw2App.device.description')">Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('enrollmentCode')">
              <span v-text="$t('iotgw2App.device.enrollmentCode')">Enrollment Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'enrollmentCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('enrollmentTime')">
              <span v-text="$t('iotgw2App.device.enrollmentTime')">Enrollment Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'enrollmentTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('stateFieldValue01')">
              <span v-text="$t('iotgw2App.device.stateFieldValue01')">State Field Value 01</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stateFieldValue01'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('stateFieldValue02')">
              <span v-text="$t('iotgw2App.device.stateFieldValue02')">State Field Value 02</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stateFieldValue02'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('stateFieldValue03')">
              <span v-text="$t('iotgw2App.device.stateFieldValue03')">State Field Value 03</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stateFieldValue03'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('stateFieldValue04')">
              <span v-text="$t('iotgw2App.device.stateFieldValue04')">State Field Value 04</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stateFieldValue04'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastError')">
              <span v-text="$t('iotgw2App.device.lastError')">Last Error</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastError'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('readAuthPattern')">
              <span v-text="$t('iotgw2App.device.readAuthPattern')">Read Auth Pattern</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'readAuthPattern'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('writeAuthPattern')">
              <span v-text="$t('iotgw2App.device.writeAuthPattern')">Write Auth Pattern</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'writeAuthPattern'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('deviceSignKey.keyId')">
              <span v-text="$t('iotgw2App.device.deviceSignKey')">Device Sign Key</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'deviceSignKey.keyId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('deviceEncKey.keyId')">
              <span v-text="$t('iotgw2App.device.deviceEncKey')">Device Enc Key</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'deviceEncKey.keyId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('serverSignKey.keyId')">
              <span v-text="$t('iotgw2App.device.serverSignKey')">Server Sign Key</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'serverSignKey.keyId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('serverEncKey.keyId')">
              <span v-text="$t('iotgw2App.device.serverEncKey')">Server Enc Key</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'serverEncKey.keyId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nextServerSignKey.keyId')">
              <span v-text="$t('iotgw2App.device.nextServerSignKey')">Next Server Sign Key</span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'nextServerSignKey.keyId'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nextServerEncKey.keyId')">
              <span v-text="$t('iotgw2App.device.nextServerEncKey')">Next Server Enc Key</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nextServerEncKey.keyId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('type.name')">
              <span v-text="$t('iotgw2App.device.type')">Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('orgUnit.name')">
              <span v-text="$t('iotgw2App.device.orgUnit')">Org Unit</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'orgUnit.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="device in devices" :key="device.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DeviceView', params: { deviceId: device.id } }">{{ device.id }}</router-link>
            </td>
            <td>{{ device.visualId }}</td>
            <td>{{ device.description }}</td>
            <td>{{ device.enrollmentCode }}</td>
            <td>{{ device.enrollmentTime ? $d(Date.parse(device.enrollmentTime), 'short') : '' }}</td>
            <td>{{ device.stateFieldValue01 }}</td>
            <td>{{ device.stateFieldValue02 }}</td>
            <td>{{ device.stateFieldValue03 }}</td>
            <td>{{ device.stateFieldValue04 }}</td>
            <td>{{ device.lastError }}</td>
            <td>{{ device.readAuthPattern }}</td>
            <td>{{ device.writeAuthPattern }}</td>
            <td>
              <div v-if="device.deviceSignKey">
                <router-link :to="{ name: 'KeyPairView', params: { keyPairId: device.deviceSignKey.id } }">{{
                  device.deviceSignKey.keyId
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="device.deviceEncKey">
                <router-link :to="{ name: 'KeyPairView', params: { keyPairId: device.deviceEncKey.id } }">{{
                  device.deviceEncKey.keyId
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="device.serverSignKey">
                <router-link :to="{ name: 'KeyPairView', params: { keyPairId: device.serverSignKey.id } }">{{
                  device.serverSignKey.keyId
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="device.serverEncKey">
                <router-link :to="{ name: 'KeyPairView', params: { keyPairId: device.serverEncKey.id } }">{{
                  device.serverEncKey.keyId
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="device.nextServerSignKey">
                <router-link :to="{ name: 'KeyPairView', params: { keyPairId: device.nextServerSignKey.id } }">{{
                  device.nextServerSignKey.keyId
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="device.nextServerEncKey">
                <router-link :to="{ name: 'KeyPairView', params: { keyPairId: device.nextServerEncKey.id } }">{{
                  device.nextServerEncKey.keyId
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="device.type">
                <router-link :to="{ name: 'DeviceTypeView', params: { deviceTypeId: device.type.id } }">{{ device.type.name }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="device.orgUnit">
                <router-link :to="{ name: 'OrgUnitView', params: { orgUnitId: device.orgUnit.id } }">{{ device.orgUnit.name }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DeviceView', params: { deviceId: device.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DeviceEdit', params: { deviceId: device.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(device)"
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
        ><span id="iotgw2App.device.delete.question" data-cy="deviceDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-device-heading" v-text="$t('iotgw2App.device.delete.question', { id: removeId })">
          Are you sure you want to delete this Device?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-device"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeDevice()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="devices && devices.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./device.component.ts"></script>
