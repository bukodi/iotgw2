<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="message">
        <h2 class="jh-entity-heading" data-cy="messageDetailsHeading">
          <span v-text="$t('iotgw2App.message.detail.title')">Message</span> {{ message.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('iotgw2App.message.serverTime')">Server Time</span>
          </dt>
          <dd>
            <span v-if="message.serverTime">{{ $d(Date.parse(message.serverTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.message.deviceTime')">Device Time</span>
          </dt>
          <dd>
            <span v-if="message.deviceTime">{{ $d(Date.parse(message.deviceTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.message.rawMessageSHA256')">Raw Message SHA 256</span>
          </dt>
          <dd>
            <span>{{ message.rawMessageSHA256 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.message.rawMessage')">Raw Message</span>
          </dt>
          <dd>
            <div v-if="message.rawMessage">
              <a v-on:click="openFile(message.rawMessageContentType, message.rawMessage)" v-text="$t('entity.action.open')">open</a>
              {{ message.rawMessageContentType }}, {{ byteSize(message.rawMessage) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.message.decryptedPayload')">Decrypted Payload</span>
          </dt>
          <dd>
            <span>{{ message.decryptedPayload }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.message.devToSrv')">Dev To Srv</span>
          </dt>
          <dd>
            <span>{{ message.devToSrv }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.message.indexFieldValue01')">Index Field Value 01</span>
          </dt>
          <dd>
            <span>{{ message.indexFieldValue01 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.message.indexFieldValue02')">Index Field Value 02</span>
          </dt>
          <dd>
            <span>{{ message.indexFieldValue02 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.message.indexFieldValue03')">Index Field Value 03</span>
          </dt>
          <dd>
            <span>{{ message.indexFieldValue03 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.message.indexFieldValue04')">Index Field Value 04</span>
          </dt>
          <dd>
            <span>{{ message.indexFieldValue04 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.message.processingError')">Processing Error</span>
          </dt>
          <dd>
            <span>{{ message.processingError }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.message.type')">Type</span>
          </dt>
          <dd>
            <div v-if="message.type">
              <router-link :to="{ name: 'MessageTypeView', params: { messageTypeId: message.type.id } }">{{
                message.type.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.message.device')">Device</span>
          </dt>
          <dd>
            <div v-if="message.device">
              <router-link :to="{ name: 'DeviceView', params: { deviceId: message.device.id } }">{{ message.device.visualId }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="message.id" :to="{ name: 'MessageEdit', params: { messageId: message.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./message-details.component.ts"></script>
