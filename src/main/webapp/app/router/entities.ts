import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const OrgUnit = () => import('@/entities/org-unit/org-unit.vue');
// prettier-ignore
const OrgUnitUpdate = () => import('@/entities/org-unit/org-unit-update.vue');
// prettier-ignore
const OrgUnitDetails = () => import('@/entities/org-unit/org-unit-details.vue');
// prettier-ignore
const DeviceType = () => import('@/entities/device-type/device-type.vue');
// prettier-ignore
const DeviceTypeUpdate = () => import('@/entities/device-type/device-type-update.vue');
// prettier-ignore
const DeviceTypeDetails = () => import('@/entities/device-type/device-type-details.vue');
// prettier-ignore
const Device = () => import('@/entities/device/device.vue');
// prettier-ignore
const DeviceUpdate = () => import('@/entities/device/device-update.vue');
// prettier-ignore
const DeviceDetails = () => import('@/entities/device/device-details.vue');
// prettier-ignore
const KeyPair = () => import('@/entities/key-pair/key-pair.vue');
// prettier-ignore
const KeyPairUpdate = () => import('@/entities/key-pair/key-pair-update.vue');
// prettier-ignore
const KeyPairDetails = () => import('@/entities/key-pair/key-pair-details.vue');
// prettier-ignore
const MessageType = () => import('@/entities/message-type/message-type.vue');
// prettier-ignore
const MessageTypeUpdate = () => import('@/entities/message-type/message-type-update.vue');
// prettier-ignore
const MessageTypeDetails = () => import('@/entities/message-type/message-type-details.vue');
// prettier-ignore
const Message = () => import('@/entities/message/message.vue');
// prettier-ignore
const MessageUpdate = () => import('@/entities/message/message-update.vue');
// prettier-ignore
const MessageDetails = () => import('@/entities/message/message-details.vue');
// prettier-ignore
const Processor = () => import('@/entities/processor/processor.vue');
// prettier-ignore
const ProcessorUpdate = () => import('@/entities/processor/processor-update.vue');
// prettier-ignore
const ProcessorDetails = () => import('@/entities/processor/processor-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/org-unit',
    name: 'OrgUnit',
    component: OrgUnit,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/org-unit/new',
    name: 'OrgUnitCreate',
    component: OrgUnitUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/org-unit/:orgUnitId/edit',
    name: 'OrgUnitEdit',
    component: OrgUnitUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/org-unit/:orgUnitId/view',
    name: 'OrgUnitView',
    component: OrgUnitDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/device-type',
    name: 'DeviceType',
    component: DeviceType,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/device-type/new',
    name: 'DeviceTypeCreate',
    component: DeviceTypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/device-type/:deviceTypeId/edit',
    name: 'DeviceTypeEdit',
    component: DeviceTypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/device-type/:deviceTypeId/view',
    name: 'DeviceTypeView',
    component: DeviceTypeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/device',
    name: 'Device',
    component: Device,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/device/new',
    name: 'DeviceCreate',
    component: DeviceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/device/:deviceId/edit',
    name: 'DeviceEdit',
    component: DeviceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/device/:deviceId/view',
    name: 'DeviceView',
    component: DeviceDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/key-pair',
    name: 'KeyPair',
    component: KeyPair,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/key-pair/new',
    name: 'KeyPairCreate',
    component: KeyPairUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/key-pair/:keyPairId/edit',
    name: 'KeyPairEdit',
    component: KeyPairUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/key-pair/:keyPairId/view',
    name: 'KeyPairView',
    component: KeyPairDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/message-type',
    name: 'MessageType',
    component: MessageType,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/message-type/new',
    name: 'MessageTypeCreate',
    component: MessageTypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/message-type/:messageTypeId/edit',
    name: 'MessageTypeEdit',
    component: MessageTypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/message-type/:messageTypeId/view',
    name: 'MessageTypeView',
    component: MessageTypeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/message',
    name: 'Message',
    component: Message,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/message/new',
    name: 'MessageCreate',
    component: MessageUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/message/:messageId/edit',
    name: 'MessageEdit',
    component: MessageUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/message/:messageId/view',
    name: 'MessageView',
    component: MessageDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/processor',
    name: 'Processor',
    component: Processor,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/processor/new',
    name: 'ProcessorCreate',
    component: ProcessorUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/processor/:processorId/edit',
    name: 'ProcessorEdit',
    component: ProcessorUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/processor/:processorId/view',
    name: 'ProcessorView',
    component: ProcessorDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
