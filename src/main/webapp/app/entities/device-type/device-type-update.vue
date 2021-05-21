<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="iotgw2App.deviceType.home.createOrEditLabel"
          data-cy="DeviceTypeCreateUpdateHeading"
          v-text="$t('iotgw2App.deviceType.home.createOrEditLabel')"
        >
          Create or edit a DeviceType
        </h2>
        <div>
          <div class="form-group" v-if="deviceType.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="deviceType.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.deviceType.name')" for="device-type-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="device-type-name"
              data-cy="name"
              :class="{ valid: !$v.deviceType.name.$invalid, invalid: $v.deviceType.name.$invalid }"
              v-model="$v.deviceType.name.$model"
              required
            />
            <div v-if="$v.deviceType.name.$anyDirty && $v.deviceType.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.deviceType.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.deviceType.description')" for="device-type-description"
              >Description</label
            >
            <input
              type="text"
              class="form-control"
              name="description"
              id="device-type-description"
              data-cy="description"
              :class="{ valid: !$v.deviceType.description.$invalid, invalid: $v.deviceType.description.$invalid }"
              v-model="$v.deviceType.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.deviceType.stateFieldName01')" for="device-type-stateFieldName01"
              >State Field Name 01</label
            >
            <input
              type="text"
              class="form-control"
              name="stateFieldName01"
              id="device-type-stateFieldName01"
              data-cy="stateFieldName01"
              :class="{ valid: !$v.deviceType.stateFieldName01.$invalid, invalid: $v.deviceType.stateFieldName01.$invalid }"
              v-model="$v.deviceType.stateFieldName01.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.deviceType.stateFieldName02')" for="device-type-stateFieldName02"
              >State Field Name 02</label
            >
            <input
              type="text"
              class="form-control"
              name="stateFieldName02"
              id="device-type-stateFieldName02"
              data-cy="stateFieldName02"
              :class="{ valid: !$v.deviceType.stateFieldName02.$invalid, invalid: $v.deviceType.stateFieldName02.$invalid }"
              v-model="$v.deviceType.stateFieldName02.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.deviceType.stateFieldName03')" for="device-type-stateFieldName03"
              >State Field Name 03</label
            >
            <input
              type="text"
              class="form-control"
              name="stateFieldName03"
              id="device-type-stateFieldName03"
              data-cy="stateFieldName03"
              :class="{ valid: !$v.deviceType.stateFieldName03.$invalid, invalid: $v.deviceType.stateFieldName03.$invalid }"
              v-model="$v.deviceType.stateFieldName03.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.deviceType.stateFieldName04')" for="device-type-stateFieldName04"
              >State Field Name 04</label
            >
            <input
              type="text"
              class="form-control"
              name="stateFieldName04"
              id="device-type-stateFieldName04"
              data-cy="stateFieldName04"
              :class="{ valid: !$v.deviceType.stateFieldName04.$invalid, invalid: $v.deviceType.stateFieldName04.$invalid }"
              v-model="$v.deviceType.stateFieldName04.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.deviceType.readAuthPattern')" for="device-type-readAuthPattern"
              >Read Auth Pattern</label
            >
            <input
              type="text"
              class="form-control"
              name="readAuthPattern"
              id="device-type-readAuthPattern"
              data-cy="readAuthPattern"
              :class="{ valid: !$v.deviceType.readAuthPattern.$invalid, invalid: $v.deviceType.readAuthPattern.$invalid }"
              v-model="$v.deviceType.readAuthPattern.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.deviceType.writeAuthPattern')" for="device-type-writeAuthPattern"
              >Write Auth Pattern</label
            >
            <input
              type="text"
              class="form-control"
              name="writeAuthPattern"
              id="device-type-writeAuthPattern"
              data-cy="writeAuthPattern"
              :class="{ valid: !$v.deviceType.writeAuthPattern.$invalid, invalid: $v.deviceType.writeAuthPattern.$invalid }"
              v-model="$v.deviceType.writeAuthPattern.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.deviceType.enrollProcessor')" for="device-type-enrollProcessor"
              >Enroll Processor</label
            >
            <select
              class="form-control"
              id="device-type-enrollProcessor"
              data-cy="enrollProcessor"
              name="enrollProcessor"
              v-model="deviceType.enrollProcessor"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  deviceType.enrollProcessor && processorOption.id === deviceType.enrollProcessor.id
                    ? deviceType.enrollProcessor
                    : processorOption
                "
                v-for="processorOption in processors"
                :key="processorOption.id"
              >
                {{ processorOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label v-text="$t('iotgw2App.deviceType.messageTypes')" for="device-type-messageTypes">Message Types</label>
            <select
              class="form-control"
              id="device-type-messageTypes"
              data-cy="messageTypes"
              multiple
              name="messageTypes"
              v-if="deviceType.messageTypes !== undefined"
              v-model="deviceType.messageTypes"
            >
              <option
                v-bind:value="getSelected(deviceType.messageTypes, messageTypeOption)"
                v-for="messageTypeOption in messageTypes"
                :key="messageTypeOption.id"
              >
                {{ messageTypeOption.name }}
              </option>
            </select>
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
            :disabled="$v.deviceType.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./device-type-update.component.ts"></script>
