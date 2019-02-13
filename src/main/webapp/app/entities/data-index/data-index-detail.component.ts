import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDataIndex } from 'app/shared/model/data-index.model';

@Component({
    selector: 'sim-data-index-detail',
    templateUrl: './data-index-detail.component.html'
})
export class DataIndexDetailComponent implements OnInit {
    dataIndex: IDataIndex;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dataIndex }) => {
            this.dataIndex = dataIndex;
        });
    }

    previousState() {
        window.history.back();
    }
}
