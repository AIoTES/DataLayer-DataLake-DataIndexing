import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { DataIntergrationSharedLibsModule, DataIntergrationSharedCommonModule, SimLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [DataIntergrationSharedLibsModule, DataIntergrationSharedCommonModule],
    declarations: [SimLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [SimLoginModalComponent],
    exports: [DataIntergrationSharedCommonModule, SimLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DataIntergrationSharedModule {}
