export interface TableDefn {
    displayName?: string;
    mappingName?: string;
    type?: TableType;
    sort?: boolean;
    width?: number;
    checkEnabled?: boolean;
}

export enum TableType {
    number = 1,
    string = 2,
    date = 3,
    button = 4
}

export enum TableAction {
    edit = 1,
    delete = 2,
    view = 3,
    rowCheck = 4,
    rowUnCheck = 5,
    download = 6,
    rowClick = 7,
    request = 8
}


export interface TableComponentResp {
    action?: TableAction;
    dataRow?: any;
    index: number;
}
