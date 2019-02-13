/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DataIntergrationTestModule } from '../../../test.module';
import { DataIndexUpdateComponent } from 'app/entities/data-index/data-index-update.component';
import { DataIndexService } from 'app/entities/data-index/data-index.service';
import { DataIndex } from 'app/shared/model/data-index.model';

describe('Component Tests', () => {
    describe('DataIndex Management Update Component', () => {
        let comp: DataIndexUpdateComponent;
        let fixture: ComponentFixture<DataIndexUpdateComponent>;
        let service: DataIndexService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DataIntergrationTestModule],
                declarations: [DataIndexUpdateComponent]
            })
                .overrideTemplate(DataIndexUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DataIndexUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataIndexService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DataIndex('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dataIndex = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DataIndex();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dataIndex = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
