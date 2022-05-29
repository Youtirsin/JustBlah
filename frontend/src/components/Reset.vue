<script setup>
import { NSpace, NLayout, NLayoutHeader,
  NLayoutContent, NLayoutFooter, NH1, NForm, NText, 
  NFormItem, NInput, NButton } from 'naive-ui'
import { ref } from 'vue'

document.title = "JustBlah - reset"

const formRef = ref(null)
const formValue = ref({
  name: '',
  password: '',
  email: ''
})


const rules = {
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
}

function reset() {
  // TODO: reset account
}

function handleValidateClick() {
  formRef.value.validate((errors) => {
    if (error) {
      console.log(errors)
      message.error('无效的注册信息')
      return
    }
    const user = {
      name: formValue.value.name,
      password: formValue.value.password,
      email: formValue.value.email
    }
    reset(user)
  })
}

</script>

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
</style>