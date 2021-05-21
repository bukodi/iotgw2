<template>
  <div>
    <h2 id="page-heading" data-cy="MessageTypeHeading">
      <span v-text="$t('iotgw2App.messageType.home.title')" id="message-type-heading">Message Types</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('iotgw2App.messageType.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'MessageTypeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-message-type"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('iotgw2App.messageType.home.createLabel')"> Create a new Message Type </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && messageTypes && messageTypes.length === 0">
      <span v-text="$t('iotgw2App.messageType.home.notFound')">No messageTypes found</span>
    </div>
    <div class="table-responsive" v-if="messageTypes && messageTypes.length > 0">
      <table class="table table-striped" aria-describedby="messageTypes">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('typeCode')">
              <span v-text="$t('iotgw2App.messageType.typeCode')">Type Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'typeCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('devToSrv')">
              <span v-text="$t('iotgw2App.messageType.devToSrv')">Dev To Srv</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'devToSrv'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('iotgw2App.messageType.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="$t('iotgw2App.messageType.description')">Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('timeout')">
              <span v-text="$t('iotgw2App.messageType.timeout')">Timeout</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'timeout'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('retentionTime')">
              <span v-text="$t('iotgw2App.messageType.retentionTime')">Retention Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'retentionTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('indexFieldName01')">
              <span v-text="$t('iotgw2App.messageType.indexFieldName01')">Index Field Name 01</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'indexFieldName01'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('indexFieldName02')">
              <span v-text="$t('iotgw2App.messageType.indexFieldName02')">Index Field Name 02</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'indexFieldName02'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('indexFieldName03')">
              <span v-text="$t('iotgw2App.messageType.indexFieldName03')">Index Field Name 03</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'indexFieldName03'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('indexFieldName04')">
              <span v-text="$t('iotgw2App.messageType.indexFieldName04')">Index Field Name 04</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'indexFieldName04'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('readAuthPattern')">
              <span v-text="$t('iotgw2App.messageType.readAuthPattern')">Read Auth Pattern</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'readAuthPattern'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('writeAuthPattern')">
              <span v-text="$t('iotgw2App.messageType.writeAuthPattern')">Write Auth Pattern</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'writeAuthPattern'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('messageProcessor.name')">
              <span v-text="$t('iotgw2App.messageType.messageProcessor')">Message Processor</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'messageProcessor.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('timeoutProcessor.name')">
              <span v-text="$t('iotgw2App.messageType.timeoutProcessor')">Timeout Processor</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'timeoutProcessor.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="messageType in messageTypes" :key="messageType.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MessageTypeView', params: { messageTypeId: messageType.id } }">{{ messageType.id }}</router-link>
            </td>
            <td>{{ messageType.typeCode }}</td>
            <td>{{ messageType.devToSrv }}</td>
            <td>{{ messageType.name }}</td>
            <td>{{ messageType.description }}</td>
            <td>{{ messageType.timeout | duration }}</td>
            <td>{{ messageType.retentionTime | duration }}</td>
            <td>{{ messageType.indexFieldName01 }}</td>
            <td>{{ messageType.indexFieldName02 }}</td>
            <td>{{ messageType.indexFieldName03 }}</td>
            <td>{{ messageType.indexFieldName04 }}</td>
            <td>{{ messageType.readAuthPattern }}</td>
            <td>{{ messageType.writeAuthPattern }}</td>
            <td>
              <div v-if="messageType.messageProcessor">
                <router-link :to="{ name: 'ProcessorView', params: { processorId: messageType.messageProcessor.id } }">{{
                  messageType.messageProcessor.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="messageType.timeoutProcessor">
                <router-link :to="{ name: 'ProcessorView', params: { processorId: messageType.timeoutProcessor.id } }">{{
                  messageType.timeoutProcessor.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'MessageTypeView', params: { messageTypeId: messageType.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'MessageTypeEdit', params: { messageTypeId: messageType.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(messageType)"
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
        ><span id="iotgw2App.messageType.delete.question" data-cy="messageTypeDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-messageType-heading" v-text="$t('iotgw2App.messageType.delete.question', { id: removeId })">
          Are you sure you want to delete this Message Type?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-messageType"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeMessageType()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="messageTypes && messageTypes.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./message-type.component.ts"></script>
