/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import DeviceService from '@/entities/device/device.service';
import { Device } from '@/shared/model/device.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Device Service', () => {
    let service: DeviceService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new DeviceService();
      currentDate = new Date();
      elemDefault = new Device(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            enrollmentTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Device', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            enrollmentTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            enrollmentTime: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Device', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Device', async () => {
        const returnedFromService = Object.assign(
          {
            visualId: 'BBBBBB',
            description: 'BBBBBB',
            enrollmentCode: 'BBBBBB',
            enrollmentTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            stateFieldValue01: 'BBBBBB',
            stateFieldValue02: 'BBBBBB',
            stateFieldValue03: 'BBBBBB',
            stateFieldValue04: 'BBBBBB',
            lastError: 'BBBBBB',
            readAuthPattern: 'BBBBBB',
            writeAuthPattern: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            enrollmentTime: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Device', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Device', async () => {
        const patchObject = Object.assign(
          {
            enrollmentCode: 'BBBBBB',
            stateFieldValue02: 'BBBBBB',
            stateFieldValue03: 'BBBBBB',
            stateFieldValue04: 'BBBBBB',
            writeAuthPattern: 'BBBBBB',
          },
          new Device()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            enrollmentTime: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Device', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Device', async () => {
        const returnedFromService = Object.assign(
          {
            visualId: 'BBBBBB',
            description: 'BBBBBB',
            enrollmentCode: 'BBBBBB',
            enrollmentTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            stateFieldValue01: 'BBBBBB',
            stateFieldValue02: 'BBBBBB',
            stateFieldValue03: 'BBBBBB',
            stateFieldValue04: 'BBBBBB',
            lastError: 'BBBBBB',
            readAuthPattern: 'BBBBBB',
            writeAuthPattern: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            enrollmentTime: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Device', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Device', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Device', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
