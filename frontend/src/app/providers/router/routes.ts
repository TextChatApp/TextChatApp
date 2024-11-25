import Chats from '@/widgets/chats'

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
          mobile: () => import('@/widgets/chats'),
          desktop: () => import('@/shared/ui/empty-chat')
        }
      },
      {
        path: 'chat/:id',
        name: 'chat',
        components: {
          mobile: () => import('@/widgets/chat'),
          desktop: () => import('@/widgets/chat')
        }
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/pages/login')
  },
  {
    path: '/signUp',
    name: 'signUp',
    component: () => import('@/pages/sign-up')
  }
]
