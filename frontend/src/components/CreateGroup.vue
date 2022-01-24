<template>
    <div>
      <n-card title="输入群名:">
        <div>
          <n-input type="text" v-model:value="groupName"
          size="large" placeholder="输入新的群名:" />
        </div>
        <div id="edit-btns">
          <n-button color="#37a2c6" size="large" 
          @click="createGroup">确定</n-button>
        </div>
      </n-card>
    </div>
</template>

<script>
import { defineComponent, ref, getCurrentInstance } from 'vue'
import { useRouter } from 'vue-router'
import { useMessage } from 'naive-ui'

export default defineComponent({
  setup() {
    const router = useRouter()
    const message = useMessage()
    const proxy = getCurrentInstance().proxy
    const groupName = ref("")

    function createGroup() {
      if (groupName.value == '') {
        message.error("群名不能为空")
        return
      }
       proxy.$axios({
        method: "get",
        url: proxy.$MyServerAPIs.CreateGroup,
        params: {
          groupName: groupName.value
        }
      })
      .then(response => {
        if (response.data.code == 200) {
          message.success("创建成功")
        }else if (response.data.code == 2001) {
          message.error("用户未登录")
          router.push("/login/signin")
        }
      })
    }

    return {
      groupName,

      createGroup
    }
  }
})
</script>

<style scoped>
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
