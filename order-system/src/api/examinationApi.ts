import {request} from "@/utils/request"


export function examinationApi(data: any) {
    return request({
        url: `/examination/${data.name}/${data.identity}`,
        method: 'post',
        data
    })
}

export function getApi(data: any) {
    return request({
        url: `/examination/${data.page}/${data.pageSize}`,
        method: 'get',
    })
}

export function infoApi(data: any) {
    return request({
        url: `/examination/info/${data}`,
        method: 'get',
    })
}

export function putApi(data: any) {
    return request({
        url: `/examination/`,
        method: 'put',
        data
    })
}
