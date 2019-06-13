import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataIntegrationSharedModule } from 'app/shared';
/* simlife-needle-add-admin-module-import - Simlife will add admin modules imports here */

import {
    adminState,
    AuditsComponent,
    UserMgmtComponent,
    UserMgmtDetailComponent,
    UserMgmtUpdateComponent,
    UserMgmtDeleteDialogComponent,
    LogsComponent,
    SimMetricsMonitoringModalComponent,
    SimMetricsMonitoringComponent,
    SimHealthModalComponent,
    SimHealthCheckComponent,
    SimConfigurationComponent,
    SimDocsComponent
} from './';

@NgModule({
    imports: [
        DataIntegrationSharedModule,
        RouterModule.forChild(adminState)
        /* simlife-needle-add-admin-module - Simlife will add admin modules here */
    ],
    declarations: [
        AuditsComponent,
        UserMgmtComponent,
        UserMgmtDetailComponent,
        UserMgmtUpdateComponent,
        UserMgmtDeleteDialogComponent,
        LogsComponent,
        SimConfigurationComponent,
        SimHealthCheckComponent,
        SimHealthModalComponent,
        SimDocsComponent,
        SimMetricsMonitoringComponent,
        SimMetricsMonitoringModalComponent
    ],
    entryComponents: [UserMgmtDeleteDialogComponent, SimHealthModalComponent, SimMetricsMonitoringModalComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DataIntegrationAdminModule {}
