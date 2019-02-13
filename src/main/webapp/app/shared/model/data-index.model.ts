export interface IDataIndex {
    id?: string;
    indexID?: string;
    indexBy?: string;
}

export class DataIndex implements IDataIndex {
    constructor(public id?: string, public indexID?: string, public indexBy?: string) {}
}
