import './styles/main.css'
import { createApp } from 'vue'

import App from './App.vue'
import {router, store} from './providers'

const initializeApp = createApp(App)

initializeApp.use(store).use(router)

export const app = initializeApp




