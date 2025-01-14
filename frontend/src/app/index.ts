import './styles/main.css'
import { createApp } from 'vue'
import Notifications from '@kyvg/vue3-notification'
import App from './App.vue'
import { store, router } from './providers'

const initializeApp = createApp(App)

initializeApp.use(store).use(router).use(Notifications)

export const app = initializeApp
