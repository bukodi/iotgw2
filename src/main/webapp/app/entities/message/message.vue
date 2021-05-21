<template>
  <div>
    <h2 id="page-heading" data-cy="MessageHeading">
      <span v-text="$t('iotgw2App.message.home.title')" id="message-heading">Messages</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('iotgw2App.message.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'MessageCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-message"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('iotgw2App.message.home.createLabel')"> Create a new Message </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && messages && messages.length === 0">
      <span v-text="$t('iotgw2App.message.home.notFound')">No messages found</span>
    </div>
    <div class="table-responsive" v-if="messages && messages.length > 0">
      <table class="table table-striped" aria-describedby="messages">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('serverTime')">
              <span v-text="$t('iotgw2App.message.serverTime')">Server Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'serverTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('deviceTime')">
              <span v-text="$t('iotgw2App.message.deviceTime')">Device Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'deviceTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('rawMessageSHA256')">
              <span v-text="$t('iotgw2App.message.rawMessageSHA256')">Raw Message SHA 256</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rawMessageSHA256'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('rawMessage')">
              <span v-text="$t('iotgw2App.message.rawMessage')">Raw Message</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rawMessage'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('decryptedPayload')">
              <span v-text="$t('iotgw2App.message.decryptedPayload')">Decrypted Payload</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'decryptedPayload'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('devToSrv')">
              <span v-text="$t('iotgw2App.message.devToSrv')">Dev To Srv</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'devToSrv'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('indexFieldValue01')">
              <span v-text="$t('iotgw2App.message.indexFieldValue01')">Index Field Value 01</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'indexFieldValue01'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('indexFieldValue02')">
              <span v-text="$t('iotgw2App.message.indexFieldValue02')">Index Field Value 02</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'indexFieldValue02'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('indexFieldValue03')">
              <span v-text="$t('iotgw2App.message.indexFieldValue03')">Index Field Value 03</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'indexFieldValue03'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('indexFieldValue04')">
              <span v-text="$t('iotgw2App.message.indexFieldValue04')">Index Field Value 04</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'indexFieldValue04'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('processingError')">
              <span v-text="$t('iotgw2App.message.processingError')">Processing Error</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'processingError'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('type.name')">
              <span v-text="$t('iotgw2App.message.type')">Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('device.visualId')">
              <span v-text="$t('iotgw2App.message.device')">Device</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'device.visualId'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="message in messages" :key="message.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MessageView', params: { messageId: message.id } }">{{ message.id }}</router-link>
            </td>
            <td>{{ message.serverTime ? $d(Date.parse(message.serverTime), 'short') : '' }}</td>
            <td>{{ message.deviceTime ? $d(Date.parse(message.deviceTime), 'short') : '' }}</td>
            <td>{{ message.rawMessageSHA256 }}</td>
            <td>
              <a
                v-if="message.rawMessage"
                v-on:click="openFile(message.rawMessageContentType, message.rawMessage)"
                v-text="$t('entity.action.open')"
                >open</a
              >
              <span v-if="message.rawMessage">{{ message.rawMessageContentType }}, {{ byteSize(message.rawMessage) }}</span>
            </td>
            <td>{{ message.decryptedPayload }}</td>
            <td>{{ message.devToSrv }}</td>
            <td>{{ message.indexFieldValue01 }}</td>
            <td>{{ message.indexFieldValue02 }}</td>
            <td>{{ message.indexFieldValue03 }}</td>
            <td>{{ message.indexFieldValue04 }}</td>
            <td>{{ message.processingError }}</td>
            <td>
              <div v-if="message.type">
                <router-link :to="{ name: 'MessageTypeView', params: { messageTypeId: message.type.id } }">{{
                  message.type.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="message.device">
                <router-link :to="{ name: 'DeviceView', params: { deviceId: message.device.id } }">{{
                  message.device.visualId
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'MessageView', params: { messageId: message.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'MessageEdit', params: { messageId: message.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(message)"
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
        ><span id="iotgw2App.message.delete.question" data-cy="messageDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-message-heading" v-text="$t('iotgw2App.message.delete.question', { id: removeId })">
          Are you sure you want to delete this Message?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-message"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeMessage()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="messages && messages.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./message.component.ts"></script>
