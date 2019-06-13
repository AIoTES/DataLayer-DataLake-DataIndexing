import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { DataIntegrationSharedLibsModule, DataIntegrationSharedCommonModule, SimLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [DataIntegrationSharedLibsModule, DataIntegrationSharedCommonModule],
    declarations: [SimLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [SimLoginModalComponent],
    exports: [DataIntegrationSharedCommonModule, SimLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DataIntegrationSharedModule {}
