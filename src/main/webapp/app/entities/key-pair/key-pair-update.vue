<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="iotgw2App.keyPair.home.createOrEditLabel"
          data-cy="KeyPairCreateUpdateHeading"
          v-text="$t('iotgw2App.keyPair.home.createOrEditLabel')"
        >
          Create or edit a KeyPair
        </h2>
        <div>
          <div class="form-group" v-if="keyPair.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="keyPair.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.keyPair.keyId')" for="key-pair-keyId">Key Id</label>
            <input
              type="text"
              class="form-control"
              name="keyId"
              id="key-pair-keyId"
              data-cy="keyId"
              :class="{ valid: !$v.keyPair.keyId.$invalid, invalid: $v.keyPair.keyId.$invalid }"
              v-model="$v.keyPair.keyId.$model"
              required
            />
            <div v-if="$v.keyPair.keyId.$anyDirty && $v.keyPair.keyId.$invalid">
              <small class="form-text text-danger" v-if="!$v.keyPair.keyId.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.keyPair.algorithm')" for="key-pair-algorithm">Algorithm</label>
            <select
              class="form-control"
              name="algorithm"
              :class="{ valid: !$v.keyPair.algorithm.$invalid, invalid: $v.keyPair.algorithm.$invalid }"
              v-model="$v.keyPair.algorithm.$model"
              id="key-pair-algorithm"
              data-cy="algorithm"
              required
            >
              <option value="RSA1024" v-bind:label="$t('iotgw2App.PkAlgorithm.RSA1024')">RSA1024</option>
              <option value="RSA2048" v-bind:label="$t('iotgw2App.PkAlgorithm.RSA2048')">RSA2048</option>
              <option value="RSA3072" v-bind:label="$t('iotgw2App.PkAlgorithm.RSA3072')">RSA3072</option>
              <option value="RSA4096" v-bind:label="$t('iotgw2App.PkAlgorithm.RSA4096')">RSA4096</option>
              <option value="P256" v-bind:label="$t('iotgw2App.PkAlgorithm.P256')">P256</option>
            </select>
            <div v-if="$v.keyPair.algorithm.$anyDirty && $v.keyPair.algorithm.$invalid">
              <small class="form-text text-danger" v-if="!$v.keyPair.algorithm.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.keyPair.publicKey')" for="key-pair-publicKey">Public Key</label>
            <div>
              <div v-if="keyPair.publicKey" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(keyPair.publicKeyContentType, keyPair.publicKey)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ keyPair.publicKeyContentType }}, {{ byteSize(keyPair.publicKey) }}</span>
                <button
                  type="button"
                  v-on:click="
                    keyPair.publicKey = null;
                    keyPair.publicKeyContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_publicKey"
                id="file_publicKey"
                data-cy="publicKey"
                v-on:change="setFileData($event, keyPair, 'publicKey', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="publicKey"
              id="key-pair-publicKey"
              data-cy="publicKey"
              :class="{ valid: !$v.keyPair.publicKey.$invalid, invalid: $v.keyPair.publicKey.$invalid }"
              v-model="$v.keyPair.publicKey.$model"
              required
            />
            <input
              type="hidden"
              class="form-control"
              name="publicKeyContentType"
              id="key-pair-publicKeyContentType"
              v-model="keyPair.publicKeyContentType"
            />
            <div v-if="$v.keyPair.publicKey.$anyDirty && $v.keyPair.publicKey.$invalid">
              <small class="form-text text-danger" v-if="!$v.keyPair.publicKey.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.keyPair.privateKeyDescriptor')" for="key-pair-privateKeyDescriptor"
              >Private Key Descriptor</label
            >
            <textarea
              class="form-control"
              name="privateKeyDescriptor"
              id="key-pair-privateKeyDescriptor"
              data-cy="privateKeyDescriptor"
              :class="{ valid: !$v.keyPair.privateKeyDescriptor.$invalid, invalid: $v.keyPair.privateKeyDescriptor.$invalid }"
              v-model="$v.keyPair.privateKeyDescriptor.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.keyPair.attestationData')" for="key-pair-attestationData"
              >Attestation Data</label
            >
            <div>
              <div v-if="keyPair.attestationData" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(keyPair.attestationDataContentType, keyPair.attestationData)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ keyPair.attestationDataContentType }}, {{ byteSize(keyPair.attestationData) }}</span>
                <button
                  type="button"
                  v-on:click="
                    keyPair.attestationData = null;
                    keyPair.attestationDataContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_attestationData"
                id="file_attestationData"
                data-cy="attestationData"
                v-on:change="setFileData($event, keyPair, 'attestationData', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="attestationData"
              id="key-pair-attestationData"
              data-cy="attestationData"
              :class="{ valid: !$v.keyPair.attestationData.$invalid, invalid: $v.keyPair.attestationData.$invalid }"
              v-model="$v.keyPair.attestationData.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="attestationDataContentType"
              id="key-pair-attestationDataContentType"
              v-model="keyPair.attestationDataContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.keyPair.associatedCert')" for="key-pair-associatedCert"
              >Associated Cert</label
            >
            <div>
              <div v-if="keyPair.associatedCert" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(keyPair.associatedCertContentType, keyPair.associatedCert)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ keyPair.associatedCertContentType }}, {{ byteSize(keyPair.associatedCert) }}</span>
                <button
                  type="button"
                  v-on:click="
                    keyPair.associatedCert = null;
                    keyPair.associatedCertContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_associatedCert"
                id="file_associatedCert"
                data-cy="associatedCert"
                v-on:change="setFileData($event, keyPair, 'associatedCert', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="associatedCert"
              id="key-pair-associatedCert"
              data-cy="associatedCert"
              :class="{ valid: !$v.keyPair.associatedCert.$invalid, invalid: $v.keyPair.associatedCert.$invalid }"
              v-model="$v.keyPair.associatedCert.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="associatedCertContentType"
              id="key-pair-associatedCertContentType"
              v-model="keyPair.associatedCertContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.keyPair.certificateRequest')" for="key-pair-certificateRequest"
              >Certificate Request</label
            >
            <div>
              <div v-if="keyPair.certificateRequest" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(keyPair.certificateRequestContentType, keyPair.certificateRequest)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ keyPair.certificateRequestContentType }}, {{ byteSize(keyPair.certificateRequest) }}</span>
                <button
                  type="button"
                  v-on:click="
                    keyPair.certificateRequest = null;
                    keyPair.certificateRequestContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_certificateRequest"
                id="file_certificateRequest"
                data-cy="certificateRequest"
                v-on:change="setFileData($event, keyPair, 'certificateRequest', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="certificateRequest"
              id="key-pair-certificateRequest"
              data-cy="certificateRequest"
              :class="{ valid: !$v.keyPair.certificateRequest.$invalid, invalid: $v.keyPair.certificateRequest.$invalid }"
              v-model="$v.keyPair.certificateRequest.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="certificateRequestContentType"
              id="key-pair-certificateRequestContentType"
              v-model="keyPair.certificateRequestContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.keyPair.certSubjectDN')" for="key-pair-certSubjectDN"
              >Cert Subject DN</label
            >
            <input
              type="text"
              class="form-control"
              name="certSubjectDN"
              id="key-pair-certSubjectDN"
              data-cy="certSubjectDN"
              :class="{ valid: !$v.keyPair.certSubjectDN.$invalid, invalid: $v.keyPair.certSubjectDN.$invalid }"
              v-model="$v.keyPair.certSubjectDN.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.keyPair.certIssuerDN')" for="key-pair-certIssuerDN"
              >Cert Issuer DN</label
            >
            <input
              type="text"
              class="form-control"
              name="certIssuerDN"
              id="key-pair-certIssuerDN"
              data-cy="certIssuerDN"
              :class="{ valid: !$v.keyPair.certIssuerDN.$invalid, invalid: $v.keyPair.certIssuerDN.$invalid }"
              v-model="$v.keyPair.certIssuerDN.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.keyPair.certSerial')" for="key-pair-certSerial">Cert Serial</label>
            <input
              type="text"
              class="form-control"
              name="certSerial"
              id="key-pair-certSerial"
              data-cy="certSerial"
              :class="{ valid: !$v.keyPair.certSerial.$invalid, invalid: $v.keyPair.certSerial.$invalid }"
              v-model="$v.keyPair.certSerial.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.keyPair.certNotBefore')" for="key-pair-certNotBefore"
              >Cert Not Before</label
            >
            <div class="d-flex">
              <input
                id="key-pair-certNotBefore"
                data-cy="certNotBefore"
                type="datetime-local"
                class="form-control"
                name="certNotBefore"
                :class="{ valid: !$v.keyPair.certNotBefore.$invalid, invalid: $v.keyPair.certNotBefore.$invalid }"
                :value="convertDateTimeFromServer($v.keyPair.certNotBefore.$model)"
                @change="updateZonedDateTimeField('certNotBefore', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.keyPair.certNotAfter')" for="key-pair-certNotAfter"
              >Cert Not After</label
            >
            <div class="d-flex">
              <input
                id="key-pair-certNotAfter"
                data-cy="certNotAfter"
                type="datetime-local"
                class="form-control"
                name="certNotAfter"
                :class="{ valid: !$v.keyPair.certNotAfter.$invalid, invalid: $v.keyPair.certNotAfter.$invalid }"
                :value="convertDateTimeFromServer($v.keyPair.certNotAfter.$model)"
                @change="updateZonedDateTimeField('certNotAfter', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.keyPair.certRevoked')" for="key-pair-certRevoked">Cert Revoked</label>
            <div class="d-flex">
              <input
                id="key-pair-certRevoked"
                data-cy="certRevoked"
                type="datetime-local"
                class="form-control"
                name="certRevoked"
                :class="{ valid: !$v.keyPair.certRevoked.$invalid, invalid: $v.keyPair.certRevoked.$invalid }"
                :value="convertDateTimeFromServer($v.keyPair.certRevoked.$model)"
                @change="updateZonedDateTimeField('certRevoked', $event)"
              />
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.keyPair.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./key-pair-update.component.ts"></script>
