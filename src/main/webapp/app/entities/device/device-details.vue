<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="device">
        <h2 class="jh-entity-heading" data-cy="deviceDetailsHeading">
          <span v-text="$t('iotgw2App.device.detail.title')">Device</span> {{ device.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('iotgw2App.device.visualId')">Visual Id</span>
          </dt>
          <dd>
            <span>{{ device.visualId }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.device.description')">Description</span>
          </dt>
          <dd>
            <span>{{ device.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.device.enrollmentCode')">Enrollment Code</span>
          </dt>
          <dd>
            <span>{{ device.enrollmentCode }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.device.enrollmentTime')">Enrollment Time</span>
          </dt>
          <dd>
            <span v-if="device.enrollmentTime">{{ $d(Date.parse(device.enrollmentTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.device.stateFieldValue01')">State Field Value 01</span>
          </dt>
          <dd>
            <span>{{ device.stateFieldValue01 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.device.stateFieldValue02')">State Field Value 02</span>
          </dt>
          <dd>
            <span>{{ device.stateFieldValue02 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.device.stateFieldValue03')">State Field Value 03</span>
          </dt>
          <dd>
            <span>{{ device.stateFieldValue03 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.device.stateFieldValue04')">State Field Value 04</span>
          </dt>
          <dd>
            <span>{{ device.stateFieldValue04 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.device.lastError')">Last Error</span>
          </dt>
          <dd>
            <span>{{ device.lastError }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.device.readAuthPattern')">Read Auth Pattern</span>
          </dt>
          <dd>
            <span>{{ device.readAuthPattern }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.device.writeAuthPattern')">Write Auth Pattern</span>
          </dt>
          <dd>
            <span>{{ device.writeAuthPattern }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.device.deviceSignKey')">Device Sign Key</span>
          </dt>
          <dd>
            <div v-if="device.deviceSignKey">
              <router-link :to="{ name: 'KeyPairView', params: { keyPairId: device.deviceSignKey.id } }">{{
                device.deviceSignKey.keyId
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.device.deviceEncKey')">Device Enc Key</span>
          </dt>
          <dd>
            <div v-if="device.deviceEncKey">
              <router-link :to="{ name: 'KeyPairView', params: { keyPairId: device.deviceEncKey.id } }">{{
                device.deviceEncKey.keyId
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.device.serverSignKey')">Server Sign Key</span>
          </dt>
          <dd>
            <div v-if="device.serverSignKey">
              <router-link :to="{ name: 'KeyPairView', params: { keyPairId: device.serverSignKey.id } }">{{
                device.serverSignKey.keyId
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.device.serverEncKey')">Server Enc Key</span>
          </dt>
          <dd>
            <div v-if="device.serverEncKey">
              <router-link :to="{ name: 'KeyPairView', params: { keyPairId: device.serverEncKey.id } }">{{
                device.serverEncKey.keyId
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.device.nextServerSignKey')">Next Server Sign Key</span>
          </dt>
          <dd>
            <div v-if="device.nextServerSignKey">
              <router-link :to="{ name: 'KeyPairView', params: { keyPairId: device.nextServerSignKey.id } }">{{
                device.nextServerSignKey.keyId
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.device.nextServerEncKey')">Next Server Enc Key</span>
          </dt>
          <dd>
            <div v-if="device.nextServerEncKey">
              <router-link :to="{ name: 'KeyPairView', params: { keyPairId: device.nextServerEncKey.id } }">{{
                device.nextServerEncKey.keyId
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.device.type')">Type</span>
          </dt>
          <dd>
            <div v-if="device.type">
              <router-link :to="{ name: 'DeviceTypeView', params: { deviceTypeId: device.type.id } }">{{ device.type.name }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.device.orgUnit')">Org Unit</span>
          </dt>
          <dd>
            <div v-if="device.orgUnit">
              <router-link :to="{ name: 'OrgUnitView', params: { orgUnitId: device.orgUnit.id } }">{{ device.orgUnit.name }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="device.id" :to="{ name: 'DeviceEdit', params: { deviceId: device.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./device-details.component.ts"></script>
