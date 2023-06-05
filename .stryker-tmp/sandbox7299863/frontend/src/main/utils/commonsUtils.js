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

export function daysElapsed(start, end){
    let startTime = new Date(start).getTime();
    let endTime = new Date(end).getTime();
    var diff = Math.round((endTime - startTime)/(86400000));
    return (diff > 0)? diff : 0;
}
