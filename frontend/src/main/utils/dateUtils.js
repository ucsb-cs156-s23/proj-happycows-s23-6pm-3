
const padWithZero = (n) => { return n < 10 ? '0' + n : n; }

const timestampToDate = (timestamp) => {
    var date = new Date(timestamp);
    return (date.getFullYear() + "-" + (padWithZero(date.getMonth()+1)) + "-" + padWithZero(date.getDate()));
}

export function gameInProgress(startingDate, lastDate){
    return((Date(startingDate).getTime() <= Date(endingDate).getTime())? true : false)
}

export {timestampToDate, padWithZero, gameInProgress};