import Chat from '@/widgets/chat/Chat.vue'

export const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import('@/app/layouts/homeLayout'),
    children: [
      {
        path: '',
        name: 'chats',
        components: {
          mobile: () => import('@/widgets/sidebar'),
          desktop: () => import('@/shared/ui/empty-chat')
        }
      },
      {
        path: 'chat/:id',
        name: 'chat',
        components: {
          mobile: Chat,
          desktop: Chat
        }
      }
    ],
    meta: {
      title: 'Home',
      requiresAuth: true
    }
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/pages/login')
  },
  {
    path: '/register',
    name: 'signUp',
    component: () => import('@/pages/sign-up')
  }
]
