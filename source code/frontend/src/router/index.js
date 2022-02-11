import Vue from 'vue'
import VueRouter from 'vue-router'
import Disk from '../views/Disk.vue'
import Cpu from '../views/Cpu.vue'
import Help from '../views/Help.vue'
import About from '../views/About.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '文件管理',
    component: Disk
  },
	{
		path: '/disk',
		name: '文件管理',
		redirect: '/'
	},
	{
		path: '/cpu',
		name: '调度管理',
		component: Cpu
	},
  {
    path: '/help',
    name: '帮助',
    component: Help
  },
  {
    path: '/about',
    name: 'About',
    component: About
  }
]

const router = new VueRouter({
  routes
})

export default router
