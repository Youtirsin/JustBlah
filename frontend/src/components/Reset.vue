<template>
  <n-space vertical>
    <n-layout>
      <n-layout-header>
        <n-h1>
          <n-text>
            密码重置
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
          <n-form-item label="用户名" path="name">
            <n-input v-model:value="formValue.name" placeholder="name" 
              size="large" round autosize class="input"/>
          </n-form-item>
          <n-form-item label="邮箱" path="email">
            <n-input placeholder="email" v-model:value="formValue.email" 
              size="large" round autosize class="input"/>
          </n-form-item>
          <n-form-item label="新密码" path="password">
            <n-input placeholder="password" v-model:value="formValue.password" 
              size="large" round type="password" autosize class="input"/>
          </n-form-item>
          <n-form-item>
            <n-button @click="handleValidateClick" attr-type="button" class="button"
              round size="large" color="#37a2c6">
              重置
            </n-button>
          </n-form-item>
        </n-form>
      </n-layout-content>
    </n-layout>
  </n-space>
</template>

<script>
  import { defineComponent, ref, getCurrentInstance } from 'vue'
  import { useRouter } from 'vue-router'

  import { useMessage } from 'naive-ui'

  export default defineComponent({
    setup () {
      const proxy = getCurrentInstance().proxy
      const router = useRouter()

      const formRef = ref(null)
      const message = useMessage()
      const formValue = ref({
        name: '',
        email: '',
        password: ''
      })
      function reset(user) {
        proxy.$axios
          .post(proxy.$MyServerAPIs.Reset, user)
          .then(response => {
            console.log(response)
            if (response.data.code == 200) {
              message.success("重置成功")
              // window.location.href = "/login/signin"
              router.push("/login/signin")
            }else if (response.data.code == 2004) {
              message.error("用户名不存在")
            }else if (response.data.code == 2004) {
              message.error("用户名不存在")
            }else if (response.data.code == 2005) {
              message.error("用户信息验证不正确")
            }else {
              message.error("重置失败")
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

            trigger: ['input', 'blur']
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
              reset(user)
            } else {
              console.log(errors)
              message.error('无效的重置信息')
            }
          })
        }
      }
    }
  })
</script>

<style scoped>
.formItem {
  margin-bottom: 0;
}
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
