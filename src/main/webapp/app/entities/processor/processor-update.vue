<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="iotgw2App.processor.home.createOrEditLabel"
          data-cy="ProcessorCreateUpdateHeading"
          v-text="$t('iotgw2App.processor.home.createOrEditLabel')"
        >
          Create or edit a Processor
        </h2>
        <div>
          <div class="form-group" v-if="processor.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="processor.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.processor.name')" for="processor-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="processor-name"
              data-cy="name"
              :class="{ valid: !$v.processor.name.$invalid, invalid: $v.processor.name.$invalid }"
              v-model="$v.processor.name.$model"
              required
            />
            <div v-if="$v.processor.name.$anyDirty && $v.processor.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.processor.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.processor.example')" for="processor-example">Example</label>
            <input
              type="checkbox"
              class="form-check"
              name="example"
              id="processor-example"
              data-cy="example"
              :class="{ valid: !$v.processor.example.$invalid, invalid: $v.processor.example.$invalid }"
              v-model="$v.processor.example.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.processor.description')" for="processor-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="processor-description"
              data-cy="description"
              :class="{ valid: !$v.processor.description.$invalid, invalid: $v.processor.description.$invalid }"
              v-model="$v.processor.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.processor.processorIterface')" for="processor-processorIterface"
              >Processor Iterface</label
            >
            <select
              class="form-control"
              name="processorIterface"
              :class="{ valid: !$v.processor.processorIterface.$invalid, invalid: $v.processor.processorIterface.$invalid }"
              v-model="$v.processor.processorIterface.$model"
              id="processor-processorIterface"
              data-cy="processorIterface"
              required
            >
              <option value="BeforeSendToDevice" v-bind:label="$t('iotgw2App.ProcessorInterface.BeforeSendToDevice')">
                BeforeSendToDevice
              </option>
              <option value="AfterRecivedFromDevice" v-bind:label="$t('iotgw2App.ProcessorInterface.AfterRecivedFromDevice')">
                AfterRecivedFromDevice
              </option>
              <option value="OnMessageTimeout" v-bind:label="$t('iotgw2App.ProcessorInterface.OnMessageTimeout')">OnMessageTimeout</option>
              <option value="OnMessageSendError" v-bind:label="$t('iotgw2App.ProcessorInterface.OnMessageSendError')">
                OnMessageSendError
              </option>
              <option value="OnExternalAPICall" v-bind:label="$t('iotgw2App.ProcessorInterface.OnExternalAPICall')">
                OnExternalAPICall
              </option>
            </select>
            <div v-if="$v.processor.processorIterface.$anyDirty && $v.processor.processorIterface.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.processor.processorIterface.required"
                v-text="$t('entity.validation.required')"
              >
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.processor.implType')" for="processor-implType">Impl Type</label>
            <select
              class="form-control"
              name="implType"
              :class="{ valid: !$v.processor.implType.$invalid, invalid: $v.processor.implType.$invalid }"
              v-model="$v.processor.implType.$model"
              id="processor-implType"
              data-cy="implType"
              required
            >
              <option value="JavaCall" v-bind:label="$t('iotgw2App.ImplemntationType.JavaCall')">JavaCall</option>
              <option value="GroovyScript" v-bind:label="$t('iotgw2App.ImplemntationType.GroovyScript')">GroovyScript</option>
              <option value="JavaScript" v-bind:label="$t('iotgw2App.ImplemntationType.JavaScript')">JavaScript</option>
              <option value="Blockly" v-bind:label="$t('iotgw2App.ImplemntationType.Blockly')">Blockly</option>
              <option value="WebHook" v-bind:label="$t('iotgw2App.ImplemntationType.WebHook')">WebHook</option>
            </select>
            <div v-if="$v.processor.implType.$anyDirty && $v.processor.implType.$invalid">
              <small class="form-text text-danger" v-if="!$v.processor.implType.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.processor.param01')" for="processor-param01">Param 01</label>
            <input
              type="text"
              class="form-control"
              name="param01"
              id="processor-param01"
              data-cy="param01"
              :class="{ valid: !$v.processor.param01.$invalid, invalid: $v.processor.param01.$invalid }"
              v-model="$v.processor.param01.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.processor.param02')" for="processor-param02">Param 02</label>
            <input
              type="text"
              class="form-control"
              name="param02"
              id="processor-param02"
              data-cy="param02"
              :class="{ valid: !$v.processor.param02.$invalid, invalid: $v.processor.param02.$invalid }"
              v-model="$v.processor.param02.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.processor.source')" for="processor-source">Source</label>
            <textarea
              class="form-control"
              name="source"
              id="processor-source"
              data-cy="source"
              :class="{ valid: !$v.processor.source.$invalid, invalid: $v.processor.source.$invalid }"
              v-model="$v.processor.source.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.processor.signerName')" for="processor-signerName">Signer Name</label>
            <input
              type="text"
              class="form-control"
              name="signerName"
              id="processor-signerName"
              data-cy="signerName"
              :class="{ valid: !$v.processor.signerName.$invalid, invalid: $v.processor.signerName.$invalid }"
              v-model="$v.processor.signerName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.processor.signature')" for="processor-signature">Signature</label>
            <div>
              <div v-if="processor.signature" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(processor.signatureContentType, processor.signature)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ processor.signatureContentType }}, {{ byteSize(processor.signature) }}</span>
                <button
                  type="button"
                  v-on:click="
                    processor.signature = null;
                    processor.signatureContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_signature"
                id="file_signature"
                data-cy="signature"
                v-on:change="setFileData($event, processor, 'signature', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="signature"
              id="processor-signature"
              data-cy="signature"
              :class="{ valid: !$v.processor.signature.$invalid, invalid: $v.processor.signature.$invalid }"
              v-model="$v.processor.signature.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="signatureContentType"
              id="processor-signatureContentType"
              v-model="processor.signatureContentType"
            />
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
            :disabled="$v.processor.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./processor-update.component.ts"></script>
