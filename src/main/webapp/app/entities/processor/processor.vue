<template>
  <div>
    <h2 id="page-heading" data-cy="ProcessorHeading">
      <span v-text="$t('iotgw2App.processor.home.title')" id="processor-heading">Processors</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('iotgw2App.processor.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ProcessorCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-processor"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('iotgw2App.processor.home.createLabel')"> Create a new Processor </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && processors && processors.length === 0">
      <span v-text="$t('iotgw2App.processor.home.notFound')">No processors found</span>
    </div>
    <div class="table-responsive" v-if="processors && processors.length > 0">
      <table class="table table-striped" aria-describedby="processors">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('iotgw2App.processor.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('example')">
              <span v-text="$t('iotgw2App.processor.example')">Example</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'example'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="$t('iotgw2App.processor.description')">Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('processorIterface')">
              <span v-text="$t('iotgw2App.processor.processorIterface')">Processor Iterface</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'processorIterface'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('implType')">
              <span v-text="$t('iotgw2App.processor.implType')">Impl Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'implType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('param01')">
              <span v-text="$t('iotgw2App.processor.param01')">Param 01</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'param01'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('param02')">
              <span v-text="$t('iotgw2App.processor.param02')">Param 02</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'param02'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('source')">
              <span v-text="$t('iotgw2App.processor.source')">Source</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'source'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('signerName')">
              <span v-text="$t('iotgw2App.processor.signerName')">Signer Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'signerName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('signature')">
              <span v-text="$t('iotgw2App.processor.signature')">Signature</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'signature'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="processor in processors" :key="processor.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ProcessorView', params: { processorId: processor.id } }">{{ processor.id }}</router-link>
            </td>
            <td>{{ processor.name }}</td>
            <td>{{ processor.example }}</td>
            <td>{{ processor.description }}</td>
            <td v-text="$t('iotgw2App.ProcessorInterface.' + processor.processorIterface)">{{ processor.processorIterface }}</td>
            <td v-text="$t('iotgw2App.ImplemntationType.' + processor.implType)">{{ processor.implType }}</td>
            <td>{{ processor.param01 }}</td>
            <td>{{ processor.param02 }}</td>
            <td>{{ processor.source }}</td>
            <td>{{ processor.signerName }}</td>
            <td>
              <a
                v-if="processor.signature"
                v-on:click="openFile(processor.signatureContentType, processor.signature)"
                v-text="$t('entity.action.open')"
                >open</a
              >
              <span v-if="processor.signature">{{ processor.signatureContentType }}, {{ byteSize(processor.signature) }}</span>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ProcessorView', params: { processorId: processor.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ProcessorEdit', params: { processorId: processor.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(processor)"
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
        ><span id="iotgw2App.processor.delete.question" data-cy="processorDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-processor-heading" v-text="$t('iotgw2App.processor.delete.question', { id: removeId })">
          Are you sure you want to delete this Processor?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-processor"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeProcessor()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="processors && processors.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./processor.component.ts"></script>
