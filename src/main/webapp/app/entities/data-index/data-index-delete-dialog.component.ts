import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SimEventManager } from 'ng-simlife';

import { IDataIndex } from 'app/shared/model/data-index.model';
import { DataIndexService } from './data-index.service';

@Component({
    selector: 'sim-data-index-delete-dialog',
    templateUrl: './data-index-delete-dialog.component.html'
})
export class DataIndexDeleteDialogComponent {
    dataIndex: IDataIndex;

    constructor(private dataIndexService: DataIndexService, public activeModal: NgbActiveModal, private eventManager: SimEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.dataIndexService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dataIndexListModification',
                content: 'Deleted an dataIndex'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'sim-data-index-delete-popup',
    template: ''
})
export class DataIndexDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dataIndex }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DataIndexDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.dataIndex = dataIndex;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
