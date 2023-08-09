import axios from "axios";
import {
    createDiscreteApi,
    ConfigProviderProps,
    darkTheme,
    lightTheme
} from "naive-ui";
import {ref, computed} from "vue"
import {url} from "@/main";

const themeRef = ref<'light' | 'dark'>('light')
const configProviderPropsRef = computed<ConfigProviderProps>(() => ({
    theme: themeRef.value === 'light' ? lightTheme : darkTheme
}))
const {message} = createDiscreteApi(
    ['message'],
    {configProviderProps: configProviderPropsRef}
)

export function request(config: any) {
    // noinspection TypeScriptValidateTypes
    const instance = axios.create({
        //baseURL: 'http://124.221.101.41:8081', // 根路径，网关统一入口路径
        baseURL: url,
        timeout: 20000, // 请求超时时间，请求达到5秒，不让请求
        withCredentials: true,
        headers: {
            withCredentials: true
        }
    })
    // 请求拦截
    instance.interceptors.request.use(config => {
        return config;
    }, error => {
        console.log(error)
    })

    // 响应拦截
    instance.interceptors.response.use(resp => {
        if (resp.data.code == 200) {
            message.success(resp.data.message)

        } else if (resp.data.code == 201) {

        } else if (resp.data.code == 400) {
            message.error(resp.data.message)

        } else if (resp.data.code == 401) {

        } else if (resp.data.code == 408) {
            message.error(resp.data.message)
            window.sessionStorage.removeItem("user")
            window.sessionStorage.removeItem("role")
            window.location.href = '/login'
        }
        return resp.data;
    }, err => {
        console.log(err)
    })
    instance.defaults.withCredentials = true
    return instance(config)
}

