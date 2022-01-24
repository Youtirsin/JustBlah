<template>
  <div>
    <div v-bind:class="{ 'list-item': true, 'list-item-active': bgActive == 'friend' }" @click="goTo('/home/contact/contacts/friend')">
      好友
    </div>
    <div class="divider"></div>
    <div v-bind:class="{ 'list-item': true, 'list-item-active': bgActive == 'group' }" @click="goTo('/home/contact/contacts/group')">
      群聊
    </div>
  </div>
</template>

<script>
import { defineComponent, ref, watch } from 'vue'
import { useRouter } from 'vue-router'


export default defineComponent({
  setup() {
    const router = useRouter()
    const bgActive = ref("friend")
    
    bgActive.value = router.currentRoute.value.params.type

    watch(()=> router.currentRoute.value.params, ()=> {
      bgActive.value = router.currentRoute.value.params.type
    //   if (router.path.startWith(""))
    //   if (router.path == "/home/contact/contacts/friend") {
    //     bgActive.value = "friend"
    //   }else if (router.path == "/home/contact/contacts/group") {
    //     bgActive.value = "group"
    //   }else
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
