import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Route } from '@angular/router';
import { SimPaginationUtil, SimResolvePagingParams } from 'ng-simlife';

import { AuditsComponent } from './audits.component';

export const auditsRoute: Route = {
    path: 'audits',
    component: AuditsComponent,
    resolve: {
        pagingParams: SimResolvePagingParams
    },
    data: {
        pageTitle: 'Audits',
        defaulSort: 'auditEventDate,desc'
    }
};
