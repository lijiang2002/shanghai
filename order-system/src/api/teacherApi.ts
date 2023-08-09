import {request} from "@/utils/request"


export function teacherApi(data: any) {
    return request({
        url: `/teacher/one`,
        method: 'post',
        data
    })
}

export function getListApi(data: any,name:string|null) {
    return request({
        url: `/teacher/list/${data.page}/${data.pageSize}/${name==null||name==''?'nu':name}`,
        method: 'get'
    })
}

export function updateApi(data: any) {
    return request({
        url: `/teacher/`,
        method: 'put',
        data
    })
}
export function deleteApi(data: any) {
    return request({
        url: `/teacher/${data}`,
        method: 'delete',
    })
}
export function saveApi(data: any) {
    return request({
        url: `/teacher`,
        method: 'post',
        data
    })
}
