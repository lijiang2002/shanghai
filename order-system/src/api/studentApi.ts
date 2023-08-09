import {request} from "@/utils/request"


export function getListApi(data: any, sous: string) {
    return request({
        url: `/student/list/${data.page}/${data.pageSize}/${sous == null || sous == '' ? 'nu' : sous}`,
        method: 'get'
    })
}

export function deleteApi(data: any) {
    return request({
        url: `/student/${data}`,
        method: 'delete',
    })
}

export function putApi(data: any) {
    return request({
        url: `/student`,
        method: 'put',
        data
    })
}

export function getApi(data: any) {
    return request({
        url: `/student/e/${data}`,
        method: 'get',
    })
}
