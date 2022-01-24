import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import naive from "./naiveUI"
import axios from 'axios'
import MyServerAPIs from './serverAPIs'


const app = createApp(App)

app.use(router).mount('#app')

app.use(naive)


app.config.globalProperties.$author = "Youtirsin"
app.config.globalProperties.$MyServerAPIs = MyServerAPIs

// // allow with cookies and credentials
axios.defaults.withCredentials = true

app.config.globalProperties.$axios = axios
