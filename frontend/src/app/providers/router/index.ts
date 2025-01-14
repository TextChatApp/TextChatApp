import { createRouter, createWebHistory } from 'vue-router'
import { routes } from './routes'
import { useUser } from '@/entities/user'
import { storeToRefs } from 'pinia'

export const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  document.title = typeof to.meta.title === 'string' ? to.meta.title : 'Home'
  const userStore = useUser()
  const { userInfo } = storeToRefs(userStore)

  if (to.matched.some((record) => record.meta.requiresGuest) && userInfo.value) {
    next({ path: '/' })
  } else if (to.matched.some((record) => record.meta.requiresAuth) && !userInfo.value) {
    next({ path: '/login' })
  } else {
    next()
  }
})
