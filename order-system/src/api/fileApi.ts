import {request} from "@/utils/request"


export function getListApi() {
    return request({
        url: `/upload`,
        method: 'get'
    })
}

export function putApi(data: any) {
    return request({
        url: `/upload/${data}`,
        method: 'put'
    })
}
