import { toast } from "react-toastify";

export function onDeleteSuccess(message) {
    console.log(message);
    toast(message);
}

export function cellToAxiosParamsDelete(cell) {
    return {
        url: "/api/commons",
        method: "DELETE",
        params: {
            id: cell.row.values["commons.id"]
        }
    }
}

export function daysElapsed(start){
    let startTime = new Date(start).getTime();
    var diff = Math.round((Date.now() - startTime)/(86400000));
    return diff;
}