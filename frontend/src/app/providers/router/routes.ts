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
        props: true,
        components: {
          mobile: Chat,
          desktop: Chat
        }
      },
      {
        path: 'users',
        name: 'users',
        components: {
          mobile: () => import('@/pages/users'),
          desktop: () => import('@/pages/users')
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
    component: () => import('@/pages/login'),
    meta: {
      title: 'Login',
      requiresGuest: true
    }
  },
  {
    path: '/register',
    name: 'signUp',
    component: () => import('@/pages/sign-up'),
    meta: {
      title: 'Register',
      requiresGuest: true
    }
  }
]
