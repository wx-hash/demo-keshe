import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import inject from './plugins/inject'

// 导入完整的element-ui
Vue.use(ElementUI)
//挂载到vue上
Vue.use(inject)

Vue.config.productionTip = true
window.vue=new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

// 全局路由守卫
router.beforeEach((to, form, next) => {
  // 如果进入到的路由是登录页或者注册页面，则正常展示
  if(router.app.$store.state.user||to.path=='/login'){
    if (to.path == '/login') {
      // 如果是登录页面就把框架去掉
      router.app.$store.commit('showFramework', false)
    } else {
      router.app.$store.commit('showFramework', true)
    }
    //直接放行
    next()
  }else{
    router.app.$store.commit('showFramework', false)
    //跳转到登录页面
    console.log(to)
    next({
      path:"/login"
    })
  }
})
