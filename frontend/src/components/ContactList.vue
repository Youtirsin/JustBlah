<template>
    <div id='ContactList'>
      <div id="type">
        {{ typeActive }}
      </div>
      <div class="group" v-if="typeActive=='friend'">
        <div class="group-item" v-for="friend in friends" :key="friend" @click="goTo('/home/contact/userinfo/' + friend)">
            <div class="head">
              <n-avatar
                round
                :size="48"
                src="https://img1.baidu.com/it/u=1125595869,4288987406&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
              />
            </div>
            <div class='username'>
                {{ friend }}
            </div>
        </div>
      </div>

      <div class="group" v-if="typeActive=='group'">
        <div class="group-item" v-for="group in groups" :key="group" @click="goTo('/home/contact/groupinfo/' + group)">
            <div class="head">
              <n-avatar
                round
                :size="48"
                src="https://img1.baidu.com/it/u=1125595869,4288987406&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
              />
            </div>
            <div class='username'>
                {{ group }}
            </div>
        </div>
      </div>
    </div>
</template>

<script>
import { defineComponent, ref, getCurrentInstance, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useMessage } from 'naive-ui'



export default defineComponent({
  setup() {
    const router = useRouter()
    const message = useMessage()
    const proxy = getCurrentInstance().proxy

    const typeActive = ref("friend")

    const friends = ref([])
    const groups = ref([])
    function loadFriends() {
      proxy.$axios({
        method: "get",
        url: proxy.$MyServerAPIs.LoadFriend
      })
      .then(response => {
        console.log(response)
        friends.value = []
        if (response.data.code == 200) {
          response.data.data.forEach(el => {
            friends.value.push(el)
          })
        }else if (response.data.code == 2001) {
          message.error("用户未登录")
          router.push("/login/signin")
        }
      })
    }
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
          message.error("用户未登录")
          router.push("/login/signin")
        }
      })
    }

    typeActive.value = router.currentRoute.value.params.type
    if (typeActive.value == "group") {
      loadGroups()
    }else {
      loadFriends()
    }

    watch(()=> router.currentRoute.value.params, ()=> {
      typeActive.value = router.currentRoute.value.params.type
      if (typeActive.value == "group") {
        loadGroups()
      }else {
        loadFriends()
      }
    })
    
    return {
      friends,
      groups,
      typeActive,

      goTo(path) {
        router.push(path)
      }
    }
  }
})
</script>

<style scoped>
#ContactList {
  height: 630px;
  overflow-y: scroll;
  user-select: none;
  margin-left: 2rem;
}
#type {
  font-size: 3rem;
  font-weight: bolder;
}
.group-item {
  margin-top: 1rem;
  margin-right: 8px;
  height: 4rem;
  border-radius: 12px;
  display: flex;
  align-items: flex-start;
  padding-top: 0.5rem;
  transition: all 0.3s;
  cursor: pointer;
}
.group-item:hover {
  background: #ecf0f1;
  padding-left: 1rem;
  height: 4.5rem;
}
.group-item:hover .username {
  font-size: 2rem;
}
.group-item .username {
  font-size: 1.5rem;
  font-weight: bolder;
  margin-left: 1rem;
  transition: all 0.3s;
}
</style>
