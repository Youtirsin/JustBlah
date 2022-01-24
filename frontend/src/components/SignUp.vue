<template>
  <n-space vertical>
    <n-layout>
      <n-layout-header>
        <n-h1>
          <n-text>
            注册
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
          <n-form-item label="邮箱" path="email">
            <n-input placeholder="email" v-model:value="formValue.email" 
              size="large" round autosize class="input"/>
          </n-form-item>
          <n-form-item label="密码" path="password">
            <n-input placeholder="password" v-model:value="formValue.password" 
              size="large" round type="password" autosize class="input"/>
          </n-form-item>
          <n-form-item>
            <n-button @click="handleValidateClick" attr-type="button" class="button"
              round size="large" color="#37a2c6">
              注册
            </n-button>
          </n-form-item>
        </n-form>
      </n-layout-content>
      <n-layout-footer class='footer'>
        <n-text>
          已有账号? 
          <router-link to="/login/signin">
            <n-button text color="#37a2c6">
              登录
            </n-button>
          </router-link>
        </n-text>
      </n-layout-footer>
    </n-layout>
  </n-space>
</template>

<script>
  import { useMessage } from 'naive-ui'
  import { defineComponent, ref, getCurrentInstance } from 'vue'
  import { useRouter } from 'vue-router'

  export default defineComponent({
    setup () {
      const router = useRouter()
      const proxy = getCurrentInstance().proxy
      const formRef = ref(null)
      const message = useMessage()
      const formValue = ref({
        name: '',
        email: '',
        password: ''
      })
      function signUp(user) {
        proxy.$axios({
          method: "post",
          url: proxy.$MyServerAPIs.SignUp,
          data: user,
          headers: {
            'Content-Type': 'application/json;charset=UTF-8'
          }
        })
          .then(response => {
            console.log(response)
            if (response.data.code == 200) {
              message.success("注册成功")
              // window.location.href = "/login/signin"
              router.push("/login/signin")
            }else if (response.data.code == 2002) {
              message.error("用户名已存在")
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
          email: {
            required: true,
            validator (rule, value) {
              const el = /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/
              if (!value) {
                return new Error('请输入邮箱')
              } else if (!el.test(value)) {
                return new Error('无效的邮箱格式')
              }
              return true
            },
            trigger: ['input', 'blur']
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
                password: formValue.value.password,
                email: formValue.value.email
              }
              signUp(user)
            } else {
              console.log(errors)
              message.error('无效的注册信息')
            }
          })
        }
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
</style>
