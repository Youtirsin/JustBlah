<script setup>
import { NSpace, NLayout, NLayoutHeader, 
  NLayoutContent, NMessageProvider, NH1, NText } from 'naive-ui'
import { useRouter } from 'vue-router'

const router = useRouter()
function goback() {
  router.push('/login/signup')
}
</script>

<template>
  <div class='window'>
  <div class="left">
    <img alt="Just Blah" src="../assets/LoginBg.jpg" class="img">
  </div>
  <div class="right">
    <n-space vertical class="space">
      <n-layout>
        <n-layout-header>
          <n-h1 class="header" @click="goback">
            Just Blah
          </n-h1>
        </n-layout-header>
        <n-layout-content>
          <n-message-provider>
              <router-view v-slot="{ Component }">
                <transition>
                  <component :is="Component"></component>
                </transition>
              </router-view>
          </n-message-provider>
        </n-layout-content>
      </n-layout>
    </n-space>
  </div>
</div>
</template>

<style scoped>
.window {
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
.img {
  width: 420px;
  height: 630px;
}

.left {
  width: 420px;
  height: 630px;
}
.right {
  width: 700px;
  height: 630px;
}
.space {
  margin-left: 2rem;
}
.header {
  font-size: 4rem;
  font-weight: bolder;
  color: #37a2c6;
  cursor: pointer;
  user-select: none;
}
</style>
