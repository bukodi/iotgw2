<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="processor">
        <h2 class="jh-entity-heading" data-cy="processorDetailsHeading">
          <span v-text="$t('iotgw2App.processor.detail.title')">Processor</span> {{ processor.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('iotgw2App.processor.name')">Name</span>
          </dt>
          <dd>
            <span>{{ processor.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.processor.example')">Example</span>
          </dt>
          <dd>
            <span>{{ processor.example }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.processor.description')">Description</span>
          </dt>
          <dd>
            <span>{{ processor.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.processor.processorIterface')">Processor Iterface</span>
          </dt>
          <dd>
            <span v-text="$t('iotgw2App.ProcessorInterface.' + processor.processorIterface)">{{ processor.processorIterface }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.processor.implType')">Impl Type</span>
          </dt>
          <dd>
            <span v-text="$t('iotgw2App.ImplemntationType.' + processor.implType)">{{ processor.implType }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.processor.param01')">Param 01</span>
          </dt>
          <dd>
            <span>{{ processor.param01 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.processor.param02')">Param 02</span>
          </dt>
          <dd>
            <span>{{ processor.param02 }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.processor.source')">Source</span>
          </dt>
          <dd>
            <span>{{ processor.source }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.processor.signerName')">Signer Name</span>
          </dt>
          <dd>
            <span>{{ processor.signerName }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.processor.signature')">Signature</span>
          </dt>
          <dd>
            <div v-if="processor.signature">
              <a v-on:click="openFile(processor.signatureContentType, processor.signature)" v-text="$t('entity.action.open')">open</a>
              {{ processor.signatureContentType }}, {{ byteSize(processor.signature) }}
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="processor.id"
          :to="{ name: 'ProcessorEdit', params: { processorId: processor.id } }"
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

<script lang="ts" src="./processor-details.component.ts"></script>
