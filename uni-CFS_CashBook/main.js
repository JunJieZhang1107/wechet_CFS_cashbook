import Vue from 'vue'
import App from './App'
import tabBar from './components/tabbar.vue'
import navBar from './components/navbar.vue'
import inandoutnavbar from './components/inandout-nav-bar.vue'
import remindnavbar from './components/remind-nav-bar.vue'

Vue.config.productionTip = false
Vue.component('nav-bar',navBar)
Vue.component('tab-bar',tabBar)
Vue.component('inan-nav-bar',inandoutnavbar)
Vue.component('remind-nav-bar',remindnavbar)
App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
