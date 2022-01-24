<template>
  <div id="window">
    <div id="left">
      <div id="navi-sider">
        <div id="navi">
          <div class="padding"></div>
          <div class="icon" @click="goTo('/home/contact')">
            <n-icon size="36" :color='naviActive=="contact" ? "#bdc3c7" : "#ffffff"'>
              <UserRegular />
            </n-icon>
          </div>
          <div class="icon" @click="goTo('/home/message')">
            <n-icon size="36" :color='naviActive=="message" ? "#bdc3c7" : "#ffffff"'>
              <MessageCircle />
            </n-icon>
          </div>
          <div class="icon" @click="goTo('/home/info')">
            <n-icon size="36" :color='naviActive=="info" ? "#bdc3c7" : "#ffffff"'>
              <MoreHorizontal20Filled />
            </n-icon>
          </div>
        </div>
        <div id="navi-logout">
          <div id="logout" @click="goTo('/login/signin')">
            <n-icon size="36" color='#ffffff'>
              <Logout />
            </n-icon>
          </div>
        </div>
      </div>
      <div id="list">
        <div id="list-stable">
          <div id="user" class='list-item-stable'>
            <n-avatar
              round
              :size="48"
              src="https://img1.baidu.com/it/u=1125595869,4288987406&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
            />
            <div id='username'>
              <n-ellipsis style="max-width: 200px;">
                <h2>{{ myName }}</h2> 
              </n-ellipsis>
            </div>
          </div>
          <div id="search" class='list-item-stable'>
            <div>
              <n-input size="large" style="background:#f2f2f2;" 
                round placeholder="Type to search" @keydown.enter="goTo('/home/search/search/' + keyword); keyword=''"
                v-model:value="keyword">
                <template #prefix>
                  <n-icon size="25">
                    <Search />
                  </n-icon>
                </template>
              </n-input>
            </div>
          </div>
        </div>
        <n-message-provider>
          <Contact v-if='naviActive == "contact"'/>
          <TheMsgList v-if='naviActive == "message"' :myName='myName' :signal='signal'/>
          <TheMore v-if='naviActive == "info"'/>
        </n-message-provider>
      </div>
    </div>
    <div id="right">
      <n-message-provider>
        <ContactList v-if="winActive == 'contacts'"/>
        <MsgWindow v-if="winActive == 'msgwindow'" :signal="signal"/>
        <GMsgWindow v-if="winActive == 'gmsgwindow'" :signal="signal" :myName='myName'/>
        <TheInfo v-if="winActive == 'info'"/>
        <TheSearch v-if="winActive == 'search'"/>
        <TheUserInfo v-if="winActive == 'userinfo'"/>
        <TheGroupInfo v-if="winActive == 'groupinfo'"/>
        <CreateGroup v-if="winActive == 'creategroup'"/>
      </n-message-provider>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import Contact from '@/components/Contact.vue'
import TheMsgList from '@/components/TheMsgList.vue'
import TheMore from '@/components/TheMore.vue'
import ContactList from '@/components/ContactList.vue'
import MsgWindow from '@/components/MsgWindow.vue'
import TheInfo from '@/components/TheInfo.vue'
import TheSearch from '@/components/TheSearch.vue'
import TheUserInfo from "@/components/TheUserInfo.vue"

import TheGroupInfo from "@/components/TheGroupInfo.vue"
import CreateGroup from "@/components/CreateGroup.vue"
import GMsgWindow from "@/components/GMsgWindow.vue"


import { UserRegular } from "@vicons/fa"
import { MessageCircle, Logout, Search } from "@vicons/tabler"
import { MoreHorizontal20Filled } from "@vicons/fluent"
import { defineComponent, ref, watch, getCurrentInstance } from 'vue'
import { useRouter } from "vue-router"

import Stomp from "stompjs"
import SockJs from "sockjs-client"

