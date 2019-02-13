/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { SimEventManager } from 'ng-simlife';

import { DataIntergrationTestModule } from '../../../test.module';
import { DataIndexDeleteDialogComponent } from 'app/entities/data-index/data-index-delete-dialog.component';
import { DataIndexService } from 'app/entities/data-index/data-index.service';

describe('Component Tests', () => {
    describe('DataIndex Management Delete Component', () => {
        let comp: DataIndexDeleteDialogComponent;
        let fixture: ComponentFixture<DataIndexDeleteDialogComponent>;
        let service: DataIndexService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DataIntergrationTestModule],
                declarations: [DataIndexDeleteDialogComponent]
            })
                .overrideTemplate(DataIndexDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DataIndexDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataIndexService);
            mockEventManager = fixture.debugElement.injector.get(SimEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete('123');
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith('123');
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
