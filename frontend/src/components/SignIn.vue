<template>
  <n-space vertical>
    <n-layout>
      <n-layout-header>
        <n-h1>
          <n-text>
            登录
          </n-text>
        </n-h1>
      </n-layout-header>
      <n-layout-content>
        <n-form
          size="large"
          label-width="80"
          ref="formRef"
          :model="formValue"
          :rules="rules"
        >
          <n-form-item label="用户名" path="name" class="mt-5">
            <n-input v-model:value="formValue.name" placeholder="name" 
              size="large" round autosize class="input"/>
          </n-form-item>
          <n-form-item label="密码" path="password">
            <n-input placeholder="password" v-model:value="formValue.password" 
              size="large" round type="password" autosize class="input"/>
          </n-form-item>
          <n-form-item>
            <n-button @click="handleValidateClick" attr-type="button" class="button"
              round size="large" color="#37a2c6">
              登录
            </n-button>
          </n-form-item>
        </n-form>
      </n-layout-content>
      <n-layout-footer class='footer'>
        <n-text>
          忘记密码? 
          <router-link to="/login/reset">
            <n-button text color="#37a2c6">
              重置
            </n-button>
          </router-link>
        </n-text>
      </n-layout-footer>
    </n-layout>
  </n-space>
</template>

<script>
  import { defineComponent, ref, getCurrentInstance } from 'vue'
  import { useMessage } from 'naive-ui'
  import { useRouter } from 'vue-router'

  export default defineComponent({
    setup () {
      const proxy = getCurrentInstance().proxy
      const router = useRouter()
      const formRef = ref(null)
      const message = useMessage()
      const formValue = ref({
        name: '',
        password: ''
      })
      function signIn(user) {
        proxy.$axios({
          method: "post",
          url: proxy.$MyServerAPIs.SignIn,
          params: user
        })
          .then(response => {
            console.log(response)
            if (response.data.code == 200) {
              message.success("登录成功")
              // window.location.href = "/home"
              router.push("/home")
            }else if (response.data.code == 2003) {
              message.error("密码错误")
            }else if (response.data.code == 2004) {
              message.error("用户不存在")
            }else {
              message.error("登录失败")
            }
          })
      }
      return {
        formRef,
        formValue,
        rules: {
          name: {
            required: true,
            validator (rule, value) {
              if (!value) {
                return new Error('请输入用户名')
              } else if (value.length < 2) {
                return new Error('用户名不要少于2个字符')
              } else if (value.length > 20) {
                return new Error('用户名不要超过20个字符')
              }
              return true
            },
            trigger: 'blur'
          },
          password: {
            required: true,
            validator (rule, value) {
              if (!value) {
                return new Error('请输入密码')
              } else if (value.length < 8) {
                return new Error('密码长度应大于8')
              }
              return true
            },

            trigger: ['input']
          }
        },
        handleValidateClick () {
          formRef.value.validate((errors) => {
            if (!errors) {
              const user = {
                name: formValue.value.name,
                password: formValue.value.password
              }

              signIn(user)
              
            } else {
              console.log(errors)
              message.error('无效的登录信息')
            }
          })
        },
      }
    }
  })
</script>

<style scoped>
.input {
  min-width: 80%;
}
.button {
  width: 80%;
}
.footer {
  background: white;
}
.red {
  border: 1px red solid;
}
</style>
