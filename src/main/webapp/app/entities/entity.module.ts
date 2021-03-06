import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { DataIntegrationDataIndexModule } from './data-index/data-index.module';
/* simlife-needle-add-entity-module-import - Simlife will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        DataIntegrationDataIndexModule,
        /* simlife-needle-add-entity-module - Simlife will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DataIntegrationEntityModule {}
