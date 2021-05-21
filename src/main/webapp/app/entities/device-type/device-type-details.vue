<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="deviceType">
        <h2 class="jh-entity-heading" data-cy="deviceTypeDetailsHeading">
          <span v-text="$t('iotgw2App.deviceType.detail.title')">DeviceType</span> {{ deviceType.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('iotgw2App.deviceType.name')">Name</span>
          </dt>
          <dd>
            <span>{{ deviceType.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.deviceType.description')">Description</span>
          </dt>
          <dd>
            <span>{{ deviceType.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.deviceType.stateFieldName01')">State Field Name 01</span>
          </dt>
          <dd>
            <span>{{ deviceType.stateFieldName01 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.deviceType.stateFieldName02')">State Field Name 02</span>
          </dt>
          <dd>
            <span>{{ deviceType.stateFieldName02 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.deviceType.stateFieldName03')">State Field Name 03</span>
          </dt>
          <dd>
            <span>{{ deviceType.stateFieldName03 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.deviceType.stateFieldName04')">State Field Name 04</span>
          </dt>
          <dd>
            <span>{{ deviceType.stateFieldName04 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.deviceType.readAuthPattern')">Read Auth Pattern</span>
          </dt>
          <dd>
            <span>{{ deviceType.readAuthPattern }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.deviceType.writeAuthPattern')">Write Auth Pattern</span>
          </dt>
          <dd>
            <span>{{ deviceType.writeAuthPattern }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.deviceType.enrollProcessor')">Enroll Processor</span>
          </dt>
          <dd>
            <div v-if="deviceType.enrollProcessor">
              <router-link :to="{ name: 'ProcessorView', params: { processorId: deviceType.enrollProcessor.id } }">{{
                deviceType.enrollProcessor.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.deviceType.messageTypes')">Message Types</span>
          </dt>
          <dd>
            <span v-for="(messageTypes, i) in deviceType.messageTypes" :key="messageTypes.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'MessageTypeView', params: { messageTypeId: messageTypes.id } }">{{
                messageTypes.name
              }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="deviceType.id"
          :to="{ name: 'DeviceTypeEdit', params: { deviceTypeId: deviceType.id } }"
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

<script lang="ts" src="./device-type-details.component.ts"></script>
