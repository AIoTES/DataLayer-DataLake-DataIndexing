import { NgModule } from '@angular/core';

import { DataIntergrationSharedLibsModule, SimAlertComponent, SimAlertErrorComponent } from './';

@NgModule({
    imports: [DataIntergrationSharedLibsModule],
    declarations: [SimAlertComponent, SimAlertErrorComponent],
    exports: [DataIntergrationSharedLibsModule, SimAlertComponent, SimAlertErrorComponent]
})
export class DataIntergrationSharedCommonModule {}
