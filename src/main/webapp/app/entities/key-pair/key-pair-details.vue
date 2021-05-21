<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="keyPair">
        <h2 class="jh-entity-heading" data-cy="keyPairDetailsHeading">
          <span v-text="$t('iotgw2App.keyPair.detail.title')">KeyPair</span> {{ keyPair.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('iotgw2App.keyPair.keyId')">Key Id</span>
          </dt>
          <dd>
            <span>{{ keyPair.keyId }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.keyPair.algorithm')">Algorithm</span>
          </dt>
          <dd>
            <span v-text="$t('iotgw2App.PkAlgorithm.' + keyPair.algorithm)">{{ keyPair.algorithm }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.keyPair.publicKey')">Public Key</span>
          </dt>
          <dd>
            <div v-if="keyPair.publicKey">
              <a v-on:click="openFile(keyPair.publicKeyContentType, keyPair.publicKey)" v-text="$t('entity.action.open')">open</a>
              {{ keyPair.publicKeyContentType }}, {{ byteSize(keyPair.publicKey) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.keyPair.privateKeyDescriptor')">Private Key Descriptor</span>
          </dt>
          <dd>
            <span>{{ keyPair.privateKeyDescriptor }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.keyPair.attestationData')">Attestation Data</span>
          </dt>
          <dd>
            <div v-if="keyPair.attestationData">
              <a v-on:click="openFile(keyPair.attestationDataContentType, keyPair.attestationData)" v-text="$t('entity.action.open')"
                >open</a
              >
              {{ keyPair.attestationDataContentType }}, {{ byteSize(keyPair.attestationData) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.keyPair.associatedCert')">Associated Cert</span>
          </dt>
          <dd>
            <div v-if="keyPair.associatedCert">
              <a v-on:click="openFile(keyPair.associatedCertContentType, keyPair.associatedCert)" v-text="$t('entity.action.open')">open</a>
              {{ keyPair.associatedCertContentType }}, {{ byteSize(keyPair.associatedCert) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.keyPair.certificateRequest')">Certificate Request</span>
          </dt>
          <dd>
            <div v-if="keyPair.certificateRequest">
              <a v-on:click="openFile(keyPair.certificateRequestContentType, keyPair.certificateRequest)" v-text="$t('entity.action.open')"
                >open</a
              >
              {{ keyPair.certificateRequestContentType }}, {{ byteSize(keyPair.certificateRequest) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.keyPair.certSubjectDN')">Cert Subject DN</span>
          </dt>
          <dd>
            <span>{{ keyPair.certSubjectDN }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.keyPair.certIssuerDN')">Cert Issuer DN</span>
          </dt>
          <dd>
            <span>{{ keyPair.certIssuerDN }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.keyPair.certSerial')">Cert Serial</span>
          </dt>
          <dd>
            <span>{{ keyPair.certSerial }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.keyPair.certNotBefore')">Cert Not Before</span>
          </dt>
          <dd>
            <span v-if="keyPair.certNotBefore">{{ $d(Date.parse(keyPair.certNotBefore), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.keyPair.certNotAfter')">Cert Not After</span>
          </dt>
          <dd>
            <span v-if="keyPair.certNotAfter">{{ $d(Date.parse(keyPair.certNotAfter), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.keyPair.certRevoked')">Cert Revoked</span>
          </dt>
          <dd>
            <span v-if="keyPair.certRevoked">{{ $d(Date.parse(keyPair.certRevoked), 'long') }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="keyPair.id" :to="{ name: 'KeyPairEdit', params: { keyPairId: keyPair.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./key-pair-details.component.ts"></script>
