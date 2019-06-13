import { NgModule } from '@angular/core';

import { DataIntegrationSharedLibsModule, SimAlertComponent, SimAlertErrorComponent } from './';

@NgModule({
    imports: [DataIntegrationSharedLibsModule],
    declarations: [SimAlertComponent, SimAlertErrorComponent],
    exports: [DataIntegrationSharedLibsModule, SimAlertComponent, SimAlertErrorComponent]
})
export class DataIntegrationSharedCommonModule {}
