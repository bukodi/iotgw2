<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="iotgw2App.messageType.home.createOrEditLabel"
          data-cy="MessageTypeCreateUpdateHeading"
          v-text="$t('iotgw2App.messageType.home.createOrEditLabel')"
        >
          Create or edit a MessageType
        </h2>
        <div>
          <div class="form-group" v-if="messageType.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="messageType.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.messageType.typeCode')" for="message-type-typeCode">Type Code</label>
            <input
              type="number"
              class="form-control"
              name="typeCode"
              id="message-type-typeCode"
              data-cy="typeCode"
              :class="{ valid: !$v.messageType.typeCode.$invalid, invalid: $v.messageType.typeCode.$invalid }"
              v-model.number="$v.messageType.typeCode.$model"
              required
            />
            <div v-if="$v.messageType.typeCode.$anyDirty && $v.messageType.typeCode.$invalid">
              <small class="form-text text-danger" v-if="!$v.messageType.typeCode.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.messageType.typeCode.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.messageType.devToSrv')" for="message-type-devToSrv">Dev To Srv</label>
            <input
              type="checkbox"
              class="form-check"
              name="devToSrv"
              id="message-type-devToSrv"
              data-cy="devToSrv"
              :class="{ valid: !$v.messageType.devToSrv.$invalid, invalid: $v.messageType.devToSrv.$invalid }"
              v-model="$v.messageType.devToSrv.$model"
              required
            />
            <div v-if="$v.messageType.devToSrv.$anyDirty && $v.messageType.devToSrv.$invalid">
              <small class="form-text text-danger" v-if="!$v.messageType.devToSrv.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.messageType.name')" for="message-type-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="message-type-name"
              data-cy="name"
              :class="{ valid: !$v.messageType.name.$invalid, invalid: $v.messageType.name.$invalid }"
              v-model="$v.messageType.name.$model"
              required
            />
            <div v-if="$v.messageType.name.$anyDirty && $v.messageType.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.messageType.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.messageType.description')" for="message-type-description"
              >Description</label
            >
            <input
              type="text"
              class="form-control"
              name="description"
              id="message-type-description"
              data-cy="description"
              :class="{ valid: !$v.messageType.description.$invalid, invalid: $v.messageType.description.$invalid }"
              v-model="$v.messageType.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.messageType.timeout')" for="message-type-timeout">Timeout</label>
            <input
              type="text"
              class="form-control"
              name="timeout"
              id="message-type-timeout"
              data-cy="timeout"
              :class="{ valid: !$v.messageType.timeout.$invalid, invalid: $v.messageType.timeout.$invalid }"
              v-model="$v.messageType.timeout.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.messageType.retentionTime')" for="message-type-retentionTime"
              >Retention Time</label
            >
            <input
              type="text"
              class="form-control"
              name="retentionTime"
              id="message-type-retentionTime"
              data-cy="retentionTime"
              :class="{ valid: !$v.messageType.retentionTime.$invalid, invalid: $v.messageType.retentionTime.$invalid }"
              v-model="$v.messageType.retentionTime.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.messageType.indexFieldName01')" for="message-type-indexFieldName01"
              >Index Field Name 01</label
            >
            <input
              type="text"
              class="form-control"
              name="indexFieldName01"
              id="message-type-indexFieldName01"
              data-cy="indexFieldName01"
              :class="{ valid: !$v.messageType.indexFieldName01.$invalid, invalid: $v.messageType.indexFieldName01.$invalid }"
              v-model="$v.messageType.indexFieldName01.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.messageType.indexFieldName02')" for="message-type-indexFieldName02"
              >Index Field Name 02</label
            >
            <input
              type="text"
              class="form-control"
              name="indexFieldName02"
              id="message-type-indexFieldName02"
              data-cy="indexFieldName02"
              :class="{ valid: !$v.messageType.indexFieldName02.$invalid, invalid: $v.messageType.indexFieldName02.$invalid }"
              v-model="$v.messageType.indexFieldName02.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.messageType.indexFieldName03')" for="message-type-indexFieldName03"
              >Index Field Name 03</label
            >
            <input
              type="text"
              class="form-control"
              name="indexFieldName03"
              id="message-type-indexFieldName03"
              data-cy="indexFieldName03"
              :class="{ valid: !$v.messageType.indexFieldName03.$invalid, invalid: $v.messageType.indexFieldName03.$invalid }"
              v-model="$v.messageType.indexFieldName03.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.messageType.indexFieldName04')" for="message-type-indexFieldName04"
              >Index Field Name 04</label
            >
            <input
              type="text"
              class="form-control"
              name="indexFieldName04"
              id="message-type-indexFieldName04"
              data-cy="indexFieldName04"
              :class="{ valid: !$v.messageType.indexFieldName04.$invalid, invalid: $v.messageType.indexFieldName04.$invalid }"
              v-model="$v.messageType.indexFieldName04.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.messageType.readAuthPattern')" for="message-type-readAuthPattern"
              >Read Auth Pattern</label
            >
            <input
              type="text"
              class="form-control"
              name="readAuthPattern"
              id="message-type-readAuthPattern"
              data-cy="readAuthPattern"
              :class="{ valid: !$v.messageType.readAuthPattern.$invalid, invalid: $v.messageType.readAuthPattern.$invalid }"
              v-model="$v.messageType.readAuthPattern.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.messageType.writeAuthPattern')" for="message-type-writeAuthPattern"
              >Write Auth Pattern</label
            >
            <input
              type="text"
              class="form-control"
              name="writeAuthPattern"
              id="message-type-writeAuthPattern"
              data-cy="writeAuthPattern"
              :class="{ valid: !$v.messageType.writeAuthPattern.$invalid, invalid: $v.messageType.writeAuthPattern.$invalid }"
              v-model="$v.messageType.writeAuthPattern.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.messageType.messageProcessor')" for="message-type-messageProcessor"
              >Message Processor</label
            >
            <select
              class="form-control"
              id="message-type-messageProcessor"
              data-cy="messageProcessor"
              name="messageProcessor"
              v-model="messageType.messageProcessor"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  messageType.messageProcessor && processorOption.id === messageType.messageProcessor.id
                    ? messageType.messageProcessor
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
            <label class="form-control-label" v-text="$t('iotgw2App.messageType.timeoutProcessor')" for="message-type-timeoutProcessor"
              >Timeout Processor</label
            >
            <select
              class="form-control"
              id="message-type-timeoutProcessor"
              data-cy="timeoutProcessor"
              name="timeoutProcessor"
              v-model="messageType.timeoutProcessor"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  messageType.timeoutProcessor && processorOption.id === messageType.timeoutProcessor.id
                    ? messageType.timeoutProcessor
                    : processorOption
                "
                v-for="processorOption in processors"
                :key="processorOption.id"
              >
                {{ processorOption.name }}
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
            :disabled="$v.messageType.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./message-type-update.component.ts"></script>
