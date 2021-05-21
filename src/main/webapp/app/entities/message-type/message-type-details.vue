<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="messageType">
        <h2 class="jh-entity-heading" data-cy="messageTypeDetailsHeading">
          <span v-text="$t('iotgw2App.messageType.detail.title')">MessageType</span> {{ messageType.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('iotgw2App.messageType.typeCode')">Type Code</span>
          </dt>
          <dd>
            <span>{{ messageType.typeCode }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.messageType.devToSrv')">Dev To Srv</span>
          </dt>
          <dd>
            <span>{{ messageType.devToSrv }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.messageType.name')">Name</span>
          </dt>
          <dd>
            <span>{{ messageType.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.messageType.description')">Description</span>
          </dt>
          <dd>
            <span>{{ messageType.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.messageType.timeout')">Timeout</span>
          </dt>
          <dd>
            <span>{{ messageType.timeout | duration }} ({{ messageType.timeout }})</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.messageType.retentionTime')">Retention Time</span>
          </dt>
          <dd>
            <span>{{ messageType.retentionTime | duration }} ({{ messageType.retentionTime }})</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.messageType.indexFieldName01')">Index Field Name 01</span>
          </dt>
          <dd>
            <span>{{ messageType.indexFieldName01 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.messageType.indexFieldName02')">Index Field Name 02</span>
          </dt>
          <dd>
            <span>{{ messageType.indexFieldName02 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.messageType.indexFieldName03')">Index Field Name 03</span>
          </dt>
          <dd>
            <span>{{ messageType.indexFieldName03 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.messageType.indexFieldName04')">Index Field Name 04</span>
          </dt>
          <dd>
            <span>{{ messageType.indexFieldName04 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.messageType.readAuthPattern')">Read Auth Pattern</span>
          </dt>
          <dd>
            <span>{{ messageType.readAuthPattern }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.messageType.writeAuthPattern')">Write Auth Pattern</span>
          </dt>
          <dd>
            <span>{{ messageType.writeAuthPattern }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.messageType.messageProcessor')">Message Processor</span>
          </dt>
          <dd>
            <div v-if="messageType.messageProcessor">
              <router-link :to="{ name: 'ProcessorView', params: { processorId: messageType.messageProcessor.id } }">{{
                messageType.messageProcessor.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.messageType.timeoutProcessor')">Timeout Processor</span>
          </dt>
          <dd>
            <div v-if="messageType.timeoutProcessor">
              <router-link :to="{ name: 'ProcessorView', params: { processorId: messageType.timeoutProcessor.id } }">{{
                messageType.timeoutProcessor.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="messageType.id"
          :to="{ name: 'MessageTypeEdit', params: { messageTypeId: messageType.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./message-type-details.component.ts"></script>
