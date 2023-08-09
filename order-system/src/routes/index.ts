import {createRouter, createWebHistory, RouteRecordRaw} from "vue-router"

const routes: RouteRecordRaw[] = [
    {
        path: "/login",
        name: 'login',
        component: () => import("@/views/login.vue"),
    }, {
        path: "/",
        name: 'home',
        component: () => import("@/views/home.vue"),
    }, {
        path: "/back",
        name: 'back',
        component: () => import("@/views/back.vue"),
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})
export default router;


/*router.beforeEach((to, from, next) => {


    next()
})*/

/**
 * 全局路由 当页面不存在时，重定向
 */
/*
router.beforeEach((to, from, next) => {
    if (to.matched.length === 0) {  // 如果未匹配到路由
        from.name ? next({name: from.name}) : next('/back')
    } else {
        next()  // 如果匹配到正确跳转
    }
})
*/
