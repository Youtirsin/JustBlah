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
        <n-modal
          v-model:show="showEdit"
          :mask-closable="true"
        >
          <div id="edit">
            <n-card title="输入新的简介内容:">
              <div>
                <n-input type="text" v-model:value="newDescription"
                size="large" placeholder="输入新的简介内容:" />
              </div>
              <div id="edit-btns">
                <n-button color="#37a2c6" size="large" 
                @click="updateInfo">确定</n-button>
                <n-button size="large" @click="showEdit=false">取消</n-button>
              </div>
            </n-card>
          </div>
        </n-modal>
        <div id="operations">
            <button @click="showEdit=true">
                <n-icon color="#ffffff" size="36">
                  <Edit/>
                </n-icon>
            </button>
        </div>
        </div>
      </div>
    </div>
</template>

<script>
import { defineComponent, ref, getCurrentInstance } from 'vue'
import { Edit } from "@vicons/carbon"
import { useMessage } from 'naive-ui'

export default defineComponent({
  components: {
    Edit
  },
  setup() {
    const proxy = getCurrentInstance().proxy
    const user = ref({
      username: "test",
      email: "test",
      description: "test"
    })
    // const username = ref("test")
    // const email = ref("test")
    // const description = ref("test")
    const showEdit = ref(false)
    const newDescription = ref("")
    const message = useMessage()

    proxy.$axios({
      method: "get",
      url: proxy.$MyServerAPIs.MyInfo
    })
    .then(response => {
      if (response.data.code == 200) {
        // username.value = response.data.data.name
        // email.value = response.data.data.email
        // description.value = response.data.data.description
        user.value.username = response.data.data.name
        user.value.email = response.data.data.email
        user.value.description = response.data.data.description
      }else {
        console.log("load info error")
        window.location.href = "/login/signin"
      }
    })
    return {
      user,
      showEdit,
      newDescription,
      
      updateInfo() {
          const info = {
            name : user.value.username,
            email: user.value.email,
            description: newDescription.value
          }
          console.log(info)
          proxy.$axios({
            method: "post",
            url: proxy.$MyServerAPIs.UpdateMyInfo,
            data: info,
            headers: {
              'Content-Type': 'application/json;charset=UTF-8'
            }
          })
          .then(response => {
            if (response.data.code == 200) {
              message.success("更改成功")
              user.value.description = newDescription.value
            }else {
              message.error("更改失败")
            }
            showEdit.value = false
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
#operations button {
  width: 4rem;
  height: 4rem;
  border-radius: 50%;
  border: none;
  outline: none;
  background: #37a2c6;
  box-shadow: 2px 2px 3px #888888;
  color: white;
  cursor: pointer;
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
