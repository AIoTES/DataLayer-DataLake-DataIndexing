/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DataIntegrationTestModule } from '../../../test.module';
import { DataIndexDetailComponent } from 'app/entities/data-index/data-index-detail.component';
import { DataIndex } from 'app/shared/model/data-index.model';

describe('Component Tests', () => {
    describe('DataIndex Management Detail Component', () => {
        let comp: DataIndexDetailComponent;
        let fixture: ComponentFixture<DataIndexDetailComponent>;
        const route = ({ data: of({ dataIndex: new DataIndex('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DataIntegrationTestModule],
                declarations: [DataIndexDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DataIndexDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DataIndexDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dataIndex).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
