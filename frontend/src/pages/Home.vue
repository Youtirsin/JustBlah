<script setup>
import { NIcon, NAvatar, NMessageProvider, NEllipsis, NInput } from 'naive-ui'
import { UserRegular } from "@vicons/fa"
import { MessageCircle, Logout, Search } from "@vicons/tabler"
import { MoreHorizontal20Filled } from "@vicons/fluent"

import { ref } from "vue"
import { useRouter } from "vue-router"

const router = useRouter()
const naviActive = ref("")
const keyword = ref("")
const myName = ref("Youtirsin")

function goTo(part) { 
  naviActive.value = part
  router.push('/home/' + part)
}

</script>

<template>
  <div id="window">
    <div id="left">
      <div id="navi-sider">
        <div id="navi">
          <div class="padding"></div>
          <div class="icon" @click="goTo('contact')">
            <n-icon size="36" :color='naviActive=="contact" ? "#bdc3c7" : "#ffffff"'>
              <UserRegular />
            </n-icon>
          </div>
          <div class="icon" @click="goTo('message')">
            <n-icon size="36" :color='naviActive=="message" ? "#bdc3c7" : "#ffffff"'>
              <MessageCircle />
            </n-icon>
          </div>
          <div class="icon" @click="goTo('info')">
            <n-icon size="36" :color='naviActive=="info" ? "#bdc3c7" : "#ffffff"'>
              <MoreHorizontal20Filled />
            </n-icon>
          </div>
        </div>
        <div id="navi-logout">
          <div id="logout" @click="goTo('signin')">
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
          <router-view name="list"></router-view>
        </n-message-provider>
      </div>
    </div>
    <div id="right">
      <n-message-provider>
        <router-view name="window"></router-view>
      </n-message-provider>
    </div>
  </div>
</template>

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
