import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDataIndex } from 'app/shared/model/data-index.model';
import { DataIndexService } from './data-index.service';

@Component({
    selector: 'sim-data-index-update',
    templateUrl: './data-index-update.component.html'
})
export class DataIndexUpdateComponent implements OnInit {
    private _dataIndex: IDataIndex;
    isSaving: boolean;

    constructor(private dataIndexService: DataIndexService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dataIndex }) => {
            this.dataIndex = dataIndex;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dataIndex.id !== undefined) {
            this.subscribeToSaveResponse(this.dataIndexService.update(this.dataIndex));
        } else {
            this.subscribeToSaveResponse(this.dataIndexService.create(this.dataIndex));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDataIndex>>) {
        result.subscribe((res: HttpResponse<IDataIndex>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get dataIndex() {
        return this._dataIndex;
    }

    set dataIndex(dataIndex: IDataIndex) {
        this._dataIndex = dataIndex;
    }
}
