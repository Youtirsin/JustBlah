<template>
  <div class='window'>
    <div class="left">
      <img alt="Just Blah" src="../assets/LoginBg.jpg" class="img">
    </div>
    <div class="right">
      <n-space vertical class="space">
        <n-layout>
          <n-layout-header>
            <n-h1>
              <n-text class='header'>
                Just Blah
              </n-text>
            </n-h1>
          </n-layout-header>
          <n-layout-content>
            <n-message-provider>
              <SignUp v-if="action == 'signup'"/>
              <SignIn v-if="action == 'signin'"/>
              <Reset  v-if="action == 'reset'" />
            </n-message-provider>
          </n-layout-content>
        </n-layout>
      </n-space>
    </div>
  </div>
</template>

<script>
import SignUp from '@/components/SignUp.vue'
import SignIn from '@/components/SignIn.vue'
import Reset from "@/components/Reset.vue"
import { defineComponent, ref, watch } from 'vue'
import { useRouter } from 'vue-router'

export default defineComponent({
  components: {
    SignUp,
    SignIn,
    Reset
  },
  setup() {
    const router = useRouter()
    const action = ref("signup")
    const actions = ["signin", "signup", "reset"]

    function redirect() {
      let params = router.currentRoute.value.params
      // console.log(params)
      if (actions.indexOf(params.action) == -1) {
        router.push("/login/signup")
      }else {
        action.value = params.action
      }
    }
    
    redirect()

    watch(()=> router.currentRoute.value.params, ()=> {
      if (router.currentRoute.value.path != '/home')
        redirect()
    })

    return {
      action
    }
  }
})
</script>
<style scoped>

.window {
  width: 1120px;
  height: 630px;
  min-width: 1120px;
  height: 630px;
  padding: 0;
  display: flex; 
  justify-content: center;
  align-items: center;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
.img {
  width: 420px;
  height: 630px;
}

.left {
  width: 420px;
  height: 630px;
}
.right {
  width: 700px;
  height: 630px;
}
.space {
  margin-left: 2rem;
}
.header {
  font-size: 4rem;
  font-weight: bolder;
  color: #37a2c6;
}
.red {
  border: 1px red solid;
}
</style>