<template>
    <div id='msgWindow'>
      <div id="header">
        <div id="header-item">
          <div id="head">
            <n-avatar
              round
              :size="48"
              src="https://img1.baidu.com/it/u=1125595869,4288987406&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
            />
          </div>
          <div id="nonhead">
            <div id='username'>
              <n-ellipsis style="max-width: 400px;">
                {{ group }}
              </n-ellipsis>
            </div>
            <n-icon size="36" color='#000000' id="contactMore" @click="goTo('/home/contact/groupinfo/' + group)">
              <MoreHorizontal20Filled />
            </n-icon>
          </div>
        </div>
      </div>
      <div id="window">
        <div v-for="message in messages" :key="message" 
          :class="{ 'msg-container-that': !message.isFromMe, 'msg-container-this': message.isFromMe }">
          <div class="msg">
            {{ message.content }}
          </div>
        </div>
      </div>
      <div id="input">
            <n-icon size="36" style="cursor:pointer;">
              <Emoji16Regular/>
            </n-icon>
            <n-input size="large" round autosize placeholder="Type to send"
                    v-model:value="sendContent" @keydown.enter="sendMessage"
                    style="min-width: 85%;background:#f2f2f2;"/>
            <n-icon size="24" @click="sendMessage" style="cursor:pointer;">
              <Send16Regular/>
            </n-icon>
      </div>
    </div>
</template>

<script>
import { defineComponent, ref, getCurrentInstance, watch } from 'vue'
import { MoreHorizontal20Filled, Emoji16Regular, Send16Regular } from "@vicons/fluent"
import { useMessage } from 'naive-ui'
import { useRouter } from 'vue-router'

export default defineComponent({
  components: {
    MoreHorizontal20Filled,
    Emoji16Regular,
    Send16Regular
  },
  props: ['myName', 'signal'],
  setup(props) {
    const message = useMessage()
    const router = useRouter()
    const proxy = getCurrentInstance().proxy
    const group = ref("")
    const messages = ref([])
    const sendContent = ref("")

    group.value = router.currentRoute.value.params.type

    watch(()=> router.currentRoute.value.params, ()=> {
      group.value  = router.currentRoute.value.params.type
    })



    function loadMessages() {
      if (group.value == "") {
        return
      }
      proxy.$axios({
        method: "get",
        url: proxy.$MyServerAPIs.LoadGroupMessages,
        params: {
          groupName: group.value
        }
      })
      .then(response => {
        if (response.data.code == 200) {
          console.log(response.data.data)
          const data = response.data.data
          messages.value = []
          data.forEach(el => {
            const msg = {
              content: el.msg,
              from: el.from
            }
            if (el.from == props.myName) {
              msg.isFromMe = true
            }else {
              msg.isFromMe = false
            }
            messages.value.push(msg)
          })
        }else if(response.data.code == 2004) {
          message.error("群聊无效")
        }else {
          message.error("用户未登录")
          router.push("/login/signin")
        }
      })
    }
    loadMessages()
    
    watch(()=> props.signal, ()=> {
      loadMessages()
    })

    function sendMessage() {
      if (sendContent.value == "") {
        message.error("消息不能为空")
        return
      }
      proxy.$axios({
        method: "get",
        url: proxy.$MyServerAPIs.SendGroupMessage,
        params: {
          groupName: group.value,
          msg: sendContent.value
        }
      })
      .then(response => {
        if (response.data.code == 200) {
          loadMessages()
          sendContent.value = ""
        }else if(response.data.code == 2004) {
          message.error("群聊无效")
        }else {
          message.error("用户未登录")
          router.push("/login/signin")
        }
      })
    }

    return {
      group,
      messages,
      sendContent,

      sendMessage,
      goTo(path) {
        router.push(path)
      }
    }
  }
})
</script>

<style scoped>
#msgWindow {
  height: 630px;
}
#header-item {
  margin-right: 1rem;
  margin-left: 1rem;
  height: 4rem;
  display: flex;
  align-items: flex-end;
  user-select: none;
}
#nonhead {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  width: 45rem;
}
#username {
  font-size: 1.5rem;
  font-weight: bolder;
  margin-left: 1rem;
}
#contactMore {
  cursor: pointer;
}
#window {
  background: #f2f2f2;
  height: 32rem;
  overflow: scroll;
}
.msg-container-that {
  height: 4rem;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding-left: 0.5rem;
}
.msg-container-that .msg {
  font-size: 1.2rem;
  background: white;
  border-radius: 5rem;
  padding: 0.4rem 1rem;
}
.msg-container-this {
  height: 4rem;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding-right: 0.5rem;
}
.msg-container-this .msg {
  font-size: 1.2rem;
  background: #37a2c6;
  color: white;
  border-radius: 5rem;
  padding: 0.4rem 1rem;
}
#input {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.5rem;
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
