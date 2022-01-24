import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'

const routes = [
  {
    path: '/login',
    component: Login
  },
  {
    path: '/login/:action',
    component: Login
  },
  {
    path: '/home',
    component: Home
  },
  {
    path: '/home/:navi',
    component: Home
  },
  {
    path: '/home/:navi/:win',
    component: Home
  },
  {
    path: '/home/:navi/:win/:type',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
