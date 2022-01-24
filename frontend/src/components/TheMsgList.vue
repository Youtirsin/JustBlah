<template>
  <div id='msgList'>
    <div class="list-item" v-for="message in firstmessages" :key="message" 
        @click="goTo('/home/message/msgwindow/' + message.user)">
      <div class="head">
        <n-avatar
          round
          :size="48"
          src="https://img1.baidu.com/it/u=1125595869,4288987406&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
        />
      </div>
      <div class="nonhead">
        <div class='info'>
          
          <div class='username'>
            <n-ellipsis style="max-width: 200px;">
              {{ message.user }}
            </n-ellipsis>
          </div>
          <div class="message">
            <n-ellipsis style="max-width: 200px;">
              {{ message.msg }}
            </n-ellipsis>
          </div>
        </div>
        <div class="time">
          {{ message.date }}
        </div>
      </div>
    </div>
    <div class="list-item" v-for="message in firstGroupMessages" :key="message" 
        @click="goTo('/home/message/gmsgwindow/' + message.group)">
      <div class="head">
        <n-avatar
          round
          :size="48"
          src="https://img1.baidu.com/it/u=1125595869,4288987406&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
        />
      </div>
      <div class="nonhead">
        <div class='info'>
          
          <div class='username'>
            <n-ellipsis style="max-width: 200px;">
              {{ message.group }}
            </n-ellipsis>
          </div>
          <div class="message">
            <n-ellipsis style="max-width: 200px;">
              {{ message.from + ":" + message.msg }}
            </n-ellipsis>
          </div>
        </div>
        <div class="time">
          {{ message.date }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent, ref, getCurrentInstance, watch } from 'vue'
import { useRouter } from "vue-router"
import { useMessage } from 'naive-ui'

export default defineComponent({
  props: ['myName', 'signal'],
  setup(props) {
    const router = useRouter()
    const proxy = getCurrentInstance().proxy
    const message = useMessage()
    const firstmessages = ref([]) // like {user: "", msg:"", date: ""}, first messages
    const firstGroupMessages = ref([])

    function loadFirstMessages() {
      proxy.$axios({
        method: "get",
        url: proxy.$MyServerAPIs.LoadFirstMessages
      })
      .then(response => {
        if (response.data.code == 200) {
          firstmessages.value = []
          response.data.data.forEach(el => {
            let message = {
              user: "",
              msg: el.msg,
              date: el.timestamp
            }
            message.user = el.from == props.myName ? el.to : el.from
            firstmessages.value.push(message)
          })
        }else {
          message.error("用户未登录")
          router.push("/login/signin")
        }
      })
    }
    function loadFirstGroupMessages() {
      proxy.$axios({
        method: "get",
        url: proxy.$MyServerAPIs.LoadFirstGroupMessage
      })
      .then(response => {
        if (response.data.code == 200) {
          firstGroupMessages.value = []
          response.data.data.forEach(el => {
            let message = {
              group: el.group,
              from: el.from,
              msg: el.msg,
              date: el.timestamp
            }
            firstGroupMessages.value.push(message)
          })
        }else {
          message.error("用户未登录")
          router.push("/login/signin")
        }
      })
    }

    loadFirstMessages()
    loadFirstGroupMessages()
    watch(()=> props.signal, ()=> {
      loadFirstMessages()
      loadFirstGroupMessages()
    })

    return {
      firstmessages,
      firstGroupMessages,

      goTo(path) {
        router.push(path)
      }
    }
  }
})
</script>

<style scoped>
#msgList {
  height: 510px;
  overflow-y: scroll;
}
.list-item {
  margin-left: 0.5rem;
  margin-right: 0.5rem;
  height: 4rem;
  border-radius: 12px;
  display: flex;
  align-items: flex-start;
  margin-top: 0.5rem;
  padding: 0.5rem 1rem;
  transition: all 0.3s;
  user-select: none;

}
.list-item:hover {
  background: #ecf0f1;
}
.nonhead {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  width: 250px;
  margin-left: 1rem;
}
.info .username {
  font-weight: bolder;
  font-size: 1.4rem;
}
.message, .time {
  color: #95a5a6;
}

</style>
