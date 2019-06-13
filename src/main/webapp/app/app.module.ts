import './vendor.ts';

import { NgModule, Injector } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Ng2Webstorage, LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { SimEventManager } from 'ng-simlife';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { DataIntegrationSharedModule } from 'app/shared';
import { DataIntegrationCoreModule } from 'app/core';
import { DataIntegrationAppRoutingModule } from './app-routing.module';
import { DataIntegrationHomeModule } from './home/home.module';
import { DataIntegrationAccountModule } from './account/account.module';
import { DataIntegrationEntityModule } from './entities/entity.module';
// simlife-needle-angular-add-module-import Simlife will add new module here
import { SimMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ErrorComponent } from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        DataIntegrationAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'sim', separator: '-' }),
        DataIntegrationSharedModule,
        DataIntegrationCoreModule,
        DataIntegrationHomeModule,
        DataIntegrationAccountModule,
        DataIntegrationEntityModule
        // simlife-needle-angular-add-module Simlife will add new module here
    ],
    declarations: [SimMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
            deps: [LocalStorageService, SessionStorageService]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [Injector]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [SimEventManager]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [Injector]
        }
    ],
    bootstrap: [SimMainComponent]
})
export class DataIntegrationAppModule {}
