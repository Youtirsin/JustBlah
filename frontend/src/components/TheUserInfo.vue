<template>
    <div id='infoWindow'>
      <div id="blue-bg"></div>
      <div id="white-main">
        <div id="info-container">
        <div id="head">
            <n-avatar
              round
              :size="144"
              src="https://img1.baidu.com/it/u=1125595869,4288987406&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
            />
        </div>
        <div id="info">
          <div id="name">{{ user.username }}</div>
          <div id="email">{{ user.email }}</div>
          <div id="description">{{ user.description }}</div>
        </div>
        <div id="operations">
            <button id="talk" v-if="isFriend" @click="goTo('/home/message/msgwindow/' + user.username)">
                <n-icon color="#ffffff" size="36">
                  <MessageCircle />
                </n-icon>
            </button>
            <button id="delete" v-if="isFriend" @click="deleteFriend">
                <n-icon color="#ffffff" size="36">
                  <Delete24Regular />
                </n-icon>
            </button>
            <button id="add" v-if="!isFriend" @click="addFriend">
                <n-icon color="#ffffff" size="36">
                  <Add12Filled />
                </n-icon>
            </button>
        </div>
        </div>
      </div>
    </div>
</template>

<script>
import { defineComponent, ref, getCurrentInstance, watch } from 'vue'
import { MessageCircle } from "@vicons/tabler"
import { Add12Filled, Delete24Regular } from "@vicons/fluent"
import { useMessage } from 'naive-ui'
import { useRouter } from "vue-router"

export default defineComponent({
  components: {
    Add12Filled,
    MessageCircle,
    Delete24Regular
  },
  setup() {
    const router = useRouter()
    const message = useMessage()
    const proxy = getCurrentInstance().proxy
    const isFriend = ref(false)

    const user = ref({
      username: "",
      email: "test",
      description: "test"
    })

    user.value.username = router.currentRoute.value.params.type

    watch(()=> router.currentRoute.value.params, ()=> {
      user.value.username  = router.currentRoute.value.params.type
    })


    function areWeFriend() {
      proxy.$axios({
        method: "get",
        url: proxy.$MyServerAPIs.IsFriend,
        params: {
          friendName : user.value.username
        }
      })
      .then(response => {
        if (response.data.code == 200) {
          isFriend.value = response.data.data
        }else if (response.data.code == 2001) {
            message.error("用户未登录")
            router.push("/login/signin")
            console.log(response.data.msg)
        }
      })
    }
    areWeFriend()


    proxy.$axios({
      method: "get",
      url: proxy.$MyServerAPIs.GetUserInfo,
      params: {
        name : user.value.username
      }
    })
    .then(response => {
      if (response.data.code == 200) {
        user.value.email = response.data.data.email
        user.value.description = response.data.data.description
      }else if (response.data.code == 2001) {
          message.error("用户未登录")
          router.push("/login/signin")
          console.log(response.data.msg)
      }
    })
    return {
      user,
      isFriend,

      goTo(path) {
        router.push(path)
      },
      deleteFriend() {
        proxy.$axios({
          method: "get",
          url: proxy.$MyServerAPIs.DeleteFriend,
          params: {
            friendName : user.value.username
          }
        })
        .then(response => {
          areWeFriend()
          if (response.data.code == 200) {
              message.error("已删除好友")
          }else if (response.data.code == 2001) {
              message.error("用户未登录")
              router.push("/login/signin")
              console.log(response.data.msg)
          }
        })
      },
      addFriend() {
        proxy.$axios({
          method: "get",
          url: proxy.$MyServerAPIs.MakeFriend,
          params: {
            friendName : user.value.username
          }
        })
        .then(response => {
          areWeFriend()
          if (response.data.code == 200) {
              message.success("已添加好友")
          }else if (response.data.code == 2001) {
              message.error("用户未登录")
              router.push("/login/signin")
              console.log(response.data.msg)
          }
        })
      }
    }
  }
})
</script>

<style scoped>
#infoWindow {
  height: 630px;
}
#blue-bg {
  background: #37a2c6;
  box-shadow: 0 3px 5px #888888;
  height: 16rem;
}
#white-main {
  margin-top: -5rem;
}
#info-container {
  margin-left: 1rem;
}
#info {
  height: 12rem;
}
#name {
  font-size:  2.4rem;
  font-weight: bolder;
}
#email{
  color: grey;
  font-size:  1.8rem;
} #description {
  color: grey;
  font-size:  1.2rem;
}
#operations {
  display: flex;
  justify-content: end;
  padding: 0 1rem;
}
#talk, #delete, #add {
  margin-left: 1rem;
  width: 4rem;
  height: 4rem;
  border-radius: 50%;
  border: none;
  outline: none;
  box-shadow: 2px 2px 3px #888888;
  color: white;
  cursor: pointer;
}
#talk {
  background: #37a2c6;
}
#delete {
  background: #e74c3c;
}
#add {
  background: #2ecc71;
}
#edit {
  width: 40rem;
}
#edit-btns {
  margin-top:1rem;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
</style>