export default defineComponent({
  components: {
    Contact,
    TheMsgList,
    TheMore,
    ContactList,
    MsgWindow,
    TheInfo,
    TheSearch,
    TheUserInfo,

    TheGroupInfo,
    CreateGroup,
    GMsgWindow,

    UserRegular,
    MessageCircle,
    MoreHorizontal20Filled,
    Logout,
    Search,
  },
  setup() {
    const router = useRouter()
    const proxy = getCurrentInstance().proxy


    const naviActive = ref("")
    const winActive = ref("")

    const signal = ref(false)

    const groups = ref([])
    function loadGroups() {
      proxy.$axios({
        method: "get",
        url: proxy.$MyServerAPIs.LoadGroup
      })
      .then(response => {
        console.log(response)
        groups.value = []
        if (response.data.code == 200) {
          response.data.data.forEach(el => {
            groups.value.push(el)
          })
        }else if (response.data.code == 2001) {
          router.push("/login/signin")
        }
      })
    }
    loadGroups()

    function redirect() {
      let params = router.currentRoute.value.params
      naviActive.value = params.navi
      winActive.value = params.win
    }

    redirect()

    watch(()=> router.currentRoute.value.params, ()=> {
      redirect()
    })

    const myName = ref("")

    const keyword = ref("")


    var sock = new SockJs(proxy.$MyServerAPIs.WebSocketEndPoint)
    var stompClient = Stomp.over(sock)

    proxy.$axios({
      method: "get",
      url: proxy.$MyServerAPIs.WhoAmI
    })
    .then(response => {
      if (response.data.code == 200) {
        myName.value = response.data.data
        stompClient.connect({}, function (frame) {frame
          let url = "/topic/" + myName.value
          stompClient.subscribe(url, function (msg) {msg
            signal.value = !signal.value
          }, function (err) {
            console.log("ws: error occured:", err)
          })

          groups.value.forEach(el => {
            let url = "/topic/" + el
            stompClient.subscribe(url, function (msg) {msg
              signal.value = !signal.value
            }, function (err) {
              console.log("ws: error occured:", err)
            })
          })
        })
      }else {
        router.push("/login/signin")
      }
    })
    
    return {
      myName,

      naviActive,
      winActive,

      signal,

      keyword,

      goTo(path) {
        router.push(path)
      }
    }
  }
})
</script>
<style scoped>
#window {
  width: 1120px;
  height: 630px;
  min-width: 1120px;
  height: 630px;
  padding: 0;
  display: flex; 
  justify-content: center;
  align-items: center;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
#left {
  /* with border subtracted */
  width: 398px;
  height: 630px;
  display: flex;
  border-right: solid 2px #ecf0f1;
}
#right {
  width: 720px;
  height: 630px;
}
#navi-sider {
  width: 50px;
  height: 630px;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  align-items: center;
  background: #37a2c6;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
.padding {
  width: 100%;
  height: 100px;
}
.icon {
  margin-top: 1rem;
}
#navi .icon .n-icon, #logout {
  cursor: pointer;
  transition: all 0.3s;
}
#logout {
  margin-bottom: 1rem;
}
#list {
  width: 350px;
  height: 630px;
}
.list-item-stable {
  margin-left: 8px;
  margin-right: 8px;
  height: 60px;
  border-radius: 12px;
  display: flex;
  align-items: center;
}

#username {
  height: 100%;
  margin-left: 1rem;
}
#search > div {
  width: 100%;
}
::-webkit-scrollbar {
  width: 10px;
}
::-webkit-scrollbar-track {
  border-radius: 5px;
  box-shadow: inset 0 0 10px rgba(180, 180, 180, 0.25);
  background: white;
}
::-webkit-scrollbar-thumb {
  border-radius: 5px;
  /* background: #009578; */
  background: #bdc3c7;
}
::-webkit-scrollbar-thumb:hover {
  background: #939d9e;
}
</style>