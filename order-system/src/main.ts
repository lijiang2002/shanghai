import {createApp} from 'vue'
import './style.css'
import App from './App.vue'
import router from "@/routes";

import pinia from '@/store'

createApp(App).use(router).use(pinia).mount('#app')

export const url = 'http://127.0.0.1:8080'
//export const url = 'https://4035f95163.goho.co'
