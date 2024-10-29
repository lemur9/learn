import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/views/Layout/index.vue'
import Home from '@/views/Home/index.vue'
import Login from '@/views/Login/index.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Layout',
      component: Layout,
      children: [
        {
          path: '',
          name: 'Home',
          component: Home,
        },
      ],
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
    },
  ],
})

export default router
