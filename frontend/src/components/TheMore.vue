<template>
  <div>
    <div v-bind:class="{ 'list-item': true, 'list-item-active': bgActive == 'info' }" @click="goTo('/home/info/info')">
      个人信息
    </div>
    <div class="divider"></div>
    <div v-bind:class="{ 'list-item': true, 'list-item-active': bgActive == 'creategroup' }" @click="goTo('/home/info/creategroup')">
      创建群聊
    </div>
  </div>
</template>

<script>
import { defineComponent, ref, watch } from 'vue'
import { useRouter } from 'vue-router'

export default defineComponent({
  setup() {
    const router = useRouter()
    const bgActive = ref("info")

    bgActive.value = router.currentRoute.value.params.win

    watch(()=> router.currentRoute.value.params, ()=> {
      bgActive.value = router.currentRoute.value.params.win
    })

    return {
      bgActive,

      goTo(path) {
        router.push(path)
      }
    }
  }
})
</script>

<style scoped>
.list-item {
  margin: 5px 8px 5px 8px;
  height: 4rem;
  border-radius: 12px;
  display: flex;
  align-content: flex-start;
  padding-top: 0.5rem;
  padding-left: 1rem;
  transition: all 0.3s;
  user-select: none;
  font-size: 1.6rem;
  font-weight: bolder;
}
.list-item:hover {
  background: #ecf0f1;
  height: 4.5rem;
  font-size: 2.4rem;
}
.list-item-active {
  background: #ecf0f1;
  height: 4.5rem;
  font-size: 2.4rem;
}
.divider {
  border-bottom: solid 2px #ecf0f1;
}
</style>
