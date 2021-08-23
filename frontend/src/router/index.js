import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import MyPage from '../components/MyPage'
import BoardDetail from '../components/BoardDetail'
import Ballade from '../components/Ballade'
import Hiphop from '../components/Hiphop'
import Dance from '../components/Dance'
import Etc from '../components/Etc'
import SearchResult from '../components/SearchResult'
import Register from '../components/Register'
import Login from '../components/Login'
import Upload from '../components/Upload'
import Record from '../components/Record' //추가
Vue.use(VueRouter)

const routes = [{
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/boarddetail/:boardIdx',
        name: 'BoardDetail',
        component: BoardDetail
    },
    {
        path: '/mypage',
        name: 'MyPage',
        component: MyPage
    },
    {
        path: '/ballade',
        name: 'Ballade',
        component: Ballade
    },
    {
        path: '/hiphop',
        name: 'Hiphop',
        component: Hiphop
    },
    {
        path: '/etc',
        name: 'Etc',
        component: Etc
    },
    {
        path: '/dance',
        name: 'Dance',
        component: Dance
    },
    {
        path: '/searchResult',
        name: 'SearchResult',
        component: SearchResult
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/upload',
        name: 'Upload',
        component: Upload
    }, {
        path: '/record', //추가
        name: 'Record',
        component: Record
    },
    {
        path: '/github',
        beforeEnter() { location.href = 'https://github.com/EncoreFinal2Team/WebProject-Design' }
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router