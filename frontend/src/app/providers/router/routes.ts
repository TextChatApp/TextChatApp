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
          mobile: () => import('@/pages/chat'),
          desktop: () => import('@/pages/chat')
        },
        props: true
      },
      {
        path: '/server/:serverId/room/:id',
        name: 'room',
        props: true,
        components: {
          mobile: () => import('@/pages/chat'),
          desktop: () => import('@/pages/chat')
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
  },
  {
    path: '/settings',
    name: 'settings',
    component: () => import('@/pages/settings'),
    meta: {
      title: 'Register',
      requiresAuth: true
    }
  }
]
