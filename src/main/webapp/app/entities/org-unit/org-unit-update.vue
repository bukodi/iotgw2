<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="iotgw2App.orgUnit.home.createOrEditLabel"
          data-cy="OrgUnitCreateUpdateHeading"
          v-text="$t('iotgw2App.orgUnit.home.createOrEditLabel')"
        >
          Create or edit a OrgUnit
        </h2>
        <div>
          <div class="form-group" v-if="orgUnit.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="orgUnit.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.orgUnit.name')" for="org-unit-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="org-unit-name"
              data-cy="name"
              :class="{ valid: !$v.orgUnit.name.$invalid, invalid: $v.orgUnit.name.$invalid }"
              v-model="$v.orgUnit.name.$model"
              required
            />
            <div v-if="$v.orgUnit.name.$anyDirty && $v.orgUnit.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.orgUnit.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.orgUnit.description')" for="org-unit-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="org-unit-description"
              data-cy="description"
              :class="{ valid: !$v.orgUnit.description.$invalid, invalid: $v.orgUnit.description.$invalid }"
              v-model="$v.orgUnit.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.orgUnit.readAuthPattern')" for="org-unit-readAuthPattern"
              >Read Auth Pattern</label
            >
            <input
              type="text"
              class="form-control"
              name="readAuthPattern"
              id="org-unit-readAuthPattern"
              data-cy="readAuthPattern"
              :class="{ valid: !$v.orgUnit.readAuthPattern.$invalid, invalid: $v.orgUnit.readAuthPattern.$invalid }"
              v-model="$v.orgUnit.readAuthPattern.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.orgUnit.writeAuthPattern')" for="org-unit-writeAuthPattern"
              >Write Auth Pattern</label
            >
            <input
              type="text"
              class="form-control"
              name="writeAuthPattern"
              id="org-unit-writeAuthPattern"
              data-cy="writeAuthPattern"
              :class="{ valid: !$v.orgUnit.writeAuthPattern.$invalid, invalid: $v.orgUnit.writeAuthPattern.$invalid }"
              v-model="$v.orgUnit.writeAuthPattern.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('iotgw2App.orgUnit.parent')" for="org-unit-parent">Parent</label>
            <select class="form-control" id="org-unit-parent" data-cy="parent" name="parent" v-model="orgUnit.parent">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="orgUnit.parent && orgUnitOption.id === orgUnit.parent.id ? orgUnit.parent : orgUnitOption"
                v-for="orgUnitOption in orgUnits"
                :key="orgUnitOption.id"
              >
                {{ orgUnitOption.name }}
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
            :disabled="$v.orgUnit.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./org-unit-update.component.ts"></script>
