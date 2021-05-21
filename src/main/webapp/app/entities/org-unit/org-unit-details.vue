<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="orgUnit">
        <h2 class="jh-entity-heading" data-cy="orgUnitDetailsHeading">
          <span v-text="$t('iotgw2App.orgUnit.detail.title')">OrgUnit</span> {{ orgUnit.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('iotgw2App.orgUnit.name')">Name</span>
          </dt>
          <dd>
            <span>{{ orgUnit.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.orgUnit.description')">Description</span>
          </dt>
          <dd>
            <span>{{ orgUnit.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.orgUnit.readAuthPattern')">Read Auth Pattern</span>
          </dt>
          <dd>
            <span>{{ orgUnit.readAuthPattern }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.orgUnit.writeAuthPattern')">Write Auth Pattern</span>
          </dt>
          <dd>
            <span>{{ orgUnit.writeAuthPattern }}</span>
          </dd>
          <dt>
            <span v-text="$t('iotgw2App.orgUnit.parent')">Parent</span>
          </dt>
          <dd>
            <div v-if="orgUnit.parent">
              <router-link :to="{ name: 'OrgUnitView', params: { orgUnitId: orgUnit.parent.id } }">{{ orgUnit.parent.name }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="orgUnit.id" :to="{ name: 'OrgUnitEdit', params: { orgUnitId: orgUnit.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./org-unit-details.component.ts"></script>
