import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DataIndex } from 'app/shared/model/data-index.model';
import { DataIndexService } from './data-index.service';
import { DataIndexComponent } from './data-index.component';
import { DataIndexDetailComponent } from './data-index-detail.component';
import { DataIndexUpdateComponent } from './data-index-update.component';
import { DataIndexDeletePopupComponent } from './data-index-delete-dialog.component';
import { IDataIndex } from 'app/shared/model/data-index.model';

@Injectable({ providedIn: 'root' })
export class DataIndexResolve implements Resolve<IDataIndex> {
    constructor(private service: DataIndexService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((dataIndex: HttpResponse<DataIndex>) => dataIndex.body));
        }
        return of(new DataIndex());
    }
}

export const dataIndexRoute: Routes = [
    {
        path: 'data-index',
        component: DataIndexComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataIndices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-index/:id/view',
        component: DataIndexDetailComponent,
        resolve: {
            dataIndex: DataIndexResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataIndices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-index/new',
        component: DataIndexUpdateComponent,
        resolve: {
            dataIndex: DataIndexResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataIndices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-index/:id/edit',
        component: DataIndexUpdateComponent,
        resolve: {
            dataIndex: DataIndexResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataIndices'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dataIndexPopupRoute: Routes = [
    {
        path: 'data-index/:id/delete',
        component: DataIndexDeletePopupComponent,
        resolve: {
            dataIndex: DataIndexResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataIndices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
