<template>
  <div>
    <h2 id="page-heading" data-cy="KeyPairHeading">
      <span v-text="$t('iotgw2App.keyPair.home.title')" id="key-pair-heading">Key Pairs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('iotgw2App.keyPair.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'KeyPairCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-key-pair"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('iotgw2App.keyPair.home.createLabel')"> Create a new Key Pair </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && keyPairs && keyPairs.length === 0">
      <span v-text="$t('iotgw2App.keyPair.home.notFound')">No keyPairs found</span>
    </div>
    <div class="table-responsive" v-if="keyPairs && keyPairs.length > 0">
      <table class="table table-striped" aria-describedby="keyPairs">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('keyId')">
              <span v-text="$t('iotgw2App.keyPair.keyId')">Key Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'keyId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('algorithm')">
              <span v-text="$t('iotgw2App.keyPair.algorithm')">Algorithm</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'algorithm'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('publicKey')">
              <span v-text="$t('iotgw2App.keyPair.publicKey')">Public Key</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'publicKey'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('privateKeyDescriptor')">
              <span v-text="$t('iotgw2App.keyPair.privateKeyDescriptor')">Private Key Descriptor</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'privateKeyDescriptor'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('attestationData')">
              <span v-text="$t('iotgw2App.keyPair.attestationData')">Attestation Data</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'attestationData'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('associatedCert')">
              <span v-text="$t('iotgw2App.keyPair.associatedCert')">Associated Cert</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'associatedCert'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('certificateRequest')">
              <span v-text="$t('iotgw2App.keyPair.certificateRequest')">Certificate Request</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'certificateRequest'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('certSubjectDN')">
              <span v-text="$t('iotgw2App.keyPair.certSubjectDN')">Cert Subject DN</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'certSubjectDN'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('certIssuerDN')">
              <span v-text="$t('iotgw2App.keyPair.certIssuerDN')">Cert Issuer DN</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'certIssuerDN'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('certSerial')">
              <span v-text="$t('iotgw2App.keyPair.certSerial')">Cert Serial</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'certSerial'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('certNotBefore')">
              <span v-text="$t('iotgw2App.keyPair.certNotBefore')">Cert Not Before</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'certNotBefore'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('certNotAfter')">
              <span v-text="$t('iotgw2App.keyPair.certNotAfter')">Cert Not After</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'certNotAfter'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('certRevoked')">
              <span v-text="$t('iotgw2App.keyPair.certRevoked')">Cert Revoked</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'certRevoked'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="keyPair in keyPairs" :key="keyPair.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'KeyPairView', params: { keyPairId: keyPair.id } }">{{ keyPair.id }}</router-link>
            </td>
            <td>{{ keyPair.keyId }}</td>
            <td v-text="$t('iotgw2App.PkAlgorithm.' + keyPair.algorithm)">{{ keyPair.algorithm }}</td>
            <td>
              <a
                v-if="keyPair.publicKey"
                v-on:click="openFile(keyPair.publicKeyContentType, keyPair.publicKey)"
                v-text="$t('entity.action.open')"
                >open</a
              >
              <span v-if="keyPair.publicKey">{{ keyPair.publicKeyContentType }}, {{ byteSize(keyPair.publicKey) }}</span>
            </td>
            <td>{{ keyPair.privateKeyDescriptor }}</td>
            <td>
              <a
                v-if="keyPair.attestationData"
                v-on:click="openFile(keyPair.attestationDataContentType, keyPair.attestationData)"
                v-text="$t('entity.action.open')"
                >open</a
              >
              <span v-if="keyPair.attestationData">{{ keyPair.attestationDataContentType }}, {{ byteSize(keyPair.attestationData) }}</span>
            </td>
            <td>
              <a
                v-if="keyPair.associatedCert"
                v-on:click="openFile(keyPair.associatedCertContentType, keyPair.associatedCert)"
                v-text="$t('entity.action.open')"
                >open</a
              >
              <span v-if="keyPair.associatedCert">{{ keyPair.associatedCertContentType }}, {{ byteSize(keyPair.associatedCert) }}</span>
            </td>
            <td>
              <a
                v-if="keyPair.certificateRequest"
                v-on:click="openFile(keyPair.certificateRequestContentType, keyPair.certificateRequest)"
                v-text="$t('entity.action.open')"
                >open</a
              >
              <span v-if="keyPair.certificateRequest"
                >{{ keyPair.certificateRequestContentType }}, {{ byteSize(keyPair.certificateRequest) }}</span
              >
            </td>
            <td>{{ keyPair.certSubjectDN }}</td>
            <td>{{ keyPair.certIssuerDN }}</td>
            <td>{{ keyPair.certSerial }}</td>
            <td>{{ keyPair.certNotBefore ? $d(Date.parse(keyPair.certNotBefore), 'short') : '' }}</td>
            <td>{{ keyPair.certNotAfter ? $d(Date.parse(keyPair.certNotAfter), 'short') : '' }}</td>
            <td>{{ keyPair.certRevoked ? $d(Date.parse(keyPair.certRevoked), 'short') : '' }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'KeyPairView', params: { keyPairId: keyPair.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'KeyPairEdit', params: { keyPairId: keyPair.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(keyPair)"
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
        ><span id="iotgw2App.keyPair.delete.question" data-cy="keyPairDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-keyPair-heading" v-text="$t('iotgw2App.keyPair.delete.question', { id: removeId })">
          Are you sure you want to delete this Key Pair?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-keyPair"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeKeyPair()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="keyPairs && keyPairs.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./key-pair.component.ts"></script>
