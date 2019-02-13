import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDataIndex } from 'app/shared/model/data-index.model';

type EntityResponseType = HttpResponse<IDataIndex>;
type EntityArrayResponseType = HttpResponse<IDataIndex[]>;

@Injectable({ providedIn: 'root' })
export class DataIndexService {
    private resourceUrl = SERVER_API_URL + 'api/data-indices';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/data-indices';

    constructor(private http: HttpClient) {}

    create(dataIndex: IDataIndex): Observable<EntityResponseType> {
        return this.http.post<IDataIndex>(this.resourceUrl, dataIndex, { observe: 'response' });
    }

    update(dataIndex: IDataIndex): Observable<EntityResponseType> {
        return this.http.put<IDataIndex>(this.resourceUrl, dataIndex, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IDataIndex>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDataIndex[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDataIndex[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
