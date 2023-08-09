import {defineStore} from 'pinia'
import {parse, stringify} from 'zipson'


const useStore = defineStore('useStore', {
    state: () => ({
        user: {
            username: <string | null>null,
        },

    }),
    getters: {
        getUsername: (state): string =>
            state.user.username == null || state.user.username == ''
                ? '打工人'
                : state.user.username,
    },
    actions: {
        clearUser() {
           this.user.username=null
        }
    },
    persist: {
        // 指定存储 key
        key: 'items-use-store',
        // localStorage  sessionStorage
        storage: sessionStorage,
        // 持久化指定
        paths: ['user'],
        // 序列化程序,
        serializer: {
            deserialize: parse,
            serialize: stringify,
        },
        // 还原前
        beforeRestore: (ctx) => {
            console.log(`即将恢复：'${ctx.store.$id}'`)
        },
        // 还原后
        afterRestore: (ctx) => {
            console.log(`已恢复：'${ctx.store.$id}'`)
        },
    },
})


export default useStore
