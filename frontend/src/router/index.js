import { createRouter, createWebHistory } from "vue-router"
import Home from "../pages/Home.vue"
import Login from "../pages/Login.vue"
import SignUp from "../components/SignUp.vue"
import SignIn from "../components/SignIn.vue"
import Reset from "../components/Reset.vue"

import Contact from "../components/Contact.vue"
import Message from "../components/Message.vue"
import Info from "../components/Info.vue"

import ContactFriendWindow from "../components/ContactFriendWindow.vue"
import ContactGroupWindow from "../components/ContactGroupWindow.vue"
import WhiteBoard from "../components/WhiteBoard.vue"
import MessageWindow from "../components/MessageWindow.vue"
import InfoWindow from "../components/InfoWindow.vue"

const routes = [
  {
    path: "/",
    redirect: "/home"
  },
  {
    path: "/home",
    component: Home,
    children: [
      {
        path: "contact",
        components: {
          list: Contact,
          window: WhiteBoard
        }
      },
      {
        path: "contact/friend",
        components: {
          list: Contact,
          window: ContactFriendWindow
        }
      },
      {
        path: "contact/group",
        components: {
          list: Contact,
          window: ContactGroupWindow
        }
      },
      {
        path: "message",
        components: {
          list: Message,
          window: MessageWindow
        }
      },
      {
        path: "info",
        components: {
          list: Info,
          window: InfoWindow
        }
      }
    ]
  },
  {
    path: "/login",
    component: Login,
    children: [
      {
        path: "",
        redirect: "/login/signup"
      },
      {
        path: "signup",
        component: SignUp
      },
      {
        path: "signin",
        component: SignIn
      },
      {
        path: 'reset',
        component: Reset
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
