<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="iotgw2App.message.home.createOrEditLabel"
          data-cy="MessageCreateUpdateHeading"
          v-text="$t('iotgw2App.message.home.createOrEditLabel')"
        >
          Create or edit a Message
        </h2>
        <div>
          <div class="form-group" v-if="message.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="message.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.message.serverTime')" for="message-serverTime">Server Time</label>
            <div class="d-flex">
              <input
                id="message-serverTime"
                data-cy="serverTime"
                type="datetime-local"
                class="form-control"
                name="serverTime"
                :class="{ valid: !$v.message.serverTime.$invalid, invalid: $v.message.serverTime.$invalid }"
                required
                :value="convertDateTimeFromServer($v.message.serverTime.$model)"
                @change="updateZonedDateTimeField('serverTime', $event)"
              />
            </div>
            <div v-if="$v.message.serverTime.$anyDirty && $v.message.serverTime.$invalid">
              <small class="form-text text-danger" v-if="!$v.message.serverTime.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.message.serverTime.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.message.deviceTime')" for="message-deviceTime">Device Time</label>
            <div class="d-flex">
              <input
                id="message-deviceTime"
                data-cy="deviceTime"
                type="datetime-local"
                class="form-control"
                name="deviceTime"
                :class="{ valid: !$v.message.deviceTime.$invalid, invalid: $v.message.deviceTime.$invalid }"
                :value="convertDateTimeFromServer($v.message.deviceTime.$model)"
                @change="updateZonedDateTimeField('deviceTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.message.rawMessageSHA256')" for="message-rawMessageSHA256"
              >Raw Message SHA 256</label
            >
            <input
              type="text"
              class="form-control"
              name="rawMessageSHA256"
              id="message-rawMessageSHA256"
              data-cy="rawMessageSHA256"
              :class="{ valid: !$v.message.rawMessageSHA256.$invalid, invalid: $v.message.rawMessageSHA256.$invalid }"
              v-model="$v.message.rawMessageSHA256.$model"
              required
            />
            <div v-if="$v.message.rawMessageSHA256.$anyDirty && $v.message.rawMessageSHA256.$invalid">
              <small class="form-text text-danger" v-if="!$v.message.rawMessageSHA256.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.message.rawMessage')" for="message-rawMessage">Raw Message</label>
            <div>
              <div v-if="message.rawMessage" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(message.rawMessageContentType, message.rawMessage)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ message.rawMessageContentType }}, {{ byteSize(message.rawMessage) }}</span>
                <button
                  type="button"
                  v-on:click="
                    message.rawMessage = null;
                    message.rawMessageContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_rawMessage"
                id="file_rawMessage"
                data-cy="rawMessage"
                v-on:change="setFileData($event, message, 'rawMessage', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="rawMessage"
              id="message-rawMessage"
              data-cy="rawMessage"
              :class="{ valid: !$v.message.rawMessage.$invalid, invalid: $v.message.rawMessage.$invalid }"
              v-model="$v.message.rawMessage.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="rawMessageContentType"
              id="message-rawMessageContentType"
              v-model="message.rawMessageContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.message.decryptedPayload')" for="message-decryptedPayload"
              >Decrypted Payload</label
            >
            <textarea
              class="form-control"
              name="decryptedPayload"
              id="message-decryptedPayload"
              data-cy="decryptedPayload"
              :class="{ valid: !$v.message.decryptedPayload.$invalid, invalid: $v.message.decryptedPayload.$invalid }"
              v-model="$v.message.decryptedPayload.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.message.devToSrv')" for="message-devToSrv">Dev To Srv</label>
            <input
              type="checkbox"
              class="form-check"
              name="devToSrv"
              id="message-devToSrv"
              data-cy="devToSrv"
              :class="{ valid: !$v.message.devToSrv.$invalid, invalid: $v.message.devToSrv.$invalid }"
              v-model="$v.message.devToSrv.$model"
              required
            />
            <div v-if="$v.message.devToSrv.$anyDirty && $v.message.devToSrv.$invalid">
              <small class="form-text text-danger" v-if="!$v.message.devToSrv.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.message.indexFieldValue01')" for="message-indexFieldValue01"
              >Index Field Value 01</label
            >
            <input
              type="text"
              class="form-control"
              name="indexFieldValue01"
              id="message-indexFieldValue01"
              data-cy="indexFieldValue01"
              :class="{ valid: !$v.message.indexFieldValue01.$invalid, invalid: $v.message.indexFieldValue01.$invalid }"
              v-model="$v.message.indexFieldValue01.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.message.indexFieldValue02')" for="message-indexFieldValue02"
              >Index Field Value 02</label
            >
            <input
              type="text"
              class="form-control"
              name="indexFieldValue02"
              id="message-indexFieldValue02"
              data-cy="indexFieldValue02"
              :class="{ valid: !$v.message.indexFieldValue02.$invalid, invalid: $v.message.indexFieldValue02.$invalid }"
              v-model="$v.message.indexFieldValue02.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.message.indexFieldValue03')" for="message-indexFieldValue03"
              >Index Field Value 03</label
            >
            <input
              type="text"
              class="form-control"
              name="indexFieldValue03"
              id="message-indexFieldValue03"
              data-cy="indexFieldValue03"
              :class="{ valid: !$v.message.indexFieldValue03.$invalid, invalid: $v.message.indexFieldValue03.$invalid }"
              v-model="$v.message.indexFieldValue03.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.message.indexFieldValue04')" for="message-indexFieldValue04"
              >Index Field Value 04</label
            >
            <input
              type="text"
              class="form-control"
              name="indexFieldValue04"
              id="message-indexFieldValue04"
              data-cy="indexFieldValue04"
              :class="{ valid: !$v.message.indexFieldValue04.$invalid, invalid: $v.message.indexFieldValue04.$invalid }"
              v-model="$v.message.indexFieldValue04.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.message.processingError')" for="message-processingError"
              >Processing Error</label
            >
            <textarea
              class="form-control"
              name="processingError"
              id="message-processingError"
              data-cy="processingError"
              :class="{ valid: !$v.message.processingError.$invalid, invalid: $v.message.processingError.$invalid }"
              v-model="$v.message.processingError.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.message.type')" for="message-type">Type</label>
            <select class="form-control" id="message-type" data-cy="type" name="type" v-model="message.type" required>
              <option v-if="!message.type" v-bind:value="null" selected></option>
              <option
                v-bind:value="message.type && messageTypeOption.id === message.type.id ? message.type : messageTypeOption"
                v-for="messageTypeOption in messageTypes"
                :key="messageTypeOption.id"
              >
                {{ messageTypeOption.name }}
              </option>
            </select>
          </div>
          <div v-if="$v.message.type.$anyDirty && $v.message.type.$invalid">
            <small class="form-text text-danger" v-if="!$v.message.type.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.message.device')" for="message-device">Device</label>
            <select class="form-control" id="message-device" data-cy="device" name="device" v-model="message.device" required>
              <option v-if="!message.device" v-bind:value="null" selected></option>
              <option
                v-bind:value="message.device && deviceOption.id === message.device.id ? message.device : deviceOption"
                v-for="deviceOption in devices"
                :key="deviceOption.id"
              >
                {{ deviceOption.visualId }}
              </option>
            </select>
          </div>
          <div v-if="$v.message.device.$anyDirty && $v.message.device.$invalid">
            <small class="form-text text-danger" v-if="!$v.message.device.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.message.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./message-update.component.ts"></script>
