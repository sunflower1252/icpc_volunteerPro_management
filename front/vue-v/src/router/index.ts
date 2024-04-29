import { createRouter, createWebHistory } from 'vue-router'
import Project from '../views/Project.vue' //页面
import Home from '@/views/Home.vue'  //页面
import Blog from '@/views/Blog.vue'
import About from '@/views/About.vue'
import Activity from '@/views/Activity.vue'
import Login from '@/views/Login.vue'
import myself from '@/views/myself.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/Home',  //展示的路径
      name: 'Home',   //组件的名字
      component: Home //导入的文件名字
    },
    {
      path: '/Project',  //展示的路径
      name: 'Project',   //组件的名字
      component: Project //导入的文件名字
    },
    {
      path: '/Blog',  //展示的路径
      name: 'Blog',   //组件的名字
      component: Blog //导入的文件名字
    },
    {
      path: '/About',  //展示的路径
      name: 'About',   //组件的名字
      component: About //导入的文件名字
    },
    {
      path: '/Activity',  //展示的路径
      name: 'Activity',   //组件的名字
      component: Activity //导入的文件名字
    },
    {
      path:'/Login',
      name:'Login',
      component:Login
    },
    {
      path:'/myself',
      name:'myself',
      component:myself
    },
    {
      path: '/',
      name: 'Home',
      component: Home
    }
  ]
})

export default router