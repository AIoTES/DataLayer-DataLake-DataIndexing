import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DataIntergrationSharedModule } from 'app/shared';
import {
    DataIndexComponent,
    DataIndexDetailComponent,
    DataIndexUpdateComponent,
    DataIndexDeletePopupComponent,
    DataIndexDeleteDialogComponent,
    dataIndexRoute,
    dataIndexPopupRoute
} from './';

const ENTITY_STATES = [...dataIndexRoute, ...dataIndexPopupRoute];

@NgModule({
    imports: [DataIntergrationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DataIndexComponent,
        DataIndexDetailComponent,
        DataIndexUpdateComponent,
        DataIndexDeleteDialogComponent,
        DataIndexDeletePopupComponent
    ],
    entryComponents: [DataIndexComponent, DataIndexUpdateComponent, DataIndexDeleteDialogComponent, DataIndexDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DataIntergrationDataIndexModule {}
