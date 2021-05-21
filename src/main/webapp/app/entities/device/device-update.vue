<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="iotgw2App.device.home.createOrEditLabel"
          data-cy="DeviceCreateUpdateHeading"
          v-text="$t('iotgw2App.device.home.createOrEditLabel')"
        >
          Create or edit a Device
        </h2>
        <div>
          <div class="form-group" v-if="device.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="device.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.visualId')" for="device-visualId">Visual Id</label>
            <input
              type="text"
              class="form-control"
              name="visualId"
              id="device-visualId"
              data-cy="visualId"
              :class="{ valid: !$v.device.visualId.$invalid, invalid: $v.device.visualId.$invalid }"
              v-model="$v.device.visualId.$model"
              required
            />
            <div v-if="$v.device.visualId.$anyDirty && $v.device.visualId.$invalid">
              <small class="form-text text-danger" v-if="!$v.device.visualId.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.description')" for="device-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="device-description"
              data-cy="description"
              :class="{ valid: !$v.device.description.$invalid, invalid: $v.device.description.$invalid }"
              v-model="$v.device.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.enrollmentCode')" for="device-enrollmentCode"
              >Enrollment Code</label
            >
            <input
              type="text"
              class="form-control"
              name="enrollmentCode"
              id="device-enrollmentCode"
              data-cy="enrollmentCode"
              :class="{ valid: !$v.device.enrollmentCode.$invalid, invalid: $v.device.enrollmentCode.$invalid }"
              v-model="$v.device.enrollmentCode.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.enrollmentTime')" for="device-enrollmentTime"
              >Enrollment Time</label
            >
            <div class="d-flex">
              <input
                id="device-enrollmentTime"
                data-cy="enrollmentTime"
                type="datetime-local"
                class="form-control"
                name="enrollmentTime"
                :class="{ valid: !$v.device.enrollmentTime.$invalid, invalid: $v.device.enrollmentTime.$invalid }"
                :value="convertDateTimeFromServer($v.device.enrollmentTime.$model)"
                @change="updateZonedDateTimeField('enrollmentTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.stateFieldValue01')" for="device-stateFieldValue01"
              >State Field Value 01</label
            >
            <input
              type="text"
              class="form-control"
              name="stateFieldValue01"
              id="device-stateFieldValue01"
              data-cy="stateFieldValue01"
              :class="{ valid: !$v.device.stateFieldValue01.$invalid, invalid: $v.device.stateFieldValue01.$invalid }"
              v-model="$v.device.stateFieldValue01.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.stateFieldValue02')" for="device-stateFieldValue02"
              >State Field Value 02</label
            >
            <input
              type="text"
              class="form-control"
              name="stateFieldValue02"
              id="device-stateFieldValue02"
              data-cy="stateFieldValue02"
              :class="{ valid: !$v.device.stateFieldValue02.$invalid, invalid: $v.device.stateFieldValue02.$invalid }"
              v-model="$v.device.stateFieldValue02.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.stateFieldValue03')" for="device-stateFieldValue03"
              >State Field Value 03</label
            >
            <input
              type="text"
              class="form-control"
              name="stateFieldValue03"
              id="device-stateFieldValue03"
              data-cy="stateFieldValue03"
              :class="{ valid: !$v.device.stateFieldValue03.$invalid, invalid: $v.device.stateFieldValue03.$invalid }"
              v-model="$v.device.stateFieldValue03.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.stateFieldValue04')" for="device-stateFieldValue04"
              >State Field Value 04</label
            >
            <input
              type="text"
              class="form-control"
              name="stateFieldValue04"
              id="device-stateFieldValue04"
              data-cy="stateFieldValue04"
              :class="{ valid: !$v.device.stateFieldValue04.$invalid, invalid: $v.device.stateFieldValue04.$invalid }"
              v-model="$v.device.stateFieldValue04.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.lastError')" for="device-lastError">Last Error</label>
            <textarea
              class="form-control"
              name="lastError"
              id="device-lastError"
              data-cy="lastError"
              :class="{ valid: !$v.device.lastError.$invalid, invalid: $v.device.lastError.$invalid }"
              v-model="$v.device.lastError.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.readAuthPattern')" for="device-readAuthPattern"
              >Read Auth Pattern</label
            >
            <input
              type="text"
              class="form-control"
              name="readAuthPattern"
              id="device-readAuthPattern"
              data-cy="readAuthPattern"
              :class="{ valid: !$v.device.readAuthPattern.$invalid, invalid: $v.device.readAuthPattern.$invalid }"
              v-model="$v.device.readAuthPattern.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.writeAuthPattern')" for="device-writeAuthPattern"
              >Write Auth Pattern</label
            >
            <input
              type="text"
              class="form-control"
              name="writeAuthPattern"
              id="device-writeAuthPattern"
              data-cy="writeAuthPattern"
              :class="{ valid: !$v.device.writeAuthPattern.$invalid, invalid: $v.device.writeAuthPattern.$invalid }"
              v-model="$v.device.writeAuthPattern.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.deviceSignKey')" for="device-deviceSignKey"
              >Device Sign Key</label
            >
            <select
              class="form-control"
              id="device-deviceSignKey"
              data-cy="deviceSignKey"
              name="deviceSignKey"
              v-model="device.deviceSignKey"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="device.deviceSignKey && keyPairOption.id === device.deviceSignKey.id ? device.deviceSignKey : keyPairOption"
                v-for="keyPairOption in deviceSignKeys"
                :key="keyPairOption.id"
              >
                {{ keyPairOption.keyId }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.deviceEncKey')" for="device-deviceEncKey">Device Enc Key</label>
            <select class="form-control" id="device-deviceEncKey" data-cy="deviceEncKey" name="deviceEncKey" v-model="device.deviceEncKey">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="device.deviceEncKey && keyPairOption.id === device.deviceEncKey.id ? device.deviceEncKey : keyPairOption"
                v-for="keyPairOption in deviceEncKeys"
                :key="keyPairOption.id"
              >
                {{ keyPairOption.keyId }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.serverSignKey')" for="device-serverSignKey"
              >Server Sign Key</label
            >
            <select
              class="form-control"
              id="device-serverSignKey"
              data-cy="serverSignKey"
              name="serverSignKey"
              v-model="device.serverSignKey"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="device.serverSignKey && keyPairOption.id === device.serverSignKey.id ? device.serverSignKey : keyPairOption"
                v-for="keyPairOption in serverSignKeys"
                :key="keyPairOption.id"
              >
                {{ keyPairOption.keyId }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.serverEncKey')" for="device-serverEncKey">Server Enc Key</label>
            <select class="form-control" id="device-serverEncKey" data-cy="serverEncKey" name="serverEncKey" v-model="device.serverEncKey">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="device.serverEncKey && keyPairOption.id === device.serverEncKey.id ? device.serverEncKey : keyPairOption"
                v-for="keyPairOption in serverEncKeys"
                :key="keyPairOption.id"
              >
                {{ keyPairOption.keyId }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.nextServerSignKey')" for="device-nextServerSignKey"
              >Next Server Sign Key</label
            >
            <select
              class="form-control"
              id="device-nextServerSignKey"
              data-cy="nextServerSignKey"
              name="nextServerSignKey"
              v-model="device.nextServerSignKey"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  device.nextServerSignKey && keyPairOption.id === device.nextServerSignKey.id ? device.nextServerSignKey : keyPairOption
                "
                v-for="keyPairOption in nextServerSignKeys"
                :key="keyPairOption.id"
              >
                {{ keyPairOption.keyId }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.nextServerEncKey')" for="device-nextServerEncKey"
              >Next Server Enc Key</label
            >
            <select
              class="form-control"
              id="device-nextServerEncKey"
              data-cy="nextServerEncKey"
              name="nextServerEncKey"
              v-model="device.nextServerEncKey"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  device.nextServerEncKey && keyPairOption.id === device.nextServerEncKey.id ? device.nextServerEncKey : keyPairOption
                "
                v-for="keyPairOption in nextServerEncKeys"
                :key="keyPairOption.id"
              >
                {{ keyPairOption.keyId }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.type')" for="device-type">Type</label>
            <select class="form-control" id="device-type" data-cy="type" name="type" v-model="device.type" required>
              <option v-if="!device.type" v-bind:value="null" selected></option>
              <option
                v-bind:value="device.type && deviceTypeOption.id === device.type.id ? device.type : deviceTypeOption"
                v-for="deviceTypeOption in deviceTypes"
                :key="deviceTypeOption.id"
              >
                {{ deviceTypeOption.name }}
              </option>
            </select>
          </div>
          <div v-if="$v.device.type.$anyDirty && $v.device.type.$invalid">
            <small class="form-text text-danger" v-if="!$v.device.type.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.device.orgUnit')" for="device-orgUnit">Org Unit</label>
            <select class="form-control" id="device-orgUnit" data-cy="orgUnit" name="orgUnit" v-model="device.orgUnit" required>
              <option v-if="!device.orgUnit" v-bind:value="null" selected></option>
              <option
                v-bind:value="device.orgUnit && orgUnitOption.id === device.orgUnit.id ? device.orgUnit : orgUnitOption"
                v-for="orgUnitOption in orgUnits"
                :key="orgUnitOption.id"
              >
                {{ orgUnitOption.name }}
              </option>
            </select>
          </div>
          <div v-if="$v.device.orgUnit.$anyDirty && $v.device.orgUnit.$invalid">
            <small class="form-text text-danger" v-if="!$v.device.orgUnit.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
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
            :disabled="$v.device.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./device-update.component.ts"></script>
